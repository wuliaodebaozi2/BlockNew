package com.example.block.tools.dataBaseTools;

import java.io.Serializable;

public class ViewposInfos  implements Serializable{
    private int length;
    private int [][] viewPos;

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int[][] getViewPos() {
        return viewPos;
    }

    public void setViewPos(int[][] viewPos) {
        this.viewPos = viewPos;
    }
}
