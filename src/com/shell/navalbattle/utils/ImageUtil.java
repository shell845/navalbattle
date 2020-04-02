/**
 * @author YC 03/30/2020
 */

package com.shell.navalbattle.utils;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class ImageUtil {
    public static BufferedImage rotateImage(final BufferedImage bufferedimage, final int degree) {
        int width = bufferedimage.getWidth();
        int height = bufferedimage.getHeight();
        int type = bufferedimage.getColorModel().getTransparency();

        BufferedImage img;
        Graphics2D graphics2d;

        (graphics2d = (img = new BufferedImage(width, height, type))
                .createGraphics()).setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        if (degree == -180) { // flip horizontally
            graphics2d.drawImage(bufferedimage, + width, 0, -width, height, null);
        } else {
            graphics2d.rotate(Math.toRadians(degree), width / 2, height / 2);
            graphics2d.drawImage(bufferedimage, 0, 0, null);
            graphics2d.dispose();
        }
        return img;
    }
}
