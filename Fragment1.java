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

    public static ArrayList list;
    public static String location;
    public static String date;
    public static String time;
    public static String chosenWaterbody;

    private Button search;
    private Button save;
    private TextView location_display;
    private TextView wind_display;
    private EditText search_input;
    private Spinner spinner;
    private ListView water_result;

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
        water_result=(ListView) rootView.findViewById(R.id.ListView);

        spinner=(Spinner) rootView.findViewById(R.id.spinner);

        ResultCompiler kone = ResultCompiler.getInstance();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        ArrayAdapter<ArrayList<String>> adapter = new ArrayAdapter<ArrayList<String>>(getActivity(), android.R.layout.simple_spinner_dropdown_item, list);
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

        spinner.setOnItemSelectedListener(this);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                location = search_input.getText().toString();
                location_display.setText(location);

                SimpleDateFormat datef = new SimpleDateFormat("yyyy-MM-dd");
                date = datef.format(new Date());
                System.out.println(date);

                SimpleDateFormat timef = new SimpleDateFormat("HH");
                timef.getCalendar().add(Calendar.HOUR, 1);
                time = timef.format(new Date());
                System.out.println(time);

                try {

                ArrayList tulos = ResultCompiler.Haku();
                list = (ArrayList) tulos.get(1);

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
                WriteFile(location, chosenWaterbody, date);
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

    //This method updates the listview based on found bodies of water

    public void UpdateList(){
        ArrayAdapter<ArrayList> adapter2 = new ArrayAdapter<ArrayList>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, list);
        water_result.setAdapter(adapter2);
    }

    //This method updates the spinner based on found bodies of water

    public void UpdateSpinner(){
        ArrayAdapter<ArrayList<String>> adapter = new ArrayAdapter<ArrayList<String>>(getActivity(), android.R.layout.simple_spinner_dropdown_item, list);
        spinner.setAdapter(adapter);
    }

    //This method writes the result to memory

    public void WriteFile (String location, String chosenWaterbody, String date){
        try {
            OutputStreamWriter osw = new OutputStreamWriter(context.openFileOutput("savedresults.txt", Context.MODE_APPEND));

            String s ="";
            s = "Date: "+date+" - "+location+" - "+chosenWaterbody+"\n";

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
        chosenWaterbody = (String) list.get(i);
        System.out.println(chosenWaterbody);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
