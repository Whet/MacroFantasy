package com.mygdx.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.components.primitive.TextureComponent;
import com.mygdx.game.components.ui.UiPositionComponent;
import com.mygdx.game.entities.ui.UiButtonEntity;
import com.mygdx.game.systems.ui.UiSystem;

public class MacroFantasy extends ApplicationAdapter {
	Engine engine;

	@Override
	public void create () {
		OrthographicCamera camera = new OrthographicCamera(640, 480);
		camera.position.set(320, 240, 0);
		camera.update();

		Texture crateTexture = new Texture("crate.png");

		engine = new Engine();
		engine.addSystem(new UiSystem(camera));

		TextureRegion crateTextureRegion = new TextureRegion(crateTexture);
		
		UiButtonEntity testButton = new UiButtonEntity(200, 200, crateTextureRegion) {
			
			@Override
			public boolean mD(int x, int y) {
				UiPositionComponent position = this.getComponent(UiPositionComponent.class);
				TextureComponent texture = this.getComponent(TextureComponent.class);
				position.x = x - texture.region.getRegionWidth()/2;
				position.y = y - texture.region.getRegionHeight()/2;
				return true;
			}
			
		};

		engine.addEntity(testButton);

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		engine.update(Gdx.graphics.getDeltaTime());
	}
	
}
