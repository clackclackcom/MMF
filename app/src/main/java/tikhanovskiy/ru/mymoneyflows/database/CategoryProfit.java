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
 * Таблица с категориями доходов
 */
@Table(databaseName = MoneyFlowDataBase.NAME)
public class CategoryProfit extends BaseModel implements Category {

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
    LogoCategory logo; //логотип категории

    @Column
    @ForeignKey(references = {@ForeignKeyReference(
            columnName = "color_id",
            columnType = Long.class,
            foreignColumnName = "id")},
            saveForeignKeyModel = false)
    Color color; //цвет категории

    List<SubCategoryProfit> subCategoryProfit; //список подкатегорий

    /**
     * @return Список подкатегорий для текущей категории
     */
    @OneToMany(methods = {OneToMany.Method.SAVE, OneToMany.Method.DELETE},
            variableName = "subCategoryProfit")
    public List<SubCategoryProfit> getSubCategory(){
        if(subCategoryProfit == null) {
            subCategoryProfit = new Select()
                    .from(SubCategoryProfit.class)
                    .where(Condition.column(SubCategoryProfit$Table.CATEGORYPROFIT_CATEGORY_ID).is(this.id))
                    .queryList();
        }
        return subCategoryProfit;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setLogo(LogoCategory logo) {
        this.logo = logo;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
