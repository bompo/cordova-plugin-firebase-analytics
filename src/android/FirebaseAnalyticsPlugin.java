package by.chemerisuk.cordova.firebase;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.provider.Settings;

import com.google.firebase.analytics.FirebaseAnalytics;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;
import org.apache.cordova.PluginResult.Status;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;


public class FirebaseAnalyticsPlugin extends CordovaPlugin {
    private static final String TAG = "FirebaseAnalyticsPlugin";

    private FirebaseAnalytics firebaseAnalytics;

    private boolean isTestLabDevice;

    @Override
    protected void pluginInitialize() {
        Log.d(TAG, "Starting Firebase Analytics plugin");

        Context context = this.cordova.getActivity().getApplicationContext();

        this.firebaseAnalytics = FirebaseAnalytics.getInstance(context);

        String testLabSetting = Settings.System.getString(context.getContentResolver(), "firebase.test.lab");
        if ("true".equals(testLabSetting)) {
            isTestLabDevice = true;
            this.firebaseAnalytics.setAnalyticsCollectionEnabled(false);  //Disable Analytics Collection
        }
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if ("logEvent".equals(action)) {
            logEvent(callbackContext, args.getString(0), args.getString(1), args.getString(2));

            return true;
        } else if ("setUserId".equals(action)) {
            setUserId(callbackContext, args.getString(0));

            return true;
        } else if ("setUserProperty".equals(action)) {
            setUserProperty(callbackContext, args.getString(0), args.getString(1));

            return true;
        } else if ("setEnabled".equals(action)) {
            setEnabled(callbackContext, args.getBoolean(0));

            return true;
        } else if ("setCurrentScreen".equals(action)) {
            setCurrentScreen(callbackContext, args.getString(0));

            return true;
        } else if ("isTestLabDevice".equals(action)) {
            isTestLabDevice(callbackContext);

            return true;
        }

        return false;
    }

    private void logEvent(CallbackContext callbackContext, String name, String propKey, String propValue) throws JSONException {
        Bundle bundle = new Bundle();
        if(propKey != null && !propKey.isEmpty() &&
           propValue != null && !propValue.isEmpty()) {
            bundle.putString(propKey, propValue);
        }

        this.firebaseAnalytics.logEvent(name, bundle);

        callbackContext.success();
    }

    private void setUserId(CallbackContext callbackContext, String userId) {
        this.firebaseAnalytics.setUserId(userId);

        callbackContext.success();
    }

    private void setUserProperty(CallbackContext callbackContext, String name, String value) {
        this.firebaseAnalytics.setUserProperty(name, value);

        callbackContext.success();
    }

    private void setEnabled(CallbackContext callbackContext, boolean enabled) {
        this.firebaseAnalytics.setAnalyticsCollectionEnabled(enabled);

        callbackContext.success();
    }

    private void setCurrentScreen(final CallbackContext callbackContext, final String screenName) {
        cordova.getActivity().runOnUiThread(new Runnable() {
            public void run() {
                firebaseAnalytics.setCurrentScreen(
                    cordova.getActivity(),
                    screenName,
                    null
                );

                callbackContext.success();
            }
        });
    }

    private void isTestLabDevice(CallbackContext callbackContext) {
        callbackContext.success(isTestLabDevice?1:0);
    }
}
