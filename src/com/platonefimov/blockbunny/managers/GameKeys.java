package com.platonefimov.blockbunny.managers;



public class GameKeys {

    private static boolean[] keys;
    private static boolean[] p_keys;

    public static final int NUM_KEYS = 2;
    public static final int BUTTON_1 = 0;
    public static final int BUTTON_2 = 1;

    static {
        keys = new boolean[NUM_KEYS];
        p_keys = new boolean[NUM_KEYS];
    }


    public static void update() {
        System.arraycopy(keys, 0, p_keys, 0, NUM_KEYS);
    }


    public static boolean isDown(int key) {
        return keys[key];
    }

    public static boolean isPressed(int key) {
        return keys[key] && !p_keys[key];
    }


    public static void setKey(int key, boolean bool) {
        keys[key] = bool;
    }

}
