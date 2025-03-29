package tp2.Exercice_7;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import tp2.Menu.MainActivity;

import com.example.tp2.R;


import java.util.List;



public class Exo7 extends AppCompatActivity implements OnMapReadyCallback, LocationListener, EasyPermissions.PermissionCallbacks {
    private LocationManager locationManager;
    private TextView la;
    private TextView lo;
    private boolean t ;
    private GoogleMap mMap;
    private int READ_STORAGE_PERMISSION_REQUEST = 123;
    Marker markerName;
    LatLng myposition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exo7);
        t = true;
        la = (TextView) findViewById(R.id.latitude);
        lo = (TextView) findViewById(R.id.longitude);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.maps);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, this);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            la.setText(location.getLatitude() + "");
            lo.setText(location.getLongitude() + "");
            myposition = new LatLng(location.getLatitude(), location.getLongitude());
            if (markerName != null) {
                markerName.remove();
            }
            markerName = mMap.addMarker(new MarkerOptions()
                    .position(myposition)
                    .title("Ma position"));
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        Location loc = null;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (loc != null) {
                la.setText(loc.getLatitude() + " ");
                lo.setText(loc.getLongitude() + " ");
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(loc.getLatitude(), loc.getLongitude()), 10.0f));
                myposition = new LatLng(loc.getLatitude(), loc.getLongitude());
                markerName = mMap.addMarker(new MarkerOptions()
                        .position(myposition)
                        .title("Ma position"));
            } else {
                la.setText("0.0");
                lo.setText("0.0");
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(0.0, 0.0), 10.0f));
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if(EasyPermissions.somePermissionPermanentlyDenied(this, perms)){
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    public void RetourExo7(View view) {
        Intent intention = new Intent(Exo7.this, MainActivity.class);
        startActivity(intention);
    }
}