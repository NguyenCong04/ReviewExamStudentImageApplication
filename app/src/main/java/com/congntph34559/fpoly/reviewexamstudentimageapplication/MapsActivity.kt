package com.congntph34559.fpoly.reviewexamstudentimageapplication

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.location.Location
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.congntph34559.fpoly.reviewexamstudentimageapplication.databinding.ActivityMapsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val TAG = "zzzzzMapsActivityzzzzz"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Khởi tạo FusedLocationProviderClient
        fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(this)


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Kiểm tra quyền truy cập vị trí và yêu cầu vị trí người dùng
        if (ContextCompat.checkSelfPermission(
                this, android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    Log.d("zzzzz", "onCreate: $location")
                    val userLocation =
                        LatLng(location.latitude, location.longitude)
                    mMap.animateCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            userLocation,
                            15f
                        )
                    )
                }
            }
        } else {
            // Yêu cầu quyền truy cập vị trí nếu chưa có
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
        }
        binding.autoCompleteSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                fetchSuggestions(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

    }

    private fun fetchSuggestions(query: String) {
        if (query.length < 2) return

        val apiKey = "vbj6PwUqjsrg1m5QluofFbzEI076r4axpXXEsuOH"
        RetrofitInstance.api.getPlace(apiKey, query)
            .enqueue(object : Callback<SuggestionResponse> {
                override fun onResponse(
                    call: Call<SuggestionResponse>,
                    response: Response<SuggestionResponse>
                ) {
                    if (response.isSuccessful && response.body()?.status == "OK") {
                        val suggestions = response.body()?.predictions ?: emptyList()
                        Log.d(TAG, "onResponse: suggestions $suggestions ")
                        val list = suggestions.map { it.description }
                        Log.d(TAG, "onResponse: list $list")
                        val adapter = ArrayAdapter(
                            this@MapsActivity,
                            android.R.layout.simple_list_item_1,
                            list
                        )
                        binding.autoCompleteSearch.setAdapter(adapter)
                        adapter.notifyDataSetChanged()
                    } else {
                        Log.e("Retrofit", "Response error: ${response.errorBody()?.string()}")
                    }
                }

                override fun onFailure(call: Call<SuggestionResponse>, t: Throwable) {
                    Log.e("Retrofit", "API call failed: ${t.message}")
                }
            })
    }

    private fun fetchSuggestions2(query: String) {
        if (query.length < 2) return

        val apiKey = "vbj6PwUqjsrg1m5QluofFbzEI076r4axpXXEsuOH"
        val sessionToken = "SESSION_TOKEN"
        val listAddress = mutableListOf<SuggestionResponse>()
        RetrofitInstance.api.getPlace2(query)
            .enqueue(object : Callback<SuggestSearchModel> {
                override fun onResponse(
                    call: Call<SuggestSearchModel>,
                    response: Response<SuggestSearchModel>
                ) {
                    Log.d(TAG, "onResponse:response $response")
                    Log.d(TAG, "onResponse:call $call")
                    if (response.isSuccessful) {
                        val suggestions = response.body()
                        Log.d(TAG, "onResponse: suggestions $suggestions ")
//                        Log.d(TAG, "onResponse: list $list")
                    //                        val adapter = ArrayAdapter(
//                            this@MapsActivity,
//                            android.R.layout.simple_list_item_1,
//
//                        )
//                        binding.autoCompleteSearch.setAdapter(adapter)
//                        adapter.notifyDataSetChanged()
                    } else {
                        Log.e("Retrofit", "Response error: ${response.errorBody()?.string()}")
                    }
                }

                override fun onFailure(call: Call<SuggestSearchModel>, t: Throwable) {
                    Log.e("Retrofit", "API call failed: ${t.message}")
                }
            })
    }


    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.d("zzz", "onRequestPermissionsResult: da vao")
        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(
                        this, Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    mMap.isMyLocationEnabled = true
                    mMap.uiSettings.isMyLocationButtonEnabled = true
                    mMap.uiSettings.isCompassEnabled = true
                    mMap.uiSettings.isZoomControlsEnabled = true
                    mMap.isTrafficEnabled = true

                    mMap.setOnMyLocationButtonClickListener {
                        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                            if (location != null) {
                                Log.d("zzzzz", "onMapReady: $location")
                                val userLocation =
                                    LatLng(
                                        location.latitude,
                                        location.longitude
                                    )
                                mMap.animateCamera(
                                    CameraUpdateFactory.newLatLngZoom(
                                        userLocation,
                                        15f
                                    )
                                )
                            }
                        }
                        true // Trả về true nếu bạn muốn xử lý sự kiện này
                    }
                }
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        Log.d("zzzz", "onMapReady: Da vao onMapReady")
        val addresses = listOf(
            "Cốm Vòng, Cầu Giấy, Hà Nội",
            "Mỹ Đình 2, Nam Từ Liêm, Hà Nội",
            "Mỹ Đình 1, Nam Từ Liêm, Hà Nội",
            "Mai Dịch, Cầu Giấy, Hà Nội",
        )
        addMarkersFromAddresses(googleMap, addresses, this)
        // Bật tính năng xác định vị trí người dùng
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true
            mMap.uiSettings.isMyLocationButtonEnabled = true
            mMap.uiSettings.isCompassEnabled = true
            mMap.uiSettings.isZoomControlsEnabled = true
            mMap.isTrafficEnabled = true

            mMap.setOnMyLocationButtonClickListener {
                fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                    if (location != null) {
                        Log.d("zzzzz", "onMapReady: $location")
                        val userLocation =
                            LatLng(location.latitude, location.longitude)
                        mMap.animateCamera(
                            CameraUpdateFactory.newLatLngZoom(
                                userLocation,
                                15f
                            )
                        )
                    }
                }
                true // Trả về true nếu bạn muốn xử lý sự kiện này
            }
        } else {
            // Nếu chưa có quyền, yêu cầu quyền vị trí từ người dùng
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
        }
        // Add a marker in Sydney and move the camera
        val hanoi = LatLng(21.0285, 105.8542)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hanoi, 10.0f))

    }


    fun getCoordinatesUsingNominatim(address: String, onResult: (LatLng?) -> Unit) {
        val encodedAddress = URLEncoder.encode(address, "UTF-8")
        val url =
            "https://nominatim.openstreetmap.org/search?q=${encodedAddress}&format=json&limit=1"

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val connection = URL(url).openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.setRequestProperty("User-Agent", "Mozilla/5.0 (compatible)")

                val response = connection.inputStream.bufferedReader().use { it.readText() }
                connection.disconnect()

                CoroutineScope(Dispatchers.Main).launch {
                    // Parse JSON and extract latitude and longitude
                    val jsonArray = JSONArray(response)
                    if (jsonArray.length() > 0) {
                        val firstResult = jsonArray.getJSONObject(0)
                        val lat = firstResult.getString("lat")
                        val lon = firstResult.getString("lon")
                        Log.d("Nominatimzzzzzzzzzzz", "Latitude: $lat, Longitude: $lon")
                        onResult(LatLng(lat.toDouble(), lon.toDouble()))
                    } else {
                        Log.d("Nominatimzzzzzzzzzzzzzz", "No results found.")
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun resizeBitmap(resourceId: Int, context: Context, width: Int, height: Int): Bitmap {
        val bitmap = BitmapFactory.decodeResource(context.resources, resourceId)
        return Bitmap.createScaledBitmap(bitmap, width, height, false)
    }

    // Hàm tạo custom icon
    fun createCustomIcon(text: String): BitmapDescriptor {
        // Bước 1: Khởi tạo Paint để vẽ chữ
        val paint = Paint().apply {
            color = Color.BLACK  // Chọn màu sắc cho chữ là đen
            textSize = 40f       // Kích thước chữ là 40px
            isAntiAlias = true   // Bật chế độ anti-aliasing để chữ mượt mà hơn
        }

        // Bước 2: Tính toán chiều rộng và chiều cao của chữ
        val textWidth = paint.measureText(text)  // Tính chiều rộng của text
        val textHeight =
            paint.fontMetrics.bottom - paint.fontMetrics.top  // Tính chiều cao của text (từ dòng trên đến dòng dưới)

        // Bước 3: Tạo Bitmap để vẽ chữ lên
        val bitmap = Bitmap.createBitmap(
            textWidth.toInt() + 50,
            textHeight.toInt() + 50,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)  // Tạo Canvas để vẽ lên Bitmap

        // Bước 4: Vẽ text lên Bitmap
        canvas.drawText(
            text,
            25f,
            textHeight.toFloat(),
            paint
        )  // Vẽ chữ vào vị trí (25, textHeight) trên Canvas

        // Bước 5: Trả về BitmapDescriptor từ Bitmap đã tạo
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    fun createCustomMarkerWithLayoutXML(
        context: Context,
        text: String,
        iconResId: Int
    ): BitmapDescriptor {
        // Inflate layout từ XML
        val layoutInflater = LayoutInflater.from(context)
        val layout = layoutInflater.inflate(R.layout.custom_marker, null)

        // Cập nhật TextView với nội dung văn bản
        val textView = layout.findViewById<TextView>(R.id.markerText)
        textView.text = text

        // Cập nhật ImageView với icon
        val imageView = layout.findViewById<ImageView>(R.id.markerIcon)
        imageView.setImageResource(iconResId)

        // Đảm bảo layout có kích thước phù hợp
        layout.measure(
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        )
        layout.layout(0, 0, layout.measuredWidth, layout.measuredHeight)

        // Tạo bitmap từ layout đã được đo
        val bitmap = Bitmap.createBitmap(
            layout.measuredWidth,
            layout.measuredHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        layout.draw(canvas)  // Vẽ layout lên canvas

        // Trả về BitmapDescriptor
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }


    // Hàm gọi hàm trên cho nhiều địa chỉ và thêm vào bản đồ
    fun addMarkersFromAddresses(map: GoogleMap, addresses: List<String>, context: Context) {
        for (address in addresses) {
            getCoordinatesUsingNominatim(address) { latLng ->
                latLng?.let {
                    map.addMarker(
                        MarkerOptions()
                            .position(it)
                            .title(address)
                            .icon(
                                createCustomMarkerWithLayoutXML(
                                    this,
                                    address,
                                    R.drawable.icon_board
                                )
                            )
                    )
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(it, 10f))
                }
            }
        }

    }

}