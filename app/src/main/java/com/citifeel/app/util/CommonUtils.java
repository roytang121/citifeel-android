package com.citifeel.app.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by roytang on 8/5/14.
 */
public class CommonUtils {
    public static void logKeyHash(Context context){
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    "com.sensbeat.sensbeat",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.i("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
        } catch (NoSuchAlgorithmException e) {
        }
    }

    public static int dp(Context context, int dp){
        //convert 100dp to px
        Resources r = context.getResources();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
    }

}
