package org.aakashlabs.gresyns;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class DisplayActivity extends Activity {
public static String word;
public int id;
TextView wordview;
TextView greview;
int max;
int min;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent=getIntent();
		word=intent.getStringExtra("ID");
        max=intent.getIntExtra("maxwid",-1);
        min=intent.getIntExtra("minwid",-1);
		setContentView(R.layout.activity_display);

		 wordview=(TextView)findViewById(R.id.word);
		greview=(TextView)findViewById(R.id.gremeaning);
		final DBManager db=new DBManager(this);
		db.open();
		id=Integer.parseInt(db.getID(word));
		db.close();
		setMeaning(word);
		Button b=(Button)findViewById(R.id.bview);
		Button b1=(Button)findViewById(R.id.next1);
		Button b2=(Button)findViewById(R.id.prev1);
		b.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent("org.aakashlabs.gresyns.VIEWWORDLIST");
				//i.putExtra("word",word);
				startActivity(i);
			}
		});
b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d("NEXT","NEXT");
				if(id==max)
					id=min;
				else id=id+1;
				db.open();
				word=db.getwd(String.valueOf(id));
				setMeaning(word);
				db.close();
			}
		});
b2.setOnClickListener(new View.OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Log.d("PREV","PREV");
		if(id==min)
			id=max;
		else id=id-1;
		db.open();
		word=db.getwd(String.valueOf(id));
		setMeaning(word);
		db.close();
	}
});

	}
public void setMeaning(String word)
{	
	final ListView listview = (ListView) findViewById(R.id.meanings);
	DBManager db=new DBManager(this);
	db.open();
	setTitle(word);
	wordview.setText(word);
	String gremeaning = db.getGREMeaning(word);
	greview.setText(gremeaning);
	
    List<String[]> data = db.getWord(word);
    db.close();
    
    List<String> meaning = new ArrayList<String>();
    Iterator<String[]> itr = data.iterator();
	String[] item;

    while(itr.hasNext())
    {
    	item=itr.next();
		item[1]=item[1].replace("; "+'\"',";\n"+'\"');
    	meaning.add(item[0]+"  "+item[1]);
    	
    }

	listview.setAdapter(new ArrayAdapter<String>(DisplayActivity.this,android.R.layout.simple_list_item_1, meaning));

}
}
