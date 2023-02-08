package com.jsoftware.test.impl;

import com.jsoftware.test.api.IFillInBlanksQuestion;

import java.util.Arrays;

public class FillInBlanksQuestion extends Question implements IFillInBlanksQuestion, java.io.Serializable {
	
	private String[] keywords;
	
	FillInBlanksQuestion(String question, String[] keywords) throws IllegalArgumentException {
		super(question);
		this.keywords = keywords;
		int BLANK_COUNT = (int) Arrays
			                  .stream(question.split(" ")).filter(e -> e.matches("^ ?_+[^A-Za-z]$")).count();
		if(BLANK_COUNT != keywords.length)
			throw new IllegalArgumentException(
				"Invalid fill-in-the-blanks question. Number of blanks (" + BLANK_COUNT + ") does not match number " +
					"of keywords (" + keywords.length + ").");
	}
	
	@Override
	public boolean checkAnswer(String[] keywords) {
		return Arrays.equals(keywords, this.keywords);
	}
	
	public String[] getKeywords() {
		return keywords;
	}
	
	public void setKeywords(String[] keywords) {
		this.keywords = keywords;
	}
}
