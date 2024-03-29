package com.todo.database.repositories
import com.todo.shared_types.exceptions.*
import com.todo.shared_types.models.AddTask
import com.todo.shared_types.models.Task
import com.todo.shared_types.models.getCurrentTimestamp
import io.ktor.util.logging.KtorSimpleLogger
import java.sql.Statement

class TaskRepository(val db_conn: java.sql.Connection) {
    internal val logger = KtorSimpleLogger("task_logger")

    fun getTasks(): List<Task> {
        val sql = """SELECT * FROM task"""
        val query = db_conn.prepareStatement(sql)
        val result = query.executeQuery()
        val tasks = mutableListOf<Task>()
        while (result.next()) {
            tasks.add(
                Task(
                    id = result.getInt("id"),
                    title = result.getString("title"),
                    date = result.getTimestamp("date"),
                    status = result.getInt("status"),
                    is_active = result.getBoolean("is_active"),
                    user_id = result.getInt("user_id"),
                ),
            )
        }
        return tasks
    }

    fun getTaskById(id: Int): Task {
        val st =
            db_conn.prepareStatement(
                """SELECT * FROM task where id=?""".trimIndent(),
            )
        st.setInt(1, id)
        val result = st.executeQuery()
        if (result.next() == false) {
            throw NotFoundError("task with id: $id not found")
        }

        val task =
            Task(
                id = result.getInt("id"),
                title = result.getString("title"),
                date = result.getTimestamp("date"),
                status = result.getInt("status"),
                is_active = result.getBoolean("is_active"),
                user_id = result.getInt("user_id"),
            )
        return task
    }

    fun deleteTask(id: Int) {
        val st =
            db_conn.prepareStatement(
                """DELETE FROM task WHERE id=?""".trimIndent(),
            )
        st.setInt(1, id)
        val result = st.executeUpdate()
        if (result == 0) {
            throw NotFoundError("task with id: $id not found")
        }
    }

    fun updateTask(updateTask: Task): Task {
        val st =
            db_conn.prepareStatement(
                """UPDATE task SET title=?, date=?, status=?, is_active=? where id=?""".trimIndent(),
            )

        st.setString(1, updateTask.title)
        st.setTimestamp(2, updateTask.getCurrentTimestamp())
        st.setInt(3, updateTask.status)
        st.setBoolean(4, updateTask.is_active)
        st.setInt(5, updateTask.id)

        val result = st.executeUpdate()
        if (result == 0) {
            throw NotFoundError("task with id: $updateTask.id not found")
        }
        return this.getTaskById(updateTask.id)
    }

    fun addTask(addTask: AddTask): Task {
        val st =
            db_conn.prepareStatement(
                """
                INSERT INTO task (title, status, is_active, date, user_id) VALUES
                (?, ?, ?, ?, ?)
                """.trimIndent(),
                Statement.RETURN_GENERATED_KEYS,
            )

        st.setString(1, addTask.title)
        st.setInt(2, addTask.status)
        st.setBoolean(3, addTask.is_active)
        st.setTimestamp(4, addTask.date)
        st.setInt(5, addTask.user_id)

        val rows = st.executeUpdate()
        val result = st.getGeneratedKeys()
        if (rows == 0 || result.next() == false) {
            throw GenericError("error while running adding task: $addTask")
        }
        val id = result.getInt(1)
        return this.getTaskById(id)
    }

    fun getTasksByUserId(u_id: Int): List<Task> {
        val st =
            db_conn.prepareStatement(
                """SELECT * FROM task where user_id=?""".trimIndent(),
            )
        st.setInt(1, u_id)
        val result = st.executeQuery()
        if (result.next() == false) {
            return emptyList()
        }
        val tasks = mutableListOf<Task>()
        while (result.next()) {
            tasks.add(
                Task(
                    id = result.getInt("id"),
                    title = result.getString("title"),
                    date = result.getTimestamp("date"),
                    status = result.getInt("status"),
                    is_active = result.getBoolean("is_active"),
                    user_id = result.getInt("user_id"),
                ),
            )
        }
        return tasks
    }
}
