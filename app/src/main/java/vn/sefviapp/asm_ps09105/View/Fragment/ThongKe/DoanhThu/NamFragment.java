package vn.sefviapp.asm_ps09105.View.Fragment.ThongKe.DoanhThu;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;

import vn.sefviapp.asm_ps09105.Adapter.DateAdapter;
import vn.sefviapp.asm_ps09105.Api.ThongKeApi.ThongKeNamApi.GetThongKeNamHoaDonApilml;
import vn.sefviapp.asm_ps09105.Api.ThongKeApi.ThongKeNamApi.GetThongKeNamSachApilml;
import vn.sefviapp.asm_ps09105.Interface.ThongKeListener;
import vn.sefviapp.asm_ps09105.Model.Date;
import vn.sefviapp.asm_ps09105.R;


public class NamFragment extends Fragment {
    Spinner spChonNam;
    TextView txtSachAddNam, txtSachXuatNam;
    PieChart piechartNam;
    int namHienTai;
    String tongGiaSachAdd = "0";
    String tongGiaSachXuat = "0";
    Calendar calendar = Calendar.getInstance();
    ArrayList<Date> dateArrayList;
    DateAdapter dateAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_nam, container, false);
        initControls(v);
        initEvents();
        return v;
    }

    private void initEvents() {
        spChonNam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Date date = dateArrayList.get(i);
                if (date.getId() == 0){
                    getYear();
                    getDataSach();
                    getDataHoaDon();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            txtSachAddNam.setText("Tổng Nhập: " + tongGiaSachAdd);
                            txtSachXuatNam.setText("Tổng Xuất: " + tongGiaSachXuat);
                            try {
                                pieChart();
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    },2000);
                }else {
                    namHienTai = Integer.parseInt(date.getDate());
                    getDataSach();
                    getDataHoaDon();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            txtSachAddNam.setText("Tổng Nhập: " + tongGiaSachAdd);
                            txtSachXuatNam.setText("Tổng Xuất: " + tongGiaSachXuat);
                            try {
                                pieChart();
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    },2000);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void initControls(View v) {
        spChonNam = v.findViewById(R.id.spChonNam);
        txtSachAddNam = v.findViewById(R.id.txtSachAddNam);
        txtSachXuatNam = v.findViewById(R.id.txtSachXuatNam);
        piechartNam = v.findViewById(R.id.piechartNam);
        dateArrayList = new ArrayList<>();
        dateArrayList.add(new Date(0, "Chọn năm"));
        dateArrayList.add(new Date(1, "2017"));
        dateArrayList.add(new Date(1, "2018"));
        dateArrayList.add(new Date(1, "2019"));
        dateAdapter = new DateAdapter(getContext(), dateArrayList);
        spChonNam.setAdapter(dateAdapter);
        getYear();
        getDataSach();
        getDataHoaDon();
    }

    private void getDataSach() {
        GetThongKeNamSachApilml getThongKeNamSachApilml = new GetThongKeNamSachApilml();
        getThongKeNamSachApilml.GetThongKeNamSach(String.valueOf(namHienTai), new ThongKeListener() {
            @Override
            public void GetDataSuccessNhap(String dataNhap) {
                tongGiaSachAdd = dataNhap;
            }

            @Override
            public void GetDataSuccessXuat(String dataXuat) {

            }

            @Override
            public void GetDataErr(Exception e) {

            }
        });
    }

    private void getDataHoaDon() {
        GetThongKeNamHoaDonApilml getThongKeNamHoaDonApilml = new GetThongKeNamHoaDonApilml();
        getThongKeNamHoaDonApilml.GetThongKeNamHoaDon(String.valueOf(namHienTai), new ThongKeListener() {
            @Override
            public void GetDataSuccessNhap(String dataNhap) {

            }

            @Override
            public void GetDataSuccessXuat(String dataXuat) {
                tongGiaSachXuat = dataXuat;
            }

            @Override
            public void GetDataErr(Exception e) {

            }
        });
    }

    private void getYear() {
        int year = calendar.get(Calendar.YEAR);
        namHienTai = year;
    }
    private void pieChart(){
        piechartNam.setUsePercentValues(true);
        piechartNam.getDescription().setEnabled(false);
        piechartNam.setExtraOffsets(5,5,5,5);
        piechartNam.setDragDecelerationFrictionCoef(0.99f);

        piechartNam.setDrawHoleEnabled(false);
        piechartNam.setHoleColor(Color.WHITE);
        piechartNam.setTransparentCircleRadius(61f);

        ArrayList<PieEntry> yvalue = new ArrayList<>();
        yvalue.clear();
        if (tongGiaSachXuat.equals("null")){
            if (tongGiaSachAdd.equals("null")){
                tongGiaSachAdd = "0";
                tongGiaSachXuat = "0";
            }
        }
        yvalue.add(new PieEntry(Float.parseFloat(tongGiaSachAdd),"Tổng Nhập"));
        yvalue.add(new PieEntry(Float.parseFloat(tongGiaSachXuat),"Tổng Xuất"));

        piechartNam.animateY(1000, Easing.EasingOption.EaseInOutCubic);

        PieDataSet dataSet = new PieDataSet(yvalue," ");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        PieData data = new PieData(dataSet);
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.BLUE);

        piechartNam.setData(data);
    }

    @Override
    public void onResume() {
        super.onResume();
        getDataSach();
        getDataHoaDon();
    }
}
