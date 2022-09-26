package net.xdclass.xdclassredis.controller;


import com.google.code.kaptcha.Producer;
import net.xdclass.xdclassredis.model.UserDo;
import net.xdclass.xdclassredis.model.VideoCardDO;
import net.xdclass.xdclassredis.service.VideoCardService;
import net.xdclass.xdclassredis.util.CommonUtil;
import net.xdclass.xdclassredis.util.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


@RestController
@RequestMapping("api/v1/captcha")
public class CaptchaController {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private Producer captchaProducer;



    /**
     * 获取验证码
     * @param request
     * @param response
     */
    @GetMapping("get_captcha")
    public void  getCaptcha(HttpServletRequest request, HttpServletResponse response){
        String captchaText = captchaProducer.createText();

        String key = getCaptchaKey(request);

        //  10分钟过期
        redisTemplate.opsForValue().set(key,captchaText,10, TimeUnit.MINUTES);

        //获取验证码图片输出
        BufferedImage image = captchaProducer.createImage(captchaText);
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            ImageIO.write(image,"jpg",outputStream);
            outputStream.flush();
            outputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }


    }


    /**
     * 发送短信验证码
     * @param to
     * @param captcha
     * @param request
     * @return
     */
    @GetMapping("send_code")
    public JsonData sendCode(@RequestParam(value = "to",required=true)String to,
                             @RequestParam(value = "captcha",required=true)String captcha, HttpServletRequest request){
        String key = getCaptchaKey(request);
        String cacheCaptcha= redisTemplate.opsForValue().get(key);
        // equals()是Object类中定义的方法,判断两个对象是不是“相等”,会区分大小写;
        // equalsIgnoreCase是string类中定义的方法,用来比较两个字符串中对应的字符是否相等,会忽略大小写
        if (captcha!=null && cacheCaptcha != null && captcha.equalsIgnoreCase(cacheCaptcha)){
            redisTemplate.delete(key);
            // “Java中 TODO的作用 其实就是类似于标记的作用,可以很快的定位到这个位置,方便查找,
            //TODO 发送验证码
            return JsonData.buildSuccess("获取验证码成功");
        }else {
            return JsonData.buildError("验证码错误");
        }

    }

    /**
     * 获取浏览器指纹
     * @param request
     * @return
     */
    public static String getCaptchaKey(HttpServletRequest request){
        String ip = CommonUtil.getIpAddr(request);

        String header = request.getHeader("User-Agent");

        String key = "user-service:captcha:" + CommonUtil.MD5(ip + header);

        return key;

    }



}
