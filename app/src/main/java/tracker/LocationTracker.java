package tracker;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

public class LocationTracker implements android.location.LocationListener {
    private Context context;

    public LocationTracker(Context context) {
        this.context = context;
    }

    @Override
    public void onLocationChanged(Location location) {
        getExactLocation(location);
    }

    private void getExactLocation(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        Toast.makeText(context, "Latitude " + latitude + "Longitude " + longitude, Toast.LENGTH_LONG).show();
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