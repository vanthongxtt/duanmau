package vn.sefviapp.asm_ps09105.View.Fragment.ThongKe;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import vn.sefviapp.asm_ps09105.Adapter.TopBanChayAdapter;
import vn.sefviapp.asm_ps09105.Api.TopSachBanChayApi.GetTopSachBanChayApilml;
import vn.sefviapp.asm_ps09105.Interface.HoaDonChiTietListener;
import vn.sefviapp.asm_ps09105.Model.HoaDonChiTiet;
import vn.sefviapp.asm_ps09105.R;

public class BanChayFragment extends Fragment {
    RecyclerView recyclerViewTopBanChay;
    TopBanChayAdapter topBanChayAdapter;
    Calendar calendar = Calendar.getInstance();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ban_chay, container, false);

        initControls(v);
        initEvents();

        return v;
    }

    private void initEvents() {
        getDataTopSach();
    }

    private void initControls(View v) {
        recyclerViewTopBanChay = v.findViewById(R.id.recyclerViewTopBanChay);
    }

    private void getDataTopSach(){
        int thisMonth = calendar.get(Calendar.MONTH);

        GetTopSachBanChayApilml getTopSachBanChayApilml = new GetTopSachBanChayApilml();
        getTopSachBanChayApilml.GetTopSachBanChay(String.valueOf(thisMonth + 1), new HoaDonChiTietListener() {
            @Override
            public void getDataSuccess(HoaDonChiTiet hoaDonChiTiet) {

            }

            @Override
            public void getMessageError(Exception e) {

            }

            @Override
            public void getDataArraySuccess(ArrayList<HoaDonChiTiet> hoaDonChiTietArrayList) {
                topBanChayAdapter = new TopBanChayAdapter(getContext(), hoaDonChiTietArrayList);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext().getApplicationContext());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerViewTopBanChay.setHasFixedSize(true);
                recyclerViewTopBanChay.setLayoutManager(layoutManager);
                recyclerViewTopBanChay.setItemAnimator(new DefaultItemAnimator());
                recyclerViewTopBanChay.setAdapter(topBanChayAdapter);
            }

            @Override
            public void getDataError(HoaDonChiTiet hoaDonChiTiet) {

            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        getDataTopSach();
    }
}
