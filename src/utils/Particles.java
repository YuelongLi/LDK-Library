package utils;

import java.util.ArrayList;
import java.util.List;

public class Particles extends Dataset{

	@Override
	public void initialize(double... params) {
	}
	
	/**
	 * List of particles (dots) to be printed
	 */
	List<double[]> particles = new ArrayList<double[]>();
	
	/**
	 * Radius of individual particles (not related to scales)
	 */
	public double radius = 0.25;
	
	public void addParticles(List<double[]> particles) {
		this.particles.addAll(particles);
	}
	
	/**
	 * Setter for radius
	 * @param newRadius
	 */
	public void setRadius(double newRadius) {
		this.radius = newRadius;
	}
	
	/**
	 * Getter for radius
	 * @return radius
	 */
	public double getRadius() {
		return radius;
	}
	
	public List<double[]> getParticles() {
		return particles;
	}
}
