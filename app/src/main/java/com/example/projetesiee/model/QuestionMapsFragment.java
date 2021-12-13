package com.example.projetesiee.model;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import androidx.annotation.NonNull;

import com.example.projetesiee.controller.QuestionMapsActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class QuestionMapsFragment extends SupportMapFragment implements OnMapReadyCallback {

    private QuestionMapsActivity questionMapsActivity;
    private GoogleMap googleMap;

    public QuestionMapsFragment()  {
        getMapAsync(this);
    }

    @Override
    public void onMapReady(final GoogleMap gmap) {
        this.googleMap = gmap;

        LatLng vietnam = new LatLng(14.0583, 108.2772); // 14.0583° N, 108.2772° E
        this.googleMap.addMarker(new MarkerOptions().position(vietnam).title("Marker in Vietnam"));
        this.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(vietnam,0) );
        this.googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.draggable(false);
                markerOptions.position(latLng);
                markerOptions.title(latLng.latitude + " : "+ latLng.longitude);
                googleMap.clear();
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 0));
                googleMap.addMarker(markerOptions);
                questionMapsActivity.setNewCountry(getCountryName(questionMapsActivity,latLng.latitude,latLng.longitude));
            }
        });
        questionMapsActivity.setNewCountry(getCountryName(questionMapsActivity,vietnam.latitude,vietnam.longitude));

    }

    public void setQuestionMapsActivity(QuestionMapsActivity q){
        questionMapsActivity=q;
    }
    public static String getCountryName(Context context, double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);

            if (addresses != null && !addresses.isEmpty()) {
                return addresses.get(0).getCountryName();
            }
            return null;
        } catch (IOException ignored) {
        }
        return "";
    }
}
