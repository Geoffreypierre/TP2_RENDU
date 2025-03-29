package tp2.Exercice_2;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.tp2.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.hardware.Sensor.*;

import tp2.Menu.MainActivity;

public class Exo2 extends AppCompatActivity {
    private SensorManager mSensorManager;
    private List<Sensor> sensorsList;
    private List<String> Listcapteur;
    private Map<Integer,String> capteursensort;
    private ListView listView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exo2);
        listView = (ListView)findViewById(R.id.listView);
        Listcapteur = new ArrayList<>();
        capteursensort = new HashMap<>();
        capteursensort.putIfAbsent(TYPE_ACCELEROMETER,"TYPE_ACCELEROMETER");
        capteursensort.putIfAbsent(TYPE_AMBIENT_TEMPERATURE,"TYPE_AMBIENT_TEMPERATURE");
        capteursensort.putIfAbsent(TYPE_GRAVITY,"TYPE_GRAVITY");
        capteursensort.putIfAbsent(TYPE_GYROSCOPE,"YPE_GYROSCOPE");
        capteursensort.putIfAbsent(TYPE_LIGHT,"TYPE_LIGHT");
        capteursensort.putIfAbsent(TYPE_LINEAR_ACCELERATION,"TYPE_LINEAR_ACCELERATION");
        capteursensort.putIfAbsent(TYPE_MAGNETIC_FIELD,"TYPE_MAGNETIC_FIELD");
        capteursensort.putIfAbsent(TYPE_PRESSURE,"TYPE_PRESSURE");
        capteursensort.putIfAbsent(TYPE_PROXIMITY,"TYPE_PROXIMITY");
        capteursensort.putIfAbsent(TYPE_RELATIVE_HUMIDITY,"TYPE_RELATIVE_HUMIDITY");
        capteursensort.putIfAbsent(TYPE_ROTATION_VECTOR,"TYPE_ROTATION_VECTOR");


        SensorManager mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorsList = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        for ( Integer i : capteursensort.keySet() ){
            int j = 0;
            boolean trouve = false;
            while (!trouve && j < sensorsList.size() ){
                if (sensorsList.get(j).getType()==i){trouve = true;}
                else{j++;}

            }
            if (!trouve){
                Listcapteur.add(capteursensort.get(i));
            }
        }
        Exo2Adaptateur adapter = new Exo2Adaptateur(Exo2.this,Listcapteur);
        listView.setAdapter(adapter);
    }

    public void RetourExo2(View view){
        Intent intention= new Intent(Exo2.this, MainActivity.class);
        startActivity(intention);
    }

}