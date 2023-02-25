package com.jsoftware.test.impl;

import com.jsoftware.test.api.IShortAnswerQuestion;

import java.util.Arrays;


/**
 * Represents a short answer question.
 *
 * @author jwest
 */
public class ShortAnswerQuestion extends Question implements IShortAnswerQuestion, java.io.Serializable {
    private String[] keywords;

    ShortAnswerQuestion(String question, String[] keywords) {
        super(question);
        this.keywords = keywords;
    }

    /**
     * Check if answer is correct
     *
     * @param answer answer to check
     * @return true if answer contains all keywords, otherwise false
     */
    @Override
    public boolean checkAnswer(String answer) {
        return Arrays.stream(keywords).allMatch(answer::contains);
    }

}
