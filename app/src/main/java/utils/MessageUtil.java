package utils;

import android.app.Activity;
import android.content.Context;
import android.telephony.SmsManager;
import android.widget.TextView;
import android.widget.Toast;

import eventnotification.com.tracker.R;

public class MessageUtil implements NotificationAction {

    @Override
    public void performAction(Context context, Activity activity) {
        TextView textView = activity.findViewById(R.id.text);
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(textView.getText().toString(), null, "This is a test message", null, null);
        Toast.makeText(context, "SMS sent.", Toast.LENGTH_LONG).show();

    }
}