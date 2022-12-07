package com.action.admin_render;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.action.DataBaseWrapper.DataBase_connect;

public class render_box implements admin_render{

    String base_path;

    public render_box(String base_path){
        this.base_path = base_path;
    }


    String example_for_box = """
                  <!-- Default box -->
                  <div class="box">
                    <div class="box-header with-border">
                      <h3 class="box-title">Title</h3>
                        
                      <div class="box-tools pull-right">
                        <button type="button" class="btn btn-box-tool" data-widget="collapse" data-toggle="tooltip" title="Collapse">
                          <i class="fa fa-minus"></i></button>
                        <button type="button" class="btn btn-box-tool" data-widget="remove" data-toggle="tooltip" title="Remove">
                          <i class="fa fa-times"></i></button>
                      </div>
                    </div>
                        
                    <div class="box-body">
                      Start creating your amazing application!
                    </div>
                    <!-- /.box-body -->
                        
                    <div class="box-footer">
                      Footer
                    </div>
                    <!-- /.box-footer-->
                        
                  </div>
                  <!-- /.box -->
            """;

//    2 input
    String fmt_all =
            "<!-- Default box -->\n" +
            "<div class=\"box\">\n" +
            "  <div class=\"box-header with-border\">\n" +
            "    <h3 class=\"box-title\">%s</h3>\n" +
            "    <div class=\"box-tools pull-right\">\n" +
            "      <button type=\"button\" class=\"btn btn-box-tool\" data-widget=\"collapse\" data-toggle=\"tooltip\" title=\"Collapse\">\n" +
            "        <i class=\"fa fa-minus\"></i></button>\n" +
            "      <button type=\"button\" class=\"btn btn-box-tool\" data-widget=\"remove\" data-toggle=\"tooltip\" title=\"Remove\">\n" +
            "        <i class=\"fa fa-times\"></i></button>\n" +
            "    </div>\n" +
            "  </div>\n" +
            "%s\n"+
            "</div>\n" +
            "<!-- /.box -->\n";

    String fmt_button_update =
            "<button type=\"button\" class=\"btn btn-box-tool\" " +
                    "data-toggle=\"tooltip\" title=\"Add\">\n" +
                    "  <i class=\"fa fa-plus\"></i></button>\n";

    String fmt_button_add =
            "<button type=\"button\" class=\"btn btn-box-tool\" data-toggle=\"tooltip\" " +
                    "onclick=\"%s\" title=\"Add\" >\n" +
            "  <i class=\"fa fa-plus\"></i></button>\n";
    String fmt_button_times = 
            "<button type=\"button\" class=\"btn btn-box-tool\" data-toggle=\"tooltip\" title=\"Remove\">\n" +
            "  <i class=\"fa fa-times\"></i></button>\n";

    String fmt_body =
            "<div class=\"box-body\">\n" +
            "  %s\n" +
            "</div>\n";

    String fmt_footer =
            "<div class=\"box-footer\">\n" +
            "  %s\n" +
            "</div>\n" +
            "<!-- /.box-footer-->\n";

    @Override
    public String render(){
        String output = "";

        output += render_contest_info();
        output += render_experience_info();
        output += render_project_info();

        return output;
    }

    private String make_fmt_btn(String type, String func, String icon){
        return "<button type=\"button\" class=\"btn btn-box-tool\" " +
                "value=\"" + type +"\" " +
                "onclick=\"" + func + "(this, %d)\" " +
                "data-toggle=\"tooltip\" title=\"" + type + "\">\n" +
                "  <i class=\"fa fa-" + icon + "\"></i></button>\n";
    }

    public String render_contest_info(){
        String fmt_button_update = make_fmt_btn("update", "contest_update", "plus");
        String fmt_button_remove = make_fmt_btn("remove", "contest_update", "times");

        String fmt_table =
                "<table id = \"contest_table\">\n" +
                "  <tr><td>idx</td><td>contest name:</td ><td>contest_date:</td> <td>rank:</td></tr>\n" +
                "  %s\n" +
                "</table>\n";
        String output = "";
        DataBase_connect db_conn = new DataBase_connect();
        try{
            db_conn.open();
            ResultSet res = db_conn.query("select * from contest_info");
            int iter = 0;
            while (res.next()){
                String temp = "<tr>\n";
                temp += String.format("<td class=\"tr_idx\">%s</td>\n",
                        res.getString("idx"));
                temp += String.format("<td contenteditable=\"true\">%s</td>\n",
                        res.getString("contest_name"));
                temp += String.format("<td contenteditable=\"true\">%s</td>\n",
                        res.getString("contest_date"));
                temp += String.format("<td contenteditable=\"true\">%s</td>\n",
                        res.getString("rank"));
                temp += String.format("<td class=\"tr_sql_btn\">%s</td>\n",
                        String.format(fmt_button_update, iter+1) +
                        String.format(fmt_button_remove, iter+1));
                temp += " </tr>\n";
                output += temp;
                iter ++;
            }

            output = String.format(fmt_table, output);
//            output = String.format(fmt_body, output);
            output += String.format(fmt_footer, "Add new" + String.format(fmt_button_add, "contest_add(this)"));

            db_conn.close();
            res.close();
            return String.format(fmt_all, "contest edit", output);
        } catch (SQLException e){
            System.err.println(e.getMessage());
            return "";
        }
    }

    public String render_experience_info(){
        String fmt_button_update = make_fmt_btn("update", "experience_update", "plus");
        String fmt_button_remove = make_fmt_btn("remove", "experience_update", "times");

        String fmt_table =
                "<table id=\"experience_table\">\n" +
                "  <tr><td>idx</td><td>experience time:</td ><td>experience detail:</td></tr>\n" +
                "  %s\n" +
                "</table>\n";
        String output = "";
        DataBase_connect db_conn = new DataBase_connect();
        try{
            db_conn.open();
            ResultSet res = db_conn.query("select * from experience");
            int iter = 0;
            while (res.next()){
                String temp = "<tr>\n";
                temp += String.format("<td>%s</td>\n",
                        res.getString("idx"));
                temp += String.format("<td contenteditable=\"true\">%s</td>\n",
                        res.getString("exp_time"));
                temp += String.format("<td contenteditable=\"true\">%s</td>\n",
                        res.getString("details"));
                temp += String.format("<td class=\"tr_sql_btn\">%s</td>\n",
                        String.format(fmt_button_update, iter+1) +
                        String.format(fmt_button_remove, iter+1));
                temp += " </tr>\n";
                output += temp;
                iter ++;
            }

            output = String.format(fmt_table, output);
//            output = String.format(fmt_body, output);
            output += String.format(fmt_footer, "Add new" + String.format(fmt_button_add, "experience_add(this)"));

            db_conn.close();
            res.close();
            return String.format(fmt_all, "experience edit", output);
        } catch (SQLException e){
            System.err.println(e.getMessage());
            return "";
        }
    }

    public String render_project_info(){
        String fmt_button_update = make_fmt_btn("update", "project_update", "plus");
        String fmt_button_remove = make_fmt_btn("remove", "project_update", "times");

        String fmt_table =
                "<table id=\"project_table\">\n" +
                "  <tr><td>idx</td><td>project name:</td > <td>project info:</td> " +
                "<td>project time:</td> <td>project web:</td> <td>pic:</td></tr>\n" +
                "  %s\n" +
                "</table>\n";
        String output = "";
        DataBase_connect db_conn = new DataBase_connect();
        try{
            db_conn.open();
            ResultSet res = db_conn.query("select * from projects");
            int iter = 0;
            while (res.next()){
                String temp = "<tr>\n";
                temp += String.format("<td>%s</td>\n",
                        res.getString("idx"));
                temp += String.format("<td contenteditable=\"true\">%s</td>\n",
                        res.getString("project_name"));
                temp += String.format("<td contenteditable=\"true\">%s</td>\n",
                        res.getString("project_info"));
                temp += String.format("<td contenteditable=\"true\">%s</td>\n",
                        res.getString("project_time"));
                temp += String.format("<td contenteditable=\"true\">%s</td>\n",
                        res.getString("project_web"));
                temp += String.format("<td><img src=\"%s\" width=\"250px\"></td>\n",
                        res.getString("project_pic_path"));
                temp += String.format("<td class=\"tr_sql_btn\">%s</td>\n",
                        String.format("<input type=\"file\" onchange=\"pic_input(event,this,%d)\" " +
                                    "accept=\"image/jpg,image/jpeg,image/gif,image/png\">", iter+1) +
                        String.format(fmt_button_update, iter+1) +
                        String.format(fmt_button_remove, iter+1));
                temp += " </tr>\n";

                output += temp;
                iter ++;
            }

            output = String.format(fmt_table, output);
//            output = String.format(fmt_body, output);
                output += String.format(fmt_footer, "Add new project history" + String.format(fmt_button_add, "project_add(this)"));

            db_conn.close();
            res.close();
            return String.format(fmt_all, "project edit", output);
        } catch (SQLException e){
            System.err.println(e.getMessage());
            return "";
        }
    }
}
