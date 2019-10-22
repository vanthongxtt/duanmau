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
import vn.sefviapp.asm_ps09105.View.Activity.ChiTietHoaDonAddActivity;

public class HoaDonChiTietAdapter extends RecyclerView.Adapter<HoaDonChiTietAdapter.MyViewHolder> {
    ArrayList<HoaDonChiTiet> hoaDonChiTietArrayList;
    private List<HoaDonChiTiet> hoaDonList;
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    View v;
    int tongAll = 0;
    public HoaDonChiTietAdapter(Context context,List<HoaDonChiTiet> hoaDons) {
        this.hoaDonList = hoaDons;
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtCustomTenSachHoaDonChiTiet, txtCustomSoLuongHoaDonChiTiet, txtCustomGiaBanHoaDonChiTiet, txtCustomTongHoaDonChiTiet;
        ImageView imgCustomDeleteHoaDonChiTiet;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtCustomTenSachHoaDonChiTiet = itemView.findViewById(R.id.txtCustomTenSachHoaDonChiTiet);
            txtCustomSoLuongHoaDonChiTiet = itemView.findViewById(R.id.txtCustomSoLuongHoaDonChiTiet);
            txtCustomGiaBanHoaDonChiTiet = itemView.findViewById(R.id.txtCustomGiaBanHoaDonChiTiet);
            imgCustomDeleteHoaDonChiTiet = itemView.findViewById(R.id.imgCustomDeleteHoaDonChiTiet);
            txtCustomTongHoaDonChiTiet = itemView.findViewById(R.id.txtCustomTongHoaDonChiTiet);

        }
    }
    @Override
    public HoaDonChiTietAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = mLayoutInflater.inflate(R.layout.custom_item_chi_tiet_hoa_don,parent,false);
        v = item;
        return new HoaDonChiTietAdapter.MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(HoaDonChiTietAdapter.MyViewHolder holder, final int position) {
        final HoaDonChiTiet hoaDonChiTiet = hoaDonList.get(position);

        holder.txtCustomTenSachHoaDonChiTiet.setText(hoaDonChiTiet.getTenSach());
        holder.txtCustomSoLuongHoaDonChiTiet.setText(hoaDonChiTiet.getSoLuong() + " quyển");
        holder.txtCustomGiaBanHoaDonChiTiet.setText(hoaDonChiTiet.getGiaSach() + " VND");
        holder.imgCustomDeleteHoaDonChiTiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChiTietHoaDonAddActivity.deleteHoaDonChiTiet(hoaDonChiTiet, mContext, v);
            }
        });
        int sl = Integer.parseInt(hoaDonChiTiet.getSoLuong());
        int gia = Integer.parseInt(hoaDonChiTiet.getGiaSach());
        int tong = gia*sl;
        holder.txtCustomTongHoaDonChiTiet.setText(tong + " VND");
        tongAll = tongAll + tong;
        ChiTietHoaDonAddActivity.tongGia(tongAll, mContext);

        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.slide_left);
        holder.itemView.startAnimation(animation);

    }

    @Override
    public int getItemCount() {
        return hoaDonList.size();
    }
}
