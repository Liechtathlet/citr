package ch.zhaw.mdp.lhb.citr.adapters;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import ch.zhaw.mdp.lhb.citr.R;
import ch.zhaw.mdp.lhb.citr.dto.GroupDTO;
import ch.zhaw.mdp.lhb.citr.dto.SubscriptionDTO;

import java.util.List;

public class SubscriptionAdapter extends ArrayAdapter<SubscriptionDTO>  {


	private final Context context;
	private final String userStateText;
	private final int textColor;
	private final List<SubscriptionDTO> subscriptionDTOs;
	private SubscriptionDTO subscriptionDTO;

	/**
	 * the adapter for groups
	 * @param context
	 * @param subscriptionDTOs
	 */
	public SubscriptionAdapter(Context context, List<SubscriptionDTO> subscriptionDTOs) {
		super(context, R.layout.row_group, subscriptionDTOs);
		this.context = context;
		this.subscriptionDTOs = subscriptionDTOs;
		userStateText = "";
		textColor = Color.BLACK;
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
		subscriptionDTO = subscriptionDTOs.get(position);
		SubscriptionDTO.State state = subscriptionDTO.getState();

		String userStateText = "";
		int textColor = Color.BLACK;

		switch (state) {
			case approved:
				textColor = Color.parseColor("#669900");
				userStateText = "Mitglied";
				break;
			case open:
				textColor = Color.parseColor("#FF8800");
				userStateText = "Anfrage ausstehend";
				break;
		}

		// defaults android color from: http://developer.android.com/design/style/color.html
		groupName.setText(subscriptionDTO.getGroup().getName());
		groupTextLeft.setText("");
		groupTextRight.setText(userStateText);
		groupTextRight.setTextColor(textColor);

		return rowView;
	}
}