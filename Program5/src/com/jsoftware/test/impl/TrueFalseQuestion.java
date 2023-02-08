package com.jsoftware.test.impl;
import com.jsoftware.test.api.ITrueFalseQuestion;

public class TrueFalseQuestion extends Question implements ITrueFalseQuestion, java.io.Serializable  {
	private boolean answer;
	
	TrueFalseQuestion(String question, boolean answer) {
		super(question);
		this.answer = answer;
	}
	
	@Override
	public boolean checkAnswer(boolean answer) {
		return this.answer == answer;
	}
	
	public void setAnswer(boolean answer) {
		this.answer = answer;
	}
}
