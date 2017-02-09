package developerkampus.zodiak.retrofit;

import developerkampus.zodiak.model.ZodiakModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Wambakun on 08/02/2017.
 */

public interface ApiInterface {
    @GET("/api/zodiak")
    Call<ZodiakModel> callZodiak(
            @Query("nama") String nama,
            @Query("tgl") String tanggal
    );

}
