package com.mygdx.game.actor;

import java.io.IOException;
import java.util.Random;

public abstract class Character {

	Random rn = new Random();
	
	//Name
	private String name;
	private NameGenerator nameGen;

	//Stats
	private int health, maxHealth;
	private int mana, maxMana;
	private int hunger, maxHunger;
	private int gold;
	private int happiness;

	//Traits
	private String race;
	protected String trait;

	//Job
	public String job;

	//Status
	private boolean alive;
	private String causeOfDeath;

	public Character()
	{
		//Initialise variables
		setAlive(true);

		try {
			nameGen = new NameGenerator("assets/humanNames.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		generateRace();
		generateName();
		setGold(5);
		setHappiness(100);
		setHunger(100);

	}

	private void generateName() {
		setName(nameGen.compose(3));
	}

	private void generateRace()
	{
		switch(rn.nextInt(5))
		{
		case 0: setRace("Human");
		setStats(100,100);
		break;

		case 1: setRace("Gnome");
		setStats(75, 125);
		break;

		case 2: setRace("Elf");
		setStats(90, 110);
		break;

		case 3: setRace("Dwarf");
		setStats(140, 60);
		break;

		case 4: setRace("Halfling");
		setStats(80, 120);
		break;

		case 5: setRace("Orc");
		setStats(175, 25);
		break;
		}
	}



	public void setStats(int health, int mana)
	{
		this.setHealth(health);
		this.setMaxHealth(health);
		this.setMaxMana(mana);
		this.setMana(mana);
	}


	public boolean isAlive() {
		checkAlive();
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}
	
	public void setAlive(boolean alive, String causeOfDeath) {
		this.alive = alive;
		this.causeOfDeath = causeOfDeath;
	}

	public void checkAlive()
	{
		if (getHealth() < 0)
			setAlive(false, "health");
		else if (getHunger() < 0)
			setAlive(false, "hunger");
	}

	public String getCauseOfDeath() {
		return causeOfDeath;

	}

	public String getJob()
	{
		return job;
	}

	public void setJob(String job)
	{
		this.job = job;
	}


	public int getHealth() {
		return health;
	}


	public void setHealth(int health) {
		this.health = health;
	}

	public void addHealth(int increment) {
		setHealth(getHealth() + increment);
	}

	public void subtractHealth(int increment) {
		setHealth(getHealth() - increment);
	}
	
	public int getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(int max_health) {
		this.maxHealth = max_health;
	}

	public int getMana() {
		return mana;
	}

	public void setMana(int mana) {
		this.mana = mana;
	}

	public void addMana(int increment) {
		setMana(getMana() + increment);
	}

	public void subtractMana(int increment) {
		setMana(getMana() - increment);
	}

	public int getMaxMana() {
		return maxMana;
	}
	
	public void setMaxMana(int max_mana) {
		this.maxMana = max_mana;
	}

	public int getHappiness() {
		return happiness;
	}


	public void setHappiness(int happiness) {
		this.happiness = happiness;

	}

	public void addHappiness(int increment) {
		setHappiness(getHappiness() + increment);
	}
	
	public void subtractHappiness(int increment) {
		setHappiness(getHappiness() - increment);
	}

	public int getHunger() {
		return hunger;
	}

	public void setHunger(int hunger) {
		this.hunger = hunger;

	}
	
	public void addHunger(int increment) {
		setHunger(getHunger() + increment);
	}
	
	public void subtractHunger(int increment) {
		setHunger(getHunger() - increment);
	}

	public void setMaxHunger(int maxHunger) {
		this.maxHunger = maxHunger;
	}

	public int getMaxHunger() {
		return maxHunger;
	}

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}	

	public void addtGold(int increment) {
		setGold(getGold() + increment);
	}	
	
	public void subtractGold(int increment) {
		setGold(getGold() - increment);
	}

	public String getRace() {
		return race;
	}

	public void setRace(String race) {
		this.race = race;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public abstract String getTrait();

}
