package com.qunlivideo.myapplication;

import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.RequiresPermission;

import java.util.List;
import java.util.Locale;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class Locations {

    public static abstract class LocalCallBack implements LocationListener {

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }
    }

    public interface OnLocationListener {
        void onLocalAddress(int code, String address);
    }

    @RequiresPermission(anyOf = {ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION})
    public static void location(final Context context, final OnLocationListener onLocationListener) {
        //获取位置管理服务
        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        //查找服务信息
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE); //定位精度: 最高
        criteria.setAltitudeRequired(false); //海拔信息：不需要
        criteria.setBearingRequired(false); //方位信息: 不需要
        criteria.setCostAllowed(false);  //是否允许付费
        criteria.setPowerRequirement(Criteria.POWER_LOW); //耗电量: 低功耗
        String provider = manager.getBestProvider(criteria, true); //获取GPS信息
        manager.requestLocationUpdates(provider, 2000, 5, new LocalCallBack() {
            @Override
            public void onLocationChanged(Location location) {
                String address = "";
                if (location != null) {
                    //获取国家，省份，城市的名称
                    Log.e("location", location.toString());
                    List<Address> m_list = getAddress(context, location);
                    Log.e("str", m_list.toString());
                    for (int i = 0; i < m_list.size(); i++) {
                        Log.e("location", "str pos = " + i + m_list.get(i).toString());
                    }
                    if (m_list != null && m_list.size() > 0) {
                        address = m_list.get(0).getLocality();//获取城市
                    }
                }
                if (null != onLocationListener)
                    onLocationListener.onLocalAddress(TextUtils.isEmpty(address) ? -1 : 0, address);
            }
        });
    }

    private static List<Address> getAddress(Context context, Location location) {
        List<Address> result = null;
        try {
            if (location != null) {
                Geocoder gc = new Geocoder(context, Locale.getDefault());
                result = gc.getFromLocation(location.getLatitude(),
                        location.getLongitude(), 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
