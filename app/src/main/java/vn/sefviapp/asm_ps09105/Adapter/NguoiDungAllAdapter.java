package vn.sefviapp.asm_ps09105.Adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import vn.sefviapp.asm_ps09105.Config.Constant;
import vn.sefviapp.asm_ps09105.Model.Account;

import vn.sefviapp.asm_ps09105.R;
import vn.sefviapp.asm_ps09105.View.Fragment.DanhSachNguoiDungFragment;

public class NguoiDungAllAdapter extends RecyclerView.Adapter<NguoiDungAllAdapter.MyViewHolder> {
    private List<Account> accounts;
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    View v;
    public NguoiDungAllAdapter(Context context,List<Account> accounts) {
        this.accounts = accounts;
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = mLayoutInflater.inflate(R.layout.custom_nguoi_dung_list,parent,false);
        v = item;
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(NguoiDungAllAdapter.MyViewHolder holder, int position) {
        final Account account = accounts.get(position);
        holder.txtNameNguoiDung.setText(account.getName());
        holder.txtEmailNguoiDung.setText(account.getEmail());

        Picasso.get().load(Constant.URL_BASE_UPLOAD + account.getImage()).error(R.drawable.img_sach).into(holder.imgUser);
        holder.imgXoaNguoiDung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DanhSachNguoiDungFragment.deleteUser(account.getId(), mContext, v);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DanhSachNguoiDungFragment.itemClickUser(account, mContext, v);
            }
        });

        // hiệu ứng
        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.slide_left);
        holder.itemView.startAnimation(animation);
    }

    @Override
    public int getItemCount() {
        return accounts.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgUser, imgXoaNguoiDung;
        TextView txtNameNguoiDung, txtEmailNguoiDung;
        public MyViewHolder(View itemView) {
            super(itemView);
            txtNameNguoiDung = itemView.findViewById(R.id.txtCustomtenNguoiDung);
            txtEmailNguoiDung = itemView.findViewById(R.id.txtCustomEmailNguoiDung);
            imgUser = itemView.findViewById(R.id.imgUserItem);
            imgXoaNguoiDung = itemView.findViewById(R.id.imgCustomDeleteNguoiDungAll);
        }
    }
}
