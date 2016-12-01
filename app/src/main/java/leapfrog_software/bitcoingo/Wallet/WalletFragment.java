package leapfrog_software.bitcoingo.Wallet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import leapfrog_software.bitcoingo.MasterFragment;
import leapfrog_software.bitcoingo.R;

/**
 * Created by Leapfrog-Software on 2016/11/29.
 */

public class WalletFragment extends MasterFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View view = inflater.inflate(R.layout.fragment_wallet, null);
        return view;
    }
}
