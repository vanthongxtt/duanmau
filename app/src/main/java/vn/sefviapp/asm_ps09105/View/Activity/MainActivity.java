package vn.sefviapp.asm_ps09105.View.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;

import vn.sefviapp.asm_ps09105.R;
import vn.sefviapp.asm_ps09105.Utils.Utils;
import vn.sefviapp.asm_ps09105.View.Fragment.HoaDonFragment;
import vn.sefviapp.asm_ps09105.View.Fragment.NguoiDungFragment;
import vn.sefviapp.asm_ps09105.View.Fragment.SachFragment;
import vn.sefviapp.asm_ps09105.View.Fragment.TheLoaiFragment;
import vn.sefviapp.asm_ps09105.View.Fragment.ThongKeFragment;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView navView;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment sefviFragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_thongke:
                    sefviFragment = new ThongKeFragment();
                    break;
                case R.id.navigation_theloai:
                    sefviFragment = new TheLoaiFragment();
                    break;
                case  R.id.navigation_hoadon:
                    sefviFragment = new HoaDonFragment();
                    break;
                case R.id.navigation_sach:
                    sefviFragment = new SachFragment();
                    break;
                case R.id.navigation_nguoidung:
                    sefviFragment = new NguoiDungFragment();
                    break;
            }
            return loadFragment(sefviFragment);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initControls();
        initEvents();

    }

    private void initEvents() {
    }

    private void initControls() {
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();
        Utils.darkenStatusBar(this, R.color.colorPrimary);
        navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_SELECTED);
        loadFragment(new ThongKeFragment());
    }

    public  boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();
            return true;
        }
        return false;
    }


}
