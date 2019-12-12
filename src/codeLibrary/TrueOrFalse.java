package codeLibrary;

public class TrueOrFalse {
	static double correctNumbers = 0;
	static double totalNumbers = 0;
	public static void main(String[] args) {
		while(true) {
			boolean  isTrue = (int)(2*Math.random())==0;
			boolean guessTrue = Console.getInt()==0;
			if(isTrue == guessTrue) {
				correctNumbers++;
				Console.println("Your guess was correct");
			}else Console.println("Your guess was wrong");
			totalNumbers++;
			Console.println(correctNumbers/totalNumbers);
		}
	}
}
