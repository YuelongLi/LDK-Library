package testers;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import fxui.DataPane3D;

public class DataPane3DFXTester extends Application{
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		DataPane3D pane = new DataPane3D();
		pane.setPrefSize(1000, 1000);
		primaryStage.setScene(new Scene(pane));
		primaryStage.show();
	}
}
