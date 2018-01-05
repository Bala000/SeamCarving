package com.cci.SeamCarvers;

import com.cci.EnergyFunctions.IEnergyFunction;
import com.cci.utils;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SeamInserter implements ISeamCarver{

    public void Run(Color[][] pixels, int height, int width, int seams, IEnergyFunction energyFunction, boolean isHorizontal){

        Color[][] copy = utils.ColorArrayCopy(pixels, pixels.length, pixels[0].length);
        List<List<Point>> seamPoints = RunSeamRemover(copy, height, width, seams, energyFunction, isHorizontal);
        if (isHorizontal)
            addAndShiftPixelsHorizontally(pixels, seamPoints, height);
        else
            addAndShiftPixelsVertically(pixels, seamPoints, width);

    }

    private void addAndShiftPixelsHorizontally(Color[][] pixels, List<List<Point>> seamPoints, int height){

        int y = 0;
        for (List<Point> points : seamPoints) {
            for (Point p : points) {
                int i = p.i + y;
                int j = p.j;
                int n = height-1;
                while (n >= i) {
                    pixels[n+1][j] = pixels[n][j];
                    n--;
                }
                pixels[i][j] = i == 0? colorAverage(pixels[1][j], pixels[3][j]):
                        i == height-1? colorAverage(pixels[i-1][j], pixels[i-3][j]):
                                colorAverage(pixels[i-1][j], pixels[i+1][j]);

            }
            height++;
            y++;
        }
    }

    private void addAndShiftPixelsVertically(Color[][] pixels, List<List<Point>> seamPoints, int width){

        int x = 0;
        for (List<Point> points : seamPoints) {
            for (Point p : points) {
                int i = p.i;
                int j = p.j + x;
                int n = width-1;
                while (n >= j) {
                    pixels[i][n+1] = pixels[i][n];
                    n--;
                }
                pixels[i][j] = j == 0? colorAverage(pixels[i][1], pixels[i][3]):
                        j == width-1? colorAverage(pixels[i][j-1], pixels[i][j-3]):
                                colorAverage(pixels[i][j-1], pixels[i][j+1]);

            }
            width++;
            x++;
        }
    }

    private Color colorAverage(Color x, Color y){
        Color c = new Color((x.getRed()+y.getRed())/2,
                (x.getGreen()+y.getGreen())/2,
                (x.getBlue()+y.getBlue())/2);
        return c;
    }

    public List<List<Point>> RunSeamRemover(Color[][] pixels, int height, int width, int seams, IEnergyFunction energyFunction, boolean isHorizontal){

        CostMatrixCalculator cmc = new CostMatrixCalculator();
        SeamCalculator sm = new SeamCalculator();
        List<List<Point>> seamPoints = new ArrayList<>();
        while (seams > 0){
            int[][] energy = energyFunction.energyExtractor(pixels, height, width);
            if(isHorizontal){
                int[][] costMatrix = cmc.calculateHorizontalCostMatrix(energy, height, width);
                List<Point> points = sm.getHorizontalSeam(costMatrix, height, width);
                seamPoints.add(points);
                removeAndShiftPixelsHorizontally(pixels, points, height);
                height--;
            }
            else {
                int[][] costMatrix = cmc.calculateVerticalCostMatrix(energy, height, width);
                List<Point> points = sm.getVerticalSeam(costMatrix, height, width);
                seamPoints.add(points);
                removeAndShiftPixelsVertically(pixels, points, width);
                width--;
            }
            seams--;
        }

        return seamPoints;
    }

    private void removeAndShiftPixelsHorizontally(Color[][] pixels, List<Point> points, int height){

        for (Point p : points) {
            int i = p.i;
            int j = p.j;
            while (i + 1 < height) {
                pixels[i][j] = pixels[i + 1][j];
                i++;
            }
        }
    }

    private void removeAndShiftPixelsVertically(Color[][] pixels, List<Point> points, int width){

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
