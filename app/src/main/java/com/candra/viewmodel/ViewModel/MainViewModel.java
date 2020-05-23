package com.candra.viewmodel.ViewModel;

import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.candra.viewmodel.MainActivity;
import com.candra.viewmodel.WeatherItem;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

// Kelas yang menjaga data activity menjadi lebih baik ketika terjadi perubahan pada orientation
public class MainViewModel extends ViewModel {
    ProgressBar candra;
    private MutableLiveData<ArrayList<WeatherItem>> listMutableLiveData = new MutableLiveData<>();

    public void setWeather(final String cities){
        final ArrayList<WeatherItem> list = new ArrayList<>();

        String apikey = "379d2a3252974d07bc7a54c259ca2187";
        String url = "https://api.openweathermap.org/data/2.5/group?id=" + cities + "&units=metric&appid=" + apikey; // merupakan url dari API

        AsyncHttpClient client = new AsyncHttpClient(); // melakukan request api
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                try {
                    // Parsing JSON
                    String result = new String(responseBody);
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray list1 = jsonObject.getJSONArray("list"); // Untuk mengambil data array

                    // Untuk mengambil data semuanya pada JSON objek
                    for (int i = 0; i < list1.length(); i++) {
                        JSONObject weather = list1.getJSONObject(i);
                        WeatherItem weatherItem = new WeatherItem();
                        weatherItem.setId(weather.getInt("id"));
                        weatherItem.setName(weather.getString("name"));
                        weatherItem.setCurrentWeather(weather.getJSONArray("weather").getJSONObject(0).getString("main"));
                        weatherItem.setDeskripsi(weather.getJSONArray("weather").getJSONObject(0).getString("description"));
                        double tempInKelvin = weather.getJSONObject("main").getDouble("temp");
                        double tempInCelcius = tempInKelvin - 273;
                        weatherItem.setTemperature(new DecimalFormat("##.##").format(tempInCelcius));
                        list.add(weatherItem);
                    }
                    // set data ke adapter
                    // dan Kita bisa menyisipkan perubahan yang terjadi dengan postValue(). Jadi secara realtime MutableLiveData akan menerima data yang baru.
                    // Lalu apa bedanya antara postValue(), getValue() dan setValue().
                    listMutableLiveData.postValue(list);
                } catch (JSONException e) {
                    Log.d("Kesalahan",e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("Terjadi Error",error.getMessage());
                String errorMessage = "";
                switch (statusCode){
                    case 400:
                        errorMessage = statusCode + " : Server tidak mengerti request yang dikirimkan ";
                        break;
                    case 401:
                        errorMessage = statusCode + " : Request membutuhkan authorization ";
                        break;
                    case 403:
                        errorMessage = statusCode + " : Server mengerti request dari client tapi menolak memprosesnya ";
                        break;
                    case 404:
                        errorMessage = statusCode + " : Resource yang diminta tidak ditemukan ";
                        break;
                    case 500:
                        errorMessage = statusCode + " : Server mengalami kesalahan yang tidak diketahui saat melakukan pemrosesan terhadap request yang dikirimkan oleh client ";
                        break;
                }
                Log.d("Terjadi kesalahan ", errorMessage);

            }
        });
    }


    public void showLoading(boolean state){
        if (state){
            candra.setVisibility(View.VISIBLE);
        }else {
            candra.setVisibility(View.GONE);
        }
    }


    public LiveData<ArrayList<WeatherItem>> getListMutableLiveData() {
        return listMutableLiveData;
    }
}
    // Catatan :
        // Ada perbedaan dengan LiveData dengan MutableLiveData
        // LiveData bersifat Read Only dan MutableLiveData bersifat dapat diubah valuenya

        //setValue()
            //Menetapkan sebuah nilai dari LiveData. Jika ada observer yang aktif, nilai akan dikirim kepada mereka. Metode ini harus dipanggil dari main thread.

        //postValue()
            // Posting tugas ke main thread untuk menetapkan nilai yang diberikan dari background thread, seperti melalui dalam kelas MainViewModel.
            // Jika Anda memanggil metode ini beberapa kali sebelum main thread menjalankan tugas yang di-posting, hanya nilai terakhir yang akan dikirim.

        // getValue()
            // Mendapatkan nilai dari sebuah LiveData
    // Intinya adalah setValue() bekerja di main thread dan postValue() bekerja di background thread.