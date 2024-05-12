package com.example.mymad.models

import java.io.Serializable

data class Task(
    var name: String = "",
    var category: String = "",
    var priorityStr: String = "",
    var priority: Int = 0
) : Serializable {
    override fun toString(): String {
        return "Task(name='$name', category='$category', priorityStr='$priorityStr', priority=$priority)"
    }
}
