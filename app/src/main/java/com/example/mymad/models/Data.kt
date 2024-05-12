package com.example.mymad.models

import java.util.ArrayList

object Data {
    val categories = arrayOf(
        "Work/Professional",
        "Personal Errands",
        "Home & Family",
        "Health & Fitness",
        "Finance",
        "Education & Learning",
        "Leisure & Social",
        "Projects",
        "Urgent/Important"
    )

    val priorities = arrayOf(
        "Very High", //5
        "High",
        "Medium",
        "Low",
        "Very Low" //1
    )

    val sampleTestTasks = arrayListOf(
        Task("Task 1", "Work/Professional", "High", 4),
        Task("Task 2", "Personal Errands", "Low", 2),
        Task("Task 3", "Projects", "Very High", 5),
        Task("Task 4", "Home & Family", "Medium", 3),
        Task("Task 5", "Finance", "Very Low", 1)
    )
}
