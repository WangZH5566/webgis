package com.ydh.controller;

import com.ydh.util.Constant;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

@Controller
public class KaptchaController
{
    private final Font mFont = new Font("Arial Black", 0, 15);
    private final int lineWidth = 2;
    private final int width = 60;
    private final int height = 22;
    private final int count = 200;

    public static String KAPTCHA_SESSION_KEY = "KAPTCHA_SESSION_KEY";

    @RequestMapping(value="/kaptcha.do", method={org.springframework.web.bind.annotation.RequestMethod.GET}, produces={"image/png"})
    @ResponseBody
    public byte[] kaptcha(HttpServletRequest request) throws IOException { BufferedImage image = new BufferedImage(60, 22, 1);

        Graphics2D g = (Graphics2D)image.getGraphics();

        Random random = new Random();

        g.setColor(getRandColor(200, 250));

        g.fillRect(0, 0, 60, 22);

        g.setFont(this.mFont);

        g.setColor(getRandColor(0, 20));
        g.drawRect(0, 0, 59, 21);

        for (int i = 0; i < 200; i++) {
            g.setColor(getRandColor(150, 200));
            int x = random.nextInt(57) + 1;
            int y = random.nextInt(19) + 1;
            int xl = random.nextInt(2);
            int yl = random.nextInt(2);
            g.drawLine(x, y, x + xl, y + yl);
        }

        String sRand = "";

        for (int i = 0; i < 4; i++) {
            String rand = String.valueOf(random.nextInt(10));
            sRand = sRand + rand;

            g.setColor(new Color(20 + random.nextInt(130), 20 + random.nextInt(130), 20 + random.nextInt(130)));
            g.drawString(rand, 13 * i + 6, 16);
        }

        request.getSession().setAttribute(Constant.CAPTCHA, sRand);

        g.dispose();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        IOUtils.closeQuietly(baos);
        return baos.toByteArray();
    }

    private Color getRandColor(int fc, int bc)
    {
        Random random = new Random();
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);

        return new Color(r, g, b);
    }
}