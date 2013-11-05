package ch.zhaw.mdp.lhb.citr.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import ch.zhaw.mdp.lhb.citr.R;
import ch.zhaw.mdp.lhb.citr.dto.GroupDTO;
import ch.zhaw.mdp.lhb.citr.rest.IRGroupServices;

/**
 * Created with IntelliJ IDEA.
 * User: michael
 * Date: 30.10.13
 * Time: 22:58
 * To change this template use File | Settings | File Templates.
 */
public class GroupCreateActivity extends CitrBaseActivity {

    private static final String TAG = "GroupCreateActivity";

    private IRGroupServices groupServices;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_create);
		
    }

    public void onAECreateGroup(View view) {

        EditText editText = (EditText) findViewById(R.id.etGroupName);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.rgMode);

        String msgStr = editText.getText().toString();
        int radButtonId = radioGroup.getCheckedRadioButtonId();
        View radioButton = radioGroup.findViewById(radButtonId);
        int idx = radioGroup.indexOfChild(radioButton);

        Log.d(TAG, "Activity-Event: Create group with mode: " + idx);


        if (msgStr != null && !msgStr.equals("") && idx !=  0) {
            GroupDTO group = new GroupDTO();
            group.setName(msgStr);
            group.setPublicGroup(true);

            boolean result = groupServices.createGroup(group);
            String resultMsg = "Das citr konnte nicht übermittelt werden.";

            if (result) {
                resultMsg = " Das citr wurde erfolgreich übermittelt.";
                editText.setText("");
            }

            Toast.makeText(getApplicationContext(), resultMsg, Toast.LENGTH_SHORT).show();
        }


    }
    //  http://localhost:8080/citrServer/groups/create/{name:string}/{state:int}/{mode:int}

}