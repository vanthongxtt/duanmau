package vn.sefviapp.asm_ps09105.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import vn.sefviapp.asm_ps09105.Config.Constant;
import vn.sefviapp.asm_ps09105.Model.Sach;
import vn.sefviapp.asm_ps09105.R;
import vn.sefviapp.asm_ps09105.View.Activity.ChiTietHoaDonAddActivity;
import vn.sefviapp.asm_ps09105.View.Activity.EditSachActivity;
import vn.sefviapp.asm_ps09105.View.Fragment.SachFragment;

public class SachItemAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Sach> saches;
    public SachItemAdapter(Context context, int layout, List<Sach> saches) {
        this.context = context;
        this.layout = layout;
        this.saches = saches;
    }

    @Override
    public int getCount() {
        return saches.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder{
        TextView txtSach;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SachItemAdapter.ViewHolder holder;
        if (convertView == null) {
            holder = new SachItemAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            holder.txtSach = (TextView) convertView.findViewById(R.id.txtCustomTenSach);

            convertView.setTag(holder);
        } else {
            holder = (SachItemAdapter.ViewHolder) convertView.getTag();
        }

        final Sach s = saches.get(position);
        holder.txtSach.setText(s.getTenSach());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChiTietHoaDonAddActivity.getSachItemClick(s, context);
            }
        });
        return convertView;

    }
}
