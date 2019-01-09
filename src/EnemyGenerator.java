import java.io.*;
import java.util.*;
import java.lang.*;
import java.util.Random;

/**
 * EnemyGenerator - representation of a potential enemies
 * Incorporates Singleton and Factory design pattern
 */
public class EnemyGenerator
{
	/** Instance of EnemyGenerator that ensures that only one instance of EnemyGenerator is created*/
	private static EnemyGenerator instance = null;

	/**
	 * Constructor
	 */
	private EnemyGenerator()
	{
	}

	/**
	 * Get Instance of EnemyGenerator
	 * @return new EnemyGenerator if instance is null, else return existing EnemyGenerator
	 */
	public static EnemyGenerator getInstance()
	{
		if(instance == null)
		{
			instance = new EnemyGenerator();
		}
		return instance;
	}

	/**
	 * Generate an random Enemy
	 * Decorate Enemy if level is greater than 1
	 * @param level
	 * @return a random enemy
	 */
	public Enemy generateEnemy(int level)
	{
		// generate random number between 1 to 4
		Random rand = new Random();
		int randomEnemyChoice = 0;
		randomEnemyChoice = rand.nextInt(4) + 1;

		Enemy monster = new Troll("Troll", level, 5, "../img/../img/.png");

		if(randomEnemyChoice == 1)
		{
			monster = new Troll("Troll", level, 5, "../img/troll.png");
		}
		else if(randomEnemyChoice == 2)
		{
			monster = new Froglok("Froglok", level, 2, "../img/froglok.png");
		}

		else if(randomEnemyChoice == 3)
		{
			monster = new Orc("Orc", level, 4, "../img/orc.png");
		}

		else if(randomEnemyChoice == 4)
		{
			monster = new Goblin("Goblin", level, 2, "../img/goblin.png");
		}

		// Decorate Enemy
		if(level > 1)
		{
			Random randType = new Random();
			// Generate random number between 1 and 2;
			int randomDecorator = 0;
			randomDecorator = rand.nextInt(2) + 1;

			if(randomDecorator == 1)
			{
				for(int i = 0; i < level - 1; i++)
				{
					monster = new Warrior(monster);
				}
			}

			if(randomDecorator == 2)
			{
				for(int i = 0; i < level - 1; i++)
				{
					monster = new Warlock(monster);
				}
			}
		}

		return monster;
	}
}