package ir.aliap1376ir.sources.protobuf.android.retrofit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.protobuf.ProtoConverterFactory;

public class RetrofitConfiguration {
    public static Api getAPI() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        OkHttpClient okHttpClient = new OkHttpClient.
                Builder()
                .addInterceptor(logging)
                .build();

        return new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .client(okHttpClient)
//                .addConverterFactory
                .addConverterFactory(ProtoConverterFactory.create())
                .build()
                .create(Api.class);
    }


}
