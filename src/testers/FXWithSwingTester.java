package testers;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import javafx.application.Application;
import javafx.embed.swing.SwingNode;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import ui.*;

public class FXWithSwingTester extends Application{
	public static void main(String[] args) {
		launch(args);
	}

	 @Override
     public void start(Stage stage) {
         SwingNode swingNode = new SwingNode();

         createAndSetSwingContent(swingNode);

         Pane pane = new Pane();
         pane.getChildren().add(swingNode); // Adding swing node
         

         stage.setScene(new Scene(pane, 100, 50));
         stage.show();
     }

     private void createAndSetSwingContent(final SwingNode swingNode) {
         SwingUtilities.invokeLater(new Runnable() {
             @Override
             public void run() {
                 JPanel panel = new JPanel();
                 JButton mark = new JButton("Click me!");
                 panel.add(mark);
                 panel.setBounds(100, 500, 40, 10);
                 swingNode.setContent(panel);
             }
         });
     }
}
