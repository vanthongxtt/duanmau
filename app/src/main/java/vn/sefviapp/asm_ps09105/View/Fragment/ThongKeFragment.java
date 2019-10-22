package vn.sefviapp.asm_ps09105.View.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import vn.sefviapp.asm_ps09105.Adapter.ThongKeAdapter;
import vn.sefviapp.asm_ps09105.R;


public class ThongKeFragment extends Fragment {




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_thong_ke, container, false);

        TabLayout tabLayout = v.findViewById(R.id.main_tabThongKe);
        final ViewPager viewPager = v.findViewById(R.id.main_viewpagerThongKe);
        ThongKeAdapter thongKeAdapter = new ThongKeAdapter(getChildFragmentManager());
        viewPager.setAdapter(thongKeAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));


        return v;
    }

}
