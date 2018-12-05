package startandroid.ru.formsofclient;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class FormOfClient extends AppCompatActivity implements View.OnClickListener{

    ArrayList<FormData> arrayFill, arrayConfirm1, arrayActualPresence, arrayConfirm2;
    RadioGroup rd_question1, rd_question2, rd_question3, rd_question4;
    EditText tv_grantAmount;
    Button btn_send;
    DBHelper dbHelper;
    SQLiteDatabase db;
    Cursor c;
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_of_client);

        fillData();

        rd_question1 = findViewById(R.id.rd_question1);
        addRadioButtons(rd_question1, arrayFill);
        rd_question2 = findViewById(R.id.rd_question2);
        addRadioButtons(rd_question2, arrayConfirm1);
        rd_question3 = findViewById(R.id.rd_question3);
        addRadioButtons(rd_question3, arrayActualPresence);
        rd_question4 = findViewById(R.id.rd_question4);
        addRadioButtons(rd_question4, arrayConfirm2);
        btn_send = findViewById(R.id.btn_send);
        btn_send.setOnClickListener(this);
        tv_grantAmount = findViewById(R.id.editText2);

        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl("http://lap.drc.ngo:8088")
                .build();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_send:
                int fillId = getIdDataForm(rd_question1, arrayFill);
                int currentlyEngagedId = getIdDataForm(rd_question2, arrayConfirm1);
                int actualPresenceId = getIdDataForm(rd_question3, arrayActualPresence);
                int usingEquipmentId = getIdDataForm(rd_question4, arrayConfirm2);
                int grantAmount = Integer.parseInt(tv_grantAmount.getText().toString());

                /*JSONObject parameters = new JSONObject();
                parameters.put("fillId", fillId);
                parameters.put("currentlyEngagedId", currentlyEngagedId);
                parameters.put("actualPresenceId", actualPresenceId);
                parameters.put("usingEquipmentId", usingEquipmentId);
                parameters.put("grantAmount", grantAmount);
                String parameter = parameters.toString();*/
                ResponceData responceData = new ResponceData(fillId, currentlyEngagedId, actualPresenceId, usingEquipmentId, grantAmount);
                //Toast.makeText(FormOfClient.this, parameter, Toast.LENGTH_LONG).show();
                sendResponce(responceData);

                break;
            default:
                break;
        }
    }

    private void sendResponce(ResponceData responceData) {

        /*WebAPI webAPI = retrofit.create(WebAPI.class);
        webAPI.sendResponce(responceData).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Void>() {
                               @Override
                               public void onCompleted() {

                               }

                               @Override
                               public void onError(Throwable e) {
                                   Toast.makeText(FormOfClient.this, e.toString(), Toast.LENGTH_LONG).show();
                               }

                               @Override
                               public void onNext(Void aVoid) {
                                   Toast.makeText(FormOfClient.this, aVoid.toString(), Toast.LENGTH_LONG).show();
                               }
                           }

                );*/
        WebAPI webAPI = retrofit.create(WebAPI.class);
        webAPI.sendResponce(responceData).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(FormOfClient.this, response.toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(FormOfClient.this, t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private int getIdDataForm(RadioGroup rd_question, ArrayList<FormData> currentArray) {
        int position = rd_question.getCheckedRadioButtonId();
        FormData formData = currentArray.get(position);
        return formData.getId_base();
    };

    private void addRadioButtons(RadioGroup rd_question, ArrayList<FormData> currentArray) {
        int sizeOfArray = currentArray.size();
        RadioButton[] rb = new RadioButton[sizeOfArray];
        for (int i = 0; i < sizeOfArray; i++){
            rb[i]  = new RadioButton(this);
            FormData formData = currentArray.get(i);
            rb[i].setText(formData.getName());
            rb[i].setId(formData.getId());
            rd_question.addView(rb[i]);
        }
    }

    private void fillData() {

        dbHelper = new DBHelper(this);
        arrayFill = fillNextData(dbHelper, "fill", "textName");;
        arrayConfirm1 = fillNextData(dbHelper, "yes_no", "textName");
        arrayActualPresence = fillNextData(dbHelper, "actual_presence", "nameEn");
        arrayConfirm2 = fillNextData(dbHelper, "yes_no", "textName");
        dbHelper.close();

    }

    private ArrayList fillNextData(DBHelper dbHelper, String table, String nameField) {
        db = dbHelper.getWritableDatabase();
        c = db.query(table, null, null, null, null, null, "id");
        Log.d("Retrofit", "read SQL Lite");
        ArrayList currentArray = new ArrayList<FormData>();
        if (c.moveToFirst()) {

            int id_ColIndex = c.getColumnIndex("id");
            int id_base_ColIndex = c.getColumnIndex("id_base");
            int texName_ColIndex = c.getColumnIndex(nameField);

            do {
                Log.d("Retrofit", c.getString(texName_ColIndex));
                FormData formData = new FormData(c.getInt(id_ColIndex), c.getInt(id_base_ColIndex), c.getString(texName_ColIndex));
                currentArray.add(c.getInt(id_ColIndex), formData);
            } while (c.moveToNext());
        } else
            Log.d("Retrofit", "0 rows");
        c.close();
        return currentArray;
    }


}
