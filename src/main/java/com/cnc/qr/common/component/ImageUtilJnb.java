package com.cnc.qr.common.component;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.springframework.stereotype.Component;

/**
 * 画像圧縮実現クラス.
 */
@Component
public class ImageUtilJnb {

    /**
     * 缩图.
     */
    public boolean zoomFile(String oldFile, String zoomFile, double newRate) {
        double rate = 0.8;
        long newFile = new File(oldFile).length();
        boolean isResult = false;
        try {
            // 如果首次压缩还大于2MB则继续处理
            while ((float) newFile / 1048576 >= newRate) {
                rate = rate - 0.05;// 暂定按照0.03频率压缩
                BufferedImage srcImage = ImageIO.read(new File(oldFile));
                int width = (int) (srcImage.getWidth() * rate);
                int height = (int) (srcImage.getHeight() * rate);
                BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                Graphics g = image.getGraphics();
                g.drawImage(srcImage, 0, 0, width, height, null);
                // 缩小
                ImageIO.write(image, "jpg", new File(zoomFile));
                newFile = new File(zoomFile).length();
                isResult = true;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            isResult = false;
        }
        return isResult;
    }

    /**
     * 对图片进行旋转.
     *
     * @param src   被旋转图片
     * @param angel 旋转角度
     * @return 旋转后的图片
     */
    private BufferedImage rotate(Image src, int angel) {
        int srcWidth = src.getWidth(null);
        int srcHeight = src.getHeight(null);
        // 计算旋转后图片的尺寸
        Rectangle rectDes = calcRotatedSize(new Rectangle(new Dimension(srcWidth, srcHeight)),
            angel);
        BufferedImage res = null;
        res = new BufferedImage(rectDes.width, rectDes.height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = res.createGraphics();
        // 进行转换
        g2.translate((rectDes.width - srcWidth) / 2, (rectDes.height - srcHeight) / 2);
        g2.rotate(Math.toRadians(angel), srcWidth / 2, srcHeight / 2);

        g2.drawImage(src, null, null);
        return res;
    }

    /**
     * 计算旋转后的图片.
     *
     * @param src   被旋转的图片
     * @param angel 旋转角度
     * @return 旋转后的图片
     */
    private Rectangle calcRotatedSize(Rectangle src, int angel) {
        // 如果旋转的角度大于90度做相应的转换
        if (angel >= 90) {
            if (angel / 90 % 2 == 1) {
                int temp = src.height;
                src.height = src.width;
                src.width = temp;
            }
            angel = angel % 90;
        }

        double r = Math.sqrt(src.height * src.height + src.width * src.width) / 2;
        double len = 2 * Math.sin(Math.toRadians(angel) / 2) * r;
        double angelAlpha = (Math.PI - Math.toRadians(angel)) / 2;
        double angelDaltaWidth = Math.atan((double) src.height / src.width);
        double angelDaltaHheight = Math.atan((double) src.width / src.height);

        int lenDaltaWidth = (int) (len * Math.cos(Math.PI - angelAlpha - angelDaltaWidth));
        int lenDaltaHeight = (int) (len * Math.cos(Math.PI - angelAlpha - angelDaltaHheight));
        int desWidth = src.width + lenDaltaWidth * 2;
        int desHeight = src.height + lenDaltaHeight * 2;
        return new Rectangle(new Dimension(desWidth, desHeight));
    }
}
