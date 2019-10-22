package vn.sefviapp.asm_ps09105.View.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

import vn.sefviapp.asm_ps09105.Adapter.ChiTietHoaDonAdapter;
import vn.sefviapp.asm_ps09105.Adapter.HoaDonChiTietAdapter;
import vn.sefviapp.asm_ps09105.Api.HoaDonChiTiet.GetHoaDonChiTietApilml;
import vn.sefviapp.asm_ps09105.Interface.HoaDonChiTietListener;
import vn.sefviapp.asm_ps09105.Model.HoaDonChiTiet;
import vn.sefviapp.asm_ps09105.R;

public class ChiTietHoaDonActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerViewChiTietHoaDon;
    int idHoaDon;
    ChiTietHoaDonAdapter chiTietHoaDonAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_hoa_don);


        initControls();


    }

    private void initControls() {
        toolbar = findViewById(R.id.toolBarChiTietHoaDon);
        recyclerViewChiTietHoaDon = findViewById(R.id.recyclerViewChiTietHoaDon);

        toolbar.setTitle("Chi Tiết Hóa Đơn");

        idHoaDon = getIntent().getIntExtra("idHoaDon", 0);
        getData();
    }
    private void getData(){
        GetHoaDonChiTietApilml getHoaDonChiTietApilml = new GetHoaDonChiTietApilml();
        getHoaDonChiTietApilml.GetHoaDonChiTiet(String.valueOf(idHoaDon), new HoaDonChiTietListener() {
            @Override
            public void getDataSuccess(HoaDonChiTiet hoaDonChiTiet) {

            }

            @Override
            public void getMessageError(Exception e) {

            }

            @Override
            public void getDataArraySuccess(ArrayList<HoaDonChiTiet> hoaDonChiTietArrayList) {
                chiTietHoaDonAdapter = new ChiTietHoaDonAdapter(getApplicationContext(), hoaDonChiTietArrayList);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext().getApplicationContext());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerViewChiTietHoaDon.setHasFixedSize(true);
                recyclerViewChiTietHoaDon.setLayoutManager(layoutManager);
                recyclerViewChiTietHoaDon.setItemAnimator(new DefaultItemAnimator());
                recyclerViewChiTietHoaDon.setAdapter(chiTietHoaDonAdapter);
            }

            @Override
            public void getDataError(HoaDonChiTiet hoaDonChiTiet) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }
}
