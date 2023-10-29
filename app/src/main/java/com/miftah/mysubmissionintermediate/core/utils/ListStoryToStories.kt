package com.miftah.mysubmissionintermediate.core.utils

import com.miftah.mysubmissionintermediate.core.data.source.local.entity.Stories
import com.miftah.mysubmissionintermediate.core.data.source.remote.response.ListStoryItem


fun ListStoryItem.listStoryToStories() : Stories{
    return Stories(
        id = id,
        name = name,
        description = description,
        photoUrl = photoUrl,
        lat = lat,
        lon = lon
    )
}