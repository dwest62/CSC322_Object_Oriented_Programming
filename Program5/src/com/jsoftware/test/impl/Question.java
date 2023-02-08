package com.jsoftware.test.impl;

import com.jsoftware.test.api.IQuestion;


public class Question implements IQuestion, java.io.Serializable  {
	private String question;
	
	public Question(String question) {this.question = question;}
	
	@Override
	public String getQuestion() {
		return this.question;
	}
	
	public void setQuestion(String question) {
		this.question = question;
	}
}
