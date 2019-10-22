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
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;

import vn.sefviapp.asm_ps09105.Adapter.DateAdapter;
import vn.sefviapp.asm_ps09105.Api.ThongKeApi.ThongKeThangApi.GetThongKeThangHoaDonApilml;
import vn.sefviapp.asm_ps09105.Api.ThongKeApi.ThongKeThangApi.GetThongKeThangSachApilml;
import vn.sefviapp.asm_ps09105.Interface.ThongKeListener;
import vn.sefviapp.asm_ps09105.Model.Date;
import vn.sefviapp.asm_ps09105.R;


public class ThangFragment extends Fragment {
    Spinner spChonThang;
    TextView txtSachAddThang,txtSachXuatThang;
    PieChart piechartThang;
    int thangHienTai;
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
        View v = inflater.inflate(R.layout.fragment_thang, container, false);

        initControls(v);
        initEvents();

        return v;
    }

    private void initEvents() {
        spChonThang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Date date = dateArrayList.get(i);
                if (date.getId() == 0){
                    thangNamHienTai();
                    getDataSach();
                    getDataHoaDon();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            txtSachAddThang.setText("Tổng Nhập: " + tongGiaSachAdd);
                            txtSachXuatThang.setText("Tổng Xuất: " + tongGiaSachXuat);
                            try {
                                pieChart();
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    },2000);
                }else {
                    thangHienTai = date.getId();
                    namHienTai = calendar.get(Calendar.YEAR);
                    getDataSach();
                    getDataHoaDon();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            txtSachAddThang.setText("Tổng Nhập: " + tongGiaSachAdd);
                            txtSachXuatThang.setText("Tổng Xuất: " + tongGiaSachXuat);
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
        spChonThang = v.findViewById(R.id.spChonThang);
        txtSachAddThang = v.findViewById(R.id.txtSachAddThang);
        txtSachXuatThang = v.findViewById(R.id.txtSachXuatThang);
        piechartThang = v.findViewById(R.id.piechartThang);

        dateArrayList = new ArrayList<>();
        dateArrayList.add(new Date(0,"Chọn tháng"));
        for (int i = 1; i < 13; i++){
            dateArrayList.add(new Date(i,"Tháng " + i));
        }
        dateAdapter = new DateAdapter(getContext(), dateArrayList);
        spChonThang.setAdapter(dateAdapter);
        thangNamHienTai();
        getDataSach();
        getDataHoaDon();

    }
    public void thangNamHienTai(){
        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);
        thangHienTai = mMonth+1;
        namHienTai = mYear;
    }
    private void getDataSach(){
        GetThongKeThangSachApilml getThongKeThangSachApilml = new GetThongKeThangSachApilml();
        getThongKeThangSachApilml.GetThongKeThangSach(String.valueOf(thangHienTai), String.valueOf(namHienTai), new ThongKeListener() {
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
    private void getDataHoaDon(){
        GetThongKeThangHoaDonApilml getThongKeThangHoaDonApilml = new GetThongKeThangHoaDonApilml();
        getThongKeThangHoaDonApilml.GetThongKeThangHoaDon(String.valueOf(thangHienTai), String.valueOf(namHienTai), new ThongKeListener() {
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
    private void pieChart(){
        piechartThang.setUsePercentValues(true);
        piechartThang.getDescription().setEnabled(false);
        piechartThang.setExtraOffsets(5,5,5,5);
        piechartThang.setDragDecelerationFrictionCoef(0.99f);

        piechartThang.setDrawHoleEnabled(false);
        piechartThang.setHoleColor(Color.WHITE);
        piechartThang.setTransparentCircleRadius(61f);

        ArrayList<PieEntry> yvalue = new ArrayList<>();
        yvalue.clear();
//        Toast.makeText(getContext(), "" + tongGiaSachAdd.toString(), Toast.LENGTH_SHORT).show();
        if (tongGiaSachXuat.equals("null")){
           if (tongGiaSachAdd.equals("null")){
               tongGiaSachAdd = "0";
               tongGiaSachXuat = "0";
           }
        }
        yvalue.add(new PieEntry(Float.parseFloat(tongGiaSachAdd),"Tổng Nhập"));
        yvalue.add(new PieEntry(Float.parseFloat(tongGiaSachXuat),"Tổng Xuất"));

        piechartThang.animateY(1000, Easing.EasingOption.EaseInOutCubic);

        PieDataSet dataSet = new PieDataSet(yvalue," ");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        PieData data = new PieData(dataSet);
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.BLUE);

        piechartThang.setData(data);
    }
    @Override
    public void onResume() {
        super.onResume();
        getDataSach();
        getDataHoaDon();
    }
}
