package com.tuxiaoer.shanghai.modules.system.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
* @author: YeJR 
* @version: 2018年5月10日 下午5:43:11
* 菜单实体
*/
@Data
public class Menu implements Serializable{

	/**
	 * 主键
	 */
	private Long id;
	
	/**
	 * 父级菜单id
	 */
	private Long parentId;
	
	/**
	 * 菜单名
	 */
	private String name;
	
	/**
	 * 排序
	 */
	private Integer sort;
	
	/**
	 * 菜单icon
	 */
	private String icon;
	
	/**
	 * 链接
	 */
	private String href;
	
	/**
	 * 是否显示（0：隐藏， 1：显示）
	 */
	private Integer isShow;
	
	/**
	 * 权限标识
	 */
	private String permission;

	/**
	 * 版本
	 */
	private Integer version;

	/**
	 * 父菜单
	 */
	private Menu parent;
	
	/**
	 * 子菜单
	 */
	private List<Menu> children = new ArrayList<>();

	public Menu() {}

	public Menu(Long id) {
		this.id = id;
	}

	/**
	 * 将从数据库获取到的Menu集合按照上下级和排序关系进行重排
	 * @param list
	 * @param sourceList
	 * @param parentId
	 */
	@JsonIgnore
	public static void sortList(List<Menu> list, List<Menu> sourceList, Long parentId){
		for (int i = 0; i < sourceList.size(); i++){
			Menu menu = sourceList.get(i);
			if (parentId.equals(menu.getParentId())){
				list.add(menu);
				// 重新循环从数据库中查询出来的sourcelist，看其中是否有该菜单的子菜单
				for (int j = 0; j < sourceList.size(); j++){
					Menu child = sourceList.get(j);
					if (menu.getId().equals(child.getParentId())){
						// 如果集合中有该菜单的子菜单，进行递归调用，将该菜单的子菜单优先加入到重排后的集合中
						sortList(list, sourceList, menu.getId());
						break;
					}
				}
			}
		}

	}


}
