package tp2.Exercice_5;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraCharacteristics;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import tp2.Menu.MainActivity;
import com.example.tp2.R;

public class Exo5 extends AppCompatActivity implements SensorEventListener {
    private int m;
    private boolean tampon;
    private float[] tab;
    private Sensor accelerometre, linearAcc;
    private SensorManager mSensorManager;
    private ImageView image;
    private CameraManager camManager;
    private String cameraId;
    private boolean lampe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exo5);

        // Initialisation des variables
        m = 0;
        tampon = true;
        tab = new float[10];
        for (int i = 0; i < 10; ++i) tab[i] = 10000;

        image = findViewById(R.id.imageView2);
        image.setImageResource(R.drawable.eteindre);

        // Initialisation du gestionnaire de caméra
        camManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            cameraId = camManager.getCameraIdList()[0]; // Récupère l'ID de la caméra
        } catch (CameraAccessException e) {
            Toast.makeText(this, "Problème d'accès au flash", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float x = sensorEvent.values[0];
        float y = sensorEvent.values[1];
        float z = sensorEvent.values[2];

        // Calcul de l'intensité du mouvement
        tab[m % 10] = Math.abs(x) + Math.abs(y) + Math.abs(z);
        m++;

        float somme = 0;
        for (int i = 0; i < 10; ++i) {
            if (tab[i] != 10000) somme += tab[i];
        }

        float moyenne = somme / 10;

        // Si on détecte une secousse significative
        if (moyenne > 15) { // Augmenté pour éviter les faux déclenchements
            if (tampon) {
                lampe = !lampe; // Toggle lampe

                // Activation/Désactivation du flash
                if (camManager != null && cameraId != null) {
                    try {
                        if (camManager.getCameraCharacteristics(cameraId).get(CameraCharacteristics.FLASH_INFO_AVAILABLE)) {
                            camManager.setTorchMode(cameraId, lampe);
                            image.setImageResource(lampe ? R.drawable.allumer : R.drawable.eteindre);
                            Toast.makeText(this, lampe ? "Lampe allumée" : "Lampe éteinte", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "L'appareil ne possède pas de flash", Toast.LENGTH_LONG).show();
                        }
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }
                tampon = false; // Empêcher les doubles déclenchements
            }
        } else {
            tampon = true;
        }
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
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION) != null) {
            linearAcc = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
            mSensorManager.registerListener(this, linearAcc, SensorManager.SENSOR_DELAY_UI);
        } else {
            Toast.makeText(this, "Accéléromètre non disponible", Toast.LENGTH_LONG).show();
            if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
                accelerometre = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                mSensorManager.registerListener(this, accelerometre, SensorManager.SENSOR_DELAY_UI);
            } else {
                Toast.makeText(this, "Aucun capteur de mouvement disponible", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void RetourExo5(View view) {
        Intent intention = new Intent(Exo5.this, MainActivity.class);
        startActivity(intention);
    }
}
