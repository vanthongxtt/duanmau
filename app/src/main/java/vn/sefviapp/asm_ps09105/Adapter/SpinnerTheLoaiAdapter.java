package vn.sefviapp.asm_ps09105.Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import vn.sefviapp.asm_ps09105.Model.TheLoai;
import vn.sefviapp.asm_ps09105.R;

public class SpinnerTheLoaiAdapter extends ArrayAdapter<TheLoai> {
    private Context context;
    private ArrayList<TheLoai> data;
    public Resources res;
    private LayoutInflater inflater;

    public SpinnerTheLoaiAdapter(Context context, ArrayList<TheLoai> objects) {
        super(context, R.layout.sp_the_loai, objects);

        this.context = context;
        data = objects;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {

        View row = inflater.inflate(R.layout.sp_the_loai, parent, false);
        TextView txtShowTheLoai = (TextView) row.findViewById(R.id.txtShowTheLoai);

        txtShowTheLoai.setText(data.get(position).getTenTheLoai());

        return row;
    }
}
