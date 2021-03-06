package ch.zhaw.mdp.lhb.citr.jpa.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
@Entity
@Table(name = "tbl_subscription")
public class SubscriptionDVO implements Serializable {
	@Id
	@Column(name = "usr_id")
	private int userId;

	@Id
	@Column(name = "grp_id")
	private int groupId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumns({@JoinColumn(name = "usr_id", insertable=false, updatable=false)})
	private UserDVO user;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumns({@JoinColumn(name = "grp_id", insertable=false, updatable=false)})
	private GroupDVO group;

	@Column(name = "usg_state")
	@Enumerated(EnumType.STRING)
	private State state;


	/**
	 * @author Simon Lang
	 *
	 * Data-Class for 'User Group'.
	 */
	public enum State {
		APPROVED,
		OPEN,
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public UserDVO getUser() {
		return user;
	}

	public void setUser(UserDVO user) {
		this.user = user;
	}

	public GroupDVO getGroup() {
		return group;
	}

	public void setGroup(GroupDVO group) {
		this.group = group;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
}
