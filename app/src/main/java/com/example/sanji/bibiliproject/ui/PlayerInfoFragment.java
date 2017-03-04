package com.example.sanji.bibiliproject.ui;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sanji.bibiliproject.R;
import com.example.sanji.bibiliproject.adapter.FragmentTabLayoutAdapter;
import com.example.sanji.bibiliproject.ui.LivePlayerInfoFragment;
import com.example.sanji.bibiliproject.ui.LivePlayerSayFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class PlayerInfoFragment extends Fragment {


    @BindView(R.id.tablayout_player)
    TabLayout tablayoutPlayer;
    @BindView(R.id.viewpager_player)
    ViewPager viewpagerPlayer;

    public PlayerInfoFragment() {
        // Required empty public constructor
    }

    private String[] titles;
    private List<Fragment> fragmentList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_player_live, container, false);
        ButterKnife.bind(this, view);

        initFragmentData();
        FragmentTabLayoutAdapter adapter = new FragmentTabLayoutAdapter(getChildFragmentManager(), getContext(), fragmentList, titles);
        viewpagerPlayer.setAdapter(adapter);
        tablayoutPlayer.setupWithViewPager(viewpagerPlayer);

        return view;
    }

    private void initFragmentData() {
        //标题
        titles = getResources().getStringArray(R.array.livePlayerTabLayout);

        //Fragment
        fragmentList.add(new LivePlayerInfoFragment());
        fragmentList.add(new LivePlayerSayFragment());
    }


}
