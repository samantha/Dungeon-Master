import java.util.Random;

/**
 * Enemy Class - create an enemy
 * Incorporates Decorator design pattern
 */
public abstract class Enemy extends Entity
{
	/** The item held by enemy */
	private Item item;

	/** The image file for this enemy */
	private String imgPath;

	/** Initializes an Entity.
	 * @param n    The name
	 * @param l    The level
	 * @param m    The max hit points at level
	 * @param i    The image path for the enemy
	 */
	public Enemy(String n, int l, int m, String i)
	{
		super(n, l, m);

		item = ItemGenerator.getInstance().generateItem();
		imgPath = i;
	}

	/**
	 * Retrieve item
	 * @return item
	 */
	public Item getItem()
	{
		return item;
	}

	/**
	 * Retrieve image path
	 * @return image path
	 */
	public String getPath()
	{
		return imgPath;
	}

	/* Abstract method to provide attack implementation in subclasses */
	public abstract int attack(Entity e);

	/* Abstract method to provide result of attack in subclasses */
	public abstract String attackResult(Entity e);
}