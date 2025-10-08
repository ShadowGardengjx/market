package com.hbh.controller;

import com.hbh.service.CaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/captcha")
public class CaptchaController {

    @Autowired
    private CaptchaService captchaService;

    /**
     * 生成验证码图片
     */
    @RequestMapping(value = "/generate", method = RequestMethod.GET)
    public void generateCaptcha(HttpServletRequest request, HttpServletResponse response) {
        // 设置响应类型为图片
        response.setContentType("image/jpeg");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        HttpSession session = request.getSession();
        try {
            // 生成验证码文本和图片
            String captchaText = captchaService.generateCaptchaText();
            BufferedImage captchaImage = captchaService.generateCaptchaImage(captchaText);

            // 将验证码文本存入session
            session.setAttribute("captcha", captchaText);
            // 设置session5分钟后过期
            session.setMaxInactiveInterval(300);

            // 输出图片到响应流
            ServletOutputStream out = response.getOutputStream();
            ImageIO.write(captchaImage, "jpeg", out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 验证验证码
     */
    @RequestMapping(value = "/validate", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> validateCaptcha(@RequestParam("captcha") String inputCaptcha,
                                               HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        HttpSession session = request.getSession();
        String sessionCaptcha = (String) session.getAttribute("captcha");

        boolean isValid = false;
        if (sessionCaptcha != null && sessionCaptcha.equalsIgnoreCase(inputCaptcha)) {
            isValid = true;
            // 验证成功后移除session中的验证码，确保一次性使用
            session.removeAttribute("captcha");
        }

        result.put("valid", isValid);
        return result;
    }
}