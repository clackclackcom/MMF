package tikhanovskiy.ru.mymoneyflows.database;

/**
 * Created by Maxim Tikhanovskiy on 26.08.2015.
 */
public interface SubCategory {

    public String getName();

    public void setName(String name);

    public void setCategory(Category category);

    public Category getCategory();
}
