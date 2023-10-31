package com.miftah.mysubmissionintermediate.utils.fake

import androidx.paging.PagingSource
import com.miftah.mysubmissionintermediate.core.data.source.local.entity.Stories
import com.miftah.mysubmissionintermediate.core.data.source.local.room.StoriesDao

class FakeStoriesDao : StoriesDao {
    override suspend fun insertQuote(quote: List<Stories>) {
        TODO("Not yet implemented")
    }

    override fun getAllStories(): PagingSource<Int, Stories> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll() {
        TODO("Not yet implemented")
    }
}