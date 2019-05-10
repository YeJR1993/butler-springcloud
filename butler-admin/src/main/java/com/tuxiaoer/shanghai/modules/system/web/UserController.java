package com.tuxiaoer.shanghai.modules.system.web;

import com.tuxiaoer.shanghai.common.constant.SystemConstants;
import com.tuxiaoer.shanghai.common.excel.ExportExcel;
import com.tuxiaoer.shanghai.common.persistence.BaseController;
import com.tuxiaoer.shanghai.common.utils.PageInfo;
import com.tuxiaoer.shanghai.common.utils.Result;
import com.tuxiaoer.shanghai.modules.system.entity.User;
import com.tuxiaoer.shanghai.modules.system.feignclient.UserFeignService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * @author ：YeJR
 * @version : 1.0
 * @date ：2019/4/12 10:44
 * @description：用户模块
 */
@Controller
@RequestMapping(value = "system/user")
public class UserController extends BaseController {

    @Autowired
    private UserFeignService userFeignService;

    /**
     * list列表
     *
     * @param username
     * @param model
     * @return
     */
    @RequestMapping(value = "list")
    @RequiresPermissions(value = "system:list:user")
    public String list(String username, Model model) {
        PageInfo<User> page = new PageInfo<>(userFeignService.userPageList(username, getPageDefault(SystemConstants.PAGE_NUM), getPageDefault(SystemConstants.PAGE_SIZE)).getData());
        model.addAttribute("page", page);
        return "modules/system/userList";
    }

    /**
     * form表单
     *
     * @param user
     * @param model
     * @return
     */
    @RequestMapping(value = "form")
    @RequiresPermissions(value = {"system:add:user", "system:view:user", "system:edit:user"}, logical = Logical.OR)
    public String form(User user, Model model) {
        Result<HashMap<String, Object>> result= userFeignService.userAndRole(user);
        HashMap<String, Object> hashMap = result.getData();
        model.addAttribute("user", hashMap.get("user"));
        model.addAttribute("allRoles", hashMap.get("allRoles"));
        return "modules/system/userForm";
    }

    /**
     * 添加或者编辑
     *
     * @param user
     * @param redirectAttributes
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "save")
    @RequiresPermissions(value = {"system:add:user", "system:edit:user"}, logical = Logical.OR)
    public String save(User user, RedirectAttributes redirectAttributes) {
        System.out.println(user.toString());
        Result<String> result = userFeignService.save(user);
        redirectAttributes.addFlashAttribute("msg", result.getData());
        return "redirect:/system/user/list";
    }

    /**
     * 单个删除
     *
     * @param user
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "delete")
    @RequiresPermissions(value = "system:delete:user")
    public String delete(User user, RedirectAttributes redirectAttributes) {
        Result<String> result = userFeignService.delete(user);
        redirectAttributes.addFlashAttribute("msg", result.getData());
        return "redirect:/system/user/list";
    }

    /**
     * 批量删除
     *
     * @param ids
     * @param redirectAttributes
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "deleteAll")
    @RequiresPermissions(value = "system:delete:user")
    public String deleteAll(Long[] ids, RedirectAttributes redirectAttributes) {
        Result<String> result = userFeignService.deleteAll(ids);
        redirectAttributes.addFlashAttribute("msg", result.getData());
        return "redirect:/system/user/list";
    }

    /**
     * 导出Excel
     *
     * @param user
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "export")
    @RequiresPermissions(value = "system:export:user")
    public String export(User user, HttpServletResponse response) throws IOException {
        String fileName = "用户数据" + System.currentTimeMillis() + ".xlsx";
        Result<List<User>> listResult = userFeignService.userList(user);
        List<User> list = listResult.getData();
        new ExportExcel("用户数据", User.class).setDataList(list).write(response, fileName).dispose();
        return null;
    }

    /**
     * 校验用户名是不是新的
     *
     * @param username
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "verifyUsername")
    public boolean verifyUsername(String username, String oldUsername) {
        Result<Boolean> booleanResult = userFeignService.verifyUsername(username, oldUsername);
        return booleanResult.getData();
    }

}
