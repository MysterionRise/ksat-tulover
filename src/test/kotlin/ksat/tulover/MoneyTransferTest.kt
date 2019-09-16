package ksat.tulover

import com.natpryce.hamkrest.assertion.assertThat
import org.http4k.core.Body
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Status.Companion.OK
import org.http4k.format.Jackson.auto
import org.http4k.hamkrest.hasStatus
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mapdb.DBMaker
import java.util.*
import kotlin.test.assertTrue

class MoneyTransferTest {

    private var testApp = MoneyTransferServer.moneyTransferApp()
    private val reqLens = Body.auto<Req>().toLens()
    private val fooResp = Body.auto<Succeeded<String>>().toLens()

    @Before
    fun `create fresh db`() {
        val db = DBMaker
            .memoryDB()
            .make()

        testApp = MoneyTransferServer.moneyTransferApp()
    }

    @After
    fun `clean up after`() {

    }

    @Test
    fun `test foo bar`() {

        val id = UUID.randomUUID()
        val create = GetFoo(id)
        val req = reqLens.inject(create, Request(Method.GET, "/foo"))

        val response = testApp(req)

        val fooStr = fooResp.extract(response).value
        println(fooStr)

        assertThat(response, hasStatus(OK))
        assertTrue(fooStr.contains(id.toString()))
    }
}