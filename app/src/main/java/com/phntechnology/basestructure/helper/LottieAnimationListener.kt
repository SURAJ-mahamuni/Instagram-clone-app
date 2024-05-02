package com.phntechnology.basestructure.helper

import android.animation.Animator

abstract class LottieAnimationListener : Animator.AnimatorListener {
    override fun onAnimationStart(animation: Animator) {
    }

    override fun onAnimationEnd(animation: Animator) {
        onEndAnimation(animation)
    }

    override fun onAnimationCancel(animation: Animator) {
    }

    override fun onAnimationRepeat(animation: Animator) {
    }

    abstract fun onEndAnimation(animation: Animator)

}