package com.example.block.tools;

import com.example.block.MainGames.gameBases.Collision;
import com.example.block.MainGames.gameBases.DealCollision;
import com.example.block.MainGames.gameBases.SpeedChange;

public class ArrayAndString {
    public static String ArraytoString(int [] array)
    {
        if (array != null && array.length > 0)
        {
            String str = "";
            for (int i=0;i<array.length;i++)
            {
                str += array[i] + ",";

            }
            return str;
        }
        else
        {
            return "";
        }
    }

    public static int[] StringtoArray(String str)
    {
        String[] arr = str.split(","); // 用,分割
        int [] ints = new int[arr.length];
        for (int i = 0;i < arr.length ;i++)
        {
            ints[i] = Integer.parseInt(arr[i]);
        }
        return ints;
    }

    public static DealCollision StringtoDealCollision(String string)
    {
        float [] floats = StringtoFloat(string);
        int i = 0;

        boolean Gone;     //元素是否消失

        int Type;               //需改变成的状态码

        int GetScore;           //是否加分

        boolean MarioDead;      //主角是否死亡

        SpeedChange MarioSpeedChange;  //对主角速度的改变

        SpeedChange SelfSpeedChange;   //对自身速度的改变


        Gone = floattoBoolean(floats[0]);

        Type = (int)floats[1];

        GetScore = (int)floats[2];

        MarioDead = floattoBoolean(floats[3]);

        MarioSpeedChange = new SpeedChange(floattoBoolean(floats[4]),floattoBoolean(floats[5]),floattoBoolean(floats[6]),floats[7],
                floattoBoolean(floats[8]),floattoBoolean(floats[9]),floattoBoolean(floats[10]),floats[11],
                floattoBoolean(floats[12]),floattoBoolean(floats[13]),floattoBoolean(floats[14]),floats[15],
                floattoBoolean(floats[16]),floattoBoolean(floats[17]),floattoBoolean(floats[18]),floats[19]
        );

        SelfSpeedChange = new SpeedChange(floattoBoolean(floats[20]),floattoBoolean(floats[21]),floattoBoolean(floats[22]),floats[23],
                floattoBoolean(floats[24]),floattoBoolean(floats[25]),floattoBoolean(floats[26]),floats[27],
                floattoBoolean(floats[28]),floattoBoolean(floats[29]),floattoBoolean(floats[30]),floats[31],
                floattoBoolean(floats[32]),floattoBoolean(floats[33]),floattoBoolean(floats[34]),floats[35]
        );

        return new DealCollision(Gone,Type,GetScore,MarioDead,MarioSpeedChange,SelfSpeedChange);

    }

    public static String DealCollisiontoString(DealCollision dealCollision)
    {
        String string = "";

        if (dealCollision != null)
        {
            string += booleantoInt(dealCollision.Gone) + ",";

            string += dealCollision.Type + ",";

            string += dealCollision.GetScore + ",";

            string += booleantoInt(dealCollision.MarioDead) + ",";

            string += MarioSpeedChangetoString(dealCollision.MarioSpeedChange);

            string += MarioSpeedChangetoString(dealCollision.SelfSpeedChange);
        }
        else
        {
            string = "0,0,0,0,";

            string += MarioSpeedChangetoString(null);

            string += MarioSpeedChangetoString(null);
        }



        return string;

    }

    private static String MarioSpeedChangetoString(SpeedChange speedChange)
    {
        String string = "";

        if (speedChange != null)
        {
            string += booleantoInt(speedChange.xSpeedG0) + ",";
            string += booleantoInt(speedChange.xSpeedL0) + ",";
            string += booleantoInt(speedChange.xSpeedE0) + ",";
            string += speedChange.xSpeedAdd + ",";

            string += booleantoInt(speedChange.ySpeedG0) + ",";
            string += booleantoInt(speedChange.ySpeedL0) + ",";
            string += booleantoInt(speedChange.ySpeedE0) + ",";
            string += speedChange.ySpeedAdd + ",";

            string += booleantoInt(speedChange.xAG0) + ",";
            string += booleantoInt(speedChange.xAL0) + ",";
            string += booleantoInt(speedChange.xAE0) + ",";
            string += speedChange.xAAdd + ",";

            string += booleantoInt(speedChange.yAG0) + ",";
            string += booleantoInt(speedChange.yAL0) + ",";
            string += booleantoInt(speedChange.yAE0) + ",";
            string += speedChange.yAAdd + ",";
        }
        else
        {
            string = "0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,";
        }

        return string;
    }


    private static int booleantoInt(boolean b)
    {
        if (b)
            return 1;
        else
            return 0;
    }
    private static boolean floattoBoolean(float f)
    {
        return (int) f != 0;
    }

    public static float[] StringtoFloat(String string)
    {
        String[] arr = string.split(","); // 用,分割
        float [] floats = new float[arr.length];
        for (int i = 0;i < arr.length ;i++)
        {
            floats[i] = Float.parseFloat(arr[i]);
        }
        return floats;
    }

    public static String checkPointtoString(int [][]checkPoint)
    {
        String string = "";
        for (int i = 0;i < checkPoint.length;i++)
        {
            string += checkPoint[i][0] + ",";
            string += checkPoint[i][1] + ",";
            string += checkPoint[i][2] + ",";
        }
        return string;
    }
    public static int [][] stringtoCheckpoint(String string)
    {
        String[] arr = string.split(","); // 用,分割
        int [][] ints = new int[arr.length / 3][3];
        for (int i = 0;i < ints.length ;i++)
        {
            ints[i][0] = Integer.parseInt(arr[i * 3]);
            ints[i][1] = Integer.parseInt(arr[i * 3 + 1]);
            ints[i][2] = Integer.parseInt(arr[i * 3 + 2]);
        }
        return ints;
    }
}
