package org.aakashlabs.gresyns;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class GroupList extends Activity {
	List<ListItem> list=new ArrayList<ListItem>();
	private final String[] from = new String[] { "title" };
    private final int[] to = new int[] { R.id.title };
    private static ProgressDialog progDailog;
    ArrayList<HashMap<String, Object>>fillMaps;
    ArrayList<HashMap<String,Object>>fillMaps1;
    Thread t;
    int flag=0;
    private Handler progressHandler ;
	DBManager db;
	EditText meaning;
	Spinner spinner;
	List<ListItem> glosslist;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group_list);
        setTitle("Group");

		progDailog=new ProgressDialog(this);
		progDailog.setTitle("Searching..");
		progDailog.setMessage("Please Wait!!");
		progDailog.setCancelable(false);
		progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	    final ListView listview;
	  
		glosslist=new ArrayList<ListItem>();
	    db=new DBManager(getApplicationContext());
		db.open();
		
		glosslist = db.getGroupList();

		db.close();
		
		listview = (ListView) findViewById(R.id.grouplist);
		listview.setFastScrollEnabled(true);
		
		fillMaps =new ArrayList<HashMap<String, Object>>();
		for(ListItem li:glosslist){
			HashMap<String, Object> map = new HashMap<String, Object>();
		    //String sid=li.getSID();
			String data=li.getGloss();
			String mean[]=data.split(";");
		    map.put("title",mean[0]);// This will be shown in R.id.title

		    fillMaps.add(map);	
		}

		
		final SimpleAdapter adapter = new SimpleAdapter(this, fillMaps, R.layout.row, from, to);
	    listview.setAdapter(adapter);   

	    listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View arg1, int position,
					long id) {ListItem li;
				// TODO Auto-generated method stub
				if(flag==0)
				li=glosslist.get(position);
				else li=list.get(position);
				try{
					
					Intent ourIntent=new Intent(getApplicationContext(),GroupDisplayConfirm.class);
					ourIntent.putExtra("sid",li.getSID());
					ourIntent.putExtra("gloss",li.getGloss());
					startActivity(ourIntent);
					
				}
				catch(Exception e)
				{e.printStackTrace();}
			}
			
		});

		Button b=(Button)findViewById(R.id.newlist);
b.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i=new Intent(getApplicationContext(),NewGroup.class);
				startActivity(i);
			}
		});
	final EditText et=(EditText)findViewById(R.id.etext);
	final ImageButton srch=(ImageButton)findViewById(R.id.search_group_list);
	final TextView tv=(TextView)findViewById(R.id.grouptv);
	srch.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			final String srchData=et.getText().toString();
			tv.setText("Searching groups for words matching "+'\"'+srchData+'\"');
			tv.setTextColor(Color.RED);
			if(srchData.length()>0)
			{	
				flag=1;
				fillMaps1 = new ArrayList<HashMap<String, Object>>();
				progressHandler = new Handler() 
				 {

				     public void handleMessage(Message msg1) 
				     {
				    		 progDailog.dismiss();				         	
				         //toGroup();
				    		 SimpleAdapter adapter11 = new SimpleAdapter(GroupList.this, fillMaps1, R.layout.row, from, to);
				 		    listview.setAdapter(adapter11);
				    		 super.handleMessage(msg1);
				     }
				 };
				 progDailog.show();
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					
					db=new DBManager(GroupList.this);
					db.open();
					list=db.searchGroup(srchData);
					db.close();
					for(ListItem li:list){
						HashMap<String, Object> map = new HashMap<String, Object>();
					    //String sid=li.getSID();
						String data=li.getGloss();
						//String mean[]=data.split(";");
					    System.out.println(data);
						map.put("title",data);// This will be shown in R.id.title
					    fillMaps1.add(map);
					    System.out.println(fillMaps1.get(0));
					}
					
				    progressHandler.sendEmptyMessage(0);
				}
			}).start();
			SimpleAdapter adapter11 = new SimpleAdapter(GroupList.this, fillMaps1, R.layout.row, from, to);
		    listview.setAdapter(adapter11);
			}
			else {listview.setAdapter(adapter); 
			tv.setText("");
			}
		}
	});
	
	}
	
	


}
