package testers;

import java.util.ArrayList;
import java.util.LinkedList;

import codeLibrary.Console;
import codeLibrary.Tester;

public class ListTester{
	public static void main(String[] args) {
		ArrayList<Integer> a = new ArrayList<Integer>(10000000);
		LinkedList<Integer> b = new LinkedList<Integer>();
	    Tester.timerStart();
	    for(int i = 0; i<1000000; i++) {
	    	a.add(i);
	    }
	    Tester.timerStop();
	    Console.println(Tester.timerReset("ArrayList"));
	    Tester.timerStart();
	    for(int i = 0; i<1000000; i++) {
	    	b.add(i);
	    }
	    Tester.timerStop();
	    Console.println(Tester.timerReset("LinkedList"));
	}
}
