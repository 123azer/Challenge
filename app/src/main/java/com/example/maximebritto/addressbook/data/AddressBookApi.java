package com.example.maximebritto.addressbook.data;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by trainermac on 02/03/16.
 */
public interface AddressBookApi {

    // @GET("search/repositories?q=created:>2017-10-22")
    // Call<List<Group>> getGroupList();

    @GET("/search/repositories")
    Call<List<Group>> getGroupList(@QueryMap(encoded = false)  Map<String,String> filter);

    //@GET("search/repositories/{id}")
   // Call<Group> getGroup(@Path("id") int groupId,@Query("q") String q);
}
