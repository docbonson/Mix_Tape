package com.bonsondave.android.mixtape

import android.provider.ContactsContract
import androidx.annotation.RawRes

data class MixTapeData(val songTitle: String,
                       val artist: String,
                        val songFile: Int)