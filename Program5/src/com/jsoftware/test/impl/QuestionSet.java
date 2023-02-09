package com.jsoftware.test.impl;

import com.jsoftware.test.api.IQuestion;
import com.jsoftware.test.api.IQuestionSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static java.util.stream.Collectors.toCollection;

/**
 * Represents a set of questions.
 * @author jwest
 */
public class QuestionSet implements IQuestionSet, java.io.Serializable{
	private final ArrayList<IQuestion> QUESTIONS;
	
	/**
	 * Create a Question set with initial list of questions
	 * @param QUESTIONS Questions in set
	 */
	private QuestionSet(ArrayList<IQuestion> QUESTIONS) {
		this.QUESTIONS = QUESTIONS;
	}
	
	/**
	 * Default constructor. Creates empty set
	 */
	public QuestionSet() {
		this(new ArrayList<>());
	}
	
	/**
	 * @return new empty question set
	 */
	@Override
	public IQuestionSet emptyTestSet() {
		return new QuestionSet(new ArrayList<>());
	}
	
	/**
	 * @param size The number of random questions.
	 * @return new random sample of this question.
	 * @throws IllegalArgumentException if size is not between 1 and size of this questions set
	 */
	@Override
	public IQuestionSet randomSample(int size) throws IllegalArgumentException {
		if (size < 1 || size >= this.size()) throw new IllegalArgumentException(
			"Invalid sample size. Please enter a value between 1 and " + (this.size() - 1));

		int[] randomIndices = new Random().ints(0, this.size()).distinct().limit(size).toArray();
		
		return new QuestionSet(
			Arrays.stream(randomIndices)
				.boxed()
				.map(this::getQuestion)
				.collect(toCollection(ArrayList<IQuestion>::new))
		);

	}
	
	/**
	 * @param question question to add to set
	 * @return true if operation succeeds, otherwise false
	 */
	@Override
	public boolean add(IQuestion question) {
		return QUESTIONS.add(question);
	}
	
	/**
	 * Remove a question from set
	 * @param index index of question to be removed
	 * @return true if index
	 */
	@Override
	public boolean remove(int index) {
		if(index < 0 || index >= size()) return false;
		else {
			QUESTIONS.remove(index);
			return true;
		}
	}
	
	/**
	 * Get question from set
	 * @param index index of question to get
	 * @return question
	 */
	@Override
	public IQuestion getQuestion(int index) {
		return QUESTIONS.get(index);
	}
	
	/**
	 * @return size of question set (the number of questions).
	 */
	@Override
	public int size() {
		return QUESTIONS.size();
	}
	
}
