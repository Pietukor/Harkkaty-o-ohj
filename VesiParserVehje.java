package com.example.harkkaduuni;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class VesiParserVehje {
    //Singleton:

    private static final VesiParserVehje P = new VesiParserVehje();

    public static VesiParserVehje getInstance() {
        return P;
    }

    private VesiParserVehje() {

    }

    public static ArrayList parseWater() {
        ArrayList tuotos = new ArrayList<>();

        String json = getJSON();
        System.out.println(json);

        if (json != null) {
            try {
                JSONObject a = new JSONObject(json);
                JSONArray jsonarray = a.getJSONArray("value");
                for (int i = 0; i < jsonarray.length(); i++) {
                    JSONObject jobject = jsonarray.getJSONObject(i);
                    System.out.println("++++++++++" + (i + 1) + "+++++++++");
                    String jarvi = jobject.getString("Nimi");
                    tuotos.add(jarvi);
                    System.out.println(jarvi);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return tuotos;
    }

    public static String getJSON() {
        String sijainti = Fragment1.sijainti;
        String response = null;
        try {
            URL vesiurl = new URL("http://rajapinnat.ymparisto.fi/api/jarvirajapinta/1.0/odata/Jarvi?$top=10&$filter=KuntaNimi%20eq%20%27"+sijainti+"%27");
            HttpURLConnection conn = (HttpURLConnection) vesiurl.openConnection();
            conn.setRequestMethod("GET");
            InputStream in = new BufferedInputStream(conn.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            response = sb.toString();
            in.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ;
        return response;
    }


}
