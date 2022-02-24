package demolition;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PFont;
import processing.data.JSONObject;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class AppTest {
	private App app;
	private Gamemap map;
	private Player player;
	
	@BeforeEach
	public void setup(){
        app = new App();
        app.noLoop();
        PApplet.runSketch(new String[] {"App"}, app);
        app.setup();
        app.delay(1500);

		map = app.getMap();
		player = map.getPlayer();
	}

	@Test
	public void testNotNull(){
		assertNotNull(app);
		assertNotNull(map);
	} 

    @Test 
    public void basicTest() {
        // Create an instance of your application
        App app = new App();

        // Set the program to not loop automatically
		// doesnt go automatically
        app.noLoop();

        // Set the path of the config file to use
        // app.setConfig("src/test/resources/config.json");

        // Tell PApplet to create the worker threads for the program
        PApplet.runSketch(new String[] {"App"}, app);

        // Call App.setup() to load in sprites
        app.setup();

        // Set a 1 second delay to ensure all resources are loaded
        app.delay(500);

        // Call draw to update the program.
        app.draw();

        // Call keyPressed() or similar

        // Call draw again to move onto the next frame
		// EVERYTIME YOU CALL DRAW IT GOES TO THE NEXT FRAME
        app.draw();

        // Verify the new state of the program with assertions
    }
    // test setup is correct, how many sprites and so on

	// then call the draw method if the things are at the right
	@Test
	public void creatingExplosion(){
		int[] coords = new int[]{32, 96};
		Explosion.createExplosion(app.getMap(), coords);
		app.draw();
		app.draw();
	}

	@Test
	public void playerPlaceBomb(){
		app.getMap().placeBomb();
		app.getMap().getPlayer().movement(' ', 0);

		assertTrue(app.getMap().findCollisionObject(app.getMap().getPlayer().getCoords()) instanceof Bomb);
	}

	@Test
	public void loadNextLevel(){
		int previousLevel = map.getCurrentLevelIndex();
		map.getPlayer().movement('?', PApplet.RIGHT);
		map.getPlayer().movement('?', PApplet.RIGHT);

		int nextLevelIndex = map.getCurrentLevelIndex();

		assertTrue(previousLevel == nextLevelIndex - 1);
	}

	@Test
	public void appEnemygrMovement(){
		Enemy lem = new YellowEnemy(map.getAllSprites().get("yellow"), 0,0, map.getAllAnimations().get("yellow"));

		int[] initialCoords = lem.getCoords();

		//simulates 5 seconds in the game, the enemy shoud have moved 5 times
		for (int i = 0; i < App.FPS * 5; i++){
			app.draw();
		}

		int[] movedCoords = lem.getCoords();

		assertFalse(Arrays.equals(initialCoords, movedCoords));
	}	

	@Test
	public void timeOutGameOverTest(){
		// simulates 10 seconds in game to time it out
		for (int i = 0; i < App.FPS * 10; i++){
			app.draw();
		}

		assertTrue(app.getGameOver());
	}
	
	@Test
	public void explosionHitPlayer(){
		// makes an explosion where the player is
		HashMap directionAndCoordinates = new HashMap<>();
		directionAndCoordinates.put("centre", map.getPlayer().getCoords());
		Explosion newExplosion = new Explosion(map.getAllSprites().get("explosion"), map.getPlayer().getCoords()[0], map.getPlayer().getCoords()[1], map.getAllExplosionSprites(), directionAndCoordinates, map);
		
		int playerLives = map.getPlayer().getLives();
		// calls the explosion destroy things method
		newExplosion.tick();
		
		int newPlayerLives = map.getPlayer().getLives();
		assertTrue(playerLives == newPlayerLives + 1);
	}

	@Test
	public void notNull(){
		assertNotNull(player);
		player.getCoords();
	}

	@Test
	public void playerMoveDown(){
		int[] initialCoords = player.getCoords();
		player.movement('?', 40);
		int[] movedCoords = player.getCoords();
		movedCoords[1] -= 32;

		assertTrue(Arrays.equals(initialCoords, movedCoords));
	}	

	@Test
	public void playerMoveUp(){		
		int[] initialCoords = player.getCoords();
		player.movement('?', 38);
		int[] movedCoords = player.getCoords();
		movedCoords[1] += 32;

		assertTrue(Arrays.equals(initialCoords, movedCoords));
	}	

	@Test
	public void playerMoveLeft(){
		int[] initialCoords = player.getCoords();
		player.movement('?', 37);
		int[] movedCoords = player.getCoords();
		movedCoords[0] += 32;

		assertTrue(Arrays.equals(initialCoords, movedCoords));
	}	

	@Test
	public void playerMoveRight(){		
		int[] initialCoords = player.getCoords();
		player.movement('?', 39);
		int[] movedCoords = player.getCoords();
		movedCoords[0] -= 32;

		assertTrue(Arrays.equals(initialCoords, movedCoords));
	}	

	@Test
	public void walkIntoWall(){		
		player.movement('?', 40);
		int[] initialCoords = player.getCoords();
		player.movement('?', 40);
		int[] movedCoords = player.getCoords();

		assertTrue(Arrays.equals(initialCoords, movedCoords));
	}

	@Test
	public void walkIntoEnemy(){		
		player.movement('?', 38);
		int initial = player.getLives();
		player.movement('?', 38);
		int hit = player.getLives();

		assertTrue(initial - 1 == hit);
	}
}