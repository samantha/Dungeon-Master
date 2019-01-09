/**
 * Entity (Abstract) Class - simple representation of hero and enemies
 */
public abstract class Entity
{
	/** Entity's name */
	private String name;
	/* Entity's level */
	private int level;
	/* Entity's maximum hit points */
	private int maxHp;
	/* Entity's current hit points */
	private int hp;

	/** Initializes an Entity.
	 * @param n    The name.
	 * @param l    The level.
	 * @param m    The max hit points.
	 */
	public Entity(String n, int l, int m)
	{
		name = n;
		level = l;
		maxHp = m;
		hp = maxHp;
	}

	/* Abstract method to provide attack implementation in subclasses */
	public abstract int attack(Entity e);

	/* Abstract method to provide result of attack in subclasses */
	public abstract String attackResult(Entity e);

	/**
	 * Retrieve Entity's name
	 * @return name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Retrieve Entity's Level
	 * @return level
	 */
	public int getLevel()
	{
		return level;
	}

	/**
	 * Retrieve Entity's hit points
	 * @return current hit points
	 */
	public int getHP()
	{
		return hp;
	}

	/**
	 * Retrieve Entity's maximum hit points
	 * @return max hit points
	 */
	public int getMaxHP()
	{
		return maxHp;
	}

	/**
	 * Hero gains a level if user enters the finish
	 * maxHP increases by 10 points
	 * re-load first map for level 4, second map of level 6, etc.
	 */
	public void increaseLevel()
	{
		level++;
	}

	/**
	 * If hero has a point in inventory, user has option when fighting
	 * to use potion to refill hero's hit points +25 or up to hero's level max.
	 * If hit points + 25 is less or equal to max; add 25 to current hit
	 * Else, set current hit to max level hit
	 * @param h    Current hit points.
	 */
	public void heal(int h)
	{
		if (h + 25 <= maxHp)
		{
			hp = h + 25;
		}

		else if (hp + 25 > maxHp)
		{
			hp = maxHp;
		}
	}

	/**
	 * Subtract a random amount of damage based on hero's level from current hit points
	 * @param h    Current hit points.
	 */
	public void takeDamage(int h)
	{
		if(h > getHP())
		{
			hp = 0;
		}

		else
		{
			hp -= h;
		}
	}

	/**
	 * Add 10 points to max hit points
	 * @param h 		Current MAX hit points
	 */
	public void increaseMaxHP(int h)
	{
		maxHp += h;
	}

	/**
	 * Decrease maximum hit points
	 * @param h 		Current MAX hit points
	 */
	public void decreaseMaxHP(int h)
	{
		maxHp -= h;
	}
}


