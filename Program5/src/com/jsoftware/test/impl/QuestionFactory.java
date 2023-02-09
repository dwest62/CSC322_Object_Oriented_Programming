package com.jsoftware.test.impl;

import com.jsoftware.test.api.IQuestion;
import com.jsoftware.test.api.IQuestionFactory;
import com.jsoftware.test.api.IQuestionSet;

import java.io.*;


/**
 * Represents a Question Factory used to make new questions of various types, save questions to file, and load
 * questions from file. Implements IQuestionFactory.
 *
 * @author jwest
 */
public class QuestionFactory implements IQuestionFactory {
	
	/**
	 * @param question question
	 * @param choices  potential answer array
	 * @param answer   index of correct answer
	 * @return new multiple choice question
	 */
	@Override
	public IQuestion makeMultipleChoice(String question, String[] choices, int answer) {
		return new MultipleChoiceQuestion(question, choices, answer);
	}
	
	/**
	 * @param question question
	 * @param answer boolean representing correct answer
	 * @return new true / false question
	 */
	@Override
	public IQuestion makeTrueFalse(String question, boolean answer) {
		return new TrueFalseQuestion(question, answer);
	}
	
	/**
	 * @param question question with blanks ('_')
	 * @param answers  answers to blanks as an array
	 * @return new fill-in-the-blanks question
	 */
	@Override
	public IQuestion makeFillInBlank(String question, String[] answers) throws IllegalArgumentException {
		return new FillInBlanksQuestion(question, answers);
	}
	
	/**
	 * @param question question
	 * @param keywords keywords to validate answer
	 * @return new short answer question
	 */
	@Override
	public IQuestion makeShortAnswer(String question, String[] keywords) {
		return new ShortAnswerQuestion(question, keywords);
	}
	
	/**
	 * Load Test set from file
	 * @param filename Test set file name (with or without file extension (.dat))
	 * @return Test set
	 * @throws IOException if error loading file
	 */
	@Override
	public IQuestionSet load(String filename) throws IOException {
		try(ObjectInputStream input = new ObjectInputStream(new FileInputStream(filename)))
		{
			return (IQuestionSet) input.readObject();
		} catch (ClassNotFoundException e) {
			throw new IOException(e);
		}
	}
	
	/**
	 * Save test set to file
	 * @param testSet  The test set to be stored.
	 * @param filename The filename to be used.
	 * @return true if save successful, otherwise false.
	 */
	@Override
	public boolean save(IQuestionSet testSet, String filename) {
		try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(filename, true))) {
			output.writeObject(testSet);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}
	
	/**
	 * @return new empty question set
	 */
	@Override
	public IQuestionSet makeEmptyQuestionSet() {
		return new QuestionSet();
	}
}
