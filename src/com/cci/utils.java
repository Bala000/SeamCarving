package com.cci;

import java.awt.Color;

public class utils {
    public static Color[][] ColorArrayCopy(Color[][] arr, int height, int width){

        Color[][] newArr =  new Color[height][width];

        for (int i = 0; i < arr.length; i++){
            for (int j = 0; j < arr[0].length; j++){
                newArr[i][j] = arr[i][j];
            }
        }

        return newArr;
    }
}
