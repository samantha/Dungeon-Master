import java.util.Random;

/**
 * Warlock - Magical Decorated Enemy Class
 */
public class Warlock extends EnemyType implements Magical
{
	/** The spell that the Warlock casts on the Hero */
	private String spell;

	/**
	 * Warlock Constructor
	 * Increase max HP by 1
	 * @param e Enemy that is to be decorated with Warlock
	 */
	public Warlock(Enemy e)
	{
		super(e);

		increaseMaxHP(1);
		heal(1);
	}

	@Override
	/**
	 * Attaches Warlock to Enemy's name based on level
	 * @return name of Enemy that is now decorated with Warlock
	 */
	public String getName()
	{
		return super.getName() + " Warlock";
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
		int damage = 0;

		Random rand = new Random();
		int randomMagic = rand.nextInt(3) + 1;

		if(randomMagic == 1)
		{
			damage = magicMissile();
			spell = "missile";
		}
		else if(randomMagic == 2)
		{
			damage = fireball();
			spell = "fireball";
		}
		else if(randomMagic == 3)
		{
			damage = thunderclap();
			spell = "thunderclap";
		}

		e.takeDamage(damage);
		int accruedDamage = prevDamage + damage;
		System.out.println("Warlock, Accrued Damage: " + accruedDamage);
		return accruedDamage;
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

		//System.out.println(" with a fireball for " + randomDamage + " damage.");
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

		//System.out.println(" with a thunderclap for " + randomDamage + " damage.");
		return randomDamage;
	}

	@Override
	/**
	 * Calls attack function to inflict damage and return result of that attack
	 * @param e    The Hero character that is being attacked
	 * @return string that describes how much damage was inflicted and what spell was used
	 */
	public String attackResult(Entity e)
	{
		int damage = attack(e);
		return "attacks for " + damage + " damage with a " + spell;
	}
}