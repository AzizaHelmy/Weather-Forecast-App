import com.example.weatherforecastapp.home.model.Forecast
import retrofit2.Response

interface WeatherRemoteDataSource{
  suspend  fun getCurrentWeather(model:Forecast)
}