package com.todo.api.controllers.tasks

import com.todo.api.request.*
import com.todo.api.response.TaskResponse
import com.todo.database.repositories.TaskRepository
import com.todo.database.repositories.UserRepository
import com.todo.shared_types.exceptions.*
import com.todo.shared_types.mappers.TaskMapper
import com.todo.shared_types.mappers.mapAddTaskRequestToAddTask
import com.todo.shared_types.mappers.mapTaskToTaskResponse
import com.todo.shared_types.models.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.util.logging.*

class TaskController(val taskRepo: TaskRepository, val userRepo: UserRepository) {
    internal val logger = KtorSimpleLogger("task_logger")
    internal val taskMapper = TaskMapper()

    fun getTasks(): List<TaskResponse> {
        logger.info("getting all tasks")
        val tasks = taskRepo.getTasks()
        val tasksResponseList = tasks.map { taskMapper.mapTaskToTaskResponse(it) }
        return tasksResponseList
    }

    fun getTask(id: Int): TaskResponse {
        logger.info("getting task with id: ($id)")
        val task = taskRepo.getTaskById(id)
        return taskMapper.mapTaskToTaskResponse(task)
    }

    fun updateTask(
        id: Int,
        updateTask: UpdateTaskRequest,
    ): TaskResponse {
        logger.info("updating task with id: ($id) with data: $updateTask")
        val currentTask = taskRepo.getTaskById(id)
        val newTask =
            Task(
                currentTask.id,
                updateTask.title,
                updateTask.status,
                updateTask.is_active,
                currentTask.user_id,
                currentTask.date,
            )
        val task = taskRepo.updateTask(newTask)
        return taskMapper.mapTaskToTaskResponse(task)
    }

    fun deleteTask(id: Int) {
        logger.info("deleting task with id: $id")
        taskRepo.deleteTask(id)
    }

    fun addTask(newTaskRequest: AddTaskRequest): TaskResponse {
        logger.info("add new task: $newTaskRequest")
        val newTask = taskMapper.mapAddTaskRequestToAddTask(newTaskRequest)
        val task = taskRepo.addTask(newTask)
        return taskMapper.mapTaskToTaskResponse(task)
    }

    fun getUserTasks(u_id: Int): List<TaskResponse> {
        logger.info("fetching tasks for the user with id: ${u_id}")
        val user = userRepo.getUserById(u_id)
        val tasks = taskRepo.getTasksByUserId(user.id)
        val tasksResponseList = tasks.map { taskMapper.mapTaskToTaskResponse(it) }
        return tasksResponseList
    }
}
