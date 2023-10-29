package com.miftah.mysubmissionintermediate.core.data.source.local.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.miftah.mysubmissionintermediate.core.data.source.local.entity.Stories


@Dao
interface StoriesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuote(quote: List<Stories>)

    @Query("SELECT * FROM Stories")
    fun getAllStories(): PagingSource<Int, Stories>

    @Query("DELETE FROM Stories")
    suspend fun deleteAll()
}