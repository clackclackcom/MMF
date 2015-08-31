package tikhanovskiy.ru.mymoneyflows.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.raizlabs.android.dbflow.sql.builder.Condition;
import com.raizlabs.android.dbflow.sql.language.Select;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import tikhanovskiy.ru.mymoneyflows.R;
import tikhanovskiy.ru.mymoneyflows.database.Cash;
import tikhanovskiy.ru.mymoneyflows.database.Cash$Table;
import tikhanovskiy.ru.mymoneyflows.database.Expense;
import tikhanovskiy.ru.mymoneyflows.database.Operation;
import tikhanovskiy.ru.mymoneyflows.database.Profit;
import tikhanovskiy.ru.mymoneyflows.database.SubCategory;
import tikhanovskiy.ru.mymoneyflows.database.SubCategoryExpense;
import tikhanovskiy.ru.mymoneyflows.database.SubCategoryExpense$Table;
import tikhanovskiy.ru.mymoneyflows.database.SubCategoryProfit;
import tikhanovskiy.ru.mymoneyflows.database.SubCategoryProfit$Table;


public class NewOperationActivity extends Activity {


    boolean typeCategory ; // if TRUE then Expensive

    RadioGroup radioGroupType;
    Spinner spinnerCash;
    Spinner spinnerSubCategory;
    EditText editTextSumma;
    EditText editTextComment;

    List<SubCategoryExpense> listSubCategoryExpense;
    List<SubCategoryProfit> listSubCategoryProfits;
    List<Cash> listCashs;

    List<String> listNamesSubCategoriesExpense; // Для адаптера
    List<String> listNamesSubCategoriesProfit; // Для адаптера
    List<String> listNamesCash; // Для адаптера

    ArrayAdapter<String> adapterExpense; // Адаптер расхода
    ArrayAdapter<String> adapterProfit;  // Адаптер дохода


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_operation);

        radioGroupType = ((RadioGroup) findViewById(R.id.radioGroup_type_operation));
        typeCategory = (radioGroupType.getCheckedRadioButtonId() == R.id.radioButton_expense);

        spinnerCash = ((Spinner) findViewById(R.id.spinner_cash));
        spinnerSubCategory = ((Spinner) findViewById(R.id.spinner_category));
        editTextSumma = ((EditText) findViewById(R.id.editText_sum));
        editTextComment = ((EditText) findViewById(R.id.editText_comment));

        listSubCategoryExpense = new Select().from(SubCategoryExpense.class).queryList();
        listNamesSubCategoriesExpense = new ArrayList<>();
        if(listSubCategoryExpense.size() != 0){
            for(SubCategoryExpense subCategoryExpense : listSubCategoryExpense){
                listNamesSubCategoriesExpense.add(subCategoryExpense.getName());
            }
        }

        listSubCategoryProfits = new Select().from(SubCategoryProfit.class).queryList();
        listNamesSubCategoriesProfit = new ArrayList<>();
        if(listSubCategoryProfits.size() != 0){
            for(SubCategoryProfit subCategoryProfit : listSubCategoryProfits){
                listNamesSubCategoriesProfit.add(subCategoryProfit.getName());
            }
        }

        listCashs = new Select().from(Cash.class).queryList();
        listNamesCash = new ArrayList<>();
        if(listCashs.size() != 0){
            for(Cash cash : listCashs){
                listNamesCash.add(cash.getName());
            }
        }

        adapterExpense = new ArrayAdapter<String>(
                NewOperationActivity.this,
                android.R.layout.simple_list_item_1,
                listNamesSubCategoriesExpense);

        adapterProfit = new ArrayAdapter<String>(
                NewOperationActivity.this,
                android.R.layout.simple_list_item_1,
                listNamesSubCategoriesProfit);

        spinnerSubCategory.setAdapter(adapterExpense);

        spinnerCash.setAdapter(
                new ArrayAdapter<String>(NewOperationActivity.this,
                android.R.layout.simple_list_item_1,
                listNamesCash));

        radioGroupType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButton_expense:
                        typeCategory = true;
                        spinnerSubCategory.setAdapter(adapterExpense);
                        break;
                    case R.id.radioButton_profit:
                        typeCategory = false;
                        spinnerSubCategory.setAdapter(adapterProfit);
                        break;
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_operation, menu);
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

        // ***************************** Сохранение операции ************************************
        if(id == R.id.action_save){


            Operation operation;
            if(typeCategory)
                operation = new Expense();
            else
                operation = new Profit();

            // Получаем категорию
            String stringSubCategory = String.valueOf(((((TextView) spinnerSubCategory.getSelectedView().findViewById(android.R.id.text1))).getText()));
            SubCategory subCategory;
            if(typeCategory)
                subCategory = new Select()
                        .from(SubCategoryExpense.class)
                        .where(Condition.column(SubCategoryExpense$Table.NAME)
                        .eq(stringSubCategory))
                        .querySingle();
            else
                subCategory = new Select()
                        .from(SubCategoryProfit.class)
                        .where(Condition.column(SubCategoryProfit$Table.NAME)
                        .eq(stringSubCategory))
                        .querySingle();

            // Получаем кошелек
            String stringCash = String.valueOf(((((TextView) spinnerCash.getSelectedView().findViewById(android.R.id.text1))).getText()));
            Cash cash;
            cash = new Select()
                    .from(Cash.class)
                    .where(Condition.column(Cash$Table.NAME)
                    .eq(stringCash))
                    .querySingle();

            // Получаем стоимость
            String summaString = String.valueOf(editTextSumma.getText());
            float amount;
            if(summaString.length() == 0)
                amount = 0;
            else
                amount = Float.parseFloat(String.valueOf(summaString));

            // Получаем комментарий
            String comment = String.valueOf(editTextComment.getText());
            if(summaString.length() == 0)
                comment = "";

            // Сохраняем операцию
            operation.setCash(cash);
            operation.setDate(new Date(System.currentTimeMillis()));
            operation.setSubCategory(subCategory);
            operation.setAmount(amount);
            operation.setComment(comment);
            if(typeCategory){
                ((Expense)operation).save();
                cash.setAmount(cash.getAmount() - amount);
                cash.save();
            }
            else {
                ((Profit)operation).save();
                cash.setAmount(cash.getAmount() + amount);
                cash.save();
            }

            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
