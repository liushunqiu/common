package com.liushunqiu.util;

import com.liushunqiu.entity.ValidateCode;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Random;

/**
 * 图形验证码工具类
 */
public class CaptchaUtils {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Random random = new Random();

    private String randString = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";// 随机产生的字符串

    private Font font;

    /**
     * 图片宽
     */
    private int width = 80;
    /**
     * 图片高
     */
    private int height = 26;
    /**
     * 干扰线数量
     */
    private int lineSize = 40;
    /**
     * 随机产生字符数量
     */
    private int defaultNum = 4;

    public CaptchaUtils(int width, int height, int defaultNum) {
        this.width = width;
        this.height = height;
        this.defaultNum = defaultNum;
    }

    /*
     * 获得字体
     */
    private Font getFont() {
        if (font == null){
            font = new Font("Fixedsys", Font.CENTER_BASELINE, 18);
        }
        return font;
    }

    /*
     * 获得颜色
     */
    private Color getRandColor(int fc, int bc) {
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc - 16);
        int g = fc + random.nextInt(bc - fc - 14);
        int b = fc + random.nextInt(bc - fc - 18);
        return new Color(r, g, b);
    }

    /*
     * 绘制字符串
     */
    private String drowString(Graphics g, String randomString, int i) {
        g.setFont(getFont());
        g.setColor(new Color(random.nextInt(101), random.nextInt(111), random
                .nextInt(121)));
        String rand = getRandomString(random.nextInt(randString
                .length()));
        randomString += rand;
        g.translate(random.nextInt(3), random.nextInt(3));
        g.drawString(rand, 13 * i, 16);
        return randomString;
    }

    /*
     * 绘制干扰线
     */
    private void drowLine(Graphics g) {
        int x = random.nextInt(width);
        int y = random.nextInt(height);
        int xl = random.nextInt(13);
        int yl = random.nextInt(15);
        g.drawLine(x, y, x + xl, y + yl);
    }

    /*
     * 获取随机的字符
     */
    public String getRandomString(int num) {
        return String.valueOf(randString.charAt(num));
    }


    /**
     * 生成随机图片
     */
    public ValidateCode getRandcode() {
        // BufferedImage类是具有缓冲区的Image类,Image类是用于描述图像信息的类
        BufferedImage image = new BufferedImage(width, height,BufferedImage.TYPE_INT_BGR);
        Graphics g = image.getGraphics();// 产生Image对象的Graphics对象,改对象可以在图像上进行各种绘制操作
        g.fillRect(0, 0, width, height);
        g.setFont(new Font("Times New Roman", Font.ROMAN_BASELINE, 18));
        g.setColor(getRandColor(110, 133));
        // 绘制干扰线
        for (int i = 0; i <= lineSize; i++) {
            drowLine(g);
        }
        // 绘制随机字符
        String randomString = "";
        for (int i = 1; i <= defaultNum; i++) {
            randomString = drowString(g, randomString, i);
        }
        logger.info("生成的验证码={}",randomString);
        g.dispose();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        String encryptStr = DigestUtils.md5Hex(randomString + System.currentTimeMillis());
        try {
            ImageIO.write(image, "png", outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ValidateCode.builder()
                .setEncryptKey(encryptStr)
                .setCode(Base64.getEncoder().encodeToString(outputStream.toByteArray()));
    }
}
