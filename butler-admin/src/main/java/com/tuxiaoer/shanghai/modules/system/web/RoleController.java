package com.tuxiaoer.shanghai.modules.system.web;

import com.tuxiaoer.shanghai.common.constant.SystemConstants;
import com.tuxiaoer.shanghai.common.persistence.BaseController;
import com.tuxiaoer.shanghai.common.utils.PageInfo;
import com.tuxiaoer.shanghai.common.utils.Result;
import com.tuxiaoer.shanghai.modules.system.entity.Role;
import com.tuxiaoer.shanghai.modules.system.feignclient.RoleFeignService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;

/**
 * @author ：YeJR
 * @version : 1.0
 * @date ：2019/4/18 16:03
 * @description：角色controller
 */
@Controller
@RequestMapping(value = "system/role")
public class RoleController extends BaseController {

    @Autowired
    private RoleFeignService roleFeignService;

    /**
     * list列表
     *
     * @param role
     * @param model
     * @return
     */
    @RequestMapping(value = "list")
    @RequiresPermissions(value = "system:list:role")
    public String list(Role role, Model model) {
        PageInfo<Role> page = new PageInfo<>(roleFeignService.rolePageList(role, getPageDefault(SystemConstants.PAGE_NUM), getPageDefault(SystemConstants.PAGE_SIZE)).getData());
        model.addAttribute("page", page);
        return "modules/system/roleList";
    }

    /**
     * form表单
     *
     * @param role
     * @param model
     * @return
     */
    @RequestMapping(value = "form")
    @RequiresPermissions(value = {"system:add:role", "system:view:role", "system:edit:role"}, logical = Logical.OR)
    public String form(Role role, Model model) {
        if (role.getId() != null) {
            Result<Role> roleResult = roleFeignService.roleInfo(role);
            role = roleResult.getData();
            model.addAttribute("role", role);
        }
        return "modules/system/roleForm";
    }

    /**
     * 添加或者编辑
     *
     * @param role
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "save")
    @RequiresPermissions(value = {"system:add:role", "system:edit:role"}, logical = Logical.OR)
    public String save(Role role, RedirectAttributes redirectAttributes) {
        Result<String> result = roleFeignService.save(role);
        redirectAttributes.addFlashAttribute("msg", result.getData());
        return "redirect:/system/role/list";
    }

    /**
     * 单个删除
     *
     * @param role
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "delete")
    @RequiresPermissions(value = "system:delete:role")
    public String delete(Role role, RedirectAttributes redirectAttributes) {
        Result<String> result = roleFeignService.delete(role);
        redirectAttributes.addFlashAttribute("msg", result.getData());
        return "redirect:/system/role/list";
    }

    /**
     * 批量删除
     *
     * @param ids
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "deleteAll")
    @RequiresPermissions(value = "system:delete:user")
    public String deleteAll(Long[] ids, RedirectAttributes redirectAttributes) {
        Result<String> result = roleFeignService.deleteAll(ids);
        redirectAttributes.addFlashAttribute("msg", result.getData());
        return "redirect:/system/role/list";
    }

    /**
     * 校验角色名是不是新的
     *
     * @param roleName
     * @param oldRoleName
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "verifyRoleName")
    public boolean verifyUsername(String roleName, String oldRoleName) {
        Result<Boolean> booleanResult = roleFeignService.verifyRoleName(roleName, oldRoleName);
        return booleanResult.getData();
    }

    /**
     * 进入权限设置页面
     *
     * @param role
     * @param model
     * @return
     */
    @RequestMapping(value = "auth")
    @RequiresPermissions(value = "system:allocation:role")
    public String auth(Role role, Model model) {
        Result<HashMap<String, Object>> result = roleFeignService.auth(role);
        HashMap<String, Object> hashMap = result.getData();
        model.addAttribute("role", hashMap.get("role"));
        model.addAttribute("menuList", hashMap.get("menuList"));
        return "modules/system/roleAuth";
    }

    /**
     * 权限保存
     *
     * @param role
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "authSave")
    @RequiresPermissions(value = "system:allocation:role")
    public String authSave(Role role, RedirectAttributes redirectAttributes) {
        Result<String> result = roleFeignService.authSave(role);
        redirectAttributes.addFlashAttribute("msg", result.getData());
        return "redirect:/system/role/list";
    }

}
