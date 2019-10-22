package vn.sefviapp.asm_ps09105.View.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import vn.sefviapp.asm_ps09105.Adapter.HoaDonChiTietAdapter;
import vn.sefviapp.asm_ps09105.Adapter.SachAdapter;
import vn.sefviapp.asm_ps09105.Adapter.SachItemAdapter;
import vn.sefviapp.asm_ps09105.Api.HoaDonChiTiet.AddHoaDonChiTietApilml;
import vn.sefviapp.asm_ps09105.Api.HoaDonChiTiet.DeleteHoaDonChiTietApi;
import vn.sefviapp.asm_ps09105.Api.HoaDonChiTiet.DeleteHoaDonChiTietApilml;
import vn.sefviapp.asm_ps09105.Api.HoaDonChiTiet.GetHoaDonChiTietApilml;
import vn.sefviapp.asm_ps09105.Api.SachApi.GetDataSearchSachApilml;
import vn.sefviapp.asm_ps09105.Api.SachApi.GetSachAllApilml;
import vn.sefviapp.asm_ps09105.Interface.HoaDonChiTietListener;
import vn.sefviapp.asm_ps09105.Interface.SachListener;
import vn.sefviapp.asm_ps09105.Model.HoaDon;
import vn.sefviapp.asm_ps09105.Model.HoaDonChiTiet;
import vn.sefviapp.asm_ps09105.Model.Sach;
import vn.sefviapp.asm_ps09105.R;
import vn.sefviapp.asm_ps09105.View.ShowSnackBar;

public class ChiTietHoaDonAddActivity extends AppCompatActivity {
    Toolbar toolbarChiTietHoaDon;
    static RecyclerView recyclerViewHoaDonChiTiet;
    FloatingActionButton fabAddHoaDonChiTiet;
    private static String idUser;
    static ListView lvShowDialogHoaDonChiTiet;
    static SachItemAdapter sachItemAdapter;
    static HoaDonChiTietAdapter hoaDonChiTietAdapter;
    static EditText edtSearchDialogHoaDonChiTiet;
    static String idSach;
    static String idHoaDon;
    static ProgressDialog progressDialog;
    static TextView txtTongHoaDonChiTiet;
    static ArrayList<HoaDonChiTiet> hoaDonChiTiets;
    Button btnThanhToanHoaDonChiTiet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_hoa_don_add);

        initControls();
        initEvents();
    }

    private void initEvents() {
        fabAddHoaDonChiTiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogAddData();
            }
        });
        btnThanhToanHoaDonChiTiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    private void initControls() {
        SharedPreferences pref = this.getSharedPreferences("Account", 0);
        idUser = pref.getString("idUser", null);
        toolbarChiTietHoaDon = findViewById(R.id.toolBarHoadonChiTiet);
        toolbarChiTietHoaDon.setTitle("Chi Tiết Hóa Đơn");
        recyclerViewHoaDonChiTiet = findViewById(R.id.recyclerViewHoaDonChiTiet);
        fabAddHoaDonChiTiet = findViewById(R.id.fabAddHoaDonChiTiet);
        txtTongHoaDonChiTiet = findViewById(R.id.txtTongHoaDonChiTiet);
        HoaDon hoaDon = (HoaDon) getIntent().getSerializableExtra("hoadon");
        idHoaDon = String.valueOf(hoaDon.getId());
        progressDialog = new ProgressDialog(this);
        hoaDonChiTiets = new ArrayList<>();
        btnThanhToanHoaDonChiTiet = findViewById(R.id.btnThanhToanHoaDonChiTiet);
        getHoaDonChiTiet(getApplicationContext());

    }
    private static void getHoaDonChiTiet(final Context c){
        GetHoaDonChiTietApilml getHoaDonChiTietApilml = new GetHoaDonChiTietApilml();
        getHoaDonChiTietApilml.GetHoaDonChiTiet(idHoaDon, new HoaDonChiTietListener() {
            @Override
            public void getDataSuccess(HoaDonChiTiet hoaDonChiTiet) {

            }

            @Override
            public void getMessageError(Exception e) {

            }

            @Override
            public void getDataArraySuccess(ArrayList<HoaDonChiTiet> hoaDonChiTietArrayList) {
                hoaDonChiTiets.addAll(hoaDonChiTietArrayList);
                hoaDonChiTietAdapter = new HoaDonChiTietAdapter(c, hoaDonChiTietArrayList);
                LinearLayoutManager layoutManager = new LinearLayoutManager(c.getApplicationContext());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerViewHoaDonChiTiet.setLayoutManager(layoutManager);
                recyclerViewHoaDonChiTiet.setItemAnimator(new DefaultItemAnimator());
                recyclerViewHoaDonChiTiet.setAdapter(hoaDonChiTietAdapter);

            }

            @Override
            public void getDataError(HoaDonChiTiet hoaDonChiTiet) {

            }
        });
    }
    public static void getSachItemClick(Sach sach, Context context){
        edtSearchDialogHoaDonChiTiet.setEnabled(false);
        lvShowDialogHoaDonChiTiet.setEnabled(false);
        idSach = String.valueOf(sach.getId());
        Toast.makeText(context, "Bạn Đã Chọn Sách: " + sach.getTenSach(), Toast.LENGTH_SHORT).show();
    }
    private void showDialogAddData() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_add_hoa_don_chi_tiet);

        edtSearchDialogHoaDonChiTiet = dialog.findViewById(R.id.edtSearchDialogHoaDonChiTiet);
        lvShowDialogHoaDonChiTiet = dialog.findViewById(R.id.lvShowDialogHoaDonChiTiet);
        final EditText edtDialogSoLuongHoaDonChiTiet = dialog.findViewById(R.id.edtDialogSoLuongHoaDonChiTiet);
        Button btnSave = dialog.findViewById(R.id.btnDialogAddHoaDonChiTietDialog);
        Button btnHuy = dialog.findViewById(R.id.btnDialogHuyAddHoaDonChiTiet);
        getSach();
        edtSearchDialogHoaDonChiTiet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(final CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().length() == 0) {
                    getSach();
                } else {
                    GetDataSearchSachApilml getDataSearchSachApilml = new GetDataSearchSachApilml();
                    getDataSearchSachApilml.GetDataSearchSach(String.valueOf(charSequence), new SachListener() {
                        @Override
                        public void getDataSachSuccess(Sach sach) {

                        }

                        @Override
                        public void getMessageError(Exception e) {

                        }

                        @Override
                        public void getDataArraySuccess(ArrayList<Sach> sachArrayList) {
                            sachItemAdapter = new SachItemAdapter(getApplicationContext(), R.layout.custom_sach_view_item, sachArrayList);
                            lvShowDialogHoaDonChiTiet.setAdapter(sachItemAdapter);
                        }
                    });

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String edtSoLuong = edtDialogSoLuongHoaDonChiTiet.getText().toString();
                if (edtSoLuong.isEmpty()){
                    Toast.makeText(ChiTietHoaDonAddActivity.this, "Không Được Bỏ Trống", Toast.LENGTH_SHORT).show();
                }else {
                    if (edtSoLuong.equalsIgnoreCase("0")){
                        Toast.makeText(ChiTietHoaDonAddActivity.this, "Số lượng không bằng 0", Toast.LENGTH_SHORT).show();
                    }else {
                        dialog.dismiss();
                        progressDialog.setIndeterminate(true);
                        progressDialog.setMessage("Vui lòng chờ...");
                        progressDialog.show();
                        AddHoaDonChiTietApilml addHoaDonChiTietApilml = new AddHoaDonChiTietApilml();
                        addHoaDonChiTietApilml.AddHoaDonChiTiet(idSach, idHoaDon, edtSoLuong, new HoaDonChiTietListener() {
                            @Override
                            public void getDataSuccess(HoaDonChiTiet hoaDonChiTiet) {
                                progressDialog.dismiss();
                                getHoaDonChiTiet(getApplicationContext());
                                Toast.makeText(ChiTietHoaDonAddActivity.this, "Thành Công", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void getMessageError(Exception e) {

                            }

                            @Override
                            public void getDataArraySuccess(ArrayList<HoaDonChiTiet> hoaDonChiTietArrayList) {

                            }

                            @Override
                            public void getDataError(HoaDonChiTiet hoaDonChiTiet) {

                            }
                        });
                    }
                }
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void getSach() {
        GetSachAllApilml getSachAllApilml = new GetSachAllApilml();
        getSachAllApilml.GetSach(idUser, new SachListener() {
            @Override
            public void getDataSachSuccess(Sach sach) {

            }

            @Override
            public void getMessageError(Exception e) {

            }

            @Override
            public void getDataArraySuccess(ArrayList<Sach> sachArrayList) {
                sachItemAdapter = new SachItemAdapter(getApplicationContext(), R.layout.custom_sach_view_item, sachArrayList);
                lvShowDialogHoaDonChiTiet.setAdapter(sachItemAdapter);
            }
        });
    }
    public static void deleteHoaDonChiTiet(final HoaDonChiTiet hoaDonChiTiet, final Context context, final View v){
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Vui lòng chờ...");
        progressDialog.show();
        DeleteHoaDonChiTietApilml deleteHoaDonChiTietApi = new DeleteHoaDonChiTietApilml();
        deleteHoaDonChiTietApi.DeleteHoaDonChiTiet(String.valueOf(hoaDonChiTiet.getId()), new HoaDonChiTietListener() {
            @Override
            public void getDataSuccess(HoaDonChiTiet hoaDonChiTiet) {
                progressDialog.dismiss();
                getHoaDonChiTiet(context);
                ShowSnackBar showSnackBar = new ShowSnackBar();
                showSnackBar.showSnackbar(v, "Xóa Thành Công");
            }

            @Override
            public void getMessageError(Exception e) {

            }

            @Override
            public void getDataArraySuccess(ArrayList<HoaDonChiTiet> hoaDonChiTietArrayList) {

            }

            @Override
            public void getDataError(HoaDonChiTiet hoaDonChiTiet) {

            }
        });
    }
    public static void tongGia(int tong, Context context){
        txtTongHoaDonChiTiet.setText(tong + " VND");
    }
}
