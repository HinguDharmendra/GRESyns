package org.aakashlabs.gresyns;

import java.util.ArrayList;
import java.util.List;

public class ListItem {
	public String sid;
	public String gloss;
public List<String> widList;
//public int count=0;
public ListItem(){
	widList=new ArrayList<String>();
	
}
public void setSID(String sid)
{
this.sid=sid;	
}
public void setGloss(String gloss)
{
this.gloss=gloss;	
}
public void addWID(String wid)
{	
	widList.add(wid);
}
public String getSID()
{
return sid;	
}
public String getGloss()
{
return gloss;	
}
/*public String getWID(int i)
{
return widList
}
public int getCount()
{
return count;	
}*/
}
