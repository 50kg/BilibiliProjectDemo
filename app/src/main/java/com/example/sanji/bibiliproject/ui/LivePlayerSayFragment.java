package com.example.sanji.bibiliproject.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sanji.bibiliproject.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LivePlayerSayFragment extends Fragment {


    public LivePlayerSayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_live_player_say, container, false);
    }

}
