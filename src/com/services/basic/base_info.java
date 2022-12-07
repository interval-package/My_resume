package com.services.basic;
import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.*;
import javax.servlet.http.*;
import com.google.gson.Gson;

import com.action.DataBaseWrapper.DataBase_connect;
import com.action.info_shadow.contest_info;
import com.action.info_shadow.projects_info;

// 扩展 HttpServlet 类
public class base_info extends HttpServlet {

    public static void main(String[] args) throws SQLException {
    }

    public void init() throws ServletException {
//        db_conn = new DataBase_connect();
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException
    {
        Map<String, String[]> parameterMap = request.getParameterMap();
        try {
            if (parameterMap.containsKey("content")){
                String[] value = parameterMap.get("content");
                for (String content_type: value){
                    if (content_type.equals("contest")){
                        feedback_contest_info(response);
                    } else if (content_type.equals("projects")) {
                        feedback_projects_info(response);
                    } else {
                        feedback_null_info(response, "no such param");
                    }
//                    caution, only tackle one param one time.
                    return;
                }
            }else {
                feedback_null_info(response, "no such key");
            }
        } catch (IOException e) {
            System.err.println("SQLException happened, response failed.");
        }
    }

    private void feedback_contest_info(HttpServletResponse response) throws IOException {
        DataBase_connect db_conn = new DataBase_connect();
        Gson gson = new Gson();

        List<contest_info> arr = new ArrayList<contest_info>();
        try{
            db_conn.open();
            ResultSet res = db_conn.query("select * from contest_info");
            while (res.next()){
                arr.add(new contest_info(res.getString("contest_name"), res.getString("rank")));
            }
            db_conn.close();
            res.close();
        } catch (SQLException e){
            System.err.println(e.getMessage());
            feedback_null_info(response, e.getMessage());
            return;
        }
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println(gson.toJson(arr));
    }

    private void feedback_projects_info(HttpServletResponse response) throws IOException {
        DataBase_connect db_conn = new DataBase_connect();
        Gson gson = new Gson();
        List<projects_info> arr = new ArrayList<projects_info>();
        try{
            db_conn.open();
            ResultSet res = db_conn.query("select * from projects");
            while (res.next()){
                arr.add(new projects_info(res));
            }
            db_conn.close();
            res.close();
        } catch (SQLException e){
            System.err.println(e.getMessage());
            feedback_null_info(response, e.getMessage());
            return;
        }
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println(gson.toJson(arr));
    }

    private void feedback_null_info(HttpServletResponse response, String msg) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String err_message = "<h1>Get base info failed, please check your param.</h1>";
        out.println(err_message);
        out.println(String.format("<h2>%s</h2>", msg));
    }

    public void destroy()
    {
        System.out.println("ServeLet end life.");
    }
}
