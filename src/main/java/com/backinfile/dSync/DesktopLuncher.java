package com.backinfile.dSync;

import java.io.File;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class DesktopLuncher {

	public static class JavaFxApplication extends Application {
		@Override
		public void start(Stage primaryStage) throws Exception {
			BorderPane root = new BorderPane();
			primaryStage.setScene(new Scene(root, 400, 160));
			primaryStage.show();
			primaryStage.setTitle("dSync generator");
			primaryStage.setResizable(false);

//			root.setStyle("-fx-font-size: 12pt");

			TextField outPackagePath = new TextField();
			TextField outFilePath = new TextField();
			TextField outClassName = new TextField();
			TextField dsSourceFilePath = new TextField();

			FileChooser fileChooser = new FileChooser();
			DirectoryChooser directoryChooser = new DirectoryChooser();

			Button fileSelectBtn = new Button("select");
			fileSelectBtn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					fileChooser.setTitle("select outFilePath");
					fileChooser.setInitialDirectory(new File("./"));
					var file = fileChooser.showOpenDialog(primaryStage);
					if (file != null) {
						dsSourceFilePath.setText(file.getPath());
					}
				}
			});
			Button directorySelectBtn = new Button("select");
			directorySelectBtn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					directoryChooser.setTitle("select dsSourceFilePath");
					directoryChooser.setInitialDirectory(new File("./"));
					var file = directoryChooser.showDialog(primaryStage);
					if (file != null) {
						outFilePath.setText(file.getPath());
					}
				}
			});

			Label genLabel = new Label();
			Button genButton = new Button("generator");
			genButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					try {
						dialog
					} catch (Exception e) {
					}
				}
			});

			Label headLabel = new Label("DSync Generator");
			headLabel.setFont(Font.font(20));

			GridPane gridPane = new GridPane();
			gridPane.add(headLabel, 0, 0, 3, 1);

			gridPane.add(new Label("outPackagePath"), 0, 1);
			gridPane.add(outPackagePath, 1, 1);

			gridPane.add(new Label("outFilePath"), 0, 2);
			gridPane.add(outFilePath, 1, 2);
			gridPane.add(directorySelectBtn, 2, 2);

			gridPane.add(new Label("outClassName"), 0, 3);
			gridPane.add(outClassName, 1, 3);

			gridPane.add(new Label("dsSourceFilePath"), 0, 4);
			gridPane.add(dsSourceFilePath, 1, 4);
			gridPane.add(fileSelectBtn, 2, 4);

			gridPane.add(child, columnIndex, rowIndex);

			root.setCenter(gridPane);
			gridPane.prefWidthProperty().bind(root.widthProperty());
			gridPane.prefHeightProperty().bind(root.heightProperty());
			root.setBottom(new Button("generate"));
		}
	}

	public static void main(String[] args) {
		Application.launch(JavaFxApplication.class, args);
	}

}
