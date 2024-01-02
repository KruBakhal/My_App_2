package com.example.my_app_2.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<T : ViewDataBinding>(@LayoutRes val layoutId: Int) : Fragment() {

    private var useBus = false
    var setupUI = true // To hideKeyboard for non-editable view click.
    lateinit var binding: T


    abstract fun init(): Boolean

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        useBus = init()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (setupUI) {
//            binding.root.setupUI()
        }
    }

    override fun onStart() {
        super.onStart()
        if (useBus) {
//            EventBus.getDefault().register(this)
        }
    }

    override fun onStop() {
        if (useBus) {
//            EventBus.getDefault().unregister(this)
        }
        super.onStop()
    }

    override fun onDestroyView() {

        super.onDestroyView()
    }


}