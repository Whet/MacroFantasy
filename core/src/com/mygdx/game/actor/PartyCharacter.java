package com.mygdx.game.actor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.mygdx.game.actor.enums.CauseOfDeath;
import com.mygdx.game.actor.enums.Gender;
import com.mygdx.game.actor.enums.Job;
import com.mygdx.game.actor.enums.Need;
import com.mygdx.game.actor.enums.Race;
import com.mygdx.game.actor.enums.Stat;
import com.mygdx.game.actor.traits.AbstractTrait;
import com.mygdx.game.actor.traits.TraitFlag;
import com.mygdx.game.actor.traits.pools.PoolFemale;
import com.mygdx.game.actor.traits.pools.PoolGeneric;
import com.mygdx.game.actor.traits.pools.PoolMale;
import com.mygdx.game.actor.traits.pools.TraitPool;

public class PartyCharacter {

	private static final int DEFAULT_MAX = 100;

	//Random Generators
	Random rn = new Random();

	//Name
	private String name;
	private Race race;
	private Gender gender;

	//Needs
	private int health, maxHealth;
	private int mana, maxMana;
	private int hunger, maxHunger;
	private int gold, maxGold;
	private int happiness, maxHappiness;

	//Stats
	private int fastTalk;
	private int pathfinder;
	private int sneak;
	private int combat;
	private int luck;

	//Traits
	private ArrayList<AbstractTrait> traits;
	private TraitPool genericTraitPool;
	private TraitPool femaleTraitPool;
	private TraitPool maleTraitPool;
	//Job
	private Job job;
	private HashMap<Job, Integer> jobSkills;

	private boolean alive;
	private CauseOfDeath causeOfDeath;

	public PartyCharacter()
	{
		//Initialise variables
		setAlive(true);
		jobSkills = new HashMap<Job, Integer>();

		traits = new ArrayList<AbstractTrait>();
		genericTraitPool = new PoolGeneric();
		femaleTraitPool = new PoolFemale();
		maleTraitPool = new PoolMale();

		//Assign gender
		if (rn.nextInt(2) == 0)
			gender = Gender.MALE;
		else
			gender = Gender.FEMALE;

		generateRace();
		generateName();
		setBaseNeed(Need.GOLD, rn.nextInt(20));
		setBaseNeed(Need.HAPPINESS, rn.nextInt(60) + 40);
		setBaseNeed(Need.HUNGER, 100);

		setBaseStat(Stat.COMBAT, rn.nextInt(20));
		setBaseStat(Stat.LUCK, rn.nextInt(20));
		setBaseStat(Stat.PATHFINDER, rn.nextInt(20));
		setBaseStat(Stat.SNEAK, rn.nextInt(20));
		setBaseStat(Stat.LUCK, rn.nextInt(20));

		//		Assign starting job values
		jobSkills.put(Job.ALCHEMIST, rn.nextInt(10));
		jobSkills.put(Job.BARD, rn.nextInt(10));
		jobSkills.put(Job.COOK, rn.nextInt(10));
		jobSkills.put(Job.HEALER, rn.nextInt(10));
		jobSkills.put(Job.MERCHANT, rn.nextInt(10));

		//Assign 2 positive/neutral traits
		if (!genericTraitPool.isPosNeuEmpty())
			traits.add(genericTraitPool.getRandomTrait());

		//20% chance of positive/neutral gender related trait otherwise normal trait
		if (rn.nextInt(5) == 0)
			if (!femaleTraitPool.isPosNeuEmpty() && getGender() == Gender.FEMALE)
				traits.add(femaleTraitPool.getRandomTrait());
		//else if (!maleTraitPool.isEmpty() && getGender() == Gender.MALE)
		//traits.add(maleTraitPool.getRandomTrait());
			else if (!genericTraitPool.isPosNeuEmpty())
				traits.add(genericTraitPool.getRandomTrait());

		//20% chance of gender related negative trait otherwise normal negative trait
		if (rn.nextInt(5) == 0)
		{
			if (!femaleTraitPool.isNegativeEmpty() && getGender() == Gender.FEMALE)
				traits.add(femaleTraitPool.getNegativeTrait());
			else if (!maleTraitPool.isNegativeEmpty() && getGender() == Gender.MALE)
				traits.add(maleTraitPool.getRandomTrait());
		}
		else if (!genericTraitPool.isNegativeEmpty())
		{
			traits.add(genericTraitPool.getNegativeTrait());
		}
	}

	private void generateName() {
		try {
			NameGenerator nameGen = new NameGenerator("humanNames.txt");
			setName(nameGen.compose(rn.nextInt(3)+1));
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
		setBaseNeeds(100, 100, 100, 100, 0);
		break;

		case 1: setRace(Race.GNOME);
		setBaseNeeds(75, 125, 100, 100, 0);
		break;

		case 2: setRace(Race.ELF);
		setBaseNeeds(90, 110, 100, 100, 0);
		break;

		case 3: setRace(Race.DWARF);
		setBaseNeeds(140, 60, 100, 100, 0);
		break;

		case 4: setRace(Race.HALFLING);
		setBaseNeeds(80, 120, 100, 100, 0);
		break;

		case 5: setRace(Race.ORC);
		setBaseNeeds(175, 25, 100, 100, 0);
		break;
		}
	}

	private void setBaseNeeds(int health, int mana, int hunger, int happiness, int gold)
	{
		this.setBaseNeed(Need.HEALTH, health);
		this.setBaseNeed(Need.MANA, mana);
		this.setBaseNeed(Need.HUNGER, hunger);
		this.setBaseNeed(Need.HAPPINESS, happiness);
		this.setBaseNeed(Need.GOLD, gold);

		this.setBaseNeed(Need.MAXHEALTH, health);
		this.setBaseNeed(Need.MAXMANA, mana);
		this.setBaseNeed(Need.MAXHUNGER, hunger);
		this.setBaseNeed(Need.MAXHAPPINESS, happiness);
		this.setBaseNeed(Need.MAXGOLD, DEFAULT_MAX);
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public void setAlive(boolean alive, CauseOfDeath causeOfDeath) {
		this.alive = alive;
		this.causeOfDeath = causeOfDeath;
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

	public void setBaseNeed(Need need, int value) {
		switch (need) {
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

	public int getBaseNeed(Need need) {
		switch (need) {
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

	public int getTrueNeed(Need need) {
		switch (need) {
		case HEALTH :
			int trueHealth = health;
			for(AbstractTrait t : traits) {
				t.getTrueHealth(trueHealth);
			}
			return trueHealth;
		case MAXHEALTH :
			int trueMaxHealth = maxHealth;
			for(AbstractTrait t : traits) {
				t.getTrueMaxHealth(trueMaxHealth);
			}
			return trueMaxHealth;
		case MANA :
			int trueMana = mana;
			for(AbstractTrait t : traits) {
				t.getTrueMana(trueMana);
			}
			return trueMana;
		case MAXMANA :
			int trueMaxMana = maxMana;
			for(AbstractTrait t : traits) {
				t.getTrueMaxMana(trueMaxMana);
			}
			return trueMaxMana;
		case HUNGER :
			int trueHunger = hunger;
			for(AbstractTrait t : traits) {
				t.getTrueHunger(trueHunger);
			}
			return trueHunger;
		case MAXHUNGER :
			int trueMaxHunger = maxHunger;
			for(AbstractTrait t : traits) {
				t.getTrueMaxHunger(trueMaxHunger);
			}
			return trueMaxHunger;
		case HAPPINESS :
			int trueHappiness = happiness;
			for(AbstractTrait t : traits) {
				t.getTrueHappiness(trueHappiness);
			}
			return trueHappiness;
		case MAXHAPPINESS :
			int trueMaxHappiness = maxHappiness;
			for(AbstractTrait t : traits) {
				t.getTrueMaxHappiness(trueMaxHappiness);
			}
			return trueMaxHappiness;
		case GOLD :
			int trueGold = gold;
			for(AbstractTrait t : traits) {
				t.getTrueGold(trueGold);
			}
			return trueGold;
		case MAXGOLD :
			int trueMaxGold = maxGold;
			for(AbstractTrait t : traits) {
				t.getTrueMaxGold(trueMaxGold);
			}
			return trueMaxGold;
		}
		return 0;
	}

	public void incrementNeed(Need need, int increment) {
		switch (need) {
		case HEALTH :
			if (maxHealth < getBaseNeed(need) + increment)
				setBaseNeed(need, maxHealth);
			else if (getBaseNeed(need) + increment < 0)
				setBaseNeed(need, 0);
			else
				setBaseNeed(need, getBaseNeed(need) + increment);
			break;
		case MANA :
			if (maxMana < getBaseNeed(need) + increment)
				setBaseNeed(need, maxMana);
			else if (getBaseNeed(need) + increment < 0)
				setBaseNeed(need, 0);
			else
				setBaseNeed(need, getBaseNeed(need) + increment);
			break;
		case HAPPINESS :
			if (maxHappiness < getBaseNeed(need) + increment)
				setBaseNeed(need, maxHappiness);
			else if (getBaseNeed(need) + increment < 0)
				setBaseNeed(need, 0);
			else
				setBaseNeed(need, getBaseNeed(need) + increment);
			break;
		case HUNGER :
			if (maxHunger < getBaseNeed(need) + increment)
				setBaseNeed(need, maxHunger);
			else if (getBaseNeed(need) + increment < 0)
				setBaseNeed(need, 0);
			else
				setBaseNeed(need, getBaseNeed(need) + increment);
			break;
		case GOLD :
			if (maxGold < getBaseNeed(need) + increment)
				setBaseNeed(need, maxGold);
			else if (getBaseNeed(need) + increment < 0)
				setBaseNeed(need, 0);
			else
				setBaseNeed(need, getBaseNeed(need) + increment);
			break;
		default :
			if (getBaseNeed(need) + increment < 0)
				setBaseNeed(need, 0);
			setBaseNeed(need, getBaseNeed(need) + increment);
			break;
		}

	}

	public void setBaseStat(Stat stat, int value) {
		switch (stat) {
		case COMBAT:
			this.combat = value;
			break;
		case FASTTALK:
			this.fastTalk = value;
			break;
		case LUCK:
			this.luck = value;
			break;
		case PATHFINDER:
			this.pathfinder = value;
			break;
		case SNEAK:
			this.sneak = value;
			break;
		}
	}

	public int getBaseStat(Stat stat) {
		switch (stat) {
		case COMBAT:
			return combat;
		case FASTTALK:
			return fastTalk;
		case LUCK:
			return luck;
		case PATHFINDER:
			return pathfinder;
		case SNEAK:
			return sneak;
		}
		return 0;
	}

	public int getTrueStat(Stat stat) {
		switch (stat) {
		case COMBAT:
			int trueCombat = combat;
			for(AbstractTrait t : traits) {
				t.getTrueCombat(trueCombat);
			}
			return trueCombat;
		case FASTTALK:
			int trueFastTalk = fastTalk;
			for(AbstractTrait t : traits) {
				t.getTrueCombat(trueFastTalk);
			}
			return trueFastTalk;
		case LUCK:
			int trueLuck = luck;
			for(AbstractTrait t : traits) {
				t.getTrueCombat(trueLuck);
			}
			return trueLuck;
		case PATHFINDER:
			int truePathfinder = pathfinder;
			for(AbstractTrait t : traits) {
				t.getTrueCombat(truePathfinder);
			}
			return truePathfinder;
		case SNEAK:
			int trueSneak = sneak;
			for(AbstractTrait t : traits) {
				t.getTrueCombat(trueSneak);
			}
			return trueSneak;
		}
		return 0;
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

	public Gender getGender() {
		return gender;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean hasFlag(TraitFlag flag) {
		for(AbstractTrait t : traits) {
			if(t.hasFlag(flag)) {
				return true;
			}
		}
		return false;
	}

	public void endTurn() {

		// Stops stats becoming super negative
		if(!this.isAlive())
			return;

		// Decrease needs based on what the character consumes per turn	
		hunger -= 1;
		happiness -= 1;
		gold -= 1;

		int trueHealth = health, trueMaxHealth = maxHealth;
		int trueMana = mana, trueMaxMana = maxMana;
		int trueHunger = hunger, trueMaxHunger = maxHunger;
		int trueGold = gold, trueMaxGold = maxGold;
		int trueHappiness = happiness, trueMaxHappiness = maxHappiness;

		for(AbstractTrait t : traits) {
			trueHealth = t.getTrueHealth(health);
			trueMaxHealth = t.getTrueMaxHealth(maxHealth);
			trueMana = t.getTrueMana(mana);
			trueMaxMana = t.getTrueMaxMana(maxMana);
			trueHunger = t.getTrueHunger(hunger);
			trueMaxHunger = t.getTrueMaxHunger(maxHunger);
			trueGold = t.getTrueGold(gold);
			trueMaxGold = t.getTrueMaxGold(maxGold);
			trueHappiness = t.getTrueHappiness(happiness);
			trueMaxHappiness = t.getTrueMaxHappiness(maxHappiness);

			t.act(this);	//Any other crazy changes to needs are done here.
		}

		System.out.println(maxHealth + " " + trueMaxHealth);
		System.out.println(maxHunger + " " + trueMaxHunger);

		if(trueHealth < 0) {
			setAlive(false, CauseOfDeath.HEALTH);
		}
		if(trueHunger < 0) {
			setAlive(false, CauseOfDeath.HUNGER);
		}
		if(trueGold < 0) {
			setAlive(false, CauseOfDeath.GOLD);
		}
	}

	public ArrayList<AbstractTrait> getTraits() {
		// TODO Auto-generated method stub
		return traits;
	}
}
