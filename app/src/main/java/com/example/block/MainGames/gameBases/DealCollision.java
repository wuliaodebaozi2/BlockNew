package com.example.block.MainGames.gameBases;

public class DealCollision {


    public DealCollision(boolean gone, int type, int getScore, boolean marioDead,
                         SpeedChange marioSpeedChange, SpeedChange selfSpeedChange) {
        Gone = gone;
        Type = type;
        GetScore = getScore;
        MarioDead = marioDead;
        MarioSpeedChange = marioSpeedChange;
        SelfSpeedChange = selfSpeedChange;
    }

    public boolean Gone;     //元素是否消失

    public int Type;               //需改变成的状态码

    public int GetScore;           //是否加分

    public boolean MarioDead;      //主角是否死亡

    public SpeedChange MarioSpeedChange;  //对主角速度的改变

    public SpeedChange SelfSpeedChange;   //对自身速度的改变


}
