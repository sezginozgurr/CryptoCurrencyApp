package com.example.cryptocurrencyapp.ui.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.cryptocurrencyapp.R
import com.example.cryptocurrencyapp.data.model.CoinListResponse
import com.example.cryptocurrencyapp.databinding.FragmentHomeBinding
import com.example.cryptocurrencyapp.ui.base.BaseFragment
import com.example.cryptocurrencyapp.util.Constants
import com.example.cryptocurrencyapp.util.Resource
import com.example.cryptocurrencyapp.util.Status
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val viewModel by viewModel<HomeViewModel>()
    private lateinit var adapter: CoinRecylerAdapter
    private var refreshPeriod = Constants.EMPTY_STRING

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    private fun initialize() {
        if (arguments != null && requireArguments().containsKey("period")) {
            refreshPeriod = requireArguments().getString("period").toString()
        }
        setObservers()
        setClickListener()
        loadRecycler()

        if (refreshPeriod.isNotEmpty()) {
            refreshPeriod.toIntOrNull()?.let { setRefreshPeriod(it) }
        }
    }

    private fun setObservers() {
        viewModel.getCoinList().observe(viewLifecycleOwner, getCoinListObserver)
    }

    private fun setClickListener() {
        binding.edtSarch.addTextChangedListener(searchChangedListener)
    }

    private fun loadRecycler() {
        adapter = CoinRecylerAdapter()
        binding.recyclerViewCoins.adapter = adapter
        adapter.onClickItem {
            val data = bundleOf(Constants.COIN_ID to it.id)
            findNavController().navigate(R.id.coinDetailFragment, data)
        }
    }

    private val getCoinListObserver = Observer<Resource<List<CoinListResponse>>> { state ->
        when (state.status) {
            Status.LOADING -> {
                binding.homeProgress.visibility = View.VISIBLE
            }
            Status.SUCCESS -> {
                binding.homeProgress.visibility = View.GONE
                state.data?.let {
                    adapter.coins.clear()
                    adapter.coins.addAll(it)
                    adapter.notifyDataSetChanged()
                }
            }
            Status.ERROR -> {
                binding.homeProgress.visibility = View.GONE
            }
        }
    }

    private val searchChangedListener = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            //seacrh i??lemi
            adapter.filter.filter(s.toString())
            adapter.notifyDataSetChanged()
        }

    }

    private fun setRefreshPeriod(period: Int) {
        viewLifecycleOwner.lifecycleScope.launch {
            while (true) {
                viewModel.getCoinList().observe(viewLifecycleOwner, getCoinListObserver)
                val delay = period * 60000
                delay(delay.toLong())
            }
        }
    }

    override fun getFragmentView(): Int = R.layout.fragment_home

}