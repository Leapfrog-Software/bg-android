package leapfrog_software.bitcoingo.Map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import leapfrog_software.bitcoingo.MasterFragment;
import leapfrog_software.bitcoingo.R;

/**
 * Created by Leapfrog-Software on 2016/11/29.
 */

public class MapFragment extends MasterFragment implements OnMapReadyCallback {

    private MapView mMapView;
    private GoogleMap mGoogleMap;
    private boolean mDidMapMove = false;
    private ArrayList<Marker> mMarkerList = new ArrayList<Marker>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View view = inflater.inflate(R.layout.fragment_map, null);

        //マップ
        MapsInitializer.initialize(this.getActivity());
        mMapView = (MapView) view.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstance);
        mMapView.getMapAsync(this);



        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }
    @Override
    public void onLowMemory()
    {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mGoogleMap = googleMap;

        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {



                return false;
            }
        });
    }

    public void updateGpsInfo(boolean result, double latitude, double longitude){

        if (mGoogleMap == null){
            return;
        }
        if(mDidMapMove == false){
            mDidMapMove = true;

            LatLng latlng = new LatLng(latitude, longitude);
            CameraPosition cameraPos = new CameraPosition.Builder().target(latlng).zoom(15.0f).bearing(0).build();
            CameraUpdate camera = CameraUpdateFactory.newCameraPosition(cameraPos);
            mGoogleMap.moveCamera(camera);
        }

        //一旦マーカーを取り除く
        for(int i=0;i<mMarkerList.size();i++){
            mMarkerList.get(i).remove();
        }
        mMarkerList.clear();

        //マーカーを追加
        MarkerOptions markerOption = new MarkerOptions().position(new LatLng(35, 138));
        Marker marker = mGoogleMap.addMarker(markerOption);
        mMarkerList.add(marker);

    }

}
