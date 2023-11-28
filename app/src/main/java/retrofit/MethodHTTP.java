package retrofit;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MethodHTTP {
//    memanggil main linknya
    @GET("cedasapi/User_Registration.php")
    Call<UserResponse> getAllUser();





}
