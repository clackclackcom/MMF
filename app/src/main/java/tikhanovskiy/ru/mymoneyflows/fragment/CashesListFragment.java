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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tikhanovskiy.ru.mymoneyflows.R;
import tikhanovskiy.ru.mymoneyflows.activity.MainActivity;
import tikhanovskiy.ru.mymoneyflows.activity.NewCashActivity;
import tikhanovskiy.ru.mymoneyflows.database.Cash;

public class CashesListFragment extends ListFragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    final String ATTRIBUTE_NAME_LOGO = "logo";
//    final String ATTRIBUTE_NAME_COLOR = "color";
    final String ATTRIBUTE_NAME_TITLE = "title";
    final String ATTRIBUTE_NAME_SUM = "sum";
    final String ATTRIBUTE_NAME_CURRENCY = "currency";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Включаем отображение меню
        setHasOptionsMenu(true);

        List<Cash> cashList = new Select().from(Cash.class).queryList();
        List<Integer> logos = new ArrayList<>(cashList.size());
//        List<Integer> colours = new ArrayList<>(cashList.size());
        List<String> titles = new ArrayList<>(cashList.size());
        List<Float> amounts = new ArrayList<>(cashList.size());
        List<String> currencies = new ArrayList<>(cashList.size());

        for(Cash cash : cashList){
            logos.add(cash.getLogo().getResourceId());
//            colours.add(cash.getColor().getResourceId());
            titles.add(cash.getName());
            amounts.add(cash.getAmount());
            currencies.add(cash.getCurrency().getShortHand());
        }

        ArrayList<Map<String,Object>> data = new ArrayList<>(cashList.size());
        Map<String, Object> attr;
        for(int i = 0; i < cashList.size(); i++){
            attr = new HashMap<>();
            attr.put(ATTRIBUTE_NAME_LOGO, logos.get(i));
//            attr.put(ATTRIBUTE_NAME_COLOR, colours.get(i));
            attr.put(ATTRIBUTE_NAME_TITLE, titles.get(i));
            attr.put(ATTRIBUTE_NAME_SUM, amounts.get(i));
            attr.put(ATTRIBUTE_NAME_CURRENCY, currencies.get(i));
            data.add(attr);
        }

        String[] from =  {ATTRIBUTE_NAME_LOGO, ATTRIBUTE_NAME_TITLE, ATTRIBUTE_NAME_SUM,
                        ATTRIBUTE_NAME_CURRENCY};
        int [] to = {R.id.imageView_logo, R.id.textView_title, R.id.textView_sum, R.id.textView_currency};
        SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(), data, R.layout.item_cashes_list, from, to);
        this.setListAdapter(simpleAdapter);

    }

    public static CashesListFragment newInstance(int sectionNumber) {
        CashesListFragment fragment = new CashesListFragment();
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
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        FloatingActionButton floatingActionButton = (FloatingActionButton)view.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), NewCashActivity.class));
            }
        });

        return view;
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
        if (id == R.id.action_new_cash) {
            startActivity(new Intent(getActivity(), NewCashActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}

