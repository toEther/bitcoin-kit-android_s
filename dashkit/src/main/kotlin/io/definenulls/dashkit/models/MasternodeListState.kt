package io.definenulls.dashkit.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class MasternodeListState(var baseBlockHash: ByteArray) {

    @PrimaryKey
    var primaryKey: String = "primary-key"

}
