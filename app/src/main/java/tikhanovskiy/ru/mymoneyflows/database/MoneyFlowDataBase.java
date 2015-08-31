package tikhanovskiy.ru.mymoneyflows.database;

import com.raizlabs.android.dbflow.annotation.Database;

@Database(name = MoneyFlowDataBase.NAME, version = MoneyFlowDataBase.VERSION)
public class MoneyFlowDataBase {

    public static final String NAME = "MoneyFlowDataBase";

    public static final int VERSION = 1;
}
