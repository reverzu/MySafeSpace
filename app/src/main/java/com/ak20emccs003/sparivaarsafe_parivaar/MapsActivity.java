package com.ak20emccs003.sparivaarsafe_parivaar;
//
//import androidx.annotation.NonNull;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//import androidx.fragment.app.FragmentActivity;
//
//import android.Manifest;
//import android.annotation.SuppressLint;
//import android.app.PendingIntent;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.content.pm.PackageManager;
//import android.graphics.Color;
//import android.location.Address;
//import android.location.Geocoder;
//import android.location.LocationListener;
//import android.location.LocationManager;
//import android.os.Build;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Toast;
//
//import com.google.android.gms.location.Geofence;
//import com.google.android.gms.location.GeofencingClient;
//import com.google.android.gms.location.GeofencingRequest;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.model.CircleOptions;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.Marker;
//import com.google.android.gms.maps.model.MarkerOptions;
//import com.ak20emccs003.sparivaarsafe_parivaar.databinding.ActivityMapsBinding;
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//
//import java.io.IOException;
//import java.util.ArrayList;
//
//public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener, GoogleMap.OnInfoWindowLongClickListener {
//    GeofenceBroadcastReceiver geofenceBroadcastReceiver=new GeofenceBroadcastReceiver();
//    private GeofencingClient geofencingClient;
//    private static final String TAG = "MapsActivity";
//    private GeoFenceHelper geoFenceHelper;
//    private GoogleMap mMap;
//    private ActivityMapsBinding binding;
//    private double latitude;
//    private double longitude;
//    private double radius=10;
//    private double zoom;
//    private int FINE_LOCATION_ACCESS_REQUEST_CODE=10001;
//    private int BACKGROUND_LOCATION_ACCESS_REQUEST_CODE=10002;
//    private String GEOFENCE_ID="GEOFENCE_ID";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//
//        binding = ActivityMapsBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//
//        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
//        geofencingClient= LocationServices.getGeofencingClient(this);
//        geoFenceHelper=new GeoFenceHelper(this);
//    }
//
//
//    @SuppressLint("MissingPermission")
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//        latitude=27.5530;
//        longitude=76.6346;
//        radius=500.0;
//        zoom=16.5-(radius/1000);
//        LatLng alwarRaj = new LatLng(latitude, longitude);
//        mMap.addMarker(new MarkerOptions().position(alwarRaj).title("Marker in Alwar"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(alwarRaj));
//        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(alwarRaj, (float)zoom));
//
//        mMap.setMapType(4);
//        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//            @Override
//            public void onMapClick(@NonNull LatLng latLng) {
//                mMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));
//                Geocoder geocoder=new Geocoder(MapsActivity.this);
//                try {
//                    ArrayList<Address> arrAdr=(ArrayList<Address>) geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
//                        Log.d("Address: ", arrAdr.get(0).getAddressLine(0));
//                }catch(IOException e){
//                    e.printStackTrace();
//                }
//            }
//        });
//        mMap.setOnMapLongClickListener(this);
//        mMap.setOnInfoWindowLongClickListener(this);
//
//    enableUserLocation();
//    }
//    @SuppressLint("MissingPermission")
//    private void enableUserLocation(){
//
//    if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
//        mMap.setMyLocationEnabled(true);
//    }else{
//        if(ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)){
//            //show user why we need the permission and then ask for it
//            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, FINE_LOCATION_ACCESS_REQUEST_CODE);
//        }else{
//            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, FINE_LOCATION_ACCESS_REQUEST_CODE);
//        }
//    }
//    }
//
//    @SuppressLint("MissingPermission")
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == FINE_LOCATION_ACCESS_REQUEST_CODE) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                //we have the permission
//                mMap.setMyLocationEnabled(true);
//            } else {
//                //we do not have the permission
//
//            }
//        }
//
//        if (requestCode == BACKGROUND_LOCATION_ACCESS_REQUEST_CODE) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(this, "You can add GeoFences...", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(this, "Background Permissions are necessary add GeoFences...", Toast.LENGTH_SHORT).show();
//
//            }
//        }
//    }
//
//    static LatLng coordinates(double latitude, double longitude){
//        return new LatLng(latitude, longitude);
//    }
//
//    @Override
//    public void onMapLongClick(@NonNull LatLng latLng) {
//        if(Build.VERSION.SDK_INT>=29){
//            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION)==PackageManager.PERMISSION_GRANTED){
//                mMap.clear();
//                addMarker(latLng);
//                addCircle(latLng, (float) radius);
//                addGeoFence(latLng, (float) radius);
//            }else{
//                if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION)){
//                    //show user why we need the permission and then ask for it
//                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION}, BACKGROUND_LOCATION_ACCESS_REQUEST_CODE);
//                }else{
//                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION}, BACKGROUND_LOCATION_ACCESS_REQUEST_CODE);
//                }
//            }
//        }else{
//            mMap.clear();
//            addMarker(latLng);
//            addCircle(latLng, (float) radius);
//            addGeoFence(latLng, (float) radius);
//        }
//    }
//    private void addGeoFence(LatLng latLng, float radius){
//        Geofence geofence=geoFenceHelper.getGeofence(GEOFENCE_ID, latLng, radius, Geofence.GEOFENCE_TRANSITION_DWELL);
//        GeofencingRequest geofencingRequest=geoFenceHelper.getGeofencingRequest(geofence);
//        PendingIntent pendingIntent=geoFenceHelper.getPendingIntent();
//        geofencingClient.addGeofences(geofencingRequest, pendingIntent)
//        .addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void unused) {
//                Log.d(TAG, "On Success: GeoFence Added Successfully");
//            }
//        })
//        .addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                String errorMessage=geoFenceHelper.getErrorString(e);
//                Log.d(TAG, "On Failure: "+errorMessage);
//            }
//        });
//    }
//    private void addMarker(LatLng latLng){
//        MarkerOptions markerOptions=new MarkerOptions().position(latLng);
//        mMap.addMarker(markerOptions);
//    }
//    private void addCircle(LatLng latLng, float radius){
//        CircleOptions circleOptions=new CircleOptions();
//        circleOptions.center(latLng);
//        circleOptions.radius(radius);
//        circleOptions.strokeColor(Color.RED);
//        circleOptions.fillColor(R.color.gray_circle);
//        mMap.addCircle(circleOptions);
//    }
//
////    @Override
////    protected void onStart() {
////        super.onStart();
////        IntentFilter intentFilter=new IntentFilter();
////        intentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);
////        this.registerReceiver(myReceiver, intentFilter);
////    }
////
////    @Override
////    protected void onStop() {
////        super.onStop();
////        this.unregisterReceiver(myReceiver);
////    }
//
//    @Override
//    public void onInfoWindowLongClick(@NonNull Marker marker) {
//
//    }
//}
//

//
//import androidx.annotation.NonNull;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//import androidx.fragment.app.FragmentActivity;
//
//import android.Manifest;
//import android.annotation.SuppressLint;
//import android.app.PendingIntent;
//import android.content.Context;
//import android.content.pm.PackageManager;
//import android.graphics.Color;
//import android.location.Location;
//import android.location.LocationManager;
//import android.os.Build;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AutoCompleteTextView;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.RelativeLayout;
//import android.widget.Toast;
//
//import com.google.android.gms.location.Geofence;
//import com.google.android.gms.location.GeofencingClient;
//import com.google.android.gms.location.GeofencingRequest;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.model.CircleOptions;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.MarkerOptions;
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.android.libraries.places.api.Places;
//import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
//import com.google.android.libraries.places.api.model.Place;
//import com.google.android.libraries.places.api.net.PlacesClient;
//
//import java.util.Arrays;
//import java.util.List;
//
//public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {
//
//    private static final String TAG = "MapsActivity";
//
//    private GoogleMap mMap;
//    private SupportMapFragment mapFragment;
//    private GeofencingClient geofencingClient;
//    private GeofenceHelper geofenceHelper;
//    LatLng currentLatLng;
//    LatLng destinationPlace;
//    private float GEOFENCE_RADIUS = 200;
//    private String GEOFENCE_ID = "SOME_GEOFENCE_ID";
//    private int FINE_LOCATION_ACCESS_REQUEST_CODE = 10001;
//    private int BACKGROUND_LOCATION_ACCESS_REQUEST_CODE = 10002;
//
//
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_maps);
////        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
////
////        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
////        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
////                .findFragmentById(R.id.map);
////        mapFragment.getMapAsync(this);
//
//        geofencingClient = LocationServices.getGeofencingClient(this);
//        geofenceHelper = new GeofenceHelper(this);
//        // Initialize the Places client
//    }
//
//
//
//    /**
//     * Manipulates the map once available.
//     * This callback is triggered when the map is ready to be used.
//     * This is where we can add markers or lines, add listeners or move the camera. In this case,
//     * we just add a marker near Sydney, Australia.
//     * If Google Play services is not installed on the device, the user will be prompted to install
//     * it inside the SupportMapFragment. This method will only be triggered once the user has
//     * installed Google Play services and returned to the app.
//     */
//    @SuppressLint("MissingPermission")
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//        // Assuming mMap is a GoogleMap object
//        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//
//        int padding = 10;
//
//        // Set the padding for the map
//        mMap.setPadding(0, 0, padding, padding);
//
//        // Enable the "My Location" button and position it in the bottom right corner
//        mMap.getUiSettings().setMyLocationButtonEnabled(true);
//        View locationButton = ((View) mapFragment.getView().findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
//        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
//        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
//        rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
//        rlp.setMargins(0, 0, padding, padding);
//        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        @SuppressLint("MissingPermission")
//        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//
//        // Check if the location is not null
//        if (location != null) {
//            double latitude = location.getLatitude();
//            double longitude = location.getLongitude();
//
//            // Create a LatLng object with the current coordinates
//            LatLng currentLatLng = new LatLng(latitude, longitude);
//
//            // Move the camera to the current location
//            mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLatLng));
//            mMap.animateCamera(CameraUpdateFactory.zoomTo(16));
//
//            enableUserLocation();
//            mMap.setOnMapLongClickListener(this);
//        }
//    }
//
//    @SuppressLint("MissingPermission")
//    private void enableUserLocation(){
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            mMap.setMyLocationEnabled(true);
//        } else {
//            //Ask for permission
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
//                //We need to show user a dialog for displaying why the permission is needed and then ask for the permission...
//                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, FINE_LOCATION_ACCESS_REQUEST_CODE);
//            } else {
//                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, FINE_LOCATION_ACCESS_REQUEST_CODE);
//            }
//        }
//    }
//
//    @SuppressLint({"MissingSuperCall", "MissingPermission"})
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        if (requestCode == FINE_LOCATION_ACCESS_REQUEST_CODE) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                //We have the permission
//                mMap.setMyLocationEnabled(true);
//            } else {
//                //We do not have the permission..
//
//            }
//        }
//
//        if (requestCode == BACKGROUND_LOCATION_ACCESS_REQUEST_CODE) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                //We have the permission
//                Toast.makeText(this, "You can add geofences...", Toast.LENGTH_SHORT).show();
//            } else {
//                //We do not have the permission..
//                Toast.makeText(this, "Background location access is neccessary for geofences to trigger...", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//
//
//    @Override
//    public void onMapLongClick(LatLng latLng) {
//        if (Build.VERSION.SDK_INT >= 29) {
//            //We need background permission
//            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//                handleMapLongClick(latLng);
//            } else {
//                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION)) {
//                    //We show a dialog and ask for permission
//                    ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_BACKGROUND_LOCATION}, BACKGROUND_LOCATION_ACCESS_REQUEST_CODE);
//                } else {
//                    ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_BACKGROUND_LOCATION}, BACKGROUND_LOCATION_ACCESS_REQUEST_CODE);
//                }
//            }
//
//        } else {
//            handleMapLongClick(latLng);
//        }
//
//    }
//
//
//    private void handleMapLongClick(LatLng latLng) {
//        mMap.clear();
////        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
////        @SuppressLint("MissingPermission") Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
////
////        // Check if the location is not null
////        if (location != null) {
////            double latitude = location.getLatitude();
////            double longitude = location.getLongitude();
////
////            // Create a LatLng object with the current coordinates
////            currentLatLng = new LatLng(latitude, longitude);
////        }
//        addMarker(latLng);
//        addCircle(latLng, GEOFENCE_RADIUS);
//        addGeofence(latLng, GEOFENCE_RADIUS);
//    }
//
//    @SuppressLint("MissingPermission")
//    private void addGeofence(LatLng latLng, float radius) {
//
//        Geofence geofence = geofenceHelper.getGeofence(GEOFENCE_ID, latLng, radius, Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_DWELL | Geofence.GEOFENCE_TRANSITION_EXIT);
//        GeofencingRequest geofencingRequest = geofenceHelper.getGeofencingRequest(geofence);
//        PendingIntent pendingIntent = geofenceHelper.getPendingIntent();
//
//        geofencingClient.addGeofences(geofencingRequest, pendingIntent)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Log.d(TAG, "onSuccess: Geofence Added...");
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        String errorMessage = geofenceHelper.getErrorString(e);
//                        Log.d(TAG, "onFailure: " + errorMessage);
//                    }
//                });
//    }
//
//    private void addMarker(LatLng latLng) {
//        MarkerOptions markerOptions = new MarkerOptions().position(latLng);
//        mMap.addMarker(markerOptions);
//    }
//

//    }
//}

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.ak20emccs003.sparivaarsafe_parivaar.communication.ServerThread;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {

    private static final String TAG = "MapsActivity";

    private GoogleMap mMap;
    private GeofencingClient geofencingClient;
    private GeofenceHelper geofenceHelper;
    private SupportMapFragment mapFragment;
    private float GEOFENCE_RADIUS = 200;
    private String GEOFENCE_ID = "SOME_GEOFENCE_ID";
    SearchView searchView;
    private int FINE_LOCATION_ACCESS_REQUEST_CODE = 10001;
    private int BACKGROUND_LOCATION_ACCESS_REQUEST_CODE = 10002;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        ServerThread serverThread = new ServerThread();
        serverThread.start();
        // initializing our search view.
//        searchView = findViewById(R.id.idSearchView);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                // on below line we are getting the
//                // location name from search view.
//                String location = searchView.getQuery().toString();
//
//                // below line is to create a list of address
//                // where we will store the list of all address.
//                List<Address> addressList = null;
//
//                // checking if the entered location is null or not.
//                if (location != null || location.equals("")) {
//                    // on below line we are creating and initializing a geo coder.
//                    Geocoder geocoder = new Geocoder(MapsActivity.this);
//                    try {
//                        // on below line we are getting location from the
//                        // location name and adding that location to address list.
//                        addressList = geocoder.getFromLocationName(location, 1);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    // on below line we are getting the location
//                    // from our list a first position.
//                    Address address = addressList.get(0);
//
//                    // on below line we are creating a variable for our location
//                    // where we will add our locations latitude and longitude.
//                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
//
//                    // on below line we are adding marker to that position.
//                    mMap.addMarker(new MarkerOptions().position(latLng).title(location));
//
//                    // below line is to animate camera to that position.
//                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
//                }
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });

        geofencingClient = LocationServices.getGeofencingClient(this);
        geofenceHelper = new GeofenceHelper(this);
        mapFragment.getMapAsync(this);
    }


    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Assuming mMap is a GoogleMap object
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        View locationButton = ((View) mapFragment.getView().findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                locationButton.getLayoutParams();
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        layoutParams.setMargins(0, 200, 50, 0);


        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        @SuppressLint("MissingPermission")
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        // Check if the location is not null
        if (location != null) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();

            // Create a LatLng object with the current coordinates
            LatLng currentLatLng = new LatLng(latitude, longitude);

            // Move the camera to the current location
            mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLatLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(16));

            enableUserLocation();
            mMap.setOnMapLongClickListener(this);
        }
    }

    @SuppressLint("MissingPermission")
    private void enableUserLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            //Ask for permission
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                //We need to show user a dialog for displaying why the permission is needed and then ask for the permission...
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, FINE_LOCATION_ACCESS_REQUEST_CODE);
            } else {
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, FINE_LOCATION_ACCESS_REQUEST_CODE);
            }
        }
    }

    @SuppressLint({"MissingSuperCall", "MissingPermission"})
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == FINE_LOCATION_ACCESS_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //We have the permission
                mMap.setMyLocationEnabled(true);
            } else {
                //We do not have the permission..

            }
        }

        if (requestCode == BACKGROUND_LOCATION_ACCESS_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //We have the permission
                Toast.makeText(this, "You can add geofences...", Toast.LENGTH_SHORT).show();
            } else {
                //We do not have the permission..
                Toast.makeText(this, "Background location access is neccessary for geofences to trigger...", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        if (Build.VERSION.SDK_INT >= 29) {
            //We need background permission
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                handleMapLongClick(latLng);
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION)) {
                    //We show a dialog and ask for permission
                    ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_BACKGROUND_LOCATION}, BACKGROUND_LOCATION_ACCESS_REQUEST_CODE);
                } else {
                    ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_BACKGROUND_LOCATION}, BACKGROUND_LOCATION_ACCESS_REQUEST_CODE);
                }
            }

        } else {
            handleMapLongClick(latLng);
        }

    }

    private void handleMapLongClick(LatLng latLng) {
        mMap.clear();
        addMarker(latLng);
        addCircle(latLng, GEOFENCE_RADIUS);
        addGeofence(latLng, GEOFENCE_RADIUS);
    }

    @SuppressLint("MissingPermission")
    private void addGeofence(LatLng latLng, float radius) {

        Geofence geofence = geofenceHelper.getGeofence(GEOFENCE_ID, latLng, radius, Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_DWELL | Geofence.GEOFENCE_TRANSITION_EXIT);
        GeofencingRequest geofencingRequest = geofenceHelper.getGeofencingRequest(geofence);
        PendingIntent pendingIntent = geofenceHelper.getPendingIntent();

        geofencingClient.addGeofences(geofencingRequest, pendingIntent)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "onSuccess: Geofence Added...");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String errorMessage = geofenceHelper.getErrorString(e);
                        Log.d(TAG, "onFailure: " + errorMessage);
                    }
                });
    }

    private void addMarker(LatLng latLng) {
        MarkerOptions markerOptions = new MarkerOptions().position(latLng);
        mMap.addMarker(markerOptions);
    }

    private void addCircle(LatLng latLng, float radius) {
            CircleOptions circleOptions = new CircleOptions();
            circleOptions.center(latLng);
            circleOptions.radius(radius);
            circleOptions.strokeColor(Color.RED);
            circleOptions.fillColor(R.color.gray_circle);
            circleOptions.strokeWidth(6);
            mMap.addCircle(circleOptions);
    }
}
