package investment_calculator;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Investment_Calculator extends Application {
	private static final LabeledTextField LTF_INVESTMENT_AMOUNT =
		new LabeledTextField(new TextField(), new Label("Investment Amount:"));

	private static final LabeledTextField LTF_NUMBER_OF_YEARS =
		new LabeledTextField(new TextField(), new Label("Number of Years:"));
	private static final LabeledTextField LTF_ANNUAL_INTEREST_RATE =
		new LabeledTextField(new TextField(), new Label("Annual Interest Rate:"));
	private static final LabeledTextField LTF_FUTURE_VALUE =
		new LabeledTextField(new TextField(), new Label("Future value:"));
	private static final Button CALCULATE_BTN = new Button("Calculate");
	private static final GridPane MAIN_PANE = new GridPane();
	
	@Override
	public void start (Stage stage) {
		setupInputBoxes();
		setupOutputBox();
		setupCalculatorBtn();
		setupMainPane();
		setupPrimaryStage(stage);
		updateFutureValue();
		stage.show();
	}
	
	public static void main (String[] args) {
		Application.launch(args);
	}
	
	private void setupInputBoxes () {
		
		Stream.of(LTF_INVESTMENT_AMOUNT, LTF_NUMBER_OF_YEARS, LTF_ANNUAL_INTEREST_RATE)
			.forEach(field -> {
				TextField textField = field.textField();
				textField.focusedProperty().addListener(
					(obs, oldVal, newVal) -> {if(oldVal && !newVal) updateFutureValue();}
				);
				textField.alignmentProperty().set(Pos.CENTER_RIGHT);
			});
		
		LTF_INVESTMENT_AMOUNT.textField().setTextFormatter(
			getDoubleTextFormatter(
				d -> String.format("$%,.2f", d),
				Pattern.compile("[$,]"),
				Pattern.compile("^\\$?([0-9,]*)(\\.[0-9]*)?$")
			)
		);
		
		LTF_ANNUAL_INTEREST_RATE.textField().setTextFormatter(
			getDoubleTextFormatter(
				d -> String.format("%.1f%%", d),
				Pattern.compile("%"),
				Pattern.compile("(([1-9][0-9]*)|0)?(\\.[0-9]*)?%?")
			)
		);
		
		LTF_NUMBER_OF_YEARS.textField().setTextFormatter(
			new TextFormatter<>(
				new IntegerStringConverter(),
				0,
				c -> c.getControlNewText().matches("[0-9]+") ? c : null
			)
		);
	}
	
	private static TextFormatter<Double> getDoubleTextFormatter
		(Function<Double, String> toString, Pattern replace, Pattern filterPattern) {
		DoubleStringConverter converter = new  DoubleStringConverter() {
			@Override
			public String toString (Double aDouble) {return toString.apply(aDouble);}
			@Override
			public Double fromString (String s) {

				return Double.parseDouble(s.replaceAll(replace.pattern(), ""));
			}
		};
		
		return new TextFormatter<>(
			converter,
			0.0,
			c -> c.getControlNewText().matches(filterPattern.pattern()) ? c : null
		);
	}
	
	public void setupOutputBox () {
		LTF_FUTURE_VALUE.textField().alignmentProperty().set(Pos.CENTER_RIGHT);
		LTF_FUTURE_VALUE.textField().setEditable(false);
	}
	
	public void setupCalculatorBtn() {
		CALCULATE_BTN.setOnMouseClicked(e -> updateFutureValue());
		HBox hBox = new HBox(CALCULATE_BTN);
		hBox.setAlignment(Pos.CENTER_RIGHT);
	}
	private void setupMainPane () {
		MAIN_PANE.setVgap(5);
		MAIN_PANE.setHgap(5);
		MAIN_PANE.setAlignment(Pos.CENTER);
		MAIN_PANE.setPadding(new Insets(12,12,12,12));
		addNodesToMainPane();
	}
	
	private void addNodesToMainPane () {
		Stream.of(LTF_INVESTMENT_AMOUNT, LTF_NUMBER_OF_YEARS, LTF_ANNUAL_INTEREST_RATE, LTF_FUTURE_VALUE)
			.forEach(LTF -> MAIN_PANE.addRow(MAIN_PANE.getRowCount(), LTF.label(), LTF.textField()));
		
		MAIN_PANE.add(CALCULATE_BTN, 1, MAIN_PANE.getRowCount());
	}
	
	private void setupPrimaryStage (Stage stage) {
		stage.setTitle("Investment Calculator");
		stage.setScene(new Scene(MAIN_PANE));
		stage.setResizable(false);
	}
	
	public void updateFutureValue () {
		
		final Double INVESTMENT = (Double) LTF_INVESTMENT_AMOUNT.textField().getTextFormatter().getValue();
		final Integer YEARS = (Integer) LTF_NUMBER_OF_YEARS.textField().getTextFormatter().getValue();
		final Double INTEREST_RATE = (Double) LTF_ANNUAL_INTEREST_RATE.textField().getTextFormatter().getValue();
		
		if(INVESTMENT != null && YEARS != null && INTEREST_RATE != null) {
			final double FUTURE_VALUE = INVESTMENT * Math.pow((1 + INTEREST_RATE / 100), YEARS.doubleValue());
			LTF_FUTURE_VALUE.textField().setText(String.format("$%,.2f", FUTURE_VALUE));
		}
	}
	
	private record LabeledTextField(TextField textField, Label label) {}
}
