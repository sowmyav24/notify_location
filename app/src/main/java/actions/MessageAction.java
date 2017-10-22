package actions;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.widget.Toast;

import tracker.LocationTracker;

import static android.provider.ContactsContract.CommonDataKinds.Phone.NUMBER;

public class MessageAction extends AppCompatActivity implements NotificationAction {

    private String contactNumber;
    private Context context;

    public MessageAction(Context context) {
        this.context = context;
    }

    @Override
    public void performAction() {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(contactNumber, null, "Reached ! (From App)", null, null);
        Toast.makeText(context, "SMS sent.", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onActivity(Intent intent, LocationManager locationManager, LocationTracker locationTracker) {
        String[] projection = {NUMBER};
        Cursor cursor = context.getContentResolver().query(intent.getData(), projection, null, null, null);
        cursor.moveToFirst();
        int numberColumnIndex = cursor.getColumnIndex(NUMBER);
        contactNumber = cursor.getString(numberColumnIndex);
        locationTracker.startListening();
    }
}