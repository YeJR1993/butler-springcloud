package com.tuxiaoer.shanghai.modules.system.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tuxiaoer.shanghai.common.excel.ExcelField;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author ：YeJR
 * @version : 1.0
 * @date ：2019/4/4 9:46
 * @description：系统用户实体
 */
@Data
public class User implements Serializable {

    /**
     * 用户ID（主键）
     */
    @ExcelField(title="ID", type=1, align=2, sort=1)
    private Long id;

    /**
     * 用户名
     */
    @ExcelField(title="用户名", align=2, sort=2)
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 电话
     */
    @ExcelField(title="手机号", align=2, sort=3)
    private String phone;

    /**
     * 头像
     */
    @ExcelField(title="头像", align=2, sort=4)
    private String headImage;

    /**
     * 开始时间
     */
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @ExcelField(title="开始时间", align=2, sort=5)
    private Date startTime;

    /**
     * 结束时间
     */
    @JSONField (format="yyyy-MM-dd HH:mm:ss")
    @ExcelField(title="结束时间", align=2, sort=6)
    private Date endTime;

    /**
     * 版本
     */
    private Integer version;

    /**
     * 新增属性用于：用户保存并保存角色
     */
    @JsonIgnore
    private List<Integer> roleIds;

    /**
     * 角色
     */
    private List<Role> roles = new ArrayList<>();

    /**
     * 用户拥有的菜单
     */
    private List<Menu> menus = new ArrayList<>();

    public User() {}

    public User(Long id) {
        this.id = id;
    }

    public User(String username) {
        this.username = username;
    }

    public Boolean isAdmin() {
        for (Role role : roles) {
            if (role.getIsAdmin() == 1) {
                return true;
            }
        }
        return false;
    }

}
