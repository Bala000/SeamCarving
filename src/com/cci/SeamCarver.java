package com.cci;
import com.cci.EnergyFunctions.*;
import com.cci.SeamCarvers.*;

import java.awt.*;

public class SeamCarver {
    public static Color[][] Run(Color[][] pixels, int height, int width, int newHeight, int newWidth, String energyFunctionTag){

        int h = Math.max(height, newHeight);
        int w = Math.max(width, newWidth);

        Color[][] newPixels = utils.ColorArrayCopy(pixels, h, w);

        IEnergyFunction energyFunction = EnergyFactory.Create(energyFunctionTag);

        ISeamCarver sc1 = height > newHeight? new SeamRemover() : new SeamInserter();
        sc1.Run(newPixels, height, width, Math.abs(height-newHeight), energyFunction, true);
        height = newHeight;

        ISeamCarver sc2 = width > newWidth? new SeamRemover() : new SeamInserter();
        sc2.Run(newPixels, height, width, Math.abs(width-newWidth), energyFunction, false);
        width = newWidth;

        return newPixels;
    }
}
