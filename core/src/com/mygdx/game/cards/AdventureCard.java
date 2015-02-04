package com.mygdx.game.cards;

import java.util.ArrayList;
import java.util.List;

public abstract class AdventureCard {

	protected String title, description;
	protected List<Choice> choices;
	
	public AdventureCard(String title, String description, Choice... choices) {
		this.title = title;
		this.description = description;
		this.choices = new ArrayList<Choice>();
		
		java.util.Collections.addAll(this.choices, choices);
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public List<Choice> getChoices() {
		return choices;
	}

}
