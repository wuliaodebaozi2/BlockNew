package com.example.block.MainGames.gameBases;

import java.util.ArrayList;

public class CollisionNode {


    public ArrayList<Collision>  collisionArrayList;

    public CollisionNode PreCoNo;          //上一个节点

    public CollisionNode NextCoNo;         //下一个节点

    public CollisionNode()
    {
        collisionArrayList = new ArrayList<Collision>();
    }

}
