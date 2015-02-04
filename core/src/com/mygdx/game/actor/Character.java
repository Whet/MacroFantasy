package com.mygdx.game.actor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Character {

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
	private HashMap<String, Integer> essentialStats;

	//Traits
	private String race;
	private ArrayList<String> traits;

	//Job
	public String job;

	//Status
	private boolean alive;
	private String causeOfDeath;

	public Character()
	{
		//Initialise variables
		traits = new ArrayList<String>();
		essentialStats = new HashMap<String, Integer>();
		setAlive(true);

		try {
			nameGen = new NameGenerator("assets/humanNames.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		generateRace();
		generateTraits();
		generateName();
		setGold(5);
		setHappiness(100);
		setHunger(100);


		essentialStats.put("health", health);
		essentialStats.put("hunger", hunger);

	}

	private void generateName() {
		setName(nameGen.compose(3));
	}

	private void generateTraits() {
		switch(rn.nextInt(10))
		{
		case 1: addTrait("axeman");
		break;
		case 2: addTrait("mercenary");
		essentialStats.put("gold", gold);
		break;
		case 3: addTrait("fireball");
		break;
		case 4: addTrait("moneymaker");
		break;
		case 5: addTrait("lich");
		essentialStats.put("mana", mana);
		break;
		case 6: addTrait("fatbastard");
		essentialStats.remove("hunger");
		essentialStats.put("hunger", hunger+50);
		break;
		case 7: addTrait("magicalshield");
		break;
		case 8: addTrait("petdog");
		break;
		case 9: addTrait("fuckinguseless");
		break;
		case 10: addTrait("totallynotabadguy");
		break;
		}

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

	public void checkAlive()
	{
		for(Map.Entry<String, Integer> entry : essentialStats.entrySet())
		{
			if (entry.getValue() < 0)
			{
				System.out.println("Essential Stat: " + entry.getKey() + " " + entry.getValue());
				setAlive(false);
				setCauseOfDeath(entry.getKey());
			}
		}
	}

	private void setCauseOfDeath(String stat) {
		causeOfDeath = stat;

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


	public int getMaxHealth() {
		return maxHealth;
	}


	public void incrementHealth(int increment) {
		setHealth(getHealth() + increment);
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


	public int getMaxMana() {
		return maxMana;
	}

	public void incrementMana(int increment) {
		setMana(getMana() + increment);
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

	public void incrementMaxHappiness(int increment) {
		setHappiness(getHappiness() + increment);
	}

	public int getHunger() {
		return hunger;
	}

	public void setHunger(int hunger) {
		this.hunger = hunger;

	}

	public int getMaxHunger() {
		return maxHunger;
	}

	public void setMaxHunger(int maxHunger) {
		this.maxHunger = maxHunger;
	}

	public void incrementMaxHunger(int increment) {
		setHunger(getHunger() + increment);

	}

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;

	}	

	public void incrementGold(int increment) {
		setGold(getGold() + increment);
	}

	public String getRace() {
		return race;
	}

	public void setRace(String race) {
		this.race = race;
	}

	public ArrayList<String> getTraits() {
		return traits;
	}

	public void setTraits(ArrayList<String> traits) {
		this.traits = traits;
	}

	public void addTrait(String trait) {
		traits.add(trait);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
