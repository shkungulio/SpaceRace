package com.bongonets.spacerace

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import androidx.core.animation.doOnEnd
import kotlin.math.roundToLong

open class Spacecraft(universe: MainActivity) {
    // properties -----------------------------------------------------

    // Task 3 - type together:
    var name = "Basica"
    var imageId = R.drawable.basica
    val universe = universe

    // Task 3
    // a Float called fuelQuantity          - initialize to 1000.0F
    var fuelQuantity = 1000.0F
    // a Float called fuelPerAU             - initialize to 1500.0F
    var fuelPerAU = 1500.0F
    // a Float called speed (AU per second) - initialize to 0.2
    var speed = 0.2 // AU per second

    // methods --------------------------------------------------------

    // Task 3 - create a method launch()
    // copy the code from launch.txt
    open fun launch(){
        if (universe.meteorView == null || universe.spacecraftView == null) { return }

        // calculate the x and y distances between the centres of the spacecraft and the meteor

        val distanceX =
            (universe.meteorView!!.x + universe.meteorView!!.width / 2.0F) -
                    (universe.spacecraftView!!.x + universe.spacecraftView!!.width / 2.0F)
        val distanceY =
            (universe.meteorView!!.y + universe.meteorView!!.height / 2.0F) -
                    (universe.spacecraftView!!.y + universe.spacecraftView!!.height / 2.0F)

        // what portion of the total distance (1 AU) can the spacecraft travel with its fuel?

        val fractionCovered = fuelQuantity / fuelPerAU

        // animate the spacecraftView

        val deltaX = PropertyValuesHolder.ofFloat("x",
            universe.spacecraftView!!.x + distanceX * fractionCovered)

        val deltaY = PropertyValuesHolder.ofFloat("y",
            universe.spacecraftView!!.y + distanceY * fractionCovered)

        ObjectAnimator.ofPropertyValuesHolder(universe.spacecraftView, deltaX, deltaY).apply {
            duration = (1 / speed * fractionCovered).roundToLong() * 1000
            addListener(doOnEnd {
                universe.announceEndOfTravel()
            })
            start()
        }

    }
}