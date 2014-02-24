package com.manoj445.quizquestion.generators;

import java.io.Serializable;
import java.util.ArrayList;

public class QuizQuestionObject implements Serializable{

	private int number;
	private int multiplier;
	private int userAnswer;
	
	ArrayList<Integer> possibleAnswers;
	
	public QuizQuestionObject(int numberOfOptions) {
		super();
		this.possibleAnswers = new ArrayList<Integer>(4);
		for (int i = 0; i < numberOfOptions; i++) {
			this.possibleAnswers.add(new Integer(-1));
		}
	}

	public int getNumber() {
		return number;
	}
	
	public void setNumber(int number) {
		this.number = number;
	}
	
	public int getMultiplier() {
		return multiplier;
	}
	
	public void setMultiplier(int multiplier) {
		this.multiplier = multiplier;
	}
	
	public int getUserAnswer() {
		return userAnswer;
	}
	
	public void setUserAnswer(int userAnswer) {
		this.userAnswer = userAnswer;
	}
	
	public ArrayList<Integer> getPossibleAnswers() {
		return possibleAnswers;
	}
	
	public void setPossibleAnswers(ArrayList<Integer> possibleAnswers) {
		this.possibleAnswers = possibleAnswers;
	}
	
	public int getCorrectAnswer(){
		return multiplier*number;
	}
	
}
