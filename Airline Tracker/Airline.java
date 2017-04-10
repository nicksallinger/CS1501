import java.awt.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

import javax.sql.ConnectionPoolDataSource;


public class Airline{
	//adjListCNECY LIST OF NEIGHBORS
	//BFS=SHORTEST PATH
	static Scanner sc = new Scanner(System.in);
	static Scanner fileInput;
	static EdgeWeightedGraph priceGraph; 
	static EdgeWeightedGraph distanceGraph;
	static int numCities;
	static String[] cities;
	String[] Cities; //just an array of all the cities
	static File file;
	
	static LinkedList<Node>[] adjList;
	static Graph bfsGraph;
	
	public static void main(String args[]) throws IOException{
		System.out.println("Please enter a filename: ");

		
		try{				
			file = new File(sc.nextLine());		
			fileInput = new Scanner(file);
		}
		catch (Exception e) {
			System.out.println("File not found");
			System.exit(0);
		}
		createGraphs();
		userMenu();
		int choice = -1;
		try{
		choice = sc.nextInt();
		}
		catch(Exception e){
			System.out.println("Not a correct choice");
		}
		
		
		while(true){
			
			if(choice < 0 || choice > 9){
				System.out.println("Not a valid choice, please try again");
				userMenu();
				choice = sc.nextInt();
			}	
			
		switch(choice){
		
		case(1): allRoutes(); break;//need to dispay all the routes
		case(2): MST(); break; //MST with lazyprims
		case(3):
		{
			sc = new Scanner(System.in);
			//shortest distance with dijikstra
			System.out.println("Enter Source Location");
			String source = sc.nextLine();
			int sourceIndex = -1;
			int destIndex = -1;
			
			for(int i = 0; i < numCities; i++){
				
				if(cities[i].toLowerCase().equals(source.toLowerCase())){
					sourceIndex = i;
					break;
				}
			}
			
			System.out.println("Enter destination");
			String dest = sc.nextLine();
			for(int i = 0; i < numCities; i++){
				
				if(cities[i].toLowerCase().equals(dest.toLowerCase())){
					destIndex = i;
					break;
				}
			}
			if(sourceIndex != -1 && destIndex != -1)
				shortestPathDistance(sourceIndex,destIndex);
			else
				System.out.println("Source or destination does not exist");
			userMenu();
			break;
		}
		case(4):
		{
			sc = new Scanner(System.in);
			//shortest distance with dijikstra
			System.out.println("Enter Source Location");
			String source = sc.nextLine();
			int sourceIndex = -1;
			int destIndex = -1;			
			for(int i = 0; i < numCities; i++){
				if(cities[i].toLowerCase().equals(source.toLowerCase())){
					sourceIndex = i;
					break;
				}
			}
			
			System.out.println("Enter destination");
			String dest = sc.nextLine();
			for(int i = 0; i < numCities; i++){
				if(cities[i].toLowerCase().equals(dest.toLowerCase())){
					destIndex = i;
					break;
				}
			}
			if(sourceIndex != -1 && destIndex != -1){
				shortestPrice(sourceIndex,destIndex);
			}
		} 
		break;
		
		case(5):{
			//shortest path hops with bfs
			sc = new Scanner(System.in);
			//shortest distance with dijikstra
			System.out.println("Enter Source Location");
			String source = sc.nextLine();
			int sourceIndex = -1;
			int destIndex = -1;
			
			for(int i = 0; i < numCities; i++){
				
				if(cities[i].toLowerCase().equals(source.toLowerCase())){
					sourceIndex = i;
					break;
				}
			}
			
			System.out.println("Enter destination");
			String dest = sc.nextLine();
			for(int i = 0; i < numCities; i++){
				
				if(cities[i].toLowerCase().equals(dest.toLowerCase())){
					destIndex = i;
					break;
				}
			}
			if(sourceIndex != -1 && destIndex != -1){
				BFS(sourceIndex,destIndex);
			}
		
		} break;
		case(6):
		{ 
			sc = new Scanner(System.in);
			//shortest distance with dijikstra
			System.out.println("Enter Price");
			int price = sc.nextInt();
			
			lessThanOrEqual(price);
			
		} break; //dijikstra all under price
		case(7): //ADD ROUTE
			{
			sc = new Scanner(System.in);
			
			System.out.println("Enter Source Location");
			String source = sc.nextLine();
			int sourceIndex = -1;
			int destIndex = -1;
			
			for(int i = 0; i < numCities; i++){
				System.out.println("Looking at " + cities[i]);
				if(cities[i].toLowerCase().equals(source.toLowerCase())){
					sourceIndex = i;
					break;
				}
			}
			
			System.out.println("Enter destination");
			String dest = sc.nextLine();
			for(int i = 0; i < numCities; i++){
				System.out.println("Looking at " + cities[i]);
				if(cities[i].toLowerCase().equals(dest.toLowerCase())){
					destIndex = i;
					break;
				}
			}
			System.out.println("Enter price for route");
			double newPrice = sc.nextInt();
			System.out.println("Enter distance for route");
			int newDist = sc.nextInt();
			
			
			addRoute(sourceIndex, destIndex, newPrice, newDist);//add route
			} break;
		case(8): {
			sc = new Scanner(System.in);
			
			System.out.println("Enter Source Location");
			String source = sc.nextLine();
			int sourceIndex = -1;
			int destIndex = -1;
			
			for(int i = 0; i < numCities; i++){
				System.out.println("Looking at " + cities[i]);
				if(cities[i].toLowerCase().equals(source.toLowerCase())){
					sourceIndex = i;
					//System.out.println("Found source");
					break;
				}
			}
			
			System.out.println("Enter destination");
			String dest = sc.nextLine();
			for(int i = 0; i < numCities; i++){
				System.out.println("Looking at " + cities[i]);
				if(cities[i].toLowerCase().equals(dest.toLowerCase())){
					//System.out.println("Found dest");
					destIndex = i;
					break;
				}
			}
						
			removeRoute(sourceIndex, destIndex);//add route
			} break;//remove route
		case(9): System.exit(0);
		}
		userMenu();
		choice = sc.nextInt();
	}
		
}
	
	private static void removeRoute(int src, int dest) throws IOException {
		Scanner input = new Scanner(file);
		src = src+1;
		dest = dest+1;
		//File temp = new File("temp.txt");
		//FileWriter writer = new FileWriter(temp);
		String line;
		String[] tempLine;
		
		
		File tempFile = new File("myTempFile.txt");

		BufferedReader reader = new BufferedReader(new FileReader(file));
		BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

		line = src+" "+dest;
		String currentLine;

		while((currentLine = reader.readLine()) != null) {
		    if(currentLine.contains(line)){
		    	currentLine = reader.readLine();
		    }
		    else{
		    	writer.write(currentLine+"\n");
		    }
		
		}
		
		writer.close(); 
		reader.close(); 
		
		/*
		int num = input.nextInt();
		writer.write(num+"");
		for(int i = 0; i < num; i++){
			writer.write("\n"+input.nextLine()+"");
		}
		while(input.hasNextLine()){
			line = input.nextLine();
			tempLine = line.split(" ");
			//System.out.println(tempLine[0]);
			
			if((tempLine[0].equals(src) && tempLine[1].equals(dest))
					|| (tempLine[0].equals(dest) && tempLine[1].equals(src))){
				try{
					line = input.nextLine();
					System.out.println("Skipping " + line);
				}
				catch(Exception e){
					break;
				}
			}
			else{
				writer.write(line+"\n");
			}
		
		}
		*/
		writer.close();
		tempFile.renameTo(file);
		createGraphs();
		
		
		
	}
	public static void addRoute(int src, int dest, double price, int dist) throws IOException{
		
		FileWriter write = new FileWriter(file,true);
		PrintWriter output = new PrintWriter(write);	
		
		output.println((src+1) + " " + (dest+1) + " " + dist + " " + price);
		write.close();
		adjList[src].add(new Node(dest,dist,price));
		adjList[dest].add(new Node(src,dist,price));
		priceGraph.addEdge(new Edge(src,dest,price));
		distanceGraph.addEdge(new Edge(src,dest,dist));	
		bfsGraph.addEdge(src, dest);
		bfsGraph.addEdge(dest, src);
		
		
	}
	private static void lessThanOrEqual(int p) {
		System.out.println("**************Routes under price**************\n");
		
		for(int i = 0; i < numCities; i ++){
		DijkstraUndirectedSP shortestPrice = new DijkstraUndirectedSP(priceGraph,i);
			for(int j = 0; j < numCities; j++){
				if(shortestPrice.hasPathTo(j) && i != j){
					if(shortestPrice.distTo(j) <= p){
					System.out.println(cities[i]+"->"+cities[j]+": $"+ shortestPrice.distTo(j));
					}
				}
			}
		}
		
	}
	
	private static void shortestPrice(int source, int dest) {
		System.out.println("**************Shortest Path**************\n");
		DijkstraUndirectedSP shortestPrice = new DijkstraUndirectedSP(priceGraph,source);
		  // print shortest path      
            if (shortestPrice.hasPathTo(dest)) {
            	System.out.println("A path exists");
               System.out.println(shortestPrice.distTo(dest));
            }
            else{
            	System.out.println("No path exists");
            }
		
	}
	//DONE
	private static void shortestPathDistance(int source, int dest) {
		
		DijkstraUndirectedSP shortestDistance = new DijkstraUndirectedSP(distanceGraph,source);
		  // print shortest path
		
		System.out.println("*************Shortest Path by Distance****************\n");
            if (shortestDistance.hasPathTo(dest)) {
            	System.out.println("A path exists: ");
           
               System.out.println(shortestDistance.distTo(dest));
            }
            
            
	}
	private static void BFS(int source, int dest){
		System.out.println("*************Shortest Distance by hops****************\n");
		BreadthFirstPaths bfs = new BreadthFirstPaths(bfsGraph,source);
		if(bfs.hasPathTo(dest)){
			System.out.println("A path exists");
			for (int e : bfs.pathTo(dest)) 
            {
				System.out.print(cities[e] + " ");
            }
            
			System.out.println();
		}
		else{
			System.out.println("No path exists");
		}
		
	}
	private static void MST(){
		
		LazyPrimMST mst = new LazyPrimMST(distanceGraph);
		System.out.println("*************Shortest Distance MST****************\n");
		System.out.println("\n");
		for(Edge e :mst.edges())
		{	
			System.out.println(cities[e.either()] + " to " + cities[e.other(e.either())] 
					+ " : Distance = " + e.weight() );
		
		        
		        
		}
	System.out.println("\n");
	}
	private static void createGraphs() throws FileNotFoundException{
				//First line is number of cities
				fileInput = new Scanner(file);
		
				numCities = Integer.parseInt(fileInput.nextLine());
				cities = new String[numCities];
				priceGraph = new EdgeWeightedGraph(numCities);
				distanceGraph = new EdgeWeightedGraph(numCities);
				bfsGraph = new Graph(numCities);
				adjList = new LinkedList[numCities];
				
				for (int j=0; j<numCities; j++){
				   adjList[j]=new LinkedList<Node>();
				   
				}
				Edge pEdge;
				Edge dEdge;
				Edge bfsEdge;
				
			
				for(int i = 0; i < numCities; i++){
					
					cities[i] = fileInput.nextLine();
					//System.out.println(cities[i]);
					
					Node e = new Node(i);
					adjList[i].add(e);
					//I IS THE CITY
					//WORKS: System.out.println(adjList[i].get(0).city);
					
				}
				int cityIndex = 0;
				int conIndex = 0;
				//System.out.println("HERE" + adjList[0].connectionz.length);
				while(fileInput.hasNext()){
					
					String[] line = fileInput.nextLine().split(" ");
					//System.out.println(line[0]+" "+line[1]);
					//0start city - 1destination city - 2distance - 3price
					
					if(Integer.parseInt(line[0])-1 != cityIndex){
						cityIndex = Integer.parseInt(line[0])-1;
						conIndex = 0;
					}
					adjList[cityIndex].add(new Node(Integer.parseInt(line[1]) -1,
							Integer.parseInt(line[2]), 
							(double) Double.parseDouble(line[3])));
					
					adjList[Integer.parseInt(line[1])-1].add(new Node(cityIndex,
							Integer.parseInt(line[2]), (double) Double.parseDouble(line[3])));
					//i = source city by number-1
					
					//System.out.println("HERE" + adjList[0]);
					
					pEdge = new Edge(Integer.parseInt(line[0]) - 1,Integer.parseInt(line[1]) - 1, (double) Double.parseDouble(line[3]));
					dEdge = new Edge(Integer.parseInt(line[0]) - 1,Integer.parseInt(line[1]) - 1, Integer.parseInt(line[2]));
					
					bfsGraph.addEdge(Integer.parseInt(line[0]) - 1,Integer.parseInt(line[1]) - 1);
					//System.out.println((Integer.parseInt(line[0]) - 1)+" "+(Integer.parseInt(line[1]) - 1));
					
					bfsGraph.addEdge(Integer.parseInt(line[1]) - 1,Integer.parseInt(line[0]) - 1);
					priceGraph.addEdge(pEdge); 
					distanceGraph.addEdge(dEdge);
					conIndex++;
				}
			//fileInput.close();
				
		
	}
	public static void userMenu(){
		System.out.println("*******************Main Menu*********************");
		System.out.println("1. Show all route, prices, and distances");
		System.out.println("2. Show distance MST");
		System.out.println("3. Shortest path by distance");
		System.out.println("4. Shortest path by price");
		System.out.println("5. Shortest path by hops");
		System.out.println("6. All trips under a certain price");
		System.out.println("7. Add a new route");
		System.out.println("8. Remove a route");
		System.out.println("9. Save and exit");
		System.out.println();
		System.out.println("Please Enter your choice:");
		
	}
	public static void allRoutes(){
		System.out.println("*****************All Routes********************");
		System.out.println("\n");
		DecimalFormat df = new DecimalFormat("0.00");
			for(int i = 0; i < numCities; i++){
				
				
				if(adjList[i].size() > 1){
					for(int j = 1; j < adjList[i].size(); j++){
						
						System.out.print(cities[i]+" -> "+cities[j]);
						System.out.println("\n\tPrice: $" + df.format(adjList[i].get(j).price) 
							+ "\n\tDistance: " + adjList[i].get(j).distance + " miles");
						
					}
				}
			}
			System.out.println("\n");
		/*
		 * Pittsburg -> Erie
		 * Pittsburgh ->Johnstown
		 * Pittsburgh->Harrisburgh
		 * Pittsburgh->Phila
		 * Altoona->Johnstown
		 * Altoona->Harrisburg
		 * Harrisburgh->Phila
		 * Harrisburg->Reading
		 * Phila->Scranton
		 * Phila->Allentown
		 * Reading->Allentown
ALL OF THIS NEEDS TO DISPLAY:
	0 1 127 200.00
	0 3 66 125.00
	0 4 205 125.00
	0 5 306 550.00
	
	2 3 43 150.00
	2 4 134 225.00
	
	4 5 105 175.00
	4 7 59 200.00
	
	5 6 125 275.00
	5 8 62 150.00
	
	7 8 35 175.00
	
	8 7	
	8 5
	7 4
	6 5
	5 4
	5 0
	4 2
	4 0
	3 2
	3 0
	1 0
		*/
		}
	
}
	

class Node{
	int city;
	double price;
	int distance;
	Node next;
	int[] connections;
	int connSize;
	Node first;
	Node connection;
	Node current;
	Node head;
	
	public Node(int i){
		this.city = i;
	}
	public Node(int i, int distance, double price){
		this.city = i;
		this.distance = distance;
		this.price = price;
	}
	
	

	
	public Node getNext(){return next;}
	public int getCity(){ return city;}
	public double getPrice(){ return price;}
	public int getDistace(){return distance;}
	
	
	public void setCity(Node n, int c){ n.city = c;}
	public void setPrice(Node n, double p){n.price = p;}
	public void setDistance(Node n, int d){n.distance = d;}
	
	
}
