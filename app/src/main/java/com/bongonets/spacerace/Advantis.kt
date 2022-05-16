package com.bongonets.spacerace

open class Advantis(universe: MainActivity) : Spacecraft(universe) {
    init{
        name = "Advantis"
        imageId = R.drawable.advantis
        fuelQuantity = 1500.0F
    }
}