package com.example.sanji.bibiliproject.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sanji.bibiliproject.R;

import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class LivePlayerInfoFragment extends Fragment {


    public LivePlayerInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_live_player_info, container, false);

        return view;
    }

}
