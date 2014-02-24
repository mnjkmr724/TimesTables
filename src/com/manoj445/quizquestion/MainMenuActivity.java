package com.manoj445.quizquestion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainMenuActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		 try {
			 AppRater.app_launched(this);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void userChoice(View view) {
		Intent intent=null;
		int viewId = view.getId();
		int selectedNumber = -1;
		switch (viewId) {
		case R.id.learn:
			
			intent=new Intent(this,SelectNumberToLearn.class );
			
			break;
		case R.id.revision_quiz:
			intent=new Intent(this,MainActivity.class );
			break;
		case R.id.random_quiz:
			intent=new Intent(this,QuizSelectNumbersActivity.class);
			break;
		default:
			break;
		}
		startActivity(intent);
	}
}
