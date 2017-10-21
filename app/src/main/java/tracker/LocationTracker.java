package tracker;

import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;

import java.text.DecimalFormat;

import eventnotification.com.tracker.MainActivity;

public class LocationTracker implements android.location.LocationListener {
    private Place place;
    private MainActivity activity;

    public LocationTracker(Place place, MainActivity mainActivity) {
        this.place = place;
        this.activity = mainActivity;
    }

    @Override
    public void onLocationChanged(Location location) {
        getExactLocation(location);
    }

    private void getExactLocation(Location location) {
        DecimalFormat df = new DecimalFormat("#.####");
        double currentLat = Double.valueOf(df.format(location.getLatitude()));
        double currentLng = Double.valueOf(df.format(location.getLongitude()));
        LatLng latLng = place.getLatLng();
        double destinationLat = Double.valueOf(df.format(latLng.latitude));
        double destinationLng = Double.valueOf(df.format(latLng.longitude));
        if (destinationLat == currentLat && destinationLng == currentLng) {
            activity.performAction();
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}