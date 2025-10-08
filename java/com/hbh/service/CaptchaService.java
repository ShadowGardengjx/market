package com.hbh.service;

import java.awt.image.BufferedImage;

public interface CaptchaService {
    /**
     * 生成随机验证码文本
     */
    String generateCaptchaText();

    /**
     * 根据文本生成验证码图片
     */
    BufferedImage generateCaptchaImage(String captchaText);
}