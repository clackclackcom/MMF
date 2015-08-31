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
 * Таблица со счетами
 */
@Table(databaseName = MoneyFlowDataBase.NAME)
public class Cash extends BaseModel {

    @Column
    @PrimaryKey(autoincrement = true)
    long id;

    @Column
    String name; //название счета

    @Column
    float amount; //колличество средств

    @Column
    @ForeignKey(references = {@ForeignKeyReference(
            columnName = "currency_id",
            columnType = Long.class,
            foreignColumnName = "id")},
            saveForeignKeyModel = false)
    Currency currency; //валюта

    @Column
    @ForeignKey(references = {@ForeignKeyReference(
            columnName = "logo_id",
            columnType = Long.class,
            foreignColumnName = "id")},
            saveForeignKeyModel = false)
    LogoCash logo; //логотип

    @Column
    @ForeignKey(references = {@ForeignKeyReference(
            columnName = "colour_id",
            columnType = Long.class,
            foreignColumnName = "id")},
            saveForeignKeyModel = false)
    Color color; //цвет

    List<Expense> expenses; //список расходов

    /**
     * @return Список расходов по текущему счету
     */
    @OneToMany(methods = {OneToMany.Method.SAVE, OneToMany.Method.DELETE},
            variableName = "expenses")
    public List<Expense> getExpenses(){
        if(expenses == null){
            expenses = new Select()
                    .from(Expense.class)
                    .where(Condition.column(Expense$Table.CASH_CASH_ID).is(this.id))
                    .queryList();
        }
        return expenses;
    }

    List<Profit> profits; //список доходов

    /**
     * @return Список доходов по текущему счету
     */
    @OneToMany(methods = {OneToMany.Method.SAVE, OneToMany.Method.DELETE},
            variableName = "profits")
    public List<Profit> getProfits(){
        if(profits == null){
            profits = new Select()
                    .from(Profit.class)
                    .where(Condition.column(Profit$Table.CASH_CASH_ID).is(this.id))
                    .queryList();
        }
        return profits;
    }


    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public LogoCash getLogo() {
        return logo;
    }

    public Color getColor() {
        return color;
    }

    public void setLogo(LogoCash logo) {
        this.logo = logo;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public void setName(String name) {
        this.name = name;
    }
}
