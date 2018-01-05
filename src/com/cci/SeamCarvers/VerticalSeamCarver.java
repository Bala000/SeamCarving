package com.cci.SeamCarvers;

import com.cci.SeamCarvers.ISeamCarver;

import java.awt.*;

public class VerticalSeamCarver implements ISeamCarver {

    public void carveSeam(Color[][] pixels, int[][] energy, int height, int width){
        int[][] seamDp = calculateSeams(energy, height, width);
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

    private int[][] calculateSeams(int[][] energy, int height, int width){

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

    private void removeAndShiftPixels(Color[][] pixels,int[][] seamDp, int i, int j, int width){

        while(j+1 < width){
            pixels[i][j] = pixels[i][j+1];
            seamDp[i][j] = seamDp[i][j+1];
            j++;
        }
    }
}
