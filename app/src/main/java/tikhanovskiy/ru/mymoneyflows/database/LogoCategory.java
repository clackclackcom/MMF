package tikhanovskiy.ru.mymoneyflows.database;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Таблица с логотипами для категорий
 */
@Table(databaseName = MoneyFlowDataBase.NAME)
public class LogoCategory extends BaseModel {

    @Column
    @PrimaryKey(autoincrement = true)
    long id;

    @Column
    int resourceId; //id логотипа

    public int getResourceId() {
        return resourceId;
    }
}
