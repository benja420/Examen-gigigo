package com.monolabs.benja.examengigigo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;
import java.io.FileOutputStream;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String latitud="";
    private String longitud="";
    private String nombrelugar="";
    private String ciudad="";
    private Button compartir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        compartir=(Button)findViewById(R.id.compartir);

        Bundle bundle= new Bundle(getIntent().getExtras());
        latitud= bundle.getString("latitud");
        longitud=bundle.getString("longitud");
        ciudad=bundle.getString("ciudad");
        nombrelugar=bundle.getString("nombrelugar");

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        compartir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //https://www.facebook.com/benjamin.zazueta.50
//                Uri uri = Uri.parse("https://www.facebook.com/benjamin.zazueta.50");
//                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
//                intent.setType("text/plain");
//                intent.putExtra(Intent.EXTRA_TEXT, "nombrelugar + ciudad");
//                //intent.setPackage("com.facebook.katana");
//                startActivity(Intent.createChooser(intent, "Share with")); http://maps.gstatic.com/mapfiles/place_api/icons/restaurant-71.png

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "El mejor blog de android https://maps.google.com/maps/contrib/108261801381770772582/photos\\\"\\u003eHotel Ritz\\u003c/a\\u003e");
               // intent.setPackage("com.facebook.katana");
                startActivity(intent);

//                try {
//
//                    File file = new File(getCacheDir(), bitmap + ".png");
//                    FileOutputStream fOut = null;
//                    fOut = new FileOutputStream(file);
//                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
//                    fOut.flush();    fOut.close();
//                    file.setReadable(true, false);
//                    final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
//                    intent.setType("image/png");    startActivity(intent);}
//                    catch (Exception e) {
//                    e.printStackTrace();}

//                final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.putExtra(Intent.EXTRA_TITLE,"Hola ");
//                intent.putExtra(Intent.EXTRA_TEXT, "Compatiendo desde android");
////                imagee.buildDrawingCache();
////                Bitmap image= imagee.getDrawingCache();
////                extras.putParcelable(Intent.EXTRA_STREAM, image);
////                intent.setType("image/*");
//                startActivity(intent);


            }

        });


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        try {
          //  double lat = Double.valueOf(latitud);
           //

            String var=latitud.substring(0, latitud.length() - 1);
            String var2 =longitud.substring(0, longitud.length() - 1);
            double lat = Double.parseDouble(var);
            double longi = Double.valueOf(var2);
            LatLng sydney = new LatLng(lat,longi);
            mMap.addMarker(new MarkerOptions().position(sydney).title(nombrelugar + ciudad));
            mMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder()
                    .target(sydney)
                    .zoom(15.5f)
                    .build()));
        }catch (Exception e)
        {
            e.toString();
        }



    }
}
