package com.services.admin;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.http.Cookie;

import com.action.login_action;


public class validation extends HttpServlet {

    public void init() throws ServletException
    {}

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException
    {

        feedback_login_validation(response);
    }


    public void feedback_login_validation(HttpServletResponse response) throws IOException {
        response.sendRedirect("/manage/admin");
    }


    public void destroy()
    {}
}
