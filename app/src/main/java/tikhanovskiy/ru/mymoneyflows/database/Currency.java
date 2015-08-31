package tikhanovskiy.ru.mymoneyflows.database;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Таблица с валютами
 */
@Table(databaseName = MoneyFlowDataBase.NAME)
public class Currency extends BaseModel {

    @Column
    @PrimaryKey(autoincrement = true)
    long id;

    @Column
    String name; //название валюты

    @Column
    String shortHand; //короткая запись

    public String getName() {
        return name;
    }

    public String getShortHand() {
        return shortHand;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setShortHand(String shortHand) {
        this.shortHand = shortHand;
    }
}
