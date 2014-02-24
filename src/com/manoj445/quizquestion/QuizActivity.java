package com.manoj445.quizquestion;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;

import com.manoj445.quizquestion.R;
import com.manoj445.quizquestion.generators.QuizObject;

public class QuizActivity extends ActionBarActivity {
	
	int quizType;
	int selectedNumbers[];
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		QuizObject quizObject= (QuizObject)getIntent().getSerializableExtra("questionnaire");
		quizType=quizObject.getQuizType();
		selectedNumbers=quizObject.getNumbersIncludedInQuiz();
		 getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		//set
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public Intent getSupportParentActivityIntent() {
		Intent intent=null;
		// TODO Auto-generated method stub
		if (quizType==QuizObject.QUIZ_TYPE_RANDOM) {
			intent=new Intent(this, QuizSelectNumbersActivity.class);
		}
		if (quizType==QuizObject.QUIZ_TYPE_LEARNING) {
			
			if ((selectedNumbers!=null)&&(selectedNumbers.length>0)) {
				intent=new Intent(this, TimesTableOfANumber.class);
				intent.putExtra("number", new Integer(selectedNumbers[0]));
				
			}
			else {
				intent=new Intent(this, SelectNumberToLearn.class);
				
			}
			
		}
		else{
			intent=new Intent(this,MainActivity.class);
		}
		return intent;
	}
	
	

}
