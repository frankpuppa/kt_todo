package com.todo.database.repositories
import com.todo.shared_types.exceptions.*
import com.todo.shared_types.models.AddUser
import com.todo.shared_types.models.User
import io.ktor.util.logging.*
import java.sql.Statement

class UserRepository(val db_conn: java.sql.Connection) {
    internal val logger = KtorSimpleLogger("user_logger")

    fun getUsers(): List<User> {
        val sql = """SELECT * FROM "user""""
        val query = db_conn.prepareStatement(sql)
        val result = query.executeQuery()
        val users = mutableListOf<User>()
        while (result.next()) {
            users.add(
                User(
                    id = result.getInt("id"),
                    firstname = result.getString("firstname"),
                    lastname = result.getString("lastname"),
                    email = result.getString("email"),
                ),
            )
        }
        return users
    }

    fun getUserById(id: Int): User {
        val st =
            db_conn.prepareStatement(
                """SELECT * FROM "user" where id=?""".trimIndent(),
            )
        st.setInt(1, id)
        val result = st.executeQuery()
        if (result.next() == false) {
            throw NotFoundError("user with id: $id not found")
        }

        val user =
            User(
                id = result.getInt("id"),
                firstname = result.getString("firstname"),
                lastname = result.getString("lastname"),
                email = result.getString("email"),
            )
        return user
    }

    fun deleteUser(id: Int) {
        val st =
            db_conn.prepareStatement(
                """DELETE FROM "user" WHERE id=?""".trimIndent(),
            )
        st.setInt(1, id)
        val result = st.executeUpdate()
        if (result == 0) {
            throw NotFoundError("user with id: $id not found")
        }
    }

    fun updateUser(updateUser: User): User {
        val st =
            db_conn.prepareStatement(
                """UPDATE "user" SET firstname=?, lastname=?, email=? where id=?""".trimIndent(),
            )

        st.setString(1, updateUser.firstname)
        st.setString(2, updateUser.lastname)
        st.setString(3, updateUser.email)
        st.setInt(4, updateUser.id)

        val result =
            try {
                st.executeUpdate()
            } catch (e: org.postgresql.util.PSQLException) {
                logger.error("$e.message")
                if (e.getSQLState() == "23505") { // conflict error
                    throw ConflictError("${e.message}")
                } else {
                    throw e
                }
            }
        if (result == 0) {
            throw NotFoundError("user with id: $updateUser.id not found")
        }
        return this.getUserById(updateUser.id)
    }

    fun addUser(addUser: AddUser): User {
        val st =
            db_conn.prepareStatement(
                """
                INSERT INTO "user" (firstname, lastname, email) VALUES (?, ?, ?)
                """.trimIndent(),
                Statement.RETURN_GENERATED_KEYS,
            )

        st.setString(1, addUser.firstname)
        st.setString(2, addUser.lastname)
        st.setString(3, addUser.email)
        val rows =
            try {
                st.executeUpdate()
            } catch (e: org.postgresql.util.PSQLException) {
                logger.error("$e.message")
                if (e.getSQLState() == "23505") { // conflict error
                    throw ConflictError("${e.message}")
                } else {
                    throw e
                }
            }
        val result = st.getGeneratedKeys()
        if (rows == 0 || result.next() == false) {
            throw GenericError("error while running adding user: $addUser")
        }
        val id = result.getInt(1)
        return this.getUserById(id)
    }
}
