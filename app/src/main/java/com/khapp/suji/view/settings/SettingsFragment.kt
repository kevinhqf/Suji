package com.khapp.suji.view.settings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.khapp.suji.Config
import com.khapp.suji.Constance
import com.khapp.suji.R
import com.khapp.suji.utils.InjectorUtils
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
    private val mainViewModel: MainViewModel by activityViewModels {
        InjectorUtils.provideMainViewModelFactory()
    }

    private val userViewModel: UserViewModel by activityViewModels {
        InjectorUtils.provideUserViewModelFactory()
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
            fs_tv_theme_value.text = Config.theme.description
            fs_tv_language_value.text = Config.language.description
            fs_tv_currency_value.text = Config.currency.description
        }
    }


    private fun initListeners() {
        root?.apply {
            //todo 选择后设置config并应用修改后的配置
            fs_item_theme.setOnClickListener {
                themeDialog?.setOKListener {

                }?.show()
            }
            fs_item_language.setOnClickListener {
                languageDialog?.setOKListener {

                }?.show()
            }
            fs_item_currency.setOnClickListener {

                currencyDialog?.setOKListener {

                }?.show()
            }
            fs_item_notification.setOnClickListener {

            }
            fs_item_about.setOnClickListener {

            }
            fs_item_logout.setOnClickListener {
                if (Constance.userId != -1L)
                    userViewModel.logout()
            }
            fs_iv_user_icon.setOnClickListener {
                if (Constance.userId == -1L) {
                    startActivity(Intent(requireContext(), LoginActivity::class.java))
                } else {
                    profileDialog?.show()
                }
            }
        }
    }

    //todo user和配置config绑定
    private fun initObservers() {
        root?.apply {
            userViewModel.user.observe(viewLifecycleOwner, Observer {
                if (it.id == -1L) {
                    fs_tv_user_name.text = "请登录"
                    fs_iv_user_icon.setImageResource(R.mipmap.icon_user)
                } else {
                    fs_tv_user_name.text = it.name
                    Glide.with(requireContext())
                        .load(it.getAvatarUrl())
                        .apply(RequestOptions.bitmapTransform(CircleCrop()))
                        .error(R.mipmap.icon_user)
                        .placeholder(R.mipmap.icon_user)
                        .into(fs_iv_user_icon)
                }
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