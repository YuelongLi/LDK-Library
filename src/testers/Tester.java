package testers;

import codeLibrary.Console;

/**
 * Tester class with timers
 * @author Yuelong Li
 *
 */
public class Tester {
	
	public static void main(String[] args) {
		long a = System.nanoTime();
		  long sec = 0;
		  for(;;){
		    long b = System.nanoTime();
		    if((b-a)*3/2000000000>sec){
		      sec = (b-a)*3/2000000000;
		     System.out.println(sec);
		    }
		  }
	}
	static long start, end;
	public static void timerStart(){
		start = System.nanoTime();
	}
	public static void timerStop(){
		end = System.nanoTime();
	}
	public static String timerReset(String object){
		long relativeDuration = end-start;
		timerStart();
		timerStop();
		long error = end-start;
		long duration = relativeDuration-error;
		Console.println("testing object: "+object);
		Console.println("Consumed time: "+duration+"*10^-9 seconds");
		end=0;
		start=0;
		return getScientific((double)duration/1000000000);
	}
	
	public static String getScientific(double num){
		int log=0;
		if(num>1){
			log = (int)Math.log10(num);
		}
		if(num<1) log = (int)Math.floor(Math.log10(num));
		num/=Math.pow(10, log);
		return num + " * 10^" +log;
	};
}