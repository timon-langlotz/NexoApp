package com.adyen.nexoapp.util

import android.app.Application
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner

val FragmentActivity.viewModelProvider: ViewModelProvider
    get() = getViewModelProvider()

val Fragment.viewModelProvider: ViewModelProvider
    get() = getViewModelProvider()

fun FragmentActivity.getViewModelProvider(
    owner: ViewModelStoreOwner = this,
    factory: ViewModelProvider.Factory = getDefaultFactory(application)
): ViewModelProvider {
    return ViewModelProvider(owner, factory)
}

fun Fragment.getViewModelProvider(
    owner: ViewModelStoreOwner = this,
    factory: ViewModelProvider.Factory = getDefaultFactory(requireActivity().application)
): ViewModelProvider {
    return ViewModelProvider(owner, factory)
}

private fun getDefaultFactory(application: Application): ViewModelProvider.Factory {
    return ViewModelProvider.AndroidViewModelFactory(application)
}
