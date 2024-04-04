package com.trungkieu.data.features.poi.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.trungkieu.data.core.UNSPECIFIED_ID
import kotlinx.datetime.Instant

@Entity(tableName = "table_poi_comments")
class PoiCommentEntity(
    @ColumnInfo(name = "parent_id")
    val parentId: Int,
    val body: String,
    val creationDataTime: Instant
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = UNSPECIFIED_ID
}