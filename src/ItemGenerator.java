import java.io.*;
import java.util.*;
/**
 * ItemGenerator - representation of a potential items
 * Incorporates Singleton design pattern
 */
public class ItemGenerator
{
	/** Instance of ItemGenerator that ensures that only one instance of ItemGenerator is created*/
	private static ItemGenerator instance = null;

	/** The items the hero and enemies can have */
	private ArrayList<Item> itemList;

	/** Constructor - Reads in ItemList.txt
	 *	Access to constructor is removed by making it private
	 */
	private ItemGenerator()
	{
		// initialize itemList to an empty collection
		itemList = new ArrayList<Item>();

		try
		{
			// open file with enemy data and read from it
			File itemsFile = new File ("../doc/ItemList.txt");
			Scanner read = new Scanner(itemsFile);
			do
			{
				String line = read.nextLine();

				// split file into parts
				String [] itemTraits = line.split(",");

				// get traits of enemy
				String name = itemTraits[0];
				String strValue = itemTraits[1];
				String img = itemTraits[2];

				// convert hit points from string to int
				int value = Integer.parseInt(strValue);

				itemList.add(new Item(name, value, img));

			}while(read.hasNext());
			read.close();
		}
		catch(FileNotFoundException fnf)
		{
			System.out.println("File was not found");
		}
	}

	/**
	 * Get instance of ItemGenerator
	 * @return new ItemGenerator if instance is null, else return existing ItemGenerator
	 */
	public static ItemGenerator getInstance()
	{
		if(instance == null)
		{
			instance = new ItemGenerator();
		}
		return instance;
	}

	/**
	 * Generate a random item from the item list
	 * Call clone() function
	 * @return copy of random item
	 */
	public Item generateItem()
	{
		// generate random number between 0 to arrayList.size -1
		Random rand = new Random();
		int randomIndex = rand.nextInt(itemList.size());
		Item randomItem = itemList.get(randomIndex);
		Item randomItemCopy = randomItem.clone();
		return randomItemCopy;
	}

	/**
	 * Find health potion from the item list
	 * @return health potion
	 */
	public Item getPotion()
	{
		boolean potionFound = false;
		int potionIndex = 0;

		for(int i = 0; i < itemList.size(); i++)
		{
			if(itemList.get(i).getName().equals("Health Potion"))
			{
				potionIndex = i;
				potionFound = true;
				break;
			}
			else
			{
				potionFound = false;
			}
		}

		if(!potionFound)
		{
			potionIndex = -1;
		}

		return itemList.get(potionIndex);
	}
}