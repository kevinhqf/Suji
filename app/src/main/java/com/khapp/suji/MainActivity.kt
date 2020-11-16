package com.khapp.suji

import android.Manifest
import android.animation.ObjectAnimator
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import com.khapp.suji.data.NoteType
import com.khapp.suji.utils.InjectorUtils
import com.khapp.suji.utils.LocationUtils
import com.khapp.suji.utils.ScreenUtils
import com.khapp.suji.view.addition.AdditionDialog
import com.khapp.suji.view.analysis.AnalysisFragment
import com.khapp.suji.view.comm.OnMainPageScrollListener
import com.khapp.suji.view.home.HomeFragment
import com.khapp.suji.viewmodel.AdditionViewModel
import com.khapp.suji.viewmodel.TransactionViewModel
import com.tencent.map.geolocation.TencentLocation
import com.tencent.map.geolocation.TencentLocationListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.menu_main.*
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions


class MainActivity : AppCompatActivity() {
    companion object {
        const val RC_LOCATION_PHONE_STORAGE: Int = 1
        const val HOME_POSITION = 1
        const val DATA_POSITION = 2
        const val USER_POSITION = 3
    }

    private val additionViewModel: AdditionViewModel by viewModels {
        InjectorUtils.provideAdditionViewModelFactory()
    }
    private val transactionViewModel: TransactionViewModel by viewModels {
        InjectorUtils.provideTransactionViewModelFactory()
    }


    private lateinit var additionDialog: AdditionDialog
    private val homeFragment = HomeFragment.newInstance()
    private val analysisFragment = AnalysisFragment.newInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initData()
        initViews()
        initListeners()
        initObservers()
        requestPermissions()
    }

    private fun initData() {

    }


    private fun requestLocation() {
        LocationUtils.requestLocation(this, object : TencentLocationListener {
            override fun onStatusUpdate(p0: String?, p1: Int, p2: String?) {

            }

            override fun onLocationChanged(
                location: TencentLocation?,
                error: Int,
                reason: String?
            ) {
                location?.let {
                    Constance.lat = it.latitude
                    Constance.lng = it.longitude
                    Constance.address = it.address
                    Log.e(
                        "onLocationChanged: ",
                        "lat=${it.latitude},lng=${it.longitude},address=${it.address}"
                    )
                }
            }
        })
    }

    private fun clearMenuItemState() {
        mm_iv_home.setImageResource(R.mipmap.icon_home_normal)
        mm_iv_data.setImageResource(R.mipmap.icon_data_normal)
        mm_iv_user.setImageResource(R.mipmap.icon_user_normal)
    }

    private fun changeMenuItemState(position: Int) {
        clearMenuItemState()
        when (position) {
            HOME_POSITION -> mm_iv_home.setImageResource(R.mipmap.icon_home_active)
            DATA_POSITION -> mm_iv_data.setImageResource(R.mipmap.icon_data_active)
            USER_POSITION -> mm_iv_user.setImageResource(R.mipmap.icon_user_active)
        }
    }

    private fun initObservers() {
        additionViewModel.newValues.observe(this, Observer {
            additionDialog.updateMoneyValue(it)
        })
        additionViewModel.dataTypes.observe(this, Observer {
            additionDialog.updateDataTypeList(it)
        })
        additionViewModel.newDataType.observe(this, Observer {
            if (it == null) {
                additionDialog.updateIcon(R.mipmap.icon_pic)
                additionDialog.updateMoneySign(NoteType.INCOME.value)
            } else {
                additionDialog.updateIcon(it.icon)
                additionDialog.updateMoneySign(it.type)
            }
        })

    }

    var isMenuHide = false
    private fun initListeners() {
        additionDialog.initListeners(additionViewModel)
        mm_add.setOnClickListener {
            additionDialog.show()
        }
        //滑动隐藏menu
        val height = ScreenUtils.getHeight(this).toFloat()
        val y = am_menu.y
        val scrollListener = object : OnMainPageScrollListener {
            override fun onScrollUp() {
                if (!isMenuHide) {
                    isMenuHide = true
                    Log.e("onScrolled: ", "hide")
                    am_menu.animate().translationY(height).setDuration(400).start()

                }
            }

            override fun onScrollDown() {
                if (isMenuHide) {
                    isMenuHide = false
                    Log.e("onScrolled: ", "show")
                    am_menu.animate().translationY(y).setDuration(300).start()

                }
            }

        }

        homeFragment.scrollListener = scrollListener
        analysisFragment.scrollListener = scrollListener
        mm_iv_home.setOnClickListener {
            changeFragment(HOME_POSITION)
        }
        mm_iv_data.setOnClickListener {
            changeFragment(DATA_POSITION)
        }
        mm_iv_user.setOnClickListener {
            changeFragment(USER_POSITION)
        }


    }

    private fun initViews() {
        additionDialog = AdditionDialog(this)
        supportFragmentManager.commit {
            replace(R.id.am_content, homeFragment)
        }
    }

    private fun changeFragment(position: Int){
        changeMenuItemState(position)
        when(position){
            HOME_POSITION->{
                supportFragmentManager.commit {
                    replace(R.id.am_content, homeFragment)
                }
            }
            DATA_POSITION->{
                supportFragmentManager.commit {
                    replace(R.id.am_content, analysisFragment)
                }
            }
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    @AfterPermissionGranted(RC_LOCATION_PHONE_STORAGE)
    private fun requestPermissions() {
        val permissions = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        val permissionsForQ = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION,  //target为Q时，动态请求后台定位权限
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        if (EasyPermissions.hasPermissions(this, *permissions)) {
            requestLocation()
        } else {
            if (if (Build.VERSION.SDK_INT >= 29) EasyPermissions.hasPermissions(
                    this,
                    *permissionsForQ
                ) else EasyPermissions.hasPermissions(this, *permissions)
            ) {
                Toast.makeText(this, "权限OK", Toast.LENGTH_LONG).show()
            } else {
                EasyPermissions.requestPermissions(
                    this,
                    "需要权限",
                    RC_LOCATION_PHONE_STORAGE,
                    *if (Build.VERSION.SDK_INT >= 29) permissionsForQ else permissions
                )
            }
        }

    }
}