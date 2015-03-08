package com.platonefimov.blockbunny.states;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import static com.platonefimov.blockbunny.managers.Variables.*;

import com.platonefimov.blockbunny.Game;
import com.platonefimov.blockbunny.managers.GameKeys;
import com.platonefimov.blockbunny.managers.StateManager;
import com.platonefimov.blockbunny.managers.ContactListener;


public class PlayState extends GameState {

    private World world;

    private Box2DDebugRenderer renderer;

    private OrthographicCamera box2DCamera;

    private ContactListener contactListener;

    private Body playerBody;


    public PlayState(StateManager stateManager) {
        super(stateManager);

        world = new World(new Vector2(0, -9.81f), true);
        contactListener = new ContactListener();
        world.setContactListener(contactListener);

        renderer = new Box2DDebugRenderer();

        box2DCamera = new OrthographicCamera();
        box2DCamera.setToOrtho(false, Game.V_WIDTH / PPM, Game.V_HEIGHT / PPM);

        BodyDef bodyDef = new BodyDef();
        Body body;
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape polygonShape = new PolygonShape();

        // Ground creating
        bodyDef.position.set(160 / PPM, 120 / PPM);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(bodyDef);
        polygonShape.setAsBox(50 / PPM, 5 / PPM);
        fixtureDef.shape = polygonShape;
        fixtureDef.filter.categoryBits = BIT_GROUND;
        fixtureDef.filter.maskBits = BIT_PLAYER;
        body.createFixture(fixtureDef).setUserData("ground");

        // Player creating
        bodyDef.position.set(160 / PPM, 200 / PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        playerBody = world.createBody(bodyDef);
        polygonShape.setAsBox(5 / PPM, 5 / PPM);
        fixtureDef.shape = polygonShape;
        fixtureDef.filter.categoryBits = BIT_PLAYER;
        fixtureDef.filter.maskBits = BIT_GROUND;
        playerBody.createFixture(fixtureDef).setUserData("Player");
        // Player's foot creating
        polygonShape.setAsBox(2 / PPM, 2 / PPM, new Vector2(0, -5 / PPM), 0);
        fixtureDef.shape = polygonShape;
        fixtureDef.filter.categoryBits = BIT_PLAYER;
        fixtureDef.filter.maskBits = BIT_GROUND;
        fixtureDef.isSensor = true;
        playerBody.createFixture(fixtureDef).setUserData("foot");
    }


    public void update(float deltaTime) {
        handleInput();

        world.step(deltaTime, 6, 2);
    }


    public void handleInput() {
        if (GameKeys.isPressed(GameKeys.BUTTON_1))
            if (contactListener.isPlayerOnGround())
                playerBody.applyForceToCenter(0, 200, true);
    }


    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render(world, box2DCamera.combined);
    }


    public void dispose() {
    }

}
