package com.app.hassaniageoit;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.app.hassaniageoit.Cellules.ProjetsFormations;

public class HomeActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);

        final CardView ProjetFormationCard = findViewById(R.id.ProjetFormationID);
        CardView EventsCard = findViewById(R.id.EventsID);
        CardView DesignID = findViewById(R.id.DesignID);
        CardView MediaID = findViewById(R.id.MediaID);
        CardView SponsoringID = findViewById(R.id.SponsoringID);

        ProjetFormationCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent HomeIntent = new Intent(HomeActivity.this, ProjetsFormations.class);
                startActivity(HomeIntent);
            }
        });

}




}