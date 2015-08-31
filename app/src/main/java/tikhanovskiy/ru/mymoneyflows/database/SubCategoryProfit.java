package tikhanovskiy.ru.mymoneyflows.database;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.ForeignKeyReference;
import com.raizlabs.android.dbflow.annotation.OneToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.builder.Condition;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;

/**
 * Таблица с подкатегориями доходов
 */
@Table(databaseName = MoneyFlowDataBase.NAME)
public class SubCategoryProfit extends BaseModel implements SubCategory {

    @Column
    @PrimaryKey(autoincrement = true)
    long id;

    @Column
    String name; //название подкатегории

    @Column
    @ForeignKey(references = {@ForeignKeyReference(
            columnName = "category_id",
            columnType = Long.class,
            foreignColumnName = "id")},
            saveForeignKeyModel = false)
    CategoryProfit categoryProfit; //категория

    List<Profit> profits; //список доходов

    /**
     * @return Список доходов по текущей категории
     */
    @OneToMany(methods = {OneToMany.Method.SAVE, OneToMany.Method.DELETE},
            variableName = "profits")
    public List<Profit> getProfits(){
        if(profits == null){
            profits = new Select()
                    .from(Profit.class)
                    .where(Condition.column(Profit$Table.SUBCATEGORYPROFIT_SUBCATEGORYEXPENSE_ID).is(this.id))
                    .queryList();
        }
        return profits;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(Category category) {
        this.categoryProfit = (CategoryProfit)category;
    }

    public Category getCategory() {
        return categoryProfit;
    }
}
