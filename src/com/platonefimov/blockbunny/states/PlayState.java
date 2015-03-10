package com.platonefimov.blockbunny.states;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;

import static com.platonefimov.blockbunny.managers.Variables.*;

import com.platonefimov.blockbunny.Game;
import com.platonefimov.blockbunny.entities.Crystal;
import com.platonefimov.blockbunny.entities.HUD;
import com.platonefimov.blockbunny.entities.Player;
import com.platonefimov.blockbunny.managers.GameKeys;
import com.platonefimov.blockbunny.managers.StateManager;
import com.platonefimov.blockbunny.managers.ContactListener;



public class PlayState extends GameState {

    private World world;

    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera box2DCamera;

    private ContactListener contactListener;

    private OrthogonalTiledMapRenderer mapRenderer;

    private TiledMap tiledMap;
    private float tileSize;

    private Player player;

    private Array<Crystal> crystals;

    private HUD hud;


    public PlayState(StateManager stateManager) {
        super(stateManager);

        world = new World(new Vector2(0, -9.81f), true);
        contactListener = new ContactListener();
        world.setContactListener(contactListener);
        debugRenderer = new Box2DDebugRenderer();
        box2DCamera = new OrthographicCamera();
        box2DCamera.setToOrtho(false, V_WIDTH / PPM, V_HEIGHT / PPM);

        loadResources();
        createPlayer();
        createTiles();
        createCrystals();

        hud = new HUD(player);
    }


    private void loadResources() {
        Game.resources.loadTexture("images/bunny.png", "bunny");
        Game.resources.loadTexture("images/crystal.png", "crystal");
        Game.resources.loadTexture("images/hud.png", "hud");
    }


    private void createPlayer() {
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape polygonShape = new PolygonShape();
        Body playerBody;

        bodyDef.position.set(50 / PPM, 200 / PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.linearVelocity.set(0.5f, 0);
        playerBody = world.createBody(bodyDef);
        polygonShape.setAsBox(13 / PPM, 13 / PPM);
        fixtureDef.shape = polygonShape;
        fixtureDef.filter.categoryBits = BIT_PLAYER;
        fixtureDef.filter.maskBits = BIT_CRYSTAL | BIT_RED;
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
        tiledMap = new TmxMapLoader().load("maps/level_1.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        tileSize = Integer.parseInt(tiledMap.getProperties().get("tilewidth").toString());

        TiledMapTileLayer layer;
        layer = (TiledMapTileLayer) tiledMap.getLayers().get("Red blocks");
        createLayer(layer, BIT_RED);
        layer = (TiledMapTileLayer) tiledMap.getLayers().get("Green blocks");
        createLayer(layer, BIT_GREEN);
        layer = (TiledMapTileLayer) tiledMap.getLayers().get("Blue blocks");
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


    private void createCrystals() {
        crystals = new Array<Crystal>();

        MapLayer layer = tiledMap.getLayers().get("Crystals");
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        CircleShape circleShape = new CircleShape();
        Body body;
        Crystal crystal;

        for (MapObject mapObject : layer.getObjects()) {
            bodyDef.type = BodyDef.BodyType.StaticBody;
            float x = Float.parseFloat(mapObject.getProperties().get("x").toString()) / PPM;
            float y = Float.parseFloat(mapObject.getProperties().get("y").toString()) / PPM;
            bodyDef.position.set(x, y);

            circleShape.setRadius(8 / PPM);
            fixtureDef.shape = circleShape;
            fixtureDef.isSensor = true;
            fixtureDef.filter.categoryBits = BIT_CRYSTAL;
            fixtureDef.filter.maskBits = BIT_PLAYER;

            body = world.createBody(bodyDef);
            body.createFixture(fixtureDef).setUserData("crystal");
            crystal = new Crystal(body);
            crystals.add(crystal);
            body.setUserData(crystal);
        }
    }


    public void update(float deltaTime) {
        handleInput();

        world.step(deltaTime, 6, 2);

        Array<Body> bodiesToRemove = contactListener.getBodiesToRemove();
        for (int i = 0; i < bodiesToRemove.size; i++) {
            Body body = bodiesToRemove.get(i);
            crystals.removeValue((Crystal) body.getUserData(), true);
            world.destroyBody(body);
            player.collectCrystal();
        }
        bodiesToRemove.clear();

        player.update(deltaTime);

        for (int i = 0; i < crystals.size; i++)
            crystals.get(i).update(deltaTime);
    }


    public void handleInput() {
        if (GameKeys.isPressed(GameKeys.BUTTON_1))
            if (contactListener.isPlayerOnGround())
                player.body.applyForceToCenter(0, JUMP_FORCE, true);

        if (GameKeys.isPressed(GameKeys.BUTTON_2))
            switchBlocks();
    }


    private void switchBlocks() {
        Filter filter = player.body.getFixtureList().first().getFilterData();
        short bits = filter.maskBits;

        if ((bits & BIT_RED) != 0) {
            bits &= ~BIT_RED;
            bits |= BIT_GREEN;
        }
        else if ((bits & BIT_GREEN) != 0) {
            bits &= ~BIT_GREEN;
            bits |= BIT_BLUE;
        }
        else if ((bits & BIT_BLUE) != 0) {
            bits &= ~BIT_BLUE;
            bits |= BIT_RED;
        }

        filter.maskBits = bits;
        player.body.getFixtureList().first().setFilterData(filter);

        filter = player.body.getFixtureList().get(1).getFilterData();
        bits &= BIT_CRYSTAL;
        filter.maskBits = bits;
        player.body.getFixtureList().get(1).setFilterData(filter);
    }


    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.position.set(player.getPosition().x * PPM + V_WIDTH / 4, V_HEIGHT / 2, 0);
        camera.update();

        mapRenderer.setView(camera);
        mapRenderer.render();

        spriteBatch.setProjectionMatrix(camera.combined);

        player.render(spriteBatch);

        for (int i = 0; i < crystals.size; i++)
            crystals.get(i).render(spriteBatch);

        spriteBatch.setProjectionMatrix(hudCamera.combined);
        hud.render(spriteBatch);

        if (debug)
            debugRenderer.render(world, box2DCamera.combined);
    }


    public void dispose() {
    }

}
