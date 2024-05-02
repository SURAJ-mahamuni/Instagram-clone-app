package com.phntechnology.basestructure.fragment

import android.animation.Animator
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.commonutils.util.backPressedHandle
import com.example.commonutils.util.hideView
import com.example.commonutils.util.hideViewVisiblity
import com.example.commonutils.util.showView
import com.github.instagram4j.instagram4j.IGClient
import com.phntechnology.basestructure.R
import com.phntechnology.basestructure.adapters.GenericAdapter
import com.phntechnology.basestructure.databinding.FragmentHomeBinding
import com.phntechnology.basestructure.databinding.PostListItemBinding
import com.phntechnology.basestructure.databinding.StoryListItemBinding
import com.phntechnology.basestructure.helper.DoubleClickListener
import com.phntechnology.basestructure.helper.LottieAnimationListener
import com.phntechnology.basestructure.model.DemoModel
import com.phntechnology.basestructure.model.PostData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.ArrayList

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    private var _adapter: GenericAdapter<PostData, PostListItemBinding>? = null

    private val adapter get() = _adapter!!

    private var _storyAdapter: GenericAdapter<PostData, StoryListItemBinding>? = null

    private val storyAdapter get() = _storyAdapter!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        backPressedHandle {
            requireActivity().finishAffinity()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeAdapter()

        initializeData()

    }

    private fun initializeData() {
        val postData = ArrayList<PostData>()
        var storyData = ArrayList<PostData>()
        for (i in 0..10) {
            postData.add(PostData(false, "Name $i"))
        }

        adapter.setData(postData)
        storyAdapter.setData(postData)
    }

    private fun initializeAdapter() {

//        lifecycleScope.launch(Dispatchers.IO) {
//            try {
//                val client = IGClient.builder()
//                    .username("newinsta5433")
//                    .password("Rocky@5050")
//                    .login()
//                Log.e("username", client.selfProfile.toString())
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }

        _adapter = GenericAdapter(
            PostListItemBinding::inflate,
            onBind = { itemData, itemBinding, position, listSize ->
                itemBinding.apply {
                    val doubleClick = object : DoubleClickListener() {
                        override fun onDoubleClick(v: View?) {
                            like.setImageDrawable(
                                ContextCompat.getDrawable(
                                    requireContext(),
                                    R.drawable.like_fill
                                )
                            )
                            lottie.showView()
                            lottie.playAnimation()
                            lottie.addAnimatorListener(object : LottieAnimationListener() {
                                override fun onEndAnimation(animation: Animator) {
                                    lottie.hideView()
                                }
                            })
                        }
                    }
                    post.setOnClickListener(doubleClick)
                }
            })
        binding.homeLayout.postRv.adapter = adapter

        _storyAdapter = GenericAdapter(
            StoryListItemBinding::inflate,
            onBind = { itemData, itemBinding, position, listSize ->
                itemBinding.apply {

                    storyText.text = if (position == 0) {
                        backRing.hideViewVisiblity()
                        getString(R.string.your_story)
                    } else {
                        itemData.name
                    }


                }
            })
        binding.homeLayout.storyRv.adapter = storyAdapter

//        binding.scrollView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
////            Log.e("scrollY", scrollY.toString())
//            Log.e("scrollY", "current : ${scrollY} old : $oldScrollY")
//            if (oldScrollY < scrollY){
//                binding.schoolTopBar.hideView()
//            }else if (oldScrollY > scrollY){
//                binding.schoolTopBar.showView()
//            }
//        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}