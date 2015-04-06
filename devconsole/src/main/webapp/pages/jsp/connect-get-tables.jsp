<%@page import="com.mydev.entity.TableBean"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
	List<TableBean> tables = (List<TableBean>) request.getAttribute("tables");
	
%>    
<div class="panel panel-default">
    <div class="panel-heading">
        表选择
    </div>
    <div class="panel-body">
        <div class="row">
            <div class="col-lg-6">
                <form id="frmTables" role="form">
                	<div class="form-group">
<% 
	for (TableBean tableBean: tables) {		
%>
                        <div class="checkbox">
                            <label>
                                <input name="tableNames" type="checkbox" value="<%=tableBean.getName() %>" /><%=tableBean.getName() %> (<%=tableBean.getComment() %>)
                            </label>
                        </div>      
<%                        
	}	
%>                        
                    </div>				                                        
                    <button type="submit" class="btn btn-default">创建代码</button>                                        
                </form>
            </div>
            
        </div>
    </div>
</div>
<script type="text/javascript">
$(document).ready(function() { 
	var options = {
            url: "/build-code/create_code.do",
            
            success: function () {
                alert("ajax请求成功");
            }
        };
	$('#frmTables').ajaxForm(options);   
   	 } 
);
    </script>