package com.jsoftware.test.impl;

import com.jsoftware.test.api.IMultipleChoiceQuestion;

import java.util.stream.IntStream;

/**
 * Implementation of a multiple choice question. User is given a question and 4 choices. Answer is true if int provided
 * matches answer key.
 */
public class MultipleChoiceQuestion extends Question implements IMultipleChoiceQuestion, java.io.Serializable
{
	private int answer;
	private String[] choices;
	
	
	/**
	 * @param question Question to ask
	 * @param choices  array of possible answers
	 * @param answer   int representing index of correct answer (1-4)
	 * @throws IllegalArgumentException if answer is not 1 - 4.
	 */
	MultipleChoiceQuestion ( String question, String[] choices, int answer ) throws IllegalArgumentException
	{
		super(question);
		this.choices = choices;
		setAnswer(answer);
	}
	
	/**
	 * @param answer int representing index of user choice
	 * @return true if number matches answer
	 */
	@Override
	public boolean checkAnswer ( int answer )
	{
		return answer == this.answer;
	}
	
	/**
	 * Gets question prompt
	 *
	 * @return question
	 */
	@Override
	public String getQUESTION ()
	{
		StringBuilder sb = new StringBuilder(super.getQUESTION()).append("\n");
		IntStream.range(0, choices.length).forEach(e -> sb.append(String.format("\t%d) %s\n", e + 1, choices[ e ])));
		return sb.toString().trim();
	}
	
	/**
	 * @param answer answer index
	 * @throws IllegalArgumentException if answer key is not an int between 1 and 4
	 */
	private void setAnswer ( int answer ) throws IllegalArgumentException
	{
		if (answer > choices.length || answer < 1)
			throw new IllegalArgumentException(
				"Invalid answer index. Please input a number between 1 and " + choices.length);
		
		this.answer = answer;
	}
}
