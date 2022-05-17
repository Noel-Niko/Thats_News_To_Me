package com.livingTechUSA.thatsnewstome.Base

import android.content.Context
import android.os.Bundle
import android.provider.Contacts
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import com.livingTechUSA.thatsnewstome.util.Ui

abstract class BaseFragment : Fragment() {
    private var mPresenter: BasePresenter? = null

    private val loadingView: View? by lazy { inflateHospiceLoaderLayout() }

    open fun inflateHospiceLoaderLayout(): View? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    @CallSuper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // create and set presenter here. All sub-classes need to call super to ensure it is set
        setPresenter(initPresenter())
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        mPresenter?.onStart()

    }

    override fun onResume() {
        super.onResume()
        if (mPresenter != null) {
            mPresenter?.onResume()
        }
    }

    override fun onDetach() {
        super.onDetach()
    }

    override fun onStop() {
        super.onStop()
        mPresenter?.onStop()
    }

    override fun onPause() {
        super.onPause()
        if (mPresenter != null) {
            mPresenter?.onPause()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mPresenter != null) {
            mPresenter?.onDestroy()
        }
    }


    fun setPresenter(presenter: BasePresenter?) {
        mPresenter = presenter
    }

    abstract fun initPresenter(): BasePresenter?


     fun showLoading(loading: Boolean) {
        if (loadingView != null) {
            loadingView?.bringToFront()
            loadingView?.visibility = if (loading) View.VISIBLE else View.GONE
        }
    }

    open fun isTablet(): Boolean = activity?.let { Ui.isTablet(it) } ?: false

    fun dismissKeyboard() {
        activity?.let { Ui.dismissKeyboard(it) }
    }

}