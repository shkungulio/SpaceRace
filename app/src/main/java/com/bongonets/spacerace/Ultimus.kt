package com.bongonets.spacerace

import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.core.animation.doOnEnd

class Ultimus(universe: MainActivity) : Spacecraft(universe) {
    init {
        name = "Ultimus"
        imageId = R.drawable.ultimus
    }

    override fun launch(){
        if (universe.meteorView == null || universe.spacecraftView == null) { return }

        ObjectAnimator.ofFloat(universe.spacecraftView, "alpha", -1.0F).apply {

            duration = 2000
            addListener(doOnEnd {
                universe.spacecraftView!!.x = universe.meteorView!!.x
                universe.spacecraftView!!.y = universe.meteorView!!.y

                // flash a few times for special effect!
                val rootView = universe.getWindow().getDecorView().findViewById<View>(android.R.id.content)
                rootView?.let {
                    it.startAnimation(AlphaAnimation(0.2F, 1.0F).also {
                        it.duration = 50L
                        it.startOffset = 20L
                        it.repeatMode = Animation.RESTART
                        it.repeatCount = 5
                    })
                }

                universe.spacecraftView!!.alpha = 1.0F

                universe.announceEndOfTravel()
            })
            start()
        }

    }
}