package tp2.Exercice_1;

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
import java.util.List;

import tp2.Menu.MainActivity;

public class Exo1 extends AppCompatActivity {

    ListView listView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exo1);
        listView = (ListView)findViewById(R.id.listView);
        final SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        List<Sensor> sensorsList = sensorManager.getSensorList(Sensor.TYPE_ALL);

        List<List<String>> s = new ArrayList<>();

        ArrayList<String> init = new ArrayList<>();
        init.add("Nom du capteur");
        init.add("Type du capteur");
        init.add("Version du capteur");
        init.add("Resolution du capteur");
        init.add("Puissance du capteur");
        init.add("Vendeur du capteur");
        init.add("Valeur maximum du capteur");
        init.add("delais du capteur");
        s.add(init);

        for (Sensor sensor : sensorsList) {
            ArrayList<String> e = new ArrayList<>();
            e.add(sensor.getName());
            e.add(sensor.getType()+"");
            e.add(sensor.getVersion()+"");
            e.add(sensor.getResolution()+"");
            e.add(sensor.getPower()+"");
            e.add(sensor.getVendor());
            e.add(sensor.getMaximumRange()+"");
            e.add(sensor.getMinDelay()+"");
            s.add(e);
        }
        CapteurAdapteur adapter = new CapteurAdapteur(Exo1.this,s);
        listView.setAdapter(adapter);
    }

    public void RetourExo1(View view){
        Intent intention= new Intent(Exo1.this, MainActivity.class);
        startActivity(intention);
    }
}