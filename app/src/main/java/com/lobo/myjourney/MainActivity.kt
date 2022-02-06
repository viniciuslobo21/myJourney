package com.lobo.myjourney

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.utils.ViewState
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.lobo.myjourney.common.base.BaseActivity
import com.lobo.myjourney.common.utils.extensions.visible
import com.lobo.myjourney.databinding.ActivityMainBinding
import com.lobo.myjourney.presentation.JourneyAdapter
import com.lobo.myjourney.presentation.MainViewActions
import com.lobo.myjourney.presentation.MainViewModel
import com.lobo.myjourney.presentation.MainViewState
import com.lobo.myjourney.presentation.model.JourneyUiModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val viewModel: MainViewModel by viewModel()

    override fun getViewBinding() = ActivityMainBinding.inflate(LayoutInflater.from(this))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeViewModel()
        addListeners()
        viewModel.dispatchViewAction(MainViewActions.Init)
    }

    private fun observeViewModel() {
        viewModel.viewState.journeysList.observe(this) {
            binding.swipe.isRefreshing = false
            loadItems(it)
        }

        viewModel.viewState.state.observe(this) { state ->
            when (state) {
                MainViewState.State.LOADING -> {
                    binding.swipe.isRefreshing = false
                    display(loading = true)
                }
                MainViewState.State.ERROR -> {
                    display(error = true)
                }
                MainViewState.State.SUCCESS -> {
                    display(content = true)
                }
            }
        }
    }

    private fun addListeners() {
        binding.swipe.setOnRefreshListener {
            viewModel.dispatchViewAction(MainViewActions.Init)
        }
    }

    private fun loadItems(list: List<JourneyUiModel>) {
        binding.swipe.setColorSchemeColors(ContextCompat.getColor(this, R.color.blue))
        binding.rvItems.adapter = JourneyAdapter(list) { journey ->
            showDialog(journey)
        }
    }

    private fun showDialog(journey: JourneyUiModel) {
        AlertDialog.Builder(this).apply {
            setTitle(journey.title)
            setMessage(journey.subtitle)
            setPositiveButton(R.string.ok) { dialog, _ ->
                dialog.dismiss()
            }
        }.show()
    }

    private fun display(
        content: Boolean = false,
        loading: Boolean = false,
        error: Boolean = false
    ) {
        binding.rvItems.isVisible = content
        binding.loading.isVisible = loading
        binding.errorContent.errorGroup.isVisible = error
    }
}