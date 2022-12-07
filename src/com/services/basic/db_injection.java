package com.services.basic;
import java.io.*;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.servlet.*;
import javax.servlet.http.*;

import com.action.login_action;
import com.google.gson.Gson;

import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.action.DataBaseWrapper.DataBase_connect;

// 扩展 HttpServlet 类
public class db_injection extends HttpServlet {

    public static void main(String[] args) throws SQLException {
        String temp =
                "{" +
                "\"tar\":{" +
                    "\"tar2\":\"obj\"" +
                "}, " +
                "\"db\":1" +
                "}";

        Gson gson = new Gson();
        Map<String, LinkedTreeMap<String, String>> map = new Gson().fromJson(temp, new TypeToken<HashMap<String,Object>>(){}.getType());
        System.out.println(map);
        for (String str: map.keySet()){
            System.out.println(str);
            if(map.get(str) instanceof LinkedTreeMap){
                LinkedTreeMap<String, String> map1 = map.get(str);
                System.out.println(map.get(str).get("tar2"));
            }else {
                System.out.println(map.get(str));
            }

        }
    }

    @Override
    public void init() throws ServletException {
        super.init();
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException
    {
        feedback_null_info(response, "Get invalid.");
    }

    public void feedback_null_info(HttpServletResponse response, String info) throws IOException {
        response.getWriter().println("<h1> invalid method </h1>\n" + info);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response){
        Map<String, String[]> parameterMap = request.getParameterMap();
        try {
            Map<String,LinkedTreeMap<String, String>> details = get_json(request);

            if (parameterMap.containsKey("action")){
                String[] value = parameterMap.get("action");
                for (String action: value){
                    String sql = service_update(details, action);
                    System.out.println(sql);
                    if (sql != null){
                        DataBase_connect conn = new DataBase_connect();
                        conn.open();
                        conn.stat.executeUpdate(sql);
                        conn.close();
                        response.getWriter().write("action success");
                    }else {
                        response.getWriter().write("invalid action");
                    }
                }
            }else {
                feedback_null_info(response, "no such key");
            }
        } catch (IOException e) {
            System.err.println("SQLException happened, response failed.");
        } catch (Exception e){
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
    }

    String fmt_update =
            "UPDATE table_name\n" +
            "SET column1 = value1, column2 = value2...., columnN = valueN\n" +
            "WHERE [condition]";

    public String service_update(Map<String, LinkedTreeMap<String, String>> details, String action){
        LinkedTreeMap<String, String> login_info = details.get("login");
        LinkedTreeMap<String, String> data = details.get("data");
        String db = details.get("db").get("name");
        login_action _login = new login_action(login_info.get("account"), login_info.get("password"));
        String sql = "";
        String keys = "";
        String values = "";
        if (_login.login_check()){
            for (String key: data.keySet()){
                if (!key.equals("idx")){
                    sql += String.format(" \"%s\"=\"%s\",", key, data.get(key));
                    keys += String.format("%s,", key);
                    values += String.format("\"%s\",", data.get(key));
                }
            }
            sql = sql.substring(0, sql.length()-1);
            keys = keys.substring(0, keys.length()-1);
            values = values.substring(0, values.length()-1);

            sql = switch (action) {
                case "update" -> String.format("UPDATE %s \n SET %s \n WHERE idx=%s", db, sql, data.get("idx"));
                case "remove" -> String.format("DELETE FROM %s WHERE idx=%s", db, data.get("idx"));
                case "add" -> String.format("INSERT INTO %s(idx,%s) VALUES (%d, %s)", db, keys, _login.get_max_idx(db)+1, values);
                default -> null;
            };
        } else {
            sql = null;
        }
        return sql;
    }


    public Map<String,LinkedTreeMap<String, String>> get_json(HttpServletRequest request){
        String result = "";
        BufferedReader in = null;
        try {
            in= new BufferedReader(new InputStreamReader(
                    request.getInputStream(), StandardCharsets.UTF_8));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                assert in != null;
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new Gson().fromJson(result, new TypeToken<HashMap<String,Object>>(){}.getType());
    }
}
