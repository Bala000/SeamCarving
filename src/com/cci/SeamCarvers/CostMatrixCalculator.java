package com.cci.SeamCarvers;

public class CostMatrixCalculator {

    public int[][] calculateHorizontalCostMatrix(int[][] energy, int height, int width){

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

    public int[][] calculateVerticalCostMatrix(int[][] energy, int height, int width){

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
}
