package tikhanovskiy.ru.mymoneyflows.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import tikhanovskiy.ru.mymoneyflows.database.Currency;

/**
 * Created by m.tihanovsky on 25.08.2015.
 */
public class ListCurrencyAdapter extends ArrayAdapter<Currency> {

    public ListCurrencyAdapter(Context context, List<Currency> currencies){
        super(context, android.R.layout.simple_list_item_2, currencies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Currency currency = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(android.R.layout.simple_list_item_2, null);
        }

        ((TextView)(convertView.findViewById(android.R.id.text1))).setText(currency.getShortHand());
        ((TextView)(convertView.findViewById(android.R.id.text2))).setText(currency.getName());

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        Currency currency = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(android.R.layout.simple_list_item_2, null);
        }

        ((TextView)(convertView.findViewById(android.R.id.text1))).setText(currency.getShortHand());
        ((TextView)(convertView.findViewById(android.R.id.text2))).setText(currency.getName());

        return convertView;
    }
}
