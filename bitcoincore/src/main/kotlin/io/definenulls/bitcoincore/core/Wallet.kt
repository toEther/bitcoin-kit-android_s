package io.definenulls.bitcoincore.core

import io.definenulls.bitcoincore.models.PublicKey
import io.definenulls.hdwalletkit.HDKey
import io.definenulls.hdwalletkit.HDWallet
import java.lang.Exception

class Wallet(private val hdWallet: HDWallet, val gapLimit: Int): IPrivateWallet {

    fun publicKey(account: Int, index: Int, external: Boolean): PublicKey {
        val hdPubKey = hdWallet.hdPublicKey(account, index, external)
        return PublicKey(account, index, external, hdPubKey.publicKey, hdPubKey.publicKeyHash)
    }

    fun publicKeys(account: Int, indices: IntRange, external: Boolean): List<PublicKey> {
        val hdPublicKeys = hdWallet.hdPublicKeys(account, indices, external)

        if (hdPublicKeys.size != indices.count()) {
            throw HDWalletError.PublicKeysDerivationFailed()
        }

        return indices.mapIndexed { position, index ->
            val hdPublicKey = hdPublicKeys[position]
            PublicKey(account, index, external, hdPublicKey.publicKey, hdPublicKey.publicKeyHash)
        }
    }

    override fun privateKey(account: Int, index: Int, external: Boolean): HDKey {
        return hdWallet.privateKey(account, index, external)
    }

    open class HDWalletError : Exception() {
        class PublicKeysDerivationFailed : HDWalletError()
    }

}
