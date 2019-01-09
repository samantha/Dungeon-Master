/**
 * EnemyType is the Decorator class for the abstract Component Enemy
 * Decorator Design Pattern
 */
public abstract class EnemyType extends Enemy
{
	/** Enemy that is being decorated */
	private Enemy enemy;

	/**
	 * Constructor for Decorated Enemy
	 * @param e Enemy character
	 */
	public EnemyType(Enemy e)
	{
		super(e.getName(), e.getLevel(), e.getMaxHP(), e.getPath());
		enemy = e;
	}

	/**
	 * Attack function
	 * @param e Hero character that is being attacked
	 * @return value of damage Enemy inflicted on Hero
	 */
	@Override
	public int attack(Entity e)
	{
		return enemy.attack(e);
	}

	/**
	 * Attack Result function
	 * @param e Hero character that is being attacked
	 * @return empty string for Decorator class
	 */
	@Override
	public String attackResult(Entity e)
	{
		return "";
	}
}