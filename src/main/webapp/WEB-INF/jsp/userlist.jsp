<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@include file="/WEB-INF/jsp/common/head.jsp"%>
        <div class="right">
            <div class="location">
                <strong>你现在所在的位置是:</strong>
                <span>用户管理页面</span>
            </div>
            <div class="search">
           		<form method="get" action="${pageContext.request.contextPath }/jsp/list">
					<input name="method" value="query" class="input-text" type="hidden">
					 <span>用户名：</span>
					 <input name="queryUserName" class="input-text"	type="text" value="${queryUserName }">
					 
					 <span>用户角色：</span>
					<!-- <select name="queryUserRole">
				        	<c:if test="${roleList != null }">
						   <option value="0">--请选择--</option>
						   <c:forEach var="role" items="${roleList}">
						   		<option <c:if test="${role.id == queryUserRole }">selected="selected"</c:if>
						   		value="${role.id}">${role.roleName}</option>
						   </c:forEach>
						</c:if>
	        		</select>-->
					<select id="roleSelect" name="queryUserRole">
						<option value="0">--请选择--</option>
					</select>
					 <input type="hidden" name="pageIndex" value="1"/>
					 <input	value="查 询" type="submit" id="searchbutton">
					 <a href="${pageContext.request.contextPath}/jsp/redirectu" >添加用户</a>
				</form>
            </div>
            <!--用户-->
            <table class="providerTable" cellpadding="0" cellspacing="0">
                <tr class="firstTr">
                    <th width="10%">用户编码</th>
                    <th width="20%">用户名称</th>
                    <th width="10%">性别</th>
                    <th width="10%">年龄</th>
                    <th width="10%">电话</th>
                    <th width="10%">用户角色</th>
                    <th width="30%">操作</th>
                </tr>
                   <c:forEach var="user" items="${pageInfo.list }" varStatus="status">
					<tr>
						<td>
						<span>${user.userCode }</span>
						</td>
						<td>
						<span>${user.userName }</span>
						</td>
						<td>
							<span>
								<c:if test="${user.gender==1}">男</c:if>
								<c:if test="${user.gender==2}">女</c:if>
							</span>
						</td>
						<td>
						<span>${user.age}</span>
						</td>
						<td>
						<span>${user.phone}</span>
						</td>
						<td>
							<span>${user.roleName}</span>
						</td>
						<td>
						<span><a class="viewUser" href="javascript:;" userid=${user.id } username=${user.userName }><img src="${pageContext.request.contextPath }/static/images/read.png" alt="查看" title="查看"/></a></span>
						<span><a class="modifyUser" href="javascript:;" userid=${user.id } username=${user.userName }><img src="${pageContext.request.contextPath }/static/images/xiugai.png" alt="修改" title="修改"/></a></span>
						<span><a class="deleteUser" href="javascript:;" userid=${user.id } username=${user.userName }><img src="${pageContext.request.contextPath }/static/images/schu.png" alt="删除" title="删除"/></a></span>
						</td>
					</tr>
				</c:forEach>
			</table>
			<input type="hidden" id="totalPageCount" value="${pageInfo.total}"/>
		  	<c:import url="rollpage.jsp">
	          	<c:param name="totalCount" value="${pageInfo.total}"/>
	          	<c:param name="currentPageNo" value="${pageInfo.pageNum}"/>
	          	<c:param name="totalPageCount" value="${pageInfo.pages}"/>
          	</c:import>
        </div>
    </section>

<!--点击删除按钮后弹出的页面-->
<div class="zhezhao"></div>
<div class="remove" id="removeUse">
    <div class="removerChid">
        <h2>提示</h2>
        <div class="removeMain">
            <p>你确定要删除该用户吗？</p>
            <a href="#" id="yes">确定</a>
            <a href="#" id="no">取消</a>
        </div>
    </div>
</div>


<%@include file="/WEB-INF/jsp/common/foot.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath }/static/js/userlist.js"></script>
<script type="text/javascript">
	$(function () {
		$.ajax({
			"url":"getrolelist",
			"success":function (data) {
				for (var i = 0; i <data.length ; i++) {
					var role=data[i];
					$("<option></option>").text(role.roleName).attr("value",role.id).appendTo($("#roleSelect"));
				}
			}
		});
	})
</script>