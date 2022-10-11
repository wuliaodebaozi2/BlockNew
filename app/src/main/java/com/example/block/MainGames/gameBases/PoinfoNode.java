package com.example.block.MainGames.gameBases;

public class PoinfoNode {
    public PoInfo poInfo;
    public PoinfoNode PrePoNo;
    public PoinfoNode NextPoNo;

    public PoinfoNode(PoInfo poInfo)
    {
        this.poInfo = poInfo;
    }
}
