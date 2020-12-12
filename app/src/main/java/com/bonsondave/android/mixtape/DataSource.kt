package com.bonsondave.android.mixtape

class DataSource{

    companion object{

        fun createDataSet(): ArrayList<MixTapeData>{
            val list = ArrayList<MixTapeData>()
            list.add(
                MixTapeData(
                    "Dave's Mix",
                    "Hot Hot Hot",
                    "Skizzwhores",
                    songFile = R.raw.hot_hot_hot
                )
            )

            list.add(
                MixTapeData(
                    "Dave's Mix",
                    "Heroin Gun",
                    "Skizzwhores",
                    songFile = R.raw.heroin_gun
                )
            )

            list.add(
                MixTapeData(
                    "Dave's Mix",
                    "Just Like You, Just Like Me",
                    "Skizzwhores",
                    songFile = R.raw.just_like_you_just_like_me
                )
            )

            return list
        }
    }
}