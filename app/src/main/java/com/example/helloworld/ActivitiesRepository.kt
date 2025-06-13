package com.example.helloworld

enum class Type {
    MY_ACTIVITIES,
    USERS_ACTIVITIES
}

class ActivitiesRepository {

    private val myActivities = listOf(
        ActivityItem.ActivityHeader("Вчера"),
        ActivityItem.ActivityMain(
            1, "14.32 км", "2 часа 46 минут", "14 часов назад", "Серфинг", false
        ),
        ActivityItem.ActivityHeader("Май 2022 года"),
        ActivityItem.ActivityMain(
            2,"1000 м","60 минут","29.05.2022","Велосипед",false
        )
    )

    private val usersActivities = listOf(
        ActivityItem.ActivityHeader("Вчера"),
        ActivityItem.ActivityMain(
            3,"14.32 км","2 часа 46 минут","14 часов назад","Серфинг",true,"@van_darkholme"
        ),
        ActivityItem.ActivityMain(4,"228 м","14 часов 48 минут","14 часов назад","Качели",true,"@techniquepasha"
        ),
        ActivityItem.ActivityMain(
            5,"10 км","1 час 10 минут","14 часов назад","Езда на кадиллаке",true,"@morgen_shtern"
        )
    )

    fun getActivities(type: Type): List<ActivityItem> {
        return when (type) {
            Type.MY_ACTIVITIES -> myActivities
            Type.USERS_ACTIVITIES -> usersActivities
        }
    }
}