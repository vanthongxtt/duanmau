package vn.sefviapp.asm_ps09105.Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import vn.sefviapp.asm_ps09105.Model.Date;
import vn.sefviapp.asm_ps09105.R;

public class DateAdapter extends ArrayAdapter<Date> {
    private Context context;
    private ArrayList<Date> data;
    public Resources res;
    private LayoutInflater inflater;

    public DateAdapter(Context context, ArrayList<Date> objects) {
        super(context, R.layout.custom_date_item, objects);

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

        View row = inflater.inflate(R.layout.custom_date_item, parent, false);
        Date date = data.get(position);
        TextView txtCustomSp = (TextView) row.findViewById(R.id.txtCustomSpDate);
        txtCustomSp.setText(date.getDate());

        return row;
    }
}
