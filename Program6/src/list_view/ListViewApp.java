package list_view;

import javafx.application.Application;
import javafx.collections.FXCollections;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javafx.util.Callback;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ListViewApp extends Application {
	final ListView<Person> PERSON_LIST_VIEW = new ListView<>(
		FXCollections.observableArrayList(
			new Person("Ian", "Bergstrom"),
			new Person("Carter", "Daniel"),
			new Person("March", "Hill"))
	);
	
	private static final Text CURRENT_TEXT = new Text();
	@Override
	public void start (Stage stage) {
		PERSON_LIST_VIEW.setCellFactory(getCellFactory());

		VBox box = new VBox(PERSON_LIST_VIEW);
		box.setPadding(new Insets(20,10,20 ,10));
		box.setSpacing(5);
		
		PERSON_LIST_VIEW.borderProperty().set(Border.stroke(Color.GRAY));
		Scene scene = new Scene(box);
		scene.getStylesheets().add("list_view/styles.css");
		stage.setScene(scene);
		stage.show();
	}
	
	private static Callback<ListView<Person>, ListCell<Person>> getCellFactory () {
		return new Callback<>() {
			@Override public ListCell<Person> call (ListView<Person> personListView) {
				return new ListCell<>() {
					@Override
					public void updateItem(Person person, boolean bool) {
						this.setId("list");
						super.updateItem(person, bool);
						if(!(bool || person == null)) {
							final String NAME = person.getlName() + ", " + person.getfName();
							setText(NAME);
							this.focusedProperty().addListener((observableValue, oldVal, newVal) -> {
								if (newVal) CURRENT_TEXT.setText(NAME);
							});
						}
					}
				};
			}
		};
	}
	
	public static void main(String[] args) {
		Application.launch(args);
	}
}
