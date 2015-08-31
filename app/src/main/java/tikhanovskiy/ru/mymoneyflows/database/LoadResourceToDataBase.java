package tikhanovskiy.ru.mymoneyflows.database;

import java.util.ArrayList;

import tikhanovskiy.ru.mymoneyflows.R;


public class LoadResourceToDataBase {

   /**
    *  Добавление цветов в таблицу базы данных на основе color.xml
    */
    public static void loadColors() {

        ArrayList<Integer> colorIds = new ArrayList<>();

        colorIds.add(R.color.red);
        colorIds.add(R.color.pink);
        colorIds.add(R.color.purple);
        colorIds.add(R.color.deepPurple);
        colorIds.add(R.color.indigo);
        colorIds.add(R.color.blue);
        colorIds.add(R.color.lightBlue);
        colorIds.add(R.color.cyan);
        colorIds.add(R.color.teal);
        colorIds.add(R.color.green);
        colorIds.add(R.color.lightGreen);
        colorIds.add(R.color.lime);
        colorIds.add(R.color.yellow);
        colorIds.add(R.color.amber);
        colorIds.add(R.color.orange);
        colorIds.add(R.color.red);
        colorIds.add(R.color.deepOrange);
        colorIds.add(R.color.brown);
        colorIds.add(R.color.grey);
        colorIds.add(R.color.blueGrey);

        for(Integer integer : colorIds){
            Color color = new Color();
            color.resourceId = integer;
            color.save();
        }
    }

    /**
     *  Добавление иконок счетов в таблицу базы данных
     */
    public static void loadLogoCash() {

        ArrayList<Integer> logoIds = new ArrayList<>();

        logoIds.add(R.drawable.cash);
        logoIds.add(R.drawable.visa);
        logoIds.add(R.drawable.mastercard);

        for(Integer integer : logoIds){
            LogoCash logoCash = new LogoCash();
            logoCash.resourceId = integer;
            logoCash.save();
        }
    }

    /**
     *  Добавление иконок категорий в таблицу базы данных
     */
    public static void loadLogoCategory(){

        ArrayList<Integer> logoIds = new ArrayList<>();

        logoIds.add(R.drawable.baby);
        logoIds.add(R.drawable.car);
        logoIds.add(R.drawable.drink);
        logoIds.add(R.drawable.education);
        logoIds.add(R.drawable.food);
        logoIds.add(R.drawable.job);
        logoIds.add(R.drawable.sport);

        for(Integer integer : logoIds){
            LogoCategory logoCategory = new LogoCategory();
            logoCategory.resourceId = integer;
            logoCategory.save();
        }
    }
}
