package com.hbh.service.imp;

import com.hbh.service.CaptchaService;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

@Service
public class CaptchaServiceImpl implements CaptchaService {

    // 验证码字符集
    private static final char[] CHARS = {
            '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M',
            'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
    };

    // 验证码长度
    private static final int CAPTCHA_LENGTH = 4;

    // 图片宽度和高度
    private static final int IMAGE_WIDTH = 120;
    private static final int IMAGE_HEIGHT = 40;

    private Random random = new Random();

    @Override
    public String generateCaptchaText() {
        StringBuilder captcha = new StringBuilder();
        for (int i = 0; i < CAPTCHA_LENGTH; i++) {
            captcha.append(CHARS[random.nextInt(CHARS.length)]);
        }
        return captcha.toString();
    }

    @Override
    public BufferedImage generateCaptchaImage(String captchaText) {
        BufferedImage image = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();

        // 设置背景色
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, IMAGE_WIDTH, IMAGE_HEIGHT);

        // 绘制干扰线
        for (int i = 0; i < 10; i++) {
            g.setColor(getRandomColor(150, 250));
            int x1 = random.nextInt(IMAGE_WIDTH);
            int y1 = random.nextInt(IMAGE_HEIGHT);
            int x2 = random.nextInt(IMAGE_WIDTH);
            int y2 = random.nextInt(IMAGE_HEIGHT);
            g.drawLine(x1, y1, x2, y2);
        }

        // 绘制验证码字符
        for (int i = 0; i < captchaText.length(); i++) {
            g.setFont(new Font("Arial", Font.BOLD, 28 + random.nextInt(5)));
            g.setColor(getRandomColor(20, 130));
            g.drawString(String.valueOf(captchaText.charAt(i)), 20 * i + 15, 30);
        }

        // 添加噪点
        float yawpRate = 0.05f; // 噪声率
        int area = (int) (yawpRate * IMAGE_WIDTH * IMAGE_HEIGHT);
        for (int i = 0; i < area; i++) {
            int x = random.nextInt(IMAGE_WIDTH);
            int y = random.nextInt(IMAGE_HEIGHT);
            image.setRGB(x, y, getRandomColor(100, 200).getRGB());
        }

        g.dispose();
        return image;
    }

    /**
     * 生成随机颜色
     */
    private Color getRandomColor(int fc, int bc) {
        if (fc > 255) fc = 255;
        if (bc > 255) bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }
}