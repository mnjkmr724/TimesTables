package com.manoj445.quizquestion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ToggleButton;
import android.widget.Toast;

import com.manoj445.quizquestion.R;
import com.manoj445.quizquestion.generators.QuizObject;

public class QuizSelectNumbersActivity extends ActionBarActivity {
    
	int counter=0;
	Menu menu;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quiz_select_numbers_screen);
		 getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		//set
		
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		this.menu=menu;
		MenuItem menuItem=menu.findItem(R.id.next_question);
		menuItem.setTitle("start quiz");
		menuItem.setVisible(false);
		return super.onCreateOptionsMenu(menu);
	}

	public void userChoice(View v){
		ToggleButton cBox=(ToggleButton)v;
		MenuItem menuItem=menu.findItem(R.id.next_question);
		//menuItem.setVisible(true);
		
		if (cBox.isChecked()) {
		//cBox.setBackgroundDrawable(getResources().getDrawable(
			//	R.drawable.btn_green));
				counter++;
			
		}
		else{
			//cBox.setBackgroundDrawable(getResources().getDrawable(
				//	R.drawable.btn_blue));	
			counter--;
		}
		
		if(counter>0){
			
			if (!menuItem.isVisible()) {
				menuItem.setVisible(true);
			}
			
		}
		

		if(counter<=0){
		
			if (menuItem.isVisible()) {
				menuItem.setVisible(false);
			}
			
		}
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		
		// in this case the user has navigated up
		
		if (item.getItemId()!=R.id.next_question) {
			return false;
			
		}
		
		// get all ToggleButtones
		
		ArrayList<Integer> numbersInQuiz=new ArrayList(12);
		
		ToggleButton cb1=(ToggleButton)findViewById(R.id.number_1);
		ToggleButton cb2=(ToggleButton)findViewById(R.id.number_2);
		ToggleButton cb3=(ToggleButton)findViewById(R.id.number_3);
		ToggleButton cb4=(ToggleButton)findViewById(R.id.number_4);
		ToggleButton cb5=(ToggleButton)findViewById(R.id.number_5);
		ToggleButton cb6=(ToggleButton)findViewById(R.id.number_6);
		ToggleButton cb7=(ToggleButton)findViewById(R.id.number_7);
		ToggleButton cb8=(ToggleButton)findViewById(R.id.number_8);
		ToggleButton cb9=(ToggleButton)findViewById(R.id.number_9);
		ToggleButton cb10=(ToggleButton)findViewById(R.id.number_10);
		ToggleButton cb11=(ToggleButton)findViewById(R.id.number_11);
		ToggleButton cb12=(ToggleButton)findViewById(R.id.number_12);
		
		if (cb1.isChecked()) {
			numbersInQuiz.add(new Integer(1));
		}
		if (cb2.isChecked()) {
			numbersInQuiz.add(new Integer(2));
		}
		if (cb3.isChecked()) {
			numbersInQuiz.add(new Integer(3));
		}
		if (cb4.isChecked()) {
			numbersInQuiz.add(new Integer(4));
		}
		if (cb5.isChecked()) {
			numbersInQuiz.add(new Integer(5));
		}
		if (cb6.isChecked()) {
			numbersInQuiz.add(new Integer(6));
		}
		if (cb7.isChecked()) {
			numbersInQuiz.add(new Integer(7));
		}
		if (cb8.isChecked()) {
			numbersInQuiz.add(new Integer(8));
		}
		if (cb9.isChecked()) {
			numbersInQuiz.add(new Integer(9));
		}
		if (cb10.isChecked()) {
			numbersInQuiz.add(new Integer(10));
		}
		if (cb11.isChecked()) {
			numbersInQuiz.add(new Integer(11));
		}
		if (cb12.isChecked()) {
			numbersInQuiz.add(new Integer(12));
		}
		
		int[] numbers_in_quiz=convertIntegers(numbersInQuiz);
		
		QuizObject quizObject=new QuizObject(numbers_in_quiz);
		quizObject.setCurrentIndex(0);
		quizObject.generateQuestionnaire();
		Intent intent=new Intent(this, QuizActivity.class);
		intent.putExtra("questionnaire", quizObject);
		startActivity(intent);
		
		 
		String quizText="You will get 10 random questions based on numbers ";
		
		//for (int i = 0; i < numbers_in_quiz.length; i++) {
			
		//}
		
		
		return true;
	}
	
	//helper method
	public static int[] convertIntegers(List<Integer> integers)
	{
	    int[] ret = new int[integers.size()];
	    for (int i=0; i < ret.length; i++)
	    {
	        ret[i] = integers.get(i).intValue();
	    }
	    return ret;
	}
	
	//hack to set the correct background if another activity is started user comes back to this one
	//... as android remembers the checked status, but not color
}
