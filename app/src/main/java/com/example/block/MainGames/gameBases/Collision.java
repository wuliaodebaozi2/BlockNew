package com.example.block.MainGames.gameBases;

/*
碰撞信息类
 */
public class Collision {

    public enum CollisionDirection{                    //碰撞方向
        Left,right,top,bottom
    }

    public CollisionDirection collisionDirection;

    public PoinfoNode collisionObjectNode;              //碰撞物节点

    public PoinfoNode collisedObjectNode;                //被碰撞物节点

    public Collision(CollisionDirection collisionDirection , PoinfoNode collisionObjectNode
    , PoinfoNode collisedObjectNode)
    {
        this.collisionDirection = collisionDirection;
        this.collisionObjectNode = collisionObjectNode;
        this.collisedObjectNode = collisedObjectNode;
    }
}
