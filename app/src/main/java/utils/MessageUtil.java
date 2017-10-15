package utils;

import android.content.Context;
import android.telephony.SmsManager;
import android.widget.Toast;

public class MessageUtil {
    public static void sendMessage(Context context) {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage("+919000000000", null, "This is a test message", null, null);
        Toast.makeText(context, "SMS sent.", Toast.LENGTH_LONG).show();
    }
}