package com.mygdx.game.mouse;

import com.badlogic.gdx.Gdx;

public class Mouse {

	// Invert mouse to sync the graphics and action coordinates
	public static MousePos getMouse() {
		int x = Gdx.input.getX();
		int y = Gdx.graphics.getHeight() - Gdx.input.getY();
		return new MousePos(x, y);
	}
	
	public static class MousePos {
		public int x, y;
		
		public MousePos(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	
}
