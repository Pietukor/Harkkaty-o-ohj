package com.example.harkkaduuni;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class WindParser {

    //Singleton:

    private static final WindParser P = new WindParser();

    public static WindParser getInstance() {
        return P;
    }

    private WindParser(){

    }

    // This method fetches weather data based on current time and location given by user.
    // This method parses the data and returns it as a string.

    public static ArrayList parseWind(){
        ArrayList tuotos = new ArrayList<>();
        String location = Fragment1.location;
        String date = Fragment1.date;
        String time = Fragment1.time;

            String url_tuuli = "https://opendata.fmi.fi/wfs?service=WFS&version=2.0.0&request=getFeature&storedquery_id=fmi::forecast::hirlam::surface::point::simple&place=" + location + "&parameters=WindSpeedMS&timeStep=60&starttime=" + date + "T" + time + "Z&endtime=" + date + "T" + time + "Z";

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
