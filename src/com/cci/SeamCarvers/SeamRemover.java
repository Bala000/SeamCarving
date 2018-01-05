package com.cci.SeamCarvers;

import com.cci.EnergyFunctions.IEnergyFunction;

import java.awt.*;
import java.util.List;

public class SeamRemover implements ISeamCarver{

    public void Run(Color[][] pixels, int height, int width, int seams, IEnergyFunction energyFunction, boolean isHorizontal){

        CostMatrixCalculator cmc = new CostMatrixCalculator();
        SeamCalculator sm = new SeamCalculator();
        while (seams > 0){
            int[][] energy = energyFunction.energyExtractor(pixels, height, width);
            if(isHorizontal){
                int[][] costMatrix = cmc.calculateHorizontalCostMatrix(energy, height, width);
                List<Point> points = sm.getHorizontalSeam(costMatrix, height, width);
                removeAndShiftPixelsHorizontally(pixels, points, height);
                height--;
            }
            else {
                int[][] costMatrix = cmc.calculateVerticalCostMatrix(energy, height, width);
                List<Point> points = sm.getVerticalSeam(costMatrix, height, width);
                removeAndShiftPixelsVertically(pixels, points, width);
                width--;
            }
            seams--;
        }
    }

    private void removeAndShiftPixelsHorizontally(Color[][] pixels,List<Point> points, int height){

        for (Point p : points) {
            int i = p.i;
            int j = p.j;
            while (i + 1 < height) {
                pixels[i][j] = pixels[i + 1][j];
                i++;
            }
        }
    }

    private void removeAndShiftPixelsVertically(Color[][] pixels,List<Point> points, int width){

        for (Point p : points) {
            int i = p.i;
            int j = p.j;
            while (j + 1 < width) {
                pixels[i][j] = pixels[i][j + 1];
                j++;
            }
        }
    }
}
