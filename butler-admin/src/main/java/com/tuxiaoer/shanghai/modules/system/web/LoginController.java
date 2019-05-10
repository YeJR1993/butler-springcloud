package com.tuxiaoer.shanghai.modules.system.web;

import com.tuxiaoer.shanghai.common.exception.LoginException;
import com.tuxiaoer.shanghai.common.shiro.LoginToken;
import com.tuxiaoer.shanghai.common.utils.CodeMsg;
import com.tuxiaoer.shanghai.common.utils.RandomValidateCodeUtil;
import com.tuxiaoer.shanghai.common.utils.Result;
import com.tuxiaoer.shanghai.modules.system.entity.Menu;
import com.tuxiaoer.shanghai.modules.system.entity.User;
import com.tuxiaoer.shanghai.modules.system.feignclient.MenuFeignService;
import com.tuxiaoer.shanghai.modules.system.feignclient.UserFeignService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author ：YeJR
 * @version : 1.0
 * @date ：2019/4/12 10:45
 * @description：登录（公用）模块
 */
@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserFeignService userFeignService;

    @Autowired
    private MenuFeignService menuFeignService;

    /**
     * 登入页
     *
     * @return
     */
    @RequestMapping(value = "/login")
    public String login() {
        // 当前用户信息
        Subject subject = SecurityUtils.getSubject();
        // 用户是授权或者记住我
        if (subject.isAuthenticated() || subject.isRemembered()) {
            return "redirect:/index";
        }
        return "modules/login";
    }

    /**
     * 生成验证码
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/getVerify")
    public void getVerify(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 设置相应类型,告诉浏览器输出的内容为图片
            response.setContentType("image/jpeg");
            // 设置响应头信息，告诉浏览器不要缓存此内容
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expire", 0);
            RandomValidateCodeUtil randomValidateCode = new RandomValidateCodeUtil();
            // 输出验证码图片方法
            randomValidateCode.getRandcode(request, response);
        } catch (Exception e) {
            logger.info("获取验证码失败:{}", e);
        }
    }

    /**
     * 登入 授权，具体的逻辑交由shiro完成
     *
     * @param username
     * @param password
     * @param rememberMe
     * @param validateCode
     * @return
     */
    @RequestMapping(value = "/authorize")
    public String login(String username, String password, @RequestParam(defaultValue = "false") Boolean rememberMe, String validateCode) {

        // 当前用户信息
        Subject subject = SecurityUtils.getSubject();
        // 用户是授权或者记住我
        if (subject.isAuthenticated() || subject.isRemembered()) {
            return "redirect:/index";
        }
        // 没有用户名密码直接回登录页
        if (username == null || password == null) {
            return "redirect:/login";
        }
        Result<User> userResult = userFeignService.getUserByUsername(username);
        User user = userResult.getData();
        if (user != null) {
            Result<List<Menu>> menuResult = menuFeignService.getMenuByUserId(user.getId(), null, user.isAdmin());
            List<Menu> menus = menuResult.getData();
            user.setMenus(menus);
        }

        LoginToken token = new LoginToken(username, password, rememberMe, validateCode, user);

        // 进行验证，捕获异常
        try {
            subject.login(token);
        } catch (UnknownAccountException e) {
            throw new LoginException(CodeMsg.USER_NOT_EXIST);
        } catch (IncorrectCredentialsException e) {
            throw new LoginException(CodeMsg.NAME_NOT_MATCH_PASSWORD);
        } catch (LockedAccountException e) {
            throw new LoginException(CodeMsg.USER_LOCKED);
        } catch (AuthenticationException e) {
            CodeMsg.CODE_INVALID.setMsg(e.getMessage());
            throw new LoginException(CodeMsg.CODE_INVALID);
        }
        return "redirect:/index";
    }

    /**
     * 首页
     *
     * @return
     */
    @RequestMapping(value = "/index")
    public String index() {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        Session session = subject.getSession();
        session.setAttribute("loginName", user.getUsername());
        return "modules/index";
    }

    /**
     * 权限不足
     *
     * @return
     */
    @RequestMapping(value = "/403")
    public String unAuthorize() {
        return "error/403";
    }

    /**
     * 检查Excel导出是否结束
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/exportExcelFinished")
    public Boolean exportExcelFinished() {
        Session session = SecurityUtils.getSubject().getSession();
        Object falg = session.getAttribute("exportExcel");
        if (falg != null) {
            if ((boolean) falg) {
                // 删除该session， 不然对下次导出造成影响
                session.removeAttribute("exportExcel");
                return true;
            }
        }
        return false;
    }

}
