package com.cci;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {

    private static Color[][] pixelExtractor(String path) throws Exception {

        BufferedImage bufferedImage = ImageIO.read(new File(path));
        Color[][] colors = new Color[bufferedImage.getHeight()][bufferedImage.getWidth()];
        for (int y = 0; y < bufferedImage.getHeight(); y++) {
            for (int x = 0; x < bufferedImage.getWidth(); x++) {
                colors[y][x] = new Color(bufferedImage.getRGB(x, y));
            }
        }
        return colors;

    }

    private static void saveImage(String path, Color[][] image, int height, int width) throws IOException {
        // Initialize BufferedImage, assuming Color[][] is already properly populated.
        BufferedImage bufferedImage = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);

        // Set each pixel of the BufferedImage to the color from the Color[][].
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                bufferedImage.setRGB(x, y, image[y][x].getRGB());
            }
        }

        File outputFile = new File(path);
        ImageIO.write(bufferedImage, "png", outputFile);

    }

    public static void main(String[] args) throws Exception{
        String imagePath, outputPath, energyFunctionTag;
        int newHeight, newWidth;
        if(args.length > 0){
            imagePath = args[0];
            outputPath = args[1];
            newHeight = Integer.valueOf(args[2]);
            newWidth = Integer.valueOf(args[3]);
            energyFunctionTag = args[4];
        }
        else {
            imagePath = ".\\seamImg.png";
            Color[][] p = pixelExtractor(imagePath);
            int vseams = 50;
            int hseams = 50;
            newHeight = p.length + hseams;
            newWidth = p[0].length + vseams;
            outputPath = ".\\output.png";
            energyFunctionTag = "dg";
        }

        Color[][] pixels = pixelExtractor(imagePath);
        int height = pixels.length;
        int width = pixels[0].length;
        Color[][] newPixels = SeamCarver.Run(pixels, height, width, newHeight, newWidth, energyFunctionTag);
        saveImage(outputPath, newPixels, newHeight, newWidth);

    }


}
