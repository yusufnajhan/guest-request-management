
import com.example.tasklistapp.data.response.RequestBody
import com.example.tasklistapp.data.response.RequestResponseItem
import com.example.tasklistapp.data.response.RequestUpdate
import com.example.tasklistapp.data.response.ResponseUpdate
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    @GET("requests")
    fun getListTasks(
    ): Call<List<RequestResponseItem>>

    @GET("requests/{id}")
    fun getDetailRequests(
        @Path("id") id: Int
    ): Call<List<RequestResponseItem>>

    @PUT("requests/{id}")
    fun updateRequestStatus(
        @Path("id") id: Int,
        @Body updatedRequest: RequestUpdate
    ): Call<ResponseUpdate>

    @POST("requests")
    fun createRequest(
        @Body request: RequestBody
    ): Call<ResponseUpdate>
}