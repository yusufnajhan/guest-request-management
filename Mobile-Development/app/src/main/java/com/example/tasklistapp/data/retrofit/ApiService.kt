import com.example.tasklistapp.BuildConfig
import com.example.tasklistapp.data.response.TaskResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {
    @Headers("Authorization: token ${BuildConfig.KEY}")
    @GET("search/users")
    fun getListUsers(
        @Query("q") username: String
    ): Call<TaskResponse>
}