package com.dougstoker.flappy;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import java.util.Random;

public class Flappy extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Texture bg;
	Texture[] bird;
	Texture topTube;
	Texture bottomTube;
	int flapstate = 0;
	float birdYpos = 0;
	float birdYvel = 0;
	boolean go = false;
	int flaptimer = 30;
	int score = 0;
	int timeTube;
	float tubeGap = 750;
	Rectangle birdr;
	Rectangle tTube;
	Rectangle bTube;
	BitmapFont font;
	Random rand = new Random();
	Sprite birdSprite;
	Sprite tTubeSprite;
	Sprite bTubeSprite;
	@Override
	public void create () {
		int eighthWidth = Gdx.graphics.getWidth() / 8;
		timeTube = Gdx.graphics.getWidth()*2;
		batch = new SpriteBatch();
		bg = new Texture("bg.png");
		bird = new Texture[2];
		bird[0] = new Texture("bird.png");
		bird[1] = new Texture("bird2.png");
		birdYpos = (Gdx.graphics.getHeight()/2)-(Gdx.graphics.getWidth() / 8);
		topTube = new Texture("toptube.png");
		bottomTube = new Texture("bottomtube.png");
		font = new BitmapFont();
		font.getData().setScale(8.0f);
		flapstate = 0;
//		birdSprite = new Sprite(bird[0]);
//		birdSprite.setSize(eighthWidth,eighthWidth);
		tTube = new Rectangle();
		bTube = new Rectangle();
		birdr = new Rectangle();


		



	}

	@Override
	public void render () {
		//ScreenUtils.clear(1, 0, 0, 1);

		int eighthWidth = Gdx.graphics.getWidth() / 8;
		int sixteenthWidth = eighthWidth / 2;
		int quarterWidth = eighthWidth * 2;
		int halfWidth = Gdx.graphics.getWidth() / 2;



		if(Gdx.input.justTouched()){
			go = true;
			birdYvel= 20;
			flapstate = 1;
			flaptimer = 20;

		}
		if(go){
			if(birdYvel > -20){
			birdYvel --;}
			birdYpos += birdYvel;
			if(flaptimer > 0){
				flaptimer -=1;
			}
			if(flaptimer == 0){
				flapstate = 0;
			}

		}
		if(timeTube < -eighthWidth & go){
			tubeGap = rand.nextInt(900) + 300;
			score += 1;
			timeTube = Gdx.graphics.getWidth();

		}
		else if (go) {
			timeTube -=10;
		}
		tTube.set((timeTube)-eighthWidth,Gdx.graphics.getHeight()-tubeGap,quarterWidth,Gdx.graphics.getHeight());
		bTube.set((timeTube)-eighthWidth,Gdx.graphics.getHeight()-tubeGap-Gdx.graphics.getHeight()-(quarterWidth*2),quarterWidth,Gdx.graphics.getHeight());
		//birdSprite.setPosition(halfWidth-sixteenthWidth, birdYpos);
		birdr.set(halfWidth-sixteenthWidth, birdYpos,eighthWidth,eighthWidth);



		if(birdYpos < 0 | birdYpos > Gdx.graphics.getHeight() | tTube.overlaps(birdr) | bTube.overlaps(birdr)){
			go = false;
			birdYpos = (Gdx.graphics.getHeight()/2)-(Gdx.graphics.getWidth() / 8);
			birdYvel = 0;
			flapstate = 0;
			score = 0;
			timeTube =  Gdx.graphics.getWidth()*2;
		}





		batch.begin();
		batch.draw(bg, 0, 0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		if(go){
			batch.draw(topTube,(timeTube)-eighthWidth,Gdx.graphics.getHeight()-tubeGap,quarterWidth,Gdx.graphics.getHeight());
			batch.draw(bottomTube,(timeTube)-eighthWidth,Gdx.graphics.getHeight()-tubeGap-Gdx.graphics.getHeight()-(quarterWidth*2),quarterWidth,Gdx.graphics.getHeight());
		}
		batch.draw(bird[flapstate], halfWidth-sixteenthWidth, birdYpos,eighthWidth,eighthWidth);
		font.draw(batch, Integer.toString(score), halfWidth, 200);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
