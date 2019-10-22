package vn.sefviapp.asm_ps09105.View.Fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.OpenableColumns;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;

import vn.sefviapp.asm_ps09105.Adapter.SachAdapter;
import vn.sefviapp.asm_ps09105.Adapter.SpinnerTheLoaiAdapter;
import vn.sefviapp.asm_ps09105.Adapter.TheLoaiAdapter;
import vn.sefviapp.asm_ps09105.Api.NguoiDungApi.AddUserAllApilml;
import vn.sefviapp.asm_ps09105.Api.SachApi.AddSachApilml;
import vn.sefviapp.asm_ps09105.Api.SachApi.DeleteSachApilml;
import vn.sefviapp.asm_ps09105.Api.SachApi.EditSachApilml;
import vn.sefviapp.asm_ps09105.Api.SachApi.GetSachAllApilml;
import vn.sefviapp.asm_ps09105.Api.TheLoaiApi.GetTheLoaiApilml;
import vn.sefviapp.asm_ps09105.Api.TheLoaiApi.GetTheLoaiChiTietApilml;
import vn.sefviapp.asm_ps09105.Config.Constant;
import vn.sefviapp.asm_ps09105.Interface.GetTheLoaiListener;
import vn.sefviapp.asm_ps09105.Interface.SachListener;
import vn.sefviapp.asm_ps09105.Interface.TheLoaiListener;
import vn.sefviapp.asm_ps09105.Model.Sach;
import vn.sefviapp.asm_ps09105.Model.TheLoai;
import vn.sefviapp.asm_ps09105.R;
import vn.sefviapp.asm_ps09105.View.ShowSnackBar;

import static android.app.Activity.RESULT_OK;
import static android.util.Base64.DEFAULT;


public class SachFragment extends Fragment {

    Toolbar toolbar;
    FloatingActionButton fabAddBook;
    //    ArrayList<TheLoai> theLoaiArrayList;
//    ArrayList<Sach> sachArrayList;
    static SachAdapter sachAdapter;
    static RecyclerView recyclerViewSach;
    static ImageView imgAddBook;
    static int idTheLoai;
    private static String idUser;
    static int REQUEST_CODE_CAMERA = 123;
    static int REQUEST_CODE_FOLDER = 456;
    private static Uri filePath;
    ProgressDialog progressDialog;
    Dialog dialogAddSach;
    Calendar calendar = Calendar.getInstance();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sach, container, false);



        initControls(v);
        initEvents();
        return v;
    }

    private void initEvents() {
        fabAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAddSach();
            }
        });

    }

    @SuppressLint("NewApi")
    private void initControls(View v) {
        toolbar = v.findViewById(R.id.toolbarSach);
        fabAddBook = v.findViewById(R.id.fabAddSach);
        recyclerViewSach = v.findViewById(R.id.recyclerViewSach);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        toolbar.setTitle("Sách");
        progressDialog = new ProgressDialog(getActivity());
        SharedPreferences pref = getActivity().getSharedPreferences("Account", 0);
        idUser = pref.getString("idUser", null);

//        sachArrayList = new ArrayList<>();

        getDataSach(getContext());

    }

    private static void getDataSach(final Context context) {
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
                sachAdapter = new SachAdapter(context, sachArrayList);
                LinearLayoutManager layoutManager = new LinearLayoutManager(context.getApplicationContext());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerViewSach.setHasFixedSize(true);
                recyclerViewSach.setLayoutManager(layoutManager);
                recyclerViewSach.setItemAnimator(new DefaultItemAnimator());
                recyclerViewSach.setAdapter(sachAdapter);
            }
        });
    }

    private void dialogAddSach() {
        dialogAddSach = new Dialog(getActivity());
        dialogAddSach.setContentView(R.layout.dialog_add_book);

        Spinner spinnerTheLoai = dialogAddSach.findViewById(R.id.spTheLoai);
        imgAddBook = dialogAddSach.findViewById(R.id.imgAddSach);
        final EditText edtTenSach = dialogAddSach.findViewById(R.id.edtTenSach);
        final EditText edtTacGia = dialogAddSach.findViewById(R.id.edtTacGia);
        final EditText edtNXB = dialogAddSach.findViewById(R.id.edtNXB);
        final EditText edtGiaBan = dialogAddSach.findViewById(R.id.edtGiaBan);
        final EditText edtSoLuong = dialogAddSach.findViewById(R.id.edtSoLuong);
        Button btnDialogAddSach = dialogAddSach.findViewById(R.id.btnDialogAddSach);
        Button btnDialogHuySach = dialogAddSach.findViewById(R.id.btnDialogHuySach);

        addItemsToSpinnerTheLoai(spinnerTheLoai, getContext(), 0, "0");

        imgAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_FOLDER);
            }
        });


        btnDialogAddSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String tenSach = edtTenSach.getText().toString();
                final String tacGia = edtTacGia.getText().toString();
                final String nxb = edtNXB.getText().toString();
                final String giaBan = edtGiaBan.getText().toString();
                final String soLuong = edtSoLuong.getText().toString();
                if (isChecks(tenSach, tacGia, nxb, giaBan, soLuong)) {
                    progressDialog.setIndeterminate(true);
                    progressDialog.setMessage("Vui lòng chờ...");
                    progressDialog.show();
                    saveSach(tenSach, tacGia, nxb, giaBan, soLuong);
                } else {
                    Toast.makeText(getActivity(), "Không được bỏ trống", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDialogHuySach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAddSach.dismiss();
            }
        });
        dialogAddSach.show();
    }

    private static void addItemsToSpinnerTheLoai(final Spinner spChonTheLoai, final Context context, final int isEdit, final String idTheLoaiedit) {
        GetTheLoaiApilml getTheLoaiApilml = new GetTheLoaiApilml();
        getTheLoaiApilml.GetTheLoai(idUser, new GetTheLoaiListener() {
            @Override
            public void GetDataTheLoaiSuccess(final ArrayList<TheLoai> theLoais) {
                SpinnerTheLoaiAdapter spinAdapter = new SpinnerTheLoaiAdapter(context, theLoais);
                spChonTheLoai.setAdapter(spinAdapter);
                spChonTheLoai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> adapter, View v,
                                               int position, long id) {
                        idTheLoai = theLoais.get(position).getId();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                    }
                });
            }

            @Override
            public void GetDataTheLoaiError(Exception e) {

            }
        });
    }

    private boolean isChecks(String tenSach, String tacGia, String nXB, String giaBan, String soLuong) {
        return !tenSach.isEmpty() && !tacGia.isEmpty() && !nXB.isEmpty() && !soLuong.isEmpty() && !giaBan.isEmpty();
    }

    public String getFileName(Uri uri, Context context) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK && data != null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgAddBook.setImageBitmap(bitmap);

        }
        if (requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK && data != null) {
            filePath = data.getData();
            try {
                InputStream inputStream = getContext().getContentResolver().openInputStream(filePath);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgAddBook.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void saveSach(String tenSach, String tacGia, String nxb, String giaBan, String soLuong ) {
        try {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) imgAddBook.getDrawable();
            Bitmap bitmapLuu = bitmapDrawable.getBitmap();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmapLuu.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
            byte[] hinh = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
            String encodedImage = Base64.encodeToString(hinh, DEFAULT);
            int thisDay = calendar.get(Calendar.DAY_OF_MONTH);
            int thisMonth = calendar.get(Calendar.MONTH);
            int thisYear = calendar.get(Calendar.YEAR);
            int Month = thisMonth + 1;
            String date = thisYear + "-" + Month + "-" + thisDay;
            AddSachApilml addSachApilml = new AddSachApilml();
            addSachApilml.AddSach(tenSach, tacGia, nxb, giaBan, soLuong, idUser, getFileName(filePath, getContext()), encodedImage, String.valueOf(idTheLoai), date, new SachListener() {
                @Override
                public void getDataSachSuccess(Sach sach) {
                    progressDialog.dismiss();
                    dialogAddSach.dismiss();
                    getDataSach(getContext());
                    ShowSnackBar showSnackBar = new ShowSnackBar();
                    showSnackBar.showSnackbar(getView(), "Thêm Sách Thành Công");
                }

                @Override
                public void getMessageError(Exception e) {

                }

                @Override
                public void getDataArraySuccess(ArrayList<Sach> sachArrayList) {

                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void showDialogInfosach(Context context, Sach sach) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_xem_sach);
        TextView txtTenSach = dialog.findViewById(R.id.txtDialogTenSach);
        ImageView imgSach = dialog.findViewById(R.id.imgDialogXemAnhSach);
        TextView txtTacGia = dialog.findViewById(R.id.txtDialogTacGia);
        TextView txtNxb = dialog.findViewById(R.id.txtDialogNhaXuatBan);
        TextView txtGiaBan = dialog.findViewById(R.id.txtDialogGiaBan);
        TextView txtSoLuong = dialog.findViewById(R.id.txtDialogSoLuong);
        final TextView txtTheLoai = dialog.findViewById(R.id.txtDialogTheLoai);
        Button btnClose = dialog.findViewById(R.id.btnDialogCloseSachInfo);

        Picasso.get().load(Constant.URL_BASE_UPLOAD + sach.getImage()).error(R.drawable.img_sach).into(imgSach);
        txtTenSach.setText(sach.getTenSach());
        txtNxb.setText(sach.getNxb());
        txtTacGia.setText(sach.getTacGia());
        txtGiaBan.setText(sach.getGiaBan() + " VND");
        txtSoLuong.setText(sach.getSoLuong() + " quyển");

        GetTheLoaiChiTietApilml getTheLoaiChiTietApilml = new GetTheLoaiChiTietApilml();
        getTheLoaiChiTietApilml.GetTheLoaiChiTiet(idUser, sach.getIdTheLoai(), new TheLoaiListener() {
            @Override
            public void getDataTheLoaiSuccess(TheLoai theLoai) {
                txtTheLoai.setText(theLoai.getTenTheLoai());
            }

            @Override
            public void getMessageError(Exception e) {

            }

            @Override
            public void getDataSuccess(String thanhcong) {

            }
        });

        txtTheLoai.setText(sach.getIdTheLoai());


        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public static void showDialogXoaSach(final Context context, final Sach sach, final View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Bạn có muốn xóa " + sach.getTenSach());
        builder.setCancelable(false);
        builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final ProgressDialog progressDialog = new ProgressDialog(context);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Vui lòng chờ...");
                progressDialog.show();
                DeleteSachApilml deleteSachApilml = new DeleteSachApilml();
                deleteSachApilml.DeleteSach(String.valueOf(sach.getId()), new SachListener() {
                    @Override
                    public void getDataSachSuccess(Sach sach) {
                        getDataSach(context);
                        progressDialog.dismiss();
                        ShowSnackBar showSnackBar = new ShowSnackBar();
                        showSnackBar.showSnackbar(view, "Xóa sách thành công");
                    }

                    @Override
                    public void getMessageError(Exception e) {

                    }

                    @Override
                    public void getDataArraySuccess(ArrayList<Sach> sachArrayList) {

                    }
                });

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        getDataSach(getContext());
    }
}
