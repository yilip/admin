<%
	String path = request.getContextPath();
%>
<%
	out.println("<link rel='stylesheet' type='text/css' href='" + path + "/style/easyui.css' />");
	out.println("<link rel='stylesheet' type='text/css' href='" + path + "/style/color.css' />");
	out.println("<link rel='stylesheet' type='text/css' href='" + path + "/style/icon.css' />");
	out.println("<link rel='stylesheet' type='text/css' href='" + path + "/style/style.css' />");
	out.println("<script type='text/javascript' src='" + path + "/js/common/jquery.js'></script>");
	out.println("<script type='text/javascript' src='" + path + "/js/common/jquery.easyui.min.js'></script>");
	out.println("<script type='text/javascript' src='" + path + "/js/common/easyui-lang-zh_CN.js'></script>");
	out.println("<script type='text/javascript' src='" + path + "/js/common/jquery-validation/jquery.validate.js'></script>");
	out.println("<script type='text/javascript' src='" + path + "/js/common/jquery-validation/messages_zh.js'></script>");
	out.println("<script type='text/javascript' src='" + path + "/js/common/My97DatePicker/WdatePicker.js'></script>");
	out.println("<script type='text/javascript' src='" + path + "/js/common/common.js'></script>");
	out.println("<script type='text/javascript'>var path=\"" + path + "\";</script>");
%>