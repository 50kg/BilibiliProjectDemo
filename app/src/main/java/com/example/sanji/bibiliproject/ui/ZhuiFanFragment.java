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
public class ZhuiFanFragment extends Fragment {


    public ZhuiFanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_zhui_fan, container, false);
    }

    private static ZhuiFanFragment zhuiFanFragment;

    public static ZhuiFanFragment getInstance() {
        if (zhuiFanFragment == null) {
            zhuiFanFragment = new ZhuiFanFragment();
        }
        return zhuiFanFragment;
    }
}
