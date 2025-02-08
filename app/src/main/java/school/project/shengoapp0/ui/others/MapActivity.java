package school.project.shengoapp0.ui.others;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import school.project.shengoapp0.R;

public class MapActivity extends AppCompatActivity {
    MapView mapView;
    private static final int REQUEST_LOCATION_PERMISSION = 1;

    private void requestPermissionLocatoin(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        }else{
            enableMyLocationOverlay();
        }
    }

    private void enableMyLocationOverlay() {
        GpsMyLocationProvider provider = new GpsMyLocationProvider(this);
        provider.addLocationSource(LocationManager.NETWORK_PROVIDER);//this is optional i think it enables to use the network address
        MyLocationNewOverlay myLocationNewOverlay = new MyLocationNewOverlay(provider, mapView);
        myLocationNewOverlay.enableMyLocation();

        //enabling tracking
        myLocationNewOverlay.enableFollowLocation();

        Bitmap scaledIcon = Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(getResources(), R.drawable.current_location)
                ,90, 100,
                false
        );


        myLocationNewOverlay.setPersonIcon(scaledIcon);

        mapView.getOverlays().add(myLocationNewOverlay);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_PERMISSION){
            if (grantResults.length >  0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                enableMyLocationOverlay();
            }else{
                Toast.makeText(this, "You have to enable for best Experience", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_map);

        // Setting the User Agent
        Configuration.getInstance().setUserAgentValue(getPackageName());

        mapView = findViewById(R.id.mapView);
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setBuiltInZoomControls(true);
        mapView.setMultiTouchControls(true);

        Bitmap scaledIcon = Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(getResources(), R.drawable.lawyer_office),
                140, // Desired width in pixels
                150, // Desired height in pixels
                false); // filter

        Drawable d = new BitmapDrawable(getResources(), scaledIcon);

        GeoPoint startPoint = new GeoPoint(9.0227, 38.7469); // Example: AA
        mapView.getController().setCenter(startPoint);
        mapView.getController().setZoom(12.0);

        GeoPoint airportPoint = new GeoPoint(8.983801950089944, 38.796310187664986);
        Marker airportMarker = new Marker(mapView);
        airportMarker.setPosition(airportPoint);
        airportMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        airportMarker.setTitle("Bole International Airport");

        airportMarker.setIcon(d);
        mapView.getOverlays().add(airportMarker);

        GeoPoint meskelPoint = new GeoPoint(9.010529991323786, 38.761093927798484);
        Marker meskelMarker = new Marker(mapView);
        meskelMarker.setPosition(meskelPoint);
        meskelMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        meskelMarker.setIcon(d);
        meskelMarker.setTitle("Meskel Square");
        mapView.getOverlays().add(meskelMarker);

        requestPermissionLocatoin();

    }
}