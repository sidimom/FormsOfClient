package startandroid.ru.formsofclient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;
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

/**
 * Created by Администратор on 20.09.2018.
 */

public interface WebAPI {
    @GET("/micro/post-form/fill")
    Observable<List<TypeData>> getDataFill();

    @GET("/micro/post-form/users")
    Observable<List<User>> getDataUser();

    @GET("/micro/post-form/reasonAbsence")
    Observable<List<ReasonAbsence>> getDataReasonAbsence();

    @GET("/micro/post-form/actualPresence")
    Observable<List<ActualPresence>> getDataActualPresence();

    @GET("/micro/post-form/yesNo")
    Observable<List<TypeData>> getDataYesNo();

    @GET("/micro/post-form/difficulties")
    Observable<List<Difficulties>> getDataDifficulties();

    @GET("/micro/post-form/selfSufficiency")
    Observable<List<SelfSufficiency>> getDataSelfSufficiency();

    @GET("/micro/post-form/receiver")
    Observable<List<Receiver>> getDataReceiver();

    @GET("/micro/post-form/circumstances")
    Observable<List<Circumstances>> getDataCircumstances();

    @GET("/micro/post-form/copingMechanism")
    Observable<List<CopingMechanism>> getDataCopingMechanism();

    @GET("/micro/post-form/informSatisfyDrcStaff")
    Observable<List<InformSatisfyDrcStaff>> getDataInformSatisfyDrcStaff();

    @POST("/form/micro-post")
    @Headers({
            "Content-Type: application/json;charset=utf-8",
            "Accept: application/json;charset=utf-8",
            "Cache-Control: max-age=640000"
    })
    //Observable<Void> sendResponce(@Body ResponceData responceData);
    Call<Void> sendResponce(@Body ResponceData responceData);
}
