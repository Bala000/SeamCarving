package com.cci.SeamCarvers;

import com.cci.EnergyFunctions.IEnergyFunction;

import java.awt.*;

public interface ISeamCarver {
    void Run(Color[][] pixels, int height, int width, int seams, IEnergyFunction energyFunction, boolean isHorizontal);
}
