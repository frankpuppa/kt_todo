package com.todo.shared_types.mappers

import com.todo.api.request.AddTaskRequest
import com.todo.api.response.TaskResponse
import com.todo.shared_types.models.AddTask
import com.todo.shared_types.models.Task
import com.todo.shared_types.models.timestampToDateString

class TaskMapper

fun TaskMapper.mapAddTaskRequestToAddTask(addTaskRequest: AddTaskRequest): AddTask {
    return AddTask(
        title = addTaskRequest.title,
        status = addTaskRequest.status,
        is_active = addTaskRequest.is_active,
        user_id = addTaskRequest.user_id,
    )
}

fun TaskMapper.mapTaskToTaskResponse(task: Task): TaskResponse {
    return TaskResponse(
        id = task.id,
        title = task.title,
        status = task.status,
        is_active = task.is_active,
        date = task.timestampToDateString(),
    )
}
