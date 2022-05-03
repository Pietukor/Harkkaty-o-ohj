package com.example.harkkaduuni;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class TuuliParserVehje {

    //Singleton:

    private static final TuuliParserVehje P = new TuuliParserVehje();

    public static TuuliParserVehje getInstance() {
        return P;
    }

    private TuuliParserVehje(){

    }

    // Tämä metodi Hakee käyttäjän syöttämän paikkakunnan ja senhetkisen kellonajan mukaan ilmatieteen avoimesta rajapinnasta säädataa.
    // metodi käsittelee xml-muotoisen dokumentin ja palauttaa String-muotoisen tuulennopeuden.

    public static ArrayList parseWind(){
        ArrayList tuotos = new ArrayList<>();
        String sijainti = Fragment1.sijainti;
        String paiva = Fragment1.paiva;
        String aika = Fragment1.aika;

            String url_tuuli = "https://opendata.fmi.fi/wfs?service=WFS&version=2.0.0&request=getFeature&storedquery_id=fmi::forecast::hirlam::surface::point::simple&place=" + sijainti + "&parameters=WindSpeedMS&timeStep=60&starttime=" + paiva + "T" + aika + "Z&endtime=" + paiva + "T" + aika + "Z";

        try {

            DocumentBuilder docB = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document xmldoc = docB.parse(url_tuuli);

            Element root = xmldoc.getDocumentElement();
            root.normalize();
            String windSpeed = root.getElementsByTagName("BsWfs:ParameterValue").item(0).getTextContent();
            System.out.println(windSpeed);
            tuotos.add(windSpeed);
        }
        catch (ParserConfigurationException e) {
            System.err.println("Couldn't initialize parser.");
            e.printStackTrace();
        }
        catch (SAXException | IOException e) {
            System.err.println("Couldn't fetch API data.");
            e.printStackTrace();
        }


        return tuotos;
    }

}
