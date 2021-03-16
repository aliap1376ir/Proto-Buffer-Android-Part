package ir.aliap1376ir.sources.protobuf.android.retrofit;

import ir.aliap1376ir.sources.protobuf.android.object.PersonOuterClass;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface Api {
    String BASE_URL = "http://192.168.1.40:5000";
    String CONTENT_TYPE = "Content-Type";
    String CONTENT_TYPE_VALUE_PROTO_BUF = "application/x-protobuf";
    String CONTENT_TYPE_PROTO_BUF = CONTENT_TYPE + ": " + CONTENT_TYPE_VALUE_PROTO_BUF;

    @POST("/register")
    @Headers(CONTENT_TYPE_PROTO_BUF)
    Call<PersonOuterClass.Person> register(@Body PersonOuterClass.Person person);

    @GET("/all")
    @Headers(CONTENT_TYPE_PROTO_BUF)
    Call<PersonOuterClass.People> all();

}
