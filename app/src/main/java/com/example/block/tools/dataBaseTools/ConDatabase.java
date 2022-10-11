package com.example.block.tools.dataBaseTools;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConDatabase {
    public static  Connection getConnection() throws Exception{
        try {
            Connection cn = null;
            Class.forName("com.mysql.jdbc.Driver");
            cn = DriverManager.getConnection("jdbc:mysql://114.55.103.50:3306/mario?useUnicode=true&characterEncoding=UTF-8",     // ip + 端口  + 数据库名字 ip 10.0.2.2 端口3306 数据库 run
                    "root", "Admin123!");
            return cn;
        } catch (Exception e) {
            throw (e);
        }
    }
}
