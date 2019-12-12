package codeLibrary;
import java.io.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Yuelong Li
 * @version 1.0
 */
public class CSV extends File{
	private static final long serialVersionUID = 1L;
	ArrayList<ArrayList<String>> chart = new ArrayList<ArrayList<String>>();
	String[][] matrix;
	
	static String separator = ",";
	
	public static void setSeparator(String Separator){
		separator = Separator;
	}
	
	/**
	 * @see java.io.File
	 * @param parent
	 * @param child
	 * @param append <I> If you choose false or leave it default,
	 * 			the old csv file will be overriden and the initial chart will be empty </I>
	 */
	public CSV(File parent, String child, boolean... append) throws FileNotFoundException{
		super(parent, child);
		initializeChart(append);
	}
	
	/**
	 * @see java.io.File
	 * @param name
	 * @param append <I> If you choose false or leave it default,
	 * 			the old csv file will be overriden and the initial chart will be empty</I>
	 */
	public CSV(String name, boolean... append) throws FileNotFoundException{
		super(name);
		initializeChart(append);
	}
	
	/**
	 * @see java.io.File
	 * @param uri
	 * @param append <I> If you choose false or leave it default,
	 * 			the old csv file will be overriden and the initial chart will be empty </I>
	 */
	public CSV(URI uri, boolean... append) throws FileNotFoundException{
		super(uri);
		initializeChart(append);
	}
	
	private void initializeChart(boolean[] append) throws FileNotFoundException{
		try{
			
		if((append.length==0||append.length!=0&&append[0])&&exists()){
			try {
				FileReader in = new FileReader(this);
				BufferedReader reader = new BufferedReader(in);
				String line;
				while((line = reader.readLine())!=null){
					chart.add(new ArrayList<String>(Arrays.asList(line.split(separator))));
				}
				reader.close();
				in.close();
			} catch (FileNotFoundException e) {
				Console.println("no original file found, new file created");
			}	
		}
		} catch (IOException e1) {
			throw new FileNotFoundException();
		}
	}
	
	/**
	 * 
	 * @return the chart that describes to the csv file;
	 * 	every change made to the chart will be recorded. The order
	 *  of indexes are row entry and then column entry.
	 */
	public ArrayList<ArrayList<String>> getChart(){
		return chart;
	}
	
	/**
	 * Assigns a new chart for the original .csv file and overrides the old one
	 * @param chart the new chart to be assigned
	 */
	public void setChart(ArrayList<ArrayList<String>> chart){
		this.chart = chart;
	}
	
	/**
	 * Assigns a new 2D array map for the original .csv file and overrides the old one
	 * @param matrix the new matrix to be assigned
	 */
	public void setMatrix(String[][] matrix){
		this.matrix=matrix;
	}
	/**
	 * Outputs the csv format file in its current state, 
	 * the changes to chart will not be finalized until close() method is called
	 * @see java.io.FileWriter
	 * @see java.io.PrintWriter
	 */
	public void flushChart(){
		try {
			FileWriter out = new FileWriter(this,false);
			PrintWriter writer = new PrintWriter(out);
			for(ArrayList<String> row:chart){
				for(String item:row){
					writer.print(((item!=null)?item:"")+separator);
				}
				writer.println();
			}
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void flushMatrix(){
		try {
			FileWriter out = new FileWriter(this,false);
			PrintWriter writer = new PrintWriter(out);
			for(String[] row:matrix){
				for(String item:row){
					writer.print(((item!=null)?item:"")+separator);
				}
				writer.println();
			}
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
