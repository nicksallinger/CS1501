/*************************************************************************
 *  Compilation:  javac LZW.java
 *  Execution:    java LZW - < input.txt   (compress)
 *  Execution:    java LZW + < input.txt   (expand)
 *  Dependencies: BinaryIn.java BinaryOut.java
 *
 *  Compress or expand binary input from standard input using LZW.
 *
 *  WARNING: STARTING WITH ORACLE JAVA 6, UPDATE 7 the SUBSTRING
 *  METHOD TAKES TIME AND SPACE LINEAR IN THE SIZE OF THE EXTRACTED
 *  SUBSTRING (INSTEAD OF CONSTANT SPACE AND TIME AS IN EARLIER
 *  IMPLEMENTATIONS).
 *
 *  See <a href = "http://java-performance.info/changes-to-string-java-1-7-0_06/">this article</a>
 *  for more details.
 *
 *************************************************************************/

public class MyLZW {
    private static int R = 256;        // number of input chars
   // private static final int L = 4096;       // number of codewords = 2^W
    private static int W = 9;         // codeword width
    private static int L = (int) Math.pow(2, W);  
    private static String mode;
    
    public static void compress() { 
        String input = BinaryStdIn.readString();
        TST<Integer> st = new TST<Integer>();
        BinaryStdOut.write(mode.charAt(0));
        for (int i = 0; i < R; i++)
            st.put("" + (char) i, i);
        int code = R+1;  // R is codeword for EOF
        double oldRatio = 0, compRatio = 0;
        //BinaryStdOut.write(mode);
        int total=0,totalWritten=0;
        
        
        while (input.length() > 0) {
        	
        	
            String s = st.longestPrefixOf(input);  // Find max prefix match s.
            BinaryStdOut.write(st.get(s), W);      // Print s's encoding.
            int t = s.length();
            total += t*8;
            totalWritten += W;
            oldRatio = total/totalWritten;
            
            
            if(code == L){ 
            	
            	if(W == 16 && mode.contentEquals("m")){
            		
            		compRatio = total/totalWritten;
            		if(compRatio/oldRatio > 1.1){
            			//System.err.println("resetting");
            			W = 9;
            			L = (int) Math.pow(2, W);
            			st = new TST<Integer>();    
            			for (int i = 0; i < R; i++)
            	            st.put("" + (char) i, i);
            	        code = R+1;  // R is codeword for EOF
            	        oldRatio = 0;
            	        compRatio = 0;
            	        total = 0;
            	        totalWritten = 0;
            	        }
            		
            	}
            	//compRatio = total/totalWritten; //begin monitoring when codebook is filled;
            	
            	//System.err.println("ratio = " + ratio);
        		if(W < 16){
        			W++;
        			//System.err.println("W = " + W);
        			//System.err.println("Code = " + code);
        			L = (int) Math.pow(2, W);
        			//System.err.println("L = " + L);
        		}
        		else if(W == 16 && mode.equals("r")){ //reset only mode
        			//System.err.println("resetting");
        			W = 9;
        			L = (int) Math.pow(2, W);
        			st = new TST<Integer>();    
        			for (int i = 0; i < R; i++)
        	            st.put("" + (char) i, i);
        	        code = R+1;  // R is codeword for EOF
        		}
//        		if(mode.equals("m") && ratio > 1.10){
//        			//System.err.println("resetting");
//        			W = 9;
//        			L = (int) Math.pow(2, W);
//        			st = new TST<Integer>();    
//        			for (int i = 0; i < R; i++)
//        	            st.put("" + (char) i, i);
//        	        code = R+1;  // R is codeword for EOF        			
//        		}
        		
        	}
            if (t < input.length() && code < L)    // Add s to symbol table.
            	
                st.put(input.substring(0, t + 1), code++);
           // System.err.println("code = " + code);
            input = input.substring(t);            // Scan past s in input.

        	
        }
       // System.err.println("code = " + code);
        BinaryStdOut.write(R, W);
        BinaryStdOut.close();
    } 


    public static void expand() {
    	char tempMode = BinaryStdIn.readChar();
    	mode = ""+tempMode;
    	//System.err.println("Mode = " + mode);
        String[] st = new String[65536];
        int i; // next available codeword value
       //mode = BinaryStdIn.readString;
        // initialize symbol table with all 1-character strings
        double oldRatio = 0, compRatio = 0;
        //BinaryStdOut.write(mode);
        int total=0,totalWritten=0;
        
        for (i = 0; i < R; i++)
            st[i] = "" + (char) i;
        st[i++] = "";                        // (unused) lookahead for EOF
        
        
        int codeword = BinaryStdIn.readInt(W);
        if (codeword == R) return;           // expanded message is empty string
        String val = st[codeword];
       
        while (true) {
        	
        	total += i*8;
            totalWritten += W;
            oldRatio = total/totalWritten;
                    	
            BinaryStdOut.write(val);
            codeword = BinaryStdIn.readInt(W);
            
            if (codeword == R) break;
            String s = st[codeword];
            if (i == codeword) {
            	s = val + val.charAt(0);   // special case hack
            } 
            
            
            if (i < L){
            	
            	//System.err.println("I = " + i);
            	st[i++] = val + s.charAt(0);
            	
            }    
            if(i == L){ 
            	
            		if(W == 16 && mode.contentEquals("m")){
            		
            			compRatio = total/totalWritten;
            			if(compRatio/oldRatio > 1.1){
            				//System.err.println("resetting");
            				W = 9;
            				L = (int) Math.pow(2, W);
            				for (i = 0; i < R; i++)
            		            st[i] = "" + (char) i;
            		        st[i++] = "";   
            					oldRatio = 0;
            					compRatio = 0;
            					total = 0;
            					totalWritten = 0;
            	        	}
            		
            		}
            	
            	
            	
        		if(W < 16){
        			W++;
        			//System.err.println("W = " + W);
        			L = (int) Math.pow(2, W);
        		}
        		else if(W == 16 && mode.equals("r")){ //reset only mode
        			//System.err.println("resetting");
        			W = 9;
        			L = (int) Math.pow(2, W);
        			 // initialize symbol table with all 1-character strings
        	        for (i = 0; i < R; i++)
        	            st[i] = "" + (char) i;
        	        st[i++] = "";                        // (unused) lookahead for EOF
        		}
        		
        	}
            val = s;
        }
        BinaryStdOut.close();
    }



    public static void main(String[] args) {
    	
        if(args[0].equals("-")){
        	  	if(args[1].equals("n")){
                	mode = "n";
            	}
                if(args[1].equals("r")){
                	//System.err.println("reset mode");
                	mode = "r";
            	}
                if(args[1].equals("m")){
                	mode = "m";
                }     
                
        	compress();
        }
        
        else if (args[0].equals("+")){ 
        	expand();
        	}
     
        else throw new IllegalArgumentException("Illegal command line argument");
    }

}
