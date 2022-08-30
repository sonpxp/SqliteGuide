package com.cloudxanh.examplesqlite.model


/**
 * Created by sonpxp on 8/31/2022.
 * Email: sonmob202@gmail.com
 */
data class Feed(
    var title: String,
    var subtitle: String,
){
    companion object {
        val MOCKED_ITEMS = listOf(
            Feed(
                "New York",
                "The city that never sleeps",
            ),
            Feed(
                "Barcelona",
                "A relaxed afternoon",
            ),
            Feed(
                "Santorini",
                "Beautiful sunset",
            ),
        )
    }
}
