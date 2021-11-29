package com.example.cryptocurrencyapp.ui.home.coindetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.cryptocurrencyapp.data.model.CoinDetailResponse
import com.example.cryptocurrencyapp.databinding.FragmentCoinDetailBinding
import com.example.cryptocurrencyapp.util.Resource
import com.example.cryptocurrencyapp.util.Status
import org.koin.androidx.viewmodel.ext.android.viewModel

class CoinDetailFragment : Fragment() {

    private var _binding: FragmentCoinDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<CoinDetailViewModel>()
    private var coinID = ""
    private var coinImage = ""
    private var priceChange = ""
    private var isFavorite = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        coinID = requireArguments().getString("coinID").toString()
        _binding = FragmentCoinDetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    private fun initialize() {
        setObserver()
    }

    private fun setObserver() {
        viewModel.getCoinDetail().observe(viewLifecycleOwner, getCoinDetailObserver)
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

                    coinImage = it.image?.large.toString()
                    priceChange = it.market_data?.price_change_percentage_24h.toString()
                }
            }
            Status.ERROR -> {
                binding.coinDetailProgress.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}