package me.carlamko.payup.extensions

fun Float.formatAsMoney(): String = "$${"%.2f".format(this)}"