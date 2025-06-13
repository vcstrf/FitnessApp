package com.example.helloworld
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class ActivityItem : Parcelable {
    @Parcelize
    data class ActivityHeader(val date: String) : ActivityItem()

    @Parcelize
    data class ActivityMain(
        val id: Int,
        val distance: String,
        val duration: String,
        val timeAgo: String,
        val type: String,
        val isFromOtherUser: Boolean = false,
        val username: String? = null,
        val timeRange: String? = null
    ) : ActivityItem()
}
