package startandroid.ru.formsofclient;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Администратор on 25.09.2018.
 */

class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        // конструктор суперкласса
        super(context, "myDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d("Retrofit", "--- onCreate database ---");
        // создаем таблицы с полями
        //ActualPresence
        sqLiteDatabase.execSQL("create table actual_presence ("
                + "id integer primary key autoincrement,"
                + "id_base integer,"
                + "nameUa text,"
                + "nameEn text,"
                + "identifier text,"
                + "tmspDtCreate integer,"
                + "tmspDtChange integer" + ");");
        //Circumstances
        sqLiteDatabase.execSQL("create table circumstances ("
                + "id integer primary key autoincrement,"
                + "id_base integer,"
                + "nameUa text,"
                + "nameEn text,"
                + "identifier text,"
                + "tmspDtCreate integer,"
                + "tmspDtChange integer" + ");");
        //CopingMechanism
        sqLiteDatabase.execSQL("create table coping_mechanism ("
                + "id integer primary key autoincrement,"
                + "id_base integer,"
                + "nameUa text,"
                + "nameEn text,"
                + "identifier text,"
                + "tmspDtCreate integer,"
                + "tmspDtChange integer" + ");");
        //Difficulties
        sqLiteDatabase.execSQL("create table difficulties ("
                + "id integer primary key autoincrement,"
                + "id_base integer,"
                + "nameUa text,"
                + "nameEn text,"
                + "identifier text,"
                + "tmspDtCreate integer,"
                + "tmspDtChange integer" + ");");
        //InformSatisfyDrcStaff
        sqLiteDatabase.execSQL("create table inform_satisfy_drc_staff ("
                + "id integer primary key autoincrement,"
                + "id_base integer,"
                + "nameUa text,"
                + "nameEn text,"
                + "identifier text,"
                + "tmspDtCreate integer,"
                + "tmspDtChange integer" + ");");
        //ReasonAbsence
        sqLiteDatabase.execSQL("create table reason_absence ("
                + "id integer primary key autoincrement,"
                + "id_base integer,"
                + "nameUa text,"
                + "nameEn text,"
                + "identifier text,"
                + "tmspDtCreate integer,"
                + "tmspDtChange integer" + ");");
        //Receiver
        sqLiteDatabase.execSQL("create table receiver ("
                + "id integer primary key autoincrement,"
                + "id_base integer,"
                + "nameUa text,"
                + "nameEn text,"
                + "identifier text,"
                + "tmspDtCreate integer,"
                + "tmspDtChange integer" + ");");
        //SelfSufficiency
        sqLiteDatabase.execSQL("create table self_sufficiency ("
                + "id integer primary key autoincrement,"
                + "id_base integer,"
                + "nameUa text,"
                + "nameEn text,"
                + "identifier text,"
                + "tmspDtCreate integer,"
                + "tmspDtChange integer" + ");");
        //TypeData
        sqLiteDatabase.execSQL("create table fill ("
                + "id integer primary key autoincrement,"
                + "id_base integer,"
                + "name text,"
                + "textName text,"
                + "textNameUa text,"
                + "color text,"
                + "categoryId integer,"
                + "identifier text" + ");");
        //User
        sqLiteDatabase.execSQL("create table user ("
                + "id integer primary key autoincrement,"
                + "id_base integer,"
                + "name text" + ");");
        //YesNo
        sqLiteDatabase.execSQL("create table yes_no ("
                + "id integer primary key autoincrement,"
                + "id_base integer,"
                + "name text,"
                + "textName text,"
                + "textNameUa text,"
                + "color text,"
                + "categoryId integer,"
                + "identifier text" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
