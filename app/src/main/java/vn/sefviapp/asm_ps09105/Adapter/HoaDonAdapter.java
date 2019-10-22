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

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.sefviapp.asm_ps09105.Model.HoaDon;
import vn.sefviapp.asm_ps09105.R;
import vn.sefviapp.asm_ps09105.View.Activity.ChiTietHoaDonActivity;
import vn.sefviapp.asm_ps09105.View.Fragment.HoaDonFragment;

public class HoaDonAdapter extends RecyclerView.Adapter<HoaDonAdapter.MyViewHolder>{
    ArrayList<HoaDon> hoaDonArrayList;
    private List<HoaDon> hoaDonList;
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    View v;
    public HoaDonAdapter(Context context,List<HoaDon> hoaDons) {
        this.hoaDonList = hoaDons;
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtNameTenHoaDon, txtDateHoaDon;
        ImageView  imgDeleteHoaDon;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtNameTenHoaDon = itemView.findViewById(R.id.txtCustomMaHoadon);
            txtDateHoaDon = itemView.findViewById(R.id.txtCustomNgayMuaHoaDon);
            imgDeleteHoaDon = itemView.findViewById(R.id.imgCustomDeleteHoaDon);
        }
    }
    @Override
    public HoaDonAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = mLayoutInflater.inflate(R.layout.item_hoadon,parent,false);
        v = item;
        return new HoaDonAdapter.MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(HoaDonAdapter.MyViewHolder holder, final int position) {
        final HoaDon hoaDon = hoaDonList.get(position);
        holder.txtNameTenHoaDon.setText(hoaDon.getName());
        holder.txtDateHoaDon.setText(hoaDon.getDate());
        holder.imgDeleteHoaDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HoaDonFragment.deleteHoaDon(mContext, v, hoaDon, position);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, ChiTietHoaDonActivity.class);
                i.putExtra("idHoaDon", hoaDon.getId());
                mContext.startActivity(i);
            }
        });
        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.slide_left);
        holder.itemView.startAnimation(animation);

    }

    @Override
    public int getItemCount() {
        return hoaDonList.size();
    }
}
