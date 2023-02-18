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


/**
 * Represents a test taker CLI which allows prompts the user for answers to questions and provides feedback.
 */
public class TestTaker {
//	Number of correct answers
	private static int correctAnswerCount = 0;
	private static final QuestionFactory FACTORY = new QuestionFactory();
	private static IQuestionSet SET;
	
	public static void main(String[] args) {
		loadQuestionSet();
		loadCurrentQuestionSet();
		testQuestions();
		printResults();
	}
	
//	Map of test type to test question function which prompts user for answer and validates answer
	private static final Map<Class<? extends IQuestion>, Function<IQuestion, Boolean>> QUESTION_TEST_MAP =
		Map.of(
			IShortAnswerQuestion.class, e -> testShortAnswer((IShortAnswerQuestion) e),
			IFillInBlanksQuestion.class, e -> testFillInTheBlank((IFillInBlanksQuestion) e),
			ITrueFalseQuestion.class, e -> testTrueFalse((ITrueFalseQuestion) e),
			IMultipleChoiceQuestion.class, e -> testMultipleChoice((IMultipleChoiceQuestion) e)
		);
	
	/**
	 * Attempts to load file from user prompt for filename. Will try to append .dat to filename if first attempt fails.
	 */
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
	
	/**
	 * Get user choice between full test and random sample and sets questions to random sample if needed.
	 */
	private static void loadCurrentQuestionSet() {
		int choice  = PromptHelper.getSanitizedInt(
			"Enter 1 to take a whole test or 2 to take a sample test.\nYour Choice: ",
			"Invalid choice.",
			Pattern.compile("^[12]$")
		);
		SET = choice == 1 ? SET : promptRandomSample();
	}
	
	/**
	 * Loop through the question set and test user on each question
	 */
	private static void testQuestions () {
		String bar = "-".repeat(28);
		System.out.print(bar + "\n\nThe test starts now!\n" + bar + "\n");
		IntStream.range(0,  SET.size()).forEach(TestTaker::testQuestion);
	}
	
	/**
	 * @param index The index of the current question being tested
	 */
	private static void testQuestion(int index) {
		final IQuestion QUESTION = SET.getQuestion(index);
		
//		print question header
		String question = "\nQuestion " + (index + 1) + " of " + SET.size() + ":\n" + "-".repeat(17) + "\n"
				+ QUESTION.getQUESTION();
		
//		print question
		System.out.println(question);
		
//		Loop through question type map. Attempt to match implemented class with inherited key.
		try {
			boolean isCorrect =
				QUESTION_TEST_MAP
					.entrySet()
					.stream()
					.filter(e -> e.getKey().isAssignableFrom(QUESTION.getClass()))
					.findAny()
					.orElseThrow(NoSuchElementException::new)
					.getValue()
					.apply(QUESTION);
			correctAnswerCount += isCorrect ? 1 : 0;
			System.out.println(isCorrect ? "You got it!" : "Wrong!");
		} catch (NoSuchElementException ex) {
//			If code reaches here, the question being processed is not a valid subtype of any class in map.
			
			System.out.println("Fatal error processing Question. No handling for question type: " + QUESTION.getClass()
				+". " + ex.getMessage());
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
	
	/**
	 * @return Prompt for number of questions in sample and return random sample
	 */
	private static IQuestionSet promptRandomSample() {
		final String PROMPT_N = "How many questions would you like to sample? ";
		final String ERR_MSG = "Invalid input. Please enter a number above 0";
		
		return PromptHelper.sanitizePrompt(
				() -> SET.randomSample(PromptHelper.getInt("How many questions would you like to sample? "))
		);
	}
	
	private static void printResults() {
		System.out.println(
			"\nYou got " + correctAnswerCount + " questions right out of " + SET.size() +" questions total."
		);
	}
}
