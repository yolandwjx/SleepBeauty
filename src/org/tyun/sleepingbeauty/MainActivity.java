package org.tyun.sleepingbeauty;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ListActivity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class MainActivity extends ListActivity {
	  ArrayList<Map<String, String>> list;
	  int year,month,day,hour,minute,current_changing;
	  static final int DATE_DIALOG_ID = 999;
	  static final int TIME_DIALOG_ID = 998;
	  static final int TIME_CHANGE_DIALOG_ID = 997;
	  SimpleAdapter adapter;
	  
	  
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    list = buildData();
	    String[] from = { "date", "time" };
	    int[] to = { android.R.id.text1, android.R.id.text2 };

	    adapter = new SimpleAdapter(this, list,
	        android.R.layout.simple_list_item_2, from, to);
	    setListAdapter(adapter);
	    
	    
	  }
	  
	  @SuppressWarnings("deprecation")
	@Override
	  protected void onListItemClick(ListView l, View v, int position, long id) {
	    //String item = (String) getListAdapter().getItem(position);
		if  (position==list.size()-1){
			Toast.makeText(this, "Exact same time will be ignored", Toast.LENGTH_SHORT).show();
	    
	    showDialog(TIME_DIALOG_ID);
	    showDialog(DATE_DIALOG_ID);
	    //DatePickerDialog dateFragment = new DatePickerDialog(this, datePickerListener,year_now,month_now,day_now);
        //dateFragment.show(getFragmentManager(), "DatePicker");
	    
	    //TimePickerDialog timeFragment = new TimePickerDialog(this,timePickerListener,hour_now,minute_now,true);
        ////timeFragment.show(getFragmentManager(), "TimePicker");
	    
        }
		else{
			Toast.makeText(this, "Changing the current record", Toast.LENGTH_SHORT).show();
			current_changing=position;
			showDialog(TIME_CHANGE_DIALOG_ID);
		    showDialog(DATE_DIALOG_ID);
		}
	  }
	  
	  @Override
	  protected Dialog onCreateDialog(int id) {
		  Calendar now = Calendar.getInstance();
		    int hour_now = now.get(Calendar.HOUR_OF_DAY);            
		    int minute_now = now.get(Calendar.MINUTE);
		    int year_now = now.get(Calendar.YEAR);
		    int month_now = now.get(Calendar.MONTH);
		    int day_now = now.get(Calendar.DATE);
		  switch (id) {
		    case DATE_DIALOG_ID:
		        return new DatePickerDialog(this, datePickerListener,year_now,month_now,day_now);
		    case TIME_DIALOG_ID:
		    		return new TimePickerDialog(this,timePickerListener,hour_now,minute_now,true);
		    case TIME_CHANGE_DIALOG_ID:
	    			return new TimePickerDialog(this,timeChangeListener,hour_now,minute_now,true);
		    }
		    return null;
	  }
	  
	  
	  
	  private ArrayList<Map<String, String>> buildData() {
	    ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
	    //list.add(putData("Windows7", "Windows7"));
	    list.add(putData("Add..", "Click on existing record to modify"));
	    //list.add(putData("iPhone", "iPhone"));
	    return list;
	  }
	  private HashMap<String, String> putData(String name, String time) {
	    HashMap<String, String> item = new HashMap<String, String>();
	    item.put("date", name);
	    item.put("time", time);
	    return item;
	  }
	  
	  private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

		  // when dialog box is closed, below method will be called.
		  public void onDateSet(DatePicker view, int selectedYear,
				  int selectedMonth, int selectedDay) {
			  year = selectedYear;
			  month = selectedMonth;
			  day = selectedDay;

		  }
	  };
	  
	  private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {

		  // when dialog box is closed, below method will be called.
		  public void onTimeSet(TimePicker view, int selectedDay, int selectedMinute) {
			  hour = selectedDay;
			  minute = selectedMinute;
			  String set_date=year+"-"+(month+1)+"-"+day;
			  String set_time=hour+":"+minute;
			  if(list.get(0).get("date")==null){
				  Log.d("SLEEPB", "Can't find");
				  list.add(0,putData(year+"-"+(month+1)+"-"+day, hour+":"+minute));
				  adapter.notifyDataSetChanged();
			  }
			  else if(!(list.get(0).get("date").equals(set_date)) || !(list.get(0).get("time").equals(set_time))){
				  Log.d("SLEEPB", "No duplicate");
				  list.add(0,putData(year+"-"+(month+1)+"-"+day, hour+":"+minute));
				  adapter.notifyDataSetChanged();
			  }
		  }
	  };
	  
	  private TimePickerDialog.OnTimeSetListener timeChangeListener = new TimePickerDialog.OnTimeSetListener() {

		  // when dialog box is closed, below method will be called.
		  public void onTimeSet(TimePicker view, int selectedDay, int selectedMinute) {
			  hour = selectedDay;
			  minute = selectedMinute;
			  String set_date=year+"-"+(month+1)+"-"+day;
			  String set_time=hour+":"+minute;
			  list.get(current_changing).put("date",set_date);
			  list.get(current_changing).put("time",set_time);
			  adapter.notifyDataSetChanged();
			  
		  }
	  };
	  
} 



