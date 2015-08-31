package tikhanovskiy.ru.mymoneyflows.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.List;

import tikhanovskiy.ru.mymoneyflows.R;
import tikhanovskiy.ru.mymoneyflows.database.LogoCash;


/**
 * Created by m.tihanovsky on 25.08.2015.
 */
public class ListLogoCashSpinnerAdapter extends ArrayAdapter<LogoCash> {

    Resources res;

    public ListLogoCashSpinnerAdapter(Context context, List<LogoCash> logos){
        super(context, R.layout.icon_item, logos);
        res = context.getResources();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LogoCash logo = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.icon_item, null);
        }

        ImageView imageView = (ImageView)(convertView.findViewById(R.id.imageView));
        imageView.setImageResource(logo.getResourceId());
        imageView.setTag(logo.getResourceId());

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        LogoCash logo = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.icon_item, null);
        }

        ImageView imageView = (ImageView)(convertView.findViewById(R.id.imageView));
        imageView.setImageResource(logo.getResourceId());
        imageView.setTag(logo.getResourceId());

        return convertView;
    }
}
