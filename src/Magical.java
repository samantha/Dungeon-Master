/** Interface for magical behaviors of an entity */
public interface Magical
{
	/** Menu for magic behaviors */
	public static final String MAGIC_MENU = "1. Magic Missle\n2. Fireball\n3. Thunderclap";

	/** Method to make entity use a Magic Missle */
	public int magicMissile();

	/** Method to make entity use a Fireball */
	public int fireball();

	/** Method to make entity use a Thunderclap */
	public int thunderclap();
}