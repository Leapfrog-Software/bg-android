package leapfrog_software.bitcoingo.System;

import android.os.Handler;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Leapfrog-Software on 2016/11/30.
 */

public class CurrentTimeContainer {

    private static CurrentTimeContainer container = new CurrentTimeContainer();
    private Date mCurrentDate;
    private boolean mIsLoading = false;

    private CurrentTimeContainer(){}

    public static CurrentTimeContainer getInstance(){
        return container;
    }

    public void start(){

        //定期実行
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                request();
                handler.postDelayed(this, 60000);
            }
        }, 100);
    }

    private void request(){

        if(mIsLoading){
            return;
        }

        HttpRequestor requestor = new HttpRequestor(new HttpRequestor.HttpCallback() {
            @Override
            public void didReceiveData(String string) {
                mCurrentDate = new Date();
            }
        });
        requestor.execute("https://google.com", "POST", "");
    }

    public Date getCurrentDate(){
        return mCurrentDate;
    }
}
