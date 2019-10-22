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

import java.util.ArrayList;
import java.util.List;

import vn.sefviapp.asm_ps09105.Model.HoaDonChiTiet;
import vn.sefviapp.asm_ps09105.R;

public class ChiTietHoaDonAdapter extends RecyclerView.Adapter<ChiTietHoaDonAdapter.MyViewHolder>  {
    ArrayList<HoaDonChiTiet> hoaDonChiTietArrayList;
    private List<HoaDonChiTiet> hoaDonList;
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    View v;

    public ChiTietHoaDonAdapter(Context context,List<HoaDonChiTiet> hoaDons) {
        this.hoaDonList = hoaDons;
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtCustomTenSachChiTietHoaDon, txtCustomSoLuongChiTietHoaDon, txtCustomGiaBanChiTietHoaDon, txtCustomTongChiTietHoaDon;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtCustomTenSachChiTietHoaDon = itemView.findViewById(R.id.txtCustomTenSachChiTietHoaDon);
            txtCustomSoLuongChiTietHoaDon = itemView.findViewById(R.id.txtCustomSoLuongChiTietHoaDon);
            txtCustomGiaBanChiTietHoaDon = itemView.findViewById(R.id.txtCustomGiaBanChiTietHoaDon);
            txtCustomTongChiTietHoaDon = itemView.findViewById(R.id.txtCustomTongChiTietHoaDon);
        }
    }
    @Override
    public ChiTietHoaDonAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = mLayoutInflater.inflate(R.layout.custom_hoa_don_chi_tiet,parent,false);
        v = item;
        return new ChiTietHoaDonAdapter.MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(ChiTietHoaDonAdapter.MyViewHolder holder, final int position) {
        final HoaDonChiTiet hoaDonChiTiet = hoaDonList.get(position);

        holder.txtCustomTenSachChiTietHoaDon.setText(hoaDonChiTiet.getTenSach());
        holder.txtCustomSoLuongChiTietHoaDon.setText(hoaDonChiTiet.getSoLuong() + " quyển");
        holder.txtCustomGiaBanChiTietHoaDon.setText(hoaDonChiTiet.getGiaSach() + " VND");

        int sl = Integer.parseInt(hoaDonChiTiet.getSoLuong());
        int gia = Integer.parseInt(hoaDonChiTiet.getGiaSach());
        int tong = gia*sl;
        holder.txtCustomTongChiTietHoaDon.setText(tong + " VND");

        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.slide_left);
        holder.itemView.startAnimation(animation);

    }

    @Override
    public int getItemCount() {
        return hoaDonList.size();
    }
}
