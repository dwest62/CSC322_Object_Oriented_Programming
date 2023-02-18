package com.jsoftware.test.test;

import com.jsoftware.test.api.IQuestion;
import com.jsoftware.test.api.IQuestionSet;
import com.jsoftware.test.impl.QuestionFactory;
import menu.CLIMenu;
import menu.CLIOption;
import util.PromptHelper;
import java.util.Arrays;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.IntStream;


/**
 * Represents a CLI which allows the user to create a new test. The test will be saved to file upon exit of program.
 */
public class TestMaker {
	private static final QuestionFactory FACTORY = new QuestionFactory();
	private static String NAME = "test";
	private static final IQuestionSet SET = FACTORY.makeEmptyQuestionSet();
	private static final CLIMenu MENU = new CLIMenu(
		new CLIOption[]{
			new CLIOption(1, "add a multiple-choice question", TestMaker::addMultipleChoice),
			new CLIOption(2, "add a true/false question", TestMaker::addTrueFalse),
			new CLIOption(3, "add a fill-in-the-blank question", TestMaker::addFillInBlanks),
			new CLIOption(4, "add a short answer question", TestMaker::addShortAnswer),
			new CLIOption(5, "remove a question", TestMaker::removeQuestion),
			new CLIOption(6, "exit program", TestMaker::exit)
		}
	);
	
//	Entry point of program
	public static void main(String[] args) {
		init();
		MENU.runMenu("\nWhat would you like to do?", "Your choice: ", 6);
	}
	private static void init() {
		System.out.println("Welcome to the TestMaker program!\n");
		NAME = PromptHelper.getString("What would you like to call this test? ");
	}
	
	
	/**
	 * Add a multiple choice question to question set
	 */
	private static void addMultipleChoice() {
		final String QUESTION = PromptHelper.getString("\nWhat is your multiple-choice question?\n");
		System.out.println();
		
		final String[] CHOICES_PROMPTS =
			Arrays
				.stream(new String[]{"first", "second", "third", "fourth"})
                .map(e -> "Please enter your " + e + " choice: ")
                .toArray(String[]::new);
		
		final String[] CHOICES = PromptHelper.getStringArr(CHOICES_PROMPTS);
		
		final int ANSWER = PromptHelper.getSanitizedInt(
			"What choice was the answer? (Enter #1-4): ",
			"Invalid argument, please enter a number between 1 and 4.\n",
			java.util.regex.Pattern.compile("^[1-4]$")
		);
	
		SET.add(FACTORY.makeMultipleChoice(QUESTION, CHOICES, ANSWER));
	}
	
	/**
	 * Add a true / false question to question set
	 */
	private static void addTrueFalse() {
		final String QUESTION = PromptHelper.getString("\nWhat is your true/false question?\n");
		final boolean ANSWER = PromptHelper.getSanitizedBoolean(
			"\nWhat is the answer? (Please enter exactly true or false)\n");
		
		SET.add(FACTORY.makeTrueFalse(QUESTION, ANSWER));
	}
	
	/**
	 * Add a fill-in-the-blanks question to question set
	 */
	private static void addFillInBlanks() {
//		Get question with at least one blank
		Predicate<String> hasABlank =
			s -> Arrays.stream(s.split(" ")).anyMatch(e -> e.matches("^ ?_+[^A-Za-z]?$"));
		final String QUESTION =
			PromptHelper.getString(
				"\nWhat is your fill-in-the-blank-question?\n",
				"A fill-in-the-blanks question must have at least one blank (e.g. The blank can go ____).",
				hasABlank
			);
			
		/*
			User will be prompted for a String ANSWER which is split on comma, trimmed of whitespace, and collected to array.
			Sanitize Prompt display error message and retry prompt if any exception is thrown.
		*/
		IQuestion Q = PromptHelper.sanitizePrompt(
			() -> {
				final String ANSWER =
					PromptHelper.getString("\nWhat is the answer? Please separate with commas.\n");
				
//				Parse array from String. Split on comma, trim whitespace, and collect.
				final String[] ANSWERS =
					Arrays.stream(ANSWER.split(",")).map(String::trim).toArray(String[]::new);
				
//
				return FACTORY.makeFillInBlank(QUESTION, ANSWERS);
			}
		);
		
		SET.add(Q);
	}
	
	/**
	 * Add a short answer question to question set
	 */
	private static void addShortAnswer() {
		String QUESTION = PromptHelper.getString("\nWhat is your short answer question?\n");
		
		int N_KEYWORDS = PromptHelper.getSanitizedInt(
			"\nHow many keywords does your short answer question have?\n",
			"Invalid input. Please enter a valid positive integer.",
			Pattern.compile("^[1-9]+[0-9]*$")
		);
		System.out.println();
		
//		Get keywords iterating over a stream of ints from given range, prompting user for keywords
		final String[] KEYWORDS =
			IntStream
				.range(0, N_KEYWORDS)
				.boxed()
				.map(e ->PromptHelper.getString("What is a keyword in your short answer question? "))
				.toArray(String[]::new);
		
		SET.add(FACTORY.makeShortAnswer(QUESTION, KEYWORDS));
	}
	
	/**
	 * remove question from question set
	 */
	private static void removeQuestion() {
//		Handle case where set is empty
		if (SET.size() == 0) System.out.println("\nThere are currently no questions in the test.");
		else {
			final String PROMPT = getQuestions();
			final String ERR_MSG =
				"Invalid input. Please enter a valid integer within range 0 - " + (SET.size() - 1) + ".";

//			Prompt for int and run remove from set using its boolean result as a predicate to validate input.
			final Predicate<Integer> IS_VALID_INDEX = SET::remove;
			PromptHelper.getSanitizedInt(PROMPT, ERR_MSG, IS_VALID_INDEX);
		}
	}
	
	/**
	 * Save test and print exit message
	 */
	private static void exit() {
//		Save file under file name appending .dat extension of not already added
		FACTORY.save(SET, TestMaker.NAME.matches(".dat$") ? TestMaker.NAME : TestMaker.NAME + ".dat");
		System.out.println("\nTest saved\nGoodbye!");
	}
	
	/**
	 * @return Get indexed String of current of questions to display to user
	 */
	private static String getQuestions() {
		final StringBuilder PROMPT_BUILDER =
			new StringBuilder("\nSelect the index of the question you would like to remove.\n\n");
		
		IntStream
			.range(0, SET.size())
			.forEach(e -> PROMPT_BUILDER.append(
				String.format("Question %d) %s\n", e, SET.getQuestion(e).getQUESTION())));
		
		return PROMPT_BUILDER.append("Your choice: ").toString();
	}
}
