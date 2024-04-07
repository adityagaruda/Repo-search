package com.kaisebhi.githubproject.data.network

import androidx.room.TypeConverter
import com.kaisebhi.githubproject.data.room.Owner


class TypeConverters {
    @TypeConverter
    fun setOwner(owner: Owner): String? {
        return owner.avatar_url
    }

    @TypeConverter
    fun getOwner(owner: String): Owner {
        return Owner(owner)
    }
}