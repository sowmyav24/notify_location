package actions;

import android.content.Intent;
import android.location.LocationManager;

import tracker.LocationTracker;

public interface NotificationAction {

    void performAction();

    void onActivity(Intent intent, LocationManager locationManager, LocationTracker locationTracker);

}
