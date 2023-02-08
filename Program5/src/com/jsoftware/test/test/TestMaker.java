package com.jsoftware.test.test;

import menu.CLIMenu;
import menu.CLIOption;
import util.PromptHelper;
import com.jsoftware.test.api.IQuestion;
import com.jsoftware.test.api.IQuestionSet;
import com.jsoftware.test.impl.QuestionFactory;

import java.util.Arrays;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.IntStream;


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
	public static void main(String[] args) {
		init();
		MENU.runMenu("\nWhat would you like to do?", "Your choice: ", 6);
	}
	private static void init() {
		System.out.println("Welcome to the TestMaker program!\n");
		NAME = PromptHelper.getString("What would you like to call this test? ");
	}

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
	
	
	private static void addTrueFalse() {
		final String QUESTION = PromptHelper.getString("\nWhat is your true/false question?\n");
		final boolean ANSWER = PromptHelper.getSanitizedBoolean(
			"\nWhat is the answer? (Please enter exactly true or false)\n");
		
		SET.add(FACTORY.makeTrueFalse(QUESTION, ANSWER));
	}
	//TODO
	private static void addFillInBlanks() {
		Predicate<String> hasABlank =
			s -> Arrays.stream(s.split(" ")).anyMatch(e -> e.matches("^ ?_+[^A-Za-z]?$"));
		final String QUESTION =
			PromptHelper.getString(
				"\nWhat is your fill-in-the-blank-question?\n",
				"A fill-in-the-blanks question must have at least one blank (e.g. The blank can go ____).",
				hasABlank
			);
			
		IQuestion Q = PromptHelper.sanitizePrompt(
			() -> {
				final String ANSWER =
					PromptHelper.getString("\nWhat is the answer? Please separate with commas.\n");
				if (ANSWER.length() == 0) throw new IllegalArgumentException("Invalid input. Please enter a keyword.");
				final String[] ANSWERS =
					Arrays.stream(ANSWER.split(",")).map(String::trim).toArray(String[]::new);
				return FACTORY.makeFillInBlank(QUESTION, ANSWERS);
			}
		);
		
		SET.add(Q);
	}
	//TODO
	private static void addShortAnswer() {
		String QUESTION = PromptHelper.getString("\nWhat is your short answer question?\n");
		
		int N_KEYWORDS = PromptHelper.getSanitizedInt(
			"\nHow many keywords does your short answer question have?\n",
			"Invalid input. Please enter a valid positive integer.",
			Pattern.compile("^[1-9]+[0-9]*$")
		);
		System.out.println();
		
		final String[] KEYWORDS =
			IntStream
				.range(0, N_KEYWORDS)
				.boxed()
				.map(e ->PromptHelper.getString("What is a keyword in your short answer question? "))
				.toArray(String[]::new);
		
		SET.add(FACTORY.makeShortAnswer(QUESTION, KEYWORDS));
	}
	
	//TODO
	private static void removeQuestion() {
		if (SET.size() == 0) {
			System.out.println("\nThere are currently no questions in the test.");
		}
		else {
			final String PROMPT = getQuestions();
			final String ERR_MSG =
				"Invalid input. Please enter a valid integer within range 0 - " + (SET.size() - 1) + ".";
			final Predicate<Integer> IS_VALID_INDEX = e -> e >= 0 && e < SET.size();
			
			final int INDEX = PromptHelper.getSanitizedInt(
				PROMPT,
				ERR_MSG,
				IS_VALID_INDEX
			);
			
			SET.remove(INDEX);
		}
	}
	
	//TODO
	private static void exit() {
		FACTORY.save(SET, TestMaker.NAME + ".dat");
		System.out.println("\nTest saved\nGoodbye!");
	}
	private static String getQuestions() {
		final StringBuilder PROMPT_BUILDER =
			new StringBuilder("\nSelect the index of the question you would like to remove.\n\n");
		
		BiConsumer<Integer, StringBuilder> appendQuestion =
			(i, sb) ->
                sb.append("Question ").append(i).append(") ").append(SET.getQuestion(i).getQuestion()).append("\n");
		
		IntStream.range(0, SET.size()).forEach(e -> appendQuestion.accept(e, PROMPT_BUILDER));
		PROMPT_BUILDER.append("Your choice: ");
		return PROMPT_BUILDER.toString();
	}
}
