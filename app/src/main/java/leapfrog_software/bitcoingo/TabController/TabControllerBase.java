package leapfrog_software.bitcoingo.TabController;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.ArrayList;

import leapfrog_software.bitcoingo.R;

/**
 * Created by Leapfrog-Software on 2016/11/29.
 */

public class TabControllerBase {

    public FragmentManager mFragmentManager = null;
    public ArrayList<Fragment> mFragmentList = new ArrayList<Fragment>();
    public int mContainerId = 0;

    public void initialize(FragmentManager fm, int containerId){
        mFragmentManager = fm;
        mContainerId = containerId;

        FragmentTransaction ft = fm.beginTransaction();
        ft.add(containerId, mFragmentList.get(0));
        ft.commit();
    }

    public void show(){

        FragmentTransaction ft = mFragmentManager.beginTransaction();
        ft.show(mFragmentList.get(mFragmentList.size() - 1));
        ft.commit();
    }

    public void hide(){

        FragmentTransaction ft = mFragmentManager.beginTransaction();
        ft.hide(mFragmentList.get(mFragmentList.size() - 1));
        ft.commit();
    }


    public void stack(Fragment fragment, boolean slide){

        FragmentTransaction ft = mFragmentManager.beginTransaction();
        if(slide){
            ft.setCustomAnimations(R.anim.stack_from_right, R.anim.close_for_left);
        }
        for(int i=0;i<mFragmentList.size();i++){
            ft.remove(mFragmentList.get(i));
        }
        ft.add(mContainerId, fragment);
        ft.commit();
        mFragmentList.add(fragment);
    }

    public void pop(boolean slide){

        if(mFragmentList.size() < 2){
            return;
        }
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        if(slide){
            ft.setCustomAnimations(R.anim.stack_from_left, R.anim.close_for_right);
        }
        ft.remove(mFragmentList.get(mFragmentList.size() - 1));
        ft.add(mContainerId, mFragmentList.get(mFragmentList.size() - 2));
        ft.commit();
        mFragmentList.remove(mFragmentList.size() - 1);
    }

}
