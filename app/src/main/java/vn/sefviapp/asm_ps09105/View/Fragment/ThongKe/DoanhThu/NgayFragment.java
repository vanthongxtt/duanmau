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

import vn.sefviapp.asm_ps09105.Api.ThongKeApi.ThongKeNgayApi.GetThongKeNgayHoaDonApilml;
import vn.sefviapp.asm_ps09105.Api.ThongKeApi.ThongKeNgayApi.GetThongKeNgaySachApilml;
import vn.sefviapp.asm_ps09105.Interface.ThongKeListener;
import vn.sefviapp.asm_ps09105.R;

public class NgayFragment extends Fragment {
    TextView txtSachAddNgay, txtSachXuatNgay;
    PieChart pieChart;
    Calendar calendar = Calendar.getInstance();
    String tongGiaSachAdd;
    String tongGiaSachXuat;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ngay, container, false);
        initControls(v);
        initEvents();
        return v;
    }

    private void initEvents() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                txtSachAddNgay.setText("Tổng Nhập: " + tongGiaSachAdd);
                txtSachXuatNgay.setText("Tổng Xuất: " + tongGiaSachXuat);
                try {
                    pieChart();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        },2000);
    }

    private void initControls(View v) {
        txtSachAddNgay = v.findViewById(R.id.txtSachAddNgay);
        txtSachXuatNgay = v.findViewById(R.id.txtSachXuatNgay);
        pieChart = v.findViewById(R.id.piechartNgay);
        getTongSach();
        getTongHoaDon();
    }

    private void getTongSach(){
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        GetThongKeNgaySachApilml getThongKeNgaySachApilml = new GetThongKeNgaySachApilml();
        getThongKeNgaySachApilml.GetThongKeNgaySach(String.valueOf(day), new ThongKeListener() {

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
    private void getTongHoaDon(){
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        GetThongKeNgayHoaDonApilml getThongKeNgayHoaDonApilml = new GetThongKeNgayHoaDonApilml();
        getThongKeNgayHoaDonApilml.GetThongKeNgayHoaDon(String.valueOf(day), new ThongKeListener() {


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
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5,5,5,5);
        pieChart.setDragDecelerationFrictionCoef(0.99f);

        pieChart.setDrawHoleEnabled(false);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(61f);

        ArrayList<PieEntry> yvalue = new ArrayList<>();
        yvalue.clear();
        yvalue.add(new PieEntry(Float.parseFloat(tongGiaSachAdd),"Tổng Nhập"));
        yvalue.add(new PieEntry(Float.parseFloat(tongGiaSachXuat),"Tổng Xuất"));

        pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic);

        PieDataSet dataSet = new PieDataSet(yvalue," ");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        PieData data = new PieData(dataSet);
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.BLUE);

        pieChart.setData(data);
    }
    @Override
    public void onResume() {
        super.onResume();
        getTongSach();
        getTongHoaDon();
    }
}
