package ksat.tulover

class MoneyTransferException(msg: String) : Exception(msg) {

    override fun fillInStackTrace(): Throwable {
        return Throwable()
    }
}