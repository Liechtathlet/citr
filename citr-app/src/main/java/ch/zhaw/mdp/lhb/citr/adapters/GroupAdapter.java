package ch.zhaw.mdp.lhb.citr.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import ch.zhaw.mdp.lhb.citr.R;
import ch.zhaw.mdp.lhb.citr.dto.GroupDTO;

import java.util.List;

/**
 * @author Michael Hadorn
 * Date: 18.11.13
 * Time: 09:38
 *
 * Adapter for used to show groups in view lists. Generate the single items for the listview
 */
public class GroupAdapter extends ArrayAdapter<GroupDTO> {

    /**
     * Current context (used for inflater)
     */
    private final Context context;

    /**
     * Container with groups
     */
    private List<GroupDTO> groups;

	/**
     * the adapter for groups
     * @param context
     * @param groups
     */
    public GroupAdapter(Context context, List<GroupDTO> groups) {
        super(context, R.layout.row_group, groups);
        this.context = context;
        this.groups = groups;
    }

    /**
     * the creator for each item in a listview
     * @param position current position of item (to collect data)
     * @param convertView
     * @param parent the parent object
     * @return one row for the listview
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_group, parent, false);

	    TextView groupName = (TextView) rowView.findViewById(R.id.tvGroupName);
        TextView groupTextLeft = (TextView) rowView.findViewById(R.id.tvGroupTextLeft);
        TextView groupTextRight = (TextView) rowView.findViewById(R.id.tvGroupTextRight);

        // defaults android color from: http://developer.android.com/design/style/color.html
	    groupName.setText(this.groups.get(position).getName());
        groupTextLeft.setText("");
        groupTextRight.setText("");
        groupTextRight.setTextColor(Color.BLACK);

        return rowView;
    }

    /**
     * @param aGroups the groups to set
     */
    public void setGroups(List<GroupDTO> aGroups) {
        groups = aGroups;
    }
    
}
