package com.action.admin_render;

public class render_side_list implements admin_render{

    String example_for_list = """
                        <li>
                          <a href="#"><i class="fa fa-circle-o"></i> Level One
                            <span class="pull-right-container">
                              <i class="fa fa-angle-left pull-right"></i>
                            </span>
                          </a>
                          <ul class="treeview-menu">
                            <li><a href="#"><i class="fa fa-circle-o"></i> Level Two</a></li>
                            <li>
                              <a href="#"><i class="fa fa-circle-o"></i> Level Two
                                <span class="pull-right-container">
                                  <i class="fa fa-angle-left pull-right"></i>
                                </span>
                              </a>
                              <ul class="treeview-menu">
                                <li><a href="#"><i class="fa fa-circle-o"></i> Level Three</a></li>
                                <li><a href="#"><i class="fa fa-circle-o"></i> Level Three</a></li>
                              </ul>
                            </li>
                          </ul>
                        </li>
            """;

    String example_list_one = """
            <%--            <li><a href="#"><i class="fa fa-circle-o"></i> Level One</a></li>--%>
            <%--            <li><a href="#"><i class="fa fa-circle-o"></i> Level One</a></li>--%>
            """;

    @Override
    public String render() {
        return "";
    }
}
