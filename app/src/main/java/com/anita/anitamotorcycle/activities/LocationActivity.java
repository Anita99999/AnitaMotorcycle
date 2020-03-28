package com.anita.anitamotorcycle.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.LoginFilter;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anita.anitamotorcycle.R;
import com.anita.anitamotorcycle.helps.MotorHelper;
import com.anita.anitamotorcycle.utils.PermissionUtils;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.hb.dialog.dialog.ConfirmDialog;

public class LocationActivity extends AppCompatActivity {
    private String mLocation = "定位";
    private static final String TAG = "LocationActivity";
    MapView mMapView = null;
    BaiduMap mBaiduMap;
    private ImageView mSaLocation;
    LocationClient mLocClient;
    private LatLng latLng;
    boolean isFirstLoc = true; // 是否首次定位
    public MyLocationListenner myListener = new MyLocationListenner();
    private ImageView mIv_back;
    private TextView mTv_position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        Log.d(TAG, "onCreate: 测试1");
        mMapView = findViewById(R.id.bmapView);
        mIv_back = findViewById(R.id.iv_location_back);
        mMapView = findViewById(R.id.bmapView);
        mSaLocation = findViewById(R.id.sa_location);
        mTv_position = findViewById(R.id.tv_position);
        if (mTv_position != null && mTv_position.toString() != "") {
            Log.d(TAG, "旧数据mTv_position更新定位-- " + mTv_position.getText().toString());
//            更新摩托车定位标记
            MotorHelper.getInstance().setLocation(mTv_position.getText().toString());
        }
        PermissionUtils.getGPSPermission1(this);

        Log.d(TAG, "onCreate: 测试2");

        requestLocation();

        initListener();

    }

    private void requestLocation() {
        mBaiduMap = mMapView.getMap();
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 定位初始化
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setIsNeedAddress(true);
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);//可选，设置的扫描间隔，单位是毫秒，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setNeedDeviceDirect(true);//在网络定位时，是否需要设备方向- true:需要 ; false:不需要。默认为false
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation
        mLocClient.setLocOption(option);
        mLocClient.start();
        Log.d(TAG, "onCreate: 测试3");
    }

    private void initListener() {
        mIv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();    //后退操作
            }
        });
        mSaLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //把定位点再次显现出来
                MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLng(latLng);
                mBaiduMap.animateMapStatus(mapStatusUpdate);
            }
        });
        Log.d(TAG, "initListener: 测试4");
    }

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            Log.d(TAG, "onCreate: 测试5");
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;

            }
            latLng = new LatLng(location.getLatitude(), location.getLongitude());
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            if (isFirstLoc) {
                Log.d(TAG, "onCreate: 测试6");
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));

            }
            if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                Log.d(TAG, "onReceiveLocation: GPS定位结果--" + location.getAddrStr());
            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                Log.d(TAG, "onReceiveLocation: 网络定位结果" + location.getAddrStr());
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                Log.d(TAG, "onReceiveLocation: 离线定位结果" + location.getAddrStr());
            } else if (location.getLocType() == BDLocation.TypeServerError) {//服务器错误
                Toast.makeText(LocationActivity.this, "服务器错误，请检查", Toast.LENGTH_SHORT).show();
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {//网络错误
                Toast.makeText(LocationActivity.this, "网络错误，请检查", Toast.LENGTH_SHORT).show();
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {//手机模式错误
                Toast.makeText(LocationActivity.this, "手机模式错误，请检查是否飞行", Toast.LENGTH_SHORT).show();
            }
            mLocation = location.getAddrStr();
            if (mLocation != null) {
                mTv_position.setText("当前定位：" + mLocation);
                Log.d(TAG, "onReceiveLocation当前定位: " + mLocation);
//            更新摩托车定位标记
                MotorHelper.getInstance().setLocation(mLocation);
            }
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }

    /**
     * 动态获取定位权限的回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "onRequestPermissionsResult:动态获取定位权限的回调 ");
        if (requestCode == 200) {
            //判断结果
            if (grantResults.length == 5 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED && grantResults[2] == PackageManager.PERMISSION_GRANTED && grantResults[3] == PackageManager.PERMISSION_GRANTED && grantResults[4] == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "has permissions..");
                //有权限
                requestLocation();
            } else {
                Log.d(TAG, "no permissionS...");
                //没权限
//                finish();
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) && !ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION) && !ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.READ_PHONE_STATE) && !ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) && !ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    //走到这里，说明用户之前用户禁止权限的同时，勾选了不再询问
                    //那么，你需要弹出一个dialog，提示用户需要权限，然后跳转到设置里头去打开。
                    Log.d(TAG, "用户之前勾选了不再询问...");
                    //TODO:弹出一个框框，然后提示用户说需要开启权限。
                    showComfirmDialog();

                } else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 200);

                    //请求权限
                    Log.d(TAG, "请求权限...");
                }
            }
        }


    }

    private void showComfirmDialog() {
        ConfirmDialog confirmDialog = new ConfirmDialog(this);
        confirmDialog.setLogoImg(R.mipmap.dialog_notice).setMsg("勾选了不再询问，需先到请到手机设置里开启相应权限");
        confirmDialog.setClickListener(new ConfirmDialog.OnBtnClickListener() {
            @Override
            public void ok() {
                //TODO:用户点击确定的时候，跳转到设置里去
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                //在activity结果范围的地方，再次检查是否有权限
                startActivityForResult(intent, 200);
            }

            @Override
            public void cancel() {
                Toast.makeText(getApplicationContext(), "取消", Toast.LENGTH_LONG).show();
            }
        });
        confirmDialog.show();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mLocClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: ");
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause: ");
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }


}
