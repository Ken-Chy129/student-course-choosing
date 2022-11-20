package cn.ken.student.rubcourse.common.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.Random;

/**
 * <pre>
 * <p>验证码生成工具</p>
 * </pre>
 *
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date 2022/11/20 17:07
 */
public class ValidateCodeUtil {
    
    private static Random random = new Random();
    
    final private static int width = 165; //验证码的宽

    final private static int height = 45; //验证码的高

    final private static int lineSize = 30; //验证码中夹杂的干扰线数量

    final private static int randomStrNum = 6; //验证码字符个数

    final private static String randomStrings = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWSYZ";
    
    // 字体的设置
    private static Font getFont() {
        return new Font("Times New Roman", Font.PLAIN, 40);
    }

    // 颜色的设置
    private static Color getRandomColor(int fc, int bc) {
        fc = Math.min(fc, 255);
        bc = Math.min(bc, 255);
        int r = fc + random.nextInt(bc - fc - 16);
        int g = fc + random.nextInt(bc - fc - 14);
        int b = fc + random.nextInt(bc - fc - 12);
        return new Color(r, g, b);
    }

    // 干扰线的绘制
    private static void drawLine(Graphics g) {
        int x = random.nextInt(width);
        int y = random.nextInt(height);
        int xl = random.nextInt(20);
        int yl = random.nextInt(10);
        g.drawLine(x, y, x + xl, y + yl);
    }

    // 随机字符的获取
    public static String getRandomString(int num) {
        num = num > 0 ? num : randomStrings.length();
        return String.valueOf(randomStrings.charAt(random.nextInt(num)));
    }

    // 字符串的绘制
    private static void drawString(Graphics g, String rand, int i) {
        g.setFont(getFont());
        g.setColor(getRandomColor(108, 190));
        //System.out.println(random.nextInt(randomString.length()));
        g.translate(random.nextInt(3), random.nextInt(6));
        g.drawString(rand, 40 * i + 10, 25);
    }
    
    // 生成随机图片
    public static BufferedImage getRandomCodeImage(String randomStr) {
        // BufferedImage类是具有缓冲区的Image类,Image类是用于描述图像信息的类
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        Graphics g = image.getGraphics();
        g.fillRect(0, 0, width, height);
        g.setColor(getRandomColor(105, 189));
        g.setFont(getFont());
        // 干扰线
        for (int i = 0; i < lineSize; i++) {
            drawLine(g);
        }
        for (int i = 0; i < randomStrNum; i++) {
            drawString(g, String.valueOf(randomStr.charAt(i)), i);
        }
        g.dispose();
        return image;
    }
    
    // 生成随机图片的base64编码字符串
    public static String getRandomCodeBase64(String randomStr) {
        BufferedImage image = getRandomCodeImage(randomStr);
        String base64String = "";
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(image, "PNG", bos);
            byte[] bytes = bos.toByteArray();
            Base64.Encoder encoder = Base64.getEncoder();
            base64String = encoder.encodeToString(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return base64String;
    }
}
