package com.manoj445.quizquestion;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.manoj445.quizquestion.generators.QuizObject;

public class MainActivity extends ActionBarActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.learn_main);
		 getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
	}

	public void userChoice(View view) {
		int viewId=view.getId();
		int selectedNumber=-1;
		switch (viewId) {
		case R.id.number_1:
			selectedNumber=1;
			break;
		case R.id.number_2:
			selectedNumber=2;
			break;
		case R.id.number_3:
			selectedNumber=3;
			break;
		case R.id.number_4:
			selectedNumber=4;
			break;
		case R.id.number_5:
			selectedNumber=5;
			break;
		case R.id.number_6:
			selectedNumber=6;
			break;
		case R.id.number_7:
			selectedNumber=7;
			break;
		case R.id.number_8:
			selectedNumber=8;
			break;
		case R.id.number_9:
			selectedNumber=9;
			break;
		case R.id.number_10:
			selectedNumber=10;
			break;
		case R.id.number_11:
			selectedNumber=11;
			break;
		case R.id.number_12:
			selectedNumber=12;
			break;
		
		default:
			break;
		}
		
		int multiplier=5;
		
		QuizObject quizObject=new QuizObject(selectedNumber);
		quizObject.setCurrentIndex(0);
		quizObject.generateQuestionnaire();
		Intent intent=new Intent(this, QuizActivity.class);
		intent.putExtra("questionnaire", quizObject);
		startActivity(intent);
		
	}
}
