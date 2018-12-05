package startandroid.ru.formsofclient;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import startandroid.ru.formsofclient.DataClasses.ActualPresence;
import startandroid.ru.formsofclient.DataClasses.Circumstances;
import startandroid.ru.formsofclient.DataClasses.CopingMechanism;
import startandroid.ru.formsofclient.DataClasses.Difficulties;
import startandroid.ru.formsofclient.DataClasses.InformSatisfyDrcStaff;
import startandroid.ru.formsofclient.DataClasses.ReasonAbsence;
import startandroid.ru.formsofclient.DataClasses.Receiver;
import startandroid.ru.formsofclient.DataClasses.SelfSufficiency;
import startandroid.ru.formsofclient.DataClasses.TypeData;
import startandroid.ru.formsofclient.DataClasses.User;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Retrofit retrofit;
    DBHelper dbHelper;
    Button btn_getStructure, btn_fillForm;
    final String myTag = "Retrofit";
    WebAPI webAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        btn_getStructure = findViewById(R.id.btn_GetStructure);
        btn_getStructure.setOnClickListener(this);
        btn_fillForm = findViewById(R.id.btn_fillForm);
        btn_fillForm.setOnClickListener(this);

        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl("http://lap.drc.ngo:8088")
                .build();
        dbHelper = new DBHelper(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btn_GetStructure:
                getStructure();
                break;
            case R.id.btn_fillForm:
                openForm();
                break;
            default:
                break;
        }

    }

    private void openForm() {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, FormOfClient.class);
        startActivity(intent);
    }

    private void getStructure() {

        webAPI = retrofit.create(WebAPI.class);
        getStructureFill();
        getStructureUser();
        getStructureReasonAbsence();
        getStructureActualPresence();
        getStructureYesNo();
        getStructureDifficulties();
        getStructureSelfSufficiency();
        getStructureReceiver();
        getStructureCircumstances();
        getStructureCopingMechanism();
        getStructureInformSatisfyDrcStaff();
        Toast.makeText(MainActivity.this, "Data received",Toast.LENGTH_LONG).show();

    }

    private void deleteDataBase(SQLiteDatabase db, String table) {
        int clearCount = db.delete(table, null, null);
        Log.d(myTag, "deleted data " + table + " " + clearCount + " counts");
    }

    private void getStructureFill() {

        Observable<List<TypeData>> observable = webAPI.getDataFill();


        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<TypeData>>() {
                    @Override
                    public void onCompleted() {
                        Log.d(myTag,"onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(myTag,"onError " + e);
                        Toast.makeText(MainActivity.this, e.toString(),Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNext(List<TypeData> typeData) {

                        Log.d(myTag,"onNext");
                        if (typeData.size() > 0){
                            ContentValues cv = new ContentValues();
                            SQLiteDatabase db = dbHelper.getWritableDatabase();
                            //удаляем вначале все записи
                            deleteDataBase(db, "fill");
                            //записываем новые записи
                            int i = 0;
                            for (TypeData element : typeData) {
                                cv.put("id", i);
                                i = i + 1;
                                cv.put("id_base", element.getId());
                                cv.put("name", element.getName());
                                cv.put("textName", element.getTextName());
                                cv.put("textNameUa", element.getTextNameUa());
                                cv.put("color", element.getColor());
                                cv.put("categoryId", element.getCategoryId());
                                cv.put("identifier", element.getIdentifier());
                                // вставляем запись и получаем ее ID
                                long rowID = db.insert("fill", null, cv);
                                Log.d(myTag, "fill row inserted, ID = " + rowID);
                            }
                        }
                    }
                });
    };

    private void getStructureUser() {

        Observable<List<User>> observable = webAPI.getDataUser();
        final String myTag = "Retrofit";

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<User>>() {
                    @Override
                    public void onCompleted() {
                        Log.d(myTag,"onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(myTag,"onError " + e);
                        Toast.makeText(MainActivity.this, e.toString(),Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNext(List<User> users) {

                        Log.d(myTag,"onNext");
                        if (users.size() > 0){
                            ContentValues cv = new ContentValues();
                            SQLiteDatabase db = dbHelper.getWritableDatabase();
                            //удаляем вначале все записи
                            deleteDataBase(db, "user");
                            //записываем новые записи
                            int i = 0;
                            for (User element : users) {
                                cv.put("id", i);
                                i = i + 1;
                                cv.put("id_base", element.getId());
                                cv.put("name", element.getName());
                                // вставляем запись и получаем ее ID
                                long rowID = db.insert("user", null, cv);
                                Log.d(myTag, "user row inserted, ID = " + rowID);
                            }
                        }
                    }
                });
    }

    private void getStructureReasonAbsence() {
        WebAPI webAPI = retrofit.create(WebAPI.class);
        Observable<List<ReasonAbsence>> observable = webAPI.getDataReasonAbsence();
        final String myTag = "Retrofit";

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<ReasonAbsence>>() {
                    @Override
                    public void onCompleted() {
                        Log.d(myTag,"onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(myTag,"onError " + e);
                        Toast.makeText(MainActivity.this, e.toString(),Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNext(List<ReasonAbsence> reasonAbsences) {

                        Log.d(myTag,"onNext");
                        if (reasonAbsences.size() > 0){
                            ContentValues cv = new ContentValues();
                            SQLiteDatabase db = dbHelper.getWritableDatabase();
                            //удаляем вначале все записи
                            deleteDataBase(db, "reason_absence");
                            //записываем новые записи
                            int i = 0;
                            for (ReasonAbsence element : reasonAbsences) {
                                cv.put("id", i);
                                i = i + 1;
                                cv.put("id_base", element.getId());
                                cv.put("nameUa", element.getNameUa());
                                cv.put("nameEn", element.getNameEn());
                                cv.put("identifier", element.getIdentifier());
                                cv.put("tmspDtCreate", element.getTmspDtCreate());
                                cv.put("tmspDtChange", element.getTmspDtChange());
                                // вставляем запись и получаем ее ID
                                long rowID = db.insert("reason_absence", null, cv);
                                Log.d(myTag, "reasonAbsences row inserted, ID = " + rowID);
                            }
                        }
                    }
                });
    }

    private void getStructureActualPresence() {
        WebAPI webAPI = retrofit.create(WebAPI.class);
        Observable<List<ActualPresence>> observable = webAPI.getDataActualPresence();
        final String myTag = "Retrofit";

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<ActualPresence>>() {
                    @Override
                    public void onCompleted() {
                        Log.d(myTag,"onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(myTag,"onError " + e);
                        Toast.makeText(MainActivity.this, e.toString(),Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNext(List<ActualPresence> actualPresences) {

                        Log.d(myTag,"onNext");
                        if (actualPresences.size() > 0){
                            ContentValues cv = new ContentValues();
                            SQLiteDatabase db = dbHelper.getWritableDatabase();
                            //удаляем вначале все записи
                            deleteDataBase(db, "actual_presence");
                            //записываем новые записи
                            int i = 0;
                            for (ActualPresence element : actualPresences) {
                                cv.put("id", i);
                                i = i + 1;
                                cv.put("id_base", element.getId());
                                cv.put("nameUa", element.getNameUa());
                                cv.put("nameEn", element.getNameEn());
                                cv.put("identifier", element.getIdentifier());
                                cv.put("tmspDtCreate", element.getTmspDtCreate());
                                cv.put("tmspDtChange", element.getTmspDtChange());
                                // вставляем запись и получаем ее ID
                                long rowID = db.insert("actual_presence", null, cv);
                                Log.d(myTag, "actualPresences row inserted, ID = " + rowID);
                            }
                        }
                    }
                });
    }

    private void getStructureYesNo() {

        WebAPI webAPI = retrofit.create(WebAPI.class);
        Observable<List<TypeData>> observable = webAPI.getDataYesNo();
        final String myTag = "Retrofit";

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<TypeData>>() {
                    @Override
                    public void onCompleted() {
                        Log.d(myTag,"onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(myTag,"onError " + e);
                        Toast.makeText(MainActivity.this, e.toString(),Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNext(List<TypeData> typeData) {

                        Log.d(myTag,"onNext");
                        if (typeData.size() > 0){
                            ContentValues cv = new ContentValues();
                            SQLiteDatabase db = dbHelper.getWritableDatabase();
                            //удаляем вначале все записи
                            deleteDataBase(db, "yes_no");
                            //записываем новые записи
                            int i = 0;
                            for (TypeData element : typeData) {
                                cv.put("id", i);
                                i = i + 1;
                                cv.put("id_base", element.getId());
                                cv.put("name", element.getName());
                                cv.put("textName", element.getTextName());
                                cv.put("textNameUa", element.getTextNameUa());
                                cv.put("color", element.getColor());
                                cv.put("categoryId", element.getCategoryId());
                                cv.put("identifier", element.getIdentifier());
                                // вставляем запись и получаем ее ID
                                long rowID = db.insert("yes_no", null, cv);
                                Log.d(myTag, "yesNo row inserted, ID = " + rowID);
                            }
                        }
                    }
                });
    };

    private void getStructureDifficulties() {
        WebAPI webAPI = retrofit.create(WebAPI.class);
        Observable<List<Difficulties>> observable = webAPI.getDataDifficulties();
        final String myTag = "Retrofit";

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Difficulties>>() {
                    @Override
                    public void onCompleted() {
                        Log.d(myTag,"onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(myTag,"onError " + e);
                        Toast.makeText(MainActivity.this, e.toString(),Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNext(List<Difficulties> difficulties) {

                        Log.d(myTag,"onNext");
                        if (difficulties.size() > 0){
                            ContentValues cv = new ContentValues();
                            SQLiteDatabase db = dbHelper.getWritableDatabase();
                            //удаляем вначале все записи
                            deleteDataBase(db, "difficulties");
                            //записываем новые записи
                            int i = 0;
                            for (Difficulties element : difficulties) {
                                cv.put("id", i);
                                i = i + 1;
                                cv.put("id_base", element.getId());
                                cv.put("nameUa", element.getNameUa());
                                cv.put("nameEn", element.getNameEn());
                                cv.put("identifier", element.getIdentifier());
                                cv.put("tmspDtCreate", element.getTmspDtCreate());
                                cv.put("tmspDtChange", element.getTmspDtChange());
                                // вставляем запись и получаем ее ID
                                long rowID = db.insert("difficulties", null, cv);
                                Log.d(myTag, "difficulties row inserted, ID = " + rowID);
                            }
                        }
                    }
                });
    }

    private void getStructureSelfSufficiency() {
        WebAPI webAPI = retrofit.create(WebAPI.class);
        Observable<List<SelfSufficiency>> observable = webAPI.getDataSelfSufficiency();
        final String myTag = "Retrofit";

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<SelfSufficiency>>() {
                    @Override
                    public void onCompleted() {
                        Log.d(myTag,"onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(myTag,"onError " + e);
                        Toast.makeText(MainActivity.this, e.toString(),Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNext(List<SelfSufficiency> selfSufficiencies) {

                        Log.d(myTag,"onNext");
                        if (selfSufficiencies.size() > 0){
                            ContentValues cv = new ContentValues();
                            SQLiteDatabase db = dbHelper.getWritableDatabase();
                            //удаляем вначале все записи
                            deleteDataBase(db, "self_sufficiency");
                            //записываем новые записи
                            int i = 0;
                            for (SelfSufficiency element : selfSufficiencies) {
                                cv.put("id", i);
                                i = i + 1;
                                cv.put("id_base", element.getId());
                                cv.put("nameUa", element.getNameUa());
                                cv.put("nameEn", element.getNameEn());
                                cv.put("identifier", element.getIdentifier());
                                cv.put("tmspDtCreate", element.getTmspDtCreate());
                                cv.put("tmspDtChange", element.getTmspDtChange());
                                // вставляем запись и получаем ее ID
                                long rowID = db.insert("self_sufficiency", null, cv);
                                Log.d(myTag, "selfSufficiencies row inserted, ID = " + rowID);
                            }
                        }
                    }
                });
    }

    private void getStructureReceiver() {
        WebAPI webAPI = retrofit.create(WebAPI.class);
        Observable<List<Receiver>> observable = webAPI.getDataReceiver();
        final String myTag = "Retrofit";

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Receiver>>() {
                    @Override
                    public void onCompleted() {
                        Log.d(myTag,"onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(myTag,"onError " + e);
                        Toast.makeText(MainActivity.this, e.toString(),Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNext(List<Receiver> receivers) {

                        Log.d(myTag,"onNext");
                        if (receivers.size() > 0){
                            ContentValues cv = new ContentValues();
                            SQLiteDatabase db = dbHelper.getWritableDatabase();
                            //удаляем вначале все записи
                            deleteDataBase(db, "receiver");
                            //записываем новые записи
                            int i = 0;
                            for (Receiver element : receivers) {
                                cv.put("id", i);
                                i = i + 1;
                                cv.put("id_base", element.getId());
                                cv.put("nameUa", element.getNameUa());
                                cv.put("nameEn", element.getNameEn());
                                cv.put("identifier", element.getIdentifier());
                                cv.put("tmspDtCreate", element.getTmspDtCreate());
                                cv.put("tmspDtChange", element.getTmspDtChange());
                                // вставляем запись и получаем ее ID
                                long rowID = db.insert("receiver", null, cv);
                                Log.d(myTag, "receivers row inserted, ID = " + rowID);
                            }
                        }
                    }
                });
    }

    private void getStructureCircumstances() {
        WebAPI webAPI = retrofit.create(WebAPI.class);
        Observable<List<Circumstances>> observable = webAPI.getDataCircumstances();
        final String myTag = "Retrofit";

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Circumstances>>() {
                    @Override
                    public void onCompleted() {
                        Log.d(myTag,"onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(myTag,"onError " + e);
                        Toast.makeText(MainActivity.this, e.toString(),Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNext(List<Circumstances> circumstances) {

                        Log.d(myTag,"onNext");
                        if (circumstances.size() > 0){
                            ContentValues cv = new ContentValues();
                            SQLiteDatabase db = dbHelper.getWritableDatabase();
                            //удаляем вначале все записи
                            deleteDataBase(db, "circumstances");
                            //записываем новые записи
                            int i = 0;
                            for (Circumstances element : circumstances) {
                                cv.put("id", i);
                                i = i + 1;
                                cv.put("id_base", element.getId());
                                cv.put("nameUa", element.getNameUa());
                                cv.put("nameEn", element.getNameEn());
                                cv.put("identifier", element.getIdentifier());
                                cv.put("tmspDtCreate", element.getTmspDtCreate());
                                cv.put("tmspDtChange", element.getTmspDtChange());
                                // вставляем запись и получаем ее ID
                                long rowID = db.insert("circumstances", null, cv);
                                Log.d(myTag, "circumstances row inserted, ID = " + rowID);
                            }
                        }
                    }
                });
    }

    private void getStructureCopingMechanism() {
        WebAPI webAPI = retrofit.create(WebAPI.class);
        Observable<List<CopingMechanism>> observable = webAPI.getDataCopingMechanism();
        final String myTag = "Retrofit";

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<CopingMechanism>>() {
                    @Override
                    public void onCompleted() {
                        Log.d(myTag,"onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(myTag,"onError " + e);
                        Toast.makeText(MainActivity.this, e.toString(),Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNext(List<CopingMechanism> copingMechanisms) {

                        Log.d(myTag,"onNext");
                        if (copingMechanisms.size() > 0){
                            ContentValues cv = new ContentValues();
                            SQLiteDatabase db = dbHelper.getWritableDatabase();
                            //удаляем вначале все записи
                            deleteDataBase(db, "coping_mechanism");
                            //записываем новые записи
                            int i = 0;
                            for (CopingMechanism element : copingMechanisms) {
                                cv.put("id", i);
                                i = i + 1;
                                cv.put("id_base", element.getId());
                                cv.put("nameUa", element.getNameUa());
                                cv.put("nameEn", element.getNameEn());
                                cv.put("identifier", element.getIdentifier());
                                cv.put("tmspDtCreate", element.getTmspDtCreate());
                                cv.put("tmspDtChange", element.getTmspDtChange());
                                // вставляем запись и получаем ее ID
                                long rowID = db.insert("coping_mechanism", null, cv);
                                Log.d(myTag, "copingMechanisms row inserted, ID = " + rowID);
                            }
                        }
                    }
                });
    }

    private void getStructureInformSatisfyDrcStaff() {
        WebAPI webAPI = retrofit.create(WebAPI.class);
        Observable<List<InformSatisfyDrcStaff>> observable = webAPI.getDataInformSatisfyDrcStaff();
        final String myTag = "Retrofit";

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<InformSatisfyDrcStaff>>() {
                    @Override
                    public void onCompleted() {
                        Log.d(myTag,"onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(myTag,"onError " + e);
                        Toast.makeText(MainActivity.this, e.toString(),Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNext(List<InformSatisfyDrcStaff> informSatisfyDrcStaffs) {

                        Log.d(myTag,"onNext");
                        if (informSatisfyDrcStaffs.size() > 0){
                            ContentValues cv = new ContentValues();
                            SQLiteDatabase db = dbHelper.getWritableDatabase();
                            //удаляем вначале все записи
                            deleteDataBase(db, "inform_satisfy_drc_staff");
                            //записываем новые записи
                            int i = 0;
                            for (InformSatisfyDrcStaff element : informSatisfyDrcStaffs) {
                                cv.put("id", i);
                                i = i + 1;
                                cv.put("id_base", element.getId());
                                cv.put("nameUa", element.getNameUa());
                                cv.put("nameEn", element.getNameEn());
                                cv.put("identifier", element.getIdentifier());
                                cv.put("tmspDtCreate", element.getTmspDtCreate());
                                cv.put("tmspDtChange", element.getTmspDtChange());
                                // вставляем запись и получаем ее ID
                                long rowID = db.insert("inform_satisfy_drc_staff", null, cv);
                                Log.d(myTag, "informSatisfyDrcStaffs row inserted, ID = " + rowID);
                            }
                        }
                    }
                });
    }
}
