import java.util.Random;
import java.util.*;
import java.awt.Point;
import java.lang.String.*;

/**
 * Hero Class - representation of the user-controlled hero that extends Entity
 */
public class Hero extends Entity implements Magical
{
	/** Expandable array of items */
	private ArrayList<Item> items;;
	/** Instance of type Map */
	private Map map;
	/** Marker to show where hero is located */
	private Point location;
	/** Amount of hero's gold in bag */
	private int gold;

	/**
	 * Constructor - gives the hero inital characteristics
	 * @param n sets hero's name
	 * @param m sets game map
	 * set to start level to Level 1
	 * set max hit points to 15
	 */
	public Hero(String n, Map m)
	{
		super(n, 1, 20);
		map = m;
		gold = 50;
		location = map.findStart();
		map.reveal(location);
		items = new ArrayList<Item>();
	}


	/**
	 * Display items in inventory
	 */
	public String displayItem(int i)
	{
		return items.get(i).getPath();
	}

	/**
	 * Retrieve number of items in inventory
	 * @return number of items
	 */
	public int getNumItems()
	{
		return items.size();
	}

	/**
	 * Check to see if hero can add item to inventory, then add item or gold
	 * @param i item of interest
	 * @return true or false
	 */
	public boolean pickUpItem(Item i)
	{
		boolean pickUp = false;

		if(i.getName().equals("Bag o' Gold"))
		{
			collectGold(i.getValue());
		}

		else if(!(i.getName().equals("Health Potion")))
		{
			items.add(i);
			increaseMaxHP(i.getValue());
			pickUp = true;
		}
		else if(i.getName().equals("Health Potion"))
		{
			items.add(i);
			pickUp = true;
		}
		return pickUp;
	}

	/**
	 * Remove an item
	 * @param n name of item that is being removed
	 * @return item being removed
	 */
	public Item removeItem(String n)
	{
		boolean itemFound = false;
		int itemIndex = 0;

		for(int i = 0; i < items.size(); i++)
		{
			if(items.get(i).getName().equals("Health Potion"))
			{
				itemIndex = i;
				itemFound = true;
				break;
			}
			else
			{
				itemFound = false;
			}
		}
		if(!itemFound)
		{
			itemIndex = -1;
		}

		Item usedItem = items.get(itemIndex);
		items.remove(itemIndex);

		return usedItem;
	}

	/**
	 * Remove an item
	 * @param index index of item that is being removed
	 * @return item being removed
	 */
	public Item removeItem(int index)
	{
		// Armor items - use item's value for both maxHp and gold
		// add value of armor in inventory to hitpoints

		if(!(items.get(index).getName().equals("Health Potion")))
		{
			decreaseMaxHP(items.get(index).getValue());
		}
		Item itemDeleted = items.get(index);
		items.remove(index);
		return itemDeleted;
	}

	/**
	 * Allows hero to use potion to refill hero's hit points  or sell if in hero's inventory
	 * @return true if potion is in inventory
	 */
	public boolean hasPotion()
	{
		boolean potionFound = false;
		int potionIndex = 0;

		for(int i = 0; i < items.size(); i++)
		{
			if(items.get(i).getName().equals("Health Potion"))
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
		return potionFound;
	}

	/**
	 * Retrieve hero's location
	 * @return rectuangular coordinates of hero's location
	 */
	public Point getLocation()
	{
		return location;
	}

	/**
	 * Allow hero to move North
	 * @return character of element in that room or 'd' if dead end
	 */
	public char goNorth()
	{
		int x = (int)location.getX();
		int y = (int)location.getY();
		char northRoom;

		if(x == 0)
		{
			northRoom = 'd';
		}
		else
		{
			location.setLocation(x-1, y);
			northRoom = map.getCharAtLoc(location);
			map.reveal(location);
		}
		return northRoom;
	}

	/**
	 * Allow hero to move South
	 * @return character of element in that room or 'd' if dead end
	 */
	public char goSouth()
	{
		int x = (int)location.getX();
		int y =  (int)location.getY();
		char southRoom;

		if(x == 4)
		{
			southRoom = 'd';
		}
		else
		{
			location.setLocation(x+1, y);
			southRoom = map.getCharAtLoc(location);
			map.reveal(location);
		}
		return southRoom;
	}

	/**
	 * Allow hero to move East
	 * @return character of element in that room or 'd' if dead end
	 */
	public char goEast()
	{
		int x = (int)location.getX();
		int y = (int)location.getY();
		char eastRoom;

		if(y == 4)
		{
			eastRoom = 'd';
		}
		else
		{
			location.setLocation(x, y+1);
			eastRoom = map.getCharAtLoc(location);
			map.reveal(location);
		}
		return eastRoom;
	}

	/**
	 * Allow hero to move West
	 * @return name of element in that room or 'd' if dead end
	 */
	public char goWest()
	{
		int x = (int)location.getX();
		int y = (int)location.getY();
		char westRoom;

		if(y == 0)
		{
			westRoom = 'd';
		}
		else
		{
			location.setLocation(x, y-1);
			westRoom = map.getCharAtLoc(location);
			map.reveal(location);
		}
		return westRoom;
	}

	/**
	 * Retrieves hero's balance
	 * @return amount of gold
	 */
	public int getGold()
	{
		return gold;
	}

	/**
	 * Add gold to hero's current balance
	 * @param gold being collected
	 */
	public void collectGold(int g)
	{
		gold += g;
	}

	/**
	 * Subtract gold from hero's current balance
	 * @param gold being spent
	 */
	public void spendGold(int g)
	{
		gold -= g;
	}

	/**
	 * Physical attack function
	 * @param e enemy character that is being attacked
	 */
	@Override
	public int attack(Entity e)
	{
		Random rand = new Random();
		int randomDamage = rand.nextInt(getHP()) + 1;
		//int randomDamage = rand.nextInt(3) + 1;

		e.takeDamage(randomDamage);
		return randomDamage;
	}

	@Override
	public String attackResult(Entity e)
	{
		int randomDamage = attack(e);
		return getName() + " attacks for " + randomDamage + " damage!";
	}

	/**
	 * Magic missle attack
	 * @return random amount of damage based on hero's level
	 */
	@Override
	public int magicMissile()
	{
		// return random amount of damage
		Random rand = new Random();
		int randomDamage = rand.nextInt(getHP()) + 1;
		return randomDamage;
	}

	/**
	 * Fireball attack
	 * @return random amount of damage based on hero's level
	 */
	@Override
	public int fireball()
	{
		// return random amount of damage
		Random rand = new Random();
		int randomDamage = rand.nextInt(getHP()) + 1;
		return randomDamage;
	}

	/**
	 * Thunderclap attack
	 * @return random amount of damage based on hero's level
	 */
	@Override
	public int thunderclap()
	{
		// return random amount of damage
		Random rand = new Random();
		int randomDamage = rand.nextInt(getHP()) + 1;
		return randomDamage;
	}
}
