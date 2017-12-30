package com.cci;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {

    private static Color[][] pixelExtractor(String path) throws Exception
    {

            BufferedImage bufferedImage = ImageIO.read(new File(path));
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
    private static int[][] energyExtractor(Color[][] pixels, int height, int width) {

        int[][] energy = new int[height][width];

        calculateCornerPixelEnergy(pixels, energy, height, width);
        calculateBorderPixelEnergy(pixels, energy, height, width);
        calculateNonBorderPixelEnergy(pixels, energy, height, width);

        return energy;
    }

    private static void calculateCornerPixelEnergy(Color[][] pixels,int[][] energy, int height, int width) {

        int m = height-1;
        int n = width-1;
        energy[0][0] = calculateEnergy(pixels, 0, 0, 1, 3, 1, 3);
        energy[0][n] = calculateEnergy(pixels, 0, n, 1, 3, n-1, n-3);
        energy[m][0] = calculateEnergy(pixels, m, 0, m-1, m-3, 1, 3);
        energy[m][n] = calculateEnergy(pixels, m, n, m-1, m-3, n-1, n-3);
    }

    private static void calculateBorderPixelEnergy(Color[][] pixels,int[][] energy, int height, int width) {

        int m = height-1;
        int n = width-1;

        for(int i = 1; i < m; i++){
            energy[i][0] = calculateEnergy(pixels, i, 0, i-1, i+1, 1, 3);
            energy[i][n] = calculateEnergy(pixels, i, n, i-1, i+1, n-1, n-3);
        }

        for(int j = 1; j < n; j++){
            energy[0][j] = calculateEnergy(pixels, 0, j, 1, 3, j-1, j+1);
            energy[m][j] = calculateEnergy(pixels, m, j, m-1, m-3, j-1, j+1);
        }
    }

    private static void calculateNonBorderPixelEnergy(Color[][] pixels,int[][] energy, int height, int width) {

        for(int r=1; r<height-1; r++) {
            for (int c = 1; c < width - 1; c++) {
                int redX = pixels[r - 1][c].getRed() - pixels[r + 1][c].getRed();
                redX = redX * redX;
                int greenX = pixels[r - 1][c].getGreen() - pixels[r + 1][c].getGreen();
                greenX = greenX * greenX;
                int blueX = pixels[r - 1][c].getBlue() - pixels[r + 1][c].getBlue();
                blueX = blueX * blueX;
                int redY = pixels[r][c - 1].getRed() - pixels[r][c + 1].getRed();
                redY = redY * redY;
                int greenY = pixels[r][c - 1].getGreen() - pixels[r][c + 1].getGreen();
                greenY = greenY * greenY;
                int blueY = pixels[r][c - 1].getBlue() - pixels[r][c + 1].getBlue();
                blueY = blueY * blueY;
                energy[r][c] = calculateEnergy(pixels, r, c, r-1, r+1, c-1, c+1);
            }
        }
    }

    private static int calculateEnergy(Color[][] pixels,int r, int c, int r1, int r2, int c1, int c2) {

        int redX = pixels[r1][c].getRed() - pixels[r2][c].getRed();
        redX = redX * redX;
        int greenX = pixels[r1][c].getGreen() - pixels[r2][c].getGreen();
        greenX = greenX * greenX;
        int blueX = pixels[r1][c].getBlue() - pixels[r2][c].getBlue();
        blueX = blueX * blueX;
        int redY = pixels[r][c1].getRed() - pixels[r][c2].getRed();
        redY = redY * redY;
        int greenY = pixels[r][c1].getGreen() - pixels[r][c2].getGreen();
        greenY = greenY * greenY;
        int blueY = pixels[r][c1].getBlue() - pixels[r][c2].getBlue();
        blueY = blueY * blueY;
        return redX + greenX + blueX + redY + greenY + blueY;
    }

    private static int[][] calculateSeams(int[][] energy, int height, int width){

        int[][] seamDp = new int[height][width];

        for(int j = 0; j < width; j++) {
            seamDp[0][j] = energy[0][j];
        }

        for(int i = 1; i < height; i++){
            for(int j = 0; j < width; j++){
                int minEnergy = Integer.MAX_VALUE;
                for(int k = j-1; k <= j+1; k++){
                    if(k >= 0 && k < width){
                        minEnergy = Math.min(minEnergy, energy[i-1][k]);
                    }
                }
                seamDp[i][j] = energy[i][j] + minEnergy;
            }
        }

        return seamDp;
    }

    private static void carveSeam(Color[][] pixels, int[][] seamDp, int height, int width){
        int n = height - 1;

        int minEnergy = Integer.MAX_VALUE;
        int minEnergyIndex = -1;

        for(int j = 0; j < width; j++){
            if(seamDp[n][j] < minEnergy){
                minEnergy = seamDp[n][j];
                minEnergyIndex = j;
            }
        }

        removeAndShiftPixels(pixels,seamDp, n, minEnergyIndex, width);

        for(int i = n; i > 0; i--){
                minEnergy = Integer.MAX_VALUE;
                int currMinEnergyIndex = -1;
                for(int k = minEnergyIndex-1; k <= minEnergyIndex+1; k++){
                    if(k >= 0 && k < width && seamDp[i-1][k] < minEnergy){
                        minEnergy = seamDp[i-1][k];
                        currMinEnergyIndex = k;
                    }
                }
                minEnergyIndex = currMinEnergyIndex;
                removeAndShiftPixels(pixels, seamDp,i-1, minEnergyIndex, width);
        }

    }

    private static void removeAndShiftPixels(Color[][] pixels,int[][] seamDp, int i, int j, int width){

        while(j+1 < width){
            pixels[i][j] = pixels[i][j+1];
            seamDp[i][j] = seamDp[i][j+1];
            j++;
        }
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
	// write your code here
        String imagePath = ".\\seamImg.png";
        int seams = 100;
        Color[][] pixels = pixelExtractor(imagePath);
        int height = pixels.length;
        int width = pixels[0].length;

        while (seams > 0){
            int[][] energy = energyExtractor(pixels, height, width);
            int[][] seamDp = calculateSeams(energy, height, width);
            carveSeam(pixels, seamDp, height, width);
            seams--;
            width--;
        }

        saveImage(".\\output.png", pixels, height, width);

    }


}
