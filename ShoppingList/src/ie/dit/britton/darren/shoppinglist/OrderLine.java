package ie.dit.britton.darren.shoppinglist;

/**
 * this class is used in the Basket class to represent the item in the users
 * basket. As a subclass of item it has an extra quantity attribute and function
 * to get the OrderLine total
 */
public class OrderLine extends Item
{

	private int quantity;

	public OrderLine(String name, Double price, int quantity)
	{
		super(name, price);
		this.quantity = quantity;
	}

	public int getQuantity()
	{
		return quantity;
	}

	public void setQuantity(int itemQuantity)
	{
		this.quantity = itemQuantity;
	}

	public double getTotal()
	{
		Double price = getPrice();
		price *= 100;
		price = (double) Math.round(price);
		price /= 100;

		Double total = price * getQuantity();

		total *= 100;
		total = (double) Math.round(total);
		total /= 100;

		return total;
	}

}