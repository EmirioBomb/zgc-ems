package com.zgc.zhaopin.controller;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

import com.zgc.zhaopin.util.ResultUtil;

/**
 * 页面跳转类
 *
 * @author emirio
 * @version 1.0
 * @date 2019/4/24 14:37
 * @since 1.0
 */
@Slf4j
@Controller
public class RenderController {

    @RequiresAuthentication
    @GetMapping(value = {"", "/index"})
    public ModelAndView home() {
        return ResultUtil.view("index");
    }

    @RequiresPermissions("users")
    @GetMapping("/users")
    public ModelAndView user() {
        return ResultUtil.view("user/list");
    }

    @RequiresPermissions("resources")
    @GetMapping("/resources")
    public ModelAndView resources() {
        return ResultUtil.view("resources/list");
    }

    @RequiresPermissions("roles")
    @GetMapping("/roles")
    public ModelAndView roles() {
        return ResultUtil.view("role/list");
    }

    @RequiresPermissions("dept")
    @GetMapping("/dept")
    public ModelAndView dept() {
        return ResultUtil.view("dept/list");
    }

    @RequiresPermissions("upload")
    @GetMapping("/upload")
    public ModelAndView upload() {
        return ResultUtil.view("upload/list");
    }

    @RequiresPermissions("download")
    @GetMapping("/download")
    public ModelAndView download() {
        return ResultUtil.view("download/list");
    }

    @RequiresPermissions("employee")
    @GetMapping("/employee")
    public ModelAndView employee() {
        return ResultUtil.view("employee/list");
    }

    @RequiresPermissions("swagger")
    @GetMapping("/swagger")
    public ModelAndView swagger() {
        return ResultUtil.redirect("/swagger-ui.html");
    }

    @RequiresPermissions("knife4j")
    @GetMapping("/knife4j")
    public ModelAndView knife4j() {
        return ResultUtil.redirect("/doc.html");
    }
}
