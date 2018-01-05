package com.cci.EnergyFunctions;

public class EnergyFactory {
    public static IEnergyFunction Create(String functionTag){

        switch (functionTag){
            case "dg":
                return new DualGradientEneryFunction();
            default:
                throw new RuntimeException("Energy function not found.");
        }
    }
}
