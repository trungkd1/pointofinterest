package com.trungkieu.pointofinterest.features.home.vm

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trungkieu.data.features.categories.model.PoiModel
import com.trungkieu.domain.features.categories.interactor.GetCategoriesByIdsUseCase
import com.trungkieu.domain.features.poi.interactor.DeletePoiUseCase
import com.trungkieu.domain.features.poi.interactor.GetDetailedPoiUseCase
import com.trungkieu.domain.features.poi.interactor.GetPoiListUseCase
import com.trungkieu.domain.features.poi.interactor.GetUsedCategoriesUseCase
import com.trungkieu.pointofinterest.core.utils.ErrorDisplayObject
import com.trungkieu.pointofinterest.core.utils.RetryTrigger
import com.trungkieu.pointofinterest.core.utils.retryableFlow
import com.trungkieu.pointofinterest.core.utils.toDisplayObject
import com.trungkieu.pointofinterest.features.categories.ui.models.toUiModel
import com.trungkieu.pointofinterest.features.home.ui.models.PoiListItem
import com.trungkieu.pointofinterest.features.home.ui.models.PoiSortByUiOption
import com.trungkieu.pointofinterest.features.home.ui.models.toDomain
import com.trungkieu.pointofinterest.features.home.ui.models.toListDataModel
import com.trungkieu.pointofinterest.features.home.ui.models.toListUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Home view model
 *
 * @param getUsedCategoriesUseCase
 * @constructor
 * @property savedStateHandle
 * @property getCategoriesUseCase
 * @property getPoiListUseCase
 */
@OptIn(FlowPreview::class)
@HiltViewModel
// tạO class HomeViewModel và dùng HiltModel để quản lý Dependency Ịnjection
// ViewModel đóng vai trò quan trọng như lớp trung gian giữa Model và View.
// chức năng chính của ViewModel: lưu trữ dữ liệu, Cung cấp dữ liệu cho View, Quản lý logic giao diện người dùng, Kết nối với Model
// Hilt sẽ chịu trách nhiệm tạo các instance cho các đối tượng
class HomeViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    getUsedCategoriesUseCase: GetUsedCategoriesUseCase,
    private val deletePoiUseCase: DeletePoiUseCase,
    private val getDetailedPoiUseCase: GetDetailedPoiUseCase,
    private val getCategoriesUseCase: GetCategoriesByIdsUseCase,
    private val getPoiListUseCase: GetPoiListUseCase
) : ViewModel() {

    // getUsedCategoriesUseCase(Unit): Hàm này có thể trả về một danh sách ID danh mục được coi là "đã sử dụng".
    //
    @OptIn(ExperimentalCoroutinesApi::class)
    val categoriesState = getUsedCategoriesUseCase(Unit).flatMapLatest { ids -> //biến ids sẽ chứa danh sách ID danh mục đã sử dụng.
        // getCategoriesUseCase(GetCategoriesByIdsUseCase.Params(ids)). Hàm này có thể lấy dữ liệu danh mục đầy đủ bằng cách sử dụng ID được cung cấp.
        getCategoriesUseCase(GetCategoriesByIdsUseCase.Params(ids))
            .map { list -> list.map { it.toUiModel() } } // là một hàm chuyển đổi đối tượng danh mục thành một phiên bản đơn giản hơn hoặc định dạng khác phù hợp với UI (ví dụ, loại bỏ các trường không cần thiết).
    // Biến list ở đây chính là danh sách các đối tượng danh mục nhận được từ getCategoriesUseCase.
    }.stateIn(
        // stateIn được sử dụng để tạo một StateFlow lưu trữ giá trị phát ra mới nhất
        // StateFlow lưu trữ giá trị trạng thái hiện tại, giúp truy cập và cập nhật trạng thái dễ dàng.
        scope = viewModelScope,
        // kiểm soát cách luồng bắt đầu và dừng phát ra giá tri, hoạt động tối đa 5s
        started = SharingStarted.WhileSubscribed(5_000),
        // cung cấp giá trị ban đầu cho StateFlow
        initialValue = emptyList()
    )

    // đoạn code tạo một biến riêng _homeUiContentState với kiểu dữ liệu HomeUiContentState và gán giá trị ban đầu là Loading.
    private val _homeUiContentState = MutableStateFlow<HomeUiContentState>(HomeUiContentState.Loading)
    // MutableStateFlow có thể thay đổi trực tiếp, StateFlow chỉ đọc.
    // Phương thức asStateFlow() chuyển đổi _homeUiContentState từ MutableStateFlow (có thể đọc và viết) sang StateFlow (chỉ có thể đọc).
    val homeUiContentState = _homeUiContentState.asStateFlow()

    // khai báo _displaySortOptionUiState với giá trị ban đầu là DATE
    private val _displaySortOptionUiState = MutableStateFlow(PoiSortByUiOption.DATE)
    // Phương thức asStateFlow() chuyển đổi _displaySortOptionUiState từ MutableStateFlow (có thể đọc và viết) sang StateFlow (chỉ có thể đọc).
    val displaySortOptionUiState = _displaySortOptionUiState.asStateFlow()

    //Lợi ích sử dụng lazy: Tiết kiệm bộ nhớ, tối ưu hiệu suất
    private val retryTrigger by lazy { RetryTrigger() }

    @OptIn(ExperimentalCoroutinesApi::class)
    private val mainPoiState =
        savedStateHandle.getStateFlow(KEY_SORT_OPTION, PoiSortByUiOption.DATE)
            .onStart { _homeUiContentState.value = HomeUiContentState.Loading }
            .onEach { _displaySortOptionUiState.value = it }
            .flatMapLatest { sortOption -> // chính là kiểu giá trị PoiSortByUiOption
                retryableFlow(retryTrigger = retryTrigger) { ->
                    sortOption.name
                    // return@retryableFlow : cách viết này để thoát khỏi hàm con lamda expression
                    // sortOption gọi hàm toDomain() và sẽ check giá trị được gán từ trước là "DATE"
                    return@retryableFlow getPoiListUseCase(GetPoiListUseCase.Params(sortOption.toDomain()))
                        // onStart: Đặt trạng thái nội dung giao diện người dùng chính
                        .onStart { _homeUiContentState.value = HomeUiContentState.Loading }
                        // Giới hạn tần suất phát dữ liệu để tránh làm giao diện người dùng bị choáng ngợp bởi những thay đổi nhanh chóng.
                        .debounce(100)
                        .map { list -> list.map { it.toListUiModel() } }
                        .onEach { // Cập nhật trạng thái giao diện người dùng
                            if (it.isEmpty()) _homeUiContentState.value = HomeUiContentState.Empty
                            else _homeUiContentState.value = HomeUiContentState.Result(it)
                        }
                        .catch { _homeUiContentState.value = HomeUiContentState.Error(it.toDisplayObject()) }
                }
            }
            .stateIn(
                // stateIn được sử dụng để tạo một StateFlow lưu trữ giá trị phát ra mới nhất
                // StateFlow lưu trữ giá trị trạng thái hiện tại, giúp truy cập và cập nhật trạng thái dễ dàng.
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = PoiSortByUiOption.DATE
            )

    init {
        mainPoiState.launchIn(viewModelScope)
    }

    /** On retry */
    fun onRetry() {
        retryTrigger.retry()
    }

    fun onDeletePoi(item: PoiListItem) {
        // to do : need to convert from PoiListItem to PoiModel to carry out deletePoiUseCase
        viewModelScope.launch {
           val modelState =  getDetailedPoiUseCase(GetDetailedPoiUseCase.Params(item.id))
            deletePoiUseCase(DeletePoiUseCase.Params(modelState))
        }
    }

    /**
     * On apply sort by
     *
     * @param option
     */
    fun onApplySortBy(option: PoiSortByUiOption) {
        savedStateHandle[KEY_SORT_OPTION] = option
    }

    /**
     * Home ui content state : dùng để khai báo các trạng thái hoạt động của màn hình Home
     *
     * @constructor Create empty Home ui content state
     */
    sealed class HomeUiContentState {
        object Loading : HomeUiContentState()
        object Empty : HomeUiContentState()

        /**
         * Result
         *
         * @constructor Create empty Result
         * @property poiList
         */
        data class Result(val poiList: List<PoiListItem>) : HomeUiContentState()

        /**
         * Error
         *
         * @constructor Create empty Error
         * @property displayObject
         */
        data class Error(val displayObject: ErrorDisplayObject) : HomeUiContentState()
    }

    companion object {
        private const val KEY_SORT_OPTION = "sort_option"
    }
}