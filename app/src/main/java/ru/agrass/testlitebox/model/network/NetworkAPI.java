package ru.agrass.testlitebox.model.network;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ru.agrass.testlitebox.model.network.responses.QueryResponse;

public interface NetworkAPI {

    @GET("v1?key=AIzaSyCe5iNeuNLarPBSgMuXZHynb_V_wNgqJOU&cx=018157940209485584464:-7fpivzqfc8")
    Observable<QueryResponse> getPages(@Query("q") String query);

}
