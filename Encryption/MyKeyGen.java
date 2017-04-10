import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Random;

public class MyKeyGen{
	/*
	 * Generates a new keypair

		Pick P and Q to be random primes of an appropriate size to generate a 1024 bit key
		Generate N as P * Q
		Generate PHI(N) as (P-1) * (Q-1)
		Pick E such that 1 < E < PHI(N) and GCD(E, PHI(N))=1 (E must not divide PHI(N) evenly)
		Pick D such that D = E-1 mod PHI(N)
	*/
	public static void main(String[] args){
		BigInteger p,q,n,e,d,phiN;
		Random rn = new Random(); //used for bigInteger constructor
		
		/*
		 * Pick P and Q to be random primes of an appropriate size to generate a 1024 bit key
		 *  - 512 bit number * 512 bit number = 1024 bit number
		 */
		do{
		p = new BigInteger(512,100,rn);//Constructs a randomly generated positive BigInteger that is probably prime, with the specified bitLength.
		q = new BigInteger(512,100,rn);
		
		//Make sure that p and q are (highly likely to be) prime
		while(!p.isProbablePrime(100)){
			p = new BigInteger(512,100,rn);
		}
		while(!q.isProbablePrime(100)){
			q = new BigInteger(512,100,rn);
		}
		/* Generate N as P * Q */
		n = p.multiply(q);
		
		}while(n.bitLength() != 1024);

		
		/*Generate PHI(N) as (P-1) * (Q-1)*/
		phiN = (p.subtract(new BigInteger("1"))).multiply(q.subtract(new BigInteger("1")));
		
		/*Pick E such that 1 < E < PHI(N) and GCD(E, PHI(N))=1 (E must not divide PHI(N) evenly)*/
		e = new BigInteger("3"); //3 is first prime number != 1
		
	
		boolean prime = false;
		while(prime != true){
			
			//System.out.println("E = " + e);
			//System.out.println("PhiN = " + phiN);
			if(e.equals(phiN)){ //Will never work out
				System.out.println("Error in calculating keys");
				System.exit(1);
			}
			if((e.gcd(phiN).equals(BigInteger.ONE) == false)){
				e = e.add(BigInteger.ONE); 
				e = e.add(BigInteger.ONE); 
			}//Adding 2 to three gaurantees it will be an odd number, since prime numbers >3 cannot be even
			
			else{
				prime = true;
			}
			
		}
		
		/*Pick D such that D = E-1 mod PHI(N)*/
		d = e.modInverse(phiN);
		
		//System.out.println("E = " + e);
		//System.out.println("N = " + n);
		//System.out.println("D = " + d);
		//System.out.println("Phi(N) = " + phiN);
		
		//save E and N to pubkey.rsa and D and N to privkey.rsa
		
		try{
			/*
			 * FileOutputStream to write BYTES, required for the SHA-256 
			 * hashing in MySign.java
			 *  - Cant use it as writer because it writes primitives, 
			 *    which BigInteger is not
			 * ObjectOutputStream to write objects, namely BigIntegers
			 */
			FileOutputStream pubKey = new FileOutputStream("pubkey.rsa");
			FileOutputStream privKey = new FileOutputStream("privkey.rsa");
			ObjectOutputStream pubWriter = new ObjectOutputStream(pubKey);
			ObjectOutputStream privWriter = new ObjectOutputStream(privKey);
			
			pubWriter.writeObject(e);
			pubWriter.writeObject(n);
			privWriter.writeObject(d);
			privWriter.writeObject(n);
			
			privWriter.close();
			pubWriter.close();
		
		}
		catch(Exception exc){
			System.out.println("Error, keys not written to files");
			System.exit(1);
		}
		
		
		System.exit(0);
		
}
	
	
}