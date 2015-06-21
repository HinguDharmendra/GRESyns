package org.aakashlabs.gresyns;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class NewGroup extends Activity {

	DBManager db;
	EditText meaning;
	Spinner spinner;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_group);
        setTitle("Create Group");


		meaning=(EditText) findViewById(R.id.meaningBox);
		spinner = (Spinner) findViewById(R.id.posSpinner);

		Button add=(Button)findViewById(R.id.create);
		add.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String result=null;
				db = new DBManager(getApplicationContext());
				db.open();
				result=db.newGroup(meaning.getText().toString(),spinner.getSelectedItem().toString(),DisplayActivity.word);
				db.close();
				Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
				finish();
				
			}
		});
		
		Button cancel=(Button)findViewById(R.id.cancel);
		cancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
	}
	
	

}
