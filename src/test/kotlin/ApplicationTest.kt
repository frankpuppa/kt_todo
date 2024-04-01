import com.todo.api.module
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.testing.TestApplication
import io.ktor.test.dispatcher.testSuspend
import io.ktor.util.logging.KtorSimpleLogger
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class ApplicationTest {
    internal val logger = KtorSimpleLogger("test_logger")

    companion object {
        lateinit var testApp: TestApplication

        @JvmStatic
        @BeforeAll
        fun setup() {
            testApp =
                TestApplication {
                    application { module() }
                }
        }

        @JvmStatic
        @AfterAll
        fun teardown() {
            testApp.stop()
        }
    }

    @Test
    fun testGetUsers() =
        testSuspend {
            logger.info("running getUsers test")
            val response = testApp.client.get("/users")
            assertEquals(HttpStatusCode.OK, response.status)
        }

    @Test
    fun testGetUser() =
        testSuspend {
            val uId = 1
            logger.info("running getUser test with user id: $uId")
            val response = testApp.client.get("/users/$uId")
            assertEquals(HttpStatusCode.OK, response.status)
        }

    @Test
    fun testGetUserNotFound() =
        testSuspend {
            val uId = 10
            logger.info("running getUser test with user id: $uId")
            val response = testApp.client.get("/users/$uId")
            assertEquals(HttpStatusCode.NotFound, response.status)
        }

    @Test
    fun testUpdateUser() =
        testSuspend {
            val uId = 1
            logger.info("running updateUser test with user id: $uId")

            val response =
                testApp.client.put("/users/$uId") {
                    contentType(ContentType.Application.Json)
                    setBody(
                        """{
                        "firstname": "adam_toni",
                        "lastname": "adrian",
                        "email": "adam.adrian@example.com"
                        }""",
                    )
                }
            assertEquals(HttpStatusCode.OK, response.status)
        }

    @Test
    fun testUpdateUserConflict() =
        testSuspend {
            val uId = 1
            logger.info("running updateUser test with user id: $uId")

            val response =
                testApp.client.put("/users/$uId") {
                    contentType(ContentType.Application.Json)
                    setBody(
                        """{
                        "firstname": "adam_toni",
                        "lastname": "adrian",
                        "email": "colin@example.com"
                        }""",
                    )
                }
            assertEquals(HttpStatusCode.Conflict, response.status)
        }

    @Test
    fun testAddUserConflict() =
        testSuspend {
            val body =
                """{
                "firstname": "adam_toni",
                "lastname": "adrian",
                "email": "colin@example.com"
            }"""
            logger.info("running addUser: $body")

            val response =
                testApp.client.post("/users") {
                    contentType(ContentType.Application.Json)
                    setBody(body)
                }
            assertEquals(HttpStatusCode.Conflict, response.status)
        }

    @Test
    @Disabled
    fun testDeleteUser() =
        testSuspend {
            val uId = 4
            logger.info("running deleteUser test with user id: $uId")
            val response = testApp.client.delete("/users/$uId")
            assertEquals(HttpStatusCode.OK, response.status)
        }

    @Test
    fun testDeleteUserNotFound() =
        testSuspend {
            val uId = 40
            logger.info("running deleteUser test with user id: $uId")
            val response = testApp.client.delete("/users/$uId")
            assertEquals(HttpStatusCode.NotFound, response.status)
        }

//  TASKS
    @Test
    fun testTasks() =
        testSuspend {
            logger.info("running getTasks test")
            val response = testApp.client.get("/tasks")
            assertEquals(HttpStatusCode.OK, response.status)
        }

    @Test
    fun testGetTask() =
        testSuspend {
            val tId = 1
            logger.info("running getTask test with task id: $tId")
            val response = testApp.client.get("/tasks/$tId")
            assertEquals(HttpStatusCode.OK, response.status)
        }

    @Test
    fun testUpdateTask() =
        testSuspend {
            val tId = 1
            logger.info("running updateTask test with user id: $tId")

            val response =
                testApp.client.put("/tasks/$tId") {
                    contentType(ContentType.Application.Json)
                    setBody(
                        """{
                        "title": "clean the windows",
                        "status": 1,
                        "is_active": true
                        }""",
                    )
                }
            assertEquals(HttpStatusCode.OK, response.status)
        }

    @Test
    @Disabled
    fun testDeleteTask() =
        testSuspend {
            val tId = 6
            logger.info("running deleteTask test with user id: $tId")
            val response = testApp.client.delete("/tasks/$tId")
            assertEquals(HttpStatusCode.OK, response.status)
        }

    @Test
    fun testDeleteTaskNotFound() =
        testSuspend {
            val tId = 40
            logger.info("running deleteTask test with task id: $tId")
            val response = testApp.client.delete("/tasks/$tId")
            assertEquals(HttpStatusCode.NotFound, response.status)
        }
}
