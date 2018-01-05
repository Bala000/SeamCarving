package com.cci.SeamCarvers;

import java.awt.*;

public interface ISeamCarver {

    public void carveSeam(Color[][] pixels, int[][] energy, int height, int width);
}
