package com.mygdx.game.actor;

import java.util.ArrayList;
import java.util.List;

public class CharacterBank {

	private static final int PARTY_MEMBERS = 5;
	
	public List<PartyCharacter> characters;
	
	public CharacterBank() {
		this.characters = new ArrayList<PartyCharacter>();
		
		for(int i = 0; i < PARTY_MEMBERS; i++) {
			this.characters.add(new PartyCharacter() {
				
				@Override
				public String getTrait() {
					return null;
				}
			});
		}
	}
	
}
