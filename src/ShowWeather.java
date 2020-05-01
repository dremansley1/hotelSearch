import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;
import sun.net.www.http.HttpClient;

public class ShowWeather extends HttpClient {

    public JSONObject weather(String location) {

        String owmApiKey = "982807db125d34ad3ed93ab8903ed609";
        String requestAddr = "https://api.openweathermap.org/data/2.5/weather?q=" + location + "&mode=json&units=metric&cnt=7&appid=" + owmApiKey;
        JSONObject jsonObj = null;
        try {

            URL obj = new URL(requestAddr);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            int responseCode = con.getResponseCode();

            BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in .readLine()) != null) {
                response.append(inputLine);
            }

            jsonObj = new JSONObject(response.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonObj;
    }

}