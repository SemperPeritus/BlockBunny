package com.platonefimov.blockbunny.managers;


import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;



public class ContactListener implements com.badlogic.gdx.physics.box2d.ContactListener {

    private int numFootContacts;

    private Array<Body> bodiesToRemove;


    public ContactListener() {
        super();

        bodiesToRemove = new Array<Body>();
    }


    public void beginContact(Contact contact) {
        Fixture fixtureB = contact.getFixtureB();

        if (fixtureB.getUserData().equals("foot"))
            numFootContacts++;
        if (fixtureB.getUserData().equals("crystal"))
            bodiesToRemove.add(fixtureB.getBody());
    }


    public void endContact(Contact contact) {
        Fixture fixtureB = contact.getFixtureB();

        if (fixtureB.getUserData().equals("foot"))
            numFootContacts--;
    }


    public Array<Body> getBodiesToRemove() {
        return bodiesToRemove;
    }


    public boolean isPlayerOnGround() {
        return numFootContacts > 0;
    }


    public void preSolve(Contact contact, Manifold manifold) {
    }
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {
    }

}
