package vn.sefviapp.asm_ps09105.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import vn.sefviapp.asm_ps09105.View.Fragment.ThongKe.DoanhThu.NamFragment;
import vn.sefviapp.asm_ps09105.View.Fragment.ThongKe.DoanhThu.NgayFragment;
import vn.sefviapp.asm_ps09105.View.Fragment.ThongKe.DoanhThu.ThangFragment;
import vn.sefviapp.asm_ps09105.View.Fragment.ThongKe.DoanhThu.TuanFragment;

public class DoanhThuAdapter extends FragmentPagerAdapter {
    public DoanhThuAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new NgayFragment();
            case 1:
                return new TuanFragment();
            case 2:
                return new ThangFragment();
            case 3:
                return new NamFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Ngày";
            case 1:
                return "Tuần";
            case 2:
                return "Tháng";
            case 3:
                return "Năm";
            default:
                return null;
        }
    }
}
