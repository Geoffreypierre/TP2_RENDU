package tp2.Exercice_6;

import com.example.tp2.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import tp2.Menu.MainActivity;

public class ExoProximiter extends AppCompatActivity  {

    ConstraintLayout cl;
    TextView ProximitySensor, data;
    ImageView iv;
    SensorManager mySensorManager;
    Sensor myProximitySensor;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exo_proximiter);
        ProximitySensor = (TextView) findViewById(R.id.proximitySensor);
        data = (TextView) findViewById(R.id.data);
        iv = (ImageView) findViewById(R.id.imageView);
        mySensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        myProximitySensor = mySensorManager.getDefaultSensor(
                Sensor.TYPE_PROXIMITY);
        if (myProximitySensor == null) {
            ProximitySensor.setText("No Proximity Sensor!");
        } else {
            mySensorManager.registerListener(proximitySensorEventListener,myProximitySensor,SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected  void onPause(){
        super.onPause();
        mySensorManager.unregisterListener(proximitySensorEventListener,myProximitySensor);
    }

    @Override
    protected void onResume(){
        super.onResume();
        mySensorManager.registerListener(proximitySensorEventListener,myProximitySensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    SensorEventListener proximitySensorEventListener
            = new SensorEventListener() {
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
                if (event.values[0] == 0) {
                    data.setText("Near");
                    iv.setImageDrawable(getDrawable(R.drawable.near));
                } else {
                    data.setText("Away");
                    iv.setImageDrawable(getDrawable(R.drawable.far));
                }
            }
        }
    };

    public void RetourExo6(View view) {
        Intent intention = new Intent(ExoProximiter.this, MainActivity.class);
        startActivity(intention);
    }
}