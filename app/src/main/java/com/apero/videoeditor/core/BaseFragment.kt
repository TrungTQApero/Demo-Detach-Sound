package com.apero.videoeditor.core

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.library.baseAdapters.BR
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import dagger.android.AndroidInjection

/**
 * Created by Furkan on 2019-10-16
 */

abstract class BaseFragment<VM : BaseViewModel, DB : ViewDataBinding>(@LayoutRes val layout: Int, viewModelClass: Class<VM>) : Fragment() {

    open lateinit var binding: DB
    lateinit var dataBindingComponent: DataBindingComponent
    lateinit var myContext: Context

    private fun initView(inflater: LayoutInflater, container: ViewGroup) {
        binding = DataBindingUtil.inflate(inflater, layout, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.setVariable(BR.viewModel, viewModel)
    }

    //gọi 1 lần khi create fragment
    open fun initData() {}
    //gọi mỗi lần show fragment
    open fun initView() {}

    private val viewModel by lazy {
        (activity as? BaseActivity<*, *>)?.viewModelProviderFactory?.let { ViewModelProvider(this, it).get(viewModelClass) }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        myContext = context
    }

    open fun onInject() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(activity)
        super.onCreate(savedInstanceState)
        initData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.e("BaseFragment", "onCreateView: "  )
        initView(inflater, container!!)
        initView()
        super.onCreateView(inflater, container, savedInstanceState)
        return binding.root
    }

    open fun refresh() {

    }

    open fun navigate(action: Int) {
        view?.let { _view ->
            Navigation.findNavController(_view).navigate(action)
        }
    }

    open fun navigate(action: Int, bundle: Bundle) {
        view?.let { _view ->
            Navigation.findNavController(_view).navigate(action, bundle)
        }
    }


//    fun getPermission(): Array<String> {
//        return arrayOf(
//            Manifest.permission.READ_EXTERNAL_STORAGE,
//            Manifest.permission.WRITE_EXTERNAL_STORAGE
//        )
//    }


//    @RequiresApi(Build.VERSION_CODES.M)
//    fun checkPermission(per: Array<String>): Boolean {
//        for (s in per) {
//            if (requireActivity().checkSelfPermission(s) != PackageManager.PERMISSION_GRANTED) {
//                return false
//            }
//        }
//        return true
//    }

}
