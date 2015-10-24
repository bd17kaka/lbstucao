package com.hacklm.tucao;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMap.InfoWindowAdapter;
import com.amap.api.maps.AMap.OnCameraChangeListener;
import com.amap.api.maps.AMap.OnInfoWindowClickListener;
import com.amap.api.maps.AMap.OnMapClickListener;
import com.amap.api.maps.AMap.OnMapLoadedListener;
import com.amap.api.maps.AMap.OnMapLongClickListener;
import com.amap.api.maps.AMap.OnMapTouchListener;
import com.amap.api.maps.AMap.OnMarkerClickListener;
import com.amap.api.maps.AMap.OnMarkerDragListener;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.Projection;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;

public class MainActivity extends Activity implements OnMapClickListener,
		OnMapLongClickListener, OnCameraChangeListener, OnMapTouchListener,
		OnMarkerClickListener, OnInfoWindowClickListener, OnMarkerDragListener,
		OnMapLoadedListener, InfoWindowAdapter {
	private AMap aMap;
	private MapView mapView;
	private Marker marker;// 有跳动效果的marker对象

	private MarkerOptions markerOption;
	private LinearLayout inputContent;
	private Button ok, cancle;
	private EditText input;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		/*
		 * 设置离线地图存储目录，在下载离线地图或初始化地图设置; 使用过程中可自行设置, 若自行设置了离线地图存储的路径，
		 * 则需要在离线地图下载和使用地图页面都进行路径设置
		 */
		// Demo中为了其他界面可以使用下载的离线地图，使用默认位置存储，屏蔽了自定义设置
		// MapsInitializer.sdcardDir =OffLineMapUtils.getSdCacheDir(this);
		mapView = (MapView) findViewById(R.id.map);
		mapView.onCreate(savedInstanceState);// 此方法必须重写
		init();
	}

	/**
	 * 初始化AMap对象
	 */
	private void init() {
		if (aMap == null) {
			aMap = mapView.getMap();
			setUpMap();
		}
		inputContent = (LinearLayout) findViewById(R.id.input_content);
		ok = (Button) findViewById(R.id.btn_ok);
		cancle = (Button) findViewById(R.id.btn_cancle);
		inputContent.setVisibility(View.GONE);
		input = (EditText) findViewById(R.id.inputs);
		cancle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				input.clearComposingText();
				input.setText("");
				destroyMarker();
				inputContent.setVisibility(View.GONE);
				// TODO Auto-generated method stub
				input.setFocusable(false);
				InputMethodManager imm = (InputMethodManager) input.getContext()
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
			}
		});
		ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(MainActivity.this, input.getText().toString(),
						Toast.LENGTH_SHORT);
				if (!TextUtils.isEmpty(input.getText())) {
					submitMarker(input.getText().toString());
					input.setText("");
					inputContent.setVisibility(View.GONE);
					input.setFocusable(false);
					InputMethodManager imm = (InputMethodManager) input.getContext()
							.getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
				} else {
					Toast.makeText(MainActivity.this, "吐槽点什么吧",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
		this.marker = null;
	}

	protected void submitMarker(String title) {
		// TODO Auto-generated method stub
		this.marker.setTitle(title);
		marker.showInfoWindow();
		this.marker = null;
		input.clearFocus();
	}

	protected void destroyMarker() {
		// TODO Auto-generated method stub
		if (this.marker != null) {
			this.marker.destroy();
			this.marker = null;
			
		}

	}

	/**
	 * amap添加一些事件监听器
	 */
	private void setUpMap() {

		aMap.setOnMapClickListener(this);// 对amap添加单击地图事件监听器
		aMap.setOnMapLongClickListener(this);// 对amap添加长按地图事件监听器
		aMap.setOnCameraChangeListener(this);// 对amap添加移动地图事件监听器
		aMap.setOnMapTouchListener(this);// 对amap添加触摸地图事件监听器
		aMap.setMyLocationEnabled(true);

		aMap.setOnMarkerDragListener(this);// 设置marker可拖拽事件监听器
		aMap.setOnMapLoadedListener(this);// 设置amap加载成功事件监听器
		aMap.setOnMarkerClickListener(this);// 设置点击marker事件监听器
		aMap.setOnInfoWindowClickListener(this);// 设置点击infoWindow事件监听器
		aMap.setInfoWindowAdapter(this);// 设置自定义InfoWindow样式
		addMarkersToMap(new LatLng(30.502326, 114.407022), "卧槽", "警察这里有HACK");// 往地图上添加marker
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onResume() {
		super.onResume();
		mapView.onResume();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onPause() {
		super.onPause();
		mapView.onPause();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mapView.onSaveInstanceState(outState);
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mapView.onDestroy();
	}

	/**
	 * 在地图上添加marker
	 */
	private Marker addMarkersToMap(LatLng point, String title, String snip) {
		Marker marker = aMap.addMarker(new MarkerOptions()
				.title(title)
				.snippet(snip)
				.icon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
				.draggable(true));
		marker.setPosition(point);
		if (title != "")
			marker.showInfoWindow();
		return marker;
	}

	/**
	 * 对marker标注点点击响应事件
	 */
	@Override
	public boolean onMarkerClick(final Marker marker) {
		if (aMap != null) {
			jumpPoint(marker);
		}
		// markerText.setText("你点击的是" + marker.getTitle());
		return false;
	}

	/**
	 * marker点击时跳动一下
	 */
	public void jumpPoint(final Marker marker) {
		final Handler handler = new Handler();
		final long start = SystemClock.uptimeMillis();
		Projection proj = aMap.getProjection();
		Point startPoint = proj.toScreenLocation((LatLng) marker.getPosition());
		Toast.makeText(
				this,
				marker.getPosition().longitude + "  "
						+ marker.getPosition().latitude, Toast.LENGTH_SHORT)
				.show();

		final Double lon = marker.getPosition().longitude;
		final Double lati = marker.getPosition().latitude;

		startPoint.offset(0, -100);
		final LatLng startLatLng = proj.fromScreenLocation(startPoint);
		final long duration = 1500;

		final Interpolator interpolator = new BounceInterpolator();
		handler.post(new Runnable() {
			@Override
			public void run() {
				long elapsed = SystemClock.uptimeMillis() - start;
				float t = interpolator.getInterpolation((float) elapsed
						/ duration);
				double lng = t * lon + (1 - t) * startLatLng.longitude;
				double lat = t * lati + (1 - t) * startLatLng.latitude;
				marker.setPosition(new LatLng(lat, lng));
				if (t < 1.0) {
					handler.postDelayed(this, 16);
				}
			}
		});
	}

	/**
	 * 监听点击infowindow窗口事件回调
	 */
	@Override
	public void onInfoWindowClick(Marker marker) {
		Toast.makeText(this, "你点击了infoWindow窗口" + marker.getTitle(),
				Toast.LENGTH_SHORT).show();
		startActivity(new Intent(this.getApplicationContext(),
				DetailActivity.class));

		// ToastUtil.show(MarkerActivity.this, "当前地图可视区域内Marker数量:"
		// + aMap.getMapScreenMarkers().size());
	}

	/**
	 * 监听拖动marker时事件回调
	 */
	@Override
	public void onMarkerDrag(Marker marker) {
		// String curDes = marker.getTitle() + "拖动时当前位置:(lat,lng)\n("
		// + marker.getPosition().latitude + ","
		// + marker.getPosition().longitude + ")";
		// markerText.setText(curDes);
	}

	/**
	 * 监听拖动marker结束事件回调
	 */
	@Override
	public void onMarkerDragEnd(Marker marker) {
		// markerText.setText(marker.getTitle() + "停止拖动");
	}

	/**
	 * 监听开始拖动marker事件回调
	 */
	@Override
	public void onMarkerDragStart(Marker marker) {
		// markerText.setText(marker.getTitle() + "开始拖动");
	}

	/**
	 * 监听amap地图加载成功事件回调
	 */
	@Override
	public void onMapLoaded() {
		// 设置所有maker显示在当前可视区域地图中
		LatLngBounds bounds = new LatLngBounds.Builder().include(
				new LatLng(30.502326, 114.407022)).build();
		aMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 150));
	}

	/**
	 * 监听自定义infowindow窗口的infocontents事件回调
	 */
	@Override
	public View getInfoContents(Marker marker) {
		// if (radioOption.getCheckedRadioButtonId() !=
		// R.id.custom_info_contents) {
		return null;
		// }
		// View infoContent = getLayoutInflater().inflate(
		// R.layout.custom_info_contents, null);
		// render(marker, infoContent);
		// return infoContent;
	}

	/**
	 * 监听自定义infowindow窗口的infowindow事件回调
	 */
	@Override
	public View getInfoWindow(Marker marker) {
		return null;
	}

	/**
	 * 对单击地图事件回调
	 */
	@Override
	public void onMapClick(LatLng point) {
		// mTapTextView.setText("tapped, point=" + point);
		Toast.makeText(this, point.latitude + " " + point.longitude,
				Toast.LENGTH_SHORT).show();
		if (inputContent.getVisibility() == View.GONE) {
			this.marker = addMarkersToMap(point, "", "");
			inputContent.setVisibility(View.VISIBLE);
			input.requestFocus();
			InputMethodManager imm = (InputMethodManager) input.getContext()
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
			input.setText("");
		} else {
			Toast.makeText(this, "莫慌", Toast.LENGTH_SHORT).show();
		}

	}

	/**
	 * 对长按地图事件回调
	 */
	@Override
	public void onMapLongClick(LatLng point) {
		// mTapTextView.setText("long pressed, point=" + point);
	}

	/**
	 * 对正在移动地图事件回调
	 */
	@Override
	public void onCameraChange(CameraPosition cameraPosition) {
		// mCameraTextView.setText("onCameraChange:" +
		// cameraPosition.toString());
	}

	/**
	 * 对移动地图结束事件回调
	 */
	@Override
	public void onCameraChangeFinish(CameraPosition cameraPosition) {

	}

	/**
	 * 对触摸地图事件回调
	 */
	@Override
	public void onTouch(MotionEvent event) {

		// mTouchTextView.setText("触摸事件：屏幕位置" + event.getX() + " " +
		// event.getY());
		// Toast.makeText(this, event.getX()+" "+event.getY(),
		// Toast.LENGTH_SHORT).show();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
