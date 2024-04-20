package com.trungkieu.pointofinterest.features.categories.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SnackbarResult
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.trungkieu.pointofinterest.features.categories.ui.composable.CategoryTypeHeader
import com.trungkieu.pointofinterest.features.categories.ui.composable.CategoryView
import com.trungkieu.pointofinterest.features.categories.ui.composable.EditableCategoryView
import com.trungkieu.pointofinterest.features.categories.ui.models.CategoryAction
import com.trungkieu.pointofinterest.features.categories.ui.models.CategoryUiModel
import com.trungkieu.pointofinterest.features.categories.vm.CategoriesViewModel
import com.trungkieu.pointofinterest.navigation.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
// class này gồm màn hình và nội dung màn hình Categories
// Các giao diện con sẽ được tạo trong file CategoriesScreenComposables.kt
/**
 * Categories screen : màn hình hiển thị Categories với các parameter cơ bản
 *
 * @param snackbarHostState : truyền thông báo trạng thái snackbar
 * @param coroutineScope : truyền xử lý bất đồng bộ
 * @param viewModel : truyền model
 * @param onNavigate : truyền một callback điều hướng
 * @receiver
 */
@Composable
fun CategoriesScreen(
    snackbarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope,
    viewModel: CategoriesViewModel,
    onNavigate: (Screen, List<Pair<String, Any>>) -> Unit
) {

    val categoriesState by viewModel.categoriesState.collectAsStateWithLifecycle()
    val itemToDelete by viewModel.itemsToDelete.collectAsStateWithLifecycle()

    Box(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
        CategoriesContent(
            onNavigate = onNavigate,
            onDeleteItem = viewModel::onDeleteItem,
            onCommitDeleteItem = viewModel::onCommitDeleteItem,
            onUndoDeleteItem = viewModel::onUndoDeleteItem,
            coroutineScope = coroutineScope,
            snackbarHostState = snackbarHostState,
            categories = categoriesState,
            itemsToDelete = itemToDelete
        )
    }
}

/**
 * Categories content : các content cần có trong màn hình Categories
 *
 * @param onNavigate : truyền một callback điều hướng
 * @param onDeleteItem : truyền một callback onDeleteItem để xử lý item  cần xoá
 * @param onCommitDeleteItem : truyền một callback onCommitDeleteItem để xử lý item  cần xoá
 * @param onUndoDeleteItem  : truyền một callback onUndoDeleteItem để xử lý item  cần xoá
 * @param coroutineScope  : truyền xử lý bất đồng bộ
 * @param snackbarHostState  : truyền thông báo trạng thái snackbar
 * @param categories : truyền trạng thái các categories
 * @param itemsToDelete : truyền danh sách các item cần xoá
 * @receiver callback onNavigate
 * @receiver callback onDeleteItem
 * @receiver callback onCommitDeleteItem
 * @receiver callback onUndoDeleteItem
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CategoriesContent(
    onNavigate: (Screen, List<Pair<String, Any>>) -> Unit,
    onDeleteItem: (String) -> Unit,
    onCommitDeleteItem: (String) -> Unit,
    onUndoDeleteItem: (String) -> Unit,
    coroutineScope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    categories: Map<Int, List<CategoryUiModel>>,
    itemsToDelete: List<String>
) {

    Column {
        LazyColumn(
            Modifier
                .weight(1f)
                .testTag("categories_screen_full_list")
        ) { // trước tiên sẽ lặp và hiển thị theo group, và sau đó sẽ hiển thị các item con của group
            categories.entries.forEach { group ->
                stickyHeader(key = group.key) {
                    CategoryTypeHeader(type = stringResource(id = group.key))
                    Divider(
                        modifier = Modifier.animateItemPlacement(),
                        thickness = 0.5.dp,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f)
                    )
                }
                items(group.value.filter { it.id !in itemsToDelete }, { it.hashCode() }) { item ->
                    if (item.isMutableCategory) {
                        EditableCategoryView(Modifier.animateItemPlacement(), item) { action, model, displayData ->
                            when (action) {
                                CategoryAction.DELETE -> { // check action trả về là Delete
                                    onDeleteItem(item.id) // thực hiện callback gọi hàm xử lý tại viewmodel
                                    displayData?.let { snackbarDisplayData ->
                                        coroutineScope.launch {
                                            val snackBarResult = snackbarHostState.showSnackbar(
                                                message = snackbarDisplayData.message,
                                                actionLabel = snackbarDisplayData.action,
                                                duration = SnackbarDuration.Short
                                            )
                                            when (snackBarResult) {
                                                SnackbarResult.Dismissed -> onCommitDeleteItem(model.id)
                                                SnackbarResult.ActionPerformed -> onUndoDeleteItem(model.id)
                                            }
                                        }
                                    }
                                }
                                CategoryAction.EDIT -> onNavigate(
                                    Screen.CategoriesDetailed,
                                    listOf(Screen.CategoriesDetailed.ARG_CATEGORY_ID to model.id)
                                )
                            }
                        }
                    } else {
                        CategoryView(Modifier.animateItemPlacement(), item = item)
                    }
                    Divider(
                        modifier = Modifier.animateItemPlacement(),
                        thickness = 0.5.dp,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f)
                    )
                }
            }
        }
    }
}