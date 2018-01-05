package com.cci.SeamCarvers;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SeamCalculator {

    public List<Point> getHorizontalSeam(int[][] seamDp, int height, int width){
        List<Point> points = new ArrayList<>();
        int n = width - 1;

        int minEnergy = Integer.MAX_VALUE;
        int minEnergyIndex = -1;

        for(int i = 0; i < height; i++){
            if(seamDp[i][n] < minEnergy){
                minEnergy = seamDp[i][n];
                minEnergyIndex = i;
            }
        }

        points.add(new Point(minEnergyIndex, n));

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
            points.add(new Point(minEnergyIndex, j-1));
        }

        return points;
    }

    public List<Point> getVerticalSeam(int[][] seamDp, int height, int width){
        List<Point> points = new ArrayList<>();
        int n = height - 1;

        int minEnergy = Integer.MAX_VALUE;
        int minEnergyIndex = -1;

        for(int j = 0; j < width; j++){
            if(seamDp[n][j] < minEnergy){
                minEnergy = seamDp[n][j];
                minEnergyIndex = j;
            }
        }

        points.add(new Point(n, minEnergyIndex));

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
            points.add(new Point(i-1, minEnergyIndex));
        }

        return points;
    }
}
