package codeLibrary;

import java.io.IOException;
import java.util.*;

/**
 * XML parser designed according to the principle of OOP programming
 * 
 * @author Yuelong Li
 * @version 1.0
 */
public class XML {
	String xmlName;

	/**
	 * the file with which the xml reads in all the stored xml file
	 */
	JFile parseIn;

	/**
	 * the file writer that out puts all the xml
	 */
	JFile parseOut;

	/**
	 * The root xml object.
	 */
	public XObject root;

	/**
	 * the directory where all the xml items are stored and organized
	 */
	public Directory directory = new Directory(3);

	/**
	 * indicates the level of the current input tag, works exclusivly with parse
	 * in method
	 */
	private int inputLevel = 0;

	/**
	 * Constructor for class XML. This constructor detects wheteher there is any
	 * need to create a new method If there is an old xml file existing, all the
	 * xml io are pointed to it, if not, the constructor creates a new one
	 * 
	 * @param xmlName
	 *            the name of the xml file
	 * @throws IOException
	 */
	public XML(String... xmlName) throws IOException,IllegalArgumentException{
		this.xmlName = xmlName[0];
		this.parseIn = new JFile(xmlName[0] + ".xml", "<>:=	 -!?\"/", "\"<>:-!/?");
		if (parseIn.append) {
			parseIn();
		}
		try{
			if (root == null)
				initializeRoot(xmlName[1]);
		}catch(IndexOutOfBoundsException e){
			throw new IllegalArgumentException("No root class name specified and can't find "+xmlName[0]+".xml");
		}
	}

	/**
	 * Constructor for class XML and decides whether to wipe out the old
	 * directory according to the append parameter.
	 * 
	 * @param xmlName
	 *            the name of the xml file
	 * @param append
	 *            Tells the method whether, if false, to wipe out the existing
	 *            xml
	 * @throws IOException
	 */
	public XML(String xmlName, String rootName, boolean append) throws IOException {
		this.xmlName = xmlName;
		if (append) {
			this.parseIn = new JFile(xmlName + ".xml", false, FileType.R, "<>:=	 -!?\"/", "\"<>:-!?/");
			parseIn();
			if (root == null)
				initializeRoot(rootName);
		} else {
			initializeRoot(rootName);
		}
	}

	public void parseIn() throws IOException {
		String input;
		// indicates the level of the current parent object
		int parentLevel = 0;
		parseIn.currentIndex = 0;
		parseIn.currentLine = 0;
		object currentObject = null;
		XObject parentObject = null;
		while ((input = read()) != null) {
			if (!input.equals("")) {
				currentObject = typeReader(input);
				if (currentObject.isXObject) {
					root = (XObject) currentObject;
					root.isRoot = true;
					directory.extendChildrenBundle(root);
					parentObject = root;
					break;
				}
			}
		}
		while ((input = read()) != null) {
			if (!input.equals("")) {
				currentObject = typeReader(input);
				if (parentLevel == inputLevel) {
					parentObject = parentObject.parent;
					parentLevel -= 1;
				} else {
					parentObject.add(currentObject);
					if ((inputLevel - parentLevel) == 2) {
						parentLevel = inputLevel - 1;
						parentObject = (XObject) currentObject;
					}
				}
			}
		}
		parseIn.BR.close();
		parseIn.FR.close();
	}

	public void initializeRoot(String name) {
		root = new XObject(name);
		root.isRoot = true;
		directory.extendChildrenBundle(root);
	}

	// creates an inner xml
	public object newContent(String name) {
		return new Content(name, "");
	}

	public object newContent(String name, String content) {
		return new Content(name, content);
	}

	// creates an xml object
	public XObject newXObject(String name) {
		return new XObject(name);
	}

	public XObject newXObject(String name, HashMap<String, Object> attributes) {
		return new XObject(name, attributes);
	}

	public void parseOut() {
		try {
			parseOut = new JFile(xmlName + ".xml", false, FileType.W);
			parseOut.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			root.print();
			parseOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void finalize() throws Throwable {
		super.finalize();
		parseOut();
		parseOut.finalize();
	}

	protected object typeReader(String firstWord) {
		String input = "";
		if (firstWord.equals("<")) {
			input = read();
			switch (input) {
			case "?":
				StringBuilder processInstruction = new StringBuilder();
				String lastString = null;
				while (true) {
					if ((input = read()).equals(">") && lastString.equals("?")) {
						break;
					}
					;
					processInstruction.append(input);
					lastString = input;
				}
				String instructionString = processInstruction.toString();
				return new ProcessInstruction(instructionString.substring(0, instructionString.length() - 1));
			case "!":
				StringBuilder comment = new StringBuilder();
				String lastString_1 = "";
				String lastString_2 = "";
				String[] inputs = Conversion.toStringList(readChunk(), "-", "-");
				int i = 0;
				for(;!inputs[i].equals("-");i++);
				i++;
				for(;!inputs[i].equals("-");i++);
				i++;
				for(;;i++) {
					try{
						if (lastString_1.equals("-") && lastString_2.equals("-")&&i==(inputs.length)) {
							break;
						};
						input=inputs[i];
						comment.append(input);
						lastString_2 = lastString_1;
						lastString_1 = input;
					}catch(ArrayIndexOutOfBoundsException e){
						inputs=Conversion.toStringList(readChunk(), "-", "-");
						i=0;
					}
				}
				parseIn.read();
				String commentString = comment.toString().substring(0, comment.length() - 2);
				return new Comment(String.valueOf(commentString), commentString);
			case "/":
				inputLevel -= 1;
				while (!read().equals(">"))
					;
				break;
			default:
				StringBuilder name = new StringBuilder();
				name.append(input);
				XObject returnObject = new XObject(input);
				String attributeName = null;
				while (true) {
					if ((input = read()).equals("/")) {
						while (!read().equals(">"));
						break;
					}
					if ((input).equals(">")) {
						inputLevel += 1;
						break;
					}
					if (input.equals("\"")) {
						StringBuilder AVB = new StringBuilder();
						String pass;
						while (!(pass = read()).equals("\"")) {
							AVB.append(pass);
						}
						returnObject.putAttribute(attributeName, AVB.toString());
					}
					attributeName = input;
				}
				returnObject.isXObject = true;
				return returnObject;
			}
		} else {
			StringBuilder content = new StringBuilder();
			content.append(firstWord);
			while (!(input = read()).equals("<")) {
				content.append(input);
			}
			parseIn.currentIndex -= 1;
			String contentString = content.toString();
			return new Content(String.valueOf(contentString), contentString);
		}
		return null;
	}

	private String read() {
		String input = parseIn.read();
		if (input != null)
			while (input.equals("\\n")) {
				if ((input = parseIn.read()) == null)
					break;
			}
		return input;
	}
	
	//reads untill meets a ">", converts "\\n" to "\n"
	private String readChunk(){
		StringBuilder chunk = new StringBuilder();
		String input;
		while((input = parseIn.read())!=null&&!input.equals(">")){
			chunk.append(" ");
			if(input.equals("\\n")){
				chunk.append("\n");
			}else{
				chunk.append(input);
			}
		}
		parseIn.currentIndex--;
		return chunk.toString();
	}

	/**
	 * Cooperates with a string builder to make fully qualified name out of the
	 * index and the input
	 * 
	 * @param index
	 *            the index in input of the last string to which the fully
	 *            qualified name will append to
	 * @param input
	 *            a string array that implies the whole hiricacy of the xml
	 *            object
	 */
	protected String toFQName(int index, String[] input) {
		StringBuilder FQBuilder = new StringBuilder();
		FQBuilder.append(input[0]);
		for (int i = 1; i <= index; i++) {
			FQBuilder.append(".");
			FQBuilder.append(i);
		}
		return FQBuilder.toString();
	}

	/**
	 * this is the parent class of all xml elements including comments, content
	 * and xml objects
	 * 
	 * @version 1.0
	 * @author Yuelong
	 * @parsing it parses the informations out by a series of call back methods
	 *          which prints the whole directory out
	 */
	public class object {
		/**
		 * the name of the object itself
		 */
		public String name = null;

		/**
		 * fully qualified name is a name that will be unique across the xml
		 * objects which indicates the hericacy of the object it self
		 */
		public String fullyQualifiedName;

		/**
		 * the depth of the object in the tree
		 */
		public int level = 0;

		/**
		 * the parent xml object of this object, the root object's parent object
		 * is null, which is the default value
		 */
		public XObject parent = null;

		/**
		 * the order at which the object is added to its children bundle
		 */
		public int bundleIndex = 0;
		/**
		 * indicates whether the current object's type is XObject
		 */
		public boolean isXObject = false;

		public object() {
		}

		/**
		 * this method can only be called onec---when the object itself is added
		 * to a parent object
		 */
		protected void updateInfo() {
			this.level = parent.level + 1;
			this.fullyQualifiedName = new StringBuilder().append(parent.fullyQualifiedName).append(".")
					.append(fullyQualifiedName).toString();
		}

		protected void setParent(XObject ParentObject) {
			parent = ParentObject;
		}

		/**
		 * recursive method that finds the object with the "xmlName"
		 * 
		 * @param xmlName
		 *            the fully qualified name of the target object stored in an
		 *            array form
		 * @return the target object
		 */
		protected object recursiveGet(String[] xmlName, int level) {
			return this;
		}

		protected object compulsiveGet(String[] xmlName, int level) {
			return this;
		}

		/**
		 * recursive method that expands all its children into the directory by
		 * it self
		 */
		protected void addAllChildren() {
		}

		protected void print() {
			parseOut.println();
		}

		public String toString() {
			return name;
		}
	}

	public class ProcessInstruction extends object {
		String instruction;

		public ProcessInstruction(String instruction) {
			super.name = "@ProcessInstruction";
			this.instruction = instruction;
		}

		protected void print() {
			parseOut.println("<?" + instruction + "?>");
			parseOut.println();
		};
	}

	public class Comment extends object {
		public String comment = "";

		public Comment(String name) {
			super();
			super.name = new StringBuilder().append("@Comment").append(name).toString();
		}

		public Comment(String name, String comment) {
			super();
			super.name = new StringBuilder().append("@Comment").append(name).toString();
			this.comment = comment;
		}

		public void setComment(String comment) {
			this.comment = comment;
		}

		public void appendComment(String comment) {
			this.comment += comment;
		}

		protected void print() {
			StringBuilder indent = new StringBuilder();
			for (int i = 0; i < level; i++) {
				indent.append("  ");
			}
			String indentation = indent.toString();
			parseOut.println(indentation + "<!--" + comment + "-->");
		}
	}

	/**
	 * Content class is a children class of object, it has no attributes or
	 * children objects, it stores any object as its content
	 * 
	 * @version 1.0
	 * @author Yuelong Li
	 * @see XML.object
	 */
	public class Content extends object {
		/**
		 * the content of Content
		 */
		public Object content = null;

		public Content(String name, String content) {
			super();
			this.name = new StringBuilder().append("@content").append("_").append(name).toString();
			fullyQualifiedName = this.name;
			this.content = content;
		}

		public void setContent(Object content) {
			this.content = content;
		}

		public void appendContent(String appender) {
			content += appender;
		}

		protected void print() {
			StringBuilder indent = new StringBuilder();
			for (int i = 0; i < level; i++) {
				indent.append("  ");
			}
			parseOut.println(indent.toString() + content.toString());
			if (level == 1)
				parseOut.println();
		}

	}

	/**
	 * XObject is a class for xml objects. Each xml object has a nesting portal
	 * and each xml object beside the root object should be added to a nest, it
	 * also has a hashmap for all its attributs
	 * 
	 * @version 1.0
	 * @author Yuelong Li
	 * @see XML.object
	 */
	public class XObject extends object {
		/**
		 * the attributes of this object, which are all stored in a hash map
		 */
		public HashMap<String, Object> attributes = new HashMap<String, Object>();

		/**
		 * the extentable part of this xml object, which acts like a nesting
		 * portal for its child nodes
		 */
		HashMap<String, object> innerXML = new HashMap<String, object>();

		/**
		 * Stores the xml objects in the order they first added
		 */
		ArrayList<object> orderedInnerXML = new ArrayList<object>();
		/**
		 * only helps the program to identify wether to add children in add
		 * method
		 */
		private boolean isRoot = false;

		/**
		 * instanciates a xml extentable xml object
		 * 
		 * @param name
		 */
		public XObject(String name) {
			super();
			super.isXObject = true;
			fullyQualifiedName = name;
			this.name = name;
		}

		public XObject(String name, HashMap<String, Object> attributes) {
			super();
			super.isXObject = true;
			this.attributes = attributes;
			fullyQualifiedName = name;
			this.name = name;
		}

		public void putAttribute(String name, Object value) {
			attributes.put(name, value);
		}

		public void setAttribute(HashMap<String, Object> attributes) {
			this.attributes = attributes;
		}

		public Object getAttribute(String name) {
			return attributes.get(name);
		}

		/**
		 * adds a xmlObject to the current list and records its order and calls
		 * the directory method to renew every thing(if the xml object it self
		 * is under the directory)
		 * 
		 * @param xmlObject
		 *            the
		 */
		public void add(object xmlObject) {
			object oldObject;
			if ((oldObject = innerXML.put(xmlObject.name, xmlObject)) != null) {
				orderedInnerXML.remove(oldObject.bundleIndex);
			}
			xmlObject.bundleIndex = orderedInnerXML.size();
			orderedInnerXML.add(xmlObject);
			xmlObject.setParent(this);
			if (parent != null || isRoot)
				directory.add(xmlObject);
		}

		/**
		 * returns the children xml object from its children according to the
		 * name
		 * 
		 * @param name
		 *            of the the children xml object
		 * @return the children xml object specified by the name
		 */
		public object getChildren(String name) {
			return innerXML.get(name);
		}
		
		public List<Object> getChildren(){
			return (List<Object>) orderedInnerXML.clone();
		}

		/**
		 * A easy way and a fast way to retrieve a child object under the
		 * current object tree
		 * 
		 * @return the target object
		 */
		public object get(String fullyQualifiedName) {
			return recursiveGet(Conversion.toStringList(fullyQualifiedName, '.'), 0);
		}

		/**
		 * A easy way and a fast way to retrieve a child object under the
		 * current object tree
		 * 
		 * @return the target object
		 */
		public object get(String[] xmlName) {
			return recursiveGet(xmlName, 0);
		}
		
		/**
		 * Removes the specified object
		 * @param fullyQualifiedName the string used to specify the object, excample: root.level1.level2.object1
		 * @return the removed object
		 */
		public object remove(String fullyQualifiedName){
			object removed = recursiveGet(Conversion.toStringList(fullyQualifiedName, '.'), 0);
			removed.parent.innerXML.remove(removed.name);
			removed.parent.orderedInnerXML.remove(removed);
			return removed;
		}
		
		/**
		 * Removes the object itself
		 * @param removed the object that is wished to be removed
		 */
		public void remove(object removed){
			removed.parent.innerXML.remove(removed.name);
			removed.parent.orderedInnerXML.remove(removed);
		}

		protected object recursiveGet(String[] xmlName, int level) {
			if (level == (xmlName.length))
				return this;
			else
				return getChildren(xmlName[level]).recursiveGet(xmlName, level + 1);
		}

		/**
		 * this method forces the object to return a object that has same exact
		 * address as the requested object, thus, when a object is found non
		 * existing, an XObject will be created as a supplement
		 */
		protected object compulsiveGet(String[] xmlName, int level) {
			object nextObject;
			if (level == (xmlName.length))
				return this;
			else if ((nextObject = getChildren(xmlName[level])) == null) {
				nextObject = new XObject(xmlName[level]);
				add(nextObject);
			}
			return nextObject.compulsiveGet(xmlName, level + 1);
		}

		/**
		 * this method forces the object to return a object that has same exact
		 * address as the requested object, thus, when a object is found non
		 * existing, an XObject will be created as a supplement
		 */
		public object compulsiveGet(String fullyQualifiedName) {
			return compulsiveGet(Conversion.toStringList(fullyQualifiedName, '.'), 0);
		}

		/**
		 * recursive method that expands all its children into the directory by
		 * it self
		 */
		protected void addAllChildren() {
			directory.extendChildrenBundle(this);
			for (object childrenObject : innerXML.values()) {
				directory.add(childrenObject);
			}
		}

		/**
		 * recursive method that prints out all its children objects
		 */
		protected void print() {
			StringBuilder indent = new StringBuilder();
			for (int i = 0; i < level; i++) {
				indent.append("  ");
			}
			String indentation = indent.toString();
			parseOut.print(indentation + "<" + name);
			int index = 0;
			int attributesSize = attributes.size();
			for (Map.Entry<String, Object> entry : attributes.entrySet()) {
				if (index != 0)
					parseOut.print(indentation + "	");
				else
					parseOut.print(" ");
				index++;
				if (index == attributesSize)
					parseOut.print(entry.getKey() + "=\"" + entry.getValue() + "\"");
				else
					parseOut.println(entry.getKey() + "=\"" + entry.getValue() + "\"");
			}
			if (innerXML.isEmpty())
				parseOut.println("/>");
			else {
				parseOut.println(">");
				for (object childrenObject : orderedInnerXML) {
					childrenObject.print();
				}
				parseOut.println(indentation + "</" + name + ">");
				if (level == 1)
					parseOut.println();
			}
		}
	}

	public class Directory {
		// Levels<Level<ParentName,ChildrenBundle<xmlName,xmlObject>>>
		/**
		 * the directory tree where all the xml objects are stored. Every xml
		 * items are organized in three layers of Lists: levles, level and
		 * children bundle.
		 */
		ArrayList<HashMap<String, HashMap<String, object>>> tree;

		/**
		 * Initializes the directory tree
		 * 
		 * @param depth
		 *            specifies the size of the Levels
		 */
		protected Directory(int depth) {
			tree = new ArrayList<HashMap<String, HashMap<String, object>>>(depth);
			addLevel();
		}

		public void print() {
			Console.println(toString());
		}

		/**
		 * this method searches throughout a certain level and returns the
		 * object or objects that matches the name <b> level is counted from 0,
		 * which means the root object of xml has the level of 0
		 * 
		 * @param name
		 *            name of the target object(s)
		 * @param level
		 *            level at which the target object(s) is located
		 * @return the object(s)that matches the description
		 */
		public ArrayList<object> searchByLevel(int level, String name) {
			ArrayList<object> matches = new ArrayList<object>(tree.get(level).size());
			object match;
			for (HashMap<String, object> childrenBundle : tree.get(level).values()) {
				match = childrenBundle.get(name);
				if (match != null)
					matches.add(match);
			}
			return matches;
		}

		/**
		 * returns the xml object under the directory according to the fully
		 * qualified name
		 * 
		 * @param fullyQualifiedName
		 *            the name of the xml object that shows the hericacy of
		 *            itself
		 * @return the xml object that has the fullyQualifiedName
		 */
		public object get(String fullyQualifiedName) {
			String[] xmlName = Conversion.toStringList(fullyQualifiedName, '.');
			int depth = xmlName.length;
			return tree.get(depth - 1).get(toFQName(depth - 2, xmlName)).get(xmlName[depth - 1]);
		}

		/**
		 * Explores all the children content under this directory
		 */
		public String toString() {
			StringBuilder rb = new StringBuilder();
			rb.append("Directory:{");
			int index = 0;
			for (HashMap<String, HashMap<String, object>> Bundle : tree) {
				rb.append("\n level ");
				rb.append(index);
				rb.append(":{");
				for (Map.Entry<String, HashMap<String, object>> entry : Bundle.entrySet()) {
					rb.append("\n  Bundle ");
					String[] key = Conversion.toStringList(entry.getKey(), '.');
					try {
						rb.append(key[1]);
					} catch (ArrayIndexOutOfBoundsException e) {
						rb.append(key[0]);
					}
					rb.append("-");
					rb.append(key[key.length - 1]);
					rb.append(":");
					rb.append("[");
					for (object XMLObject : entry.getValue().values()) {
						rb.append(XMLObject);
						rb.append(",");
					}
					rb.append("]");
				}
				rb.append("\n }");
				index++;
			}
			rb.append("\n}");
			return rb.toString();
		}

		/**
		 * adds a new level into the tree
		 */
		protected void addLevel() {
			tree.add(new HashMap<String, HashMap<String, object>>());
		}

		/**
		 * Expands all the children of the xml object and updates them into the
		 * xml tree
		 * 
		 * @param fullyQualifiedName
		 *            the fully qualified name of its parent object
		 * @param xmlObject
		 *            the xml object itself
		 * @see XML.object
		 */
		protected void add(object xmlObject) {
			xmlObject.updateInfo();
			xmlObject.addAllChildren();
		}

		/**
		 * adds the children bundle in the next level
		 * 
		 * @param parentObject
		 *            is a object of XObject, and it is the parent of the
		 *            children bundle
		 * @see XObject
		 */
		private void extendChildrenBundle(XObject parentObject) {
			try {
				tree.get(parentObject.level + 1).put(parentObject.fullyQualifiedName, parentObject.innerXML);
			} catch (IndexOutOfBoundsException e) {
				addLevel();
				tree.get(parentObject.level + 1).put(parentObject.fullyQualifiedName, parentObject.innerXML);
			}
		}
	}
}
/*
 * © Copyright 2016 Cannot be used without authorization
 */

/**
 * this class is currently deprecated
 * 
 * @author Yuelong
 */
class ArrayMap<T, E> implements Collection {
	HashMap<T, LinkedList<Integer>> indexer = new HashMap<T, LinkedList<Integer>>();
	ArrayList<E> holder = new ArrayList<E>();

	@Override
	public boolean add(Object e) {
		//
		return false;
	}

	@Override
	public boolean addAll(Collection c) {
		//
		return false;
	}

	@Override
	public void clear() {
		indexer.clear();
		holder.clear();
	}

	@Override
	public boolean contains(Object o) {
		//
		return false;
	}

	@Override
	public boolean containsAll(Collection c) {
		return false;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public Iterator iterator() {

		return null;
	}

	@Override
	public boolean remove(Object o) {

		return false;
	}

	@Override
	public boolean removeAll(Collection c) {

		return false;
	}

	@Override
	public boolean retainAll(Collection c) {

		return false;
	}

	@Override
	public int size() {

		return 0;
	}

	@Override
	public Object[] toArray() {

		return null;
	}

	@Override
	public Object[] toArray(Object[] a) {

		return null;
	}
}
/*
 * © Copyright 2016 Cannot be used without authorization
 */