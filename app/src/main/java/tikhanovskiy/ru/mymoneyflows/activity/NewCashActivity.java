package tikhanovskiy.ru.mymoneyflows.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;
import com.raizlabs.android.dbflow.sql.builder.Condition;
import com.raizlabs.android.dbflow.sql.language.Select;

import java.util.List;

import tikhanovskiy.ru.mymoneyflows.R;
import tikhanovskiy.ru.mymoneyflows.adapter.ListCurrencyAdapter;
import tikhanovskiy.ru.mymoneyflows.adapter.ListLogoCashSpinnerAdapter;
import tikhanovskiy.ru.mymoneyflows.database.Cash;
import tikhanovskiy.ru.mymoneyflows.database.Currency;
import tikhanovskiy.ru.mymoneyflows.database.Currency$Table;
import tikhanovskiy.ru.mymoneyflows.database.LogoCash;
import tikhanovskiy.ru.mymoneyflows.database.LogoCash$Table;


public class NewCashActivity extends Activity {

    Spinner spinnerLogo;
    Spinner spinnerCurrency;
    EditText editTextTitle;
    EditText editTextAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_cash);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setHomeAsUpIndicator(android.R.drawable.ic_menu_close_clear_cancel);

        spinnerLogo = (Spinner)findViewById(R.id.spinner_logos_cash);
        spinnerCurrency = (Spinner)findViewById(R.id.spinner_currency);
        editTextTitle = (EditText)findViewById(R.id.editText_title);
        editTextAmount = (EditText)findViewById(R.id.editText_amount);

        List<LogoCash> logoCashList = new Select().from(LogoCash.class).queryList();
        List<Currency> currencyList = new Select().from(Currency.class).queryList();

        spinnerLogo.setAdapter(new ListLogoCashSpinnerAdapter(this, logoCashList));
        spinnerCurrency.setAdapter(new ListCurrencyAdapter(this, currencyList));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_cash, menu);
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

        //**************************** Сохранение счета *****************************//

        if(id == R.id.action_save){

            // Получение Logo
            int logoId = (int) ((ImageView)(spinnerLogo.getSelectedView().findViewById(R.id.imageView))).getTag();

            LogoCash logoCash = new Select().from(LogoCash.class).where(Condition.column(LogoCash$Table.RESOURCEID).eq(logoId)).querySingle();

            // Получение Currency
            String currencyString = String.valueOf(((TextView) spinnerCurrency.getSelectedView().findViewById(android.R.id.text1)).getText());
            Currency currency = new Select().from(Currency.class).where(Condition.column(Currency$Table.SHORTHAND).eq(currencyString)).querySingle();

            // Получение Названия
            String title = String.valueOf(editTextTitle.getText());

            // Получение Средств
            String amountString = String.valueOf(editTextAmount.getText());
            float amount;
            if(amountString.length() == 0)
                amount = 0;
            else
             amount = Float.parseFloat(String.valueOf(editTextAmount.getText()));

            // Проверка условий и сохранение
            if(title == null || title.length() == 0){
                Toast.makeText(this, "Введите название", Toast.LENGTH_LONG).show();
            }
            else {
                Cash cash = new Cash();
                cash.setLogo(logoCash);
                cash.setCurrency(currency);
                cash.setName(title);
                cash.setAmount(amount);
                cash.save();
                NavUtils.navigateUpFromSameTask(this);
            }

            // Возвращаемся назад после сохранения
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
