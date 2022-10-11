package com.example.block.MainGames.gameBases;

public class SpeedChange {
    public boolean xSpeedG0;  //水平速度一定大于0
    public boolean xSpeedL0; //水平速度一定小于0
    public boolean xSpeedE0; //水平速度等于0
    public float xSpeedAdd; //水平速度增量

    public boolean ySpeedG0;  //竖直速度一定大于0
    public boolean ySpeedL0;  //竖直速度一定小于0
    public boolean ySpeedE0; //竖直速度等于0
    public float ySpeedAdd; //竖直速度增量

    public boolean xAG0; //水平加速度一定大于0
    public boolean xAL0; //水平加速度一定小于0
    public boolean xAE0; //水平加速度等于0
    public float xAAdd;  //水平加速度增量

    public boolean yAG0; //竖直加速度一定大于0
    public boolean yAL0; //竖直加速度一定小于0
    public boolean yAE0; //竖直加速度等于0
    public float yAAdd;  //竖直加速度增量


    public SpeedChange(boolean xSpeedG0, boolean xSpeedL0, boolean xSpeedE0, float xSpeedAdd, boolean ySpeedG0, boolean ySpeedL0, boolean ySpeedE0, float ySpeedAdd, boolean xAG0, boolean xAL0, boolean xAE0, float xAAdd, boolean yAG0, boolean yAL0, boolean yAE0, float yAAdd) {
        this.xSpeedG0 = xSpeedG0;
        this.xSpeedL0 = xSpeedL0;
        this.xSpeedE0 = xSpeedE0;
        this.xSpeedAdd = xSpeedAdd;
        this.ySpeedG0 = ySpeedG0;
        this.ySpeedL0 = ySpeedL0;
        this.ySpeedE0 = ySpeedE0;
        this.ySpeedAdd = ySpeedAdd;
        this.xAG0 = xAG0;
        this.xAL0 = xAL0;
        this.xAE0 = xAE0;
        this.xAAdd = xAAdd;
        this.yAG0 = yAG0;
        this.yAL0 = yAL0;
        this.yAE0 = yAE0;
        this.yAAdd = yAAdd;
    }
}
