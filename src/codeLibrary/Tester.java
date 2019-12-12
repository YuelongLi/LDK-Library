package codeLibrary;

/**
 * Tester class with timers
 * @author Yuelong Li
 *
 */
public class Tester {
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
/*
 * Speed Reader
 * File avc = new File("avc.txt"," ,."," ,.");
		String input;
		avc.currentIndex=0;
		avc.currentLine=10;
		a:{
		while((input=avc.read())!=null){
			while(input.equals("\\n")){
				Console.println();
				input=avc.read();
				if(input==null){
					break a;
				}
			}
			Console.print(input);
			try{
			if(input.equals(".")||input.equals(",")){
				Console.println();
				Thread.sleep(300);
			}
			else{
				Thread.sleep(100);
			}
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}
 */
/*
 * XML test
 * XML xmlTester = new XML("xmlTest","root",false);
		timerStart();
		for(int c = 0; c<1000; c++){
			XML.XObject a= xmlTester.newXObject("level"+String.valueOf(0));
			for(int i = 1; i<100; i++){
				XML.XObject b = xmlTester.newXObject("level"+String.valueOf(i));
				b.add(a);
				a=b;
			}
			XML.XObject d= xmlTester.newXObject("Index"+String.valueOf(c));
			d.add(a);
			xmlTester.root.add(d);
			Console.println(c);
		}
		xmlTester.parseOut();
 */

/*
 * Aloga insertion sort
 * for(;;){
				List <Integer> input = new ArrayList <Integer>(0);
				int[] list;
				String in;
				Console.println("Enter the numbers, enter $ to start sorting");
				try{
					for(;;){
						in = Console.getLine();
						if(in.equals("$")) break;
						if(in.equals("")) continue;
						input.add(Integer.valueOf(in));
					}
				}catch(Exception e){
				}
				list = new int[input.size()];
				for(int i=0; i<input.size(); i++){
					list[i]=input.get(i);
				}
				int list2[] = list.clone();
				timerStart();
				list = Aloga.insertionSort(list);
				timerStop();
				timerReset("Insertion sort time: ");
				Console.println("Heap Sort result: "+ Arrays.toString(list));
				timerStart();
				list2 = ArrayTester_Li.alternateOrdered(list2);
				timerStop();
				timerReset("Li sort time: ");
				Console.println("Heap Sort result: "+ Arrays.toString(list2));
				
			}
*/

/*
 * File Printer Test:
 * List<String> a = new ArrayList(0);
		a.add("0");
		a.add("1");
		a.add("2");
		a.add("3");
		a.add("4");
		a.add(2,"inserted");
		Console.println(a);
		a.remove(2);
		Console.println(a);
		timerStart();
		File test = new File("test.txt");
		timerStop();
		timerReset("intializing File");
		
		for(int i=0;i<100;i++){
			Console.println("Test round "+(i+1));
			timerStart();
			test.print("i = ");
			timerStop();
			timerReset("print method in File");
			
			timerStart();
			test.println(i+";");
			timerStop();
			timerReset("println method in File");
			
			Console.println();
		}
		timerStart();
		test.close();
		timerStop();
		timerReset("close method in File");
		
	}
*/
/*
 * File search test
 * File a = new File("avc.txt"," ,.",",.");
		a.currentIndex=0;
		a.currentLine=0;
		try{
		timerStart();
		for(int[] pass:a.search("a"));
		timerStop();
		timerReset("File.println");
		}
		finally{
			a.close(" ");
		}
		a.close(" ");
*/
