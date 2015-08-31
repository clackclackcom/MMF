package tikhanovskiy.ru.mymoneyflows.database;

import java.util.List;

/**
 * Created by Maxim Tikhanovskiy on 26.08.2015.
 */
public interface Category {
    public long getId();

    public String getName();

    public LogoCategory getLogo();

    public Color getColor();

    public List<? extends SubCategory> getSubCategory();

    public void setColor(Color color);

    public void setLogo(LogoCategory logo);

    public void setName(String name);
}
