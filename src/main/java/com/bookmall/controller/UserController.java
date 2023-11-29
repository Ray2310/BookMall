package com.bookmall.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bookmall.annotation.Authority;
import com.bookmall.annotation.enumUtils.AuthorityType;
import com.bookmall.commonUtils.Result;
import com.bookmall.config.TokenUtils;
import com.bookmall.constants.Constants;
import com.bookmall.domain.dto.UserDTO;
import com.bookmall.domain.entity.LoginForm;
import com.bookmall.domain.entity.User;
import com.bookmall.service.UserService;
import com.sun.xml.internal.fastinfoset.stax.events.Util;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户请求Controller层
 *
 *
 */
/*
这个注解表示该控制器下所有接口都可以通过跨域访问，注解内可以指定某一域名
也可以配置config类
 */
@CrossOrigin
@RestController
public class UserController {
    @Resource
    private UserService userService;


    //todo task1 用户登录
    @PostMapping("/login")
        public Result login(@RequestBody LoginForm loginForm) {
        UserDTO dto = userService.login(loginForm);
        return Result.success(dto);
    }

    //todo task2 用户注册
    @PostMapping("/register")
    public Result register(@RequestBody LoginForm loginForm) {
        User user = userService.register(loginForm);
        return Result.success(user);
    }
    //todo task3 获取用户信息
    //http://localhost:8080/userinfo/admin
    @GetMapping("/userinfo/{username}")
    public Result getUserInfoByName(@PathVariable String username) {
        User one = userService.getOne(username);
        return Result.success(one);
    }
    // 根据用户id查询用户
    @GetMapping("/userid")
    public long getUserId() {
        return TokenUtils.getCurrentUser().getId();
    }

    // -------------------------后台对应请求----------------------------------

    //todo task5 新增 or 修改 用户
    //http://localhost:9191/user
    @PostMapping("/user")
    public Result save(@RequestBody User user) {
        return userService.saveUpdate(user);
    }

    //todo task4 获取用户列表（分页）
    //http://localhost:8080/user/page?pageNum=1&pageSize=5&id=&username=&nickname=
    @GetMapping("/user/page")
    public Result findPage(@RequestParam int pageNum,
                           @RequestParam int pageSize,
                           String id,
                           String username,
                           String nickname) {
        IPage<User> userPage = new Page<>(pageNum, pageSize);
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        if (!Util.isEmptyString(id)) {
            // 模糊查询
            userQueryWrapper.like("id", id);
        }
        if (!Util.isEmptyString(username)) {
            userQueryWrapper.like("username", username);
        }
        if (!Util.isEmptyString(nickname)) {
            userQueryWrapper.like("nickname", nickname);
        }
        userQueryWrapper.orderByDesc("id");
        System.out.println("=====当前操作的用户是=======>" + TokenUtils.getCurrentUser().getUsername());
        return Result.success(userService.page(userPage, userQueryWrapper));
    }

    //todo task5 批量删除操作
    //http://localhost:8080/user/del/batch
    @Authority(AuthorityType.requireAuthority)  //需要管理员权限
    @PostMapping("/user/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        boolean isSuccessful = userService.removeBatchByIds(ids);
        if (isSuccessful) {
            return Result.success();
        } else {
            return Result.error(Constants.CODE_500, "删除失败");
        }
    }

    // todo task6 删除单个用户
    //http://localhost:8080/user/5
    @Authority(AuthorityType.requireAuthority)
    @DeleteMapping("/user/{id}")
    public Result deleteById(@PathVariable int id) {
        boolean isSuccessful = userService.removeById(id);
        if (isSuccessful) {
            return Result.success();
        } else {
            return Result.error(Constants.CODE_500, "删除失败");
        }
    }


    //todo task6 重置密码
    /**
     * 重置密码
     * @param id          用户id
     * @param newPassword 新密码
     * @return 结果
     */
    @GetMapping("/user/resetPassword")
    public Result resetPassword(@RequestParam String id, @RequestParam String newPassword) {
        userService.resetPassword(id, newPassword);
        return Result.success();
    }
}
