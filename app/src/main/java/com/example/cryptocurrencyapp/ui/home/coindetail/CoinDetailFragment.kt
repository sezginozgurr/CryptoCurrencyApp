package com.example.cryptocurrencyapp.ui.home.coindetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.cryptocurrencyapp.R
import com.example.cryptocurrencyapp.data.model.CoinDetailResponse
import com.example.cryptocurrencyapp.databinding.FragmentCoinDetailBinding
import com.example.cryptocurrencyapp.ui.base.BaseFragment
import com.example.cryptocurrencyapp.util.Constants
import com.example.cryptocurrencyapp.util.Resource
import com.example.cryptocurrencyapp.util.Status
import org.koin.androidx.viewmodel.ext.android.viewModel

class CoinDetailFragment : BaseFragment<FragmentCoinDetailBinding>() {

    private val viewModel by viewModel<CoinDetailViewModel>()
    private var coinID = Constants.EMPTY_STRING
    private var coinImage = Constants.EMPTY_STRING
    private var priceChange = Constants.EMPTY_STRING

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    private fun initialize() {
        coinID = requireArguments().getString(Constants.COIN_ID).toString()
        setObserver()
    }

    private fun setObserver() {
        viewModel.getCoinDetail(coinID).observe(viewLifecycleOwner, getCoinDetailObserver)
    }

    private val getCoinDetailObserver = Observer<Resource<CoinDetailResponse>> { state ->
        when (state.status) {
            Status.LOADING -> {
                binding.coinDetailProgress.visibility = View.VISIBLE
            }
            Status.SUCCESS -> {
                state.data?.let {
                    binding.coinDetailProgress.visibility = View.GONE
                    binding.coinDateilViewState = CoinDetailItemViewState(it) //setup UI
                }
            }
            Status.ERROR -> {
                binding.coinDetailProgress.visibility = View.GONE
            }
        }
    }

    override fun getFragmentView(): Int = R.layout.fragment_coin_detail

}