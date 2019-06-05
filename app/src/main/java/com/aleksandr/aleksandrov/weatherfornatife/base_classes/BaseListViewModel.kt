package com.aleksandr.aleksandrov.weatherfornatife.base_classes

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

/**
 * Created by Alexandrov Alex on 2019-06-04.
 */
open class BaseListViewModel<T> : ViewModel() {

    private var isDataFetching: MutableLiveData<Boolean> = MutableLiveData()

    private var isRefreshEnabled: MutableLiveData<Boolean> = MutableLiveData()

    private var data: MutableLiveData<MutableList<T>> = MutableLiveData()

    fun setDataFetching(isFetching: Boolean) {
        isDataFetching.postValue(isFetching)
    }

    fun isDataFetching(): MutableLiveData<Boolean> {
        if (isDataFetching.value == null) isDataFetching.value = false
        return isDataFetching
    }

    fun setRefreshEnable(isEnable: Boolean) {
        isRefreshEnabled.postValue(isEnable)
    }

    fun isRefreshEnabled(): MutableLiveData<Boolean> {
        return isRefreshEnabled
    }

    fun setData(data: MutableList<T>) {
        this.data.value = data
    }

    fun getData(): MutableLiveData<MutableList<T>> {
        return data
    }

}
