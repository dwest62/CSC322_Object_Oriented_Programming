package com.jsoftware.test.impl;

import com.jsoftware.test.api.IQuestion;
import com.jsoftware.test.api.IQuestionFactory;
import com.jsoftware.test.api.IQuestionSet;

import java.io.*;



public class QuestionFactory implements IQuestionFactory {

	@Override
	public IQuestion makeMultipleChoice(String question, String[] choices, int answer) {
		return new MultipleChoiceQuestion(question, choices, answer);
	}
	
	@Override
	public IQuestion makeTrueFalse(String question, boolean answer) {
		return new TrueFalseQuestion(question, answer);
	}
	
	@Override
	public IQuestion makeFillInBlank(String question, String[] answers) {
		return new FillInBlanksQuestion(question, answers);
	}
	
	@Override
	public IQuestion makeShortAnswer(String question, String[] keywords) {
		return new ShortAnswerQuestion(question, keywords);
	}
	
	@Override
	public IQuestionSet load(String filename) throws IOException {
		try(ObjectInputStream input = new ObjectInputStream(new FileInputStream(filename)))
		{
			return (IQuestionSet) input.readObject();
		} catch (ClassNotFoundException e) {
			throw new IOException(e);
		}
	}
	
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
	
	@Override
	public IQuestionSet makeEmptyQuestionSet() {
		return new QuestionSet();
	}
}
