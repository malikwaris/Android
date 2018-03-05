package org.sferadev.meetings.timer;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.fima.cardsui.objects.CardStack;
import com.fima.cardsui.views.CardUI;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;

//TODO: Graphs
//TODO: Preferences

public class MainActivity extends Activity {

	//CardUI
	private CardUI mCardMain;
	
	//Replies
	static ArrayList<String> aReplies = new ArrayList<String>();
	
	//Timer
	Context context;
	SharedPreferences preferences;
	
	// Settings
	SharedPreferences preferences_settings;
	int settings_mic_replies;
	
	//State of the timers
	static int bBibleStudy = 0;
	static int bReading = 0;
	static int bN1 = 0;
	static int bN2 = 0;
	static int bN3 = 0;
	static int bPart1 = 0;
	static int bPart2 = 0;
	static int bPart3 = 0;
	static int bPart4 = 0;
	
	//Original value of Timer
	static Long oBibleStudy;
	static Long oReading;
	static Long oN1;
	static Long oN2;
	static Long oN3;
	static Long oPart1;
	static Long oPart2;
	static Long oPart3;
	static Long oPart4;
	
	//Last Timer value
	static String lBibleStudy = "justStarted";
	static String lReading = "justStarted";
	static String lN1 = "justStarted";
	static String lN2 = "justStarted";
	static String lN3 = "justStarted";
	static String lPart1 = "justStarted";
	static String lPart2 = "justStarted";
	static String lPart3 = "justStarted";
	static String lPart4 = "justStarted";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restoreLastTimers();
		setContentView(R.layout.activity_main);
		
		context = MainActivity.this;
		preferences = context.getSharedPreferences("Timer", Context.MODE_PRIVATE);
		preferences_settings  = PreferenceManager.getDefaultSharedPreferences(this);
        checkPreferences();
        
		// init CardView
		mCardMain = (CardUI) findViewById(R.id.cardsview);
		
		MyBaseCard cWelcome = new MyBaseCard(getString(R.string.welcome), "Timer is back!", "#33B5E5", "#0099CC", false, false);
		mCardMain.addCard(cWelcome);
		
		MyBaseCard cFeedBack = new MyBaseCard("What's to come?", "Graphs and advanced preferences are on the works!", "#33B5E5", "#0099CC", false, false);
		cFeedBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String url = "http://goo.gl/ra3uo7";
	            Intent i = new Intent(Intent.ACTION_VIEW);
	            i.setData(Uri.parse(url));
	            startActivity(i);
			}
		});
		mCardMain.addCard(cFeedBack);
		
		CardStack stackBibleStudy = new CardStack();
		stackBibleStudy.setTitle(getString(R.string.eb));
		mCardMain.addStack(stackBibleStudy);
		
		final MyBibleStudyCard cBibleStudy = new MyBibleStudyCard(getString(R.string.eb_short), getString(R.string.learn), "#FF4444", "#CC0000", false, false);
		cBibleStudy.setOnClickListener(new OnClickListener() {
			Timer timer;
			TimerTask task;
			@Override
			public void onClick(View v) {
				if(bBibleStudy == 0) {
					//Opt-out next time
					bBibleStudy = 1;
					//Set Start time in millis
					setTimer("sBibleStudy", "none");
					oBibleStudy = getTimer("sBibleStudy");
					//Clear last time
					lBibleStudy = null;
					//Update Timer
					task = new TimerTask() {
						public void run() {
							cBibleStudy.v.post(new Runnable() {
						        public void run() {
						        	Long elapsed = (System.currentTimeMillis() - getTimer("sBibleStudy"));
							    	String time = String.valueOf(DateFormat.format("mm:ss", elapsed));
							    	cBibleStudy.chrono.setText(time);
						        }
						    });
			                }
					}; 
			        timer = new Timer();
			        timer.schedule(task, 1000, 1);
				}
				else {
					//Opt-out next time
					bBibleStudy = 0;
					//Calc timing
					Long elapsed = (System.currentTimeMillis() - getTimer("sBibleStudy"));
					String time = String.valueOf(DateFormat.format("mm:ss", elapsed));
					//Set end time
					lBibleStudy = time;
					setPref("fBibleStudy", time, "none", "timing");
					//Update Timer
					if (timer != null){
						timer.cancel();
					}
					cBibleStudy.chrono.setText(time);
					//Clear Start time
					oBibleStudy = null;
				}
			}
		});
		mCardMain.addCard(cBibleStudy);
		
		CardStack stackSchool = new CardStack();
		stackSchool.setTitle(getString(R.string.tms));
		mCardMain.addStack(stackSchool);
		
		final MyReadingCard cReading = new MyReadingCard(getString(R.string.reading), getString(R.string.learn2), "#FFBB33", "#FF8800", false, false);
		cReading.setOnClickListener(new OnClickListener() {
			Timer timer;
			TimerTask task;
			@Override
			public void onClick(View v) {
				if(bReading == 0) {
					//Opt-out next time
					bReading = 1;
					//Set Start time in millis
					setTimer("sReading", "none");
					oReading = getTimer("sReading");
					//Clear last time
					lReading = null;
					//Update Timer
					task = new TimerTask() {
						public void run() {
							cReading.v.post(new Runnable() {
						        public void run() {
						        	Long elapsed = (System.currentTimeMillis() - getTimer("sReading"));
							    	String time = String.valueOf(DateFormat.format("mm:ss", elapsed));
							    	cReading.chrono.setText(time);
						        }
						    });
			                }
					}; 
			        timer = new Timer();
			        timer.schedule(task, 1000, 1);
				}
				else {
					//Opt-out next time
					bReading = 0;
					//Calc timing
					Long elapsed = (System.currentTimeMillis() - getTimer("sReading"));
					String time = String.valueOf(DateFormat.format("mm:ss", elapsed));
					//Set end time
					lReading = time;
					setPref("fReading", time, "none", "timing");
					//Update Timer
					if (timer != null){
						timer.cancel();
					}
					cReading.chrono.setText(time);
					//Clear Start time
					oReading = null;
				}
			}
		});
		cReading.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				if (bReading == 1){
					//TODO Time calc is weird
					Long elapsed = (System.currentTimeMillis() - getTimer("sReading"));
					aReplies.add(String.valueOf(elapsed));
					cReading.replies.setText("");
					int arraySize = aReplies.size();
					for(int i = 0; i < arraySize; i++) {
						if (i == 0) {
							String time = String.valueOf(DateFormat.format("mm:ss", Long.parseLong(aReplies.get(0))));
							cReading.replies.append(getString(R.string.reading_puntos) + ": " + time + "\n");
						}
						else {
							Long timing = (Long.parseLong(aReplies.get(i)) - Long.parseLong(aReplies.get(i-1)));
							String time = String.valueOf(DateFormat.format("mm:ss", timing));
							cReading.replies.append(getString(R.string.reading_reply) + " " + i + ": " + time + "\n");
						}
						setPrefInt("fReading_replies", i, "none", "timing");
					}
				}
				return true;
			}
		});
		
		mCardMain.addCard(cReading);
		
		final MyN1Card cN1 = new MyN1Card(getString(R.string.n1), getString(R.string.learn), "#FFBB33", "#FF8800", false, false);
		cN1.setOnClickListener(new OnClickListener() {
			Timer timer;
			TimerTask task;
			@Override
			public void onClick(View v) {
				if(bN1 == 0) {
					//Opt-out next time
					bN1 = 1;
					//Set Start time in millis
					setTimer("sN1", "none");
					oN1 = getTimer("sN1");
					//Clear last time
					lN1 = null;
					//Update Timer
					task = new TimerTask() {
						public void run() {
							cN1.v.post(new Runnable() {
						        public void run() {
						        	Long elapsed = (System.currentTimeMillis() - getTimer("sN1"));
							    	String time = String.valueOf(DateFormat.format("mm:ss", elapsed));
							    	cN1.chrono.setText(time);
						        }
						    });
			                }
					}; 
			        timer = new Timer();
			        timer.schedule(task, 1000, 1);
				}
				else {
					//Opt-out next time
					bN1 = 0;
					//Calc timing
					Long elapsed = (System.currentTimeMillis() - getTimer("sN1"));
					String time = String.valueOf(DateFormat.format("mm:ss", elapsed));
					//Set end time
					lN1 = time;
					setPref("fN1", time, "none", "timing");
					//Update Timer
					if (timer != null){
						timer.cancel();
					}
					cN1.chrono.setText(time);
					//Clear Start time
					oN1 = null;
				}
			}
		});
		mCardMain.addCard(cN1);
		
		final MyN2Card cN2 = new MyN2Card(getString(R.string.n2), getString(R.string.learn), "#FFBB33", "#FF8800", false, false);
		cN2.setOnClickListener(new OnClickListener() {
			Timer timer;
			TimerTask task;
			@Override
			public void onClick(View v) {
				if(bN2 == 0) {
					//Opt-out next time
					bN2 = 1;
					//Set Start time in millis
					setTimer("sN2", "none");
					oN2 = getTimer("sN2");
					//Clear last time
					lN2 = null;
					//Update Timer
					task = new TimerTask() {
						public void run() {
							cN2.v.post(new Runnable() {
						        public void run() {
						        	Long elapsed = (System.currentTimeMillis() - getTimer("sN2"));
							    	String time = String.valueOf(DateFormat.format("mm:ss", elapsed));
							    	cN2.chrono.setText(time);
						        }
						    });
			                }
					}; 
			        timer = new Timer();
			        timer.schedule(task, 1000, 1);
				}
				else {
					//Opt-out next time
					bN2 = 0;
					//Calc timing
					Long elapsed = (System.currentTimeMillis() - getTimer("sN2"));
					String time = String.valueOf(DateFormat.format("mm:ss", elapsed));
					//Set end time
					lN2 = time;
					setPref("fN2", time, "none", "timing");
					//Update Timer
					if (timer != null){
						timer.cancel();
					}
					cN2.chrono.setText(time);
					//Clear Start time
					oN2 = null;
				}
			}
		});
		mCardMain.addCard(cN2);
		
		final MyN3Card cN3 = new MyN3Card(getString(R.string.n3), getString(R.string.learn), "#FFBB33", "#FF8800", false, false);
		cN3.setOnClickListener(new OnClickListener() {
			Timer timer;
			TimerTask task;
			@Override
			public void onClick(View v) {
				if(bN3 == 0) {
					//Opt-out next time
					bN3 = 1;
					//Set Start time in millis
					setTimer("sN3", "none");
					oN3 = getTimer("sN3");
					//Clear last time
					lN3 = null;
					//Update Timer
					task = new TimerTask() {
						public void run() {
							cN3.v.post(new Runnable() {
						        public void run() {
						        	Long elapsed = (System.currentTimeMillis() - getTimer("sN3"));
							    	String time = String.valueOf(DateFormat.format("mm:ss", elapsed));
							    	cN3.chrono.setText(time);
						        }
						    });
			                }
					}; 
			        timer = new Timer();
			        timer.schedule(task, 1000, 1);
				}
				else {
					//Opt-out next time
					bN3 = 0;
					//Calc timing
					Long elapsed = (System.currentTimeMillis() - getTimer("sN3"));
					String time = String.valueOf(DateFormat.format("mm:ss", elapsed));
					//Set end time
					lN3 = time;
					setPref("fN3", time, "none", "timing");
					//Update Timer
					if (timer != null){
						timer.cancel();
					}
					cN3.chrono.setText(time);
					//Clear Start time
					oN3 = null;
				}
			}
		});
		mCardMain.addCard(cN3);
		
		CardStack stackService = new CardStack();
		stackService.setTitle(getString(R.string.rs));
		mCardMain.addStack(stackService);
		
		final MyPart1Card cPart1 = new MyPart1Card(getString(R.string.rs1), getString(R.string.learn), "#AA66CC", "#9933CC", false, false);
		cPart1.setOnClickListener(new OnClickListener() {
			Timer timer;
			TimerTask task;
			@Override
			public void onClick(View v) {
				if(bPart1 == 0) {
					//Opt-out next time
					bPart1 = 1;
					//Set Start time in millis
					setTimer("sPart1", "none");
					oPart1 = getTimer("sPart1");
					//Clear last time
					lPart1 = null;
					//Update Timer
					task = new TimerTask() {
						public void run() {
							cPart1.v.post(new Runnable() {
						        public void run() {
						        	Long elapsed = (System.currentTimeMillis() - getTimer("sPart1"));
							    	String time = String.valueOf(DateFormat.format("mm:ss", elapsed));
							    	cPart1.chrono.setText(time);
						        }
						    });
			                }
					}; 
			        timer = new Timer();
			        timer.schedule(task, 1000, 1);
				}
				else {
					//Opt-out next time
					bPart1 = 0;
					//Calc timing
					Long elapsed = (System.currentTimeMillis() - getTimer("sPart1"));
					String time = String.valueOf(DateFormat.format("mm:ss", elapsed));
					//Set end time
					lPart1 = time;
					setPref("fPart1", time, "none", "timing");
					//Update Timer
					if (timer != null){
						timer.cancel();
					}
					cPart1.chrono.setText(time);
					//Clear Start time
					oPart1 = null;
				}
			}
		});
		mCardMain.addCard(cPart1);
		
		final MyPart2Card cPart2 = new MyPart2Card(getString(R.string.rs2), getString(R.string.learn), "#AA66CC", "#9933CC", false, false);
		cPart2.setOnClickListener(new OnClickListener() {
			Timer timer;
			TimerTask task;
			@Override
			public void onClick(View v) {
				if(bPart2 == 0) {
					//Opt-out next time
					bPart2 = 1;
					//Set Start time in millis
					setTimer("sPart2", "none");
					oPart2 = getTimer("sPart2");
					//Clear last time
					lPart2 = null;
					//Update Timer
					task = new TimerTask() {
						public void run() {
							cPart2.v.post(new Runnable() {
						        public void run() {
						        	Long elapsed = (System.currentTimeMillis() - getTimer("sPart2"));
							    	String time = String.valueOf(DateFormat.format("mm:ss", elapsed));
							    	cPart2.chrono.setText(time);
						        }
						    });
			                }
					}; 
			        timer = new Timer();
			        timer.schedule(task, 1000, 1);
				}
				else {
					//Opt-out next time
					bPart2 = 0;
					//Calc timing
					Long elapsed = (System.currentTimeMillis() - getTimer("sPart2"));
					String time = String.valueOf(DateFormat.format("mm:ss", elapsed));
					//Set end time
					lPart2 = time;
					setPref("fPart2", time, "none", "timing");
					//Update Timer
					if (timer != null){
						timer.cancel();
					}
					cPart2.chrono.setText(time);
					//Clear Start time
					oPart2 = null;
				}
			}
		});
		mCardMain.addCard(cPart2);
		
		final MyPart3Card cPart3 = new MyPart3Card(getString(R.string.rs3), getString(R.string.learn), "#AA66CC", "#9933CC", false, false);
		cPart3.setOnClickListener(new OnClickListener() {
			Timer timer;
			TimerTask task;
			@Override
			public void onClick(View v) {
				if(bPart3 == 0) {
					//Opt-out next time
					bPart3 = 1;
					//Set Start time in millis
					setTimer("sPart3", "none");
					oPart3 = getTimer("sPart3");
					//Clear last time
					lPart3 = null;
					//Update Timer
					task = new TimerTask() {
						public void run() {
							cPart3.v.post(new Runnable() {
						        public void run() {
						        	Long elapsed = (System.currentTimeMillis() - getTimer("sPart3"));
							    	String time = String.valueOf(DateFormat.format("mm:ss", elapsed));
							    	cPart3.chrono.setText(time);
						        }
						    });
			                }
					}; 
			        timer = new Timer();
			        timer.schedule(task, 1000, 1);
				}
				else {
					//Opt-out next time
					bPart3 = 0;
					//Calc timing
					Long elapsed = (System.currentTimeMillis() - getTimer("sPart3"));
					String time = String.valueOf(DateFormat.format("mm:ss", elapsed));
					//Set end time
					lPart3 = time;
					setPref("fPart3", time, "none", "timing");
					//Update Timer
					if (timer != null){
						timer.cancel();
					}
					cPart3.chrono.setText(time);
					//Clear Start time
					oPart3 = null;
				}
			}
		});
		mCardMain.addCard(cPart3);
		
		final MyPart4Card cPart4 = new MyPart4Card(getString(R.string.rs4), getString(R.string.learn), "#AA66CC", "#9933CC", false, false);
		cPart4.setOnClickListener(new OnClickListener() {
			Timer timer;
			TimerTask task;
			@Override
			public void onClick(View v) {
				if(bPart4 == 0) {
					//Opt-out next time
					bPart4 = 1;
					//Set Start time in millis
					setTimer("sPart4", "none");
					oPart4 = getTimer("sPart4");
					//Clear last time
					lPart4 = null;
					//Update Timer
					task = new TimerTask() {
						public void run() {
							cPart4.v.post(new Runnable() {
						        public void run() {
						        	Long elapsed = (System.currentTimeMillis() - getTimer("sPart4"));
							    	String time = String.valueOf(DateFormat.format("mm:ss", elapsed));
							    	cPart4.chrono.setText(time);
						        }
						    });
			                }
					}; 
			        timer = new Timer();
			        timer.schedule(task, 1000, 1);
				}
				else {
					//Opt-out next time
					bPart4 = 0;
					//Calc timing
					Long elapsed = (System.currentTimeMillis() - getTimer("sPart4"));
					String time = String.valueOf(DateFormat.format("mm:ss", elapsed));
					//Set end time
					lPart4 = time;
					setPref("fPart4", time, "none", "timing");
					//Update Timer
					if (timer != null){
						timer.cancel();
					}
					cPart4.chrono.setText(time);
					//Clear Start time
					oPart4 = null;
				}
			}
		});
		mCardMain.addCard(cPart4);
		
		mCardMain.refresh();
    }
    
    public void setTimer(String code, String action){
    	if (action == "none"){
    		Editor editor = preferences.edit();
    		editor.putLong(code, System.currentTimeMillis());
    		editor.apply(); 
    	}
    	else {
    		Editor editor = preferences.edit();
    		editor.putLong(code, 0);
    		editor.apply(); 
    	}
    }
    
    public Long getTimer(String code){
    	
    	return preferences.getLong(code, 0);
    	
    }
    
    public String getPref(String code, String where){
    	String value = null;
    	if (where == "timing"){
    		value = preferences.getString(code, null);
    	}
    	if (where == "settings"){
    		value = preferences_settings.getString(code, null);
    	}
		return value;
    }

    public void setPref(String code, String value, String action, String where){
    	Editor editor = null;
    	if (where == "timing"){
    		editor = preferences.edit();
    	}
    	if (where == "settings"){
    		editor = preferences_settings.edit();
    	}
    	if (action == "reset"){
    		editor.putString(code, null);
    		editor.apply(); 
    	}
    	else {
    		editor.putString(code, value);
    		editor.apply(); 
    	}
    }

    public int getPrefInt(String code, String where){
    	int value = 0;
    	if (where == "timing"){
    		value = preferences.getInt(code, 0);
    	}
    	if (where == "settings"){
    		value = preferences_settings.getInt(code, 0);
    	}
		return value;
    }

    public void setPrefInt(String code, int value, String action, String where){
    	Editor editor = null;
    	if (where == "timing"){
    		editor = preferences.edit();
    	}
    	if (where == "settings"){
    		editor = preferences_settings.edit();
    	}
    	if (action == "reset"){
    		editor.putInt(code, 0);
    		editor.apply(); 
    	}
    	else {
    		editor.putInt(code, value);
    		editor.apply(); 
    	}
    }

    public void restoreLastTimers() {
    	if(oBibleStudy != null){
    		bBibleStudy = 1;
    		lBibleStudy = "justStarted";
    	}
    	if(oReading != null){
    		bReading = 1;
    		lReading = "justStarted";
    	}
    	if(oN1 != null){
    		bN1 = 1;
    		lN1 = "justStarted";
    	}
    	if(oN2 != null){
    		bN2 = 1;
    		lN2 = "justStarted";
    	}
    	if(oN3 != null){
    		bN3 = 1;
    		lN3 = "justStarted";
    	}
    	if(oPart1 != null){
    		bPart1 = 1;
    		lPart1 = "justStarted";
    	}
    	if(oPart2 != null){
    		bPart2 = 1;
    		lPart2 = "justStarted";
    	}
    	if(oPart3 != null){
    		bPart3 = 1;
    		lPart3 = "justStarted";
    	}
    	if(oPart4 != null){
    		bPart4 = 1;
    		lPart4 = "justStarted";
    	}
    }

    public void checkPreferences(){
    	//TODO
    	settings_mic_replies = Integer.parseInt(getPref("key_mic_replies", "settings"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_share) {
        	Intent i = new Intent(android.content.Intent.ACTION_SEND);
       		i.setType("text/plain");
       		i.putExtra(android.content.Intent.EXTRA_TEXT, "" +
       				getString(R.string.eb) + " " + getPref("fBibleStudy", "timing") + "\n" +
       				getString(R.string.reading) + " " + getPref("fReading", "timing") + "\n" +
       				getString(R.string.share_replies) + " " + getPrefInt("fReading_replies", "timing") + "\n" +
       				getString(R.string.n1) + " " + getPref("fN1", "timing") + "\n" +
       				getString(R.string.n2) + " " + getPref("fN2", "timing") + "\n" +
       				getString(R.string.n3) + " " + getPref("fN3", "timing") + "\n" +
       				getString(R.string.rs1) + " " + getPref("fPart1", "timing") + "\n" +
       				getString(R.string.rs2) + " " + getPref("fPart2", "timing") + "\n" +
       				getString(R.string.rs3) + " " + getPref("fPart3", "timing") + "\n" +
       				getString(R.string.rs4) + " " + getPref("fPart4", "timing") + "\n");
       		startActivity(Intent.createChooser(i,"Share via"));
            return true;
        }
        if (id == R.id.action_donate) {
        	AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.about_donations)
            	.setPositiveButton(R.string.menu_donate, new DialogInterface.OnClickListener() {
            		public void onClick(DialogInterface dialog, int id) {
            			String url = "http://forum.xda-developers.com/donatetome.php?u=4710474";
            	        Intent i = new Intent(Intent.ACTION_VIEW);
            	        i.setData(Uri.parse(url));
            	        startActivity(i);
            		}
            	});
            builder.show();
        }
        if (id == R.id.action_translate) {
        	String url = "http://osxmo04.oneskyapp.com/collaboration/project?id=9378";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        }
        if (id == R.id.action_feedback) {
        	String url = "http://goo.gl/ra3uo7";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        }
        if (id == R.id.action_settings) {
        	Intent i = new Intent(this, SettingsActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

}
