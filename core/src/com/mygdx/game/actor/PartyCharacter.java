package com.mygdx.game.actor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.mygdx.game.actor.enums.Need;
import com.mygdx.game.actor.enums.Race;
import com.mygdx.game.actor.enums.RandomEnum;
import com.mygdx.game.actor.enums.CharacterTraits.*;
import com.mygdx.game.actor.enums.CharacterValues.*;

public class PartyCharacter {

	private static final int DEFAULT_MAX = 100;

	//Random Generators
	Random rn = new Random();
	private static RandomEnum<CareerTrait> rCareerTrait;
	private static RandomEnum<CharacterTrait> rCharacterTrait;
	private static RandomEnum<GeneralTrait> rGeneralTrait;
	
	//Name
	private String name;
	private Race race;

	//Needs
	private int health, maxHealth;
	private int mana, maxMana;
	private int hunger, maxHunger;
	private int gold, maxGold;
	private int happiness, maxHappiness;

	//Traits
	private ArrayList<CharacterTrait> characterTraits;
	private ArrayList<CareerTrait> careerTraits;
	private ArrayList<GeneralTrait> generalTraits;

	//Job
	private Job job;
	private HashMap<Job, Integer> jobSkills;

	//Needus
	private boolean alive;
	private CauseOfDeath causeOfDeath;

	public PartyCharacter()
	{
		//Initialise variables
		setAlive(true);
		jobSkills = new HashMap<Job, Integer>();
		characterTraits = new ArrayList<CharacterTrait>() ;
		careerTraits = new ArrayList<CareerTrait>();
		generalTraits = new ArrayList<GeneralTrait>();
		
		rCareerTrait = new RandomEnum<CareerTrait>(CareerTrait.class);
		rCharacterTrait = new RandomEnum<CharacterTrait>(CharacterTrait.class);
		rGeneralTrait = new RandomEnum<GeneralTrait>(GeneralTrait.class);

		generateRace();
		generateName();
		setNeed(Need.GOLD, 5);
		setNeed(Need.HAPPINESS, 100);
		setNeed(Need.HUNGER, 100);

//		assignJob();
		jobSkills.put(Job.Alchemist, rn.nextInt(5));
		jobSkills.put(Job.Bard, rn.nextInt(5));
		jobSkills.put(Job.Cook, rn.nextInt(5));
		jobSkills.put(Job.Healer, rn.nextInt(5));
		jobSkills.put(Job.Merchant, rn.nextInt(5));
		addCharacterTrait();
		addCareerTrait();
		addGeneralTrait();

	}

	private void generateName() {
		try {
			NameGenerator nameGen = new NameGenerator("humanNames.txt");
			setName(nameGen.compose(rn.nextInt(3)+1));
			System.out.println(name);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void generateRace()
	{
		switch(rn.nextInt(5))
		{
		case 0: setRace(Race.HUMAN);
		setNeeds(100, 100, 100, 100, 0);
		break;

		case 1: setRace(Race.GNOME);
		setNeeds(75, 125, 100, 100, 0);
		break;

		case 2: setRace(Race.ELF);
		setNeeds(90, 110, 100, 100, 0);
		break;

		case 3: setRace(Race.DWARF);
		setNeeds(140, 60, 100, 100, 0);
		break;

		case 4: setRace(Race.HALFLING);
		setNeeds(80, 120, 100, 100, 0);
		break;

		case 5: setRace(Race.ORC);
		setNeeds(175, 25, 100, 100, 0);
		break;
		}
	}



	public void setNeeds(int health, int mana, int hunger, int happiness, int gold)
	{

		this.setNeed(Need.HEALTH, health);
		this.setNeed(Need.MANA, mana);
		this.setNeed(Need.HUNGER, hunger);
		this.setNeed(Need.HAPPINESS, happiness);
		this.setNeed(Need.GOLD, gold);

		this.setNeed(Need.MAXHEALTH, health);
		this.setNeed(Need.MAXMANA, mana);
		this.setNeed(Need.MAXHUNGER, hunger);
		this.setNeed(Need.MAXHAPPINESS, happiness);
		this.setNeed(Need.MAXGOLD, DEFAULT_MAX);
	}


	public boolean isAlive() {
		checkAlive();
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public void setAlive(boolean alive, CauseOfDeath causeOfDeath) {
		this.alive = alive;
		this.causeOfDeath = causeOfDeath;
	}

	public void checkAlive()
	{
		if (getNeed(Need.HEALTH) < 0)
			setAlive(false, CauseOfDeath.HEALTH);
		else if (getNeed(Need.HUNGER) < 0)
			setAlive(false, CauseOfDeath.HUNGER);
	}

	public CauseOfDeath getCauseOfDeath() {
		return causeOfDeath;

	}

	public void assignJob() {
		this.job = Job.randomJob();
	}

	public void assignJob(Job job) {
		this.job = job;
	}

	public Job getJob() {
		return job;
	}

	public int getSkill(Job job) {
		return jobSkills.get(job);
	}

	public void setSkill(Job job, int skill) {
		jobSkills.put(job, skill);
	}

	public void incrementSkill(Job job, int increment) {
		jobSkills.put(job, jobSkills.get(job) + increment);
	}

	public void setNeed(Need stat, int value) {
		switch (stat) {
		case HEALTH :
			this.health = value;
			break;
		case MAXHEALTH :
			this.maxHealth = value;
			break;
		case MANA :
			this.mana = value;
			break;
		case MAXMANA :
			this.maxMana = value;
			break;
		case HUNGER :
			this.hunger = value;
			break;
		case MAXHUNGER :
			this.maxHunger = value;
			break;
		case HAPPINESS :
			this.happiness = value;
			break;
		case MAXHAPPINESS :
			this.maxHappiness = value;
			break;
		case GOLD :
			this.gold = value;
			break;
		case MAXGOLD :
			this.maxGold = value;
			break;
		}
	}

	public int getNeed(Need stat) {
		switch (stat) {
		case HEALTH :
			return health;
		case MAXHEALTH :
			return maxHealth;
		case MANA :
			return mana;
		case MAXMANA :
			return maxMana;
		case HUNGER :
			return hunger;
		case MAXHUNGER :
			return maxHunger;
		case HAPPINESS :
			return happiness;
		case MAXHAPPINESS :
			return maxHappiness;
		case GOLD :
			return gold;
		case MAXGOLD :
			return maxGold;
		}
		return 0;
	}

	public void incrementNeed(Need need, int increment) {
		setNeed(need, getNeed(need) + increment);
	}


	public Race getRace() {
		return race;
	}

	public void setRace(Race human) {
		this.race = human;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<CareerTrait> getCareerTrait() {
		return careerTraits;
	}

	public void addCareerTrait() {
		careerTraits.add(rCareerTrait.random());
	}

	public void addCareerTrait(CareerTrait classTrait) {
		careerTraits.add(classTrait);
	}

	public ArrayList<CharacterTrait> getCharacterTraits() {
		return characterTraits;
	}

	public void addCharacterTrait() {
		characterTraits.add(rCharacterTrait.random());
	}

	public void addCharacterTrait(CharacterTrait characterTrait) {
		characterTraits.add(characterTrait);
	}

	public ArrayList<GeneralTrait> getGeneralTraits() {
		return generalTraits;
	}

	public void addGeneralTrait() {
		generalTraits.add(rGeneralTrait.random());
	}

	public void addGeneralTrait(GeneralTrait generalTrait) {
		generalTraits.add(generalTrait);
	}

	public void endTurn() {
		// Decrease stats based on what the character consumes per turn
	}


}
