package vn.sefviapp.asm_ps09105.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import vn.sefviapp.asm_ps09105.Config.Constant;
import vn.sefviapp.asm_ps09105.Model.HoaDonChiTiet;
import vn.sefviapp.asm_ps09105.R;
import vn.sefviapp.asm_ps09105.View.Activity.ChiTietHoaDonAddActivity;

public class TopBanChayAdapter extends RecyclerView.Adapter<TopBanChayAdapter.MyViewHolder> {
    ArrayList<HoaDonChiTiet> hoaDonChiTietArrayList;
    private List<HoaDonChiTiet> hoaDonList;
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    View v;

    public TopBanChayAdapter(Context context,List<HoaDonChiTiet> hoaDons) {
        this.hoaDonList = hoaDons;
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtCustomTenSachBanChay, txtCustomSoLuongSachBanChay;
        ImageView imgBookItemBanChay;
        public MyViewHolder(View itemView) {
            super(itemView);
            txtCustomTenSachBanChay = itemView.findViewById(R.id.txtCustomTenSachBanChay);
            txtCustomSoLuongSachBanChay = itemView.findViewById(R.id.txtCustomSoLuongSachBanChay);
            imgBookItemBanChay = itemView.findViewById(R.id.imgBookItemBanChay);
        }
    }
    @Override
    public TopBanChayAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = mLayoutInflater.inflate(R.layout.custom_top_sach_ban_chay,parent,false);
        v = item;

        return new TopBanChayAdapter.MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(TopBanChayAdapter.MyViewHolder holder, final int position) {
        final HoaDonChiTiet hoaDonChiTiet = hoaDonList.get(position);

        holder.txtCustomTenSachBanChay.setText(hoaDonChiTiet.getTenSach());
        holder.txtCustomSoLuongSachBanChay.setText(hoaDonChiTiet.getSoLuong() + " quyển");
        Picasso.get().load(Constant.URL_BASE_UPLOAD + hoaDonChiTiet.getImage()).error(R.drawable.img_sach).into(holder.imgBookItemBanChay);

        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.slide_left);
        holder.itemView.startAnimation(animation);

    }

    @Override
    public int getItemCount() {
        return hoaDonList.size();
    }
}
