package com.jsoftware.test.impl;

import com.jsoftware.test.api.IQuestion;


/**
 * Abstract class represents a Question.
 * @author jwest
 */
public abstract class Question implements IQuestion, java.io.Serializable  {
	private final String QUESTION;
	
	public Question(String question) {this.QUESTION = question;}
	
	/**
	 * @return Text of question
	 */
	@Override
	public String getQUESTION () {
		return this.QUESTION;
	}
}
