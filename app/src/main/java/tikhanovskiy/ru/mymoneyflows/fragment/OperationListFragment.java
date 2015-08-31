package tikhanovskiy.ru.mymoneyflows.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

import com.melnykov.fab.FloatingActionButton;
import com.raizlabs.android.dbflow.sql.language.Select;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import tikhanovskiy.ru.mymoneyflows.R;
import tikhanovskiy.ru.mymoneyflows.activity.MainActivity;
import tikhanovskiy.ru.mymoneyflows.activity.NewOperationActivity;
import tikhanovskiy.ru.mymoneyflows.database.Expense;
import tikhanovskiy.ru.mymoneyflows.database.Operation;
import tikhanovskiy.ru.mymoneyflows.database.Profit;

/**
 * Created by m.tihanovsky on 28.08.2015.
 */
public class OperationListFragment extends ListFragment {

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private int sectionNumber;


    final String ATTRIBUTE_NAME_LOGO_CATEGORY = "logoCategory";
    final String ATTRIBUTE_NAME_TITLE_CATEGORY = "titleCategory";
    final String ATTRIBUTE_NAME_AMOUNT = "amount";
    final String ATTRIBUTE_NAME_CURRENCY = "currency";
    final String ATTRIBUTE_NAME_LOGO_CASH = "logoCash";
    final String ATTRIBUTE_NAME_TITLE_CASH = "logoTitle";
    final String ATTRIBUTE_NAME_DATA = "data";
    final String ATTRIBUTE_NAME_COMMENT = "comment";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Включаем отображение меню
        setHasOptionsMenu(true);

        int itemLayout;

        List<Expense> expenses = new Select().from(Expense.class).queryList();
        List<Profit> profits = new Select().from(Profit.class).queryList();

        List<Integer> logoCategories = new ArrayList<>();
        List<String> categoryTitles = new ArrayList<>();
        List<Float> amounts = new ArrayList<>();
        List<String> currencies = new ArrayList<>();
        List<Integer> logoCashes = new ArrayList<>();
        List<String> cashTitles = new ArrayList<>();
        List<String> dates = new ArrayList<>();
        List<String> comments = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());

        List<Operation> operations = new ArrayList<>();
        if(sectionNumber == 2){
            operations.addAll(expenses);
            itemLayout = R.layout.item_operation_expense_list;
        }
        else {
            operations.addAll(profits);
            itemLayout = R.layout.item_operation_profit_list;
        }

        for(Operation operation : operations){
            logoCategories.add(operation.getSubCategory().getCategory().getLogo().getResourceId());
            categoryTitles.add(operation.getSubCategory().getName());
            amounts.add(operation.getAmount());
            currencies.add(operation.getCash().getCurrency().getShortHand());
            logoCashes.add(operation.getCash().getLogo().getResourceId());
            cashTitles.add(operation.getCash().getName());
            Date date  = operation.getDate();
            dates.add(sdf.format(date));
            comments.add(operation.getComment());
        }

        ArrayList<Map<String,Object>> data = new ArrayList<>(operations.size());
        Map<String, Object> attr;

        for(int i = 0; i < operations.size(); i++){
            attr = new HashMap<>();
            attr.put(ATTRIBUTE_NAME_LOGO_CATEGORY, logoCategories.get(i));
            attr.put(ATTRIBUTE_NAME_TITLE_CATEGORY, categoryTitles.get(i));
            attr.put(ATTRIBUTE_NAME_AMOUNT, amounts.get(i));
            attr.put(ATTRIBUTE_NAME_CURRENCY, currencies.get(i));
            attr.put(ATTRIBUTE_NAME_LOGO_CASH, logoCashes.get(i));
            attr.put(ATTRIBUTE_NAME_TITLE_CASH, cashTitles.get(i));
            attr.put(ATTRIBUTE_NAME_DATA, dates.get(i));
            attr.put(ATTRIBUTE_NAME_COMMENT, comments.get(i));
            data.add(attr);
        }

        String[] from = {
                ATTRIBUTE_NAME_LOGO_CATEGORY,
                ATTRIBUTE_NAME_TITLE_CATEGORY,
                ATTRIBUTE_NAME_AMOUNT,
                ATTRIBUTE_NAME_CURRENCY,
                ATTRIBUTE_NAME_LOGO_CASH,
                ATTRIBUTE_NAME_TITLE_CASH,
                ATTRIBUTE_NAME_DATA,
                ATTRIBUTE_NAME_COMMENT};
        int[] to = {
                R.id.imageView_logoCategory,
                R.id.textView_titleCategory,
                R.id.textView_amount,
                R.id.textView_currency,
                R.id.imageView_logoCash,
                R.id.textView_titleCash,
                R.id.textView_date,
                R.id.textView_comment};

        SimpleAdapter adapter = new SimpleAdapter(getActivity(), data, itemLayout, from, to);
        this.setListAdapter(adapter);
    }

    public static OperationListFragment newInstance(int sectionNumber) {
        OperationListFragment fragment = new OperationListFragment();
        fragment.sectionNumber = sectionNumber;
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.global, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_new_operation) {
            startActivity(new Intent(getActivity(), NewOperationActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        FloatingActionButton floatingActionButton = (FloatingActionButton)view.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), NewOperationActivity.class));
            }
        });

        return view;
    }
}
