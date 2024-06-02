package com.example.behomeapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.behomeapp.R;
import com.example.behomeapp.ui.home.tablayout.ResumenFragment;
import com.example.behomeapp.ui.home.tablayout.tareas.TareasFragment;
import com.example.behomeapp.ui.home.tablayout.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TabLayout tabLayout = view.findViewById(R.id.tabLayout);
        ViewPager2 viewPager2 = view.findViewById(R.id.viewPager2);

        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        adapter.addFragment(new ResumenFragment(), "Resumen");
        adapter.addFragment(new TareasFragment(), "Tareas");

        viewPager2.setAdapter(adapter);


        new TabLayoutMediator(tabLayout, viewPager2,
                (tab, position) -> tab.setText(adapter.getFragmentTitle(position))
        ).attach();
    }



}