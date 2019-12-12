package testers;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import codeLibrary.*;

public class DataConverter {
	public static void main(String[] args) {
		try {
			CSV velcro = new CSV("wax.csv");
			ArrayList<ArrayList<String>>chart = velcro.getChart();
			int i = 0;
			while(!chart.get(0).get(i).equals("Run #64"))i++;
			for(;i<chart.get(0).size();i+=6) {
				System.out.println(chart.get(2).get(i+3)+"	"+chart.get(3).get(i+5));
				if(chart.get(0).get(i).equals("Run #59"))break;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
