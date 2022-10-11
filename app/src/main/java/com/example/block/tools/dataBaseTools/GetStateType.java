package com.example.block.tools.dataBaseTools;

import com.example.block.MainGames.gameBases.StateType;

public class GetStateType extends Thread {
    private StateType stateType;

    private int Type;

    public GetStateType(StateType stateType,int Type)
    {
        this.stateType = stateType;
        this.Type = Type;
    }
    @Override
    public void run() {
        super.run();
        stateType = LoadUnits.GetStateTypefromDB(Type);
    }
}
