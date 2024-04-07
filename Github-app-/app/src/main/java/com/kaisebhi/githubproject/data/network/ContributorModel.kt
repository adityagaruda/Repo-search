package com.kaisebhi.githubproject.data.network

import com.google.gson.annotations.SerializedName

data class ContributorModel(
    val id: Int,
    @SerializedName("login")
    val name: String?) {
}