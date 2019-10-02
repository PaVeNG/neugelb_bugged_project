package com.bugged.themoviedb.data.remote;

import com.bugged.themoviedb.data.model.Page;
import com.bugged.themoviedb.util.ConstantUtils;
import com.bugged.themoviedb.util.RxUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface MovieListService {

    String ENDPOINT = ConstantUtils.HOST_NAME;

    @GET("movie")
    Observable<Page> getMovies(@Query("sort_by") String sort_by,
                               @Query("api_key") String api_key,
                               @Query("page") String page);


    class Creator {

        public static MovieListService newMovieDBServices() {


            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .addInterceptor(new Interceptor() {
                        @Override
                        public okhttp3.Response intercept(Chain chain) throws IOException {
                            Request.Builder ongoing = chain.request().newBuilder();
                            ongoing.addHeader("Accept", "application/json;versions=1");

                            String token = RxUtil.getToken();
                            if (!token.equals("")) {
                                ongoing.addHeader("Authorization", token);//"Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjViZDBiZGRlMWUwNGNiNjNkMDMxMTRmN2ZjYjg3MTJhNjIxNTRhMGNmMDdhOGQzYTJlNDJjYmQ2MzQzZDI1ZDUwNmFjYjQ0ZWEwYTc0OTIyIn0.eyJhdWQiOiIxIiwianRpIjoiNWJkMGJkZGUxZTA0Y2I2M2QwMzExNGY3ZmNiODcxMmE2MjE1NGEwY2YwN2E4ZDNhMmU0MmNiZDYzNDNkMjVkNTA2YWNiNDRlYTBhNzQ5MjIiLCJpYXQiOjE1Mjc3NjY5NTAsIm5iZiI6MTUyNzc2Njk1MCwiZXhwIjoxNTU5MzAyOTQ5LCJzdWIiOiIzNSIsInNjb3BlcyI6WyIqIl19.IHnT2HolJxH1t-D_Bo2gpBizvlC6l9XgbvMkZidSY8IHj1DiG9RZwDfME1ykCog389bG6DLIjJKpxTTm73K1sZOryBJRoj7mtnGfyRTwvSH1dmTvfgM4BlqdA7-Z-fr1K6w3JGug5yYaCm5OzdDCZsY59T34EFH6Mcb20xoGkA6NGI9ifdZGZliyEtza0EjlQgLLXjPPiDzp1SkI0IwAmidocfzmzjstdXOV3LeCABWxQv56bqpieo2aVQahW3XJgGs_Wjq96Hv8uwr378Fa3TvkLbQIF8YjzB0wCCpa4rjWF1dEnngFZiFFrXiVa0iC6Z4Sktl6y5mopM62PC_8LYcquBWuInMGoIt0U5Sxfl-d-DJOY0R0FJdZAPRP94-6-WHwfzdpp0Q4_AKLiG2viPMR7YeEonB9PEPG4vnp7vfN5nboRWAEZKCe4nOHi-_c1Jl_8c4jFZI39ZmlM0pazubECqTRT7RhtnICIh6KaZ4bA0QmeJnUrsPLuUcS3sUokE5Rxb-QJp7GBN9m9F5DDob1Qo6heH_X370-oHK-4lejGKP3rT3hAFzs33dhav9YvEngKOkN4tvOiKVp0r-AzyCKy5640yBw12Pm_L9Szs3aDOPgxg2hQYz6V_NW0zFWTrNnv6SSaiK-EtqsRn57m2zEmyOJC452P0D9-3o3qbY");
                            }
                            return chain.proceed(ongoing.build());
                        }
                    })
                    .build();

            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(MovieListService.ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(client)
                    .build();
            return retrofit.create(MovieListService.class);
        }
    }
}
