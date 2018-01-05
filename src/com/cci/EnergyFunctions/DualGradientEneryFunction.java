package com.cci.EnergyFunctions;

import java.awt.*;

public class DualGradientEneryFunction implements IEnergyFunction {

    //energy of pixel (x, y) is Δx2(x, y) + Δy2(x, y)
    //Δx2(x, y) = Rx(x, y)2 + Gx(x, y)2 + Bx(x, y)2
    // Rx(x, y), Gx(x, y), and Bx(x, y) are the absolute value in differences of red, green, and blue components between pixel (x + 1, y) and pixel (x − 1, y)
    public int[][] energyExtractor(Color[][] pixels, int height, int width) {

        int[][] energy = new int[height][width];

        calculateCornerPixelEnergy(pixels, energy, height, width);
        calculateBorderPixelEnergy(pixels, energy, height, width);
        calculateNonBorderPixelEnergy(pixels, energy, height, width);

        return energy;
    }

    private void calculateCornerPixelEnergy(Color[][] pixels,int[][] energy, int height, int width) {

        int m = height-1;
        int n = width-1;
        energy[0][0] = calculateEnergy(pixels, 0, 0, 1, 3, 1, 3);
        energy[0][n] = calculateEnergy(pixels, 0, n, 1, 3, n-1, n-3);
        energy[m][0] = calculateEnergy(pixels, m, 0, m-1, m-3, 1, 3);
        energy[m][n] = calculateEnergy(pixels, m, n, m-1, m-3, n-1, n-3);
    }

    private void calculateBorderPixelEnergy(Color[][] pixels,int[][] energy, int height, int width) {

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

    private void calculateNonBorderPixelEnergy(Color[][] pixels,int[][] energy, int height, int width) {

        for(int r=1; r<height-1; r++) {
            for (int c = 1; c < width - 1; c++) {
                energy[r][c] = calculateEnergy(pixels, r, c, r-1, r+1, c-1, c+1);
            }
        }
    }

    private int calculateEnergy(Color[][] pixels,int r, int c, int r1, int r2, int c1, int c2) {

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
}
