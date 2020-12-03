package com.khapp.suji.view.user

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.khapp.suji.Config
import com.khapp.suji.Constance
import com.khapp.suji.R
import com.khapp.suji.view.login.LoginActivity
import kotlinx.android.synthetic.main.fragment_user.view.*

class UserFragment : Fragment() {
    var root: View? = null
    var currencyDialog: SelectCurrencyDialog? = null
    var languageDialog: SelectLanguageDialog? = null
    var themeDialog: SelectThemeDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_user, container, false)
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
            fu_tv_theme_value.text = Config.theme.description
            fu_tv_language_value.text = Config.language.description
            fu_tv_currency_value.text = Config.currency.description
        }
    }

    private fun initListeners() {
        root?.apply {
            //todo 选择后设置config并应用修改后的配置
            fu_item_theme.setOnClickListener {
                themeDialog?.setOKListener {

                }?.show()
            }
            fu_item_language.setOnClickListener {
                languageDialog?.setOKListener {

                }?.show()
            }
            fu_item_currency.setOnClickListener {

                currencyDialog?.setOKListener {

                }?.show()
            }
            fu_item_notification.setOnClickListener {

            }
            fu_item_about.setOnClickListener {

            }
            fu_item_logout.setOnClickListener {

            }
            fu_iv_user_icon.setOnClickListener {
                if (Constance.userId == -1L)
                    startActivity(Intent(requireContext(), LoginActivity::class.java))
            }
        }
    }

    private fun initObservers() {
        root?.apply {

        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            UserFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}