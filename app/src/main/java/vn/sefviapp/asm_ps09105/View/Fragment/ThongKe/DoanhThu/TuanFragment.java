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

import vn.sefviapp.asm_ps09105.Api.ThongKeApi.ThongKeTuanApi.GetThongKeTuanHoaDonApilml;
import vn.sefviapp.asm_ps09105.Api.ThongKeApi.ThongKeTuanApi.GetThongKeTuanSachApilml;
import vn.sefviapp.asm_ps09105.Interface.ThongKeListener;
import vn.sefviapp.asm_ps09105.R;


public class TuanFragment extends Fragment {
    TextView txtSachAddTuan, txtSachXuatTuan;
    PieChart piechartTuan;
    String tongGiaSachAdd;
    String tongGiaSachXuat;
    Calendar calendar = Calendar.getInstance();
    String date;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_tuan, container, false);
        initControls(v);
        initEvents();
        return v;
    }

    private void initEvents() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                txtSachAddTuan.setText("Tổng Nhập: " + tongGiaSachAdd);
                txtSachXuatTuan.setText("Tổng Xuất: " + tongGiaSachXuat);
                try {
                    pieChart();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        },2000);
    }

    private void initControls(View v) {
        txtSachAddTuan = v.findViewById(R.id.txtSachAddTuan);
        txtSachXuatTuan = v.findViewById(R.id.txtSachXuatTuan);
        piechartTuan = v.findViewById(R.id.piechartTuan);

        int thisDay = calendar.get(Calendar.DAY_OF_MONTH);
        int thisMonth = calendar.get(Calendar.MONTH);
        int thisYear = calendar.get(Calendar.YEAR);
        int Month = thisMonth + 1;
        date = thisYear + "-" + Month + "-" + thisDay;
        getDataSach();
        getDataHoaDon();
    }

    private void getDataSach(){
        GetThongKeTuanSachApilml getThongKeTuanSachApilml = new GetThongKeTuanSachApilml();
        getThongKeTuanSachApilml.GetThongKeTuan(date, new ThongKeListener() {
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
        GetThongKeTuanHoaDonApilml getThongKeTuanHoaDonApilml = new GetThongKeTuanHoaDonApilml();
        getThongKeTuanHoaDonApilml.GetThongKeTuanHoaDon(date, new ThongKeListener() {
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
        piechartTuan.setUsePercentValues(true);
        piechartTuan.getDescription().setEnabled(false);
        piechartTuan.setExtraOffsets(5,5,5,5);
        piechartTuan.setDragDecelerationFrictionCoef(0.99f);

        piechartTuan.setDrawHoleEnabled(false);
        piechartTuan.setHoleColor(Color.WHITE);
        piechartTuan.setTransparentCircleRadius(61f);

        ArrayList<PieEntry> yvalue = new ArrayList<>();
        yvalue.clear();
        yvalue.add(new PieEntry(Float.parseFloat(tongGiaSachAdd),"Tổng Nhập"));
        yvalue.add(new PieEntry(Float.parseFloat(tongGiaSachXuat),"Tổng Xuất"));

        piechartTuan.animateY(1000, Easing.EasingOption.EaseInOutCubic);

        PieDataSet dataSet = new PieDataSet(yvalue," ");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        PieData data = new PieData(dataSet);
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.BLUE);

        piechartTuan.setData(data);
    }
    @Override
    public void onResume() {
        super.onResume();
        getDataSach();
        getDataHoaDon();
    }
}
