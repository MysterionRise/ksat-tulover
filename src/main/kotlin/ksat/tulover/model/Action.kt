package ksat.tulover.model

import java.util.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.actor

sealed class Action
data class PrepareWithdraw(val to: UUID, val amount: Int): Action()
data class PrepareTopUp(val from: UUID, val amount: Int): Action()

fun CoroutineScope.counterActor() = actor<Action> {
    var money = 0 // actor state
    for (msg in channel) { // iterate over incoming messages
        when (msg) {
            is PrepareWithdraw -> {
                money -= msg.amount
                
            }
            is PrepareTopUp -> msg.response.complete(counter)
        }
    }
}