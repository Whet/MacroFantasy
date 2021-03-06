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
import com.badlogic.ashley.core.ComponentType;
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
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.components.primitive.MultiRegionComponent;
import com.mygdx.game.components.primitive.MultiTextureComponent;
import com.mygdx.game.components.primitive.TextComponent;
import com.mygdx.game.components.primitive.TextureComponent;
import com.mygdx.game.components.ui.BarComponent;
import com.mygdx.game.components.ui.CardDisplayComponent;
import com.mygdx.game.components.ui.UiMouseActivityComponent;
import com.mygdx.game.components.ui.UiPositionComponent;
import com.mygdx.game.entities.ui.BarEntity;
import com.mygdx.game.entities.ui.CharacterImageEntity;
import com.mygdx.game.entities.ui.TextButtonEntity;
import com.mygdx.game.entities.ui.UiButtonEntity;
import com.mygdx.game.entities.ui.UiMultiButtonEntity;
import com.mygdx.game.mouse.Mouse;
import com.mygdx.game.mouse.Mouse.MousePos;
import com.mygdx.game.sprites.SpriteAnimation;

public class UiSystem extends EntitySystem implements InputProcessor {
	
	// Stores particular entities (Collections of components with added methods)
	private ImmutableArray<Entity> uiImages;
	private ImmutableArray<Entity> uiButtons;
	private ImmutableArray<Entity> uiMultiButtons;
	private ImmutableArray<Entity> multiImages;
	private ImmutableArray<Entity> bars;
	private ImmutableArray<Entity> text;
	private ImmutableArray<Entity> textButtons;
	
	// Rendering variables
	private SpriteBatch batch;
	private ShapeRenderer shape;
	private OrthographicCamera camera;

	// Lists containing all entities with given individual components
	private ComponentMapper<UiPositionComponent> pm = ComponentMapper.getFor(UiPositionComponent.class);
	private ComponentMapper<TextureComponent> tm = ComponentMapper.getFor(TextureComponent.class);
	private ComponentMapper<MultiTextureComponent> tmm = ComponentMapper.getFor(MultiTextureComponent.class);
	private ComponentMapper<MultiRegionComponent> mrm = ComponentMapper.getFor(MultiRegionComponent.class);
	private ComponentMapper<UiMouseActivityComponent> mm = ComponentMapper.getFor(UiMouseActivityComponent.class);
	private ComponentMapper<TextComponent> txtm = ComponentMapper.getFor(TextComponent.class);
	private ComponentMapper<CardDisplayComponent> cm = ComponentMapper.getFor(CardDisplayComponent.class);
	private ComponentMapper<BarComponent> bm = ComponentMapper.getFor(BarComponent.class);
	
	private float stateTime = 0.f;
	
	private SpriteAnimation exampleAnim;
	
	public UiSystem (OrthographicCamera camera) {
		batch = new SpriteBatch();
		shape = new ShapeRenderer();
		shape.setAutoShapeType(true);
		
		this.camera = camera;
		
		Gdx.input.setInputProcessor(this);
		
		String[] frameNames = {"butts01", "butts02", "butts03", "butts04", "butts05", "butts06"};
		exampleAnim = new SpriteAnimation("butts.txt", frameNames);
	}

	// Update lists of entities when something is added
	@Override
	public void addedToEngine (Engine engine) {
		uiImages = engine.getEntitiesFor(Family.getFor(UiPositionComponent.class, TextureComponent.class));
		uiButtons = engine.getEntitiesFor(Family.getFor(UiPositionComponent.class, MultiTextureComponent.class, UiMouseActivityComponent.class, TextComponent.class));
		uiMultiButtons = engine.getEntitiesFor(Family.getFor(UiPositionComponent.class, MultiRegionComponent.class, UiMouseActivityComponent.class));
		multiImages = engine.getEntitiesFor(Family.getFor(UiPositionComponent.class, MultiRegionComponent.class));
		bars = engine.getEntitiesFor(Family.getFor(UiPositionComponent.class, BarComponent.class));
		text = engine.getEntitiesFor(Family.getFor(ComponentType.getBitsFor(TextComponent.class, UiPositionComponent.class), ComponentType.getBitsFor(), ComponentType.getBitsFor(TextureComponent.class, MultiTextureComponent.class, MultiRegionComponent.class)));
		textButtons = engine.getEntitiesFor(Family.getFor(ComponentType.getBitsFor(TextComponent.class, UiPositionComponent.class, UiMouseActivityComponent.class), ComponentType.getBitsFor(), ComponentType.getBitsFor(TextureComponent.class, MultiTextureComponent.class, MultiRegionComponent.class)));
	}

	@Override
	public void removedFromEngine (Engine engine) {

	}

	@Override
	public void update (float deltaTime) {
		UiPositionComponent position;
		TextureComponent visual;
		MultiTextureComponent multiVisual;
		MultiRegionComponent multiRegion;
		UiMouseActivityComponent mouse;
		TextComponent text;
		BarComponent bar;
		
		stateTime += deltaTime;
		exampleAnim.update(stateTime);
		
		camera.update();

		batch.begin();
		batch.setProjectionMatrix(camera.combined);
		
		shape.begin();
		shape.setProjectionMatrix(camera.combined);
		
 		// Draw UI
		for (int i = 0; i < uiImages.size(); ++i) {
			Entity e = uiImages.get(i);

			position = pm.get(e);
			visual = tm.get(e);
			
			if(visual.visible)
				batch.draw(visual.region, position.x, position.y);
		}
		for (int i = 0; i < multiImages.size(); ++i) {
			Entity e = multiImages.get(i);

			position = pm.get(e);
			multiRegion = mrm.get(e);
			
			if(multiRegion.visible)
				for(TextureRegion region:multiRegion.regions) {
					batch.draw(region, position.x, position.y);
				}
		}
		for (int i = 0; i < uiButtons.size(); ++i) {
			Entity e = uiButtons.get(i);

			position = pm.get(e);
			multiVisual = tmm.get(e);
			text = txtm.get(e);
			
			if(multiVisual.visible) {
				TextureRegion region = multiVisual.regions.get(multiVisual.frame);
				batch.draw(region, position.x, position.y);
				
				if(text.visible) {
					text.font.draw(batch, text.text, position.x + 15, position.y + 30);
				}
			}
		}
		for (int i = 0; i < this.text.size(); ++i) {
			Entity e = this.text.get(i);

			position = pm.get(e);
			text = txtm.get(e);
			
			if(text.visible) {
				
				// Split text into lines
				StringBuffer sb = new StringBuffer();
				int index = 0;
				int line = 0;
				while(index < text.text.length()) {
					if((sb.length() < text.maxCharsPerLine || text.text.charAt(index) != ' ') && text.text.charAt(index) != '@') {
						sb.append(text.text.charAt(index));
					}
					else {
						text.font.draw(batch, sb.toString(), position.x, position.y - 20 * line);
						sb = new StringBuffer();
						line++;
					}
					index++;
				}
				text.font.draw(batch, sb.toString(), position.x, position.y - 20 * line);
			}
		}
		for (int i = 0; i < bars.size(); ++i) {
			BarEntity e = (BarEntity) bars.get(i);

			position = pm.get(e);
			bar = bm.get(e);
			
			if(bar.visible) {
				shape.setColor(bar.colour);
				shape.set(ShapeRenderer.ShapeType.Filled);
				
				float percent = e.getValue() / (float)bar.max;
				float drawWidth = bar.width * percent;
				shape.rect(position.x, position.y, drawWidth, bar.height);
			}
		}
		
		for (int i = 0; i < uiButtons.size(); ++i) {
			Entity e = uiButtons.get(i);

			position = pm.get(e);
			multiVisual = tmm.get(e);
			mouse = mm.get(e);
			
			if(multiVisual.visible) {
				MousePos mousePos = Mouse.getMouse();
				
				TextureRegion region = multiVisual.regions.get(multiVisual.frame);
				
				boolean isInBounds = position.x <= mousePos.x &&
									 position.y <= mousePos.y &&
									 position.x + region.getRegionWidth() >= mousePos.x  &&
									 position.y + region.getRegionHeight() >= mousePos.y;
									 
				UiButtonEntity btn = (UiButtonEntity) e;
				
				if(isInBounds && mouse.mouseActive){
					btn.mouseIn(mousePos.x, mousePos.y);
			    }
				else if(mouse.mouseActive) {
					btn.mouseOut(mousePos.x, mousePos.y);
				}
			}
		}
		for (int i = 0; i < uiMultiButtons.size(); ++i) {
			Entity e = uiMultiButtons.get(i);

			position = pm.get(e);
			multiRegion = mrm.get(e);
			mouse = mm.get(e);
			
			if(multiRegion.visible) {
				MousePos mousePos = Mouse.getMouse();
				
				TextureRegion region = multiRegion.regions.get(0);
				
				boolean isInBounds = position.x <= mousePos.x &&
									 position.y <= mousePos.y &&
									 position.x + region.getRegionWidth() >= mousePos.x  &&
									 position.y + region.getRegionHeight() >= mousePos.y;
									 
				UiMultiButtonEntity btn = (UiMultiButtonEntity) e;
				
				if(isInBounds && mouse.mouseActive){
					btn.mouseIn(mousePos.x, mousePos.y);
			    }
				else if(mouse.mouseActive) {
					btn.mouseOut(mousePos.x, mousePos.y);
				}
			}
			
		}
		for (int i = 0; i < textButtons.size(); ++i) {
			Entity e = textButtons.get(i);

			position = pm.get(e);
			mouse = mm.get(e);
			text = txtm.get(e);
			
			if(text.visible) {
				MousePos mousePos = Mouse.getMouse();
				
				boolean isInBounds = position.x <= mousePos.x &&
									 position.y <= mousePos.y &&
									 position.x + 50 >= mousePos.x  &&
									 position.y + 50 >= mousePos.y;
									 
				TextButtonEntity btn = (TextButtonEntity) e;
				
				if(isInBounds && mouse.mouseActive){
					btn.mouseIn(mousePos.x, mousePos.y);
			    }
				else if(mouse.mouseActive) {
					btn.mouseOut(mousePos.x, mousePos.y);
				}
			}
			
		}
		
		exampleAnim.draw(batch, 100, 100);
		
		batch.end();
		shape.end();
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

	// Mouse down events
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		UiPositionComponent position;
		MultiTextureComponent multiVisual;
		MultiRegionComponent multiRegion;
		UiMouseActivityComponent mouse;
		TextComponent text;
		
		for (int i = 0; i < uiButtons.size(); ++i) {
			Entity e = uiButtons.get(i);

			position = pm.get(e);
			multiVisual = tmm.get(e);
			mouse = mm.get(e);
			
			if(multiVisual.visible) {
				MousePos mousePos = Mouse.getMouse();
				
				TextureRegion region = multiVisual.regions.get(multiVisual.frame);
				
				boolean isInBounds = position.x <= mousePos.x &&
									 position.y <= mousePos.y &&
									 position.x + region.getRegionWidth() >= mousePos.x  &&
									 position.y + region.getRegionHeight() >= mousePos.y;
									 
				UiButtonEntity btn = (UiButtonEntity) e;
				
				if(isInBounds && mouse.mouseActive) {
					if(btn.mouseDown(mousePos.x, mousePos.y, button)){
						break;
					}
				}
			}
			
		}
		for (int i = 0; i < uiMultiButtons.size(); ++i) {
			Entity e = uiMultiButtons.get(i);

			position = pm.get(e);
			multiRegion = mrm.get(e);
			mouse = mm.get(e);
			
			if(multiRegion.visible) {
				MousePos mousePos = Mouse.getMouse();
				
				TextureRegion region = multiRegion.regions.get(0);
				
				boolean isInBounds = position.x <= mousePos.x &&
									 position.y <= mousePos.y &&
									 position.x + region.getRegionWidth() >= mousePos.x  &&
									 position.y + region.getRegionHeight() >= mousePos.y;
									 
				UiMultiButtonEntity btn = (UiMultiButtonEntity) e;
				
				if(isInBounds && mouse.mouseActive) {
					if(btn.mouseDown(mousePos.x, mousePos.y, button))
						break;
				}
			}
			
		}
		for (int i = 0; i < textButtons.size(); ++i) {
			Entity e = textButtons.get(i);

			position = pm.get(e);
			mouse = mm.get(e);
			text = txtm.get(e);
			
			if(text.visible) {
				MousePos mousePos = Mouse.getMouse();
				
				TextBounds bounds = text.font.getBounds(text.text);
				
				boolean isInBounds = mousePos.x > position.x &&
						mousePos.y < position.y &&
						mousePos.x < position.x + bounds.width &&
						mousePos.y > position.y - bounds.height;
						
				TextButtonEntity btn = (TextButtonEntity) e;
				
				if(isInBounds && mouse.mouseActive) {
					if(btn.mouseDown(mousePos.x, mousePos.y, button))
						break;
				}
			}
			
		}
		return true;
	}

	// Mouse up events
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		
		UiPositionComponent position;
		MultiTextureComponent multiVisual;
		MultiRegionComponent multiRegion;
		UiMouseActivityComponent mouse;
		TextComponent text;
		
		for (int i = 0; i < uiButtons.size(); ++i) {
			Entity e = uiButtons.get(i);

			position = pm.get(e);
			multiVisual = tmm.get(e);
			mouse = mm.get(e);
			
			if(multiVisual.visible) {
				MousePos mousePos = Mouse.getMouse();
				
				TextureRegion region = multiVisual.regions.get(multiVisual.frame);
				
				boolean isInBounds = position.x <= mousePos.x &&
									 position.y <= mousePos.y &&
									 position.x + region.getRegionWidth() >= mousePos.x  &&
									 position.y + region.getRegionHeight() >= mousePos.y;
									 
				UiButtonEntity btn = (UiButtonEntity) e;
				
				if(isInBounds && mouse.mouseActive) {
					if(btn.mouseUp(mousePos.x, mousePos.y, button))
						break;
				}
			}
			
		}
		for (int i = 0; i < uiMultiButtons.size(); ++i) {
			Entity e = uiMultiButtons.get(i);

			position = pm.get(e);
			multiRegion = mrm.get(e);
			mouse = mm.get(e);
			
			if(multiRegion.visible) {
				MousePos mousePos = Mouse.getMouse();
				
				TextureRegion region = multiRegion.regions.get(0);
				
				boolean isInBounds = position.x <= mousePos.x &&
									 position.y <= mousePos.y &&
									 position.x + region.getRegionWidth() >= mousePos.x  &&
									 position.y + region.getRegionHeight() >= mousePos.y;
									 
				UiMultiButtonEntity btn = (UiMultiButtonEntity) e;
				
				if(isInBounds && mouse.mouseActive) {
					if(btn.mouseUp(mousePos.x, mousePos.y, button))
						break;
				}
			}
			
		}
		for (int i = 0; i < textButtons.size(); ++i) {
			Entity e = textButtons.get(i);

			position = pm.get(e);
			mouse = mm.get(e);
			text = txtm.get(e);
			
			if(text.visible) {
				MousePos mousePos = Mouse.getMouse();
				
				TextBounds bounds = text.font.getBounds(text.text);
				
				boolean isInBounds = mousePos.x > position.x &&
						mousePos.y < position.y &&
						mousePos.x < position.x + bounds.width &&
						mousePos.y > position.y - bounds.height;
									 
				TextButtonEntity btn = (TextButtonEntity) e;
				
				if(isInBounds && mouse.mouseActive) {
					if(btn.mouseUp(mousePos.x, mousePos.y, button))
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
