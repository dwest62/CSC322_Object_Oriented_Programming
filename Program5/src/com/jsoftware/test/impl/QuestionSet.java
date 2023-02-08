package com.jsoftware.test.impl;

import com.jsoftware.test.api.IQuestion;
import com.jsoftware.test.api.IQuestionSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static java.util.stream.Collectors.toCollection;

public class QuestionSet implements IQuestionSet, java.io.Serializable{
	private final ArrayList<IQuestion> QUESTIONS;
	private QuestionSet(ArrayList<IQuestion> QUESTIONS) {
		this.QUESTIONS = QUESTIONS;
	}
	public QuestionSet() {
		this(new ArrayList<>());
	}
	
	public IQuestionSet emptyTestSet() {
		return new QuestionSet();
	}
	
	@Override
	public IQuestionSet randomSample(int size) throws IllegalArgumentException {
		int qSize = QUESTIONS.size();
		if (size < 0 || size >= qSize) throw new IllegalArgumentException(
			"Invalid sample size. Please enter a value between 1 and " + (this.size() - 1));

		int[] randomIndices = new Random().ints(0, this.size()).distinct().limit(size).toArray();
		
		return new QuestionSet(
			Arrays.stream(randomIndices)
				.boxed()
				.map(this::getQuestion)
				.collect(toCollection(ArrayList<IQuestion>::new))
		);

	}
	@Override
	public boolean add(IQuestion question) {
		return QUESTIONS.add(question);
	}
	
	@Override
	public boolean remove(int index) {
		QUESTIONS.remove(index);
		
		return true;
	}
	
	@Override
	public IQuestion getQuestion(int index) {
		return QUESTIONS.get(index);
	}
	
	@Override
	public int size() {
		return QUESTIONS.size();
	}
	
}
