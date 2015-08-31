package tikhanovskiy.ru.mymoneyflows.activity;

import com.raizlabs.android.dbflow.sql.language.Select;

import java.util.List;

import tikhanovskiy.ru.mymoneyflows.database.Cash;
import tikhanovskiy.ru.mymoneyflows.database.CategoryExpense;
import tikhanovskiy.ru.mymoneyflows.database.CategoryProfit;
import tikhanovskiy.ru.mymoneyflows.database.Color;
import tikhanovskiy.ru.mymoneyflows.database.Currency;
import tikhanovskiy.ru.mymoneyflows.database.LoadResourceToDataBase;
import tikhanovskiy.ru.mymoneyflows.database.LogoCash;
import tikhanovskiy.ru.mymoneyflows.database.LogoCategory;
import tikhanovskiy.ru.mymoneyflows.database.SubCategoryExpense;
import tikhanovskiy.ru.mymoneyflows.database.SubCategoryProfit;

/**
 * Created by m.tihanovsky on 24.08.2015.
 */
public class TestLoadBD {

    public static void main(){

        LoadResourceToDataBase.loadColors();
        LoadResourceToDataBase.loadLogoCash();
        LoadResourceToDataBase.loadLogoCategory();

        List<Color> colors = new Select().from(Color.class).queryList();
        List<LogoCash> logos = new Select().from(LogoCash.class).queryList();
        List<LogoCategory> logos2 = new Select().from(LogoCategory.class).queryList();


        //Сохранение валют в базу данных ********************************
        Currency currency = new Currency();
        currency.setName("Рубль");
        currency.setShortHand("РУБ");
        currency.save();

        Currency currency1 = new Currency();
        currency1.setName("Dollar");
        currency1.setShortHand("$");
        currency1.save();

        Currency currency2 = new Currency();
        currency2.setName("Euro");
        currency2.setShortHand("€");
        currency2.save();
        //-------------------------------- ********************************

        // Сохранение кошельков в Базу данных********************************
        Cash cash = new Cash();
        cash.setName("Кошелек");
        cash.setAmount(100);
        cash.setLogo(logos.get(0));
        cash.setCurrency(currency);
        cash.save();

        Cash cash1 = new Cash();
        cash1.setName("VISA");
        cash1.setAmount(300);
        cash1.setLogo(logos.get(1));
        cash1.setCurrency(currency1);
        cash1.save();

        Cash cash2 = new Cash();
        cash2.setName("MasterCard");
        cash2.setAmount(800);
        cash2.setLogo(logos.get(2));
        cash2.setCurrency(currency2);
        cash2.save();
        // --------------------------------********************************

        // Сохранение категорий в Базу данных********************************
        CategoryExpense categoryExpense = new CategoryExpense();
        categoryExpense.setName("Дети");
        categoryExpense.setColor(colors.get(0));
        categoryExpense.setLogo(logos2.get(0));
        categoryExpense.save();

        SubCategoryExpense subCategoryExpense = new SubCategoryExpense();
        subCategoryExpense.setName("Игрушки");
        subCategoryExpense.setCategory(categoryExpense);
        subCategoryExpense.save();

        SubCategoryExpense subCategoryExpense1 = new SubCategoryExpense();
        subCategoryExpense1.setName("Питание");
        subCategoryExpense1.setCategory(categoryExpense);
        subCategoryExpense1.save();

        SubCategoryExpense subCategoryExpense2 = new SubCategoryExpense();
        subCategoryExpense2.setName("Подгузники");
        subCategoryExpense2.setCategory(categoryExpense);
        subCategoryExpense2.save();

        CategoryExpense categoryExpense1 = new CategoryExpense();
        categoryExpense1.setName("Автомобиль");
        categoryExpense1.setColor(colors.get(5));
        categoryExpense1.setLogo(logos2.get(1));
        categoryExpense1.save();

        SubCategoryExpense subCategoryExpense3 = new SubCategoryExpense();
        subCategoryExpense3.setName("Бензин");
        subCategoryExpense3.setCategory(categoryExpense1);
        subCategoryExpense3.save();

        SubCategoryExpense subCategoryExpense4 = new SubCategoryExpense();
        subCategoryExpense4.setName("Запчасти");
        subCategoryExpense4.setCategory(categoryExpense1);
        subCategoryExpense4.save();

        CategoryExpense categoryExpense3 = new CategoryExpense();
        categoryExpense3.setName("Образование");
        categoryExpense3.setColor(colors.get(10));
        categoryExpense3.setLogo(logos2.get(3));
        categoryExpense3.save();

        CategoryProfit categoryProfit = new CategoryProfit();
        categoryProfit.setLogo(logos2.get(5));
        categoryProfit.setColor(colors.get(15));
        categoryProfit.setName("Работа");
        categoryProfit.save();

        SubCategoryProfit subCategoryProfit = new SubCategoryProfit();
        subCategoryProfit.setCategory(categoryProfit);
        subCategoryProfit.setName("Зарплата");
        subCategoryProfit.save();

        SubCategoryProfit subCategoryProfit1 = new SubCategoryProfit();
        subCategoryProfit1.setCategory(categoryProfit);
        subCategoryProfit1.setName("Аванс");
        subCategoryProfit1.save();

        CategoryProfit categoryProfit1 = new CategoryProfit();
        categoryProfit1.setLogo(logos2.get(6));
        categoryProfit1.setColor(colors.get(19));
        categoryProfit1.setName("Халтура");
        categoryProfit1.save();
        // --------------------------------********************************
    }
}
