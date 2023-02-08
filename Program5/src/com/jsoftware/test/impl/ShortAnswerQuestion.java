package com.jsoftware.test.impl;

import com.jsoftware.test.api.IShortAnswerQuestion;

import java.util.Arrays;


public class ShortAnswerQuestion extends Question implements IShortAnswerQuestion, java.io.Serializable  {
	private String[] keywords;
	
	ShortAnswerQuestion(String question, String[] keywords) {
		super(question);
		this.keywords = keywords;
	}
	
	@Override
	public boolean checkAnswer(String answer) {
		return Arrays.stream(keywords).allMatch(answer::contains);
	}
	
	public void setKeywords(String[] keywords) {
		this.keywords = keywords;
	}
}
