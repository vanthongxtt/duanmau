package vn.sefviapp.asm_ps09105.View.Fragment.ThongKe;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import vn.sefviapp.asm_ps09105.Adapter.DoanhThuAdapter;
import vn.sefviapp.asm_ps09105.R;


public class DoanhThuFragment extends Fragment {



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_doanh_thu, container, false);

        TabLayout tabLayout = v.findViewById(R.id.tabDoanhThu);
        final ViewPager viewPager = v.findViewById(R.id.ViewPagerDoanhThu);
        DoanhThuAdapter doanhThuFragment = new DoanhThuAdapter(getChildFragmentManager());
        viewPager.setAdapter(doanhThuFragment);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));



        return v;
    }

}
