package tikhanovskiy.ru.mymoneyflows.database;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.ForeignKeyReference;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.sql.Date;

/**
 * Таблица с доходами
 */
@Table(databaseName = MoneyFlowDataBase.NAME)
public class Profit extends BaseModel implements Operation {

    @Column
    @PrimaryKey(autoincrement = true)
    long id;

    @Column
    Date date; //дата дохода

    @Column
    float amount; //сумма

    @Column
    String comment; //комментарий к доходу

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
    SubCategoryProfit subCategoryProfit; //подкатегория

    public void setDate(Date date) {
        this.date = date;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setCash(Cash cash) {
        this.cash = cash;
    }

    public void setSubCategory(SubCategory subCategoryProfit) {
        this.subCategoryProfit = (SubCategoryProfit)subCategoryProfit;
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
        return subCategoryProfit;
    }
}
