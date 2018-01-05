package com.cci;
import com.cci.EnergyFunctions.EnergyFactory;
import com.cci.EnergyFunctions.IEnergyFunction;
import com.cci.SeamCarvers.ISeamCarver;
import com.cci.SeamCarvers.SeamCarverFactory;

import java.awt.*;

public class SeamCarver {
    public static void Run(Color[][] pixels, int height, int width, int newHeight, int newWidth, String energyFunctionTag){
        IEnergyFunction energyFunction = EnergyFactory.Create(energyFunctionTag);

        ISeamCarver vsc = SeamCarverFactory.Create("v");

        while (width > newWidth){
            int[][] energy = energyFunction.energyExtractor(pixels, height, width);
            vsc.carveSeam(pixels, energy, height, width);
            width--;
        }

        /*ISeamCarver hsc = SeamCarverFactory.Create("h");

        while (height > newHeight){
            int[][] energy = energyFunction.energyExtractor(pixels, height, width);
            hsc.carveSeam(pixels, energy, height, width);
            height--;
        }*/

    }
}
