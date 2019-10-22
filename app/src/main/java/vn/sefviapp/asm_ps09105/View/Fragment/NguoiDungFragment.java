package vn.sefviapp.asm_ps09105.View.Fragment;

import android.app.Dialog;
import android.app.DownloadManager;
import android.app.FragmentManager;
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
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.OpenableColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Base64;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


import vn.sefviapp.asm_ps09105.Api.NguoiDungApi.DoiMatKhauUserApilml;
import vn.sefviapp.asm_ps09105.Api.NguoiDungApi.EditNguoiDungApilml;
import vn.sefviapp.asm_ps09105.Api.NguoiDungApi.GetNguoiDungApilml;
import vn.sefviapp.asm_ps09105.Api.NguoiDungApi.UploadImageNguoiDungApilml;
import vn.sefviapp.asm_ps09105.Config.Constant;
import vn.sefviapp.asm_ps09105.Interface.LoginListener;
import vn.sefviapp.asm_ps09105.Model.Account;
import vn.sefviapp.asm_ps09105.R;
import vn.sefviapp.asm_ps09105.View.Activity.LoginActivity;
import vn.sefviapp.asm_ps09105.View.Activity.MainActivity;
import vn.sefviapp.asm_ps09105.View.ShowSnackBar;

import static android.app.Activity.RESULT_OK;
import static android.util.Base64.DEFAULT;


public class NguoiDungFragment extends Fragment {
    Button btnEditNguoiDung, btnQuanLyNguoiDung, btnDoiMatKhau, btnDangXuat, btnLuu;
    TextView txtNameNguoiDung;
    ImageView imageInfoNguoiDung, imgXemAnh;
    Dialog dialogImg;
    private String idUser;
    private String nameInfo;
    private String emailInfo;
    private String phoneInfo;
    private String diaChiInfo;
    private String image;
    private String pass;
    int REQUEST_CODE_CAMERA = 123;
    int REQUEST_CODE_FOLDER = 456;
    private Uri filePath;
    ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_nguoi_dung, container, false);
        initControls(v);
        initEvents();
        return v;
    }

    private void initEvents() {
        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialogDangXuat();
            }
        });
        btnDoiMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogDoiMatKhau();
            }
        });
        btnEditNguoiDung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogSuaThongTin();
            }
        });
        imageInfoNguoiDung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogXemAndEditImage();
            }
        });
        btnQuanLyNguoiDung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new DanhSachNguoiDungFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    private void initControls(View v) {
        btnEditNguoiDung = v.findViewById(R.id.btnEditThongTinUser);
        btnQuanLyNguoiDung = v.findViewById(R.id.btnQuanLyNguoiDung);
        btnDoiMatKhau = v.findViewById(R.id.btnDoMatKhau);
        btnDangXuat = v.findViewById(R.id.btnDangXuat);
        txtNameNguoiDung = v.findViewById(R.id.txtNameNguoiDung);
        imageInfoNguoiDung = v.findViewById(R.id.imageInfoNguoiDung);

        progressDialog = new ProgressDialog(getActivity());
        SharedPreferences pref = getActivity().getSharedPreferences("Account", 0);
        idUser = pref.getString("idUser", null);
        getDataNameNguoiDung();
    }
    private void showDialogXemAndEditImage(){
        dialogImg = new Dialog(getActivity());
        dialogImg.setContentView(R.layout.dialog_xem_anh);
        ImageView imgEdit = dialogImg.findViewById(R.id.editImageShowDialog);
        imgXemAnh = dialogImg.findViewById(R.id.imgDialogXemAnh);
        btnLuu = dialogImg.findViewById(R.id.btnDialogImageSave);
        TextView txtTitle = dialogImg.findViewById(R.id.txtTitleXemAnhDialog);
        Button btnClose = dialogImg.findViewById(R.id.btnDialogCloseImage);

        txtTitle.setText(nameInfo);
        Picasso.get().load(Constant.URL_BASE_UPLOAD + image).error(R.drawable.img_sach).into(imgXemAnh);

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogHoiLuu();
            }
        });
        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_FOLDER);
            }
        });
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogImg.dismiss();
            }
        });
        dialogImg.show();
    }
    private void showDialogSuaThongTin(){

        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_sua_thong_tin_nguoi_dung);
        final EditText editEmail = dialog.findViewById(R.id.edtEmailNguoiDungDialog);
        final EditText editName = dialog.findViewById(R.id.edtNameNguoiDungDialog);
        final EditText editPhone = dialog.findViewById(R.id.edtSoDienThoaiNguoiDungDialog);
        final EditText editDiaChi = dialog.findViewById(R.id.edtDiaChiNguoiDungDialog);

        editEmail.setText(emailInfo);
        editName.setText(nameInfo);
        editPhone.setText(phoneInfo);
        editDiaChi.setText(diaChiInfo);

        Button btnCapNhat = dialog.findViewById(R.id.btnDialogCapNhatInfoNguoiDung);
        Button btnHuy = dialog.findViewById(R.id.btnDialogThoaiInfoNguoiDung);

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editEmail.getText().toString();
                String name = editName.getText().toString();
                String phone = editPhone.getText().toString();
                String diachi = editDiaChi.getText().toString();

                if (!(email.isEmpty() && name.isEmpty() && phone.isEmpty() && diachi.isEmpty())){
                    editInfoNguoiDung(idUser, name, email, phone, diachi);
                    getDataNameNguoiDung();
                    dialog.dismiss();
                }else {
                    ShowSnackBar showSnackBar = new ShowSnackBar();
                    showSnackBar.showSnackbar(getView(), "Không được bỏ trống");
                }
            }
        });

        dialog.show();
    }
    private void getDataNameNguoiDung(){
        GetNguoiDungApilml getNguoiDungApilml = new GetNguoiDungApilml();
        getNguoiDungApilml.GetNguoiDung(idUser, new LoginListener() {
            @Override
            public void getDataSuccess(Account account) {
                txtNameNguoiDung.setText(account.getName());
                nameInfo = account.getName();
                emailInfo = account.getEmail();
                phoneInfo = account.getPhone();
                diaChiInfo = account.getDiachi();
                image = account.getImage();
                pass = account.getPassword();
                Picasso.get().load(Constant.URL_BASE_UPLOAD + image).error(R.drawable.img_sach).into(imageInfoNguoiDung);

            }
            @Override
            public void getMessageError(Exception e) {

            }

            @Override
            public void getDataArraySuccess(ArrayList<Account> accounts) {

            }

            @Override
            public void getDataError(Account account) {

            }
        });
    }
    private void showAlertDialogDangXuat(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Bạn có muốn đăng xuất không?");
        builder.setCancelable(false);
        builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getContext(), "Không đăng xuất", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                getActivity().startActivity(intent);
                getActivity().finish();

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
    private void showDialogDoiMatKhau(){
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_doi_mat_khau);

        final EditText editMkCu = dialog.findViewById(R.id.edtMatKhauCu);
        final EditText editMkMoi = dialog.findViewById(R.id.edtMatKhauMoi);
        final EditText editNlMkMoi = dialog.findViewById(R.id.edtNhapLaiMatKhauMoi);

        Button btnDoi = dialog.findViewById(R.id.btnDialogDoiMatKhau);
        Button btnHuy = dialog.findViewById(R.id.btnDialogThoaiDoiMatKhau);

        btnDoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String passCu = editMkCu.getText().toString();
                String passMoi = editMkMoi.getText().toString();
                String passNlMoi = editNlMkMoi.getText().toString();

                if (isPassword(passCu, passMoi, passNlMoi)){
                    dialog.dismiss();
                    progressDialog.setIndeterminate(true);
                    progressDialog.setMessage("Vui lòng chờ...");
                    progressDialog.show();
                    DoiMatKhauUserApilml doiMatKhauUserApilml = new DoiMatKhauUserApilml();
                    doiMatKhauUserApilml.DoiMatKhauApi(idUser, passMoi, new LoginListener() {
                        @Override
                        public void getDataSuccess(Account account) {
                            progressDialog.dismiss();
                            ShowSnackBar showSnackBar = new ShowSnackBar();
                            showSnackBar.showSnackbar(getView(), "Đổi mật khẩu thành công");
                        }

                        @Override
                        public void getMessageError(Exception e) {

                        }

                        @Override
                        public void getDataArraySuccess(ArrayList<Account> accounts) {

                        }

                        @Override
                        public void getDataError(Account account) {

                        }
                    });
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
    private void editInfoNguoiDung(String id, String name, String email, String phone, String diachi){
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Vui lòng chờ...");
        progressDialog.show();
        EditNguoiDungApilml editNguoiDungApilml = new EditNguoiDungApilml();
        editNguoiDungApilml.EditNguoiDung(id, name, email, phone, diachi, new LoginListener() {
            @Override
            public void getDataSuccess(Account account) {
                ShowSnackBar showSnackBar = new ShowSnackBar();
                showSnackBar.showSnackbar(getView(), "Cập Nhật Thành Công");
                progressDialog.dismiss();
                getDataNameNguoiDung();
            }

            @Override
            public void getMessageError(Exception e) {

            }

            @Override
            public void getDataArraySuccess(ArrayList<Account> accounts) {

            }

            @Override
            public void getDataError(Account account) {

            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK && data != null){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgXemAnh.setImageBitmap(bitmap);
            btnLuu.setEnabled(true);
        }
        if (requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK && data != null){
            filePath = data.getData();
            try{
                InputStream inputStream = getContext().getContentResolver().openInputStream(filePath);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgXemAnh.setImageBitmap(bitmap);
                btnLuu.setEnabled(true);
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void LuuAnh(){
        try {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) imgXemAnh.getDrawable();
            Bitmap bitmapLuu = bitmapDrawable.getBitmap();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmapLuu.compress(Bitmap.CompressFormat.JPEG, 100 , byteArrayOutputStream);
            byte[] hinh = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
            String encodedImage = Base64.encodeToString(hinh, DEFAULT);

            UploadImageNguoiDungApilml uploadImageNguoiDungApilml = new UploadImageNguoiDungApilml();
            uploadImageNguoiDungApilml.UploadImageNguoiDung(encodedImage, getFileName(filePath), idUser, new LoginListener() {
                @Override
                public void getDataSuccess(Account account) {
                    progressDialog.dismiss();
                    getDataNameNguoiDung();
                    ShowSnackBar showSnackBar = new ShowSnackBar();
                    showSnackBar.showSnackbar(getView(), "Cập nhật ảnh thành công");
                }

                @Override
                public void getMessageError(Exception e) {

                }

                @Override
                public void getDataArraySuccess(ArrayList<Account> accounts) {

                }

                @Override
                public void getDataError(Account account) {

                }
            });


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void dialogHoiLuu(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Bạn có muốn Lưu");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialogImg.dismiss();
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Vui lòng chờ...");
                progressDialog.show();
                LuuAnh();
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }
    String getFileName(Uri uri){
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContext().getContentResolver().query(uri, null, null, null, null);
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
    private boolean isPassword(String passCu, String passMoi, String passNlMoi){

        if (passCu.isEmpty() || passMoi.isEmpty() || passNlMoi.isEmpty()){
            Toast.makeText(getActivity(), "Không được bỏ trống", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!passMoi.equals(passNlMoi)){
            Toast.makeText(getActivity(), "Mật khẩu mới không trùng khớp", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!passCu.equals(pass)){
            Toast.makeText(getActivity(), "Mật khẩu cũ chưa chính xác", Toast.LENGTH_SHORT).show();
            return false;
        }
        return  true;
    }
}
