	import java.util.ArrayList;
import java.util.Arrays;
	import java.util.NoSuchElementException;
	import java.util.HashMap;



public class AptPQ{
	/*
	 * creates a minPQ and maxPQ with indirection
	 * IndexminPq is used for the price, as lower price is higher priority
	 * IdexMaxPQ is used for square footage, as higher footage is higher priority
	 * Seperate hashmaps are used for storing indexes as values, maped to keys of the
	 * 	apartments address,number,and zip appended together
	 */
		IndexMinPQ pricePQ= new IndexMinPQ(25);
		IndexMaxPQ footagePQ = new IndexMaxPQ(25);
		private HashMap<String, Integer> footageMap;
		private HashMap<String, Integer> priceMap;
		int size;
		
		/*
		 * Constructor with no args
		 */
		public AptPQ(){
			this(25);
		}
		/*
		 * Constructor with initial size of PQ and hashmaps
		 * @param initSize the size that the data structures are initially
		 */
		public AptPQ(int initSize){
			pricePQ = new IndexMinPQ(initSize);
			footagePQ = new IndexMaxPQ(initSize);
			priceMap = new HashMap<String, Integer>(initSize);
			footageMap = new HashMap<String, Integer>(initSize);
			size = 0;
		}
		
		/*
		 * inserts a new apartment object into both priority queues and hashmaps
		 * 
		 * @param newApt the Apt object to be added
		 */
		public void insert(Apt newApt){
			//array of APTS sorted according to a parameter
			//hashmap stores the indexes of the apartments by a key 
		
			pricePQ.insert(size, newApt);
			footagePQ.insert(size, newApt);
			String stuff = ""+newApt.getAddress().toLowerCase()+newApt.getNumber().toLowerCase()+newApt.getZip().toLowerCase();
			priceMap.put(stuff, size);
			footageMap.put(stuff, size);
			size++;
		
		}
		
		/*
		 * gets lowest overall price from all apartments in PQ. Requires no 
		 * params because lowest price is simply first on PQ
		 */
		public void getLowestPrice(){
			
			try{
				Apt lowestPrice = pricePQ.minApt();
				if(lowestPrice == null){
					System.out.println("No apartment exists");
				}
				else{
					System.out.println(lowestPrice);
				}
			}
			catch(Exception e){
				System.out.println("No apartment exists");
			}
			
		}
		
		public void getHighestFootage(){
			try{
			Apt highestFootage = footagePQ.maxApt();
			if(highestFootage == null){
				System.out.println("No apartment exists");
			}
			else{
			System.out.println(highestFootage);
			}
			}
			catch(Exception e){
				System.out.println("No apartment exists");
			}
		}
		
		
		public void cityLowestPrice(String city){
			try{			
				Apt cityLowPrice = pricePQ.minAptCity(city);
			if(cityLowPrice == null){
				System.out.println("No apartment exists!");
			}
			else{
				System.out.println(cityLowPrice);
			}
			}
			catch(Exception e){
				System.out.println("No apartment exists!");
			}
		}
		
		/*
		 * Finds and displays the highest square footage by city. IndexMaxPQ min 
		 * function was altered. It now iterates through all of the apartments sorted 
		 * by highest footage, and finds the first one with the city specifieds
		 * 
		 * @param city the city of the apartment
		 */
		public void cityHighestFootage(String city){
			try{
			Apt cityHighFoot = footagePQ.maxAptCity(city);
				if(cityHighFoot == null){
					System.out.println("No apartment exists!");
				}
				else{
					System.out.println(cityHighFoot);
				}
			}
			catch(Exception e){
				System.out.println("No apartment exists!");
			}
		}
		
		/*
		 * gets an apartment with the specified address, number and zip code.
		 * used mainly as a way to show user the apartment they would like to update.
		 * 
		 * @param address address of apartment retrieved, used as a key to hashmap
		 * @param number number of apartment retrieved, used as a key to hashmap
		 * @param zip zip of apartment retrieved, used as a key to hashmap
		 * @return the apartment with matching address,number, and zip if it exists
		 * 		null if not
		 */
		public Apt get(String address,String number, String zip){
			String stuff = ""+address.toLowerCase()+number.toLowerCase()+zip.toLowerCase();
			try{
				int index = priceMap.get(stuff);
				Apt gotApt = pricePQ.aptOf(index);
				return gotApt;
			}
			catch(Exception e){ 

				return null;
			}
			
			
			
			
			
		
		}
		
		/*
		 * retrieves value associted with key of specified apartment and updates the
		 * price attribute of it
		 * 
		 * @param address address of specified apartment
		 * @param number number of specified apartment
		 * @param zip zip code of specified apartment
		 * @param newPrice the price that apartment will now have
		 */
		public void updatePrice(String address,String number,String zip,int newPrice){
			String stuff = ""+address.toLowerCase()+number.toLowerCase()+zip.toLowerCase();
			try{
				int indexP = priceMap.get(stuff);
				//System.out.println("ere1");
				pricePQ.changeAptPrice(indexP,newPrice);
				int indexF = footageMap.get(stuff);
				footagePQ.changeAptPrice(indexF,newPrice);
			}
			catch(Exception e){ 
				System.out.println("Apartment does not exist");
			}
			
			
		}
		
		/*
		 * retrieves value associated with key of specified apartment and updates the
		 * price attribute of it via the changeAptFootage() function of IndexMinPQ and 
		 * indexMaxPQ
		 * 
		 * @param address address of specified apartment
		 * @param number number of specified apartment
		 * @param zip zip code of specified apartment
		 * @param newFootage the square footage that apartment will now have
		 */
		public void updateFootage(String address,String number,String zip,int newFootage){
			String stuff = ""+address.toLowerCase()+number.toLowerCase()+zip.toLowerCase();
			pricePQ.changeAptFootage(priceMap.get(stuff),newFootage);
			footagePQ.changeAptFootage(footageMap.get(stuff), newFootage);
		}
		
		/*
		 * retrieves index value associated with key of specified apartment and 
		 *  removes the apartment from Priority queues via the delete() 
		 *  function of IndexMinPQ and indexMaxPQ
		 * 
		 * @param address address of specified apartment
		 * @param number number of specified apartment
		 * @param zip zip code of specified apartment
		 */
		public void removeApt(String address,String number,String zip){
			
			String stuff = ""+address.toLowerCase()+number.toLowerCase()+zip.toLowerCase();
			try{
				int priceIndex = priceMap.get(stuff);
			
				int footIndex = footageMap.get(stuff);
				pricePQ.delete(priceIndex);
				footagePQ.delete(footIndex);
				//priceMap.remove(stuff);
				//footageMap.remove(stuff);
				
			
			}
			catch(Exception e){
				System.out.println("Apartment does not exist");
			}
			
		}
}
		