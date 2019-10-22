package vn.sefviapp.asm_ps09105.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import vn.sefviapp.asm_ps09105.Config.Constant;
import vn.sefviapp.asm_ps09105.Model.Sach;
import vn.sefviapp.asm_ps09105.Model.TheLoai;
import vn.sefviapp.asm_ps09105.R;
import vn.sefviapp.asm_ps09105.View.Activity.EditSachActivity;
import vn.sefviapp.asm_ps09105.View.Activity.LoginActivity;
import vn.sefviapp.asm_ps09105.View.Fragment.DanhSachNguoiDungFragment;
import vn.sefviapp.asm_ps09105.View.Fragment.SachFragment;

public class SachAdapter extends RecyclerView.Adapter<SachAdapter.MyViewHolder>{
    ArrayList<Sach> sachArrayList;
    private List<Sach> saches;
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    View v;
    public SachAdapter(Context context,List<Sach> saches) {
        this.saches = saches;
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtNameTenSach, txtSoLuongSach;
        ImageView imgEdtSach, imgDeleteSach,imgBookItem;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtNameTenSach = itemView.findViewById(R.id.txtCustomTenSach);
            txtSoLuongSach = itemView.findViewById(R.id.txtCustomSoLuongSach);
            imgEdtSach = itemView.findViewById(R.id.imgCustomEditSach);
            imgDeleteSach = itemView.findViewById(R.id.imgCustomDeleteSach);
            imgBookItem = itemView.findViewById(R.id.imgBookItem);
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = mLayoutInflater.inflate(R.layout.custom_sach,parent,false);
        v = item;
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(SachAdapter.MyViewHolder holder, int position) {
        final Sach sach = saches.get(position);
        Picasso.get().load(Constant.URL_BASE_UPLOAD + sach.getImage()).error(R.drawable.img_sach).into(holder.imgBookItem);
        holder.txtNameTenSach.setText(sach.getTenSach());
        holder.txtSoLuongSach.setText(sach.getSoLuong()+ " quyển");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SachFragment.showDialogInfosach(mContext, sach);
            }
        });
        holder.imgDeleteSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SachFragment.showDialogXoaSach(mContext, sach, v);
            }
        });
        holder.imgEdtSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, EditSachActivity.class);
                intent.putExtra("sachEdit", sach);
                mContext.startActivity(intent);
            }
        });
        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.slide_left);
        holder.itemView.startAnimation(animation);
    }

    @Override
    public int getItemCount() {
        return saches.size();
    }

}
