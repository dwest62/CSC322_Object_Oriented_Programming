package com.jsoftware.test.impl;

import com.jsoftware.test.api.ITrueFalseQuestion;

/**
 * Represents a true / false question.
 *
 * @author jwest
 */
public class TrueFalseQuestion extends Question implements ITrueFalseQuestion, java.io.Serializable {
    private boolean answer;

    /**
     * @param question String of question
     * @param answer   boolean of answer
     */
    TrueFalseQuestion(String question, boolean answer) {
        super(question);
        this.answer = answer;
    }

    /**
     * Check an answer
     *
     * @param answer boolean of answer to check
     * @return true if answer matches key
     */
    @Override
    public boolean checkAnswer(boolean answer) {
        return this.answer == answer;
    }

}
