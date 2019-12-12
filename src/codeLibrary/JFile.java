package codeLibrary;
import java.io.*;
import java.util.*;
/**
 * @version 
 * 1.0
 * @author Yuelong Li
 * @see java.io.File
 *<b>
 * java.io.File
 *</b>
 */
/**
 *<b>
 *Description:
 *</b>
 *This class is made to create a interface of a certain file
 *@author Yuelong
 */
public class JFile{
	/**
	 * the fully qualified name of the file
	 */
	String fileName;
	/**
	 * In order to ensure the speed of operation, the are three types of File class provided --- Read & Write, Read and Write. To ensure the availability is the default type;
	 * @param RW Read & Write will input the entire file and then override it;
	 * @param R Read only buffers the file to String, however doesn't perform anything else; 
	 * @param W Write only appends to the existing file, and everything operation are first stored in a String Array, which later will be transferre to buffer and to the file
	 */	
	FileType fileType;
	
	/**
	 * @param FR FileReader, defines the file that the bufferedreader will readthrough
	 * @see FileReader
	 */
	FileReader FR;
	/**
	 * @param BR BufferedReader, Provides efficient methods for input
	 * @see BufferedReader
	 */
	BufferedReader BR;
	/**
	 * indicates whether the printing content follows the existing file
	 */
	boolean append=true;

	/**
	 * @param FW FileWriter Opens a file as the destination for the BufferedWriter's output
	 * @see FileWriter
	 */
	FileWriter FW;
	/**
	 * @param PW PrintWriter which provides methods like print and println, with which we can easily perform string operation
	 * @see PrintWriter
	 */
	PrintWriter PW;
	
	/**
	 * The String arraylist used to store the whole file temprarily, upon which the complex editions can be performed
	 * <div/> <b> &nbsp; &nbsp; &nbsp; &nbsp; Preserved character</b> | : line breaker,
	 */
	ArrayList <String> text=new ArrayList<String>();
	
	/**
	 *  The integer linked list which is used to store the position of every line starting points
	 */
	LinkedList <Integer> lineIndex = new LinkedList<Integer>();
	
	/**
	 * markers that help group sentences to words
	 */
	public String deviders=" ,";
	/**
	 * deviders that will be showned in the origninal file
	 */
	public String visibleDeviders=" ,";
	
	/**
	 *  totalIndex this counts the total word number simutaneosly. it represents the current location of the cursor
	 */
	int totalWords = 0;
	/**
	 * @param currentLine The line at which the editor is at, updated everytime a println or readln is performed
	 * @param currentIndex The Index at which the editor is at, updated everytime any edition is performed                                                                       
	 */
	public int currentLine=0;
	public int currentIndex=0;
	
	/**
	 * temperary words stored in a array
	 */
	String[] tempLine=new String[0];
	
	public JFile(String fileName,boolean append, FileType fileType,String deviders, String visibleDeviders)throws IOException{
		this.fileName=fileName;
		this.append = append;
		this.deviders = deviders;
		this.visibleDeviders = visibleDeviders;
		this.fileType = fileType;
		switch(fileType){
		case RW:{
			if(append){
				FR = new FileReader(fileName);
				BR = new BufferedReader(FR);
				inputAll();
				FR.close();
				BR.close();
			}
			break;
		}
		case R:{
			FR = new FileReader(fileName);
			BR = new BufferedReader(FR);
			newLine();
			break;
		}
		case W:{
			FW = new FileWriter(fileName,append);
			PW = new PrintWriter(FW);
			break;
		}
		default: break;
		}
	}
		
	/**
	 * @param fileName Name of the generated file
	 * @throws IOException 
	 */
	public JFile(String fileName,boolean append, FileType fileType)throws IOException{
		this.fileName=fileName;
		this.fileType = fileType;
		this.append = append;
		switch(fileType){
		case RW:{
			if(append){
				FR = new FileReader(fileName);
				BR = new BufferedReader(FR);
				inputAll();
				FR.close();
				BR.close();
			}
			
			break;
		}
		case R:{
			FR = new FileReader(fileName);
			BR = new BufferedReader(FR);
			newLine();
			break;
		}
		case W:{
			FW = new FileWriter(fileName,append);
			PW = new PrintWriter(FW);
			break;
		}
		default: break;
		}
	}
	
	/**
	 * This constructor opens file in FW form by default
	 * @param fileName
	 * @param append Depending on this parameter, the constructor decides wheter to take in the existing fille.
	 * @throws IOException
	 */
	public JFile(String fileName,boolean append)throws IOException{
		this.fileName=fileName;
		this.fileType = FileType.RW;
		this.append=append;
		if(append){
			FR = new FileReader(fileName);
			BR = new BufferedReader(FR);
			inputAll();
			FR.close();
			BR.close();
		}
	}
	/**
	 * This contructor opens an old file and sets the file type to FW by default
	 * @param fileName the name of the file 
	 * @throws IOException
	 */
	public JFile(String fileName){
		this.fileName=fileName;
		append = true;
		this.fileType = FileType.RW;
		try{
		FR = new FileReader(fileName);
		BR = new BufferedReader(FR);
		inputAll();
		FR.close();
		BR.close();
		}
		catch(IOException e){
			clear();
			Console.println("file not found, new file created");
			append = false;
		}
	}
	/**
	 * This contructor opens an old file and sets the file type to FW by default
	 * @param fileName the name of the file
	 * @param deviders the deviders with which the file interpretor devides string into words
	 * @param visibleDeviders the deividers that are visible to the user
	 * @throws IOException
	 */
	public JFile(String fileName,String deviders,String visibleDeviders)throws IOException{
		this.fileName=fileName;
		append = true;
		this.deviders=deviders;
		this.visibleDeviders=visibleDeviders;
		this.fileType = FileType.RW;
		try{
		FR = new FileReader(fileName);
		BR = new BufferedReader(FR);
		inputAll();
		FR.close();
		BR.close();
		}
		catch(FileNotFoundException e){
			clear();
			Console.println("file not found, new file created");
			append = false;
		}
	}
	
	//editor
	
	public void println(){
		switch(fileType){
		case RW:
			text.add("\\n");
			currentLine+=1;
			currentIndex=0;
			totalWords+=2;
			break;
		case W:
			PW.println();
			currentLine+=1;
			currentIndex=0;
			break;
		default: 
			Console.println("method: println "+"Current line: "+currentLine);
			Console.println("This print have been jumped since the "+fileType.name()+" type does not support this operation");
			Console.println();
		break;
		}
	}
	
	public void println(Object a){
		switch(fileType){
		case RW:
			text.add(String.valueOf(a));
			text.add("\\n");
			currentLine+=1;
			currentIndex=0;
			totalWords+=2;
			updateLine();
			break;
		case W:
			PW.println(a);
			currentLine+=1;
			currentIndex=0;
			break;
		default: 
			Console.println("method: println "+"Current line: "+currentLine);
			Console.println("This print have been jumped since the "+fileType.name()+" type does not support this operation");
			Console.println();
			break;
		}
	}

	public void println(Object[] a){
		switch(fileType){
		case RW:
			text.add(Arrays.toString(a));
			text.add("\\n");
			currentLine++;
			currentIndex=0;
			totalWords+=2;
			updateLine();
			break;
		case W:
			PW.println(a);
			currentLine++;
			currentIndex=0;
			break;
		default: 
			Console.println("method: println "+"Current line: "+currentLine);
			Console.println("This print have been jumped since the "+fileType.name()+" type does not support this operation");
			Console.println();
		break;
		}
	}
	
	//editors with object input (derived from editor)
	public void print(Object a){
		switch(fileType){
		case RW:
			text.add(String.valueOf(a));
			currentIndex+=1;
			totalWords++;
			break;
		case W:
			PW.print(a);
			currentIndex+=1;
			break;
		default: 
			Console.println("method: print "+"Current line: "+currentLine);
			Console.println("This print have been jumped since the "+fileType.name()+" type does not support this operation");
			Console.println();
			break;
		}
	}

	public void print(Object[] a){
		switch(fileType){
		case RW:
			text.add(Arrays.toString(a));
			currentIndex+=1;
			totalWords++;
			break;
		case W:
			PW.print(a);
			currentIndex+=1;
			break;
		default: 
			Console.println("method: print "+"Current line: "+currentLine+", Current index: "+currentIndex);
			Console.println("This print have been jumped since the "+fileType.name()+" type does not support this operation");
			Console.println();
		break;
		}
	}

	public void println(int Line, Object a){
		switch(fileType){
		case RW:
			int index = getIndex(Line);
			text.add(index,String.valueOf(a));
			text.add(index+1,"\\n");
			currentLine++;
			totalWords+=2;
			updateLine(Line);
			currentIndex=0;
			break;
		case W:
			println(a);
			currentLine++;
			currentIndex = 0;
			Console.println("method: println "+"Current line: "+currentLine);
			Console.println("This line is misplaced, since the "+fileType.name()+" type is not sutible for this operation");
			Console.println();
			break;
		default:
			Console.println("method println "+"Current line: "+currentLine);
			Console.println("This print have been jumped since the "+fileType.name()+" type does not support this operation");
			Console.println();
			break;
		}
	}

	public void println(int Line, Object[] a){
		println(Line, Arrays.toString(a));
	}

	public void print(int Line, int Index, Object a){
		switch(fileType){
		case RW:
			text.add(Index+getIndex(Line),String.valueOf(a));
			if(Line==currentLine)currentIndex++;
			totalWords++;
			updateInsertedWord(Line);
			break;
		case W:
			print(a);
			currentIndex++;
			Console.println("method: print "+"Current line: "+currentLine);
			Console.println("This word is misplaced, since the "+fileType.name()+" type is not sutible for this operation");
			Console.println();
			break;
		default: 
			Console.println("method: print "+"Current line: "+currentLine);
			Console.println("This print have been jumped since the "+fileType.name()+" type does not support this operation");
			Console.println();
			break;
		}
	}

	public void print(int Line, int Index, Object[] a){
		print(Line,Index,Arrays.toString(a));
	}
	
	public void setln(int Line, Object a){
		switch(fileType){
		case RW:
			int index = deleteln(Line);
			text.add(index,String.valueOf(a));
			text.add(index+1,"\\n");
			break;
		default:
			Console.println("method println "+"Current line: "+currentLine);
			Console.println("This print have been jumped since the "+fileType.name()+" type does not support this operation");
			Console.println();
			break;
		}
	}

	public void setln(int Line, Object[] a){
		switch(fileType){
		case RW:
			int index = deleteln(Line);
			text.add(index, Arrays.toString(a));
			text.add(index+1,"\\n");
			break;
		default: 
			Console.println("method: println "+"Current line: "+currentLine);
			Console.println("This print have been jumped since the "+fileType.name()+" type does not support this operation");
			Console.println();
			break;
		}
	}

	public void set(int Line, int Index, Object a){
		switch(fileType){
		case RW:
			text.set(Index+getIndex(Line),String.valueOf(a));
			break;
		default: 
			Console.println("method: print "+"Current line: "+currentLine);
			Console.println("This print have been jumped since the "+fileType.name()+" type does not support this operation");
			Console.println();
			break;
		}
	}

	public void set(int Line, int Index, Object[] a){
		switch(fileType){
		case RW:
			text.set(Index+getIndex(Line),Arrays.toString(a));
			break;
		default: 
			Console.println("method: print "+"Current line: "+currentLine);
			Console.println("This print have been jumped since the "+fileType.name()+" type does not support this operation");
			Console.println();
			break;
		}
	}
	/**
	 * 
	 * @param Line
	 * @return The index of the first character of the deleted line
	 */
	public int deleteln(int Line){
		int Index = 0;
		switch(fileType){
		case RW:
			 Index = getIndex(Line);
			for(int i = Index; i<getIndex(Line+1); i++)
			text.remove(i);
			updateDeleteLine(Line);
			currentLine--;
			break;
		default: 
			Console.println("method: delete "+"Current line: "+currentLine);
			Console.println("This print have been jumped since the "+fileType.name()+" type does not support this operation");
			Console.println();
			break;
		}
		return Index;
	}
	/**
	 * Deletes the preceding line
	 */
	public void deleteln(){
		switch(fileType){
		case RW:
			int StartIndex=getIndex(currentLine)-1;
			int EndIndex= getIndex(currentLine)+currentIndex;
			for(int i = StartIndex;i<=EndIndex;i++)
			text.remove(i);
			currentLine--;
			lineIndex.removeLast();
			break;
		default: 
			Console.println("method: delete "+"Current line: "+currentLine);
			Console.println("This print have been jumped since the "+fileType.name()+" type does not support this operation");
			Console.println();
			break;
		}
	}
	public void delete(int Line,int Index){
		switch(fileType){
		case RW:
			if(text.remove(getIndex(Line)+Index).equals("\n")){
				if(Line == currentLine)Index=lineIndex.get(lineIndex.size()-1)-lineIndex.get(lineIndex.size()-2)-1;
				currentLine--;
				updateDeleteLine(Line);
			}else updateDeleteWord(Line);
			if(Line==currentLine)currentIndex--;
			break;
		default: 
			Console.println("method: delete "+"Current line: "+currentLine);
			Console.println("This print have been jumped since the "+fileType.name()+" type does not support this operation");
			Console.println();
			break;
		}
	}
	public void delete(){
		switch(fileType){
		case RW:
			delete(currentLine, currentIndex-1);
			currentIndex--;
			break;
		default: 
			Console.println("method: delete "+"Current line: "+currentLine);
			Console.println("This method has been skipped since the "+fileType.name()+" type does not support this operation");
			Console.println();
			break;
		}
	}
	public void clear(){
		switch(fileType){
		case RW:
			text.clear();
			currentLine = 0;
			currentIndex=0;
			totalWords=0;
			lineIndex.clear();
			updateLine();
			break;
		default: 
			Console.println("method: clear "+"Current line: "+currentLine);
			Console.println("This print have been jumped since the "+fileType.name()+" type does not support this operation");
			Console.println();
			break;
		}
	}
	public String read(){
		switch(fileType){
		case RW:
			currentIndex++;
			String tempWord;
			try{
				tempWord = text.get(getIndex(currentLine)+currentIndex-1);
				if(tempWord.equals("\\n")){
					currentLine++;
					currentIndex=0;
				}
			}catch(IndexOutOfBoundsException e){
				tempWord=null;
			}
			return tempWord;
		case R:
			try{
			if(currentIndex>=tempLine.length){
				currentLine++;
				currentIndex=0;
				try{
				newLine();
				}catch(IOException e){
					return null;
				}
				return "\\n";
			}
			}catch(NullPointerException e){
				return null;
			}
			currentIndex++;
			try{
				return tempLine[currentIndex-1]; 
			}catch(IndexOutOfBoundsException e){
				return null;
			}
		default: 
			Console.println("Current method read is not available in file type:");
			Console.print(fileType.name());
			break;
		}
		return null;
	}
	/**
	 * this method only returns the rest of the content in the current line 
	 * or starts a new line if the old one has come to an end
	 * @return
	 * @throws IOException
	 */
	public String readln()throws IOException{
		switch(fileType){
		case RW:
			
			StringBuilder returnLine=new StringBuilder();
			String word;
			try{
			int rollingIndex = getIndex(currentLine)+currentIndex;
			while((word=text.get(rollingIndex))!=null&&!word.equals("\\n")){
				returnLine.append(word);
				rollingIndex++;
			};
			}catch(IndexOutOfBoundsException e){
				return null;
			}
			currentLine++;
			currentIndex=0;
			return returnLine.toString();
			
		case R:
			String line="";
			try{
			if(currentIndex>=tempLine.length)
				line = BR.readLine();
			else{
				for(String pass:tempLine){
					if(pass.equals("\\n"))break;
					line+=pass;
				}
			}
			}catch(NullPointerException e){
				return null;
			}
			currentLine++;
			tempLine=new String[0];
			currentIndex=0;
			return line;
		default: 
			Console.println("Current method readln is not available in file type:");
			Console.print(fileType.name());
			break;
		}
		return null;
	}
	public char readChar()throws IOException{
		switch(fileType){
		case R: return (char) BR.read();
		default: 
			Console.println("Current method readChar is not applicable in file type:");
			Console.print(fileType.name());
			break;
		}
		return 0;
	}
	public String read(int Line, int Index){
		switch(fileType){
		case RW:
			String tempWord;
			try{
				tempWord = text.get(getIndex(Line)+Index);
			}catch(IndexOutOfBoundsException e){
				tempWord=null;
			}
			return tempWord;
		default: 
			Console.println("Current method read is not available in file type:");
			Console.print(fileType.name());
			break;
		}
		return null;
	}
	public String readln(int Line){
		switch(fileType){
		case RW:
			String returnLine="";
			String word;
			int rollingIndex = getIndex(Line);
			while((word=text.get(rollingIndex))!=null&&!word.equals("\\n")){
				returnLine+=word;
				rollingIndex++;
			};
			if(word==null)return null;
			else return returnLine;
		default: 
			Console.println("Current method readln is not available in file type:");
			Console.print(fileType.name());
			break;
		}
		return null;
	}
	
	/**
	 * Reads in all the words and skips all the line breakers
	 * @return
	 */
	public String readWord(){
		String input = read();
		if (input != null)
			while (input.equals("\\n")) {
				if ((input = read()) == null)
					break;
			}
		return input;
	}
	/**
	 * keeps skipping characters until the input matches key
	 * @param key the methodd returns when hits the key
	 */
	public void readTill(String key){
		String input;
		while((input=read())!=null&&!input.equals(key));
	}
	/**
	 * This method searches across the file for this particular vocab input
	 * @param search specifies the content that the user is searching
	 * @return matchedPosition all the positions at which the string occurs in the file
	 */
	public ArrayList<int[]> search(String search){
		switch(fileType){
		case RW:
			ArrayList<int[]> matchedPosition = new ArrayList<int[]>();
			int index = 0;
			for(String examine:text){
				if(search.equals(examine))matchedPosition.add(getPosition(index));
				index++;
			}
			return matchedPosition;
		default: 
			Console.println("method: search "+"Current line: "+currentLine);
			Console.println("This method has been skipped since the "+fileType.name()+" type does not support this operation");
			Console.println();
			return null;
		}
	}
	public ArrayList<int[]> looseSearch(String search){
		switch(fileType){
		case RW:
			ArrayList<int[]> matchedPosition = new ArrayList<int[]>();
			int index = 0;
			int[] location = new int[3];
			for(String examine:text){
				int leastIndex=0;
				if((leastIndex=examine.indexOf(search))>=0){
					location[0]=getPosition(index)[0];
					location[1]=getPosition(index)[1];
					location[2]=leastIndex;
					matchedPosition.add(location.clone());
					while((leastIndex = examine.indexOf(search,leastIndex+1))>=0){
						location[2]=leastIndex;
						matchedPosition.add(location.clone());
					}
				}
				index++;
			}
			return matchedPosition;
		default: 
			Console.println("method: search "+"Current line: "+currentLine);
			Console.println("This method has been skipped since the "+fileType.name()+" type does not support this operation");
			Console.println();
			return null;
		}
	}
	/**
	 * Returns the position of a certain index in terms of line index and word index
	 * @param index
	 * @return location of the word
	 */
	public int[] getPosition(int index){
		int tempLineIndex=0;
		if(index<lineIndex.getLast())
		for(int i = 0; ; i++){
			tempLineIndex=i;
			if(index<lineIndex.get(i+1)){
				break;
			}
		}
		else return null;
		return new int[]{tempLineIndex,index-lineIndex.get(tempLineIndex)};
	}
	/**
	 * this method is only active in the FR mode, it takes in all the information and stores it in to words
	 * @throws IOException
	 */
	private void inputAll() throws IOException{
		String pass;
		String[] line;
		updateLine();
		while((pass = BR.readLine())!=null){
			line=Conversion.toStringList(pass+"|\\n|","|"+deviders,visibleDeviders);
			text.addAll(Arrays.asList(line));
			currentLine++;
			totalWords=text.size();
			updateLine();
		}
	}
	
	/**
	 * marks down the index of the first word of the new line
	 */
	private void updateLine(){
		lineIndex.add(Integer.valueOf(totalWords));
	}
	/**
	 * marks down the index of the first word of the specified line;
	 * This method also takes in the current index parameter in order to get the
	 * length of the currentSentence
	 * @param Line the index of the Line, which starts with zero and 
	 * represents the value of line number minus one
	 */
	private void updateLine(int Line){
		try{
		int precedingWords=lineIndex.get(Line);
		lineIndex.add(Line, precedingWords);
		if(Line<lineIndex.size())
		for(int i = Line+1;i<lineIndex.size();i++){
			int tempTotalIndex = lineIndex.get(i);
			tempTotalIndex+=2;
			lineIndex.set(i, Integer.valueOf(tempTotalIndex));
		}
		}
		catch(IndexOutOfBoundsException e){
			
		}
	}
	private void updateDeleteLine(int Line){
		try{
		int difference = lineIndex.get(Line+1)-lineIndex.get(Line);
		if(Line<lineIndex.size())
		for(int i = Line+1; i<lineIndex.size();i++){
			int tempTotalIndex = lineIndex.get(i);
			tempTotalIndex-=difference;
			lineIndex.set(Line, tempTotalIndex);
		}
		}
		catch(IndexOutOfBoundsException e){
			
		}
	}
	/**
	 * adds one word count to the following lines
	 * @param Line the index of the Line, which starts with zero and 
	 * represents the value of line number minus one
	 */
	private void updateInsertedWord(int Line){
		try{
		if(Line<lineIndex.size())
		for(int i = Line+1;i<lineIndex.size();i++){
			int tempTotalIndex = lineIndex.get(i);
			tempTotalIndex+=1;
			lineIndex.set(i, Integer.valueOf(tempTotalIndex));
		}
		}
		catch(IndexOutOfBoundsException e){
			
		}
	}
	private void updateDeleteWord(int Line){
		try{
		for(int i = Line+1; i<lineIndex.size();i++){
			int tempTotalIndex = lineIndex.get(i);
			tempTotalIndex--;
			lineIndex.set(Line, tempTotalIndex);
		}
		lineIndex.remove(Line);
		}
		catch(IndexOutOfBoundsException e){
			
		}
	}
	/**
	 * Reads the file into a word list   
	 * @throws IOException
	 */
	private void newLine()throws IOException{
		String pass;
		if((pass=BR.readLine())!=null) tempLine=Conversion.toStringList(pass,deviders,visibleDeviders);
		else tempLine = null;
	}
	
	/**
	 * Finds the index of the line in text
	 * @see JFile.text
	 * @param Line The number of preceding lines
	 * @return The index at which this line starts in text
	 */
	private int getIndex(int Line){
		return lineIndex.get(Line);
	}
	protected void enterText(){
		try {
			FW = new FileWriter(fileName,append);
		} catch (IOException e) {
			e.printStackTrace();
		}
		PW = new PrintWriter(FW);
		for(String pass:text){
			if(pass.equals("\\n"))
			PW.println();
			else PW.print(pass);
		}
		text.clear();
	}
	protected void enterText(String gapFiller){
		try {
			FW = new FileWriter(fileName,append);
		} catch (IOException e) {
			e.printStackTrace();
		}
		PW = new PrintWriter(FW);
		if(text.size()>0)
		if(text.get(0).equals("\\n"))PW.println();
		else PW.print(text.get(0));
		String pass;
		for(int i=1;i<text.size();i++){
			pass = text.get(i);
			if(pass.equals("\\n"))PW.println();
			else{
				if((!text.get(i-1).equals("\\n"))&&visibleDeviders.indexOf(pass)<0&&visibleDeviders.indexOf(text.get(i-1))<0){
					PW.print(gapFiller);
					
				}
				
				PW.print(pass);
			}
		}
	} 
	protected void close()throws IOException{
		switch(fileType){
		case RW:
			enterText();
			PW.close();
			FW.close();
			break;
		case R: 
			BR.close();
			break;
		case W:
			PW.close();
			break;
		}
	}
	

	protected void close(String gapFiller)throws IOException{
	switch(fileType){
	case RW:
		enterText(gapFiller);
		PW.close();
		FW.close();
		break;
	case R: 
		BR.close();
		break;
	case W:
		PW.close();
		break;
	}
	}
	/**
	 * puts spacing between words automatically, and shuts down the flow
	 */
	public void finalize() throws Throwable{
		super.finalize();
	}
}
enum FileType{
	RW,R,W
}