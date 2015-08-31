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
 * Таблица с подкатегориями расходов
 */
@Table(databaseName = MoneyFlowDataBase.NAME)
public class SubCategoryExpense extends BaseModel implements SubCategory {

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
    CategoryExpense categoryExpense; //категория

    List<Expense> expenses; //список расходов

    /**
     * @return Список расходов по текущей категории
     */
    @OneToMany(methods = {OneToMany.Method.SAVE, OneToMany.Method.DELETE},
            variableName = "expenses")
    public List<Expense> getExpenses(){
        if(expenses == null){
            expenses = new Select()
                    .from(Expense.class)
                    .where(Condition.column(Expense$Table.SUBCATEGORYEXPENSE_SUBCATEGORYEXPENSE_ID).is(this.id))
                    .queryList();
        }
        return expenses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(Category category) {
        this.categoryExpense = (CategoryExpense)category;
    }

    public Category getCategory() {
        return categoryExpense;
    }
}
