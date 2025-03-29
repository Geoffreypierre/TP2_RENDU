package tp2.Exercice_3;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import tp2.Menu.MainActivity;
import com.example.tp2.R;

public class Exo3 extends AppCompatActivity implements SensorEventListener {

    private int m ;
    private float[] tab;
    private ConstraintLayout color;
    private TextView txt;

    private Sensor linearAcc;
    private Sensor accelerometre;
    private SensorManager mSensorManager;

    private float vert,noir,rouge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exo3);
        tab = new float[10];
        for(int i = 0 ; i < 10 ; ++i)
            tab[i] = 10000;
        m = 0;
        txt = (TextView) findViewById(R.id.textView);
        color = (ConstraintLayout) findViewById(R.id.fond) ;
        vert = 2f;
        noir = 4f;
        rouge = 6f;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float x = sensorEvent.values[0];
        float y = sensorEvent.values[1];
        float z = sensorEvent.values[2];
        tab[m%10] = Math.abs(x) + Math.abs(y) + Math.abs(z);
        ++m;
        float somme = 0;
        for(int i = 0 ; i < 10 ;++i)
            if(tab[i] != 10000)
                somme = tab[i] + somme;
        float moyenne = somme/10;
        if(moyenne < vert )
            color.setBackgroundColor(Color.parseColor("#34eb8f"));
        else if(moyenne < noir)
            color.setBackgroundColor(Color.parseColor("#474080"));
        else if(moyenne < rouge)
            color.setBackgroundColor(Color.parseColor("#ff1f57"));
        txt.setText(Float.toString(moyenne));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(this, accelerometre);
        mSensorManager.unregisterListener(this, linearAcc);
        super.onPause();
    }
    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION) !=null){
            linearAcc = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
            mSensorManager.registerListener(this, linearAcc, SensorManager.SENSOR_DELAY_NORMAL);
        }
        else {
            Toast.makeText(this,"Il vous manque le linéaire acceleromètre", Toast.LENGTH_LONG).show();
            if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) !=null){
                accelerometre = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                mSensorManager.registerListener(this, accelerometre, SensorManager.SENSOR_DELAY_NORMAL);
            }
            else
                Toast.makeText(this,"Il vous manque l'acceleromètre", Toast.LENGTH_LONG).show();
        }
    }

    public void RetourExo3(View view){
        Intent intention= new Intent(Exo3.this, MainActivity.class);
        startActivity(intention);
    }
}