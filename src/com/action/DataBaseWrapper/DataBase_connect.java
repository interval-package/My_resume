package com.action.DataBaseWrapper;

import java.sql.*;
public class DataBase_connect {
    public static void main( String args[] ) throws SQLException {
        DataBase_connect db = new DataBase_connect();
        db.open();

        ResultSet res = db.query("select * from contest_info");
        while (res.next()){
            System.out.println(String.format("%s\n", res.getString("contest_name")));
        }
        db.close();
    }
    static final public String driver_name = "org.sqlite.JDBC";
//    static final public String source_db = "jdbc:sqlite:Database/test.db";
    static final public String source_db = "jdbc:sqlite://D:\\coding\\web_projects\\My_resume\\Database\\test.db";
//    Require absolute path.
    private Connection conn = null;
    public Statement stat = null;
    public void open(){
        if (conn != null) close();
        try {
            Class.forName(driver_name);
            conn = DriverManager.getConnection(source_db);
//            System.out.println("Opened database successfully");
            stat = conn.createStatement();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    public void close(){
        try {
            if (stat != null) {
                stat.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

    }

    public ResultSet query(String sql) throws SQLException {
        if (conn == null) open();
        return stat.executeQuery(sql);
    }
}
