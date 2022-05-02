package com.example.harkkaduuni;

import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Fragment1 extends Fragment implements AdapterView.OnItemSelectedListener{

    public static ArrayList lista;
    public static String sijainti;
    public static String paiva;
    public static String aika;
    public static String valittuVesi;

    private Button search;
    private Button save;
    private TextView location_display;
    private TextView wind_display;
    private EditText search_input;
    private Spinner spinner;
    private ListView listaus;

    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment1_layout, container, false);

        context = getActivity();

        location_display=(TextView) rootView.findViewById(R.id.textView3);
        wind_display=(TextView) rootView.findViewById(R.id.textView4);
        search=(Button) rootView.findViewById(R.id.button);
        save=(Button) rootView.findViewById(R.id.button2);
        search_input=(EditText) rootView.findViewById(R.id.editTextTextPersonName);
        listaus=(ListView) rootView.findViewById(R.id.ListView);

        spinner=(Spinner) rootView.findViewById(R.id.spinner);

        Masiina kone = Masiina.getInstance();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        ArrayAdapter<ArrayList<String>> adapter = new ArrayAdapter<ArrayList<String>>(getActivity(), android.R.layout.simple_spinner_dropdown_item, lista);
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

        spinner.setOnItemSelectedListener(this);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sijainti = search_input.getText().toString();
                location_display.setText(sijainti);

                SimpleDateFormat paivaf = new SimpleDateFormat("yyyy-MM-dd");
                paiva = paivaf.format(new Date());
                System.out.println(paiva);

                SimpleDateFormat aikaf = new SimpleDateFormat("HH");
                aikaf.getCalendar().add(Calendar.HOUR, 1);
                aika = aikaf.format(new Date());
                System.out.println(aika);

                try {

                ArrayList tulos = Masiina.Haku();
                lista = (ArrayList) tulos.get(1);

                UpdateSpinner();
                UpdateList();

                    wind_display.setText((String) tulos.get(0) + " m/s");
                } catch (IndexOutOfBoundsException e) {
                    wind_display.setText("location not found");
                }

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WriteFile(sijainti, valittuVesi, paiva);
            }
        });

        return rootView;
    }

    public static Fragment1 newInstance(int page, String title) {
        Fragment1 fragmentFirst = new Fragment1();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    public void UpdateList(){
        ArrayAdapter<ArrayList> adapter2 = new ArrayAdapter<ArrayList>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, lista);
        listaus.setAdapter(adapter2);
    }

    public void UpdateSpinner(){
        ArrayAdapter<ArrayList<String>> adapter = new ArrayAdapter<ArrayList<String>>(getActivity(), android.R.layout.simple_spinner_dropdown_item, lista);
        spinner.setAdapter(adapter);
    }

    public void WriteFile (String sijainti, String valittuVesi, String paiva){
        try {
            OutputStreamWriter osw = new OutputStreamWriter(context.openFileOutput("tallennettu.txt", Context.MODE_APPEND));

            String s ="";
            s = "Date: "+paiva+" - "+sijainti+" - "+valittuVesi+"\n";

            osw.write(s);
            osw.close();
            System.out.println(s);

        } catch(Exception i){
            i.printStackTrace();
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        System.out.println(i);
        valittuVesi = (String) lista.get(i);
        System.out.println(valittuVesi);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
