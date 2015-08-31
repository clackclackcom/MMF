package tikhanovskiy.ru.mymoneyflows.database;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.ForeignKeyReference;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.sql.Date;

/**
 * Таблица с расходами
 */
@Table(databaseName = MoneyFlowDataBase.NAME)
public class Expense extends BaseModel implements Operation {

    @Column
    @PrimaryKey(autoincrement = true)
    long id;

    @Column
    Date date; //дата расхода

    @Column
    float amount; //сумма

    @Column
    String comment; //комментарий к расходу

    @Column
    @ForeignKey(references = {@ForeignKeyReference(
            columnName = "cash_id",
            columnType = Long.class,
            foreignColumnName = "id")},
            saveForeignKeyModel = false)
    Cash cash; // счет

    @Column
    @ForeignKey(references = {@ForeignKeyReference(
            columnName = "subCategoryExpense_id",
            columnType = Long.class,
            foreignColumnName = "id")},
            saveForeignKeyModel = false)
    SubCategoryExpense subCategoryExpense; //подкатегория

    public void setSubCategory(SubCategory subCategoryExpense) {
        this.subCategoryExpense = (SubCategoryExpense)subCategoryExpense;
    }

    public void setCash(Cash cash) {
        this.cash = cash;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public float getAmount() {
        return amount;
    }

    public String getComment() {
        return comment;
    }

    public Cash getCash() {
        return cash;
    }

    public SubCategory getSubCategory() {
        return subCategoryExpense;
    }
}
