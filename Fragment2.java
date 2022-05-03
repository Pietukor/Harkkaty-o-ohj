package com.example.harkkaduuni;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Fragment2 extends Fragment {

    private Button update;
    public static ListView tallennetut;
    public static ArrayList saved;

    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment2_layout, container, false);

        context = getActivity();

        tallennetut=(ListView) rootView.findViewById(R.id.ListView2);
        update = (Button) rootView.findViewById(R.id.button3);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateHistory();
            }
        });
        return rootView;
    }

    public static Fragment2 newInstance(int page, String title) {
        Fragment2 fragmentSecond = new Fragment2();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentSecond.setArguments(args);
        return fragmentSecond;
    }

    //Tämä metodi päivittää ListViewin adaperin ReadFilen palauttaman ArrayListin mukaiseksi.

    public void UpdateHistory(){

        saved = ReadFile();

        tallennetut.removeAllViewsInLayout();
        ArrayAdapter<ArrayList> adapter2 = new ArrayAdapter<ArrayList>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, saved);
        tallennetut.setAdapter(adapter2);
    }

    //Tämä metodi lukee muistista käyttäjän tallentamat sijainnit ja palauttaa ne ArrayListinä.

    public ArrayList ReadFile(){
        ArrayList tallennetut = new ArrayList();


        try{
            InputStream in = context.openFileInput("tallennettu.txt");

            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String s ="";

            while ((s=br.readLine()) !=null){
                System.out.println(s);
                tallennetut.add(s);
            }

        }catch(Exception i){
            i.printStackTrace();
        }

        return tallennetut;
    }
    

}
