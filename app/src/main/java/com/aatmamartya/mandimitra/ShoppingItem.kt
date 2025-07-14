package com.aatmamartya.mandimitra

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ShoppingItem(val rate: String, val quantity: String, val total: Double) : Parcelable