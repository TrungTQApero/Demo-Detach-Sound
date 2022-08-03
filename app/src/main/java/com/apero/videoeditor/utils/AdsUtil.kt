package com.apero.videoeditor.utils

object AdsUtil {
    val REMOTE_SPLASH = "ad_splash"
    val REMOTE_INTER_EDIT_PHOTO = "ad_inter_edit_photo"
    val REMOTE_INTER_SECTION = "ad_inter_section"
    val REMOTE_INTER_MY_CUSTOM_SECTION = "ad_inter_my_custom_section"
    val REMOTE_BANNER = "ad_banner"
    val NUM_TO_SHOW_ADS = "num_to_show_ads"
    val REMOTE_NATIVE = "ad_native_add_more_section"
    val REMOTE_RESUME = "ad_resume"
    val REMOTE_INTER_CREATE_SECTION = "ad_inter_create_new_section"
    val REMOTE_INTER_VIEW_FILE = "ad_inter_view_file_template_download"
    val REMOTE_INTER_SAVE_SECTION = "ad_inter_save_edit_section"

    var isShowSplash: Boolean
        get() =  SharePreferencesManager.instance!!.getValueBool(REMOTE_SPLASH)
        set(value) =  SharePreferencesManager.instance!!.setValueBool(REMOTE_SPLASH, value)

}