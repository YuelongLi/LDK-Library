package codeLibrary;

public class CodeMaker {
	static String[] list;
	public static void main(String[] args){
		
	}
	public void makeStringList(){
		list = Console.getList(',');
		Console.print("String[] CardType = {\""+list[0]+"\"");
		for(int i = 1; i<list.length;i++){
			Console.print(",\""+list[i]+"\"");
		}
		Console.print("};");
	}
	public void makeStringList(String[] list){
		Console.print("String[] CardType = {\""+list[0]+"\"");
		for(int i = 1; i<list.length;i++){
			Console.print(",\""+list[i]+"\"");
		}
		Console.print("};");
	}
	public void makePrinter(String looper1, String[] list, String looper2){
		Console.print("System.out.println{\"");
		for(int i = 0; i<list.length; i++)
		Console.print(looper1+list[i]+looper2);
		Console.print("};");
	}
}
