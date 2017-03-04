package com.example.sanji.bibiliproject.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.sanji.bibiliproject.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class FaXianFragment extends Fragment {


    @BindView(R.id.btn_debug)
    Button btnDebug;

    public FaXianFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fa_xian, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @OnClick(R.id.btn_debug)
    public void onClick() {
        int a[] = {1};
        Toast.makeText(getContext(), a[1], Toast.LENGTH_SHORT).show();
    }

    private static FaXianFragment faXianFragment;

    public static FaXianFragment getInstance() {
        if (faXianFragment == null) {
            faXianFragment = new FaXianFragment();
        }
        return faXianFragment;
    }
}
