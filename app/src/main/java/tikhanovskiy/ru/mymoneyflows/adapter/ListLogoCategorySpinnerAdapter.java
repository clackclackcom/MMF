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
import tikhanovskiy.ru.mymoneyflows.database.LogoCategory;

/**
 * Created by Maxim Tikhanovskiy on 25.08.2015.
 */
public class ListLogoCategorySpinnerAdapter extends ArrayAdapter<LogoCategory> {

    Resources res;

    public ListLogoCategorySpinnerAdapter(Context context, List<LogoCategory> logos){
        super(context, R.layout.icon_item, logos);
        res = context.getResources();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LogoCategory logo = getItem(position);

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

        LogoCategory logo = getItem(position);

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
