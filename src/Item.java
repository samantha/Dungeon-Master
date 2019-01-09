/**
 * Item Class - representation of a single item
 * Incorporates Prototype design pattern
 */
public class Item implements Cloneable
{
	/** Name of the item */
	private String name;

	/** Item's value */
	private int value;

	/** Item's picture */
	private String imagePath;

	/**
	 * Constructor - gives item its name and value
	 * @param n sets the name of the item
	 * @param v sets the value of the item
	 */
	public Item(String n, int v, String img)
	{
		name = n;
		value = v;
		imagePath = img;
	}

	public Item(Item i)
	{
		if(i != null)
		{
			this.name = i.name;
			this.value = i.value;
			this.imagePath = i.imagePath;
		}
	}

	@Override
	public Item clone()
	{
		return new Item(this);
	}


	/**
	 * Retrieve the item's name
	 * @return the item's name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Retrieve the item's value
	 * @return the item's value
	 */
	public int getValue()
	{
		return value;
	}

	/**
	 * Retrieve the item's value
	 * @return the item's value
	 */
	public String getPath()
	{
		return imagePath;
	}
}