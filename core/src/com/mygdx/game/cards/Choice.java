package com.mygdx.game.cards;

public class Choice {

	private AdventureCard nextCard;
	private AdventureEffect reward;
	private AdventureEffect penalty;
	private String text;

	public Choice(String text, AdventureCard nextCard) {
		this.text = text;
		this.nextCard = nextCard;
	}
	
	public Choice(String text, AdventureEffect reward, AdventureEffect penalty) {
		this.text = text;
		this.reward = reward;
		this.penalty = penalty;
	}

	public AdventureCard getNextCard() {
		return nextCard;
	}

	public AdventureEffect getReward() {
		return reward;
	}

	public AdventureEffect getPenalty() {
		return penalty;
	}

	public String getText() {
		return text;
	}

	public void choose() {
		// Use to apply dice rolls etc.
	}
	
}
