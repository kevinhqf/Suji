package com.khapp.suji.view.settings

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.khapp.suji.Constance
import com.khapp.suji.MainActivity
import com.khapp.suji.R
import com.khapp.suji.preset.Theme
import com.khapp.suji.ui.AppAlertDialog
import com.khapp.suji.ui.LoadingDialog
import com.khapp.suji.utils.InjectorUtils
import com.khapp.suji.utils.LanguageUtils
import com.khapp.suji.view.comm.BaseActivity
import com.khapp.suji.view.comm.OnMainPageScrollListener
import com.khapp.suji.view.login.LoginActivity
import com.khapp.suji.viewmodel.MainViewModel
import com.khapp.suji.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_settings.view.*

class SettingsFragment : Fragment() {
    var root: View? = null
    var currencyDialog: SelectCurrencyDialog? = null
    var languageDialog: SelectLanguageDialog? = null
    var themeDialog: SelectThemeDialog? = null
    var profileDialog: UserProfileDialog? = null
    var scrollListener: OnMainPageScrollListener? = null

    private val userViewModel: UserViewModel by activityViewModels {
        InjectorUtils.provideUserViewModelFactory()
    }
    private val mainViewModel: MainViewModel by activityViewModels {
        InjectorUtils.provideMainViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_settings, container, false)
        initViews()
        initListeners()
        initObservers()
        return root
    }

    private fun initViews() {
        root?.apply {
            currencyDialog = SelectCurrencyDialog(requireContext())
            languageDialog = SelectLanguageDialog(requireContext())
            themeDialog = SelectThemeDialog(requireContext())
            profileDialog = UserProfileDialog(requireContext(), userViewModel)
            fs_tv_theme_value.text = Constance.config.theme.getDescriptionStr(requireContext())
            fs_tv_language_value.text = Constance.config.language.description
            fs_tv_currency_value.text = Constance.config.currency.description
        }
    }


    private fun initListeners() {
        root?.apply {
            fs_item_theme.setOnClickListener {
                themeDialog?.setOKListener {
                    mainViewModel.saveConfig(theme = it) {
                        val mode = when (it) {
                            Theme.AUTO -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                            Theme.LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
                            Theme.DARK -> AppCompatDelegate.MODE_NIGHT_YES
                        }
                        (requireActivity() as BaseActivity).delegate.localNightMode = mode
                        requireActivity().recreate()
                    }
                }?.show()
            }
            fs_item_language.setOnClickListener {
                languageDialog?.setOKListener {
                    LoadingDialog(requireContext()).show()
                    mainViewModel.saveConfig(language = it) {
                        Handler().postDelayed(Runnable {
                            //切换语言
                            LanguageUtils.changeLanguage(requireContext())
                        }, 2000)
                    }
                }?.show()
            }
            fs_item_currency.setOnClickListener {
                currencyDialog?.setOKListener {
                    mainViewModel.saveConfig(currency = it) {

                    }
                }?.show()
            }
            fs_item_notification.setOnClickListener {

            }
            fs_item_about.setOnClickListener {

            }
            //退出登录
            fs_item_logout.setOnClickListener {
                if (Constance.userId != -1L) {
                    AppAlertDialog(requireContext())
                        .message(context.getString(R.string.logout_dialog_alert))
                        .ok(context.getString(R.string.confirm_text)) { userViewModel.logout() }
                        .show()

                }
            }
            //个人设置
            fs_iv_user_icon.setOnClickListener {
                if (Constance.userId == -1L) {
                    startActivity(Intent(requireContext(), LoginActivity::class.java))
                } else {
                    profileDialog?.show()
                }
            }
            //滑动监听
            fs_scrollView.setOnScrollChangeListener { _: NestedScrollView?, _: Int, scrollY: Int, _: Int, oldScrollY: Int ->
                if (scrollY - oldScrollY > MainActivity.SCROLL_DISTANCE) {
                    // 向上滑动
                    scrollListener?.onScrollUp()
                } else if (scrollY - oldScrollY < -MainActivity.SCROLL_DISTANCE) {
                    // 向下滑动
                    scrollListener?.onScrollDown()
                }
            }
        }
    }

    //todo user和配置config绑定
    private fun initObservers() {
        root?.apply {
            userViewModel.user.observe(viewLifecycleOwner, Observer {
                if (it.id == -1L) {
                    fs_tv_user_name.text = context.getString(R.string.please_login_text)
                    fs_iv_user_icon.setImageResource(R.mipmap.icon_user)
                    fs_setting_logout.visibility = View.INVISIBLE
                } else {
                    fs_setting_logout.visibility = View.VISIBLE
                    fs_tv_user_name.text = it.name
                    Glide.with(requireContext())
                        .load(it.getAvatarUrl())
                        .apply(RequestOptions.bitmapTransform(CircleCrop()))
                        .error(R.mipmap.icon_user)
                        .placeholder(R.mipmap.icon_user)
                        .into(fs_iv_user_icon)
                }
            })

            mainViewModel.appConfig.observe(viewLifecycleOwner, Observer {
                fs_tv_theme_value.text = it.theme.getDescriptionStr(requireContext())
                fs_tv_language_value.text = it.language.description
                fs_tv_currency_value.text = it.currency.description
            })
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            SettingsFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}