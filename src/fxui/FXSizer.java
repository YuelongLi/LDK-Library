package fxui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

/**
 * @version 0.1
 * @author Yuelong Li
 *         <p>
 *         FXSizer provides autosizing layout for UI designers using javaFX. It
 *         can be added on top of layouts like anchor pane and function together
 *         with synergy.
 *         </p>
 *         <p>
 *         Copyright 2017 <br>
 *         Cannot be used without authorization
 *         </p>
 * 
 * @see javafx.geometry.Bounds
 * @see javafx.scene.Node
 */

public class FXSizer {

	private Pane listenerPane;
	private Bounds org;
	private ListenerUpdate resizeAll;

	List<Node> nodes = new ArrayList<Node>();

	/**
	 * Stores the original layout of each node
	 */
	private HashMap<Node, Bounds> nodeBounds = new HashMap<Node, Bounds>();

	/**
	 * Stores all the original bound information of the child nodes for future
	 * removal
	 */
	private HashMap<Node, InvalidationListener> boundListeners = new HashMap<Node, InvalidationListener>();

	private HashMap<Node, Boolean> isNodeResized = new HashMap<Node, Boolean>();

	/**
	 * FXSizer has to be initialized with a listener panel, the size of which is
	 * constantly tracted to determine the resizing actions
	 * 
	 * @param listenerPane
	 */
	public FXSizer(Pane listenerPane) {
		this.listenerPane = listenerPane;
		resizeAll = new ListenerUpdate();
		org =listenerPane.getBoundsInLocal();
		listenerPane.layoutBoundsProperty().addListener(resizeAll);
	}

	/**
	 * Assigns the size listener pane to a new panel. All references of this panel
	 * will be <b> removed </b> from the child node configurations, if existent, to
	 * avoid conflict during resizing.
	 * 
	 * @param newListenerPane
	 */
	public void setListener(Pane newListenerPane) {
		nodes.remove(listenerPane);
		nodeBounds.remove(listenerPane);
		boundListeners.remove(listenerPane);
		listenerPane.layoutBoundsProperty().removeListener(resizeAll);
		listenerPane = newListenerPane;
		resizeAll = new ListenerUpdate();
		listenerPane.layoutBoundsProperty().addListener(resizeAll);
	}

	/**
	 * Explores all the child nodes of the listener panel and add them to the
	 * current configuration
	 */
	public void addAllChildren() {
		addAllChildren(listenerPane);
	}

	/**
	 * Explores all the child nodes of the specified panel and add them to the
	 * current configuration
	 * 
	 * @param parentPane
	 *            the panel within which the nodes are to be dynamiclly managed by
	 *            the layout
	 */
	public void addAllChildren(Pane parentPane) {
		new Thread(()-> {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			for (Node child : parentPane.getChildren())
				add(child);
		}).start();
	}

	public void add(Node node) {
		nodes.add(node);
		nodeBounds.put(node, toDepositBounds(node.getBoundsInParent()));
		isNodeResized.put(node, false);
		ChildNodeUpdate resized = new ChildNodeUpdate() {
			public void reconfigureSelf() {
				if (!isNodeResized.get(node)) {
					nodeBounds.put(node, toDepositBounds(node.getBoundsInParent()));
				} else
					isNodeResized.put(node, false);
			}
		};
		boundListeners.put(node, resized);
		node.layoutBoundsProperty().addListener(resized);
	}

	public void remove(Node node) {
		if (nodes.remove(node)) {
			nodeBounds.remove(node);
			boundListeners.remove(node);
			listenerPane.layoutBoundsProperty().removeListener(boundListeners.remove(node));
		}

	}

	public void resizeAll(double Ratio) {
		resizeAll(Ratio, Ratio);
	}

	boolean resizing = false;

	public void resizeAll(double xRatio, double yRatio) {
		resizing = true;
		for (Node child : nodes) {
			changeSize(child, xRatio, yRatio);
		}
		resizing = false;
	}

	protected void changeSize(Node node, double xRatio, double yRatio) {
		isNodeResized.put(node, true);
		if (node.isResizable()) {
			Bounds org = nodeBounds.get(node);
			node.resizeRelocate(org.getMinX() * xRatio, org.getMinY() * yRatio, org.getWidth() * xRatio,
					org.getHeight() * yRatio);
		}
		isNodeResized.put(node, true);
		if (node instanceof Region) {
			Bounds org = nodeBounds.get(node);
			node.setLayoutX(org.getMinX() * xRatio);
			node.setLayoutY(org.getMinY() * yRatio);
			((Region) node).setPrefWidth(org.getWidth() * xRatio);
			((Region) node).setPrefHeight(org.getHeight() * yRatio);
		}
	}

	/**
	 * Converts a set of bounds from the current layout to the deposited layout
	 * 
	 * @return the converted bounds
	 */
	protected Bounds toDepositBounds(Bounds b) {
		Bounds lisCurrent = listenerPane.getBoundsInLocal();
		double xRatio = org.getWidth() / lisCurrent.getWidth();
		double yRatio = org.getHeight() / lisCurrent.getHeight();
		double minX = b.getMinX() * xRatio, minY = b.getMinY() * yRatio, width = b.getWidth() * xRatio,
				height = b.getHeight() * yRatio;
		return new BoundingBox(minX, minY, width, height);
	}

	/**
	 * Resizes all child nodes if resize in listener is detected
	 */
	class ListenerUpdate implements InvalidationListener {

		@Override
		public void invalidated(Observable observable) {
			Bounds lisCurrent = listenerPane.getLayoutBounds();
			double xRatio = lisCurrent.getWidth() / org.getWidth();
			double yRatio = lisCurrent.getHeight() / org.getHeight();
			resizeAll(xRatio, yRatio);
		}

	}

	/**
	 * Updates node bounds storage if any changes that are not caused by resizeAll()
	 * method occurs
	 */
	class ChildNodeUpdate implements InvalidationListener {

		@Override
		public void invalidated(Observable observable) {
			reconfigureSelf();
		}

		public void reconfigureSelf() {

		}
	}
}