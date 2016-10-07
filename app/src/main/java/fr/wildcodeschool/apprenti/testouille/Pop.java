package fr.wildcodeschool.apprenti.testouille;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class Pop extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pop);

        final MediaPlayer mp = MediaPlayer.create(this, R.raw.punch);

        Button kill = (Button)findViewById(R.id.button2);
        kill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        mp.start();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Intent pass = (new Intent(Pop.this,MapsActivity.class));
                        startActivity(pass);
                    }
                }, mp.getDuration());
            }
        });
    }
}
