package org.aakashlabs.gresyns;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;



public class ViewWordList extends Activity {
	List<ListItem> list=new ArrayList<ListItem>();
	String data=null;
	DBManager db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_word_list);
		setTitle("Groups");

		String[] from = new String[] { "title", "description" };
	    int[] to = new int[] { R.id.title, R.id.description };
		db=new DBManager(ViewWordList.this);
		db.open();
		
		list=db.getList(DisplayActivity.word);
		ListView l=(ListView)findViewById(R.id.viewwordlist);
		List<HashMap<String, Object>> fillMaps = new ArrayList<HashMap<String, Object>>();
		for(ListItem li:list){
			HashMap<String, Object> map = new HashMap<String, Object>();
		    String sid=li.getSID();
		    data=db.getGloss(sid);
			String mean[]=data.split(";");
		    map.put("title",mean[0]);// This will be shown in R.id.title
		    String desc="";
		    		for(String wid:li.widList)
		    		{
		    			desc+=db.getwd(wid)+" , ";
		    		}
		    map.put("description",desc); // And this in R.id.description
		    fillMaps.add(map);

		}
		SimpleAdapter adapter = new SimpleAdapter(this, fillMaps, R.layout.row, from, to);
	    l.setAdapter(adapter);
	    db.close();
	    l.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View arg1, int position,
					long id) {
				// TODO Auto-generated method stub
				
				Log.d("ITEM NAME",String.valueOf(position));
				ListItem li=list.get(position);
				
				try{
					
					Intent ourIntent=new Intent("org.aakashlabs.gresyns.GROUPDISPLAY");
					ourIntent.putExtra("sid",li.getSID());
					db.open();
					ourIntent.putExtra("gloss",db.getGloss(li.getSID()));
					db.close();
					startActivity(ourIntent);
					
				}
				catch(Exception e)
				{e.printStackTrace();}
			}
			
		});
	    
	    
		Button b=(Button)findViewById(R.id.badd);
		b.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getApplicationContext(),GroupList.class);
				startActivity(i);
			}
		});

	}

}
