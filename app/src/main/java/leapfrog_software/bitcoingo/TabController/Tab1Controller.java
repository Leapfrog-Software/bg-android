package leapfrog_software.bitcoingo.TabController;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import leapfrog_software.bitcoingo.Map.MapFragment;

/**
 * Created by Leapfrog-Software on 2016/11/29.
 */

public class Tab1Controller extends TabControllerBase {

    private static Tab1Controller singleton = new Tab1Controller();
    private Tab1Controller(){}
    public static Tab1Controller getInstance(){
        return singleton;
    }


    @Override
    public void initialize(FragmentManager fm, int containerId){

        if(mFragmentList.size() == 0){
            mFragmentList.add(new MapFragment());
        }

        super.initialize(fm, containerId);
    }

    public Fragment getmapFragment(){

        return mFragmentList.get(0);
    }
}
