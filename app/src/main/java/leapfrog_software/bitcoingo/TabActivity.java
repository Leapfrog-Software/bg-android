package leapfrog_software.bitcoingo;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.Date;

import leapfrog_software.bitcoingo.Map.MapFragment;
import leapfrog_software.bitcoingo.System.CurrentTimeContainer;
import leapfrog_software.bitcoingo.System.GpsManager;
import leapfrog_software.bitcoingo.System.HttpRequestor;
import leapfrog_software.bitcoingo.TabController.Tab1Controller;
import leapfrog_software.bitcoingo.TabController.Tab2Controller;

/**
 * Created by Leapfrog-Software on 2016/11/29.
 */

public class TabActivity extends AppCompatActivity {

    private GpsManager mGpsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                initTabbar();
                initGps();
                CurrentTimeContainer.getInstance().start();
            }
        }, 200);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                versionCheck();
            }
        }, 200);



    }

    private void versionCheck(){

        HttpRequestor requestor = new HttpRequestor(new HttpRequestor.HttpCallback() {
            @Override
            public void didReceiveData(String string) {
                ((LinearLayout)findViewById(R.id.splashBaseLayout)).setVisibility(View.INVISIBLE);
            }
        });
        requestor.execute("https://google.com", "POST", "command=versioncheck&version=1&os=android");
    }


    private void initGps(){
        mGpsManager = new GpsManager();
        mGpsManager.start(this, new GpsManager.GpsManagerCallback() {
            @Override
            public void didLocationChanged(boolean result, double latitude, double longitude) {
                MapFragment mapFragment = (MapFragment)Tab1Controller.getInstance().getmapFragment();
                mapFragment.updateGpsInfo(result, latitude, longitude);
            }
        });
    }

    private void initTabbar(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        Tab1Controller.getInstance().initialize(fragmentManager, R.id.container);
        Tab2Controller.getInstance().initialize(fragmentManager, R.id.container);
    }

    public void onClickTab1(View view){
        changeTab(0);
    }

    public void onClickTab2(View view){
        changeTab(1);
    }

    private void changeTab(int index){

        FrameLayout tab1Frame = (FrameLayout)findViewById(R.id.tab1_frame);
        FrameLayout tab2Frame = (FrameLayout)findViewById(R.id.tab2_frame);
        if(index == 0)  tab1Frame.setBackgroundColor(Color.rgb(20, 69, 19));
        else            tab1Frame.setBackgroundColor(Color.rgb(47, 109, 46));
        if(index == 1)  tab2Frame.setBackgroundColor(Color.rgb(20, 69, 19));
        else            tab2Frame.setBackgroundColor(Color.rgb(47, 109, 46));

        if(index == 0) {
            Tab1Controller.getInstance().show();
            Tab2Controller.getInstance().hide();
        }else if(index == 1) {
            Tab1Controller.getInstance().hide();
            Tab2Controller.getInstance().show();
        }
    }
}
