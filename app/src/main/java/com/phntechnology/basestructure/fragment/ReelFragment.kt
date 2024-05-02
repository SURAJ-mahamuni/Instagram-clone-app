package com.phntechnology.basestructure.fragment

import android.animation.Animator
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.commonutils.util.backPressedHandle
import com.example.commonutils.util.hideView
import com.example.commonutils.util.showView
import com.example.commonutils.util.toastMsg
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.phntechnology.basestructure.R
import com.phntechnology.basestructure.adapters.GenericAdapter
import com.phntechnology.basestructure.databinding.FragmentReelBinding
import com.phntechnology.basestructure.databinding.ReelViewPagerItemBinding
import com.phntechnology.basestructure.helper.DoubleClickListener
import com.phntechnology.basestructure.helper.LottieAnimationListener
import com.phntechnology.basestructure.model.ExoPlayerItem
import com.phntechnology.basestructure.model.ReelModel
import com.phntechnology.basestructure.model.ReelViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ReelFragment : Fragment() {
    private var _binding: FragmentReelBinding? = null

    private val binding get() = _binding!!

    private var _pagerAdapter: GenericAdapter<ReelModel, ReelViewPagerItemBinding>? = null

    private val pagerAdapter get() = _pagerAdapter!!

    private val exoPlayerItem = ArrayList<ExoPlayerItem>()

    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentReelBinding.inflate(layoutInflater, container, false)
        backPressedHandle {
            requireActivity().finishAffinity()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onStart() {
        super.onStart()

        initializeAdapter()

        initializeData()
    }

    private fun initializeExoPLayer(): ExoPlayer {
        return ExoPlayer.Builder(requireContext())
            .setSeekBackIncrementMs(10000)
            .setSeekForwardIncrementMs(10000)
            .build()
    }

    private fun initializeData() {
        val reelData: ArrayList<ReelModel> = ArrayList(ReelViewModel().reels)
        pagerAdapter.setData(reelData)
    }

    private fun initializeDataSource(url: String, exoPlayer: ExoPlayer) {
        val defaultHttpDataSource = DefaultHttpDataSource.Factory()
        val mediaItem = MediaItem.fromUri(url)
        val mediaSource = ProgressiveMediaSource.Factory(defaultHttpDataSource)
            .createMediaSource(mediaItem)
        exoPlayer.apply {
            setMediaSource(mediaSource)
            seekTo(0)
            playWhenReady = playWhenReady
            playWhenReady = true
            prepare()
        }
    }

    private fun initializeAdapter() {
        _pagerAdapter = GenericAdapter(
            bindingInflater = ReelViewPagerItemBinding::inflate,
            onBind = { itemData, itemBinding, position, _ ->
                itemBinding.apply {
                    initializeExoPLayer().let { exoPlayer ->
                        videoView.player?.release()
                        videoView.player = exoPlayer

                        initializeDataSource(itemData.sources, exoPlayer)

                        videoView.controllerHideOnTouch = false
                        exoPlayer.addListener(object : Player.Listener {
                            override fun onPlaybackStateChanged(playbackState: Int) {
                                super.onPlaybackStateChanged(playbackState)
                                when (playbackState) {
                                    ExoPlayer.STATE_IDLE -> {
                                        progressBar2.showView()
                                    }

                                    ExoPlayer.STATE_BUFFERING -> {
                                        progressBar2.showView()
                                    }

                                    ExoPlayer.STATE_READY -> {
                                        progressBar2.hideView()
                                    }

                                    ExoPlayer.STATE_ENDED -> {
                                        initializeDataSource(itemData.sources, exoPlayer)
                                    }
                                }
                            }
                        })

                        //double tap handler
                        videoView.setOnClickListener(object : DoubleClickListener() {
                            override fun onDoubleClick(v: View?) {
                                like.setImageDrawable(
                                    ContextCompat.getDrawable(
                                        requireContext(),
                                        R.drawable.like_fill
                                    )
                                )
                                binding.lottie.showView()
                                binding.lottie.playAnimation()
                                binding.lottie.addAnimatorListener(object :
                                    LottieAnimationListener() {
                                    override fun onEndAnimation(animation: Animator) {
                                        binding.lottie.hideView()
                                    }
                                })
                            }
                        })


                        //previous exoplayer instance
                        exoPlayerItem.add(ExoPlayerItem(exoplayer = exoPlayer, position = position))
                    }
                }
            })
        binding.reelViewPager.adapter = pagerAdapter

        binding.reelViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                val previousIndex = exoPlayerItem.indexOfFirst { it.exoplayer.isPlaying }
                if (previousIndex != -1) {
                    val player = exoPlayerItem[previousIndex].exoplayer
                    player.pause()
                    player.playWhenReady = false
                }
                val newIndex = exoPlayerItem.indexOfFirst { it.position == position }
                if (newIndex != -1) {
                    val player = exoPlayerItem[newIndex].exoplayer
                    player.playWhenReady = true
                    player.play()
                }
            }
        })
    }

    override fun onPause() {
        super.onPause()
        val index = exoPlayerItem.indexOfFirst { it.position == binding.reelViewPager.currentItem }
        if (index != -1) {
            val player = exoPlayerItem[index].exoplayer
            player.pause()
            player.playWhenReady = false
        }
    }

    override fun onResume() {
        super.onResume()
        val index = exoPlayerItem.indexOfFirst { it.position == binding.reelViewPager.currentItem }
        if (index != -1) {
            val player = exoPlayerItem[index].exoplayer
            player.playWhenReady = true
            player.play()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        if (exoPlayerItem.isNotEmpty()) {
            for (item in exoPlayerItem) {
                val player = item.exoplayer
                player.stop()
                player.playWhenReady = false
                player.clearMediaItems()
            }
        }

        _binding = null
        _pagerAdapter = null
    }

    override fun onStop() {
        super.onStop()
        _pagerAdapter = null
    }

}