package ch.zhaw.mdp.lhb.citr.activities;

import java.util.ArrayList;
import java.util.List;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import ch.zhaw.mdp.lhb.citr.R;
import ch.zhaw.mdp.lhb.citr.adapters.GroupAdapter;
import ch.zhaw.mdp.lhb.citr.com.rest.facade.ClientRGroupServicesImpl;
import ch.zhaw.mdp.lhb.citr.dto.GroupDTO;
import ch.zhaw.mdp.lhb.citr.rest.IRGroupServices;
import ch.zhaw.mdp.lhb.citr.util.SharedPreferencHelper;
import ch.zhaw.mdp.lhb.citr.widget.CitrWidgetProvider;

/**
 * @author Daniel Brun
 * 
 *         Activity for Widget-Configuration
 */
public class ConfigureWidgetActivity extends CitrBaseActivity {
    /**
     * Tag of Activity
     */
    private static final String TAG = "ConfigureWidgetActivity";

    /**
     * Service to manage group data via rest
     */
    private IRGroupServices groupServices;

    /**
     * container for all groups where i'm member of it
     */
    private List groupsMemberOf = new ArrayList();

    private int widgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    /**
     * Called when the activity is first created.
     * 
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle). <b>Note: Otherwise it is null.</b>
     */
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	groupServices = new ClientRGroupServicesImpl(this);

	// FIXME:Remove
	// starts: dummy groups
	GroupDTO group1 = new GroupDTO();
	group1.setName("Gruppe 1");
	GroupDTO group2 = new GroupDTO();
	group2.setName("Gruppe 2");
	groupsMemberOf.add(group1);
	groupsMemberOf.add(group2);
	// end: dummy groups

	// Set the result to CANCELED. This will cause the widget host to cancel
	// out of the widget placement if they press the back button.
	setResult(RESULT_CANCELED);

	// Set the view layout resource to use.
	setContentView(R.layout.widget_configure_layout);

	// Get the list view
	ListView list = (ListView) findViewById(R.id.lvMyGroupResult);
	final GroupAdapter adapterMemberOf = new GroupAdapter(this,
		this.groupsMemberOf);
	list.setAdapter(adapterMemberOf);

	// add listener
	list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	    @Override
	    public void onItemClick(AdapterView<?> aParent, View aView,
		    int aPos, long anId) {
		GroupDTO group = (GroupDTO) aParent.getItemAtPosition(aPos);

		// Store config
		sharedPrefs.storeString(
			SharedPreferencHelper.SHARED_PREF_WIDGET, "config-"
				+ widgetId, group.getHashId());

		// Update Widget
		final Context context = ConfigureWidgetActivity.this;
		AppWidgetManager appWidgetManager = AppWidgetManager
			.getInstance(context);

		CitrWidgetProvider.updateAppWidget(context, appWidgetManager,
			widgetId);

		// Return
		Intent resultValue = new Intent();
		resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
			widgetId);
		setResult(RESULT_OK, resultValue);
		finish();
	    }
	});

	// Get widget id
	Intent intent = getIntent();
	Bundle extras = intent.getExtras();
	if (extras != null) {
	    widgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
		    AppWidgetManager.INVALID_APPWIDGET_ID);
	}

	// Fail if no valid id
	if (widgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
	    finish();
	}
    }
}