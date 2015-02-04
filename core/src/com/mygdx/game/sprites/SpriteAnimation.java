package com.mygdx.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class SpriteAnimation
{
	private TextureAtlas textureAtlas;
	private int frameCount;
	private Animation animation;
	private static final float FRAME_DURATION = 0.1f;
	private float scale;
	private float stateTime;
	
	/**
	 * Retrieve all sprites listed in array of region IDs in order, scaling each frame.
	 * @param location	Location of the spritesheet text file.
	 * @param scale		Amount each sprite should be scaled by.
	 * @param regionIDs	Array of IDs given to each sprite (by default this is the names of each image used to produce the sheet).
	 */
	public SpriteAnimation(String location, float scale, String[] regionIDs) {
		this.scale = scale;
		textureAtlas = new TextureAtlas(Gdx.files.internal(location));
		
		frameCount = regionIDs.length;		
		TextureRegion[] frameRegions = new TextureRegion[frameCount];
		for(int i=0; i<frameCount; i++)
		{
			TextureRegion region = textureAtlas.findRegion(regionIDs[i]);
			frameRegions[i] = region;
		}		
		animation = new Animation(FRAME_DURATION, frameRegions);
	}
	
	/**
	 * Retrieve all sprites listed in array of region IDs in order.
	 * @param location	Location of the spritesheet text file.
	 * @param regionIDs	Array of IDs given to each sprite (by default this is the names of each image used to produce the sheet).
	 */
	public SpriteAnimation(String location, String[] regionIDs) {
		this(location, 1.0f, regionIDs);
	}
	
	/**
	 * Retrieve every sprite from a sheet to create the animation, scaling each frame.
	 * @param location	Location of the spritesheet text file.
	 * @param scale		Amount each sprite should be scaled by.
	 */
	public SpriteAnimation(String location, float scale) {
		this.scale = scale;
		textureAtlas = new TextureAtlas(Gdx.files.internal(location));		
		Array<AtlasRegion> tr = textureAtlas.getRegions();
		frameCount = tr.size;
		
		TextureRegion[] frameRegions = new TextureRegion[frameCount];
		for(int i=0; i<frameCount; i++)
		{
			frameRegions[i] = textureAtlas.findRegion(Integer.toString(i));
		}		
		animation = new Animation(FRAME_DURATION, frameRegions);
	}
	
	/**
	 * Retrieve every sprite from a sheet to create the animation.
	 * @param location	Location of the spritesheet text file.
	 */
	public SpriteAnimation(String location) {
		this(location, 1.0f);
	}
	
	public void update(float stateTime)
	{
		this.stateTime = stateTime;
	}
	
	/**
	 * Draws sprite frame where centre is (x,y).
	 * @param x	X co-ordinate of centre.
	 * @param y	Y co-ordinate of centre.
	 */
	public void draw(SpriteBatch batch, float x, float y)
	{
		Sprite sprite = new Sprite(animation.getKeyFrame(stateTime, true));
		sprite.setCenter(x, y);
		sprite.setScale(scale);
		sprite.draw(batch);
	}
	
	public void dispose()
	{
		textureAtlas.dispose();
	}
	
	public float getWidth() {
		Sprite sprite = new Sprite(animation.getKeyFrame(stateTime, true));
		return sprite.getWidth() * scale;
	}
	
	public float getHeight() {
		Sprite sprite = new Sprite(animation.getKeyFrame(stateTime, true));
		return sprite.getWidth() * scale;
	}
}
