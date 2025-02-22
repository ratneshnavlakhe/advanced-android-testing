package com.example.android.architecture.blueprints.todoapp.data.source

import com.example.android.architecture.blueprints.todoapp.data.Result
import com.example.android.architecture.blueprints.todoapp.data.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.not
import org.hamcrest.Matchers.nullValue
import org.hamcrest.core.IsEqual
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test


@ExperimentalCoroutinesApi
class DefaultTasksRepositoryTest {
    private val task1 = Task("Title1", "Description1")
    private val task2 = Task("Title2", "Description2")
    private val task3 = Task("Title3", "Description3")
    private val remoteTasks = listOf(task1, task2).sortedBy { it.id }
    private val localTasks = listOf(task3).sortedBy { it.id }
    private val newTasks = listOf(task3).sortedBy { it.id }

    private lateinit var tasksRemoteDataSource: FakeDataSource
    private lateinit var tasksLocalDataSource: FakeDataSource

    private lateinit var tasksRepository: DefaultTasksRepository

    @Before
    fun createRepository() {
        tasksLocalDataSource = FakeDataSource(localTasks.toMutableList())
        tasksRemoteDataSource = FakeDataSource(remoteTasks.toMutableList())

        tasksRepository = DefaultTasksRepository(
            tasksRemoteDataSource, tasksLocalDataSource, Dispatchers.Unconfined
        )
    }

    @Test
    fun getTasks_requestAllTasksFromRemoteDataSource() {
        // when tasks are requested from the task repo
        runBlockingTest {
            val tasks = tasksRepository.getTasks(true) as Result.Success
            assertThat(tasks.data, IsEqual(remoteTasks))
        }
    }

    @Test
    fun getTaskWithId_requestTaskFromRemoteDataSource() {
        runBlockingTest {
            val tasks = tasksRepository.getTask(task1.id, true) as Result.Success
            assertThat(tasks.data, IsEqual(task1))
        }
    }

    @Test
    fun getTaskWithId_requestTaskFromLocalDataSource() {
        runBlockingTest {
            val tasks = tasksRepository.getTask(task3.id, false) as Result.Success
            assertThat(tasks.data, IsEqual(task3))
        }
    }
}