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
 * Таблица с категориями расходов
 */
@Table(databaseName = MoneyFlowDataBase.NAME)
public class CategoryExpense extends BaseModel implements Category {

    @Column
    @PrimaryKey(autoincrement = true)
    long id;

    @Column
    String name; //название категории

    @Column
    @ForeignKey(references = {@ForeignKeyReference(
            columnName = "logo_id",
            columnType = Long.class,
            foreignColumnName = "id")},
            saveForeignKeyModel = false)
    LogoCategory logo; //логотип

    @Column
    @ForeignKey(references = {@ForeignKeyReference(
            columnName = "color_id",
            columnType = Long.class,
            foreignColumnName = "id")},
            saveForeignKeyModel = false)
    Color color; //цвет

    List<SubCategoryExpense> subCategoryExpenses; //список подкатегорий

    /**
     * @return Список подкатегорий для текущей категории
     */
    @OneToMany(methods = {OneToMany.Method.SAVE, OneToMany.Method.DELETE},
            variableName = "subCategoryExpenses")
    public List<SubCategoryExpense> getSubCategory(){
        if(subCategoryExpenses == null){
            subCategoryExpenses = new Select()
                    .from(SubCategoryExpense.class)
                    .where(Condition.column(SubCategoryExpense$Table.CATEGORYEXPENSE_CATEGORY_ID).is(this.id))
                    .queryList();
        }
        return subCategoryExpenses;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LogoCategory getLogo() {
        return logo;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setLogo(LogoCategory logo) {
        this.logo = logo;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "CategoryExpense{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", logo=" + logo +
                ", color=" + color +
                ", subCategoryExpenses=" + subCategoryExpenses +
                '}';
    }
}
