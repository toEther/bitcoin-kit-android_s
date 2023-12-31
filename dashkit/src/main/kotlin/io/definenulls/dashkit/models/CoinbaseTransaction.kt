package io.definenulls.dashkit.models

import io.definenulls.bitcoincore.io.BitcoinInputMarkable
import io.definenulls.bitcoincore.serializers.TransactionSerializer

class CoinbaseTransaction(input: BitcoinInputMarkable) {
    val transaction = TransactionSerializer.deserialize(input)
    val coinbaseTransactionSize: Long
    val version: Int
    val height: Long
    val merkleRootMNList: ByteArray
    val merkleRootQuorums: ByteArray?

    init {
        coinbaseTransactionSize = input.readVarInt()

        version = input.readUnsignedShort()
        height = input.readUnsignedInt()
        merkleRootMNList = input.readBytes(32)
        merkleRootQuorums = when {
            version >= 2 -> input.readBytes(32)
            else -> null
        }
    }
}
