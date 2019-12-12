package utils;

import java.util.ArrayList;
import java.util.List;

public class Curve extends Dataset{
	List<double[]> curve = new ArrayList<double[]>();
	@Override
	public void initialize(double... params) {
		
	}

	public List<double[]> getCurve() {
		return curve;
	}
}
