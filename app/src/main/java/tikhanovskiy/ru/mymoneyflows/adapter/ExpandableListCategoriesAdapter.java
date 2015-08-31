package tikhanovskiy.ru.mymoneyflows.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

public class ExpandableListCategoriesAdapter extends SimpleExpandableListAdapter {

    List<Integer> colors; // Список цветов
    Resources res;

    public ExpandableListCategoriesAdapter(Context context, List<? extends Map<String, ?>> categoryData,
                                           int categoryLayout, String[] categoryFrom,
                                           int[] categoryTo,
                                           List<? extends List<? extends Map<String, ?>>> subCategoryData,
                                           int subCategoryLayout,
                                           String[] subCategoryFrom,
                                           int[] subCategoryTo, List<Integer> colors) {
        super(context,
                categoryData,
                categoryLayout,
                categoryFrom, categoryTo,
                subCategoryData,
                subCategoryLayout,
                subCategoryFrom, subCategoryTo);

        this.colors = colors;
        res = context.getResources();
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view = super.getChildView(groupPosition, childPosition, isLastChild, convertView, parent);
        TextView textView = (TextView)view.findViewById(android.R.id.text1);
        textView.setTextColor(res.getColor(colors.get(groupPosition)));
        return view;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view =  super.getGroupView(groupPosition, isExpanded, convertView, parent);
        TextView textView = (TextView)view.findViewById(android.R.id.text1);
        textView.setTextColor(res.getColor(colors.get(groupPosition)));
        return view;
    }

}
