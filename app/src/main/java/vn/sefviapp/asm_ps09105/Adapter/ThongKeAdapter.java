package vn.sefviapp.asm_ps09105.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import vn.sefviapp.asm_ps09105.View.Fragment.ThongKe.BanChayFragment;
import vn.sefviapp.asm_ps09105.View.Fragment.ThongKe.DoanhThuFragment;
import vn.sefviapp.asm_ps09105.View.Fragment.ThongKe.TongQuanFragment;

public class ThongKeAdapter extends FragmentPagerAdapter {
    public ThongKeAdapter(FragmentManager fm) {
        super(fm);
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new BanChayFragment();
            case 1:
                return new DoanhThuFragment();

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Bán Chạy";
            case 1:
                return "Doanh Thu";
            default:
                return null;
        }
    }
}
