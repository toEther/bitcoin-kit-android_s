package io.definenulls.bitcoincash.blocks.validators

import io.definenulls.bitcoincore.blocks.validators.BlockValidatorException
import io.definenulls.bitcoincore.blocks.validators.IBlockChainedValidator
import io.definenulls.bitcoincore.blocks.validators.IBlockValidator
import io.definenulls.bitcoincore.models.Block

class ForkValidator(
        private val forkHeight: Int,
        private val expectedBlockHash: ByteArray,
        private val concreteBlockValidator: IBlockValidator
) : IBlockChainedValidator {

    override fun isBlockValidatable(block: Block, previousBlock: Block): Boolean {
        return block.height == forkHeight
    }

    override fun validate(block: Block, previousBlock: Block) {
        if (!block.headerHash.contentEquals(expectedBlockHash)) {
            throw BlockValidatorException.WrongBlockHash(expectedBlockHash, block.headerHash)
        }

        concreteBlockValidator.validate(block, previousBlock)
    }
}