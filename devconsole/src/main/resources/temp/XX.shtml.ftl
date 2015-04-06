<!--#include virtual="/admin/inc/head.inc"-->
<form action="#" id="query_form">
<div  class="filter-bar" >
	<table>
	<tr>
		<th>id:</th>
		<td><input type="text" class="i-txt" name="id"  id="id" /></td>
		
		<td>
			<input type="button" value="检索" class="i-btn" style="margin-left: 15px;" onclick="${bean.value?uncap_first}.search()"/>
		 	<input type="button" value="重置" class="i-btn" style="margin-left: 15px;" onclick="${bean.value?uncap_first}.reSearch();"/>
		</td>
	</tr>
	</table>
	<br/>
</div>
</form>
<table id="site-datagrid" class="easyui-datagrid" title="" 
            data-options="rownumbers:true,singleSelect:true,fitColumns:true,url:'/admin/${bean.value?uncap_first}',pagination:true,toolbar:'#siteTb'">
     <thead>
         <tr>       	          	         
             <th data-options="field:'id',width:50,align:'center',formatter:${bean.value?uncap_first}.fmtOperate">操作</th>
         </tr>
     </thead>
</table>
<script type="text/javascript" src="/admin/js/${bean.value}/${bean.value}.js"></script>
<script type="text/javascript" src="/admin/js/common/common.js"></script>
<script>
url="rownumbers:true,singleSelect:true,fitColumns:true,url:'/admin/${bean.value?uncap_first}',pagination:true,method:'GET',toolbar:'#siteTb'";
document.getElementById("site-datagrid").setAttribute("data-options",url);
</script>
<!--#include virtual="/admin/inc/end.inc"-->