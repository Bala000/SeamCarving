package com.cci.EnergyFunctions;

import java.awt.*;

public interface IEnergyFunction {
    public abstract int[][] energyExtractor(Color[][] pixels, int height, int width);
}
