package Tracker;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class AlertImage {
    private ImageView alertImage;



    public AlertImage(String imageUrl, String wikiLink, double maxW, double maxH) {
        Image alertIm=new Image(imageUrl);
        alertIm = SwingFXUtils.toFXImage(trimImage(SwingFXUtils.fromFXImage(alertIm, null)), null);
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(alertIm, null), "png", new File("tmp.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        alertIm=new Image("tmp.png",maxW,maxH,true,true);
        File im=new File("tmp.png");
        im.delete();
        alertImage=new ImageView(alertIm);
        alertImage.setOnMouseClicked((MouseEvent event) -> {
            if (Desktop.isDesktopSupported()) {
                try {
                    Desktop.getDesktop().browse(new URI(wikiLink));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private BufferedImage trimImage(BufferedImage image) {
        WritableRaster raster = image.getAlphaRaster();
        int width = raster.getWidth();
        int height = raster.getHeight();
        int left = 0;
        int top = 0;
        int right = width - 1;
        int bottom = height - 1;
        int minRight = width - 1;
        int minBottom = height - 1;

        top:
        for (; top < bottom; top++) {
            for (int x = 0; x < width; x++) {
                if (raster.getSample(x, top, 0) != 0) {
                    minRight = x;
                    minBottom = top;
                    break top;
                }
            }
        }

        left:
        for (; left < minRight; left++) {
            for (int y = height - 1; y > top; y--) {
                if (raster.getSample(left, y, 0) != 0) {
                    minBottom = y;
                    break left;
                }
            }
        }

        bottom:
        for (; bottom > minBottom; bottom--) {
            for (int x = width - 1; x >= left; x--) {
                if (raster.getSample(x, bottom, 0) != 0) {
                    minRight = x;
                    break bottom;
                }
            }
        }

        right:
        for (; right > minRight; right--) {
            for (int y = bottom; y >= top; y--) {
                if (raster.getSample(right, y, 0) != 0) {
                    break right;
                }
            }
        }

        return image.getSubimage(left, top, right - left + 1, bottom - top + 1);
    }

    public ImageView getAlertImage() {
        return alertImage;
    }
}
