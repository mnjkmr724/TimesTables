package com.manoj445.quizquestion;


import com.manoj445.quizquestion.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Toast;

public class SelectNumberToLearn extends ActionBarActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_number_to_learn);
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
		
	
		
		Intent intent=new Intent(this, TimesTableOfANumber.class);
		intent.putExtra("number", new Integer(selectedNumber));
		startActivity(intent);
		
	}
	


//
}
