package com.kaisebhi.githubproject.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class ResponseModel(
    val items: List<AllRepoEntity>) {
}

@Entity(tableName = "repoTable")
data class AllRepoEntity(
    @PrimaryKey(autoGenerate = true)
    val uniqueId: Int,
    val name: String?,
    @SerializedName("html_url")
    val url: String?,
    val description: String?,
    val owner: Owner?,
    val message: String?,
    val contributors_url: String?) {
}

data class Owner(val avatar_url: String?)

