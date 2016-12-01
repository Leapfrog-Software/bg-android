package leapfrog_software.bitcoingo.System;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

/**
 * Created by Leapfrog-Software on 2016/11/29.
 */

public class GpsManager implements LocationListener, ActivityCompat.OnRequestPermissionsResultCallback{

    private LocationManager mLocationManager;
    private Location mCurrentLocation;
    private Activity mActivity;
    private GpsManagerCallback mCallback;
    private int mInitialRetryCount = 0;

    private int kPermissionRequestCode = 1000;

    public void start(Activity activity, GpsManagerCallback callback){

        mActivity = activity;
        mCallback = callback;

        mLocationManager = (LocationManager) mActivity.getSystemService(Context.LOCATION_SERVICE);

        boolean gpsEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsEnabled) {
            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            mActivity.startActivity(settingsIntent);
        }

        if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, kPermissionRequestCode);
            return;
        }
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 5, this);   //1000ms , 5m 毎に更新

        //定期実行
        final int delay = 3000;
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mCurrentLocation != null){
                    mCallback.didLocationChanged(true, mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
                }else{
                    if(mInitialRetryCount < 3){
                        mInitialRetryCount++;
                    }else {
                        mCallback.didLocationChanged(false, 0, 0);
                    }
                }
                handler.postDelayed(this, delay);
            }
        }, delay);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == kPermissionRequestCode) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                start(mActivity, mCallback);
            }
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        switch (status) {
            case LocationProvider.AVAILABLE:
                break;
            case LocationProvider.OUT_OF_SERVICE:
                break;
            case LocationProvider.TEMPORARILY_UNAVAILABLE:
                break;
        }
    }

    @Override
    public void onLocationChanged(Location location) {

        mCurrentLocation = location;
    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public interface GpsManagerCallback{
        void didLocationChanged(boolean result, double latitude, double longitude);
    }
}
