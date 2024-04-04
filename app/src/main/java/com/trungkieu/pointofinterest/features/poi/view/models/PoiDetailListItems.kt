package com.trungkieu.pointofinterest.features.poi.view.models

import com.trungkieu.data.features.categories.model.PoiComment
import com.trungkieu.data.features.categories.model.PoiModel
import com.trungkieu.pointofinterest.core.utils.CommonFormats
import com.trungkieu.pointofinterest.core.utils.toStringWithFormat
import com.trungkieu.pointofinterest.features.categories.ui.models.CategoryUiModel
import com.trungkieu.pointofinterest.features.categories.ui.models.toUiModel

sealed class PoiDetailListItem(val uniqueKey: String) {
    data class ImageItem(val imageUrl: String) : PoiDetailListItem(ImageItem::class.java.simpleName)
    data class MetadataItem(val dateTime: String) : PoiDetailListItem(MetadataItem::class.java.simpleName)
    data class CategoriesItem(val categoryUiModel: List<CategoryUiModel>) : PoiDetailListItem(CategoriesItem::class.java.simpleName)
    data class TitleItem(val text: String) : PoiDetailListItem(TitleItem::class.java.simpleName)
    data class BodyItem(val text: String) : PoiDetailListItem(BodyItem::class.java.simpleName)
    data class ContentUrl(val source: String, val url: String) : PoiDetailListItem(ContentUrl::class.java.simpleName)
    data class CommentsCount(val count: Int) : PoiDetailListItem(CommentsCount::class.java.simpleName)
    data class CommentItem(val id: String, val body: String, val dateTime: String, val shouldShowDivider: Boolean) : PoiDetailListItem(id)
}

fun PoiModel.toUIModelWithComments(comments: List<PoiComment>): List<PoiDetailListItem> {
    val result = arrayListOf<PoiDetailListItem>()
    imageUrl?.takeIf { it.isNotEmpty() }?.let { url -> result += PoiDetailListItem.ImageItem(url) }
    result += PoiDetailListItem.MetadataItem(creationDate.toStringWithFormat(CommonFormats.FORMAT_DATE_WITH_MONTH_NAME_AND_TIME))
    result += PoiDetailListItem.CategoriesItem(categories.map { it.toUiModel() })
    result += PoiDetailListItem.TitleItem(title)
    body?.takeIf { it.isNotEmpty() }?.let { body -> result += PoiDetailListItem.BodyItem(body) }
    contentLink?.takeIf { it.isNotEmpty() }
        ?.let { contentLink -> result += PoiDetailListItem.ContentUrl(requireNotNull(source), contentLink) }
    result += PoiDetailListItem.CommentsCount(comments.size)

    comments.forEachIndexed { index, comment ->
        result += PoiDetailListItem.CommentItem(
            comment.id,
            comment.message,
            comment.commentDate.toStringWithFormat(CommonFormats.FORMAT_DATE_WITH_MONTH_NAME_AND_TIME),
            shouldShowDivider = index < comments.size - 1
        )
    }

    return result
}