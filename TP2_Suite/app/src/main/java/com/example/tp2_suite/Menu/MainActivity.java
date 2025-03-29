package com.example.tp2_suite.Menu;

import android.Manifest;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.tp2_suite.R;

import com.example.tp2_suite.ex1.FirstActivity;
import com.example.tp2_suite.ex2.SecondActivity;
import pub.devrel.easypermissions.EasyPermissions;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private int READ_STORAGE_PERMISSION_REQUEST = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GridView simpleGrid;
        ArrayList<Item> birdList = new ArrayList<>();
        simpleGrid = (GridView) findViewById(R.id.grid);
        birdList.add(new Item("Exercices 8",R.drawable.exo8));
        birdList.add(new Item("Exercice 9",R.drawable.exo9));

        GridAdaptateur myAdapter=new GridAdaptateur(this,R.layout.adaptateur_grid_layout,birdList);
        simpleGrid.setAdapter(myAdapter);

        simpleGrid.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
            {
                Intent intention;
                switch (position){
                    case 0 :
                        intention= new Intent(MainActivity.this, FirstActivity.class);
                        startActivity(intention);
                        break;

                    case 1 :
                        intention= new Intent(MainActivity.this, SecondActivity.class);
                        startActivity(intention);
                        break;
                }
            }
        });

        String permission1 = Manifest.permission.ACCESS_FINE_LOCATION;
        String permission2 = Manifest.permission.ACCESS_COARSE_LOCATION;
        if (!EasyPermissions.hasPermissions(this, permission1)) {
            EasyPermissions.requestPermissions(this, "Our App Requires a permission to access your storage", READ_STORAGE_PERMISSION_REQUEST
                    , permission1);
        }

        if (!EasyPermissions.hasPermissions(this, permission2)) {
            EasyPermissions.requestPermissions(this, "Our App Requires a permission to access your storage", READ_STORAGE_PERMISSION_REQUEST
                    , permission2);
        }
    }
}