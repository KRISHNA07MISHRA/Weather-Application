import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface weatherinterface {

    @GET("weather")
    fun getWeather(
        @Query("units") units:String,
        @Query("q") city: String,
        @Query("appid") appid: String

    ): Call<MyWeatherData>
}
