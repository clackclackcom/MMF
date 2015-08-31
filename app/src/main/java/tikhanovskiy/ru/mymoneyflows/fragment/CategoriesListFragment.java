package tikhanovskiy.ru.mymoneyflows.fragment;


import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.RadioGroup;

import com.melnykov.fab.FloatingActionButton;
import com.raizlabs.android.dbflow.sql.language.Select;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tikhanovskiy.ru.mymoneyflows.R;
import tikhanovskiy.ru.mymoneyflows.activity.MainActivity;
import tikhanovskiy.ru.mymoneyflows.activity.NewCashActivity;
import tikhanovskiy.ru.mymoneyflows.activity.NewCategoryActivity;
import tikhanovskiy.ru.mymoneyflows.adapter.ExpandableListCategoriesAdapter;
import tikhanovskiy.ru.mymoneyflows.database.Category;
import tikhanovskiy.ru.mymoneyflows.database.CategoryExpense;
import tikhanovskiy.ru.mymoneyflows.database.CategoryProfit;
import tikhanovskiy.ru.mymoneyflows.database.SubCategory;


public class CategoriesListFragment extends Fragment {

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";


    final String ATTRIBUTE_NAME_LOGO = "logo";
    final String ATTRIBUTE_NAME_TITLE = "title";

    boolean typeCategory = true; // if TRUE then Expensive

    RadioGroup radioGroupType;
    ExpandableListView expandableListView;

    List<CategoryExpense> categoryExpenses; //Список категорий расходов
    List<CategoryProfit> categoryProfits; //Список категорий доходов

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Включаем отображение меню
        setHasOptionsMenu(true);
    }

    public static CategoriesListFragment newInstance(int sectionNumber) {
        CategoriesListFragment fragment = new CategoriesListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.fragment_list_categories, container, false);


            radioGroupType = ((RadioGroup) view.findViewById(R.id.radioGroup_type_category));
            typeCategory = (radioGroupType.getCheckedRadioButtonId() == R.id.radioButton_expense);

            expandableListView = ((ExpandableListView) view.findViewById(R.id.expandableListView));
            expandableListView.setAdapter(getAdapter(typeCategory));

            radioGroupType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {

                    ExpandableListCategoriesAdapter adapter;

                    switch (checkedId) {
                        case R.id.radioButton_expense:
                            typeCategory = true;
                            adapter = getAdapter(typeCategory);
                            expandableListView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            break;
                        case R.id.radioButton_profit:
                            typeCategory = false;
                            adapter = getAdapter(typeCategory);
                            expandableListView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            break;
                    }
                }
            });

        FloatingActionButton floatingActionButton = (FloatingActionButton)view.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), NewCategoryActivity.class));
            }
        });


        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.global, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_new_category) {
            startActivity(new Intent(getActivity(), NewCategoryActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * Get Expandable list adapter
     * @param typeCategory
     * @return
     */
    private ExpandableListCategoriesAdapter getAdapter(boolean typeCategory){

        List<Integer> colors = new ArrayList<>();
        List<? extends Category> categories;

        if(typeCategory){
            categories = new Select().from(CategoryExpense.class).queryList();
        }
        else
            categories = new Select().from(CategoryProfit.class).queryList();

        // Collection for Categories
        ArrayList<Map<String, Object>> categoryData;
        // Collection for items of the same category
        ArrayList<Map<String, Object>> subCategoryDataItem;
        // Collection subcategories
        ArrayList<ArrayList<Map<String, Object>>> subCategoryData;
        // Collection attributes
        Map<String, Object> atr;


        String[] categoryFrom = {ATTRIBUTE_NAME_TITLE};
        int[] categoryTo = {android.R.id.text1};

        String[] subCategoryFrom = {ATTRIBUTE_NAME_TITLE};
        int[] subCategoryTo = {android.R.id.text1};

        categoryData = new ArrayList<>();
        subCategoryData = new ArrayList<>();

        for(Category category : categories){
            atr = new HashMap<>();
//            atr.put(ATTRIBUTE_NAME_LOGO, category.getLogo().getResourceId());
            atr.put(ATTRIBUTE_NAME_TITLE, category.getName());
            colors.add(category.getColor().getResourceId());
            categoryData.add(atr);

            subCategoryDataItem = new ArrayList<>();

            for(SubCategory subCategory : category.getSubCategory()){
                atr = new HashMap<>();
                atr.put(ATTRIBUTE_NAME_TITLE, subCategory.getName());
                subCategoryDataItem.add(atr);
            }
            subCategoryData.add(subCategoryDataItem);
        }

        return new ExpandableListCategoriesAdapter(getActivity(),
                categoryData,
                android.R.layout.simple_expandable_list_item_1,
                categoryFrom,
                categoryTo,
                subCategoryData,
                android.R.layout.simple_expandable_list_item_1,
                subCategoryFrom,
                subCategoryTo,
                colors);
    }
}
