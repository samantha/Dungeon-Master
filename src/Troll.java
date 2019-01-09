import java.util.Random;

/** Base Class for an Enemy that is an Enemy */
public class Troll extends Enemy
{
	/** Initializes a Troll.
	 * @param n    The name
	 * @param l    The level
	 * @param m    The max hit points at level
	 * @param i    The image path for the enemy
	 */
	public Troll(String n, int l, int m, String i)
	{
		super(n, l, m, i);
	}

	@Override
	/**
	 * Enemy attacks hero for random amount of damage dependent on level
	 * @param e    The Hero character that is being attacked
	 * @return amount of damage Enemy inflicted on Hero
	 */
	public int attack(Entity e)
	{
		Random rand = new Random();
		int randomDamage = rand.nextInt(getHP()) + 1;
		e.takeDamage(randomDamage);
		return randomDamage;
	}

	@Override
	/**
	 * Calls attack function to inflict damage and return result of that attack
	 * @param e    The Hero character that is being attacked
	 * @return string that describes how much damage was inflicted
	 */
	public String attackResult(Entity e)
	{
		int damage = attack(e);
		return "attacks for " + damage + " damage!";
	}
}