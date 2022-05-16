package com.bongonets.spacerace

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.doOnLayout

class MainActivity : AppCompatActivity() {

    // properties -----------------------------------------------------

    var spacecraftView : ImageView? = null   // represents the spacecraft
    var meteorView     : ImageView? = null
    var prepareButton  : Button?    = null
    var launchButton   : Button?    = null
    var prevButton     : Button?    = null
    var nextButton     : Button?    = null
    var nameTextView   : TextView?  = null

    val availableSpacecrafts = ArrayList<Spacecraft>()
    var currentSpacecraftIndex = 0

    // this is the SpaceCraft object that is currently in use
    var activeSpacecraft : Spacecraft? = null

    var screenWidth = 1
    var screenHeight = 1
    var missionInProgress = false

    // methods --------------------------------------------------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        screenWidth  = displayMetrics.widthPixels
        screenHeight = displayMetrics.heightPixels

        spacecraftView = findViewById<ImageView>(R.id.spacecraft)
        meteorView     = findViewById<ImageView>(R.id.meteor)

        launchButton  = findViewById<Button>(R.id.btnLaunch)
        prepareButton = findViewById<Button>(R.id.btnRestart)
        prevButton    = findViewById<Button>(R.id.btnPreviousSpacecraft)
        nextButton    = findViewById<Button>(R.id.btnNextSpacecraft)
        nameTextView  = findViewById<TextView>(R.id.textViewName)

        // add SpaceCraft to the ArrayList
        availableSpacecrafts.add(Spacecraft(universe = this))
        // add Advantis to the ArrayList
        availableSpacecrafts.add(Advantis(this))
        // add Rapidus to the ArrayList
        availableSpacecrafts.add(Rapidus(this))
        // add Ultimus to the ArrayList
        availableSpacecrafts.add(Ultimus(universe = this))

        val rootView = window.decorView.findViewById<View>(android.R.id.content)

        // this is called after all the elements have been laid out, we need the sizes
        rootView.doOnLayout {
            switchSpacecraft(0)
        }

    }

    fun announceEndOfTravel() {
        prepareButton?.isEnabled = true
        prevButton?.isEnabled = true
        nextButton?.isEnabled = true
    }

    fun prepareForLaunch() {

        missionInProgress = false

        spacecraftView?.let {
            prepareButton?.isEnabled = false
            it.x = 90.0F
            it.y = screenHeight - 450.0F - it.height

            launchButton?.isEnabled = true
        }
    }

    fun switchSpacecraft(newIndex: Int) {

        if (newIndex !in 0 until availableSpacecrafts.count()) { return }

        currentSpacecraftIndex = newIndex
        activeSpacecraft = availableSpacecrafts[newIndex]

        nameTextView?.text = activeSpacecraft?.name
        // access the spacecraft's image ID
        spacecraftView?.setImageDrawable(ResourcesCompat.getDrawable(getResources(), activeSpacecraft?.imageId?: R.drawable.basica, null))

        prepareForLaunch()
    }

    // UI event handlers

    fun btnLaunchTapped(view: View) {

        if (missionInProgress) { return }

        activeSpacecraft?.let {
            missionInProgress = true
            // call the spacecraft's launch() method
            it.launch()
            launchButton?.isEnabled = false
            prevButton?.isEnabled = false
            nextButton?.isEnabled = false
        }
    }

    fun btnNextSpacecraftTapped(view: View) {
        currentSpacecraftIndex++
        if (currentSpacecraftIndex >= availableSpacecrafts.count()) { currentSpacecraftIndex = 0 }
        switchSpacecraft(newIndex = currentSpacecraftIndex)
    }

    fun btnRestartTapped(view: View) {
        activeSpacecraft = availableSpacecrafts[currentSpacecraftIndex]
        prepareForLaunch()
    }

    fun btnPreviousSpacecraftTapped(view: View) {
        currentSpacecraftIndex--
        if (currentSpacecraftIndex < 0) { currentSpacecraftIndex = availableSpacecrafts.count() - 1 }
        switchSpacecraft(newIndex = currentSpacecraftIndex)
    }
}