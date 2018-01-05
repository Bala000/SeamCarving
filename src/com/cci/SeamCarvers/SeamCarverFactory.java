package com.cci.SeamCarvers;

public class SeamCarverFactory {
    public static ISeamCarver Create(String seamcarverTag){

        switch (seamcarverTag){
            case "v":
                return new VerticalSeamCarver();
            default:
                throw new RuntimeException("Energy function not found.");
        }
    }
}
