package tracker;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationTracker implements android.location.LocationListener {
    private Context context;

    public LocationTracker(Context context) {
        this.context = context;
    }

    @Override
    public void onLocationChanged(Location location) {
        getExactlocation(location);
    }

    private void getExactlocation(Location location) {
        Geocoder geocoder = new Geocoder(context, Locale.ENGLISH);
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String address = addresses != null ? addresses.get(0).getAddressLine(0) : null;
        Toast.makeText(context, "Latitude " + latitude + "Longitude " + longitude + "Address " + address, Toast.LENGTH_LONG).show();
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