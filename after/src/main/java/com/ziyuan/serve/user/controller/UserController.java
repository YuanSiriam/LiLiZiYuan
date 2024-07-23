package com.ziyuan.serve.user.controller;


import com.google.code.kaptcha.Producer;
import com.ziyuan.serve.user.entity.Customer;
import com.ziyuan.serve.user.service.UserServiceImpl;
import com.ziyuan.serve.util.RedisKeys;
import com.ziyuan.serve.util.RedisUtil;
import com.ziyuan.serve.util.ServeConstant;
import com.ziyuan.serve.util.ServeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * class description goes here
 *
 * @ClassName: UserController
 * @Description:
 */

@Slf4j
@RestController
@RequestMapping("/user")
@Api(value = "用户模块", tags = {"用户模块"})
public class UserController {

    private final int KAPTCHA_LENGTH = 5;

    @Autowired
    Producer producer;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private RedisUtil redisUtil;

    @GetMapping("/find")
    @ApiOperation(value = "查询用户", tags = {"用户模块"})
    public Customer findUser(int id,
                             @RequestParam(defaultValue = "") String username,
                             String mail){

        return userService.findUser(id, username, mail);

    }


    @PostMapping("/register")
    @ApiOperation(value = "注册用户", tags = {"用户模块"})
    public String Register(String username, String password, String mail){

        int code = userService.register(username, password, mail);

        return ServeUtil.getJSONString(code);
    }

    @ApiOperation(value = "登录", tags = {"用户模块"})
    @PostMapping("/login")
    public String login(@RequestBody Map<String, Object> map){
        return ServeUtil.getJSONString(0,"登录成功");
    }

    /**
     * 验证码接口
     */
    @GetMapping("/captcha")
    @ApiOperation(value = "验证码", tags = {"用户模块"})
    public String Captcha() throws IOException{
        String key = ServeUtil.generateUUID();
        String code = producer.createText();

        BufferedImage image = producer.createImage(code);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image,"jpg",outputStream);

        String str = "data:image/jpeg;base64,";

        String base64Img = str + Base64.getEncoder().encodeToString(outputStream.toByteArray());

        redisUtil.hSet(RedisKeys.getKaptchaKey(), key, code, 120);

        HashMap<String, Object> map = new HashMap<>();
        map.put("key", key);
        map.put("captchaImage", base64Img);

        return ServeUtil.getJSONString(ServeConstant.SUCCESS_CODE,null, map);
    }


    @ApiOperation(value = "测试", tags = {"用户模块"})
    @GetMapping(value = "/get", produces = MediaType.TEXT_HTML_VALUE)
    public String get(){
        return "<!DOCTYPE html><html><body><h1>Hello, World!</h1></body></html>";
    }
}
