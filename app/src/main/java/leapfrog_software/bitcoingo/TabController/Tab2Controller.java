package leapfrog_software.bitcoingo.TabController;

import android.support.v4.app.FragmentManager;

import leapfrog_software.bitcoingo.Map.MapFragment;
import leapfrog_software.bitcoingo.Wallet.WalletFragment;

/**
 * Created by Leapfrog-Software on 2016/11/29.
 */

public class Tab2Controller extends TabControllerBase {

    private static Tab2Controller singleton = new Tab2Controller();
    private Tab2Controller(){}
    public static Tab2Controller getInstance(){
        return singleton;
    }


    @Override
    public void initialize(FragmentManager fm, int containerId){

        if(mFragmentList.size() == 0){
            mFragmentList.add(new WalletFragment());
        }

        super.initialize(fm, containerId);
    }
}
