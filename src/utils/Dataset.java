package utils;

import java.awt.Color;

/**
 * Dataset is a abstract class that provides a unified interface for its subclasses. 
 * It serves the function of storing and manipulating coordinate objects.
 * @author Yuelong Li
 * @version 0.2
 */

public abstract class Dataset implements Cloneable{
	protected String name = "Dataset";
	protected DataType dataType = DataType.cartesianY;
	protected Color color = Color.BLACK;
	
	protected double plotGap = 0.01;
	public Dataset self = this;
	
	protected double offsetx = 0, offsety = 0;
	
	public abstract void initialize(double... params);
	
	/**
	 * Sets the average plotting gap between each points in the data set. 
	 * This helps to keep the resolution of a data graph consistent
	 * @param plotGap
	 */
	public void setPlotGap(double plotGap) {
		this.plotGap = plotGap;
		
	}
	
	/**
	 * Returns the average plotting gap between each points in the data set. 
	 * This helps to keep the resolution of a data graph consistent
	 * @param plotGap
	 */
	public double getPlotGap() {
		return plotGap;
	}
	
	public Dataset setName(String name) {
		this.name=name;
		return this;
	}
	
	public String getName() {
		return name;
	}

	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}
	
	public DataType getDataType() {
		return dataType;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public Color getColor() {
		return color;
	}
	
	public Dataset cloneFields(Dataset cloned) {
		cloned.setName("cloned "+name);
		cloned.setDataType(dataType);
		cloned.setColor(color);
		cloned.plotGap = plotGap;
		return cloned;
	}
	
	public Dataset clone() {
		
		return cloneFields(new Dataset() {
			
			@Override
			public void initialize(double... params) {
				self.initialize(params);
			}});
	}
}

//copyright 2017, can not be used without authorization