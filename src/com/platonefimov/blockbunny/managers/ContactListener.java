package com.platonefimov.blockbunny.managers;


import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;



public class ContactListener implements com.badlogic.gdx.physics.box2d.ContactListener {

    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        System.out.println(fixtureA.getUserData() + ", " + fixtureB.getUserData());
    }


    public void endContact(Contact contact) {
    }
    public void preSolve(Contact contact, Manifold manifold) {
    }
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {
    }

}
