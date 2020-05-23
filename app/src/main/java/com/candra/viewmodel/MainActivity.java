package com.candra.viewmodel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.candra.viewmodel.Adapter.WeatherAdapter;
import com.candra.viewmodel.ViewModel.MainViewModel;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private WeatherAdapter adapter;
     private EditText editCity;
    private ProgressBar progressBar;
    private Button btnCity;
    private RecyclerView candra;
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        candra = findViewById(R.id.recyclerView);
        editCity = findViewById(R.id.editCity);
        btnCity = findViewById(R.id.btnCity);
        btnCity.setOnClickListener(this);
        progressBar = findViewById(R.id.progressBar);

        candra.setLayoutManager(new LinearLayoutManager(this));
        adapter = new WeatherAdapter();
        adapter.notifyDataSetChanged();
        candra.setAdapter(adapter);

        // Sebuah metode yang menyambungkan kelas view model dengan activity
        mainViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MainViewModel.class);

        // Jika Anda perhatikan kode di atas, weatherItems akan selalu diperbarui secara realtime sesuai dengan perubahan yang ada di kelas ViewModel.
        // Namun jika getWeather tidak dipanggil saat melakukan observe getWeathers() maka nilai weatherItems juga tidak akan ada perubahan.
        // Jadi cara mendapatkan value dari sebuah LiveData harus dilakukan dengan cara meng-observe LiveData tersebut.
        // Dan proses ini dilakukan secara asynchronous.
        mainViewModel.getListMutableLiveData().observe(this, new Observer<ArrayList<WeatherItem>>() {
            @Override
            public void onChanged(ArrayList<WeatherItem> weatherItems) {
                if (weatherItems != null ){
                    adapter.setCandra(weatherItems);
                    mainViewModel.showLoading(false);
                }
            }
        });
    }
        @Override
        public void onClick (View v){
            if (v.getId() == R.id.btnCity) {
                String city = editCity.getText().toString();
                if (TextUtils.isEmpty(city)) return;

//                showLoading(true);
                mainViewModel.setWeather(city);
                mainViewModel.showLoading(false);
            }
        }

    private void showLoading(boolean state){
        if (state){
            progressBar.setVisibility(View.VISIBLE);
        }else {
            progressBar.setVisibility(View.GONE);
        }
    }
    }
