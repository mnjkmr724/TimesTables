package com.manoj445.quizquestion;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.manoj445.quizquestion.R;
import com.manoj445.quizquestion.generators.QuizObject;
import com.manoj445.quizquestion.generators.QuizQuestionObject;

public class QuizQuestionFragment extends Fragment implements
		View.OnClickListener {
	MediaPlayer mediaPlayer;
	Menu menu;
	private final static int ANSWER_CORRECT = -1;
	private final static int ANSWER_WRONG = -2;
	private final static int SHOW_NEXT = -3;
	private int currentQuestionNumber;
	private String currentScore;
	private boolean answered = false;
	boolean nextFadeIn = false;

	QuizQuestionObject questionObject;

	Button button1;
	Button button2;
	Button button3;
	Button button4;
	Button button5;
	
	QuizObject quizObject;

	TextView questionText;
	TextView questionNumber;
	TextView currentScoreText;

	Button[] buttons = new Button[4];

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.quiz_question_screen, container,
				false);

		questionText = (TextView) view.findViewById(R.id.quiz_question);
		questionNumber = (TextView) view.findViewById(R.id.question_number);
		currentScoreText = (TextView) view.findViewById(R.id.current_score);
		// currentScoreText.setT
		button1 = (Button) view.findViewById(R.id.answer1);
		button2 = (Button) view.findViewById(R.id.answer2);
		button3 = (Button) view.findViewById(R.id.answer3);
		button4 = (Button) view.findViewById(R.id.answer4);
		button5 = (Button) view.findViewById(R.id.next_question_button);

		buttons[0] = button1;
		buttons[1] = button2;
		buttons[2] = button3;
		buttons[3] = button4;

		button1.setOnClickListener(this);
		button2.setOnClickListener(this);
		button3.setOnClickListener(this);
		button4.setOnClickListener(this);
		button5.setOnClickListener(this);
		button5.setVisibility(View.INVISIBLE);
		// TODO Auto-generated method stub

		return view;
	}

	@Override
	public void onResume() {
		fadeIn(getActivity(), R.id.quiz_question, 1000);
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setHasOptionsMenu(true);
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		this.menu = menu;
		MenuItem mItem = menu.findItem(R.id.next_question);
		mItem.setVisible(false);
		
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		quizObject = (QuizObject) getActivity().getIntent()
				.getSerializableExtra("questionnaire");
		// generate the questionnaire

		currentQuestionNumber = quizObject.getCurrentIndex() + 1;
		questionObject = quizObject.getQuestionsArrayList().get(
				quizObject.getCurrentIndex());
		ArrayList<Integer> possibleAnswers = questionObject
				.getPossibleAnswers();

		// hardcoding for 4 answers
		for (int j = 0; j < 4; j++) {
			int option = possibleAnswers.get(j).intValue();
			buttons[j].setText(option + "");
			if (option == questionObject.getCorrectAnswer()) {
				buttons[j].setTag(R.id.is_correct_answer, new Integer(1));

			} else {
				buttons[j].setTag(R.id.is_correct_answer, new Integer(0));
			}
			buttons[j].setTag(R.id.option_value, new Integer(option));
		}

		questionText.setText(questionObject.getNumber() + " x "
				+ questionObject.getMultiplier() + " = ");
		questionNumber.setText(currentQuestionNumber + "");

		// bad hack to set the text size

		if (currentQuestionNumber == 10) {
			currentScoreText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		}
		currentScoreText.setText(quizObject.getCurrentScore() + "/"
				+ (currentQuestionNumber - 1));
		// quizObject.getNumberOfQuestions();
		// quizObject.getQuizType();

		/*
		 * String choices="";
		 * 
		 * for (int i = 0; i < questionnaire.size(); i++) { choices+="\n";
		 * QuizQuestionObject
		 * questionObject=(QuizQuestionObject)questionnaire.get(i); for (int j =
		 * 0; j < questionObject.getPossibleAnswers().size(); j++) { choices+=
		 * questionObject.getPossibleAnswers().get(j)+"     |    "; }
		 * 
		 * }
		 */

		// TODO Auto-generated method stub
		// ActionBarActivity aBarActivity=(ActionBarActivity)getActivity();
		// aBarActivity.getSupportActionBar().hide();
		super.onActivityCreated(savedInstanceState);
	}

	public void onClick(View v) {
		
		//if nextquestion is clicked... hack
		if (v.getId()==R.id.next_question_button) {
			showNextQuestion(v);
			return;
			
		}
		// handling repeat answers

		if (answered)
			return;
		answered = true;

		// set the correct text of the question

		questionText
				.setText(questionObject.getNumber()
						+ " x "
						+ questionObject.getMultiplier()
						+ " = "
						+ (questionObject.getNumber() * questionObject
								.getMultiplier()));

		// which number has user clicked on?

		Integer userAnswer = (Integer) v.getTag(R.id.option_value);
		questionObject.setUserAnswer(userAnswer.intValue());

		// is the number correct? this code can be avoided by using the
		// userAnswer
		Integer whetherAnswerIsCorrect = (Integer) v
				.getTag(R.id.is_correct_answer);

		if (whetherAnswerIsCorrect.intValue() == 1) {

			// let the next question be shown immediateky

			nextFadeIn = false;
			v.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.btn_green));
			Button button = (Button) v;
			// button.setTextColor(getResources().getColor(
			// R.color.correct_answer_color));

			TextView textView = (TextView) getActivity().findViewById(
					R.id.answer_status);
			textView.setText(R.string.correct_string);
			textView.setTextColor(getResources().getColor(R.color.text_green));

			// update the score

			int newScore = quizObject.getCurrentScore() + 1;
			quizObject.setCurrentScore(newScore);

			TextView textView1 = (TextView) getActivity().findViewById(
					R.id.current_score);
			textView1.setText(newScore + "/" + currentQuestionNumber);

			fadeIn(getActivity(), R.id.answer_status, -1);
			fadeIn(getActivity(), R.id.current_score, true, -1);

			// ActionBarActivity aBarActivity=(ActionBarActivity)getActivity();
			mediaPlayer = MediaPlayer.create(getActivity(), R.raw.right_sound);
			mediaPlayer.start();
			// showOneStar();
			showNextMenu();

		} else {
			// next be shown after a dealy for the person to absorb the answer

			nextFadeIn = true;
			v.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.btn_red));
			Button button = (Button) v;
			// button.setTextColor(getResources().getColor(
			// R.color.wrong_answer_color));
			mediaPlayer = MediaPlayer.create(getActivity(),
					R.raw.incorrect_sound);
			mediaPlayer.start();

			TextView textView = (TextView) getActivity().findViewById(
					R.id.answer_status);
			textView.setText(R.string.incorrect_string);
			textView.setTextColor(getResources().getColor(R.color.text_red));

			// update the score text area
			int newScore = quizObject.getCurrentScore();

			TextView textView1 = (TextView) getActivity().findViewById(
					R.id.current_score);
			textView1.setText(newScore + "/" + currentQuestionNumber);

			fadeIn(getActivity(), R.id.answer_status, -1);
			fadeIn(getActivity(), R.id.current_score, true, -1);

			for (int i = 0; i < buttons.length; i++) {
				button1 = buttons[i];
				Integer isCorrect = (Integer) button1
						.getTag(R.id.is_correct_answer);
				if (isCorrect.intValue() == 1) {
					button1.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.btn_green));

					// button1.setTextColor(getResources().getColor(
					// R.color.correct_answer_color));
					fadeIn(getActivity(), R.id.answer1, -1);
					break;
				}
			}

		}
		// showNextMenu();
	}

	/*
	 * method to run a fadein animation. This method understands various status
	 * on what to do next if the status is ANSWER_CORRECT/ANSWER_WRONG, the
	 * corresponding text is shown if the status is SHOW_SCORE, the score is set
	 * and animated if the flag SHOW_NEXT is true, Next Question menu item is
	 * animated if it is none of these, the method does not call any next
	 * animation
	 */
	public void fadeIn(Activity activity, int viewId, boolean SHOW_NEXT,
			int duration) {
		final boolean next = SHOW_NEXT;
		Animation animation = AnimationUtils.loadAnimation(activity,
				R.anim.fade_in_animation);

		if (duration != -1) {
			animation.setDuration(duration);
		}

		animation.reset();

		if (next) {

			animation.setAnimationListener(new AnimationListener() {

				@Override
				public void onAnimationStart(Animation animation) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onAnimationRepeat(Animation animation) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onAnimationEnd(Animation animation) {
					if (nextFadeIn == true) {
						showNextMenu();
					}

					// fadeIn(getActivity(), R.id.next_question, -1);

					// TODO Auto-generated method stub

				}

			});
		}
		// if animation is null, dont bother

		View view = activity.findViewById(viewId);
		if (view != null) {
			view.clearAnimation();
			view.startAnimation(animation);

		}

	}

	public void fadeIn(Activity activity, int viewId, int duration) {
		fadeIn(activity, viewId, false, duration);
	}

	private void showNextMenu() {
		
		//useless code after hack.. remove in furture
		//MenuItem mItem = menu.findItem(R.id.next_question);
		if (quizObject.getCurrentIndex() + 2 == quizObject.getNumberOfQuestions()) button5.setText("Last Question");
if (quizObject.getCurrentIndex() + 1 == quizObject.getNumberOfQuestions()) button5.setText("Check Result");
		
			//mItem.setTitle("Check Result");
		button5.setVisibility(View.VISIBLE);
		
		//mItem.setVisible(true);
		// if we have reached to the end of questionnaire, show "Check Result"

	}

	@Override
	public void dump(String prefix, FileDescriptor fd, PrintWriter writer,
			String[] args) {
		// TODO Auto-generated method stub
		super.dump(prefix, fd, writer, args);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		// in case of up navigation, do not handle this

		if (item.getItemId() != R.id.next_question) {
			return false;
		}
		quizObject.setCurrentIndex(quizObject.getCurrentIndex() + 1);
		Intent intent = null;
		// end of questionnaire?
		if (quizObject.getCurrentIndex() == quizObject.getNumberOfQuestions()) {
			intent = new Intent(getActivity(), QuizResultActivity.class);
		} else {
			intent = new Intent(getActivity(), QuizActivity.class);
		}
		new Intent(getActivity(), QuizActivity.class);
		intent.putExtra("questionnaire", this.quizObject);
		startActivity(intent);
		// TODO Auto-generated method stub
		return super.onOptionsItemSelected(item);

	}

	@Override
	public void onStop() {

		if (mediaPlayer != null) {
			mediaPlayer.release();
			mediaPlayer = null;
		}
		// TODO Auto-generated method stub
		super.onStop();
	}

	public void showNextQuestion(View v) {
		quizObject.setCurrentIndex(quizObject.getCurrentIndex() + 1);
		Intent intent = null;
		// end of questionnaire?
		if (quizObject.getCurrentIndex() == quizObject.getNumberOfQuestions()) {
			intent = new Intent(getActivity(), QuizResultActivity.class);
		} else {
			intent = new Intent(getActivity(), QuizActivity.class);
		}
		new Intent(getActivity(), QuizActivity.class);
		intent.putExtra("questionnaire", this.quizObject);
		startActivity(intent);
	}

}
