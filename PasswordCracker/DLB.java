/*
 * @Author:Nick Sallinger
 * @Date Started 9-20-2016
 * @CS1501 - Project 1
 */

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;


public class DLB{
	
	char terminator = '#';
	Node rootNode = new Node();
	int i = 0;
	//removed a, 4, i, and 1 since they are illegal characters
	char[] set = {'b','c','d','e','f','g','h','j','k','l','m','n','o','p','q','r',
			's','t','u','v','w','x','y','z','0','2','3','5','6','7','8','9','!','@','$','^','_','*'};
	public String[] dictionaryArr = new String[1390];
	long startTime = System.nanoTime();
	

	public static void main(String args[]) throws IOException {
		DLB passwordTrie = new DLB();
		Node currentNode = passwordTrie.rootNode;
		Scanner sc = new Scanner(System.in);
		boolean findRan = false;
		boolean found = false;
		//System.out.println(args[0].compareTo("-find"));
		//System.out.println(args[0].compareTo("-check"));
		
		File file = new File("all_words.txt");
		passwordTrie.populate();
		passwordTrie.generatePass();
		
		/*if(args[0].compareTo("-find") == 0){
			//populate DLB
			passwordTrie.populate();
			//generates all valid passwords;
			passwordTrie.generatePass();
			findRan = true;
		}
		
		else if(args[0].compareTo("-check") == 0 && file.exists()){
			System.out.println("Enter password to check: ");
			String password = sc.next();
			//need to be finished
			found = passwordTrie.search("Hi");
			if(found){
				System.out.println("This is a valid password!");
			}
			if(!found){
				System.out.println("Not a valid password");
				passwordTrie.suggestions();
			}
			
		}
		else if(!findRan){
			System.out.println("Must run with -find first");
		}*/
		
	}
	public void suggestions(){
		/*
		 * Suggests 10 passwords to the user
		 */
		Random rand = new Random();
		Pattern p = Pattern.compile("^(?=.*[0-9]){1,2}(?=.*[a-z]){1,3}(?=.*[!@$^_*]).{5,5}$");
		 Matcher match;
		 boolean b;
		 int suggestionIndex = 0;

	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
		//use random numbers as the index to make random passwords
		 System.out.println("Suggested valid passwords: ");
	    int randomNum = rand.nextInt((set.length) + 1);
	   
		for(int i = randomNum%3; i < set.length; i++){
			if(suggestionIndex == 10){
				
				break;
			}
			for(int j = randomNum%4; j < set.length; j++){
				if(suggestionIndex == 10){
					
					break;
				}
				for(int k = randomNum%5; k < set.length; k++){
					if(suggestionIndex == 10){
						
						break;
					}
					for(int l = randomNum%6; l <set.length; l++){
						if(suggestionIndex == 10){
							
							break;
						}
						for(int m = randomNum%7; m < set.length; m++){
							if(suggestionIndex == 10){
								//10 passwords have been made
								break;
							}
							String toFind = "" + set[i]+set[j]+set[k]+set[l]+set[m];
							
							match = p.matcher(toFind);
							b = match.matches();
							 
							if(b){
								boolean goodWord = true;
								for(int z = 0; z < dictionaryArr.length; z++){
									
									if(toFind.contains(dictionaryArr[z])){
										
										goodWord = false;
										
									}
									
									
								}
								if(suggestionIndex < 10){
								if(goodWord){
									suggestionIndex++;
									
								}
								if(suggestionIndex == 11){
									break;
								}
								}
								}
						}
					}
				}
			}
	    }
	    
	}
	
	public void populate() throws FileNotFoundException{
		/*
		 * populates the DLB with words from a text file. Also replaces letters with similar symbols and 
		 * also adds them.
		 * 
		 */
		int words = 0;
		String word;
		File dictionary = new File("dictionary.txt");
		Scanner sc = new Scanner(dictionary);
		int index = 0;
		
		word = sc.nextLine();
		while(sc.hasNextLine()){
			if(word.length() < 6){
				insert(word);
				dictionaryArr[words] = word;
				index++;
				words++;
			}
			
			try{
			word = sc.nextLine();
			}catch(Exception e){
				
			}	
		boolean contains = false;
		
		if(word.contains("t")){
			insert(word.replace("t", "7"));
			dictionaryArr[index] = word.replace("t", "7");
			index++;
			words++;
			
			contains = true;
		}
		if(word.contains("a")){
			insert(word.replace("a", "4"));
			dictionaryArr[index] = word.replace("a", "4");
			index++;
			words++;
		}
		if(word.contains("o")){
			insert(word.replace("o", "0"));
			dictionaryArr[index] = word.replace("o", "0");
			index++;
			words++;
			
		}
		if(word.contains("e")){
			insert(word.replace("e", "3"));
			dictionaryArr[index] = word.replace("e", "3");
			index++;
			words++;
		}
		if(word.contains("i")){
			insert(word.replace("i", "1"));
			dictionaryArr[index] = word.replace("i", "1");
			index++;
			words++;
		}
		if(word.contains("s")){
			insert(word.replace("s", "$"));
			dictionaryArr[index] = word.replace("s", "$");
			index++;
			words++;
		}		
		}
		sc.close();
	}
	
	public void generatePass() throws IOException{
		/*
		 * Generates a set of all valid passwords iteratively from the set of rules. Each single password is
		 * checked to see if it contains a string from the dictionary.txt. If the password is valid, then it is
		 * written to all_words.txt
		 * 
		 */
		
		//1-3 of which must be letters (lowercase "a"-"z", no capitals)
		//1-2 of which must be numbers
		//1-2 of which must be symbols (specifically "!", "@", "$", "^", "_", or "*")
		File file = new File("all_words.txt");
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
								for(int z = 0; z < dictionaryArr.length; z++){
									
									if(toFind.contains(dictionaryArr[z])){
										
										goodWord = false;
										
									}
									
									
								}
								if(goodWord){
									long currentTime = System.nanoTime();
									bw.write(toFind+","+((currentTime-startTime)/1000)+"\n");
									
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
		/*
		 * Searches the DLB for a word.
		 * 
		 *  @param s the string being searched for
		 *  @return returns true if string is contained in DLB, false if not
		 */
		//takes the password that was created and checks the DLB for it
		Node currentNode = rootNode;
		Node tempNode;
		int index = 0;
		boolean found = false;
		boolean termSearch = true;
		if(s.length() < 5){
			System.out.println("this is not a valid password, too short");
			return false;
		}
		
		while(found == false && index < s.length()){
			if(currentNode.letter != s.charAt(index)){
				if(currentNode.next == null){
					return true;
				}
				else{
				currentNode = currentNode.next;
				}
			}		
			if(currentNode.letter == s.charAt(index)){				
				//this sends the temp node through the entire level of the chars children looking for the terminator
					currentNode = currentNode.child;
					index++;
			}			
		}
		if(index == s.length()){
			tempNode = currentNode;
		while(termSearch){
			if(tempNode.letter == terminator){
				found = true;
				termSearch = false;
				//this word was found
			}
			else if(tempNode.letter != terminator){
				if(tempNode.next == null){
					termSearch = false;
				}
				else{
					tempNode = tempNode.next;
				}
		}
		}
	}
		return found;
		}
	                    
	public void insert(String s){
		/*
		 * Inserts a string into the DLB data structure
		 * 
		 * @param s the string to be inserted into the DLB
		 
		 * Needs:
		 * 1. currentNode is occupied by different char
		 * 		- go to .next, making it currentNode
		 * 			-if .next is null, create new node with char at index
		 * 				-iterate index
		 * 			- if not null, just go there
		 * 		
		 * 2. currentNode is occupied by same char
		 * 		- go to child
		 * 			-if child null, can write rest of the string down children
		 * 			-if not null, just go there
		 * 		- iterate index
		 * 3. 
		 * 
		 */
		int index = 0;
		Node currentNode = rootNode;
		
		if(isEmpty(rootNode)){
			//Starts the trie since it is empty
			currentNode.letter = s.charAt(index);
			index++;			
			for(int i = index; i < s.length() ; i++){			
				currentNode.child = new Node(s.charAt(index));							
				currentNode = currentNode.child;
				index++;			
			}			
		}
		while(index < s.length()){
			if(currentNode.letter != s.charAt(index)){
				//case 1
				if(currentNode.next == null){
					currentNode.next = new Node(s.charAt(index));
					currentNode = currentNode.next;
				}
				else{
					currentNode = currentNode.next;
				}
			}
			else if(currentNode.letter == s.charAt(index)){
				if(currentNode.child == null){
					//write rest of string down line since nothing is under it
					index++;
					for(int i = index; i < s.length(); i++){
						currentNode.child = new Node(s.charAt(index));
						currentNode = currentNode.child;						
					}
					break;
				}
				else {
					if(index+1 == s.length()){
						index++;//do nothing
					}
					else{
				currentNode = currentNode.child;
				index++;
					}
				}
			}
		
		}
		boolean go = false;
		
		//All the string has been entered, now finding palce for terminator
		if(currentNode.child == null){
		currentNode.child = new Node(terminator);
		go = true;
		}
		else{			
			currentNode = currentNode.child;
			while(go == false){
			if(currentNode.next == null){
				//adds terminator character eventually
				currentNode.next = new Node(terminator);
				go = true;
			}
			else{
				currentNode = currentNode.next;
			}
		}
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
		
		private Node(){
			this.next = null;
			this.child = null;
			}
		private Node(char letter){
			this.letter = letter;
			this.next = null;
			this.child = null;
		}
		private Node(char letter, Node next, Node child){
			this.letter = letter;
			this.next = next;
			this.child = child;
		}
		
		public char toChar(){
			return letter;
		}
		
}
}
