package dad.javafx.terminaltrainer.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dad.javafx.terminaltrainer.editor.model.Challenge;
import dad.javafx.terminaltrainer.ui.app.App;
import dad.javafx.terminaltrainer.utils.JSONUtils;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class MainController implements Initializable {
	
	// Model
	private ObjectProperty<Challenge> challenge = new SimpleObjectProperty<>();
	
	//Controllers
	GoalController goalController = new GoalController();
	ChallengeController challengeController = new ChallengeController();

	@FXML
	private BorderPane view;

	@FXML
	private Tab tabChallenge;

	@FXML
	private Tab tabGoal;

	@FXML
	void OnDarkThemeAction(ActionEvent event) {

	}

	@FXML
	void OnLightThemeAction(ActionEvent event) {

	}

	@FXML
	void onLoadChallengeAction(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open a challenge file.");
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Challenge (*.challenge)", "*.challenge"));
		fileChooser.getExtensionFilters().add(new ExtensionFilter("All types of files", "."));
		File chFile = fileChooser.showOpenDialog(App.getPrimaryStage());
		if (chFile != null) {
			try {

				challenge.set(JSONUtils.fromJson(chFile, Challenge.class));

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public MainController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainViewTrainer.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		tabChallenge.setContent(challengeController.getView());
		tabGoal.setContent(goalController.getView());
		
		
		challenge.addListener((o, ov, nv) -> onChallengeChanged(o, ov, nv));
		challenge.set(new Challenge());
	}

	
	
	private void onChallengeChanged(ObservableValue<? extends Challenge> o, Challenge ov, Challenge nv) {
		if (ov != null) {
			challengeController.challengeProperty().unbindBidirectional(challenge);
			goalController.challengeProperty().unbindBidirectional(challenge);
		}

		if (nv != null) {
			challengeController.challengeProperty().bindBidirectional(challenge);
			goalController.challengeProperty().bindBidirectional(challenge);
		}
	}

	public BorderPane getView() {
		return view;
	}

	public ObjectProperty<Challenge> challengeProperty() {
		return challenge;
	}

}
