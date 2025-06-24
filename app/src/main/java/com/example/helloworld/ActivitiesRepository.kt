package com.example.helloworld
import java.text.SimpleDateFormat
import java.util.*

class ActivitiesRepository(private val dao: ActivityDao) {

    private val usersActivities = listOf(
        Activity(
            id = 3,
            user = "@van_darkholme",
            duration = "2 часа 46 минут",
            distance = "14.32 км",
            type = "Серфинг",
            date = getYesterdayDate(),
            startTime = "",
            finishTime = "",
            timeAgo = "Вчера",
            isFromOtherUser = true
        ),
        Activity(
            id = 4,
            user = "@techniquepasha",
            distance = "228 м",
            duration = "14 часов 48 минут",
            type = "Качели",
            date = getDateDaysAgo(2),
            startTime = "",
            finishTime = "",
            timeAgo = "2 дня назад",
            isFromOtherUser = true
        ),
        Activity(
            id = 5,
            user = "@morgen_shtern",
            distance = "10 км",
            duration = "1 час 10 минут",
            type = "Езда на кадиллаке",
            date = getDateDaysAgo(3),
            startTime = "",
            finishTime = "",
            timeAgo = "3 дня назад",
            isFromOtherUser = true
        )
    )

    fun getUsersActivities(): List<Activity> = usersActivities

    suspend fun insertActivity(activity: Activity) {
        dao.insert(activity)
    }

    private fun getYesterdayDate(): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -1)
        return formatDate(calendar.time)
    }

    private fun getDateDaysAgo(days: Int): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -days)
        return formatDate(calendar.time)
    }

    private fun formatDate(date: Date): String {
        return SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(date)
    }

    companion object {
        @Volatile
        private var INSTANCE: ActivitiesRepository? = null

        fun getInstance(): ActivitiesRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = ActivitiesRepository(App.INSTANCE.db.activityDao())
                INSTANCE = instance
                instance
            }
        }
    }
}