/*
 * @Author:Nick Sallinger
 * @Date Started 9-20-2016
 * @CS1501 - Project 1
 */

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.omg.CORBA.Current;

import java.io.*;


public class pw_check{
	
	
	Node rootNode = new Node();
	int i = 0;
	//removed a, 4, i, and 1 since they are illegal characters
	char[] set = {'b','c','d','e','f','g','h','j','k','l','m','n','o','p','q','r',
			's','t','u','v','w','x','y','z','0','2','3','5','6','7','8','9','!','@','$','^','_','*'};
	public String[] dictionaryArr = new String[1390];
	double startTime = System.nanoTime();
	

	public static void main(String args[]) throws IOException {
		
		DLB passwordTrie = new DLB();
		Node currentNode = passwordTrie.rootNode;
		DLB allpassword = new DLB();
		
		Node wordsRoot = allpassword.rootNode;
		Scanner sc = new Scanner(System.in);
		boolean findRan = false;
		boolean found = false;
		File file = new File("all_words.txt");
	
		if(args[0].compareTo("-find") == 0){
			//populate DLB
			passwordTrie.populate();
			
			//generates all valid passwords;
			passwordTrie.generatePass(passwordTrie,allpassword);
			findRan = true;
		}
		
		else if(args[0].compareTo("-check") == 0 && file.exists()){ 
			allpassword.populateAll();
			System.out.println("Enter password to check: ");
			String password = sc.next().toLowerCase();
			//need to be finished
			found = allpassword.search(password);
			if(!found){
				System.out.println("Not a valid password");
				allpassword.suggestions(password);
			}
			if(found){
				System.out.println("This is a valid password!");
				
				System.out.println("Time to find : " + System.nanoTime()/1000000.0);
				
			}
			
		}
		else if(!findRan){
			System.out.println("Must run with -find first");
		}
		
	}
	
	public void suggestions(String s){
		/*
		 * Suggests 10 passwords to the user
		 */
		if(s.length() > 4){
			s = (String) s.subSequence(0, 5);
			System.out.println(s);
		}
		
		Pattern p = Pattern.compile("^(?=.*[0-9]){1,2}(?=.*[a-z]){1,3}(?=.*[!@$^_*]).{5,5}$");
		 Matcher match;
		 match = p.matcher(s);
		boolean	b = match.matches();
			 
		int suggestionIndex = 0;
		 int index = 0;
		 char[] suggestion = new char[4]; 
		 
		 boolean goOn = true;

	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
		//use random numbers as the index to make random passwords
		 System.out.println("Suggested valid passwords: ");
	    Node currentNode = rootNode;
	    while(index < 5 && goOn){
	    if(currentNode.letter != s.charAt(index)){
	    	if(currentNode.next == null){
	    		//do nothing
	    		goOn = false;
	    	}
	    	else if(currentNode.next != null){
	    		currentNode = currentNode.next;
	    	}
	    }
	    else if(currentNode.letter == s.charAt(index)){
	    	suggestion[index] = s.charAt(index);
	    	index++;
	    	if(currentNode.end == true){
	    		goOn = false;
	    	}
	    	if(currentNode.child == null){
	    		//do nothing
	    		goOn = false;
	    	}
	    	else if(currentNode.child != null){
	    		currentNode = currentNode.child;
	    	}
	    }
	    }
	    String strSug = new String(suggestion);
	    while(index < 4){
		    try{
		    strSug = strSug+currentNode.letter;
		    }
		    catch(Exception e){
		    
		    }
		   
		    index++;
		    if(index+1 != 4){
		    	try{
		    	currentNode = currentNode.child;
		    	
		    	}
		    	catch(Exception e){
		    		
		    	}
		    }
		    
		    
		    }
	    
	    Node tempNode = currentNode.child;
	    
	    String temp = strSug;
	    temp = strSug+currentNode.letter;
	    for(int i = 0; i < 11; i++){
	    	
	    	temp = strSug;
	    	
	    	try{
	    	tempNode = tempNode.next;
	    	
	    	temp = temp+tempNode.letter;
	    	}
	    	catch(Exception e){
	    		
	    		currentNode = currentNode.next;
	    		tempNode = currentNode;
	    	}
	    	
	    	System.out.println(temp);
	    }
      	System.out.println("Suggested Done");
	    }
		
	public void populate() throws FileNotFoundException{
		/*FINAL I THINK
		 * populates the DLB with words from a text file. Also replaces letters with similar symbols and 
		 * also adds them.
		 * 
		 */
		
		String word;
		File dictionary = new File("dictionary.txt");
		Scanner sc = new Scanner(dictionary);
			
		
		int index = 0;
		word = sc.next().toLowerCase();
		
		
		
		while(sc.hasNextLine()){
			
				insert(word.toLowerCase());
				
				index++;
				
			
			
			try{
			word = sc.nextLine();
			}catch(Exception e){
				
			}	
		
		// 7,t a,4 o,0 e,3 i,1 s,$
		if(word.contains("t")){
			insert(word.replace("t", "7"));				
			index++;			
			
		}
		if(word.contains("t") && word.contains("a")){
			word = word.replace("t", "7");	
			word = word.replace("a", "4");
			insert(word);
			index++;			
			
		}
		if(word.contains("t") && word.contains("a") && word.contains("o")){
			word = word.replace("t", "7");	
			word = word.replace("a", "4");
			word = word.replace("o", "0");
			insert(word);
			index++;			
			
			
		}
		if(word.contains("t") && word.contains("a") && word.contains("o") && word.contains("e")){
			word = word.replace("t", "7");	
			word = word.replace("a", "4");
			word = word.replace("o", "0");
			word = word.replace("e", "3");	
			insert(word);
			index++;			
			
		}
		if(word.contains("t") && word.contains("a") && word.contains("o") && word.contains("e") && word.contains("i")){
			word = word.replace("t", "7");	
			word = word.replace("a", "4");
			word = word.replace("o", "0");
			word = word.replace("e", "e");	
			word = word.replace("i", "1");
			insert(word);
			index++;			
			
		}
		if(word.contains("t") && word.contains("a") && word.contains("o") && word.contains("e") && word.contains("i") && word.contains("s")){
			word = word.replace("t", "7");	
			word = word.replace("a", "4");
			word = word.replace("o", "0");
			word = word.replace("e", "e");	
			word = word.replace("i", "1");
			word = word.replace("s", "$");
			insert(word);
			index++;			
			
		}
		// 7,t// a,4 o,0 e,3 i,1 s,$
				if(word.contains("a")){
					insert(word.replace("a", "4"));	
					
					index++;			
					
				}
				if(word.contains("a") && word.contains("o")){
					word = word.replace("a", "4");	
					word = word.replace("o", "0");
					insert(word);
					index++;			
					
				}
				if(word.contains("a") && word.contains("o") && word.contains("e")){
					word = word.replace("a", "4");	
					word = word.replace("o", "0");
					word = word.replace("e", "3");
					insert(word);
					index++;			
					
					
				}
				if(word.contains("a") && word.contains("o") && word.contains("e") && word.contains("i")){
					word = word.replace("a", "4");	
					word = word.replace("o", "0");
					word = word.replace("e", "3");
					word = word.replace("i", "1");	
					insert(word);
					index++;			
					
				}
				if(word.contains("a") && word.contains("o") && word.contains("e") && word.contains("i") && word.contains("s")){
					word = word.replace("a", "4");	
					word = word.replace("o", "0");
					word = word.replace("e", "3");
					word = word.replace("i", "1");	
					word = word.replace("s", "$");
					insert(word);
					index++;			
					
				}
				
				// 7,t// a,4// o,0 e,3 i,1 s,$
				
				if(word.contains("o")){
					insert(word.replace("o", "0"));	
					
					index++;			
					
				}
				if(word.contains("o") && word.contains("e")){
					word = word.replace("o", "0");	
					word = word.replace("e", "3");
					insert(word);
					index++;			
					
				}
				if(word.contains("o") && word.contains("e") && word.contains("i")){
					word = word.replace("o", "0");	
					word = word.replace("e", "3");
					word = word.replace("i", "1");
					insert(word);
					index++;			
					
					
				}
				if(word.contains("o") && word.contains("e") && word.contains("i") && word.contains("s")){
					word = word.replace("o", "0");	
					word = word.replace("e", "3");
					word = word.replace("i", "1");
					word = word.replace("s", "$");	
					insert(word);
					index++;			
					
				}
				
				// 7,t// a,4// o,0 //e,3 i,1 s,$
				
				if(word.contains("e")){
					insert(word.replace("e", "3"));	
					
					index++;			
					
				}
				if(word.contains("e") && word.contains("i")){
					word = word.replace("e", "3");	
					word = word.replace("i", "1");
					insert(word);
					index++;			
					
				}
				if(word.contains("e") && word.contains("i") && word.contains("s")){
					word = word.replace("e", "3");	
					word = word.replace("i", "1");
					word = word.replace("s", "$");
					insert(word);
					index++;			
					
					
				}
				
				// 7,t// a,4// o,0 //e,3 //i,1 s,$
				
				if(word.contains("i")){
					insert(word.replace("i", "1"));	
					
					index++;			
					
				}
				if(word.contains("i") && word.contains("s")){
					word = word.replace("i", "1");	
					word = word.replace("s", "$");
					insert(word);
					index++;			
					
				}
				if(word.contains("s")){
					word = word.replace("s", "$");
					insert(word);
					index++;			
					
					
				}
					
		}
		sc.close();
		System.out.println(index);
	}
	
	
	public void populateAll() throws FileNotFoundException{
		/*
		 * populates the DLB with words from a text file that is larger. Also replaces letters with similar symbols and 
		 * also adds them.
		 * 
		 */
		
		String word;
		File allpasswords = new File("all_words.txt");
		Scanner sc = new Scanner(allpasswords);
			
		
		int index = 0;
		word = sc.nextLine();
		
		
		
		
		
		while(sc.hasNextLine()){
			
			String[] wordArr = word.split(",");
			word = wordArr[0].toLowerCase();
			
				insertAll(word, wordArr[1]);
				
				index++;
				
			
			
			try{
			word = sc.nextLine();
			}catch(Exception e){
				
			}	
		
		
		}
		sc.close();
		System.out.println(index);
	}
	
	public void generatePass(DLB dictionaryDLB, DLB allpasswords) throws IOException{
		/*
		 * Generates a set of all valid passwords iteratively from the set of rules. Each single password is
		 * checked to see if it contains a string from the dictionary.txt. If the password is valid, then it is
		 * written to all_words.txt
		 * 
		 */
		
		//1-3 of which must be letters (lowercase "a"-"z", no capitals)
		//1-2 of which must be numbers
		//1-2 of which must be symbols (specifically "!", "@", "$", "^", "_", or "*")
		File file = new File("all_passwords.txt");
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);		
		
		Pattern p = Pattern.compile("^(?=.*[0-9]){1,2}(?=.*[a-z]){1,3}(?=.*[!@$^_*]).{5,5}$");
		Matcher match;
		boolean b;
		 
		
		for(int i = 0; i < set.length; i++){
			for(int j = 0; j < set.length; j++){
				for(int k = 0; k < set.length; k++){
					for(int l = 0; l <set.length; l++){
						for(int m = 0; m < set.length; m++){
							String toFind = "" + set[i]+set[j]+set[k]+set[l]+set[m];							
							
							match = p.matcher(toFind);
							b = match.matches();							
							if(b){
								boolean goodWord = true;
								String strTwo = "" + set[j]+set[k]+set[l]+set[m];
								String strThree = "" + set[k]+set[l]+set[m];
								String strFour = "" + set[l]+set[m];
								String strFive = "" + set[m];
								boolean one = !search(toFind);
								boolean two = !search(strTwo);
								boolean three = !search(strThree);
								boolean four = !search(strFour);
								boolean five = !search(strFive);
								if(one&&two&&three&&four&&five){
									double currentTime = System.nanoTime();
									bw.write(toFind+","+((currentTime-startTime)/1000000.0)+"\n");
									insert(toFind);
									
								}
							}
								
							}
						}
					}
				}
			}
		bw.close();
		System.out.println("done");
		}
		
	
	public boolean search(String s){
		/*FINAL I THINK
		 * Searches the DLB for a word.
		 * 
		 *  @param s the string being searched for
		 *  @return returns true if string is contained in DLB, false if not
		 */
		Node currentNode = rootNode;
		int index = 0;
		boolean found = false;
		
		//char at node is not right
		while(index < s.length() && !found){
		if(currentNode.letter != s.charAt(index)){
			if(currentNode.next == null){
				//hit the end still searching
				return false;
				
			}
			else if(currentNode.next != null){
				currentNode = currentNode.next;
				
			}
		}
		else if(currentNode.letter == s.charAt(index)){
			if(currentNode.end == true){
				return true;
			}
			else{
				if(currentNode.child == null){
					return false;
				}
				else if(currentNode.child != null){
					currentNode = currentNode.child;
					if(currentNode.end == true){
						return true;
					}
					index++;
				}
			}
		}
		
		}
		return found;
	}
		
		
	public void insert(String s){
		
		//Final 
		//insert a word into DLB
		int index = 0;
		Node currentNode = rootNode;
		boolean done = false;
		boolean theEnd = true;
		
		
		if(isEmpty(rootNode)){
			//make rootnode have first letter, than write down the rest
			
			for(int j = 0; j < s.length(); j++){
				currentNode.letter = s.charAt(j);
				
				if(j < s.length()-1){
				currentNode.child = new Node();
				currentNode = currentNode.child;
				}
				else if(j == s.length()-1){
					
					currentNode.end = true;
					
				}
			}
			
			
			done = true;
		}
		
		
		while(index < s.length() && done == false){
			

			
		//Case 1, node is taken by something else
		if(currentNode.letter != s.charAt(index)){
			
			//either next node is null or already exists
			if(currentNode.next == null){
				
				currentNode.next = new Node(s.charAt(index));
				currentNode = currentNode.next;
				currentNode.child = new Node();
				currentNode = currentNode.child;
				index++;
				
				
				for(int i = index; i < s.length();i++){
					currentNode.letter = s.charAt(index);
					currentNode.child = new Node();
					currentNode = currentNode.child;
					index++;
				}
				
				
			}
			else if(currentNode.next != null){
				
				//if its not null, just go there again and run loop
				currentNode = currentNode.next;
				//dont iterate index because didnt go to next char
			}
			
		}
		//case 2: char found
		else if(currentNode.letter == s.charAt(index)){
		
			//either child is null or already exists
			//if its null, write rest of word down
			if(currentNode.child == null){
				
				
				currentNode.child = new Node(s.charAt(index));
				index++;
				if(index == s.length()){
					done = true;
					
				}
				else{
					index++;
					currentNode = currentNode.child;
				}
				
			}
			if(currentNode.child != null){
				currentNode = currentNode.child;
				index++;
			}
		}
		}
		
		if(index == s.length()){
			currentNode.end = true;
		}
		
		
		
	}
	

	public void insertAll(String s, String time){
		
		//Final 
		//insert a word into DLB
		int index = 0;
		Node currentNode = rootNode;
		boolean done = false;
		boolean theEnd = true;
		
		
		if(isEmpty(rootNode)){
			//make rootnode have first letter, than write down the rest
			
			for(int j = 0; j < s.length(); j++){
				currentNode.letter = s.charAt(j);
				
				if(j < s.length()-1){
				currentNode.child = new Node();
				currentNode = currentNode.child;
				}
				else if(j == s.length()-1){
					
					currentNode.end = true;
					currentNode.time = time;
					
				}
			}
			
			
			done = true;
		}
		
		
		while(index < s.length() && done == false){
			

			
		//Case 1, node is taken by something else
		if(currentNode.letter != s.charAt(index)){
			
			//either next node is null or already exists
			if(currentNode.next == null){
				
				currentNode.next = new Node(s.charAt(index));
				currentNode = currentNode.next;
				currentNode.child = new Node();
				currentNode = currentNode.child;
				index++;
				
				
				for(int i = index; i < s.length();i++){
					currentNode.letter = s.charAt(index);
					currentNode.child = new Node();
					currentNode = currentNode.child;
					index++;
				}
				
				
			}
			else if(currentNode.next != null){
				
				//if its not null, just go there again and run loop
				currentNode = currentNode.next;
				//dont iterate index because didnt go to next char
			}
			
		}
		//case 2: char found
		else if(currentNode.letter == s.charAt(index)){
		
			//either child is null or already exists
			//if its null, write rest of word down
			if(currentNode.child == null){
				
				
				currentNode.child = new Node(s.charAt(index));
				index++;
				if(index == s.length()){
					done = true;
					
				}
				else{
					index++;
					currentNode = currentNode.child;
				}
				
			}
			if(currentNode.child != null){
				currentNode = currentNode.child;
				index++;
			}
		}
		}
		
		if(index == s.length()){
			currentNode.end = true;
			currentNode.time = time;
		}
		
		
		
	}


	public boolean isEmpty(Node node){
		/*
		 * Checks if the current not is empty, meaning it has no node to the right or under it
		 * 
		 * @param node The node that is being checked
		 * @return true if the node is empty, false if not
		 */
		
		if(node.child == null && node.next == null){
				return true;	
				}
		return false;
	}
		
	
	private class Node{
		/*
		 * Private inner class that creates a node, and has multiple constructors for different scenarios
		 * 
		 */
		
		private char letter;
		private Node next = null;
		private Node child = null;
		private String time;
		boolean end = false;
		
		
		private Node(){
			this.next = null;
			this.child = null;
			this.end = false;
			
		}
		
		private Node(char letter){
			this.next = null;
			this.child = null;
			this.letter = letter;
			this.end = false;
			
			}
		private Node(char letter, boolean end){
			this.letter = letter;
			this.next = null;
			this.child = null;
			this.end = end;
		}
		private Node(char letter, String time, boolean end){
			this.time = time;
			this.letter = letter;
			this.next = next;
			this.child = child;
			this.end = end;
		}
		
		public char toChar(){
			return letter;
		}
		
}
}