package org.aakashlabs.gresyns;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class GroupDisplay extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group_display);

        setTitle("Group");

		Intent i=getIntent();
		String sid=i.getStringExtra("sid");
		String gloss=i.getStringExtra("gloss");
		
		DBManager db=new DBManager(this);
		db.open();
		
		List<String> words=db.getGroupWords(sid);
		
		db.close();
		
		TextView tv=(TextView)findViewById(R.id.Meaning);
		tv.setText(gloss.replace("; "+'\"',";\n"+'\"'));
		ListView lv=(ListView)findViewById(R.id.group_word_list);
		lv.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, words));
	}

	

}
