package com.example.android.architecture.blueprints.todoapp.statistics

import com.example.android.architecture.blueprints.todoapp.data.Task
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Test

class StatisticsUtilsTest {
    @Test
    fun getActiveAndCompletedStats_noCompleted_returnsHundredZero() {
        // create and active task
        val tasks = listOf<Task>(
            Task("title", "desc", isCompleted = false)
        )

        // call your function
        val result = getActiveAndCompletedStats(tasks)

        //check the result
        assertThat(result.completedTasksPercent, `is`(0f))
        assertThat(result.activeTasksPercent, `is`(100f))
    }

    @Test
    fun getActiveAndCompletedStats_noActive_returnsZeroHundred() {
        val tasks = listOf(
            Task("title", "desc", isCompleted = true)
        )

        val result = getActiveAndCompletedStats(tasks)

        assertThat(result.completedTasksPercent, `is`(100f))
        assertThat(result.activeTasksPercent, `is`(0f))
    }

    @Test
    fun getActiveAndCompletedStats_both_returnsFortySixty() {
        val tasks = listOf(
            Task("title", "desc", isCompleted = true),
            Task("title1", "desc1", isCompleted = true),
            Task("title2", "desc2", isCompleted = false),
            Task("title3", "desc3", isCompleted = false),
            Task("title4", "desc4", isCompleted = false),
        )

        val result = getActiveAndCompletedStats(tasks)

        assertThat(result.completedTasksPercent, `is`(40f))
        assertThat(result.activeTasksPercent, `is`(60f))
    }

    @Test
    fun getActiveAndCompletedStats_empty_returnZeros() {
        val result = getActiveAndCompletedStats(emptyList())

        assertThat(result.completedTasksPercent, `is`(0f))
        assertThat(result.activeTasksPercent, `is`(0f))
    }

    @Test
    fun getActiveAndCompletedStats_error_returnZeroes() {
        val result = getActiveAndCompletedStats(null)

        assertThat(result.completedTasksPercent, `is`(0f))
        assertThat(result.activeTasksPercent, `is`(0f))
    }
}