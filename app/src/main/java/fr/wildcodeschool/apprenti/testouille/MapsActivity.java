package fr.wildcodeschool.apprenti.testouille;

import android.*;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private DatabaseReference mDatabase;
    private HashMap proute = new HashMap();
    public static int haaa=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Toast.makeText(this,"prout "+proute.size()+"/5",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);

        // Retrieve caches from firebase DB and place them onto the map

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("geocaches").addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        Iterable<DataSnapshot> caches = dataSnapshot.getChildren();
                        for (DataSnapshot snap : caches) {
                            Cache cache = snap.getValue(Cache.class);
                            mMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(cache.getLat(), cache.getLon()))
                                    .snippet(cache.getHint())
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.interro))
                            );
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w("marker error", databaseError.toException());
                    }
                });
        float zoom = 16;
        LatLng toulouse = new LatLng(43.601253, 1.442236);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(toulouse,zoom));

        // musique dents de la mer
        Intent svc=new Intent(this, Music.class);
        startService(svc);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            // Get LocationManager object from System Service LOCATION_SERVICE
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);


            // Create a criteria object to retrieve provider
            Criteria criteria = new Criteria();

            // Get the name of the best provider
            String provider = locationManager.getBestProvider(criteria, true);

            // Get Current Location
            Location myLocation = locationManager.getLastKnownLocation(provider);

            // Create a LatLng object for the current location
                if(myLocation!=null) {
                    LatLng latLng = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());

                    // Show the current location in Google Map
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

                    // Zoom in the Google Map
                    googleMap.animateCamera(CameraUpdateFactory.zoomTo(14));
                }
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Log.i("requestCode", Integer.toString(requestCode));
        if (requestCode == 1) {
            if (permissions.length == 1 &&
                    permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION) &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                mMap.setMyLocationEnabled(true);
            } else {
                LatLng toulouse = new LatLng(43.601253, 1.442236);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(toulouse));
            }
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {




                 marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.valide));


                Intent ficheIntent = new Intent(MapsActivity.this,HintActivity.class);
                ficheIntent.putExtra(Constants.IMG_URL, marker.getSnippet());
                ficheIntent.putExtra("hashmap",proute);
                compteur(marker.getSnippet());
                startActivity(ficheIntent);


        return false;
    }
    public void compteur(String snipute) {
//if(haaa>=1){
//        Intent marcel = new Intent();
//        marcel =getIntent();
//        HashMap bigre = (HashMap)marcel.getSerializableExtra("hashmap");
//        proute=bigre;}else {
//    haaa++;
//        }
        String g1 = "http://coeurro.com/hackathon/IMG_20161005_122859.jpg";
        String g2 = "http://coeurro.com/hackathon/IMG_20161005_122642.jpg";
        String g3 = "http://coeurro.com/hackathon/IMG_20161005_121930.jpg";
        String g4 = "http://coeurro.com/hackathon/IMG_20161005_121447.jpg";
        String g5 = "http://coeurro.com/hackathon/IMG_20161005_120207.jpg";

        if (snipute.equals(g1)) {
            proute.put(1, g1);
        } else if (snipute.equals(g2)) {
            proute.put(2, g2);
        } else if (snipute.equals(g3)) {
            proute.put(3, g3);
        } else if (snipute.equals(g4)) {
            proute.put(4, g4);
        } else if (snipute.equals(g5)) {
            proute.put(5, g5);
        }



        if(proute.containsKey(1)&&proute.containsKey(2)&&proute.containsKey(3)&&proute.containsKey(4)&&proute.containsKey(5)){
            Intent pitent = new Intent(MapsActivity.this, VictoryActivity.class);
            startActivity(pitent);
        }




    }
    protected void onPause(){
        super.onPause();
        Intent svc=new Intent(this, Music.class);
        stopService(svc);
    }
    protected void onResume(){
        super.onResume();
        if(proute.size()>=1){
        Toast.makeText(this,"prout "+proute.size()+"/5",Toast.LENGTH_SHORT).show();}
        startService(new Intent(this,Music.class));

    }

    protected void onDestroy(){
        super.onDestroy();
        stopService(new Intent(this,Music.class));

    }


}
