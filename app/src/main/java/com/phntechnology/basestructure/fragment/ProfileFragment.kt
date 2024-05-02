package com.phntechnology.basestructure.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.commonutils.util.backPressedHandle
import com.example.commonutils.util.showView
import com.phntechnology.basestructure.adapters.GenericAdapter
import com.phntechnology.basestructure.databinding.FragmentProfileBinding
import com.phntechnology.basestructure.databinding.HighlightsListItemBinding
import com.phntechnology.basestructure.model.DemoModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null

    private val binding get() = _binding!!

    private var _adapter: GenericAdapter<DemoModel, HighlightsListItemBinding>? = null

    private val adapter get() = _adapter!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        backPressedHandle {
            requireActivity().finishAffinity()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeRefresh()

        initializeAdapter()

        initializeData()

    }

    private fun initializeData() {
        val list = ArrayList<DemoModel>()
        list.add(DemoModel("New"))
        list.add(DemoModel("Friends"))
        list.add(DemoModel("Sport"))
        list.add(DemoModel("Design"))
        adapter.setData(list)
    }

    private fun initializeAdapter() {
        _adapter = GenericAdapter(
            HighlightsListItemBinding::inflate,
            onBind = { itemData, itemBinding, position, listSize ->
                itemBinding.apply {
                    if (position != 0) {
                        highLightImg.showView()
                    }
                    highLightText.text = itemData.name

                }
            })
        binding.highLightRv.adapter = adapter

    }

    private fun initializeRefresh() {
        binding.igRefresh.setOnRefreshListener {
            Handler(Looper.getMainLooper()).postDelayed(
                { binding.igRefresh.isRefreshing = false },
                5000
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}