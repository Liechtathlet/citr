package ch.zhaw.mdp.lhb.citr.jpa.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ch.zhaw.mdp.lhb.citr.Logging.LoggingFactory;
import ch.zhaw.mdp.lhb.citr.Logging.LoggingStrategy;
import ch.zhaw.mdp.lhb.citr.jpa.entity.GroupDVO;
import ch.zhaw.mdp.lhb.citr.jpa.entity.SubscriptionDVO;
import ch.zhaw.mdp.lhb.citr.jpa.entity.UserDVO;
import ch.zhaw.mdp.lhb.citr.jpa.service.UserRepository;

/**
 * @author Daniel Brun
 * @author Simon Lang
 * 
 *         Implementation of the DB-Service interface {@link UserRepository}
 */
@Service("userService")
public class UserRepositoryJpaImpl implements UserRepository {

    private static final LoggingStrategy LOG = LoggingFactory.get();

    private EntityManager entityManager;

    @Transactional(readOnly = true)
    public UserDVO getById(int id) {
	return entityManager.find(UserDVO.class, id);
    }

    @Transactional(readOnly = true)
    public UserDVO getByOpenId(String openId) {
	UserDVO user = new UserDVO();
	user.setOpenId(openId);

	return findPerson(user);
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public List<UserDVO> getAll() {
	Query query = entityManager.createNamedQuery("User.findAll");
	List<UserDVO> userList = query.getResultList();

	if (userList == null) {
	    userList = new ArrayList<UserDVO>();
	}

	return userList;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public boolean save(UserDVO user) {

	entityManager.persist(user);
	entityManager.flush();

	return true;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public boolean update(UserDVO user) {

	entityManager.merge(user);
	entityManager.flush();

	return true;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public boolean delete(UserDVO user) {
	user = entityManager.getReference(UserDVO.class, user.getId());

	if (user == null) {
	    return false;
	}

	entityManager.remove(user);
	entityManager.flush();

	return true;
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public UserDVO findPerson(UserDVO user) {

	Query queryFindPerson = entityManager.createNamedQuery("User.findUser");
	queryFindPerson.setParameter("username", user.getUsername());
	queryFindPerson.setParameter("openId", user.getOpenId());

	UserDVO userFromDb = (UserDVO) queryFindPerson.getSingleResult();

	if (userFromDb != null) {
	    userFromDb.getSubscriptions().size();
	    
	    for(SubscriptionDVO subscr : userFromDb.getSubscriptions()){
		subscr.getGroup().getTags().size();
		subscr.getGroup().getSubscriptions().size();
	    }
	    
	    userFromDb.getCreatedGroups().size();

	    if (userFromDb.getCreatedGroups() != null) {
		for (GroupDVO grp : userFromDb.getCreatedGroups()) {
		    if (grp.getTags() != null) {
			grp.getTags().size();
		    }
		}
	    }
	}
	return userFromDb;
    }

    @Override
    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public List<SubscriptionDVO> getsubscriptions(UserDVO user) {
	return findPerson(user).getSubscriptions();
    }

    /**
     * Sets the Entity Manager
     * 
     * @param entityManager The Entity Manager
     */
    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
	this.entityManager = entityManager;
    }

    /**
     * Gets the Entity Manager
     * 
     * @return The Entity Manager
     */
    public EntityManager getEntityManager() {
	return entityManager;
    }
}
