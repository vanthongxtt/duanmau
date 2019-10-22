package vn.sefviapp.asm_ps09105.View.Fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import vn.sefviapp.asm_ps09105.Adapter.HoaDonAdapter;
import vn.sefviapp.asm_ps09105.Api.HoaDonApi.AddHoaDonApilml;
import vn.sefviapp.asm_ps09105.Api.HoaDonApi.DeleteHoaDonApilml;
import vn.sefviapp.asm_ps09105.Api.HoaDonApi.GetHoaDonApilml;
import vn.sefviapp.asm_ps09105.Interface.HoaDonListener;
import vn.sefviapp.asm_ps09105.Model.HoaDon;
import vn.sefviapp.asm_ps09105.R;
import vn.sefviapp.asm_ps09105.View.Activity.ChiTietHoaDonAddActivity;
import vn.sefviapp.asm_ps09105.View.ShowSnackBar;


public class HoaDonFragment extends Fragment {

    static RecyclerView recyclerViewHoaDon;
    FloatingActionButton fabAddHoaDon;

    private String idUser;
    static HoaDonAdapter hoaDonAdapter;
    static ProgressDialog progressDialog;
    static ShowSnackBar showSnackBar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_hoa_don, container, false);

        initControls(v);
        initEvents();

        return v;
    }

    private void initEvents() {
        fabAddHoaDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDialogAddHoaHon();
            }
        });
    }

    private void initControls(View v) {
        recyclerViewHoaDon = v.findViewById(R.id.recyclerViewHoaDon);
        fabAddHoaDon = v.findViewById(R.id.fabAddHoaDon);

        SharedPreferences pref = getActivity().getSharedPreferences("Account", 0);
        idUser = pref.getString("idUser", null);

        progressDialog = new ProgressDialog(getContext());
        showSnackBar = new ShowSnackBar();

        getHoaDonData("0", getContext());

    }

    private void ShowDialogAddHoaHon() {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_add_hoa_don);
        final EditText editTenHoaDon = dialog.findViewById(R.id.edtTenHoaDonDialog);
        final EditText edtNgayMuaHoaDon = dialog.findViewById(R.id.edtNgayMuaHoaDonDialog);
        Button btnAddHoaDon = dialog.findViewById(R.id.btnDialogAddHoaDonDialog);
        Button btnHuyHoaDon = dialog.findViewById(R.id.btnDialogHuyAddHoaDon);

        edtNgayMuaHoaDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chonNgay(edtNgayMuaHoaDon);
            }
        });

        btnAddHoaDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenHoaDon = editTenHoaDon.getText().toString();
                String ngayMua = edtNgayMuaHoaDon.getText().toString();
                if (!tenHoaDon.isEmpty() || !ngayMua.isEmpty()) {

                    dialog.dismiss();
                    progressDialog.setIndeterminate(true);
                    progressDialog.setMessage("Vui lòng chờ...");
                    progressDialog.show();
                    addHoaDon(tenHoaDon,ngayMua);

                } else {
                    Toast.makeText(getActivity(), "Không được bỏ trống", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnHuyHoaDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void chonNgay(final EditText chonNgay) {
        final Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                chonNgay.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, nam, thang, ngay);
        datePickerDialog.show();
    }

    public static void deleteHoaDon(final Context context, final View v, HoaDon hoaDon, int i) {
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Vui lòng chờ...");
        progressDialog.show();

        DeleteHoaDonApilml deleteHoaDonApilml = new DeleteHoaDonApilml();
        deleteHoaDonApilml.DeleteHoaDon(String.valueOf(hoaDon.getId()), new HoaDonListener() {
            @Override
            public void getDataSuccess(HoaDon hoaDon) {
                progressDialog.dismiss();
                getHoaDonData("0",context);
                ShowSnackBar showSnackBar = new ShowSnackBar();
                showSnackBar.showSnackbar(v, "Xóa Thành Công");
            }

            @Override
            public void getMessageError(Exception e) {

            }

            @Override
            public void getDataArraySuccess(ArrayList<HoaDon> hoaDonArrayList) {

            }

            @Override
            public void getDataError(HoaDon hoaDon) {

            }
        });
    }
    private void addHoaDon(String name, String date){
        AddHoaDonApilml addHoaDonApilml = new AddHoaDonApilml();
//        Toast.makeText(getContext(), "" + randomKey(), Toast.LENGTH_SHORT).show();
        addHoaDonApilml.AddHoaDon(name, date, randomKey(), new HoaDonListener() {
            @Override
            public void getDataSuccess(HoaDon hoaDon) {
                progressDialog.dismiss();
                getHoaDonData("0", getContext());
                Intent i = new Intent(getActivity(), ChiTietHoaDonAddActivity.class);
                i.putExtra("hoadon", hoaDon);
                startActivity(i);
            }

            @Override
            public void getMessageError(Exception e) {

            }

            @Override
            public void getDataArraySuccess(ArrayList<HoaDon> hoaDonArrayList) {

            }

            @Override
            public void getDataError(HoaDon hoaDon) {

            }
        });
    }
    private static void  getHoaDonData(String id, final Context context){
        GetHoaDonApilml getHoaDonApilml = new GetHoaDonApilml();
        getHoaDonApilml.GetHoaDon(id, new HoaDonListener() {
            @Override
            public void getDataSuccess(HoaDon hoaDon) {

            }

            @Override
            public void getMessageError(Exception e) {

            }

            @Override
            public void getDataArraySuccess(ArrayList<HoaDon> hoaDonArrayList) {
                hoaDonAdapter = new HoaDonAdapter(context, hoaDonArrayList);
                LinearLayoutManager layoutManager = new LinearLayoutManager(context.getApplicationContext());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerViewHoaDon.setHasFixedSize(true);
                recyclerViewHoaDon.setLayoutManager(layoutManager);
                recyclerViewHoaDon.setItemAnimator(new DefaultItemAnimator());
                recyclerViewHoaDon.setAdapter(hoaDonAdapter);
            }

            @Override
            public void getDataError(HoaDon hoaDon) {

            }
        });
    }
    private String randomKey(){
        char[] chars1 = "ABCDEF012GHIJKL345MNOPQR678STUVWXYZ9".toCharArray();
        StringBuilder sb1 = new StringBuilder();
        Random random1 = new Random();
        for (int i = 0; i < 10; i++)
        {
            char c1 = chars1[random1.nextInt(chars1.length)];
            sb1.append(c1);
        }
        return "SEFVI-" + sb1.toString();
    }
    @Override
    public void onResume() {
        super.onResume();
        getHoaDonData("0", getContext());
    }

}
