/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
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

package com.badlogicgames.superjumper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class HelpScreen extends ScreenAdapter {
	SuperJumper game;

	OrthographicCamera guiCam;
	Rectangle nextBounds;
	Rectangle backBounds;
	Vector3 touchPoint;
	int fileCounter;
	String helpFiles[];
	Texture[] helpImage;
	TextureRegion[] helpRegion = new TextureRegion[5];

	public HelpScreen (SuperJumper game) {
		this.game = game;
		guiCam = new OrthographicCamera();
		guiCam.setToOrtho(false, 320, 480);
		nextBounds = new Rectangle(320 - 64, 0, 64, 64);
		backBounds = new Rectangle(0, 0, 64, 64);
		touchPoint = new Vector3();
		fileCounter = 0;
		helpFiles = new String[]{"data/help1.png","data/help2.png","data/help3.png","data/help4.png","data/help5.png"};
		helpImage = new Texture[5];
		helpRegion = new TextureRegion[5];
		for(int i=0;i<5;i++){
			helpImage[i] = Assets.loadTexture(helpFiles[i]);
			helpRegion[i] = new TextureRegion(helpImage[i], 0, 0, 320, 480);
		}
	}

	public void update () {
		if (Gdx.input.justTouched()) {
			guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

			if (nextBounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				fileCounter++;
				if(fileCounter == 4){
					fileCounter = 0;
					game.setScreen(new MainMenuScreen(game));
				}
			}

			if(backBounds.contains(touchPoint.x,touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				if(fileCounter<1){
					game.setScreen(new MainMenuScreen(game));
				}
				else{
					fileCounter--;
				}
			}
		}
	}

	public void draw () {
		GL20 gl = Gdx.gl;
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		guiCam.update();
		game.batcher.setProjectionMatrix(guiCam.combined);
		game.batcher.disableBlending();
		game.batcher.begin();
		game.batcher.draw(helpRegion[fileCounter], 0, 0);
		game.batcher.end();

		game.batcher.enableBlending();
		game.batcher.begin();
		game.batcher.draw(Assets.arrow, 320, 0, -64, 64);
		game.batcher.draw(Assets.arrow, 0, 0, 64, 64);
		game.batcher.end();
	}

	@Override
	public void render (float delta) {
		draw();
		update();
	}

	@Override
	public void hide () {
		for(int i=0;i<5;i++){
			helpImage[i].dispose();
		}
	}
}
