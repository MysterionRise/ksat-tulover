package ksat.tulover

import org.http4k.core.*
import org.http4k.core.Status.Companion.BAD_REQUEST
import org.http4k.core.Status.Companion.OK
import org.http4k.format.Jackson.auto
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.Netty
import org.http4k.server.asServer
import org.mapdb.DBMaker
import org.slf4j.LoggerFactory
import java.util.*

sealed class Req
data class GetFoo(var userId: UUID): Req()

sealed class Result
data class Succeeded<out T>(val value: T) : Result()
data class Failed(val e: MoneyTransferException) : Result()

object MoneyTransferServer {
    private val log = LoggerFactory.getLogger(this::class.java)

    fun moneyTransferApp(): RoutingHttpHandler {
        return routes(
            foo()
        )
    }

    private fun foo(): RoutingHttpHandler {
        return "foo" bind Method.GET to { req: Request ->
            val reqLens = Body.auto<GetFoo>().toLens()
            val respLens = Body.auto<Succeeded<String>>().toLens()
            val excLens = Body.auto<Failed>().toLens()
            try {
                val msg = reqLens.extract(req)
                val userId = msg.userId
                Response(OK).with(
                    respLens.of(Succeeded("foo bar for ${userId}"))
                )
            } catch (e: Exception) {
                log.error("Exception = ${e.stackTrace}, msg = ${e.message}")
                Response(BAD_REQUEST).with(
                    excLens.of(Failed(MoneyTransferException(e.message!!)))
                )
            }

        }
    }


}


fun main(args: Array<String>) {

    val db = DBMaker.memoryDB().make()

    val app = MoneyTransferServer.moneyTransferApp()

    val jettyServer = app.asServer(Netty(9999)).start()

}