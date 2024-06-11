import com.example.tasklistapp.BuildConfig
import com.example.tasklistapp.data.response.RequestResponse
import com.example.tasklistapp.data.response.RequestResponseItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {

    @GET("requests")
    fun getListTasks(
    ): Call<List<RequestResponseItem>>
}