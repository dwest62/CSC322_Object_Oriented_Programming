package com.jsoftware.test.impl;

import com.jsoftware.test.api.IFillInBlanksQuestion;
import java.util.Arrays;


/**
 * Implementation of a fill in the blanks question. Questions are given as a String and answers as an array of Strings.
 * An answer is true if it contains all the keywords.
 * @author jwest
 */
public class FillInBlanksQuestion extends Question implements IFillInBlanksQuestion, java.io.Serializable {
	private final String[] keywords;
	
	/**
	 * @param question Question to ask
	 * @param keywords Words to check answer against. Answer must contain all keywords to be true.
	 * @throws IllegalArgumentException if number of blanks in question does not match number of keywords
	 */
	FillInBlanksQuestion(String question, String[] keywords) throws IllegalArgumentException {
		super(question);
		this.keywords = keywords;
		int BLANK_COUNT =
			(int) Arrays.stream(question.split(" ")).filter(e -> e.matches("^ ?_+[^A-Za-z]?$")).count();
		if(BLANK_COUNT != keywords.length)
			throw new IllegalArgumentException(
				"Invalid fill-in-the-blanks question. Number of blanks (" + BLANK_COUNT + ") does not match number " +
					"of keywords (" + keywords.length + ").");
	}
	
	/**
	 * @param keywords Array of filled blank answers
	 * @return true if answers match keywords
	 */
	@Override
	public boolean checkAnswer(String[] keywords) {
		return Arrays.equals(keywords, this.keywords);
	}

}
