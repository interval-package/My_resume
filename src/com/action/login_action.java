package com.action;
import com.action.DataBaseWrapper.DataBase_connect;

import java.sql.ResultSet;
import java.sql.SQLException;

public class login_action {

    public String account, password;
    public login_action(String account, String password){
        this.account = account;
        this.password = password;
    }

    public boolean login_check() {
        DataBase_connect db = new DataBase_connect();
        ResultSet res;
        String buffer;
        db.open();
        try {
            res = db.query(String.format("select * from user_info where account = \"%s\"", account));
            if (res.next()){
                buffer = res.getString("password");
                db.close();
                res.close();
                return buffer.equals(password);
            }
        } catch (SQLException e){
            return false;
        }finally {
            db.close();
        }

        db.close();
        return false;
    }

    public int get_max_idx(String db_name){
        DataBase_connect db = new DataBase_connect();
        ResultSet res;
        String buffer;
        db.open();
        try {
            res = db.query(String.format("select max(idx) as idx from %s", db_name));
            if (res.next()){
                buffer = res.getString("idx");
                db.close();
                res.close();
                return Integer.parseInt(buffer);
            }
        } catch (SQLException e){
            return -1;
        }finally {
            db.close();
        }

        db.close();
        return -1;
    }

}
