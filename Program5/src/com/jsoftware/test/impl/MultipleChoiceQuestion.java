package com.jsoftware.test.impl;

import com.jsoftware.test.api.IMultipleChoiceQuestion;

import java.util.stream.IntStream;

public class MultipleChoiceQuestion extends Question implements IMultipleChoiceQuestion, java.io.Serializable  {
	private int answer;
	private String[] choices;

	
	MultipleChoiceQuestion(String question, String[] choices, int answer) throws IllegalArgumentException {
		super(question);
		setChoices(choices);
		setAnswer(answer);
	}
	
	@Override
	public boolean checkAnswer(int answer) {
		return answer == this.answer;
	}
	@Override
	public String getQuestion() {
		StringBuilder sb = new StringBuilder(super.getQuestion()).append("\n");
		IntStream.range(0, choices.length).forEach(
			e -> sb.append("\t").append(e + 1).append(") ").append(choices[e]).append("\n")
		);
		
		return sb.toString().trim();
	}
	public void setChoices(String[] choices) {
		this.choices = choices;
	}
	public void setAnswer(int answer) throws IllegalArgumentException {
		if(answer > choices.length || answer < 1)
			throw new IllegalArgumentException(
				"Invalid answer index. Please input a number between 1 and " + choices.length);
		
		this.answer = answer;
	}
}
