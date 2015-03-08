package com.platonefimov.blockbunny.managers;


import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;



public class ContactListener implements com.badlogic.gdx.physics.box2d.ContactListener {

    private boolean playerOnGround;

    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if (fixtureA.getUserData() != null && fixtureA.getUserData().equals("foot"))
            playerOnGround = true;
        if (fixtureB.getUserData() != null && fixtureB.getUserData().equals("foot"))
            playerOnGround = true;
    }


    public void endContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if (fixtureA.getUserData() != null && fixtureA.getUserData().equals("foot"))
            playerOnGround = false;
        if (fixtureB.getUserData() != null && fixtureB.getUserData().equals("foot"))
            playerOnGround = false;
    }


    public boolean isPlayerOnGround() {
        return playerOnGround;
    }


    public void preSolve(Contact contact, Manifold manifold) {
    }
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {
    }

}
