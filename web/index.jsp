<%--
Created by IntelliJ IDEA.
User: 86189
Date: 2022/12/3
Time: 20:41
To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.util.Random" %>
<%@ page import="com.action.DataBaseWrapper.DataBase_connect" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%--<%@ page import="java.util.Arrays" %>--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

	Map<String, String[]> params = request.getParameterMap();
	boolean is_updatable = params.containsKey("edit") && params.get("edit")[0].equals("true");

	Connection conn = null;
	Statement stat = null;
	ResultSet resS = null;
	try {
		Class.forName(DataBase_connect.driver_name);
		conn = DriverManager.getConnection(DataBase_connect.source_db);
		stat = conn.createStatement();
	} catch (SQLException | ClassNotFoundException e) {
//		throw new RuntimeException(e);
		conn = null;
		System.out.println(e.getMessage());
	}

	Cookie[] cookies = request.getCookies();
	Map<String, String> cookie_info = new HashMap<>();
	for (Cookie coo: cookies){
		cookie_info.put(coo.getName(), coo.getValue());
	}

%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1" />	
	<title>简历for郑梓昂</title>

	<link rel="stylesheet" href="resume_page/style.css" type="text/css" media="all" />
	<link rel="shortcut icon" href="./images/avatar.png"/>

	<%
		if(is_updatable){
			out.write("<link rel=\"stylesheet\" href=\"resume_page/edit_style.css\" type=\"text/css\"/>");
		}
	%>

</head>
<body class="loading-process">
<div class="loading">
	<div class="loading-circle"></div>
	<div class="loading-avatar"><img src="images/avatar@2x.png" alt="" width="100" height="100"></div>
	<div class="loading-info">正在努力加载中...</div>
</div>
<div id="top" class="section-header">
	<div class="section">
		<nav class="nav" role="navigation">
			<ul>
				<li class="nav-about fade fade1"><a href="#about">关于</a></li>
				<li class="nav-works fade fade4"><a href="#works">作品</a></li>
				<li class="fade back-home"><a href="#top">首页</a></li>
				<li class="nav-skill fade fade2"><a href="#skill">能力</a></li>
				<li class="nav-contact fade fade3"><a href="#contact">联系</a></li>
			</ul>
		</nav>
	</div>
	<div class="nav-bg"></div>
</div>
<div class="home-bg">
	<img src="images/home-bg.jpg" alt="" width="1000" height="667">
</div>
<div class="section-wrap section-fristpage">
	<div class="section">
		<div class="section-content">
			<p class="fade fade1">Hi 我是 郑梓昂</p>
			<p class="fade fade2">这里是我的简历~</p>
			<p class="fade fade3"><a href="manage/admin/">想了解更多，往下滚动哦</a></p>
		</div>
		<a href="#" class="scroll-tip fade fade4">向下滚动</a>
	</div>
</div>

<div id="about" class="section-wrap section-about">
	<div class="section">
		<div class="about-content clearfix section-content">
			<div class="left" id="about_content">
				<h1>关于本人</h1>
				<%
					if (conn == null || stat == null){
						String message = "数据库连接失败，请询问管理员";
						String link = "<a href=\"http://www.zaterval.top\" target=\"_blank\">博客</a>";
						for (int i=0; i<6; i++){
							out.write(String.format("<p class=\"fade fade%d\">%s%s</p>",i+1,link,message));
						}
					} else {
						try {
							resS = stat.executeQuery("select * from contest_info");
							int contest_iter = 0;
							while (resS.next() && contest_iter <= 6){
//								contenteditable="true"
								if (is_updatable){
									out.write(String.format("<p class=\"fade fade%d\" contenteditable=\"true\">%s%s</p>",
											contest_iter+1,resS.getString("contest_name"), resS.getString("rank")));
								} else {
									out.write(String.format("<p class=\"fade fade%d\">%s%s</p>",
											contest_iter+1,resS.getString("contest_name"), resS.getString("rank")));
								}
								contest_iter++;
							}
							resS.close();
						} catch (SQLException e) {
							throw new RuntimeException(e);
						}
					}

					if (is_updatable){
						out.write("<a class=\"more-link\">添加</a>");
						out.write("<a class=\"more-link\">删除</a>");
					}
				%>
			</div>
			<div class="right">
				<img class="my-photo fade fade4" src="images/me.jpg" alt="" width="200" height="250">
			</div>
		</div>
	</div>
</div>

<div id="works" class="section-wrap section-works">
	<div class="section">
		<div class="works-content section-content" id="work_content">
			<h1>近期作品</h1>
			<div class="works-list clearfix" id="work_content_add">
				<%
					if (conn == null || stat == null){
						out.write("<h1>Connect Error</h1>");
					} else {
						try {
							resS = stat.executeQuery("select * from projects");
							int project_iter = 0;
							String web_ref = "";
							String fmt = "<div class=\"works-item %s fade fade%d\">\n" +
									"\t<a %s target=\"_blank\">\n" +
									"\t\t<img src=\"%s\" alt=\"\" width=\"300\" height=\"180\">\n" +
									"\t\t<div class=\"work-info\">\n" +
									"\t\t\t<h2>%s</h2>\n" +
									"\t\t\t<p><strong>时间</strong>%s</p>\n" +
									"\t\t\t<p><strong>工作详情</strong>%s</p>\n" +
									"\t\t</div>\n" +
									"\t</a>\n" +
									"</div>";
							while (resS.next() && project_iter < 6){
								if (is_updatable){
									web_ref = "#";
								}else {
									web_ref = String.format("href=\"%s\"",resS.getString("project_web"));
								}

								out.write(String.format(fmt,
										(project_iter % 3 == 0?"first":""), project_iter+1,
										web_ref,
										resS.getString("project_pic_path"),
										resS.getString("project_name"),
										resS.getString("project_time"),
										resS.getString("project_info")
										));
								project_iter++;
							}
							resS.close();
						} catch (Exception e) {
							out.write("<h1>SQL Error</h1>" + e.getMessage());
//							throw new RuntimeException(e);
						}
					}
				%>
			</div>
			<a class="more-link" href="http://www.zaterval.top">查看更多</a>
		</div>
	</div>
</div>

<div id="skill" class="section-wrap section-skill">
	<div class="section">
		<div class="skill-content section-content" id="skill_content">
			<h1>竞赛与科研经历</h1>
			<ul>
				<%
					Random r = new Random();
					if (conn == null || stat == null){
						out.write("<h1>Connect Error</h1>");
					} else {
						try {
							resS = stat.executeQuery("select * from experience");
							int project_iter = 0;
							String experience_stats = "";
							while (resS.next() && project_iter < 6){
								if (is_updatable){
									experience_stats = "width=\"100%\" contenteditable=\"true\"";
								} else {
									experience_stats = "width=\"100%\"";
								}
								out.write(String.format("<li class=\"fade fade%d\" %s>\n%s：%s\n</li>",
										r.nextInt(5) + 1, experience_stats,
										resS.getString("exp_time"),
										resS.getString("details")));
								project_iter++;
							}
							resS.close();
						} catch (Exception e) {
							out.write("<h1>SQL Error</h1>" + e.getMessage());
//							throw new RuntimeException(e);
						}
					}

					if (is_updatable){
						out.write("<a class=\"more-link\">添加</a>");
						out.write("<a class=\"more-link\">删除</a>");
					}
				%>
			</ul>
		</div>
	</div>
</div>

<div id="contact" class="section-wrap section-contact">
	<div class="section">
		<div class="contact-content clearfix section-content">
			<h1>联系本人</h1>
			<div class="left">
				<div class="contact-ways fade fade1">
					<h2>社交方式</h2>
					<ul>
						<li>邮箱：<a target="_blank" href="mailto:8203200527@csu.edu.cn">8203200527@csu.edu.cn</a></li>
						<li>知乎：<a target="_blank" href="http://www.zhihu.com/people/jiangshui">@j间隔太大</a></li>
						<li>微博：<a target="_blank" href="http://weibo.com/yujiangshui">@yujiangshui</a></li>
						<li>Twitter: <a target="_blank" href="https://twitter.com/yujiangshui">@yujiangshui</a></li>
						<li>Github: <a target="_blank" href="https://github.com/yujiangshui">@yujiangshui</a></li>
					</ul>
				</div>
				<div class="contact-info fade fade2">
					<h2>本站声明</h2>
					<p>没有什么东西好说的啦。</p>
				</div>
			</div>
			<div class="right fade fade3">
				<h2>项目外包</h2>
				<p>如果你有前端相关的需求，可以联系我做外包，目前我主要做：</p>
				<ul>
					<li>根据设计稿做出后端开发人员需要的前端页面，并与后端人员做交流。</li>
					<li>根据需求，对网站前端进行修改和优化，或者转响应式处理。</li>
					<li>做 WordPress 主题或者功能，功能等主要由 <a href="http://wpjam.com/" target="_blank">WPJAM</a> 来做，我来前端有关。</li>
					<li>对于网站建设项目，我也有一些同行朋友可以推荐，也欢迎咨询！</li>
				</ul>
				<p><strong>合作流程</strong>：按照合作谈定的价格，预付 50% 的项目款，开始工作。完成验收后，支付尾款。</p>
				<p><strong>价格参考</strong>：视具体项目难度和工作量等，一般来说，工时价格为 150 左右。</p>
			</div>
		</div>
	</div>
</div>

<div class="overlay"></div>
<div class="state-indicator"></div>

<script type="text/javascript" src="./libs/jquery-1.9.1.min.js"></script>
<script src="./resume_page/js/global.js"></script>
<script src="./resume_page/js/auto_render.js"></script>

</body>
</html>
<%
	if(stat != null){
		try {
			stat.close();
			conn.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
//	System.out.println("JSP render finished.");
%>