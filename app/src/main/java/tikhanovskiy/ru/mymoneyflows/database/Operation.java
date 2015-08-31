package tikhanovskiy.ru.mymoneyflows.database;

import java.sql.Date;

/**
 * Created by m.tihanovsky on 28.08.2015.
 */
public interface Operation {

    public void setSubCategory(SubCategory subCategory);

    public void setCash(Cash cash);

    public void setComment(String comment);

    public void setAmount(float amount);

    public void setDate(Date date);

    public Date getDate();

    public float getAmount();

    public String getComment();

    public Cash getCash();

    public SubCategory getSubCategory();
}
