package com.platonefimov.blockbunny.states;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import static com.platonefimov.blockbunny.managers.Variables.*;

import com.platonefimov.blockbunny.entities.Player;
import com.platonefimov.blockbunny.managers.GameKeys;
import com.platonefimov.blockbunny.managers.StateManager;
import com.platonefimov.blockbunny.managers.ContactListener;



public class PlayState extends GameState {

    private World world;

    private Box2DDebugRenderer renderer;
    private OrthographicCamera box2DCamera;

    private ContactListener contactListener;

    private OrthogonalTiledMapRenderer mapRenderer;
    private float tileSize;

    private Player player;


    public PlayState(StateManager stateManager) {
        super(stateManager);

        world = new World(new Vector2(0, -9.81f), true);
        contactListener = new ContactListener();
        world.setContactListener(contactListener);
        renderer = new Box2DDebugRenderer();
        box2DCamera = new OrthographicCamera();
        box2DCamera.setToOrtho(false, V_WIDTH / PPM, V_HEIGHT / PPM);

        createPlayer();
        createTiles();
    }


    private void createPlayer() {
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape polygonShape = new PolygonShape();
        Body playerBody;

        bodyDef.position.set(50 / PPM, 200 / PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.linearVelocity.set(0, 0);
        playerBody = world.createBody(bodyDef);
        polygonShape.setAsBox(13 / PPM, 13 / PPM);
        fixtureDef.shape = polygonShape;
        fixtureDef.filter.categoryBits = BIT_PLAYER;
        fixtureDef.filter.maskBits = BIT_RED;
        playerBody.createFixture(fixtureDef).setUserData("Player");

        polygonShape.setAsBox(13 / PPM, 2 / PPM, new Vector2(0, -13 / PPM), 0);
        fixtureDef.shape = polygonShape;
        fixtureDef.filter.categoryBits = BIT_PLAYER;
        fixtureDef.filter.maskBits = BIT_RED;
        fixtureDef.isSensor = true;
        playerBody.createFixture(fixtureDef).setUserData("foot");

        player = new Player(playerBody);

        playerBody.setUserData(player);
    }


    private void createTiles() {
        TiledMap tiledMap = new TmxMapLoader().load("maps/level_1.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        tileSize = Integer.parseInt(tiledMap.getProperties().get("tilewidth").toString());

        TiledMapTileLayer layer;
        layer = (TiledMapTileLayer) tiledMap.getLayers().get("Red layer");
        createLayer(layer, BIT_RED);
        layer = (TiledMapTileLayer) tiledMap.getLayers().get("Green layer");
        createLayer(layer, BIT_GREEN);
        layer = (TiledMapTileLayer) tiledMap.getLayers().get("Blue layer");
        createLayer(layer, BIT_BLUE);
    }


    private void createLayer(TiledMapTileLayer layer, short bits) {
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        Cell cell;
        Vector2[] vector2s = new Vector2[3];
        for (int row = 0; row < layer.getHeight(); row++) {
            for (int col = 0; col < layer.getWidth(); col++) {
                cell = layer.getCell(col, row);

                if (cell == null)
                    continue;
                if (cell.getTile() == null)
                    continue;

                bodyDef.type = BodyDef.BodyType.StaticBody;
                bodyDef.position.set((col + DIV_SCALE) * tileSize / PPM, (row + DIV_SCALE) * tileSize / PPM);

                vector2s[0] = new Vector2(-tileSize * DIV_SCALE / PPM, -tileSize * DIV_SCALE / PPM);
                vector2s[1] = new Vector2(-tileSize * DIV_SCALE / PPM, tileSize * DIV_SCALE / PPM);
                vector2s[2] = new Vector2(tileSize * DIV_SCALE / PPM, tileSize * DIV_SCALE / PPM);
                ChainShape chainShape = new ChainShape();
                chainShape.createChain(vector2s);

                fixtureDef.friction = 0;
                fixtureDef.shape = chainShape;
                fixtureDef.filter.categoryBits = bits;
                fixtureDef.filter.maskBits = BIT_PLAYER;

                world.createBody(bodyDef).createFixture(fixtureDef);
            }
        }
    }


    public void update(float deltaTime) {
        handleInput();

        world.step(deltaTime, 6, 2);

        player.update(deltaTime);
    }


    public void handleInput() {
        if (GameKeys.isPressed(GameKeys.BUTTON_1))
            if (contactListener.isPlayerOnGround())
                player.body.applyForceToCenter(0, JUMP_FORCE, true);
    }


    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mapRenderer.setView(camera);
        mapRenderer.render();

        spriteBatch.setProjectionMatrix(camera.combined);

        player.render(spriteBatch);

        renderer.render(world, box2DCamera.combined);
    }


    public void dispose() {
    }

}
