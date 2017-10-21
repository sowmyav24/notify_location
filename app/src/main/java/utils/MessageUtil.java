package utils;

import android.content.Context;
import android.telephony.SmsManager;
import android.widget.TextView;
import android.widget.Toast;

public class MessageUtil {

    public static void sendMessage(Context context, String number) {

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(number, null, "This is a test message", null, null);
        Toast.makeText(context, "SMS sent.", Toast.LENGTH_LONG).show();
    }
}