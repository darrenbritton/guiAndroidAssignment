package ie.dit.britton.darren.shoppinglist;

public class Item
{
	
	private static final Double VAT = 0.23;
	
	private String name;
	private Double price;
	private int quantity;
	
	public Item (String name, Double price, int quantity)
	{
		this.name = name;
		this.price = price;
		this.quantity = quantity;
	}
	
	public Double getVat()
	{
		Double vat;
		vat = price / (1 + VAT);
		return vat;
	}
	
	public boolean equals(String name)
	{
		if (this.name == name)
		{
			return true;
		}
		return false;
	}
	
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price)
	{
		this.price = price;
	}
	public int getQuantity()
	{
		return quantity;
	}
	public void setQuantity(int itemQuantity)
	{
		this.quantity = itemQuantity;
	}
}