package com.mygdx.game.components.ui;

import com.badlogic.ashley.core.Component;

public class UiButtonDownComponent extends Component {
	public boolean down;
	
	public UiButtonDownComponent(boolean down) {
		this.down = down;
	}
}
