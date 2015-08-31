package tikhanovskiy.ru.mymoneyflows.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.raizlabs.android.dbflow.sql.builder.Condition;
import com.raizlabs.android.dbflow.sql.language.Select;

import java.util.ArrayList;
import java.util.List;

import tikhanovskiy.ru.mymoneyflows.R;
import tikhanovskiy.ru.mymoneyflows.adapter.ListColorAdapter;
import tikhanovskiy.ru.mymoneyflows.adapter.ListLogoCategorySpinnerAdapter;
import tikhanovskiy.ru.mymoneyflows.database.Category;
import tikhanovskiy.ru.mymoneyflows.database.CategoryExpense;
import tikhanovskiy.ru.mymoneyflows.database.CategoryExpense$Table;
import tikhanovskiy.ru.mymoneyflows.database.CategoryProfit;
import tikhanovskiy.ru.mymoneyflows.database.CategoryProfit$Table;
import tikhanovskiy.ru.mymoneyflows.database.Color;
import tikhanovskiy.ru.mymoneyflows.database.Color$Table;
import tikhanovskiy.ru.mymoneyflows.database.LogoCategory;
import tikhanovskiy.ru.mymoneyflows.database.LogoCategory$Table;
import tikhanovskiy.ru.mymoneyflows.database.SubCategory;
import tikhanovskiy.ru.mymoneyflows.database.SubCategoryExpense;
import tikhanovskiy.ru.mymoneyflows.database.SubCategoryProfit;

public class NewCategoryActivity extends Activity {

    RadioGroup radioGroupType;
    RadioGroup radioGroupTypeCategory;
    TextView textViewCategory;
    Spinner spinnerCategory;
    EditText editTextTitle;
    TextView textViewLogo;
    Spinner spinnerLogo;
    TextView textViewColor;
    Spinner spinnerColor;

    List<View> listViewsCategory; // Список элементов управления категорий
    List<View> listViewsSubCategory; // Cписок элементов управления подкатегорий

    List<CategoryExpense> listCategoryExpense;
    List<String> listNamesCategoriesExpense;
    List<CategoryProfit> listCategoryProfit;
    List<String> listNamesCategoriesProfit;
    List<Color> listColor;
    List<LogoCategory> listLogoCategory;

    boolean type; //if TRUE then Category
    boolean typeCategory ; // if TRUE then Expensive


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_category);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setHomeAsUpIndicator(android.R.drawable.ic_menu_close_clear_cancel);

        radioGroupType = ((RadioGroup) findViewById(R.id.radioGroup_type));
        type = (radioGroupType.getCheckedRadioButtonId() == R.id.radioButton_category);

        radioGroupTypeCategory = ((RadioGroup) findViewById(R.id.radioGroup_type_category));
        typeCategory = (radioGroupTypeCategory.getCheckedRadioButtonId() == R.id.radioButton_expense);

        textViewCategory = ((TextView) findViewById(R.id.textView_category));
        spinnerCategory = ((Spinner) findViewById(R.id.spinner_category));

        editTextTitle = ((EditText) findViewById(R.id.editText_title));

        textViewLogo = ((TextView) findViewById(R.id.textView_logo));
        spinnerLogo = ((Spinner) findViewById(R.id.spinner_logo));

        textViewColor = ((TextView) findViewById(R.id.textView_color));
        spinnerColor = ((Spinner) findViewById(R.id.spinner_color));

        listViewsCategory = new ArrayList<>();
        listViewsCategory.add(textViewCategory);
        listViewsCategory.add(spinnerCategory);

        listViewsSubCategory = new ArrayList<>();
        listViewsSubCategory.add(textViewLogo);
        listViewsSubCategory.add(spinnerLogo);
        listViewsSubCategory.add(textViewColor);
        listViewsSubCategory.add(spinnerColor);

        listCategoryExpense = new Select().from(CategoryExpense.class).queryList();
        listCategoryProfit = new Select().from(CategoryProfit.class).queryList();
        listColor = new Select().from(Color.class).queryList();
        listLogoCategory = new Select().from(LogoCategory.class).queryList();

        listNamesCategoriesExpense = new ArrayList<>();
        if(listCategoryExpense.size() != 0){
            for(CategoryExpense category : listCategoryExpense){
                listNamesCategoriesExpense.add(category.getName());
            }
        }

        spinnerCategory.setAdapter(new ArrayAdapter<String>(NewCategoryActivity.this, android.R.layout.simple_list_item_1, listNamesCategoriesExpense));

        listNamesCategoriesProfit = new ArrayList<>();
        if(listCategoryProfit.size() != 0){
            for(CategoryProfit category : listCategoryProfit){
                listNamesCategoriesProfit.add(category.getName());
            }
        }

        spinnerColor.setAdapter(new ListColorAdapter(this, listColor));
        spinnerLogo.setAdapter(new ListLogoCategorySpinnerAdapter(this, listLogoCategory));

        radioGroupType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButton_category:
                        type = true;
                        hideAndShowViews(listViewsCategory, listViewsSubCategory);
                        break;
                    case R.id.radioButton_subCategory:
                        type = false;
                        hideAndShowViews(listViewsSubCategory, listViewsCategory);
                        break;
                }
            }
        });

        radioGroupTypeCategory.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButton_expense:
                        typeCategory = true;
                        spinnerCategory.setAdapter(new ArrayAdapter<String>(NewCategoryActivity.this, android.R.layout.simple_list_item_1, listNamesCategoriesExpense));
                        break;
                    case R.id.radioButton_profit:
                        typeCategory = false;
                        spinnerCategory.setAdapter(new ArrayAdapter<String>(NewCategoryActivity.this, android.R.layout.simple_list_item_1, listNamesCategoriesProfit));
                        break;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_category, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if(id == android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }

        //*****************************Сохранение Категории/Подкатегории***********************
        if(id == R.id.action_save){

            // Сохранение как категории
            if(type){
                Category category;
                if(typeCategory)
                    category = new CategoryExpense();
                else
                    category = new CategoryProfit();

                String title = String.valueOf(editTextTitle.getText());

                int logoId = ((int) (((ImageView) spinnerLogo.getSelectedView().findViewById(R.id.imageView))).getTag());
                LogoCategory logo = new Select().from(LogoCategory.class).where(Condition.column(LogoCategory$Table.RESOURCEID).eq(logoId)).querySingle();

                int colorId = ((int) ((TextView) spinnerColor.getSelectedView().findViewById(android.R.id.text1)).getTag());
                Color color = new Select().from(Color.class).where(Condition.column(Color$Table.RESOURCEID).eq(colorId)).querySingle();

                // Проверка условий и сохранение
                if(title == null || title.length() == 0){
                    Toast.makeText(this, "Введите название", Toast.LENGTH_LONG).show();
                }
                else {
                    category.setName(title);
                    category.setColor(color);
                    category.setLogo(logo);
                    if(typeCategory)
                        ((CategoryExpense)category).save();
                    else
                        ((CategoryProfit)category).save();
                }
                NavUtils.navigateUpFromSameTask(this);
                return true;
            }
            // Сохранение как подкатегории
            else {
                SubCategory subCategory;
                Category category;
                String categoryString = String.valueOf(((TextView) spinnerCategory.getSelectedView().findViewById(android.R.id.text1)).getText());

                if(typeCategory){
                    subCategory = new SubCategoryExpense();
                    category = new Select().from(CategoryExpense.class).where(Condition.column(CategoryExpense$Table.NAME).eq(categoryString)).querySingle();
                }
                else {
                    subCategory = new SubCategoryProfit();
                    category = new Select().from(CategoryProfit.class).where(Condition.column(CategoryProfit$Table.NAME).eq(categoryString)).querySingle();
                }

                String title = String.valueOf(editTextTitle.getText());

                // Проверка условий и сохранение
                if(title == null || title.length() == 0){
                    Toast.makeText(this, "Введите название", Toast.LENGTH_LONG).show();
                }
                else {
                    subCategory.setName(title);
                    subCategory.setCategory(category);
                    if(typeCategory)
                        ((SubCategoryExpense)subCategory).save();
                    else
                        ((SubCategoryProfit)subCategory).save();
                }
                NavUtils.navigateUpFromSameTask(this);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Hide and show views
     * @param hide Views will be hide
     * @param show Views will be show
     */
    private void hideAndShowViews (List<View> hide, List<View> show){
        for(View view : hide){
            view.setVisibility(View.GONE);
        }
        for(View view : show){
            view.setVisibility(View.VISIBLE);
        }
    }
}
