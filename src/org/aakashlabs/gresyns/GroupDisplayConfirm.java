package org.aakashlabs.gresyns;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class GroupDisplayConfirm extends Activity {

	String sid=null;
	DBManager db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group_display_confirm);
        setTitle("Add");

		Intent i=getIntent();
		sid=i.getStringExtra("sid");
		String gloss=i.getStringExtra("gloss");
		
		db=new DBManager(this);
		db.open();
		
		List<String> words=db.getGroupWords(sid);
		
		db.close();
		
		TextView tv=(TextView)findViewById(R.id.Meaning);
		tv.setText(gloss.replace("; "+'\"',";\n"+'\"'));
		ListView lv=(ListView)findViewById(R.id.group_word_list);
		lv.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, words));
		
		Button b=(Button)findViewById(R.id.add_confirm);
		b.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				db.open();
				String result=db.addToGroup(sid,DisplayActivity.word);
				Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
				finish();
				db.close();
			}
		});
		
		Button cancel=(Button)findViewById(R.id.cancel_confirm);
		cancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
	}

	

}
