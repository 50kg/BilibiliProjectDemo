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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class IndexFragment extends Fragment {


    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;


    private String[] titles;
    private List<Fragment> fragmentList = new ArrayList<>();

    public IndexFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_index, container, false);
        ButterKnife.bind(this, view);


        initData();

        FragmentTabLayoutAdapter adapter = new FragmentTabLayoutAdapter(getChildFragmentManager(), getContext(), fragmentList, titles);
        viewpager.setAdapter(adapter);
        tablayout.setupWithViewPager(viewpager);
        return view;
    }

    private void initData() {
        //标题
        titles = getResources().getStringArray(R.array.indexTabLayout);

        //Fragment
        fragmentList.add(new LiveFragment());
        fragmentList.add(new RecommendFragment());
        fragmentList.add(new ZhuiFanFragment());
        fragmentList.add(new PandaGameFragment());
        fragmentList.add(new GuanZhuFragment());
        fragmentList.add(new FaXianFragment());
    }

}
