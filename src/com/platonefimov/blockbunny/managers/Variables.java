package com.platonefimov.blockbunny.managers;



public class Variables {

    // Game block
    public static final String TITLE = "Block Bunny";
    public static final int V_WIDTH = 320;
    public static final int V_HEIGHT = 240;
    public static final int SCALE = 2;
    public static final float DIV_SCALE = 1f / SCALE;
    public static final int WIDTH = V_WIDTH * SCALE;
    public static final int HEIGHT = V_HEIGHT * SCALE;

    public static final float SPF = 1 / 60f;


    // PlayState block
    public static final float PPM = 100;

    public static final short BIT_PLAYER = 1;
    public static final short BIT_RED = 2;
    public static final short BIT_GREEN = 4;
    public static final short BIT_BLUE = 8;

    public static final float JUMP_FORCE = 200f;

}
