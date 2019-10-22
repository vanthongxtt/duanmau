package vn.sefviapp.asm_ps09105.View.Fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import vn.sefviapp.asm_ps09105.Adapter.SachAdapter;
import vn.sefviapp.asm_ps09105.Adapter.SpinnerTheLoaiAdapter;
import vn.sefviapp.asm_ps09105.Adapter.TheLoaiAdapter;
import vn.sefviapp.asm_ps09105.Api.TheLoaiApi.AddTheLoaiApilml;
import vn.sefviapp.asm_ps09105.Api.TheLoaiApi.DeleteTheLoaiApilml;
import vn.sefviapp.asm_ps09105.Api.TheLoaiApi.EditTheLoaiApilml;
import vn.sefviapp.asm_ps09105.Api.TheLoaiApi.GetTheLoaiApilml;
import vn.sefviapp.asm_ps09105.Api.TheLoaiApi.GetTheLoaiChiTietApi;
import vn.sefviapp.asm_ps09105.Api.TheLoaiApi.GetTheLoaiChiTietApilml;
import vn.sefviapp.asm_ps09105.Interface.GetTheLoaiListener;
import vn.sefviapp.asm_ps09105.Interface.TheLoaiListener;
import vn.sefviapp.asm_ps09105.Model.Sach;
import vn.sefviapp.asm_ps09105.Model.TheLoai;
import vn.sefviapp.asm_ps09105.R;
import vn.sefviapp.asm_ps09105.View.ShowSnackBar;

public class TheLoaiFragment extends Fragment {

    private static String idUser;
    FloatingActionButton fabAddTheLoai;
    static TheLoaiAdapter theLoaiAdapter;
    static RecyclerView recyclerViewTheLoai;
    AddTheLoaiApilml addTheLoaiApilml;
    ProgressDialog progressDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_the_loai, container, false);
        initControls(v);
        initEvents();
        return v;
    }

    private void initEvents() {
        fabAddTheLoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogShowAddTheLoai();
            }
        });
    }

    private void dialogShowAddTheLoai() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_add_theloai);
        final EditText edtNameTheLoai = dialog.findViewById(R.id.edtTenTheLoai);
        final EditText edtMoTaTheLoai = dialog.findViewById(R.id.edtMoTaTheLoai);
        Button btnThemTheLoai = dialog.findViewById(R.id.btnDialogAddTheLoai);
        Button btnHuyTheLoai = dialog.findViewById(R.id.btnDialogHuyTheLoai);
        btnThemTheLoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameTheLoai = edtNameTheLoai.getText().toString();
                String moTa = edtMoTaTheLoai.getText().toString();
                if (isTheLoai(nameTheLoai,moTa)){
                    progressDialog = new ProgressDialog(getContext());
                    progressDialog.setIndeterminate(true);
                    progressDialog.setMessage("Vui lòng chờ...");
                    progressDialog.show();
                    addTheLoaiApilml.AddTheLoai(nameTheLoai, moTa, "1", new TheLoaiListener() {
                        @Override
                        public void getDataTheLoaiSuccess(TheLoai theLoai) {
                            getDataTheLoai(getContext());
                            ShowSnackBar showSnackBar = new ShowSnackBar();
                            showSnackBar.showSnackbar(getView(), "Thêm Thành Công");
                            progressDialog.dismiss();
                            dialog.dismiss();
                        }
                        @Override
                        public void getMessageError(Exception e) {
                            ShowSnackBar showSnackBar = new ShowSnackBar();
                            showSnackBar.showSnackbar(getView(), "Thêm Thất Bại");
                            progressDialog.dismiss();
                            dialog.dismiss();
                        }

                        @Override
                        public void getDataSuccess(String thanhcong) {

                        }
                    });
                }else {
                    ShowSnackBar showSnackBar = new ShowSnackBar();
                    showSnackBar.showSnackbar(getView(), "Không Được Bỏ Trống");
                }

            }
        });
        btnHuyTheLoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void initControls(View v) {
        fabAddTheLoai = v.findViewById(R.id.fabAddTheLoai);
        recyclerViewTheLoai = v.findViewById(R.id.recyclerViewTheLoai);

        addTheLoaiApilml = new AddTheLoaiApilml();

        SharedPreferences pref = getActivity().getSharedPreferences("Account", 0);
        idUser = pref.getString("idUser", null);
        getDataTheLoai(getContext());


    }
    private static boolean isTheLoai(String nameTheLoai, String moTa){

        if (nameTheLoai.isEmpty() || moTa.isEmpty()){
            return false;
        }
        return true;
    }
    private static void getDataTheLoai(final Context context){
        GetTheLoaiApilml getTheLoaiApilml = new GetTheLoaiApilml();
        getTheLoaiApilml.GetTheLoai(idUser, new GetTheLoaiListener() {
            @Override
            public void GetDataTheLoaiSuccess(ArrayList<TheLoai> theLoais) {
                theLoaiAdapter = new TheLoaiAdapter(context,theLoais);
                LinearLayoutManager layoutManager = new LinearLayoutManager(context.getApplicationContext());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerViewTheLoai.setLayoutManager(layoutManager);
                recyclerViewTheLoai.setHasFixedSize(true);
                recyclerViewTheLoai.setItemAnimator(new DefaultItemAnimator());
                recyclerViewTheLoai.setAdapter(theLoaiAdapter);
            }

            @Override
            public void GetDataTheLoaiError(Exception e) {

            }
        });
    }
    public static void chiTietTheLoai(final Context context, TheLoai theLoai){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_ch_tiet_the_loai);
        final TextView txtTen = dialog.findViewById(R.id.txtTenDialog);
        final TextView txtMoTa = dialog.findViewById(R.id.txtMoTaDialog);
        Button btnDong = dialog.findViewById(R.id.btnDialogDongChiTiet);
        txtTen.setText(theLoai.getTenTheLoai());
        txtMoTa.setText(theLoai.getMoTaTheLoai());
        btnDong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public static void deleteTheLoai(final Context context, int id, final View view){
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Vui lòng chờ...");
        progressDialog.show();
        DeleteTheLoaiApilml deleteTheLoaiApilml = new DeleteTheLoaiApilml();
        deleteTheLoaiApilml.DeleteTheLoai(String.valueOf(id), idUser, new TheLoaiListener() {
            @Override
            public void getDataTheLoaiSuccess(TheLoai theLoai) {

            }
            @Override
            public void getMessageError(Exception e) {
            }
            @Override
            public void getDataSuccess(String thanhcong) {
                getDataTheLoai(context);
                progressDialog.dismiss();
                ShowSnackBar showSnackBar = new ShowSnackBar();
                showSnackBar.showSnackbar(view, thanhcong);
            }
        });
    }
    public static void editTheLoai(final Context context, final TheLoai theLoai, final View v){
        final Dialog dialog  = new Dialog(context);
        dialog.setContentView(R.layout.dialog_add_theloai);
        TextView txtTitle = dialog.findViewById(R.id.dialogTheLoaiTitle);
        final EditText edtTenTheLoai = (EditText) dialog.findViewById(R.id.edtTenTheLoai);
        final EditText edtMoTaTheLoai = (EditText) dialog.findViewById(R.id.edtMoTaTheLoai);
        Button btnSua = dialog.findViewById(R.id.btnDialogAddTheLoai);
        Button btnHuy = dialog.findViewById(R.id.btnDialogHuyTheLoai);

        btnSua.setText("Cập nhật");
        txtTitle.setText("Sửa Thể Loại");

        edtTenTheLoai.setText(theLoai.getTenTheLoai());
        edtMoTaTheLoai.setText(theLoai.getMoTaTheLoai());

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                String ten = edtTenTheLoai.getText().toString();
                String moTa = edtMoTaTheLoai.getText().toString();
                if (isTheLoai(ten, moTa)){

                    EditTheLoaiApilml editTheLoaiApilml = new EditTheLoaiApilml();
                    editTheLoaiApilml.EditTheLoai(idUser, theLoai.getId() + "", ten, moTa, new TheLoaiListener() {
                        @Override
                        public void getDataTheLoaiSuccess(TheLoai theLoai) {
                            ShowSnackBar showSnackBar = new ShowSnackBar();
                            showSnackBar.showSnackbar(v, "Cập Nhật Thành Công");
                            dialog.dismiss();
                            getDataTheLoai(context);
                        }

                        @Override
                        public void getMessageError(Exception e) {

                        }

                        @Override
                        public void getDataSuccess(String thanhcong) {
                        }
                    });
                }else {
                    ShowSnackBar showSnackBar = new ShowSnackBar();
                    showSnackBar.showSnackbar(v, "Không Được Bỏ Trống");
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
}
