public class Apt{
	private String address;
	private String number;
	private String city;
	private String zip;
	private double price;
	private int footage;
	
	/*
	 * Creates a generic apartment object with required parameters
	 */
	public Apt(String address, String number, String city, String zip, double price, int footage){
		setAddress(address);
		setNumber(number);
		setCity(city);
		setPrice(price);
		setFootage(footage);
		setZip(zip);
	}
	
	public void setAddress(String address)	{ this.address = address;	}
	public void setNumber(String number)	{	this.number = number;	}	
	public void setCity(String city)		{	this.city = city;	}
	public void setZip(String zip)			{	this.zip = zip;}
	public void setPrice(double price)		{	this.price = price;}
	public void setFootage(int footage)	{	this.footage = footage;}
	
	public String getAddress(){ return address;}	
	public String getNumber()	{return number;}	
	public String getCity()		{return city;}
	public double getPrice()	{return price;}
	public int getFootage()	{return footage;}
	public String getZip()		{return zip;}
		
	public String toString(){
		StringBuilder str = new StringBuilder();
		str.append("Address: " + address + "\n");
		str.append("Apartment number: " + number + "\n");
		str.append("city: " + city + "\n");
		str.append("zip: " + zip + "\n");
		str.append("Square footage: " + footage + "\n");
		str.append("Price: $" + price + "\n");
		return str.toString();
	}
	public int compareTo(){
		return 0;
	}
}
