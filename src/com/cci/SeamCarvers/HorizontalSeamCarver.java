package com.cci.SeamCarvers;

import java.awt.*;

public class HorizontalSeamCarver implements ISeamCarver {

    public void carveSeam(Color[][] pixels, int[][] energy, int height, int width){
        int[][] seamDp = calculateSeams(energy, height, width);
        int n = width - 1;

        int minEnergy = Integer.MAX_VALUE;
        int minEnergyIndex = -1;

        for(int i = 0; i < height; i++){
            if(seamDp[i][n] < minEnergy){
                minEnergy = seamDp[i][n];
                minEnergyIndex = i;
            }
        }

        removeAndShiftPixels(pixels,seamDp, minEnergyIndex, n, height);

        for(int j = n; j > 0; j--){
            minEnergy = Integer.MAX_VALUE;
            int currMinEnergyIndex = -1;
            for(int k = minEnergyIndex-1; k <= minEnergyIndex+1; k++){
                if(k >= 0 && k < height && seamDp[k][j-1] < minEnergy){
                    minEnergy = seamDp[k][j-1];
                    currMinEnergyIndex = k;
                }
            }
            minEnergyIndex = currMinEnergyIndex;
            removeAndShiftPixels(pixels, seamDp, minEnergyIndex, j-1, height);
        }
    }

    private int[][] calculateSeams(int[][] energy, int height, int width){

        int[][] seamDp = new int[height][width];

        for(int i = 0; i < height; i++) {
            seamDp[i][0] = energy[i][0];
        }

        for(int j = 1; j < width; j++){
            for(int i = 0; i < height; i++){
                int minEnergy = Integer.MAX_VALUE;
                for(int k = i-1; k <= i+1; k++){
                    if(k >= 0 && k < height){
                        minEnergy = Math.min(minEnergy, energy[k][j-1]);
                    }
                }
                seamDp[i][j] = energy[i][j] + minEnergy;
            }
        }

        return seamDp;
    }

    private void removeAndShiftPixels(Color[][] pixels,int[][] seamDp, int i, int j, int height){

        while(i+1 < height){
            pixels[i][j] = pixels[i+1][j];
            seamDp[i][j] = seamDp[i+1][j];
            i++;
        }
    }
}
