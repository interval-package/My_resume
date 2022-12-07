package com.action.info_shadow;

import java.sql.ResultSet;
import java.sql.SQLException;

public class projects_info{
    private String project_name, project_info, project_pic_path, project_time, project_web;

    public projects_info(ResultSet tar) throws SQLException {
        project_name        = tar.getString("project_name");
        project_info        = tar.getString("project_info");
        project_pic_path    = tar.getString("project_pic_path");
        project_time        = tar.getString("project_time");
        project_web         = tar.getString("project_web");
    }

    @Override
    public String toString(){
        return String.format("{" +
                    "project_name:%s,"      +
                    "project_info:%s,"      +
                    "project_pic_path:%s,"  +
                    "project_time:%s,"      +
                    "project_web:%s }",
                project_name, project_info, project_pic_path, project_time, project_web);
    }
}