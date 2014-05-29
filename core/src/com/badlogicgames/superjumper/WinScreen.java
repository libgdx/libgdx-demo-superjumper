package com.badlogicgames.superjumper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;

public class WinScreen extends ScreenAdapter {
	SuperJumper game;
	OrthographicCamera cam;
	TextureRegion princess;
	String[] messages = { "Princess: Oh dear!\n What have you done?",
						  "Bob: I came to \nrescue you!",
						  "Princess: you are\n mistaken\nI need no rescueing",
						  "Bob: So all this \nwork for nothing?",
						  "Princess: I have \ncake and tea!\nWould you like some?",
						  "Bob: I'd be my \npleasure!",
						  "And they ate cake\nand drank tea\nhappily ever \nafter\n\n\n\n\n\n\nKära Emma!\nDu är fantastisk!\nDu blev ferdig\n med spelet!"
			};
	int currentMessage = 0;
	
	public WinScreen(SuperJumper game) {
		this.game = game;
		cam = new OrthographicCamera();
		cam.setToOrtho(false, 320, 480);
		princess = new TextureRegion(Assets.arrow.getTexture(), 210, 122, -40, 38);
	}
	
	@Override
	public void render(float delta) {
		if(Gdx.input.justTouched()) {
			currentMessage++;
			if(currentMessage == messages.length) {
				currentMessage--;
				game.setScreen(new MainMenuScreen(game));
			}
		}
		
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		cam.update();
		game.batcher.setProjectionMatrix(cam.combined);
		game.batcher.begin();
		game.batcher.draw(Assets.backgroundRegion, 0, 0);
		game.batcher.draw(Assets.castle, 60, 120, 200, 200);
		game.batcher.draw(Assets.bobFall.getKeyFrame(0, Animation.ANIMATION_LOOPING), 120, 200);
		Assets.font.drawMultiLine(game.batcher, messages[currentMessage], 0, 400, 320, HAlignment.CENTER);
		game.batcher.draw(princess,150, 200);
		game.batcher.end();
	}
}
