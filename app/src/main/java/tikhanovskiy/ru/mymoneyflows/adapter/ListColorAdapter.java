package tikhanovskiy.ru.mymoneyflows.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import tikhanovskiy.ru.mymoneyflows.database.Color;


public class ListColorAdapter extends ArrayAdapter<Color> {

    Resources res;

    public ListColorAdapter(Context context, List<Color> colors){
        super(context, android.R.layout.simple_list_item_1, colors);
        res = context.getResources();
}

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Color color = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(android.R.layout.simple_spinner_item, null);
        }

        convertView.findViewById(android.R.id.text1)
                .setBackgroundColor(res.getColor(color.getResourceId()));
        convertView.setTag(color.getResourceId());

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        Color color = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(android.R.layout.simple_spinner_dropdown_item, null);
        }

        convertView.findViewById(android.R.id.text1)
                .setBackgroundColor(res.getColor(color.getResourceId()));

        return convertView;
    }
}
