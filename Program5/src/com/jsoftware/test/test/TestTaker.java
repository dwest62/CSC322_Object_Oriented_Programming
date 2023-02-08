package com.jsoftware.test.test;

import util.PromptHelper;
import com.jsoftware.test.api.*;
import com.jsoftware.test.impl.QuestionFactory;

import java.util.Arrays;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class TestTaker {
	private static int correctAnswerCount = 0;
	private static final QuestionFactory FACTORY = new QuestionFactory();
	private static IQuestionSet SET;
	
	public static void main(String[] args) {
		loadQuestionSet();
		loadCurrentQuestionSet();
		runTest();
		printResults();
	}
	
	private static final Map<Class<? extends IQuestion>, Function<IQuestion, Boolean>> QUESTION_TEST_MAP =
		Map.of(
			IShortAnswerQuestion.class, e -> testShortAnswer((IShortAnswerQuestion) e),
			IFillInBlanksQuestion.class, e -> testFillInTheBlank((IFillInBlanksQuestion) e),
			ITrueFalseQuestion.class, e -> testTrueFalse((ITrueFalseQuestion) e),
			IMultipleChoiceQuestion.class, e -> testMultipleChoice((IMultipleChoiceQuestion) e)
		);
	
	private static void loadQuestionSet() {
		Supplier<IQuestionSet> promptSet = () -> {
			String fileName = PromptHelper.getString("What test would you like to take? ");
			try {
				return FACTORY.load(fileName);
			} catch (Exception ex) {
				try {
					return FACTORY.load(fileName + ".dat");
				} catch (Exception e) {
					throw new IllegalArgumentException("Error loading test file. " + ex.getMessage());
				}
			}
		};
		System.out.println("Welcome to the Test taker program!\n");
		SET = PromptHelper.sanitizePrompt(promptSet);
		System.out.println("\nTest loaded successful!\n");
	}
	
	private static void loadCurrentQuestionSet() {
		int choice  = PromptHelper.getSanitizedInt(
			"Enter 1 to take a whole test or 2 to take a sample test.\nYour Choice: ",
			"Invalid choice.",
			Pattern.compile("^[12]$")
		);
		SET = choice == 1 ? SET : promptRandomSample();
	}
	
	private static void runTest() {
		String bar = "-".repeat(28);
		System.out.print(bar + "\n\nThe test starts now!\n" + bar + "\n");
		IntStream.range(0,  SET.size()).forEach(TestTaker::testQuestion);
	}
	
	private static void testQuestion(int index) {
		final IQuestion QUESTION = SET.getQuestion(index);
		String question = "\nQuestion " + (index + 1) + " of " + SET.size() + ":\n" + "-".repeat(17) + "\n"
				+ QUESTION.getQuestion();
		
		System.out.println(question);
		try {
			correctAnswerCount +=
				QUESTION_TEST_MAP
					.entrySet()
					.stream()
					.filter(e -> e.getKey().isAssignableFrom(QUESTION.getClass()))
					.findAny()
					.orElseThrow(NoSuchElementException::new)
					.getValue()
					.apply(QUESTION)
				? 1 : 0;
		} catch (NoSuchElementException ex) {
			System.out.println("Fatal error processing Question. " + ex.getMessage());
			System.exit(0);
		}
	}
	private static Boolean testTrueFalse (ITrueFalseQuestion question) {
		System.out.println("True/False?");
		return question.checkAnswer(PromptHelper.getSanitizedBoolean("Your answer: "));
	}
	private static Boolean testMultipleChoice (IMultipleChoiceQuestion question) {
		Predicate<Integer> isNumberBetween1And4 = n -> n < 5 && n > 0;
		return question.checkAnswer(
			PromptHelper.getSanitizedInt(
				"Your answer: ",
				"Invalid input, please enter a number between 1 and 4",
				isNumberBetween1And4
			)
		);
	}
	
	private static Boolean testFillInTheBlank(IFillInBlanksQuestion question) {
		final String[] ANSWER =
			Arrays
				.stream(PromptHelper.getString("Your answer (Separate answers with comma.): ").split(","))
				.map(String::trim).toArray(String[]::new);
		return question.checkAnswer(ANSWER);
	}
	
	private static Boolean testShortAnswer(IShortAnswerQuestion question) {
		return question.checkAnswer(PromptHelper.getString("Your answer: "));
	}
	
	private static IQuestionSet promptRandomSample() {
		final String PROMPT_N = "How many questions would you like to sample? ";
		final String ERR_MSG = "Invalid input. Please enter a number above 0";
		Predicate<Integer> isGreaterThanZero = e -> e > 0;
		return SET.randomSample(PromptHelper.getSanitizedInt(PROMPT_N,ERR_MSG, isGreaterThanZero));
	}
	
	private static void printResults() {
		System.out.println(
			"\nYou got " + correctAnswerCount + " questions right out of " + SET.size() +" questions total."
		);
	}
}
