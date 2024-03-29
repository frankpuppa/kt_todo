import com.todo.api.module
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.TestApplication
import io.ktor.test.dispatcher.testSuspend
import io.ktor.util.logging.KtorSimpleLogger
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

class ApplicationTest {
    internal val logger = KtorSimpleLogger("tests_logger")

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
    fun testUsers() =
        testSuspend {
            logger.info("running getUsers test")
            val response = testApp.client.get("/users")
            assertEquals(HttpStatusCode.OK, response.status)
        }

    @Test
    fun testTasks() =
        testSuspend {
            logger.info("running getTasks test")
            val response = testApp.client.get("/tasks")
            assertEquals(HttpStatusCode.OK, response.status)
        }
}
