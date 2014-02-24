package com.manoj445.quizquestion;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.manoj445.quizquestion.R;
import com.manoj445.quizquestion.generators.QuizObject;
import com.manoj445.quizquestion.generators.QuizQuestionObject;

public class QuizResultActivity extends ActionBarActivity implements View.OnClickListener {
	
	int quizType;
	float score;
	float numberOfQuestions;
	String scoreMessage="";
	String encouragementMessage="";
	int numberOfStars=0;
	int percentScore;
	QuizObject quizObject;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz_result);
		quizObject= (QuizObject)getIntent().getSerializableExtra("questionnaire");
		quizType=quizObject.getQuizType();
		score=quizObject.getCurrentScore();
		numberOfQuestions=quizObject.getNumberOfQuestions();
		
		float percentScore1=100*(score/numberOfQuestions);
		percentScore=(int)percentScore1;
		scoreMessage="<ul></li>You scored ("+(int)score+"/"+(int)numberOfQuestions+") that is <b>"+percentScore+"%</b></li><br>";
		if (percentScore==100) {
			numberOfStars=3;
			encouragementMessage="<li>Excellent! Keep it up!</li><br>";
		}
		else if (percentScore>=80) {
			numberOfStars=2;
			encouragementMessage="<li>Very good! you scored well.</li><br>";
		} 
		
		else if (percentScore>=50) {
			numberOfStars=1;
			encouragementMessage="<li>Well tried! practice some more.</li><br>";
		}
		
		else{
			numberOfStars=0;
			encouragementMessage="<li>Better luck for next time! practice a lot more.</li><br>";
		}
		
		//TextView  scoreView= (TextView) findViewById(R.id.score);
		TextView  encView= (TextView) findViewById(R.id.encouragement);
		
		String star_msg="";
		
		if (numberOfStars>0) {
			
			star_msg="You have been awarded "+numberOfStars+" star(s)!";
			
		}
		//scoreView.setText(scoreMessage);
		//encView.setText(scoreMessage+" "+encouragementMessage);
		encView.setText(Html.fromHtml(scoreMessage+" "+encouragementMessage+"<li>"+star_msg));
		//adding the stars
		   LinearLayout Parent = (LinearLayout) findViewById(R.id.stars);

		    View child =null;
		    //Log.e("KUMAR", "numberOfStars"+numberOfStars);
		    		switch (numberOfStars) {
		    		
		    		
		    		case 1:
						child=getLayoutInflater().inflate(R.layout.one_star,null);
						//Log.e("KUMAR", "inflated stars"+numberOfStars);
						break;

		    		case 2:
						child=getLayoutInflater().inflate(R.layout.two_stars,null);
						//Log.e("KUMAR", "inflated stars"+numberOfStars);
						break;

		    		case 3:
						child=getLayoutInflater().inflate(R.layout.three_stars,null);
						//Log.e("KUMAR", "inflated stars"+numberOfStars);
						break;

					default:
						break;
					}
		    		
		    if (child!=null) {
		    	Parent.addView(child);
			}
		    
		   showNextSuggestion(); 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		MenuItem menuItem=menu.findItem(R.id.next_question);
		menuItem.setTitle("Go to main menu");
		
		return true;
	}
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent=new Intent(this, MainMenuActivity.class);
		startActivity(intent);
		return true;
	}
	
	public void showNextSuggestion() {
		LinearLayout layout = (LinearLayout) findViewById(R.id.next_suggestion_container);
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
		        ViewGroup.LayoutParams.MATCH_PARENT,
	            ViewGroup.LayoutParams.WRAP_CONTENT);
		 layoutParams.setMargins(10, 10, 10, 10);
		
        
		//messages for a random Quiz result
		if(quizType==QuizObject.QUIZ_TYPE_RANDOM){
			
			//perfect result
			if(percentScore==100){
				// show 2 buttons Play again,Quiz Menu
				
				Button b1 = (Button)getLayoutInflater().inflate(R.layout.quiz_result_next_action_button, null);
				b1.setText("Play again");
			    
				b1.setId(R.id.play_again);
			    b1.setOnClickListener(this);
			    
			    Button b2 = (Button)getLayoutInflater().inflate(R.layout.quiz_result_next_action_button, null);
				b2.setId(R.id.quiz_menu);
				b2.setText("Quiz menu");
			    
				b2.setOnClickListener(this);
			    layout.addView(b1);       
			    layout.addView(b2);
			    
			}
			// some answers incorrect
			else{
				
				HashSet<Integer> incorrectAnswers=getIncorrectNumbers();
				Iterator<Integer> itr=incorrectAnswers.iterator();
				while (itr.hasNext()) {
					Integer num=itr.next();
					
					Button b1 = (Button)getLayoutInflater().inflate(R.layout.quiz_result_next_action_button, null);
					b1.setText("Learn table of "+num+" x");
					//b1.setId(R.id.revise_again);
				    b1.setId(R.id.incorrect_number);
				    b1.setOnClickListener(this);
				    b1.setTag(num);
				    layout.addView(b1);
				}
				
				//get incorrect answers
			}
		}
		
		//messages for a revision Quiz Result
		else if(quizType==QuizObject.QUIZ_TYPE_SPECIFIC_NUMBER||quizType==QuizObject.QUIZ_TYPE_LEARNING){
			//perfect result
			if(percentScore==100){
				Button b1 = (Button)getLayoutInflater().inflate(R.layout.quiz_result_next_action_button, null);
				b1.setText("Revise another table");
			    b1.setId(R.id.revise_menu);
			    b1.setOnClickListener(this);
			    
			    Button b2 = (Button)getLayoutInflater().inflate(R.layout.quiz_result_next_action_button, null);
			    b2.setText("Learn another table");
			    b2.setId(R.id.learn_menu);
			    b2.setOnClickListener(this);
			    
			    layout.addView(b1);       
			    layout.addView(b2);
			}
			// some answers incorrect
			else{
				
				/*Button b1 = (Button)getLayoutInflater().inflate(R.layout.quiz_result_next_action_button, null);
				b1.setId(R.id.revise_again);
				b1.setText("Revise again");
			    b1.setOnClickListener(this);
			    */
			    Button b2 = (Button)getLayoutInflater().inflate(R.layout.quiz_result_next_action_button, null);
				b2.setId(R.id.learn_again);
				b2.setText("Learn again");
			    b2.setOnClickListener(this);
			    
			    //layout.addView(b1);       
			    layout.addView(b2);
				
			}
		}
	}



	@Override
	public void onClick(View v) {
		Intent intent=null;
		
		int id=v.getId();
		switch(id){
		case R.id.play_again:
			
			quizObject.setCurrentIndex(0);
			quizObject.setCurrentScore(0);
			quizObject.generateQuestionnaire();
			intent=new Intent(this, QuizActivity.class);
			intent.putExtra("questionnaire", quizObject);
			
			break;

		case R.id.quiz_menu:
			
			intent=new Intent(this, QuizSelectNumbersActivity.class);
			
			break;	
			

		case R.id.learn_menu:
	
			intent=new Intent(this, SelectNumberToLearn.class);
			
			break;	
	
		case R.id.revise_menu:
			
			intent=new Intent(this, MainActivity.class);
			break;
			
		case R.id.learn_again:
			
			intent=new Intent(this, TimesTableOfANumber.class);
			intent.putExtra("number", new Integer(quizObject.getNumbersIncludedInQuiz()[0]));
			
			break;	

		case R.id.revise_again:

			quizObject.setCurrentIndex(0);
			quizObject.setCurrentScore(0);
			quizObject.generateQuestionnaire();
			intent=new Intent(this, QuizActivity.class);
			intent.putExtra("questionnaire", quizObject);
			;
	
			break;
			
		case R.id.incorrect_number:

			quizObject.setCurrentIndex(0);
			quizObject.setCurrentScore(0);
			quizObject.generateQuestionnaire();
			intent=new Intent(this, TimesTableOfANumber.class);
			Integer ix=(Integer)v.getTag();
			intent.putExtra("number",ix);
			;
	
			break;	
	
	default:
				break;
		
		}
		startActivity(intent);
		// TODO Auto-generated method stub
		
	}
	
	private HashSet<Integer> getIncorrectNumbers(){
		ArrayList<QuizQuestionObject> questionnaire=quizObject.getQuestionsArrayList();
		QuizQuestionObject q=null;
		HashSet<Integer> incorrectAnswers=new HashSet<Integer>(12);
				
		for (int i = 0; i < questionnaire.size(); i++) {
			q=questionnaire.get(i);
			int number=q.getNumber();
			int multiplier=q.getMultiplier();
			int correctAnswer=q.getCorrectAnswer();
			int userAnswer=q.getUserAnswer();
			// in case the user did not respond correctly
			if(correctAnswer!=userAnswer){
				incorrectAnswers.add(new Integer(number));
				
			}
		
		}
		return incorrectAnswers;
	}
}
