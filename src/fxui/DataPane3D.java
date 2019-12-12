package fxui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

public class DataPane3D  extends AnchorPane{
	Canvas canvas;
	GraphicsContext g;
	public DataPane3D() {
		canvas = new Canvas(1000,1000);
		g = canvas.getGraphicsContext2D();
		getChildren().add(canvas);
		AnchorPane.setTopAnchor(canvas, 0.0);
		AnchorPane.setBottomAnchor(canvas, 0.0);
		AnchorPane.setLeftAnchor(canvas, 0.0);
		AnchorPane.setRightAnchor(canvas, 0.0);
		repaint(g);
	}
	
	protected void repaint(GraphicsContext g) {
		PixelWriter a = g.getPixelWriter();
		for(int i = 0; i<1000; i++)
			for(int u=0; u<g.getCanvas().getWidth(); u++) {
				for(int v=0; v<g.getCanvas().getWidth(); v++) {
					a.setColor(u, v, Color.RED);
				}
			}
		System.out.println("finished");
	}
}
