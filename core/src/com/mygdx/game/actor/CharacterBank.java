package com.mygdx.game.actor;

import java.util.ArrayList;
import java.util.List;

import com.mygdx.game.actor.enums.Job;
import com.mygdx.game.utils.calendar.Calendar;

public class CharacterBank {

	private static final int PARTY_MEMBERS = 5;

	private PartyCharacter[] characters;

	private static CharacterBank instance;
	
	static {
		instance = new CharacterBank();
	}
	
	public static CharacterBank getInstance() {
		return instance;
	}
	
	
	private CharacterBank() {
		characters = new PartyCharacter[5];

		for(int i = 0; i < PARTY_MEMBERS; i++) {
			characters[i] = new PartyCharacter();
		}
	}

	public boolean isAssignedJob(Job job) {
		for(PartyCharacter character:getIterableCharacters()) {
			if(character.getJob() == job)
				return true;
		}
		return false;
	}

	public List<PartyCharacter> getIterableCharacters() {
		List<PartyCharacter> characters = new ArrayList<PartyCharacter>();
		
		for(PartyCharacter character:this.characters) {
			if(character != null)
				characters.add(character);
		}
		
		return characters;
	}
	
	public PartyCharacter[] getCharacters() {
		return characters;
	}
	
	public void addCharacter(PartyCharacter character) {
		for(int i = 0; i < characters.length; i++) {
			if(characters[i] == null) {
				characters[i] = character;
				break;
			}
		}
	}

	public void removeCharacter(PartyCharacter character) {
		for(int i = 0; i < characters.length; i++) {
			if(characters[i] == character) {
				characters[i] = null;
				break;
			}
		}
	}

}
