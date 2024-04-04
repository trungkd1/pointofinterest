package com.trungkieu.pointofinterest.features.categories.vm

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trungkieu.domain.features.categories.interactor.AddCategoryUseCase
import com.trungkieu.domain.features.categories.interactor.DeleteCategoryUseCase
import com.trungkieu.domain.features.categories.interactor.GetCategoriesUseCase
import com.trungkieu.domain.features.categories.interactor.GetCategoryUseCase
import com.trungkieu.domain.features.categories.interactor.UpdateCategoryUseCase
import com.trungkieu.pointofinterest.features.categories.ui.models.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Categories view model
 *
 * @param getCategoriesUseCase
 * @constructor
 * @property getCategoryUseCase
 * @property deleteCategoryUseCase
 * @property updateCategoryUseCase
 * @property addCategoryUseCase
 */
@HiltViewModel
class CategoriesViewModel @Inject constructor(
    getCategoriesUseCase: GetCategoriesUseCase,
    private val getCategoryUseCase: GetCategoryUseCase,
    private val deleteCategoryUseCase: DeleteCategoryUseCase,
    private val updateCategoryUseCase: UpdateCategoryUseCase,
    private val addCategoryUseCase: AddCategoryUseCase
) : ViewModel() {

    private val _detailedCategoriesUiState = MutableStateFlow<DetailedCategoriesUiState>(DetailedCategoriesUiState.Loading)
    val detailedCategoriesUiState = _detailedCategoriesUiState.asStateFlow()

    val categoriesState = getCategoriesUseCase(Unit).map { list ->
        list.map { it.toUiModel() }.groupBy { it.categoryType.toTitle() }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyMap()
    )

    // cách viết này giống như get set để code logic hơn
    // _itemsToDelete sẽ là SET
    // itemsToDelete sẽ là GET
    private val _itemsToDelete = MutableStateFlow<List<String>>(emptyList())
    val itemsToDelete = _itemsToDelete.asStateFlow()

    /**
     * On fetch detailed state : Cả CoroutineScope(Dispatchers.IO).launch và viewModelScope.launch đều là coroutine
     * 1. CoroutineScope(Dispatchers.IO).launch khi muốn sử lý trên luồng nào rõ ràng.
     * Ví dụ, bạn có thể cần chạy một tác vụ trên luồng chính (Dispatchers.Main) thay vì luồng I/O mặc định (Dispatchers.IO) để cập nhật trực tiếp UI.
     * 2. viewModelScope.launch : được sử dụng để thực hiện các tác vụ liên quan đến dữ liệu của ViewModel,
     * Ví dụ như tải dữ liệu từ remote server hoặc database.
     * @param categoryId
     */
    fun onFetchDetailedState(categoryId: String?) {
//        CoroutineScope(Dispatchers.IO).launch {
//
//        }
        viewModelScope.launch {
            _detailedCategoriesUiState.value = DetailedCategoriesUiState.Loading
            if (categoryId.isNullOrEmpty()) {
                _detailedCategoriesUiState.value = DetailedCategoriesUiState.Success(null)
                return@launch
            }
            val selectedCategory = getCategoryUseCase(GetCategoryUseCase.Params(categoryId))
            _detailedCategoriesUiState.value = DetailedCategoriesUiState.Success(selectedCategory.toUiModel())
        }
    }

    /**
     * On update item
     *
     * @param categoryUiModel
     * @param name
     * @param color
     */
    fun onUpdateItem(categoryUiModel: CategoryUiModel, name: String, color: Color) {
        viewModelScope.launch {
            // Lệnh copy() được sử dụng trong đoạn mã này nhằm tạo ra một bản sao của đối tượng categoryUiModel với các giá trị cập nhật cho title và color,
            // đồng thời đảm bảo tính bất biến của đối tượng ban đầu
            val updateObject = categoryUiModel.copy(title = name, color = color).toDomainModel()
            // gọi hàm updateCategoryUseCase để truyền vào updateObject với 2 giá trị title và color thay đổi
            updateCategoryUseCase(UpdateCategoryUseCase.Params(updateObject))
        }
    }

    /**
     * On create item
     *
     * @param name
     * @param color
     */
    fun onCreateItem(name: String, color: Color) {
        viewModelScope.launch {
            addCategoryUseCase(AddCategoryUseCase.Params(name, color.toArgb()))
        }
    }

    /**
     * On delete item
     *
     * @param id
     */
    fun onDeleteItem(id: String) {
        val updatedList = itemsToDelete.value.toMutableList()
        updatedList.add(id)
        _itemsToDelete.value = updatedList
    }

    /**
     * On undo delete item
     *
     * @param id
     */
    fun onUndoDeleteItem(id: String) {
        val updatedList = itemsToDelete.value.toMutableList()
        updatedList.remove(id)
        _itemsToDelete.value = updatedList
    }

    /**
     * On commit delete item
     *
     * @param id
     */
    fun onCommitDeleteItem(id: String) {
        viewModelScope.launch {
            deleteCategoryUseCase(DeleteCategoryUseCase.Params(id))
        }
    }
}
