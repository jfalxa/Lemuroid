package com.swordfish.lemuroid.common.kotlin

import android.content.SharedPreferences
import kotlin.math.roundToInt
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class SharedPreferencesDelegates {
    class BooleanDelegate(
        private val sharedPreferences: SharedPreferences,
        private val key: String,
        private val defaultValue: Boolean
    ) : ReadWriteProperty<Any, Boolean> {

        override fun getValue(thisRef: Any, property: KProperty<*>): Boolean {
            return sharedPreferences.getBoolean(key, defaultValue)
        }

        override fun setValue(thisRef: Any, property: KProperty<*>, value: Boolean) {
            sharedPreferences.edit().putBoolean(key, value).apply()
        }
    }

    class PercentageDelegate(
        private val sharedPreferences: SharedPreferences,
        private val key: String,
        private val defaultIndex: Int,
        private val max: Int = 10
    ) : ReadWriteProperty<Any, Float> {

        override fun getValue(thisRef: Any, property: KProperty<*>): Float {
            return indexToPercentage(sharedPreferences.getInt(key, defaultIndex))
        }

        override fun setValue(thisRef: Any, property: KProperty<*>, value: Float) {
            sharedPreferences.edit().putInt(key, percentageToIndex(value)).apply()
        }

        private fun indexToPercentage(index: Int) = (index.toFloat() / (max))

        private fun percentageToIndex(percentage: Float) = (percentage * max).roundToInt()
    }
}
