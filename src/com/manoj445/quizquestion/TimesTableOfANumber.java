package com.manoj445.quizquestion;


import java.util.HashMap;
import java.util.Locale;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;
import android.widget.Toast;

import com.manoj445.quizquestion.R;
import com.manoj445.quizquestion.generators.QuizObject;

public class TimesTableOfANumber extends ActionBarActivity implements TextToSpeech.OnInitListener, OnTouchListener {
    
	boolean showToast=false;
	private TextToSpeech tts;
	int num;
	HashMap<String, String> numberMap;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		//initialize number map
		
		String[] numbers=new String[]{"one","two","three","four","five","six","seven","eight","nine","ten","eleven","twelve"};
		numberMap=new HashMap<String, String>(12);
		for (int i = 1; i <=12; i++) {
			String key=i+"";
			String value=numbers[i-1]+"s";
			numberMap.put(key, value);
		}
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_times_table_of_anumber);
		Integer number= (Integer)getIntent().getSerializableExtra("number");
		num=number.intValue();
		tts = new TextToSpeech(this, this);
		tts.setSpeechRate(0.8f);
		
		TextView tView1=(TextView)findViewById(R.id.number_1);
		tView1.setText(num+"  x 1 =  "+(num*1));
		tView1.setTag("one "+num+" is "+num);
		tView1.setOnTouchListener(this);
	
		TextView tView2=(TextView)findViewById(R.id.number_2);
		tView2.setText(num+"  x 2 = "+(num*2));
		tView2.setTag(num+" "+numberMap.get("2")+" are "+(num*2));
		tView2.setOnTouchListener(this);
		
		TextView tView3=(TextView)findViewById(R.id.number_3);
		tView3.setText(num+"  x 3 = "+(num*3));
		tView3.setTag(num+" "+numberMap.get("3")+" are "+(num*3));
		tView3.setOnTouchListener(this);
		
		TextView tView4=(TextView)findViewById(R.id.number_4);
		tView4.setText(num+"  x 4 = "+(num*4));
		tView4.setTag(num+" "+numberMap.get("4")+" are "+(num*4));
		tView4.setOnTouchListener(this);
		
		TextView tView5=(TextView)findViewById(R.id.number_5);
		tView5.setText(num+"  x 5 = "+(num*5));
		tView5.setTag(num+" "+numberMap.get("5")+" are "+(num*5));
		tView5.setOnTouchListener(this);
		
		TextView tView6=(TextView)findViewById(R.id.number_6);
		tView6.setText(num+"  x 6 = "+(num*6));
		tView6.setTag(num+" "+numberMap.get("6")+" are "+(num*6));
		tView6.setOnTouchListener(this);
		
		
		TextView tView7=(TextView)findViewById(R.id.number_7);
		tView7.setText(num+"  x 7 = "+(num*7));
		tView7.setTag(num+" "+numberMap.get("7")+" are "+(num*7));
		tView7.setOnTouchListener(this);
		
		
		TextView tView8=(TextView)findViewById(R.id.number_8);
		tView8.setText(num+"  x 8 = "+(num*8));
		tView8.setTag(num+" "+numberMap.get("8")+" are "+(num*8));
		tView8.setOnTouchListener(this);
		
		TextView tView9=(TextView)findViewById(R.id.number_9);
		tView9.setText(num+"  x 9 = "+(num*9));
		tView9.setTag(num+" "+numberMap.get("9")+" are "+(num*9));
		tView9.setOnTouchListener(this);
		
		TextView tView10=(TextView)findViewById(R.id.number_10);
		tView10.setText(num+" x 10 = "+(num*10));
		tView10.setTag(num+" "+numberMap.get("10")+" are "+(num*10));
		tView10.setOnTouchListener(this);
		
	}

	@Override
	protected void onResume() {
		showToast=true;
		// TODO Auto-generated method stub
		super.onResume();
	}
	
	
	
	public void userChoice(View view) {
	
		Object o=view.getTag();
		if (o!=null) {
			speakOut(o+"");
		}
		
	}
	
	 @Override
	    public void onDestroy() {
	        // Don't forget to shutdown tts!
	        if (tts != null) {
	            tts.stop();
	            tts.shutdown();
	        }
	        super.onDestroy();
	    }
	 
	    @Override
	    public void onInit(int status) {
	 
	        if (status == TextToSpeech.SUCCESS) {
	        	
	        	           int result = tts.setLanguage(Locale.US);
	 
	            if (result == TextToSpeech.LANG_MISSING_DATA
	                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
	                //Log.e("TTS", "This Language is not supported");
	            } else {
	            	String voice_ready_message=getResources().getString(R.string.voice_ready_message);
	        		//hack to avoid showing the toast if the user has already moved on to another activity
	            	
	            	if (showToast) {
	            		//Toast.makeText(this,voice_ready_message, Toast.LENGTH_LONG).show();
	            		speakOut(voice_ready_message);
					}
	                //speakOut(voice_ready_message);
	            }
	 
	        } else {
	            //Log.e("TTS", "Initilization Failed!");
	        }
	 
	    }
	 
	    private void speakOut(String text) {
	 
	 
	        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
	    }



		@Override
		public boolean onTouch(View v, MotionEvent event) {

			Object o=v.getTag();
			if (o!=null) {
				speakOut(o+"");
			}
			else {
				speakOut("I am deeply touched");
			}
			// TODO Auto-generated method stub
			return true;
		}
		
		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
			getMenuInflater().inflate(R.menu.main, menu);
			MenuItem menuItem=menu.findItem(R.id.next_question);
			menuItem.setTitle("Revise this table");
			//menuItem.setVisible(false);
			return super.onCreateOptionsMenu(menu);
		}

@Override
public boolean onOptionsItemSelected(MenuItem item) {
	
	QuizObject quizObject=new QuizObject(num,QuizObject.QUIZ_TYPE_LEARNING);
	quizObject.setCurrentIndex(0);
	quizObject.generateQuestionnaire();
	
	Intent intent=new Intent(this, QuizActivity.class);
	intent.putExtra("questionnaire", quizObject);
	startActivity(intent);
	
	// TODO Auto-generated method stub
	return super.onOptionsItemSelected(item);
}
@Override
protected void onPause() {
	showToast=false;
	if (tts != null) {
	tts.stop();
			
    }// TODO Auto-generated method stub
	super.onPause();
}

@Override
protected void onStop() {
	 if (tts != null) {
				tts.stop();
     }
	
	// TODO Auto-generated method stub
	super.onStop();
}
}
