package com.miftah.mysubmissionintermediate.utils.dataDummy

import com.miftah.mysubmissionintermediate.core.data.source.local.entity.Stories

object LocalEntityDummy {
    fun generateStories(): List<Stories> {
        val storiesList = ArrayList<Stories>()
        for (i in 1..15) {
            val stories = generateStory()
            storiesList.add(stories)
        }
        return storiesList
    }

    fun generateStory(): Stories {
        return Stories(
            id = "user-yj5pc_LARC_AgK61",
            name = "Miftah",
            description = "Text",
            photoUrl = "https://story-api.dicoding.dev/images/stories/photos-1641623658595_dummy-pic.png",
            lat = -16.002,
            lon = -10.212,
        )
    }
}