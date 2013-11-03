package ch.zhaw.mdp.lhb.citr;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;
import ch.zhaw.lhb.citr.dto.UserDTO;
import ch.zhaw.mdp.lhb.citr.activities.CitrBaseActivity;
import ch.zhaw.mdp.lhb.citr.com.rest.RESTBackgroundTask;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Login extends CitrBaseActivity {

	private static final String SERVICE_URL = "http://10.0.2.2:8080/citrServer/myresources/";

	private static final String TAG = "AndroidRESTClientActivity";

    public final static String CITR_MAINPAGE = "ch.zhaw.mdp.lhb.citr.Main";

	/**
	 * Called when the activity is first created.
	 * 
	 * @param savedInstanceState
	 *            If the activity is being re-initialized after previously being
	 *            shut down then this Bundle contains the data it most recently
	 *            supplied in onSaveInstanceState(Bundle). <b>Note: Otherwise it
	 *            is null.</b>
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(ch.zhaw.mdp.lhb.citr.R.menu.main, menu);

        this.retrieveSampleData();

		return true;
	}

	public void retrieveSampleData() {
		String sampleURL = SERVICE_URL + "test";

		RESTBackgroundTask rest = new RESTBackgroundTask(this, RESTBackgroundTask.HTTP_GET_TASK);
		
	}

	public void handleResponse(String response) {

		try {
			EditText textF = (EditText) findViewById(R.id.loginUserId);
			// System.out.println(response);
            textF.setText("Hi " + response);

            Toast.makeText(getApplicationContext(), "answer: " + response, Toast.LENGTH_SHORT).show();

			ObjectMapper mapper = new ObjectMapper();
			List<UserDTO> user = mapper.readValue(response, new TypeReference<List<UserDTO>>(){});

//			Map<String, String> map = new HashMap<String, String>();
//			ObjectMapper mapper = new ObjectMapper();
//
//			try {
//
//				// convert JSON string to Map
//				map = mapper.readValue(response,
//						new TypeReference<HashMap<String, String>>() {
//						});
//
//
//			} catch (Exception e) {
//				e.printStackTrace();
//			}

			textF.setText("Hi " + user.get(0).getName());

		} catch (Exception e) {
			Log.e(TAG, e.getLocalizedMessage(), e);
		}

	}

	public void hideKeyboard() {

		InputMethodManager inputManager = (InputMethodManager) Login.this
				.getSystemService(Context.INPUT_METHOD_SERVICE);

		inputManager.hideSoftInputFromWindow(Login.this
				.getCurrentFocus().getWindowToken(),
				InputMethodManager.HIDE_NOT_ALWAYS);
	}


    public void userLogin(View view) {

        Toast.makeText(getApplicationContext(), "hi", Toast.LENGTH_SHORT).show();


        Intent intent = new Intent(this, Main.class);
        EditText editText = (EditText) findViewById(R.id.loginUserId);
        String message = editText.getText().toString();
        intent.putExtra(CITR_MAINPAGE, message);
        startActivity(intent);

    }
}