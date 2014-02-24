package com.manoj445.quizquestion.generators;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

import android.text.method.QwertyKeyListener;
import android.util.Log;

public class QuizObject implements Serializable{

	private int currentScore=0;
	private int currentIndex;
	private int[] numbersIncludedInQuiz;
	// the default number of questions in the quiz is 10
	private int numberOfQuestions = 10;
	public static final int QUIZ_TYPE_RANDOM = 1;
	public static final int QUIZ_TYPE_SPECIFIC_NUMBER = 2;
	public static final int QUIZ_TYPE_LEARNING = 3;
	private int quizType;

	ArrayList<QuizQuestionObject> questionsArrayList;

	// this constructor is called to create quiz specific for a particular
	// number. This creates a
	// 10 question ordered quiz for a particular number

	public QuizObject(int number) {

		this(10, 2, new int[] { number });

	}

	public QuizObject(int number,int quiztype) {

		this(10, quiztype, new int[] { number });

	}

	// constructor for a random 10 question quiz based on numbers in the caller

	public QuizObject(int[] number) {

		this(10, 1, number);

	}

	public QuizObject(int numberOfQuestions, int quiz_type,
			int[] numbersIncludedInQuiz) {
		super();
		this.numberOfQuestions = numberOfQuestions;
		this.quizType = quiz_type;
		this.numbersIncludedInQuiz = numbersIncludedInQuiz;

	}

	
	public int getCurrentIndex() {
		return currentIndex;
	}

	public void setCurrentIndex(int currentIndex) {
		this.currentIndex = currentIndex;
	}

	public int[] getNumbersIncludedInQuiz() {
		return numbersIncludedInQuiz;
	}

	public int getNumberOfQuestions() {
		return numberOfQuestions;
	}

	public int getQuizType() {
		return quizType;
	}

	
	public ArrayList<QuizQuestionObject> getQuestionsArrayList() {
		return questionsArrayList;
	}

	public int getCurrentScore() {
		return currentScore;
	}

	public void setCurrentScore(int currentScore) {
		this.currentScore = currentScore;
	}

	public ArrayList<QuizQuestionObject> generateQuestionnaire() {
		
		HashSet<Integer> duplicateCheck=new HashSet<Integer>();
		// generate a quiz for a number like 3x1,3x2,3x3,3x4...
		
		 ArrayList<QuizQuestionObject> questionnaire= new ArrayList<QuizQuestionObject>(numberOfQuestions);
				 
		if (this.quizType==QUIZ_TYPE_SPECIFIC_NUMBER||this.quizType==QUIZ_TYPE_LEARNING)
		{
			for (int i = 0; i < this.numberOfQuestions; i++) {
				int multiple=this.numbersIncludedInQuiz[0];
				int multiplier=i+1;
				QuizQuestionObject questionObject=generateOneQuestion(multiple, multiplier);
				questionnaire.add(questionObject);
			}
		}
		//to-do: introduce a duplication check for the quiz questions
		
		else if (this.quizType==QUIZ_TYPE_RANDOM) {
			
			//HashSet<Integer> checkMultipleDuplication=new HashSet<Integer>();
			
			//HashSet<Integer> checkMultiplierDuplication=new HashSet<Integer>();
			
			for (int i = 0; i < this.numberOfQuestions; i++) {
				
				// to avoid duplication
				
				int numberOfTries=100;
				int tryNumber=0;
				boolean isNotDuplicate=false;
				
				int howManyNumbersInQuestion=numbersIncludedInQuiz.length;
				
				int randomPosition= (int)(howManyNumbersInQuestion*Math.random());
				
				int multiple=this.numbersIncludedInQuiz[randomPosition];
				
				int multiplier=(int)((numberOfQuestions+1)*Math.random());
				
				int currentProduct =multiple*multiplier;
				
				
				while (!isNotDuplicate) {
					isNotDuplicate=duplicateCheck.add(new Integer(currentProduct));
					
					//exit loop on getting unique number
					
					if(isNotDuplicate)break;
					
					multiple=this.numbersIncludedInQuiz[randomPosition];
					multiplier=(int)((numberOfQuestions+1)*Math.random());
					currentProduct =multiple*multiplier;
					
					//exit loop on 100 tries
					tryNumber++;
					if (tryNumber>=100) {
						break;
					}
					
				}
				
				QuizQuestionObject questionObject=generateOneQuestion(multiple, multiplier);
				questionnaire.add(questionObject);
			}
		}
		questionsArrayList=questionnaire;
		return questionnaire;
		
		
	}
	// This method generates a 4 options quiz question object for the given
	// 
	public QuizQuestionObject generateOneQuestion(int number, int multiplier) {
		
		Log.e("KUMAR", "number "+number+" multiplier "+multiplier);
		
		
		QuizQuestionObject questionObject = new QuizQuestionObject(4);
		questionObject.setNumber(number);
		questionObject.setMultiplier(multiplier);

		// MutiOptions cOptions = new MutiOptions();

		int currentMultiple = number * multiplier;

		int randomMin = currentMultiple - 10;
		int randomMax = currentMultiple + 10;

		// do not give negative options
		if (randomMin < 1) {
			randomMin = 1;
		}

		HashSet<Integer> set = new HashSet<Integer>();

		// add the correct answer to prevent the same being randomly
		// regenerated as duplicates

		Integer currentMultipleInteger = new Integer(currentMultiple);
		set.add(currentMultipleInteger);
		
		// try a loop of 100 to get unique integer values
		// using a set to get 3 unique integers

		for (int j = 0; j < 100; j++) {
			Integer random1 = (int) (randomMin + (Math.random() * (randomMax - randomMin)));
			boolean added = set.add(random1);
			if (set.size() == 4) {
				break;
			}
		}

		// if in a loop of 100, we do not get 3 unique numbers... fill in random
		// numbers -
		// risking duplication
		
		if (set.size() < 4) {
			// means that we do not have 4 unique answers, so we are taking
			// chance with a duplicate

			for (int j = 0; j < (4 - set.size()); j++) {
				Integer random1 = (int) (randomMin + (Math.random() * (randomMax - randomMin)));
				set.add(random1);
			}

		}

		// now remove the currentmultiple as it is not needed, it was only
		// required to remove duplicates

		set.remove(currentMultipleInteger);

		int randomPosition = (int) (Math.random() * 4);
		
		
		Log.e("KUMAR", "randomPosition "+randomPosition+" currentMultipleInteger "+currentMultipleInteger);
		
		questionObject.possibleAnswers.set(randomPosition,currentMultipleInteger);
		Integer[] stack = new Integer[3];
		stack = set.toArray(stack);
		
		
		for (int j = 0, k = 0; j < questionObject.possibleAnswers.size(); j++) {
			if (questionObject.possibleAnswers.get(j).intValue() == -1) {
				questionObject.possibleAnswers.set(j, stack[k]);
				++k;
			}
		}
		return questionObject;
	}

}
