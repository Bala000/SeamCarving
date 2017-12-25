package com.cci;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {

    public static Color[][] pixelExtractor(String path) throws Exception
    {

            BufferedImage bufferedImage = ImageIO.read(new File("C:\\Users\\Balachandar\\IdeaProjects\\SeamCarvingAlgo\\seamImg.jpeg"));
            Color[][] colors = new Color[bufferedImage.getHeight()][bufferedImage.getWidth()];
            for(int y=0; y < bufferedImage.getHeight();y++)
            {
                for(int x=0; x < bufferedImage.getWidth(); x++)
                {
                    colors[y][x] = new Color(bufferedImage.getRGB(x,y));
                }
            }
            return colors;

    }

    //energy of pixel (x, y) is Δx2(x, y) + Δy2(x, y)
    //Δx2(x, y) = Rx(x, y)2 + Gx(x, y)2 + Bx(x, y)2
    // Rx(x, y), Gx(x, y), and Bx(x, y) are the absolute value in differences of red, green, and blue components between pixel (x + 1, y) and pixel (x − 1, y)
    private static int[][] energyExtractor(Color[][] pixels) {

        int[][] energy = new int[pixels.length][pixels[0].length];
        for(int r=1; r<pixels.length-1; r++)
        {
            for(int c=1; c<pixels[0].length-1; c++)
            {
                int redX = pixels[r-1][c].getRed() - pixels[r+1][c].getRed();
                redX = redX*redX;
                int greenX = pixels[r-1][c].getGreen() - pixels[r+1][c].getGreen();
                greenX = greenX*greenX;
                int blueX = pixels[r-1][c].getBlue() - pixels[r+1][c].getBlue();
                blueX = blueX*blueX;
                int redY = pixels[r][c-1].getRed() - pixels[r][c+1].getRed();
                redY = redY * redY;
                int greenY = pixels[r][c-1].getGreen() - pixels[r][c+1].getGreen();
                greenY = greenY * greenY;
                int blueY = pixels[r][c-1].getBlue() - pixels[r][c+1].getBlue();
                blueY = blueY * blueY;
                energy[r][c] = redX + greenX + blueX + redY + greenY + blueY;
            }
        }
        return energy;
    }


    public static void main(String[] args) throws Exception{
	// write your code here
        String imagePath = "C:\\Users\\Balachandar\\IdeaProjects\\SeamCarvingAlgo\\seamImg.jpeg";
        Color[][] pixels = pixelExtractor(imagePath);
        int[][] energy = energyExtractor(pixels);
        for(int i=0;i<pixels.length;i++)
        {
            for(int j=0;j<pixels[0].length;j++)
            {
                System.out.println(pixels[i][j].getRed()+" "+pixels[i][j].getGreen()+" "+pixels[i][j].getBlue());
            }
        }
    }


}
