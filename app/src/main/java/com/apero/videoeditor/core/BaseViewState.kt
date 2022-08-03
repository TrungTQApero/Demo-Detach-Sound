package com.apero.videoeditor.core

import com.apero.videoeditor.utils.domain.Status


/**
 * Created by Furkan on 9.03.2020
 */

open class BaseViewState(val baseStatus: Status, val baseError: String?) {
    fun isLoading() = baseStatus == Status.LOADING
    fun getErrorMessage() = baseError
    fun shouldShowErrorMessage() = baseError != null
}
