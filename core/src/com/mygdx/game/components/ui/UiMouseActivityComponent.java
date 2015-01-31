package com.mygdx.game.components.ui;

import com.badlogic.ashley.core.Component;

public class UiMouseActivityComponent extends Component {

	public boolean mouseActive;
	public int activityZ;
	
	public UiMouseActivityComponent() {
		this.mouseActive = true;
		this.activityZ = 0;
	}
	
}
