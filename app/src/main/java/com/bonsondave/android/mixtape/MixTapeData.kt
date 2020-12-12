package com.bonsondave.android.mixtape

import android.provider.ContactsContract
import androidx.annotation.RawRes

data class MixTapeData( var mixTitle: String,
                        var songTitle: String,
                        var artist: String,
                        var songFile: Int)