package com.khapp.suji.utils

import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.Process
import com.khapp.suji.MainActivity
import com.khapp.suji.preset.Language
import java.util.*
import kotlin.system.exitProcess

/**
 * 通过保存参数到本地，重启应用读取参数进行切换
 */
object LanguageUtils {

    /**
     * 在需要切换语言的地方调用该方法，
     * @param context   所在 activity
     * @param lang  切换的语言 eg:  SIMPLIFIED_CHINESE
     */
    fun changeLanguage(context: Context) {
        reLoadMainActivity(context)
    }

    /**
     * 重新加载MainActivity
     * @param activity
     */
    private fun reLoadMainActivity(activity: Context) {
        val intent = Intent(activity, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        activity.startActivity(intent)
        // 杀掉进程
        Process.killProcess(Process.myPid())
        exitProcess(0)
    }

    private fun applyLanguage(context: Context?, language: Language){
        val resources = context?.resources
        val configuration = resources?.configuration
        val locale = when(language){
            Language.CHN -> Locale.SIMPLIFIED_CHINESE
            Language.ENG -> Locale.ENGLISH
        }
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
            configuration?.setLocale(locale)
        }else{
            configuration?.locale = locale
            val displayMetrics = resources?.displayMetrics
            resources?.updateConfiguration(configuration, displayMetrics)
        }

    }

    /**
     * 切换应用内语言调用该方法
     * @param context
     * @param language
     * @return
     */
    fun attachBaseContext(context: Context?, language: Language): Context? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            createConfigurationResources(context, language)
        } else {
            applyLanguage(context, language)
            context
        }
    }

    @TargetApi(Build.VERSION_CODES.N)
    private fun createConfigurationResources(context: Context?, language: Language): Context? {
        val resources = context?.resources
        val configuration = resources?.configuration
        val locale = when(language){
            Language.CHN -> Locale.SIMPLIFIED_CHINESE
            Language.ENG -> Locale.ENGLISH
        }
        configuration?.setLocale(locale)
        return configuration?.let { context.createConfigurationContext(it) }
    }


}