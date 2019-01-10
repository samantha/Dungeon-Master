/** @author Samantha Hangsan */

import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.sound.sampled.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * Dungeon Master - main program that allows a user to play a dungeon maze game
 **/
public class Window extends JFrame
{
	/**
	 * Instantiate main Window
	 */
	public static void main(String[] args)
	{
		Window w = new Window();
	}

	/**
	 * Main Window - sets visibility, bounds, title, and places panel on window application
	 * Start Thread
	 */
	public Window()
	{
		//setBounds(0, 0, 1000, 1000);//x,y,w,h of window
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setTitle("Dungeon Master");

		Panel p = new Panel();
		setContentPane(p);
		Thread t = new Thread(p);
		t.start();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	/**
	 * Panel Class
	 * Displays GUI components and controls state of game
	 * Start Thread
	 */
	public class Panel extends JPanel implements Runnable, KeyListener, MouseListener, ActionListener
	{
		/** Declaration of gameMap that will be loaded */
		private Map gameMap;

		/** Declaration of Hero character that will be moving around the map */
		private Hero hero;

		/** Declaration of ItemGenerator to generate a random item */
		private ItemGenerator ig;

		/** Declaration of EnemyGenerator to generate a random enemy */
		private EnemyGenerator eg;

		/** Declaration of items held by monster and hero */
		private Item monsterItem, heroItem;

		/** Declaration of enemy that the hero will be fighting */
		private Enemy monster;

		/** Declaration of map number to load map and various states the hero will be in */
		private int count, mapNum, heroState, storeState, monsterState, attackType;

		/** Declaration of the results of hero and monster attacks */
		private String monsterAttack, heroAttack, dmgResult;

		/** Declaration of the type of room hero is in */
		private char room;

		/** Declaration of Rectangle array to draw map grid */
		private Rectangle[] inventory;

		/** Declaration of borders to draw on GUI */
		private Border blackBorder, whiteBorder;

		private JPanel[][] gridArray;

		/** Declaration of JPanels that will be placed on main window */
		private JPanel gridPanel, rightPanel, displayPanel, bottomPanel;

		/** Declaration of JLabels that will be placed on JPanels */
		private JLabel lblCharInfo;

		/** Declaration of JButtons that will be placed on JPanels */
		private JButton btnPickUp,btnSell, btnLeave, btnFight, btnRun, btnPotion, btnPhysical, btnMagical,
						btnMissile, btnFireball, btnThunderclap;

		/**
		 * Panel Constructor
		 * Initializes components for game, such as map, item/enemy generator, JPanels, JLabels, JButtons
		 */
		public Panel()
		{
			play("../sound/zelda.wav");
			addKeyListener(this);
			setFocusable(true);

			// start position of 1st map
			count = 0;
			mapNum = 1;
			heroState = 0;
			storeState = 0;
			monsterState = 0;
			room = 'd';
			gameMap = Map.getInstance();
			ig = ItemGenerator.getInstance();
			eg = EnemyGenerator.getInstance();

			gameMap.loadMap();
			hero = new Hero("Link", gameMap);

			blackBorder = BorderFactory.createLineBorder(Color.BLACK);
			whiteBorder = BorderFactory.createLineBorder(Color.WHITE);

			gridArray = new JPanel[5][5];
			inventory = new Rectangle[5];

			// Set Layout of main panel
			setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
			setBackground(Color.BLACK);

			btnPickUp = new JButton("Buy Potion");
			btnPickUp.addActionListener(this);

			btnSell = new JButton("Sell Item");
			btnSell.addActionListener(this);

			btnLeave = new JButton("Leave Shop");
			btnLeave.addActionListener(this);

			btnFight = new JButton("Stay and Fight!");
			btnFight.addActionListener(this);

			btnRun = new JButton("Run Away!");
			btnRun.addActionListener(this);

			btnPotion = new JButton("Use Potion");
			btnPotion.addActionListener(this);

			btnPhysical = new JButton("Physical Attack");
			btnPhysical.addActionListener(this);

			btnMagical = new JButton("Magical Attack");
			btnMagical.addActionListener(this);

			btnMissile = new JButton("Missile");
			btnMissile.addActionListener(this);

			btnFireball = new JButton("Fireball");
			btnFireball.addActionListener(this);

			btnThunderclap = new JButton("Thunderclap");
			btnThunderclap.addActionListener(this);

			// Grid Panel: Left panel
			gridPanel = new JPanel()
			{
				protected void paintComponent(Graphics g)
				{
					super.paintComponent(g);
					gameMap.drawMap(hero.getLocation(), g);
				}
			};
			gridPanel.setBackground(Color.BLACK);
			gridPanel.setPreferredSize(new Dimension(1005, 1005));

			add(gridPanel);

			// Right Panel
			rightPanel = new JPanel();
			rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
			rightPanel.setPreferredSize(new Dimension(400, 1000));
			rightPanel.setBackground(Color.BLACK);
			add(rightPanel);

			// Display Hero information
			displayPanel = new JPanel()
			{
				protected void paintComponent(Graphics g)
				{
					super.paintComponent(g);
					g.setColor(Color.WHITE);
					g.drawLine(0, 45, 400, 45);
					g.setFont(new Font("Calibri", Font.PLAIN, 20));
					g.drawString("Name: " + hero.getName(), 10, 80);
					g.drawString("Level: " + hero.getLevel(), 10, 120);
					g.drawString("HP: " + hero.getHP() + "/" + hero.getMaxHP(), 10, 160);
					g.drawString("$: " + hero.getGold(), 10, 200);
					g.drawString("Inventory: ", 10, 240);

					// INVENTORY
					int side = 125;
					int j = 0;
					for(int i = 0; i < 5; i++)
					{
						if(i > 2)
						{
							g.drawRect(10 + (side * j), 375, side, side);

							inventory[i] = new Rectangle(10 + (side * j), 375, side, side);
							if(hero.getNumItems() > 0 && i < hero.getNumItems())
							{
								try
								{
									String imgPath = hero.displayItem(i);
									BufferedImage img = ImageIO.read(new File(imgPath));
									g.drawImage(img, 20 + (side * j), 380, 95, 80, null);
								}
								catch(FileNotFoundException e)
								{
									System.out.println("File not found!");
								}
								catch(IOException io)
								{
									System.out.println("IO Exception");
								}
							}

							j++;
						}
						else
						{
							g.drawRect(10 + (side*i), 250, side, side);
							inventory[i] = new Rectangle(10 + (side*i), 250, side, side);
							if(hero.getNumItems() > 0 && i < hero.getNumItems())
							{
								try
								{
									String imgPath = hero.displayItem(i);
									BufferedImage img = ImageIO.read(new File(imgPath));
									g.drawImage(img, 35 + (side*i), 265, 95, 80, null);
								}
								catch(FileNotFoundException e)
								{
									System.out.println("File not found!");
								}
								catch(IOException io)
								{
									System.out.println("IO Exception");
								}
							}

						}
					}

					try
					{
						BufferedImage img = ImageIO.read(new File("../img/dungeonmaster.png"));
						g.drawImage(img, 30, 520, 330, 100, null);
					}
					catch(FileNotFoundException e)
					{
						System.out.println("File not found!");
					}
					catch(IOException io)
					{
						System.out.println("IO Exception");
					}
				}
			};

			displayPanel.setPreferredSize(new Dimension(100, 600));
			displayPanel.setBackground(Color.BLACK);
			displayPanel.setBorder(whiteBorder);
			displayPanel.addMouseListener(this);

			lblCharInfo = new JLabel("Character Information");
    		lblCharInfo.setFont(new Font("Calibri", Font.PLAIN, 32));
    		lblCharInfo.setForeground(Color.WHITE);
   			displayPanel.add(lblCharInfo);

			rightPanel.add(displayPanel);
			rightPanel.add(Box.createRigidArea(new Dimension(0, 20)));

			bottomPanel = new JPanel()
			{
				protected void paintComponent(Graphics g)
				{
					super.paintComponent(g);
					g.setColor(Color.WHITE);
					g.setFont(new Font("Calibri", Font.PLAIN, 20));

					// NULL ROOM
					if(room == 'n')
					{
						g.drawString("There's nothing here!", 10, 30);
						try
						{
							BufferedImage img = ImageIO.read(new File("../img/nothing.png"));
							g.drawImage(img, 60, 60, 300, 300, null);
						}
						catch(FileNotFoundException e)
						{
							System.out.println("File not found!");
						}
						catch(IOException io)
						{
							System.out.println("IO Exception");
						}
					}

					// STORE ROOM
					if(room == 's')
					{
						btnPickUp.setBounds(25, 150, 150, 25);
						btnSell.setBounds(25, 200, 150, 25);
						btnLeave.setBounds(25, 250, 150, 25);
						bottomPanel.add(btnPickUp);
						bottomPanel.add(btnSell);
						bottomPanel.add(btnLeave);

						g.drawString("Trader Sam's", 10, 30);
						if (storeState == 0)
						{
							g.drawString("Welcome to my store, " + hero.getName(), 10, 70);
							g.drawString("What can I do for you?", 10, 100);
						}

						else if (storeState == 1)
						{
							g.drawString(hero.getName() + " can not afford to buy", 10, 70);
							g.drawString("a Health Potion!", 10, 100);
						}

						else if (storeState == 2)
						{
							g.drawString(hero.getName() + "'s bag is too full!", 10, 70);
						}

						else if (storeState == 3)
						{
							g.drawString("Here's your potion, " + hero.getName() + ".", 10, 70);
						}

						else if (storeState == 4)
						{
							g.drawString("Click on the item you want to sell.", 10, 70);
							bottomPanel.remove(btnPickUp);
							bottomPanel.remove(btnSell);
						}

						else if (storeState == 5)
						{
							room = 'd';
							heroState = 1;
						}

						try
						{
							BufferedImage img = ImageIO.read(new File("../img/shopkeeper.png"));
							g.drawImage(img, 235, 140, 150, 150, null);
						}
						catch(FileNotFoundException e)
						{
							System.out.println("File not found!");
						}
						catch(IOException io)
						{
							System.out.println("IO Exception");
						}
					}

					// MONSTER ROOM
					else if(room == 'm')
					{
						// Enemy is alive
						if(monsterState == 0)
						{
							g.drawString("You've encountered a monster!", 10, 30);
							g.setFont(new Font("Calibri", Font.PLAIN, 20));
							g.drawString("Level " + monster.getLevel() + " " + monster.getName(), 10, 70);
							g.drawString("HP: " + monster.getHP() + "/" + monster.getMaxHP(), 10, 100);
							// g.drawString("Level " + monster.getLevel() + " " + monster.getName(), 10, 70);
							// g.drawString("HP: " + monster.getHP() + "/" + monster.getMaxHP(), 10, 100);


							btnFight.setBounds(20, 240, 150, 25);
							btnRun.setBounds(20, 270, 150, 25);
							btnPotion.setBounds(20, 300, 150, 25);
							bottomPanel.add(btnFight);
							bottomPanel.add(btnRun);
							if(hero.hasPotion())
							{
								bottomPanel.add(btnPotion);
							}
						}

						// Call Fight
						else if(monsterState == 1)
						{
							g.drawString("Choose your type of Attack", 10, 70);

							bottomPanel.remove(btnFight);
							bottomPanel.remove(btnRun);
							if(hero.hasPotion())
							{
								bottomPanel.remove(btnPotion);
							}

							btnPhysical.setBounds(25, 200, 150, 25);
							btnMagical.setBounds(25, 250, 150, 25);
							bottomPanel.add(btnPhysical);
							bottomPanel.add(btnMagical);
						}

						// Run Away
						else if(monsterState == 2)
						{
							room = 'd';
							heroState = 2;
						}

						// Drink Potion
						else if(monsterState == 3)
						{
							g.drawString(hero.getName() + " gulps down a potion!", 10, 30);
							g.drawString("Ahhhh... refreshing!", 10, 70);
							if(!hero.hasPotion())
							{
								bottomPanel.remove(btnPotion);
							}
						}

						// Physical Attack
						else if(monsterState == 4)
						{
							if(heroState == 3 || heroState == 4)
							{
								room = 'd';
							}
						}

						// Magical Attack
						else if(monsterState == 5)
						{
							// If Magical Attack
							if(heroState == 0)
							{
								g.drawString("Choose your Spell:", 10, 70);
								bottomPanel.remove(btnPhysical);
								bottomPanel.remove(btnMagical);

								btnMissile.setBounds(25, 200, 150, 25);
								btnFireball.setBounds(25, 250, 150, 25);
								btnThunderclap.setBounds(25, 300, 150, 25);

								bottomPanel.add(btnMissile);
								bottomPanel.add(btnFireball);
								bottomPanel.add(btnThunderclap);
							}

							// If win after Magical Attack
							else if(heroState == 3 || heroState == 4)
							{
								room = 'd';
							}
						}

						// Fight
						else if(monsterState == 6)
						{
							g.drawString("Fight!", 10, 30);
							g.setFont(new Font("Calibri", Font.PLAIN, 20));

							if(attackType == 0)
							{
								g.drawString(heroAttack, 10, 70);
								bottomPanel.remove(btnPhysical);
								bottomPanel.remove(btnMagical);
							}

							else if(attackType == 1)
							{
								g.drawString(heroAttack, 10, 70);
								g.drawString(dmgResult, 10, 100);
								bottomPanel.remove(btnMissile);
								bottomPanel.remove(btnFireball);
								bottomPanel.remove(btnThunderclap);
							}

							g.drawString(monster.getName(), 10, 120);
							g.setFont(new Font("Calibri", Font.PLAIN, 18));
							g.drawString(monsterAttack, 10, 140);


							g.setFont(new Font("Calibri", Font.PLAIN, 15));
							g.drawString("Level " + monster.getLevel() + " " + monster.getName(), 10, 200);
							g.drawString("HP: " + monster.getHP() + "/" + monster.getMaxHP(), 10, 220);

							btnFight.setBounds(20, 240, 150, 25);
							btnRun.setBounds(20, 270, 150, 25);
							btnPotion.setBounds(20, 300, 150, 25);
							bottomPanel.add(btnFight);
							bottomPanel.add(btnRun);
							if(hero.hasPotion())
							{
								bottomPanel.add(btnPotion);
							}
						}

						else if(monsterState == 7)
						{
							g.drawString("You Lose!", 10, 30);
							g.setFont(new Font("Calibri", Font.PLAIN, 20));
							g.drawString(monster.getName(), 10, 70);
							g.setFont(new Font("Calibri", Font.PLAIN, 15));
							g.drawString(monsterAttack, 10, 100);

							g.setFont(new Font("Calibri", Font.PLAIN, 15));
							g.drawString("Level " + monster.getLevel() + " " + monster.getName(), 10, 200);
							g.drawString("HP: " + monster.getHP() + "/" + monster.getMaxHP(), 10, 220);
							g.setFont(new Font("Calibri", Font.BOLD, 50));
							g.setColor(Color.RED);
							g.drawString("GAME", 10, 275);
							g.drawString("OVER", 10, 325);

							if(attackType == 0)
							{
								bottomPanel.remove(btnPhysical);
								bottomPanel.remove(btnMagical);
							}
							else if(attackType == 1)
							{
								bottomPanel.remove(btnMissile);
								bottomPanel.remove(btnFireball);
								bottomPanel.remove(btnThunderclap);
							}
						}

						try
						{
							String imgPath = monster.getPath();
							BufferedImage img = ImageIO.read(new File(imgPath));
							if(heroState != 2)
							{
								g.drawImage(img, 250, 200, 120, 120, null);
							}
						}
						catch(FileNotFoundException e)
						{
							System.out.println("File not found!");
						}
						catch(IOException io)
						{
							System.out.println("IO Exception");
						}
					}

					// ITEM ROOM
					else if(room == 'i')
					{
						room = 'd';
					}

					else if(room == 'f')
					{
						room = 'd';
					}

					else if(room == 'd')
					{
						if(heroState == 0)
						{
							if(monsterState == 2)
							{
								g.drawString(hero.getName() + " ran away!", 10, 30);
							}
							else
							{
								g.drawString("Welcome to the dungeon, " + hero.getName() + "!", 10, 30);
							}
						}

						// Leave store
						else if(heroState == 1)
						{
							g.drawString("Farewell and safe travels, young " + hero.getName() + "!", 10, 30);
							bottomPanel.remove(btnPickUp);
							bottomPanel.remove(btnSell);
							bottomPanel.remove(btnLeave);
						}

						// Run away
						else if(heroState == 2)
						{
							g.drawString(hero.getName() + " ran away!", 10, 30);
							bottomPanel.remove(btnFight);
							bottomPanel.remove(btnRun);
							if(hero.hasPotion())
							{
								bottomPanel.remove(btnPotion);
							}
							heroState = 0;
						}

						// MONSTER ROOM: Pick up Item
						else if(heroState == 3)
						{
							if(monsterState == 4)
							{
								bottomPanel.remove(btnFight);
								bottomPanel.remove(btnRun);
								if(hero.hasPotion())
								{
									bottomPanel.remove(btnPotion);
								}
								bottomPanel.remove(btnPhysical);
								bottomPanel.remove(btnMagical);
							}

							else if(monsterState == 5)
							{
								bottomPanel.remove(btnFight);
								bottomPanel.remove(btnRun);
								if(hero.hasPotion())
								{
									bottomPanel.remove(btnPotion);
								}
								bottomPanel.remove(btnMissile);
								bottomPanel.remove(btnFireball);
								bottomPanel.remove(btnThunderclap);
								btnLeave.setBounds(25, 250, 150, 25);
								bottomPanel.add(btnLeave);
							}

							g.drawString("You Win!", 10, 30);
							g.drawString(heroAttack, 10, 70);
							if(attackType == 1)
							{
								g.drawString(dmgResult, 10, 100);
							}
							g.setFont(new Font("Calibri", Font.PLAIN, 18));
							g.drawString("Level " + monster.getLevel() + " " + monster.getName(), 10, 130);
							g.drawString("HP: " + monster.getHP() + "/" + monster.getMaxHP(), 10, 160);
							g.setFont(new Font("Calibri", Font.PLAIN, 20));
							g.drawString("The monster is vanquished ", 10, 200);
							g.drawString("and left behind", 10, 220);
							g.drawString("a " + monsterItem.getName() + "!", 10, 240);

							g.drawString("You put the " + monsterItem.getName() + " in your bag.", 10, 275);
							btnLeave.setText("Leave room");
							btnLeave.setBounds(25, 300, 150, 25);
							bottomPanel.add(btnLeave);
						}

						// MONSTER ROOM: BAG FULL
						else if(heroState == 4)
						{
							if(monsterState == 4)
							{
								bottomPanel.remove(btnFight);
								bottomPanel.remove(btnRun);
								if(hero.hasPotion())
								{
									bottomPanel.remove(btnPotion);
								}
								bottomPanel.remove(btnPhysical);
								bottomPanel.remove(btnMagical);
							}

							else if(monsterState == 5)
							{
								bottomPanel.remove(btnFight);
								bottomPanel.remove(btnRun);
								if(hero.hasPotion())
								{
									bottomPanel.remove(btnPotion);
								}
								bottomPanel.remove(btnMissile);
								bottomPanel.remove(btnFireball);
								bottomPanel.remove(btnThunderclap);
								btnLeave.setBounds(25, 250, 150, 25);
								bottomPanel.add(btnLeave);
							}

							g.drawString("You Win!", 10, 30);
							g.drawString(heroAttack, 10, 70);
							g.drawString("Level " + monster.getLevel() + " " + monster.getName(), 10, 100);
							g.drawString("HP: " + monster.getHP() + "/" + monster.getMaxHP(), 10, 130);
							g.drawString("The monster is vanquished ", 10, 170);
							g.drawString("and left behind", 10, 195);
							g.drawString("a " + monsterItem.getName() + "!", 10, 215);

							g.drawString(hero.getName() + "'s bag is too full!", 10, 250);
							btnLeave.setText("Leave room");
							btnLeave.setBounds(25, 300, 150, 25);
							bottomPanel.add(btnLeave);
						}

						// ITEM ROOM: Pick up Item
						else if(heroState == 5)
						{
							g.drawString("You found a " + heroItem.getName() + "!", 10, 30);
							g.drawString("You put the " + heroItem.getName() + " in your bag.", 10, 70);
						}

						// ITEM ROOM: Can't pick up Item
						else if(heroState == 6)
						{
							g.drawString(hero.getName() + "'s bag is too full!", 10, 30);
						}
						else if(heroState == 9)
						{
							g.drawString(hero.getName() + " ventures back into the Dungeon.", 10, 30);
							bottomPanel.remove(btnLeave);
						}

						else if(heroState == 10)
						{
							g.drawString("LEVEL UP!", 10, 30);
							g.drawString("Congratulations, " + hero.getName() + "!", 10, 70);
							g.drawString("You completed the level!", 10, 100);
							try
							{
								BufferedImage img = ImageIO.read(new File("../img/levelup.png"));
								if(heroState != 2)
								{
									g.drawImage(img, 100, 135, 200, 200, null);
								}
							}
							catch(FileNotFoundException e)
							{
								System.out.println("File not found!");
							}
							catch(IOException io)
							{
								System.out.println("IO Exception");
							}

						}
					}

					g.drawLine(0, 45, 400, 45);
				}
			};
			bottomPanel.setPreferredSize(new Dimension(500, 300));
			bottomPanel.setBackground(Color.BLACK);
			bottomPanel.setBorder(whiteBorder);
			rightPanel.add(bottomPanel);
		}

		@Override
		/** Run thread */
		public void run()
		{
			while(true)
			{
				repaint();
				try
				{
					Thread.sleep(16); //~60fps
				}
				catch(InterruptedException e)
				{
				}
			}
		}

		@Override
		/** Executes when key is pressed */
		public void keyPressed(KeyEvent e)
		{
			if(room == 'd' || room == 'n')
			{
				heroState = 0;
				storeState = 0;
				monsterState = 0;
				if(e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP)
				{
					room = hero.goNorth();
				}

				else if(e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT)
				{
					room = hero.goWest();
				}

				else if(e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN)
				{
					room = hero.goSouth();
				}

				else if(e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT)
				{
					room = hero.goEast();
				}

				if(room == 'm')
				{
					monster = eg.generateEnemy(hero.getLevel());
					monsterItem = monster.getItem();
				}

				else if(room == 'i')
				{
					heroItem = ig.generateItem();


					if(hero.getNumItems() == 5)
					{
						heroState = 6;
					}

					else if(hero.pickUpItem(heroItem))
					{
						gameMap.removeCharAtLoc(hero.getLocation());
						heroState = 5;
					}
				}

				else if(room == 'f')
				{
					heroState = 10;

					hero.increaseLevel();
					hero.increaseMaxHP(10);
					mapNum++;

					gameMap.loadMap();
					gameMap.reveal(hero.getLocation());
				}
			}

			else if(room == 's')
			{
				if(hero.getNumItems() > 0 && storeState == 4)
				{
					if(e.getKeyCode() == KeyEvent.VK_1 && 0 < hero.getNumItems())
					{
						Item sold = hero.removeItem(0);
						hero.collectGold(sold.getValue());
					}
					else if(e.getKeyCode() == KeyEvent.VK_2 && 1 < hero.getNumItems())
					{
						Item sold = hero.removeItem(1);
						hero.collectGold(sold.getValue());
					}
					else if(e.getKeyCode() == KeyEvent.VK_3 && 2 < hero.getNumItems())
					{
						Item sold = hero.removeItem(2);
						hero.collectGold(sold.getValue());
					}
					else if(e.getKeyCode() == KeyEvent.VK_4 && 3 < hero.getNumItems())
					{
						Item sold = hero.removeItem(3);
						hero.collectGold(sold.getValue());
					}
					else if(e.getKeyCode() == KeyEvent.VK_5 && 4 < hero.getNumItems())
					{
						Item sold = hero.removeItem(4);
						hero.collectGold(sold.getValue());
					}
				}

			}
		}

		@Override
		/** Executes when key is released */
		public void keyReleased(KeyEvent e) {}

		@Override
		/** Executes when key is typed
		 * @param e
		 */
		public void keyTyped(KeyEvent e) {}

		@Override
		/** Executes when mouse is clicked
		 * @param e
		 */
		public void mouseClicked(MouseEvent e)
		{
			if(storeState == 4)
			{
				Point click = new Point(e.getX(), e.getY());
				for(int i = 0; i < hero.getNumItems(); i++)
				{
					if(inventory[i].contains(click))
					{
						Item sold = hero.removeItem(i);
						hero.collectGold(sold.getValue());
					}
				}
			}

		}

		@Override
		/** Executes when mouse is pressed
		 * @param e
		 */
		public void mousePressed (MouseEvent e) {}

		@Override
		/** Executes when mouse is released
		 * @param e
		 */
		public void mouseReleased (MouseEvent e) {}

		@Override
		/** Executes when mouse enters a space
		 * @param e
		 */
		public void mouseEntered (MouseEvent e) {}

		@Override
		/** Executes when mouse is exits a space
		 * @param e
		 */
		public void mouseExited (MouseEvent e) {}

		/**
		 * Plays a file
		 * @param filename
		 */
		public void play( String filename )
		{
			try
			{
				Clip clip = AudioSystem.getClip();
				clip.open( AudioSystem.getAudioInputStream(new File(filename)));
				clip.start();
			}
			catch(LineUnavailableException e)
			{
				System.out.println("Audio Error");
			}
			catch(IOException e)
			{
				System.out.println("File Not Found");
			}
			catch(UnsupportedAudioFileException e)
			{
				System.out.println("Wrong File Type");
			}
		}

		/** Executes when button is pressed
		 * @param a
		 */
		public void actionPerformed(ActionEvent a)
		{
			if(a.getSource() == btnPickUp)
			{
				Item potion = ig.getPotion();
				if(room == 's')
				{
					// Check how much money hero has
					if(hero.getGold() < potion.getValue())
					{
						storeState = 1;
					}
					// inventory is full
					else if(hero.getNumItems() == 5)
					{
						storeState = 2;
					}
					// if inventory < 5
					//can buy potion
					// subtract 25 gold from hero's gold
					else if(hero.pickUpItem(potion))
					{
						storeState = 3;
						hero.spendGold(potion.getValue());
					}
				}
			}
			else if(a.getSource() == btnSell)
			{
				storeState = 4;
			}

			else if(a.getSource() == btnLeave)
			{
				if(room == 's')
				{
					storeState = 5;
				}

				else if(room == 'd')
				{
					heroState = 9;
				}
			}

			else if(a.getSource() == btnFight)
			{
				monsterState = 1;
			}

			else if(a.getSource() == btnRun)
			{
				monsterState = 2;
				char element = 'd';

				while(element == 'd')
				{
					Random rand = new Random();
					int direction = rand.nextInt(4) + 1;

					if(direction == 1)
					{
						element = hero.goNorth();
					}

					else if(direction == 2)
					{
						element = hero.goSouth();
					}

					else if(direction == 3)
					{
						element = hero.goEast();
					}

					else if(direction == 4)
					{
						element = hero.goWest();
					}
				}
			}

			else if(a.getSource() == btnPotion)
			{
				monsterState = 3;
				hero.heal(hero.getHP());
				hero.removeItem("Health Potion");
			}

			else if(a.getSource() == btnPhysical)
			{
				monsterState = 4;
				attackType = 0;

				heroAttack = hero.attackResult(monster);

				// Monster is Dead
				if(monster.getHP() == 0)
				{
					gameMap.removeCharAtLoc(hero.getLocation());

					// Bag is Full
					if(hero.getNumItems() == 5)
					{
						heroState = 4;
					}

					// Pick up Item
					else if(hero.pickUpItem(monsterItem))
					{
						gameMap.removeCharAtLoc(hero.getLocation());
						heroState = 3;
					}
					// Pick up Gold
					else
					{
						heroState = 3;
					}
				}
				// Monster is Alive
				else
				{
					// Monster Attacks
					monsterAttack = monster.attackResult(hero);

					// Hero is Dead
					if(hero.getHP() == 0)
					{
						monsterState = 7;						;
					}
					// Hero is alive - continue Fighting
					else
					{
						monsterState = 6;
					}
				}

			}

			else if(a.getSource() == btnMagical)
			{
				monsterState = 5;
			}

			else if(a.getSource() == btnMissile)
			{
				// Magical
				monsterState = 5;
				attackType = 1;

				int damage = hero.magicMissile();
				heroAttack = hero.getName() + " attacks with a missile";
				dmgResult = "for " + damage + " damage!";
				monster.takeDamage(damage);

				// Monster is Dead
				if(monster.getHP() == 0)
				{
					gameMap.removeCharAtLoc(hero.getLocation());

					// Bag is Full
					if(hero.getNumItems() == 5)
					{
						heroState = 4;
					}

					// Pick up Item
					else if(hero.pickUpItem(monsterItem))
					{
						heroState = 3;
					}
					// Pick up Gold
					else
					{
						heroState = 3;
					}
				}

				// Monster is Alive
				else
				{
					monsterAttack = monster.attackResult(hero);

					// Hero is Dead
					if(hero.getHP() == 0)
					{
						heroState = 11;
						monsterState = 7;						;
					}
					// Hero is alive - continue Fighting
					else
					{
						monsterState = 6;
					}
				}
			}

			else if(a.getSource() == btnFireball)
			{
				// Magical
				monsterState = 5;
				attackType = 1;

				int damage = hero.fireball();
				heroAttack = hero.getName() + " attacks with a fireball";
				dmgResult = "for " + damage + " damage!";
				monster.takeDamage(damage);

				// Monster is Dead
				if(monster.getHP() == 0)
				{
					gameMap.removeCharAtLoc(hero.getLocation());

					// Bag is Full
					if(hero.getNumItems() == 5)
					{
						heroState = 4;
					}

					// Pick up Item
					else if(hero.pickUpItem(monsterItem))
					{
						gameMap.removeCharAtLoc(hero.getLocation());
						heroState = 3;
					}
					// Pick up Gold
					else
					{
						heroState = 3;
					}
				}
				// Monster is Alive
				else
				{
					// Monster Attacks
					monsterAttack = monster.attackResult(hero);

					// Hero is Dead
					if(hero.getHP() == 0)
					{
						monsterState = 7;						;
					}
					// Hero is alive - continue Fighting
					else
					{
						monsterState = 6;
					}
				}
			}

			else if(a.getSource() == btnThunderclap)
			{
				// Magical
				monsterState = 5;
				attackType = 1;

				int damage = hero.thunderclap();
				heroAttack = hero.getName() + " attacks with a thunderclap";
				dmgResult = "for " + damage + " damage!";
				monster.takeDamage(damage);

				// Monster is Dead
				if(monster.getHP() == 0)
				{
					gameMap.removeCharAtLoc(hero.getLocation());

					// Bag is Full
					if(hero.getNumItems() == 5)
					{
						heroState = 4;
					}

					// Pick up Item
					else if(hero.pickUpItem(monsterItem))
					{
						gameMap.removeCharAtLoc(hero.getLocation());
						heroState = 3;
					}
					// Pick up Gold
					else
					{
						heroState = 3;
					}
				}

				// Monster is Alive
				else
				{
					// Monster Attacks
					monsterAttack = monster.attackResult(hero);

					// Hero is Dead
					if(hero.getHP() == 0)
					{
						monsterState = 7;						;
					}
					// Hero is alive - continue Fighting
					else
					{
						monsterState = 6;
					}
				}

			}
		}
	}
}
