package com.maxignov.desafiofluxit.Views.Fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.maxignov.desafiofluxit.R;

public class FragmentBusiness extends Fragment implements OnMapReadyCallback {
    private GoogleMap gMap;
    private MapView mapView;

    private static final int LOCATION_REQUEST_CODE = 1;

    public FragmentBusiness() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_business, container, false);
        initViews(v);

        mapView.onCreate(null);
        mapView.onResume();
        mapView.getMapAsync(this);

        return v;
    }

    private void initViews(View v){
        mapView = v.findViewById(R.id.map);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());
        gMap = googleMap;
        gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        //Por defecto centra la camara en la sucursal de la plata
        gMap.moveCamera(CameraUpdateFactory.newCameraPosition(CameraPosition.builder()
                .target(new LatLng(-34.9208142, -57.9518059))
                .zoom(10).build()));
        //Añado marcador de la sucursal de Buenos Aires
        gMap.addMarker(new MarkerOptions()
                .position(new LatLng(-34.6188126, -58.3677217))
                .title("Sucursal - Buenos Aires"));
        //Añado marcador de la sucursal de La Plata
        gMap.addMarker(new MarkerOptions()
                .position(new LatLng(-34.9208142, -57.9518059))
                .title("Sucursal - La Plata"));

        //Si tengo los permisos necesarios, muestro mi ubicacion en el mapa, sino los solicito.
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            gMap.setMyLocationEnabled(true);
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_REQUEST_CODE);
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == LOCATION_REQUEST_CODE) {
            //Si tengo los permisos son aceptados, pongo mi ubicacion en el mapa, sino informo el error.
            if (permissions.length > 0 &&
                    permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION) &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    gMap.setMyLocationEnabled(true);
            } else {
                Toast.makeText(getContext(), getContext().getResources().getString(R.string.deniedPermits), Toast.LENGTH_LONG).show();
                Log.e("FragmentBussines","No se tiene permisos para obtener la ubicacion");
            }
        }
    }
}
