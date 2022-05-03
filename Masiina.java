package com.example.harkkaduuni;

import java.util.ArrayList;

public class Masiina {

    //Singleton:

    private static final Masiina M = new Masiina();
    private static TuuliParserVehje Vehe;
    private static VesiParserVehje Vehe2;

    public static Masiina getInstance() {
        return M;
    }

    private Masiina(){

    }

    //Tämä metodi hakee siistityt tuuli- ja vesistötiedot kutsumalla TuuliParserVejhettä ja VesiParserVehjettä ja kokoaa tulokset yhdeksi ArrayListiksi joka palautetaan.
    //Tämä metodi käsittelee myös väärästä syötteestä johtuvia virheitä

    public static ArrayList Haku(){
        ArrayList tulos = new ArrayList<>();
        ArrayList tuulitulos = new ArrayList<>();
        String sijainti = Fragment1.sijainti;
        Vehe = TuuliParserVehje.getInstance();

        tuulitulos = TuuliParserVehje.parseWind();
        try {
        tulos.add(tuulitulos.get(0));
        tulos.add(VesiParserVehje.parseWater());
        } catch (IndexOutOfBoundsException e) {
            System.out.println("location not found");
        }
        return tulos;
    }
}
