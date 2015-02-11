package com.mygdx.game.actor;

import java.util.ArrayList;
import java.util.List;

import com.mygdx.game.actor.enums.Job;
import com.mygdx.game.utils.calendar.Calendar;

public class CharacterBank {

	private static final int PARTY_MEMBERS = 5;

	private List<PartyCharacter> characters;

	private static CharacterBank instance;
	
	static {
		instance = new CharacterBank();
	}
	
	public static CharacterBank getInstance() {
		return instance;
	}
	
	
	public CharacterBank() {
		characters = new ArrayList<PartyCharacter>();

		for(int i = 0; i < PARTY_MEMBERS; i++) {
			characters.add(new PartyCharacter());
		}
	}

	public boolean isAssignedJob(Job job) {
		for(PartyCharacter character:characters) {
			if(character.getJob() == job)
				return true;
		}
		return false;
	}

	public List<PartyCharacter> getCharacters() {
		return characters;
	}
	
	public void addCharacter(PartyCharacter character) {
		characters.add(character);
	}

	public void removeCharacter(PartyCharacter character) {
		characters.remove(character);
	}

}
