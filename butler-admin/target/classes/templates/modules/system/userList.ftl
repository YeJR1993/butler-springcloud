<!DOCTYPE html>
<html lang="zh">

	<head>
		<#include "../../include/head.ftl"/>
	</head>

	<body class="contentBody">
		<div class="animated fadeIn fullHeight">
			<div class="tpl-portlet-components contentMinHight">
				<div class="portlet-title">
					<div class="caption font-green bold">
						<span class="am-icon-code"></span> 用户列表
					</div>
				</div>
				<div class="tpl-block">
					<div class="am-g">
						<div class="am-u-sm-12 am-u-md-6">
							<div class="am-btn-toolbar">
								<div class="am-btn-group am-btn-group-xs">
									<@shiro.hasPermission name="system:add:user">
										<button type="button" class="am-btn am-btn-default am-btn-success" onclick="openDialogSave('添加用户', '/system/user/form','690px', '660px')"><span class="am-icon-plus"></span> 添加</button>
									</@shiro.hasPermission>
									<@shiro.hasPermission name="system:export:user">
										<button type="button" class="am-btn am-btn-default am-btn-secondary"  onclick="exportData('确认导出？','/system/user/export')"><span class="am-icon-save"></span> 导出</button>
									</@shiro.hasPermission>
									<@shiro.hasPermission name="system:delete:user">
										<button type="button" class="am-btn am-btn-default am-btn-danger" onclick="deleteMultIterm('确认要删除吗？','/system/user/deleteAll')"><span class="am-icon-trash-o"></span> 删除</button>
									</@shiro.hasPermission>
								</div>
							</div>
						</div>
						
						<div class="am-u-sm-12 am-u-md-3">
							<form id="searchForm" action="/system/user/list">
								<input id="pageNum" name="pageNum" type="hidden" value="${page.pageNum}"/>
								<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
								<div class="am-input-group am-input-group-sm">
									<input autocomplete="off" type="text" class="am-form-field" placeholder="输入查询的用户名" name="username" value="${username !''}">
									<span class="am-input-group-btn">
							            <button class="am-btn  am-btn-default am-btn-success tpl-am-btn-success am-icon-search" type="submit"></button>
							        </span>
								</div>
							</form>
						</div>
					</div>
					<div class="am-g">
						<div class="am-u-sm-12">
							<table class="am-table am-table-striped am-table-hover table-main">
								<thead>
									<tr>
										<th class="table-check"><input type="checkbox" class="tpl-table-fz-check checkboxAll"></th>
										<th>ID</th>
										<th>登录名</th>
										<th>联系方式</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody>
									<#list page.list as user>
										<tr>
											<td> <input type="checkbox" id="${user.id}" name="box" class="tpl-table-fz-check"></td>
											<td>${user.id}</td>
											<td>${user.username}</td>
											<td>${user.phone}</td>
											<td>
												<div class="am-btn-toolbar">
                                                    <div class="am-btn-group am-btn-group-xs">
	                                                    <@shiro.hasPermission name="system:view:user">
	                                                    	<button class="am-btn am-btn-default am-btn-xs am-hide-sm-only" onclick="openDialogView('查看用户', '/system/user/form?id=${user.id}','690px', '660px')"><span class="am-icon-copy"></span> 查看</button>
	                                                    </@shiro.hasPermission>
	                                                    <@shiro.hasPermission name="system:edit:user">
															<button class="am-btn am-btn-default am-btn-xs am-text-secondary"  onclick="openDialogSave('编辑用户', '/system/user/form?id=${user.id}','690px', '660px')"><span class="am-icon-pencil-square-o"></span> 编辑</button>
	                                                    </@shiro.hasPermission>
	                                                    <@shiro.hasPermission name="system:delete:user">
			                                            	<button class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only"  onclick="deleteItem('确认要删除该用户吗？', '/system/user/delete?id=${user.id}')"><span class="am-icon-trash-o"></span> 删除</button>
	                                                    </@shiro.hasPermission>
												    </div>
                                                </div>
											</td>
										</tr>
									</#list>
								</tbody>
							</table>
							${page.html}
						</div>
					</div>
				</div>
			</div>
		</div>
		<input id="message" type="hidden" value="${msg!''}" icon=1>
	</body>
</html>