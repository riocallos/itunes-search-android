package com.riocallos.itunessearch.base

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.riocallos.itunessearch.R
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

abstract class BaseActivity<B : ViewDataBinding, T> : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    lateinit var binding: B

    protected val disposables: CompositeDisposable = CompositeDisposable()

    @Inject
    lateinit var schedulers: BaseSchedulerProvider

    @LayoutRes
    abstract fun getLayoutId(): Int

    private var loadingDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(
            this,
            getLayoutId()
        )

        binding.lifecycleOwner = this

    }

    open fun hideKeyboard() {
        val inputManager: InputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        currentFocus?.let {
            inputManager.hideSoftInputFromWindow(it.windowToken, InputMethodManager.SHOW_FORCED)
        }
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = dispatchingAndroidInjector

    open fun dismissDialogs() {
        try {
            if (loadingDialog != null && loadingDialog!!.isShowing) {
                loadingDialog!!.dismiss()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    open fun showLoadingDialog() {
        dismissDialogs()
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(false)
        builder.setView(layoutInflater.inflate(R.layout.loading, null))
        loadingDialog = builder.create()
        loadingDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        loadingDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        loadingDialog!!.show()
    }

}