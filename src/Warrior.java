import java.util.Random;

/**
 * Warrior - Decorated Enemy Class
 */
public class Warrior extends EnemyType
{
	/**
	 * Warrior Constructor
	 * Increase max HP by 2
	 * @param e Enemy that is to be decorated with Warlock
	 */
	public Warrior(Enemy e)
	{
		super(e);

		increaseMaxHP(2);
		heal(2);
	}

	@Override
	/**
	 * Attaches Warlock to Enemy's name based on level
	 * @return name of Enemy that is now decorated with Warrior
	 */
	public String getName()
	{
		return super.getName() + " Warrior";
	}

	@Override
	/**
	 * Enemy attacks hero for random amount of damage dependent on level
	 * after choosing a random magical spell
	 * @param e    The Hero character that is being attacked
	 * @return amount of damage Enemy inflicted on Hero
	 */
	public int attack(Entity e)
	{
		int prevDamage = super.attack(e);
		Random rand = new Random();
		int randomDamage = rand.nextInt(getHP()) + 1;
		e.takeDamage(randomDamage);
		int accruedDamage = prevDamage + randomDamage;
		System.out.println("Warrior, Accrued Damage: " + accruedDamage);
		return accruedDamage;
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
		return "hits " + e.getName() + " for " + damage + " damage!";
	}
}