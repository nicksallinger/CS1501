import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MySign{
	
	public static void main(String[] args){
		//args 0 = flag to sign or verify
		//args 1 = file to sign or verify
		if(args.length != 2){
			System.out.println("Command line arguments not correct");
			System.out.println("Should be of form: java MySign v filename or java MySign s filename.signed");
			System.exit(1);
		}
		String filename = args[1];
		//Add stuff to verify correct command line
		//System.out.println(args[0]);
		if(args[0].equals("s")){
			System.out.println("Attempting to sign file...");
			sign(filename);
		}
		else if(args[0].equals("v")){
			System.out.println("Attempting to verify file...");
			verify(filename);
		}
		else{
			System.out.println("Error in command line arguments");
			System.out.println("Should be of form: java MySign v or java MySign s");
		}		
	}
	
	private static void verify(String file) {
		/*
		 * Read the contents of the original file/*
		 */
		try{ 
			 FileInputStream verifyFile = new FileInputStream(file);
			 ObjectInputStream verifyReader = new ObjectInputStream(verifyFile);
	
			 byte[] original = (byte[]) verifyReader.readObject();		 
			 BigInteger decrypt = (BigInteger) verifyReader.readObject();
			 /*
		 * Generate a SHA-256 hash of the contents of the orignal file	 
		 */
			 
			 MessageDigest hash = MessageDigest.getInstance("SHA-256");
			 hash.update(original);
			 byte[] hashedOriginalBytes = hash.digest();
			 BigInteger hashedOriginal = new BigInteger(1,hashedOriginalBytes);
			 
			 
			 
			 
			 
		/* Read the "decrypted" hash of the original file */
			
			 
		/* "encrypt" this value with the contents of pubkey.rsa (i.e., raise it to the Eth power mod N)
		 * Your program should exit and display an error if pubkey.rsa is not found in the current directory
		 * */
			 File pubKeyFile = new File("pubKey.rsa");
			 if(!pubKeyFile.exists()){
				 System.out.println("Required keys do no exits, exiting program");
			 }
			 FileInputStream pubKey = new FileInputStream("pubkey.rsa");
			 ObjectInputStream pubKeyReader = new ObjectInputStream(pubKey);
			 
			 BigInteger e = (BigInteger) pubKeyReader.readObject();
			 BigInteger n = (BigInteger) pubKeyReader.readObject();
			 BigInteger encrypt = decrypt.modPow(e, n);
			 
		/*
		 * Compare these two hash values (the on newly generated and the one that you just "encrypted") 
		 * and print out to the console whether or not the signature is valid (i.e., whether or not the values 
		 * are the same).
		 */
			if(!encrypt.equals(hashedOriginal)){
				System.out.println("Signature is not valid!");
			}
			else if(encrypt.equals(hashedOriginal)){
				System.out.println("Signature is valid!");
			}
		
			verifyReader.close();
			pubKeyReader.close();
	}
		 catch(Exception e){
			 System.out.println("There was an error verifying the file, exiting program");
			 System.exit(1);
		 }
	}

	public static void sign(String file){
		try{
			/*
			 * Message digests are secure one-way hash functions that take arbitrary-sized data 
			 * and output a fixed-length hash value.
			 * 
			 * getInstance("SHA-256") - Returns a MessageDigest object that implements the specified digest algorithm.
			 * digest() - Completes the hash computation by performing final operations such as padding.
			 * 	- This is why byte arrays have been used
			 */
			File privKeyFile = new File("privKey.rsa");
			
			if(!privKeyFile.exists()){
				System.out.println("Required keys do not exist. Exiting program");
				System.exit(1);
			}
			
			Path path = Paths.get(file);
			byte[] data = Files.readAllBytes(path);
			
			MessageDigest hash = MessageDigest.getInstance("SHA-256");
			hash.update(data);
			byte[] hashData = hash.digest(); //generages hash of provided file
			
			/*
			 * "decrypt" this hash value using the private key stored in privkey.rsa (i.e., raise it to the Dth power mod N)
			 * Your program should exit and display an error if privkey.rsa is not found in the current directory
			 */
			
			
			
			FileInputStream privKey = new FileInputStream("privKey.rsa");
			ObjectInputStream read = new ObjectInputStream(privKey);
			
			
			BigInteger d = (BigInteger) read.readObject();
			BigInteger n = (BigInteger) read.readObject();
			
			
			
			/* decrypt = ((sha-256 hash)^d)%n	
			 * originalHashed = sha256.digest();	
			decrypted = new BigInteger(1, originalHashed).modPow(d, n);	*/
			BigInteger decrypt = new BigInteger(1,hashData).modPow(d, n);
			
			
			
			/*
			 * Write out a signed version of the file (e.g., "myfile.txt.signed") that contains:
			 * 	The contents of the original file
			 * 	The "decrypted" hash of the original file
			 */
			String signedFile = new String(file.concat(".signed"));
			
			
			FileOutputStream signedOutput = new FileOutputStream(signedFile);
			ObjectOutputStream signedWrite = new ObjectOutputStream(signedOutput);
			
			
			signedWrite.writeObject(data);
			signedWrite.writeObject(decrypt);
			
			System.out.println("Signing complete.");
			read.close();
			signedWrite.close();
			
				} 
		/*
		 * Needed to add these separately because I couldnt find where I was getting errors
		 */
		catch(IOException io){
			System.out.println("IO Exception from path");
			System.exit(1);
			/*Whole lot of errors possible*/
		}
		catch(ClassNotFoundException c){
			System.out.println("Class exception error");
			System.exit(1);
			/*Whole lot of errors possible*/
		}
		catch(NoSuchAlgorithmException a){
			System.out.println("Error from hashing algorithm");
			System.exit(1);
		}

	}
		
}
