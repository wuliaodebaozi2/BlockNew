package com.example.block.MainGames.gameBases;

public class StateType {

    public int Type;        //状态码

    public int width;       //宽、高

    public int [] Relevant;  //相关状态码

    public int backImage[];  //背景图片集

    public DealCollision dealCollisions[];   //碰撞结果

    public float xSpeed,ySpeed,xA,yA;  //初始速度


    public StateType(int type, int width, int[] relevant, int[] backImage, DealCollision[] dealCollisions, float xSpeed, float ySpeed, float xA, float yA) {
        Type = type;
        this.width = width;
        Relevant = relevant;
        this.backImage = backImage;
        this.dealCollisions = dealCollisions;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.xA = xA;
        this.yA = yA;
    }
}
