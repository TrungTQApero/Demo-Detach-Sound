package com.apero.videoeditor.utils

import android.content.Context
import android.content.SharedPreferences
import kotlin.jvm.Synchronized

class SharePreferencesManager private constructor(context: Context) {
    private val mPref: SharedPreferences
    fun setValue(key: String?, value: String?) {
        mPref.edit()
            .putString(key, value)
            .apply()
    }

    fun getValue(key: String?, default: String?): String? {
        return mPref.getString(key, default)
    }

    fun getValue(key: String?): String? {
        return mPref.getString(key, "")
    }

    fun setIntValue(key: String?, value: Int) {
        mPref.edit()
            .putInt(key, value)
            .apply()
    }

    fun getIntValue(key: String?): Int {
        return mPref.getInt(key, 0)
    }

    fun setValueBool(key: String?, value: Boolean) {
        mPref.edit()
            .putBoolean(key, value)
            .apply()
    }

    fun getValueBool(key: String?): Boolean {
        return mPref.getBoolean(key, false)
    }

    fun remove(key: String?) {
        mPref.edit()
            .remove(key)
            .apply()
    }

    fun clear(): Boolean {
        return mPref.edit()
            .clear()
            .commit()
    }

    companion object {
        private const val PREF_NAME = "AperoApp_"
        private var sInstance: SharePreferencesManager? = null
        @Synchronized
        fun initializeInstance(context: Context) {
            if (sInstance == null) {
                sInstance = SharePreferencesManager(context)
            }
        }

        @get:Synchronized
        val instance: SharePreferencesManager?
            get() {
                checkNotNull(sInstance) {
                    SharePreferencesManager::class.java.simpleName +
                            " is not initialized, call initializeInstance(..) method first."
                }
                return sInstance
            }
    }

    init {
        mPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }
}