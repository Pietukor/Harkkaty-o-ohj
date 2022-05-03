package com.example.harkkaduuni;

import java.util.ArrayList;

public class ResultCompiler {

    //Singleton:

    private static final ResultCompiler M = new ResultCompiler();
    private static WindParser Vehe;
    private static WaterParser Vehe2;

    public static ResultCompiler getInstance() {
        return M;
    }

    private ResultCompiler(){

    }

    //This method fetches information via WindParser and WaterParser and compiles the result as an ArrayListiksi, which it returns.
    //This method also handles some errors caused by improper inputs.

    public static ArrayList Search(){
        ArrayList result = new ArrayList<>();
        ArrayList windresult = new ArrayList<>();
        String location = Fragment1.location;
        Vehe = WindParser.getInstance();

        windresult = WindParser.parseWind();
        try {
        result.add(windresult.get(0));
        result.add(WaterParser.parseWater());
        } catch (IndexOutOfBoundsException e) {
            System.out.println("location not found");
        }
        return result;
    }
}
