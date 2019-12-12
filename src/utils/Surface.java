package utils;

import java.util.ArrayList;
import java.util.List;

public class Surface extends Dataset{

	protected List<ArrayList<double[]>>surface;
	
	public List<ArrayList<double[]>> getSurface() {
		return surface;
	}
	
	@Override
	public void initialize(double... params) {
		
	}
	
	protected Texture texture = new Texture();
	
	public void setTexture(Texture texture) {
		this.texture = texture;
	}
	
	public Texture getTexture() {
		return texture;
	}
}