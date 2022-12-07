package com.services.admin;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.http.Cookie;

import com.action.login_action;
import com.google.gson.Gson;


public class login extends HttpServlet {

    public void init() throws ServletException
    {}

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException
    {
        Map<String, String[]> params = request.getParameterMap();
        if (params.containsKey("account") && params.containsKey("password")){
            login_action _login = new login_action(params.get("account")[0], params.get("password")[0]);
            if (params.containsKey("init") && params.get("init")[0].equals("true")){
                first_login_check(_login, response);
            }else {
                action_login_check(_login, response);
            }
        }else {
            response.sendRedirect("/My_resume/manage/login?status=no_input");
        }
    }


    public void first_login_check(login_action _login, HttpServletResponse response) throws IOException {

//        这里挺不安全的
         if (_login.login_check()){
             update_cookie(_login, response);
             response.sendRedirect("/My_resume/manage/admin");
         }else {
             response.sendRedirect("/My_resume/manage/login?status=invalid_login");
         }

    }

    public void action_login_check(login_action _login, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        Map<String, String> res = new HashMap<>();
        if(_login.login_check()){
            res.put("is_login", "true");
            update_cookie(_login, response);
        }else {
            res.put("is_login", "false");
        }
        response.getWriter().println(gson.toJson(res));
    }

    public void update_cookie(login_action _login, HttpServletResponse response) throws IOException {
        Cookie password_cookie = new Cookie("password", _login.password);
        Cookie account_cookie = new Cookie("account", _login.account);
        password_cookie.setMaxAge(60);
        account_cookie.setMaxAge(2*60);
//             password_cookie.setPath("/cookie/resume/login");
//             password_cookie.setPath("/cookie/resume/login");
        response.addCookie(password_cookie);
        response.addCookie(account_cookie);
    }

    public void destroy()
    {}
}
