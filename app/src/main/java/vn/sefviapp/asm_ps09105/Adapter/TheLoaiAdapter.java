package vn.sefviapp.asm_ps09105.Adapter;

import android.content.Context;
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

import vn.sefviapp.asm_ps09105.Model.Sach;
import vn.sefviapp.asm_ps09105.Model.TheLoai;
import vn.sefviapp.asm_ps09105.R;
import vn.sefviapp.asm_ps09105.View.Fragment.TheLoaiFragment;

public class TheLoaiAdapter  extends RecyclerView.Adapter<TheLoaiAdapter.MyViewHolder>{
    ArrayList<TheLoai> theLoaiArrayList;
    private List<TheLoai> theLoais;
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    View v;
    public TheLoaiAdapter(Context context,List<TheLoai> theLoais) {
        this.theLoais = theLoais;
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtNameTenTheLoai;
        ImageView imgEdtTheLoai, imgDeleteTheLoai;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtNameTenTheLoai = itemView.findViewById(R.id.txtCustomTenTheLoai);
            imgDeleteTheLoai = itemView.findViewById(R.id.imgCustomDeleteTheLoai);
            imgEdtTheLoai = itemView.findViewById(R.id.imgCustomEditTheLoai);
        }
    }
    @Override
    public TheLoaiAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = mLayoutInflater.inflate(R.layout.custom_the_loai,parent,false);
        v = item;
        return new TheLoaiAdapter.MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(TheLoaiAdapter.MyViewHolder holder, int position) {
        final TheLoai theLoai = theLoais.get(position);
        holder.txtNameTenTheLoai.setText(theLoai.getTenTheLoai());
        holder.imgDeleteTheLoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    TheLoaiFragment.deleteTheLoai(mContext,theLoai.getId(), v);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        holder.imgEdtTheLoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    TheLoaiFragment.editTheLoai(mContext, theLoai, v);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    TheLoaiFragment.chiTietTheLoai(mContext, theLoai);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.slide_left);
        holder.itemView.startAnimation(animation);

    }

    @Override
    public int getItemCount() {
        return theLoais.size();
    }
}
