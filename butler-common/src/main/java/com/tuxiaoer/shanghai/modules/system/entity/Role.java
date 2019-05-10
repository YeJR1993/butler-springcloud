package com.tuxiaoer.shanghai.modules.system.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
* @author: YeJR 
* @version: 2018年5月10日 下午5:24:11
* 系统角色
*/
@Data
public class Role implements Serializable{

	/**
	 * 角色id
	 */
	private Long id;
	
	/**
	 * 角色名
	 */
	private String roleName;
	
	/**
	 * 是否管理员（0：不是， 1：是）
	 */
	private Integer isAdmin;
	
	/**
	 * 状态（0：不可用， 1：可用）
	 */
	private Integer status;

	/**
	 * 版本
	 */
	private Integer version;
	
	/**
	 * 该角色拥有的菜单列表
	 */
	private List<Menu> menus = new ArrayList<>();
	
	/**
	 * checkbox状态，专门为用户FORM页面显示角色时，设置的字段
	 */
	private String checkbox;

	public Role() {}

	public Role(Long id) {
		this.id = id;
	}

	/**
	 * 这两个方法供页面 ${role.menuIds} 调用
	 * @return
	 */
	@JsonIgnore
	public String getMenuIds() {
		List<Long> menuIdList = new ArrayList<>();
		for (Menu menu : menus) {
			menuIdList.add(menu.getId());
		}
		return StringUtils.join(menuIdList, ",");
	}
	
	@JsonIgnore
	public void setMenuIds(String menuIds) {
		menus = new ArrayList<Menu>();
		if (menuIds != null){
			String[] ids = StringUtils.split(menuIds, ",");
			for (String id : ids) {
				Menu menu = new Menu();
				menu.setId(Long.parseLong(id));
				menus.add(menu);
			}
		}
	}



}
