package com.arrowsdashboard.mamatenderdash;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class map_activity extends AppCompatActivity {

    SupportMapFragment supportMapFragment ;
    FusedLocationProviderClient client ;
    double lat;
    double lng;
    String latt, lanng;
    Button conbtn;
    TextView addressmap;
    GoogleMap mmap ;
    String add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_map_activity);


        supportMapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        client = LocationServices.getFusedLocationProviderClient(this);
        addressmap = findViewById(R.id.addressmap);
        conbtn = findViewById(R.id.conbtn);

        check_permission();




        addressmap = findViewById(R.id.addressmap);
        conbtn = findViewById(R.id.conbtn);


        conbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add = addressmap.getText().toString();

                    Intent intent = new Intent();
                    intent.putExtra("editTextValue", add);
                    intent.putExtra("lng", lanng);
                    intent.putExtra("lat", latt);
                    //startActivityForResult(intent,1);
                    map_activity.this.setResult(RESULT_OK, intent);
                    map_activity.this.finish();

            }
        });
    }

    private void check_permission() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!(ActivityCompat.checkSelfPermission(map_activity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)){
            ActivityCompat.requestPermissions(map_activity.this,new String[]{Manifest
                    .permission.ACCESS_FINE_LOCATION},44);
        }
        else   if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
        }
        else {
            getCurrentLocation();
        }
    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {

        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @SuppressLint("MissingPermission")
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mmap = googleMap ;
                mmap.setMyLocationEnabled(true);
                //googleMap.setMyLocationEnabled(true);
                LocationManager locationManager = (LocationManager) map_activity.this.getSystemService(map_activity.LOCATION_SERVICE);
                List<String> providers = locationManager.getProviders(true);
                for (String provider : providers) {
                    locationManager.requestLocationUpdates(provider, 1000, 0,
                            new LocationListener() {
                                public void onLocationChanged(Location location) {

                                }

                                public void onProviderDisabled(String provider) {
                                }

                                public void onProviderEnabled(String provider) {
                                }

                                public void onStatusChanged(String provider,
                                                            int status, Bundle extras) {
                                }
                            });
                    Location location = locationManager.getLastKnownLocation(provider);
                    if (location != null) {

                        lat = location.getLatitude();
                        lng= location.getLongitude();

                        LatLng ltlng=new LatLng(lat,lng);
                        getAddress(ltlng);
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(ltlng,18));

                        mmap.moveCamera(CameraUpdateFactory.newLatLngZoom( new LatLng( lat, lng), 18));
                        // Zoom in, animating the camera.
                        mmap.animateCamera(CameraUpdateFactory.zoomTo(18), 1500, null);
                    }
                }

                // Zoom in, animating the camera.
                mmap.setOnCameraMoveStartedListener(new GoogleMap.OnCameraMoveStartedListener() {
                    @Override
                    public void onCameraMoveStarted(int i) {
                    }
                });

                mmap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
                    @Override
                    public void onCameraIdle() {
                        // Cleaning all the markers.
                        if (mmap != null) {
                            mmap.clear();
                        }
                        LatLng lat = mmap.getCameraPosition().target;
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(lat);
                        getAddress(lat);

                    }

                });

            }

        });

    }


   /* @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        mapIsReady = true ;
        statusCheck();
        googleMap.setOnCameraMoveStartedListener(new GoogleMap.OnCameraMoveStartedListener() {
            @Override
            public void onCameraMoveStarted(int i) {
            }
        });

        googleMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                // Cleaning all the markers.
                if (googleMap != null) {
                    googleMap.clear();
                }
                final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

                }
                else {
                    LatLng lat = googleMap.getCameraPosition().target;
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(lat);
                    getAddress(lat);
                }



            }
        });
    }*/

    private String getAddress(LatLng latLng) {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            latt = String.valueOf(latLng.latitude);
            lanng = String.valueOf(latLng.longitude);
            if (addresses.size()==0) {
                return "";
            }else {

                String address = addresses.get(0).getAddressLine(addresses.get(0).getMaxAddressLineIndex()); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment prev = getFragmentManager().findFragmentByTag("dialog");
                addressmap.setText(address);
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);
                return address;
            }

        } catch (IOException e) {
            e.printStackTrace();
            return "No Address Found";

        }
    }


    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS),2);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            check_permission();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 44) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                check_permission();
            }
        }
    }
}