/*******************************************************************************
 * Copyright 2014 See AUTHORS file.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.mygdx.game.systems.ui;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.components.primitive.MultiTextureComponent;
import com.mygdx.game.components.primitive.TextComponent;
import com.mygdx.game.components.primitive.TextureComponent;
import com.mygdx.game.components.ui.CardDisplayComponent;
import com.mygdx.game.components.ui.UiMouseActivityComponent;
import com.mygdx.game.components.ui.UiPositionComponent;
import com.mygdx.game.entities.ui.UiButtonEntity;
import com.mygdx.game.mouse.Mouse;
import com.mygdx.game.mouse.Mouse.MousePos;

public class UiSystem extends EntitySystem implements InputProcessor {
	
	private ImmutableArray<Entity> uiImages;
	private ImmutableArray<Entity> uiButtons;

	private SpriteBatch batch;
	private OrthographicCamera camera;

	private ComponentMapper<UiPositionComponent> pm = ComponentMapper.getFor(UiPositionComponent.class);
	private ComponentMapper<TextureComponent> tm = ComponentMapper.getFor(TextureComponent.class);
	private ComponentMapper<MultiTextureComponent> tmm = ComponentMapper.getFor(MultiTextureComponent.class);
	private ComponentMapper<UiMouseActivityComponent> mm = ComponentMapper.getFor(UiMouseActivityComponent.class);
	private ComponentMapper<TextComponent> txtm = ComponentMapper.getFor(TextComponent.class);
	private ComponentMapper<CardDisplayComponent> cm = ComponentMapper.getFor(CardDisplayComponent.class);
	
	public UiSystem (OrthographicCamera camera) {
		batch = new SpriteBatch();
	
		this.camera = camera;
		
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void addedToEngine (Engine engine) {
		uiImages = engine.getEntitiesFor(Family.getFor(UiPositionComponent.class, TextureComponent.class));
		uiButtons = engine.getEntitiesFor(Family.getFor(UiPositionComponent.class, MultiTextureComponent.class, UiMouseActivityComponent.class, TextComponent.class));
	}

	@Override
	public void removedFromEngine (Engine engine) {

	}

	@Override
	public void update (float deltaTime) {
		UiPositionComponent position;
		TextureComponent visual;
		MultiTextureComponent multiVisual;
		UiMouseActivityComponent mouse;
		TextComponent text;
		CardDisplayComponent card = null;
		
		BitmapFont font = new BitmapFont();

		camera.update();

		batch.begin();
		batch.setProjectionMatrix(camera.combined);
 		// Draw UI
		for (int i = 0; i < uiImages.size(); ++i) {
			Entity e = uiImages.get(i);

			position = pm.get(e);
			visual = tm.get(e);
			
			if(visual.visible)
				batch.draw(visual.region, position.x, position.y);
		}
		for (int i = 0; i < uiButtons.size(); ++i) {
			Entity e = uiButtons.get(i);

			position = pm.get(e);
			multiVisual = tmm.get(e);
			text = txtm.get(e);
			
			if(multiVisual.visible) {
				TextureRegion region = multiVisual.regions.get(multiVisual.frame);
				batch.draw(region, position.x, position.y);
				font.draw(batch, text.text, position.x + region.getRegionWidth() / 6, (int)(position.y + region.getRegionHeight() * 0.65));
			}
		}
		
		for (int i = 0; i < uiButtons.size(); ++i) {
			Entity e = uiButtons.get(i);

			position = pm.get(e);
			multiVisual = tmm.get(e);
			mouse = mm.get(e);
			card = cm.get(e);
			
			if(multiVisual.visible) {
				MousePos mousePos = Mouse.getMouse();
				
				TextureRegion region = multiVisual.regions.get(multiVisual.frame);
				
				boolean isInBounds = position.x <= mousePos.x &&
									 position.y <= mousePos.y &&
									 position.x + region.getRegionWidth() >= mousePos.x  &&
									 position.y + region.getRegionHeight() >= mousePos.y;
									 
				UiButtonEntity btn = (UiButtonEntity) e;
				
				if(isInBounds && mouse.mouseActive){
					btn.mI(mousePos.x, mousePos.y);
			    }
				else if(mouse.mouseActive) {
					btn.mO(mousePos.x, mousePos.y);
				}
			}
		}
		
		batch.end();
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		
		UiPositionComponent position;
		TextureComponent visual;
		MultiTextureComponent multiVisual;
		UiMouseActivityComponent mouse;
		TextComponent text;
		CardDisplayComponent card = null;
		
		for (int i = 0; i < uiButtons.size(); ++i) {
			Entity e = uiButtons.get(i);

			position = pm.get(e);
			multiVisual = tmm.get(e);
			mouse = mm.get(e);
			card = cm.get(e);
			
			if(multiVisual.visible) {
				MousePos mousePos = Mouse.getMouse();
				
				TextureRegion region = multiVisual.regions.get(multiVisual.frame);
				
				boolean isInBounds = position.x <= mousePos.x &&
									 position.y <= mousePos.y &&
									 position.x + region.getRegionWidth() >= mousePos.x  &&
									 position.y + region.getRegionHeight() >= mousePos.y;
									 
				UiButtonEntity btn = (UiButtonEntity) e;
				
				if(isInBounds && mouse.mouseActive && Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
					if(btn.mD(mousePos.x, mousePos.y))
						break;
				}
			}
			
		}
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		
		UiPositionComponent position;
		TextureComponent visual;
		MultiTextureComponent multiVisual;
		UiMouseActivityComponent mouse;
		TextComponent text;
		CardDisplayComponent card = null;
		
		for (int i = 0; i < uiButtons.size(); ++i) {
			Entity e = uiButtons.get(i);

			position = pm.get(e);
			multiVisual = tmm.get(e);
			mouse = mm.get(e);
			card = cm.get(e);
			
			if(multiVisual.visible) {
				MousePos mousePos = Mouse.getMouse();
				
				TextureRegion region = multiVisual.regions.get(multiVisual.frame);
				
				boolean isInBounds = position.x <= mousePos.x &&
									 position.y <= mousePos.y &&
									 position.x + region.getRegionWidth() >= mousePos.x  &&
									 position.y + region.getRegionHeight() >= mousePos.y;
									 
				UiButtonEntity btn = (UiButtonEntity) e;
				
				if(isInBounds && mouse.mouseActive && !Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
					if(btn.mU(mousePos.x, mousePos.y))
						break;
				}
			}
			
		}
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
}
