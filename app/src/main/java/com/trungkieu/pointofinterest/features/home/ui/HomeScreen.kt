package com.trungkieu.pointofinterest.features.home.ui

import androidx.compose.animation.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.trungkieu.pointofinterest.R
import com.trungkieu.pointofinterest.core.utils.containsId
import com.trungkieu.pointofinterest.features.categories.ui.models.CategoryUiModel
import com.trungkieu.pointofinterest.features.home.ui.composable.AddMoreButton
import com.trungkieu.pointofinterest.features.home.ui.composable.CategoryFilterChips
import com.trungkieu.pointofinterest.features.home.ui.composable.PoiCard
import com.trungkieu.pointofinterest.features.home.ui.models.PoiListItem
import com.trungkieu.pointofinterest.features.home.ui.models.PoiSortByUiOption
import com.trungkieu.pointofinterest.features.home.ui.models.toSubTitle
import com.trungkieu.pointofinterest.features.home.ui.models.toTitle
import com.trungkieu.pointofinterest.features.home.vm.HomeViewModel
import com.trungkieu.pointofinterest.navigation.Screen
import com.trungkieu.pointofinterest.ui.composables.uikit.ActionButton
import com.trungkieu.pointofinterest.ui.composables.uistates.EmptyView
import com.trungkieu.pointofinterest.ui.composables.uistates.ErrorView
import com.trungkieu.pointofinterest.ui.composables.uistates.ProgressView

@Composable
fun HomeScreen(
    // Giao diện màn hình home với các parameter được truyền vào
    // trạng thái sort trên dialog
    showSortDialogState: Boolean,
    // Một callback đóng dialog
    onCloseSortDialog: () -> Unit,
    // một callback cho điều hướng
    onNavigate: (Screen, List<Pair<String, Any>>) -> Unit,
    // tạo một viewModel được quản lý bởi Hilt, có thể tạo trực tiếp ( viewModel: HomeViewModel = hiltViewModel())
    viewModel: HomeViewModel
) {

    // tương đương với kotlin "val homeContentState: State<HomeUiContent> = viewModel.homeUiContentState.collectAsState()"
    //    Biến homeContentState sẽ chứa giá trị HomeUiContentState mới nhất được phát ra từ viewModel.homeUiContentState.
    //    Giá trị này có thể là:
    //    Loading: Nếu ứng dụng đang tải dữ liệu.
    //    Empty: Nếu không có dữ liệu để hiển thị.
    //    Result(poiList): Nếu dữ liệu được tải thành công, chứa danh sách các điểm quan tâm (poiList).
    //    Error(displayObject): Nếu gặp lỗi khi tải dữ liệu.
    val homeContentState by viewModel.homeUiContentState.collectAsStateWithLifecycle()
    // tương đương "categoriesState = viewModel.categoriesState.collectAsStateWithLifecycle()"
    val categoriesState by viewModel.categoriesState.collectAsStateWithLifecycle()
    // tương đương "selectedSortByOption = viewModel.displaySortOptionUiState.collectAsStateWithLifecycle()"
    val selectedSortByOption by viewModel.displaySortOptionUiState.collectAsStateWithLifecycle()
    // Như khai báo  một List<String> trong java
    // người ta dùng remember để xử lý các vấn đề liên quan đến UI trong Jetpack Compose.
    // remember là một hàm composable đặc biệt giúp lưu trữ trạng thái và các giá trị khác trong một composable.
    // Việc sử dụng remember mang lại nhiều lợi ích:
    // - Tránh recompose không cần thiết:
    // - Cập nhật UI phản hồi
    // - Chia sẻ dữ liệu giữa các composable
    // - Tạo luồng dữ liệu
    val selectedFiltersState = remember { mutableStateListOf<String>() }

    // Nội dung màn hình home sẽ có cácm tham số truyền vào như
    // homeContentState : trạng thái nội dung màn hình home
    // categoriesState : trạng thái các thể loại
    // selectedSortByOption : các lựa chọn để sort
    // selectedFiltersState : trạng thái lọc hiển thị
    // onRetry : là một callback được trả về kết quả
    // onApplySortBy : là một callback khi apply một loại sort
    // onCloseSortDialog : là một callback khi đóng màn hginhf
    // onNavigate : là một callback khi chuyển màn hình
    HomeScreenContent(
        homeContentState = homeContentState,
        categoriesState = categoriesState,
        selectedSortByOption = selectedSortByOption,
        selectedFiltersState = selectedFiltersState,
        showSortDialogState = showSortDialogState,
        onRetry = viewModel::onRetry,
        onApplySortByOption = viewModel::onApplySortBy,
        onCloseSortDialog = onCloseSortDialog,
        onNavigate = onNavigate
    )
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HomeScreenContent(
    homeContentState: HomeViewModel.HomeUiContentState,
    categoriesState: List<CategoryUiModel>,
    selectedSortByOption: PoiSortByUiOption,
    selectedFiltersState: MutableList<String>,
    showSortDialogState: Boolean,
    onRetry: () -> Unit,
    onApplySortByOption: (PoiSortByUiOption) -> Unit,
    onCloseSortDialog: () -> Unit,
    onNavigate: (Screen, List<Pair<String, Any>>) -> Unit
) {
    // trước tiên phải tạo một column vói các tông số được định nghĩa trong Modifier từ height, weight và background
    Column(
        Modifier
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Animmate hiển thị giao diện
        AnimatedVisibility(
            visible = categoriesState.isEmpty().not(),
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            // trong Animation sẽ có nội dung được hiển thị
            // onAddCategories : Một callback di chuyển đến màn hình Categories với danh sách trống ban đầu, xong khi hoàn thành sẽ có kq trả về
            // selectedFilters : List<String> các từ được filter
            // categories : List các CategoryUiModel
            HomeScreenFilterContent(
                onAddCategories = { onNavigate(Screen.Categories, emptyList()) },
                selectedFilters = selectedFiltersState,
                categories = categoriesState,
            ) { filterId -> // filterId là giá trị String trả về của onClick
                    if (filterId in selectedFiltersState) selectedFiltersState.remove(filterId)
                    else selectedFiltersState.add(filterId)
                }
            }
        }
        // Tạo 1 cái box và điều chỉnh padding với Modifier
        Box(
            modifier = Modifier
                .padding(PaddingValues(start = 16.dp, end = 16.dp))
//                .weight(1f)
        ) {
            when (homeContentState) {
                // khi trạng thái homeContentState là Loading thì thực hiện hàm ProgressView()
                is HomeViewModel.HomeUiContentState.Loading -> ProgressView( )
                // khi trạng thái homeContentState là Empty thì gọi hàm EmptyView và truyền param là message
                is HomeViewModel.HomeUiContentState.Empty -> EmptyView(message = stringResource(id = R.string.message_ui_state_empty_main_screen))
                // Khi trạng thái homeContentState là lỗi sẽ gán giá trị cho biến mới là errorState, biến này sẽ truyền vào UI ErrorView và hiển thị đối tượng đối tương lỗi
                // đồng thời cũng truyền vào một callback để gọi hàm trả về là onRetry ở HomeViewModel
                is HomeViewModel.HomeUiContentState.Error -> {
                    val errorState = homeContentState as HomeViewModel.HomeUiContentState.Error
                    ErrorView(displayObject = errorState.displayObject, onRetryClick = onRetry)
                }

                else -> {
                    // val filteredList : khai báo biến này lưu trữ kết quả quá trình filter
                    // (homeContentState as HomeViewModel.HomeUiContentState.Result) : đây là cách viết để ép kiểu biến homeContentState sang kiểu dữ liệu HomeViewModel.HomeUiContentState.Result
                    // .poiList : phần này là truy cập vào danh sách các items
                    // .filter { poi -> : poi đại diện cho từng phần tử được trả về sau khi được lọc.
                    // filteredList lấy danh sách ban đầu từ poiList, và danh sách này sẽ lọc nếu thỏa mãn các điều kiện
                    // HomeUiContentState.Result(it) đã được truyền giá trị tại dòng 98 của HomeViewModel
                    // lấy giá trị sẽ là HomeUiContentState.Result.poiList
                    val filteredList = (homeContentState as HomeViewModel.HomeUiContentState.Result).poiList.filter { poi ->
                        // selectedFiltersState.isEmpty(): Kiếm tra selectedFiltersState có rỗng hay không, nếu rỗng thì giữ lại tất cả danh sách
                        // selectedFiltersState.all { filterId -> ... }: Kiểm tra xem tất cả các bộ lọc đã chọn
                        // { filterId -> ... } là từng items đã được filter trong danh sách selectedFiltersState
                        // selectedFiltersState.isEmpty() || selectedFiltersState.all { filterId -> poi.categories.containsId(filterId) }
                        // là điều kiện lọc để xác định xem một POI có nên được giữ lại trong danh sách đã lọc hay không.
                        // tức là Mã poi.categories.containsId(filterId) được thực thi để kiểm tra xem POI hiện tại có chứa ID bộ lọc hay không.
                        // - Nếu POI chứa ID bộ lọc, nó sẽ được giữ lại trong danh sách đã lọc.
                        // - Nếu POI không chứa ID bộ lọc, nó sẽ bị loại bỏ.
                        selectedFiltersState.isEmpty() || selectedFiltersState.all { filterId -> poi.categories.containsId(filterId) }
                    }
                    // sử dụng AnimatedContent để hiển thị nội dung động
                    // giá trị true cho targetState nếu bạn muốn hiển thị một nội dung cụ thể, hoặc false nếu bạn muốn ẩn nội dung đó. dựa trên filteredList.isEmpty()
                    // Nếu chỉ cần kiểm tra trạng thái rỗng đơn giản,  có thể sử dụng filteredList.isEmpty() trực tiếp.
                    // Tuy nhiên, nếu  cần thêm logic xử lý hoặc muốn code linh hoạt, dễ mở rộng và dễ đọc hơn, bạn nên sử dụng targetState
                    AnimatedContent(targetState = filteredList.isEmpty()) { targetState ->
                        if (targetState) { // Nếu targetState == true, tức là sẽ hiển thị một nội dung cụ thể là cái EmptyView
                            EmptyView(message = stringResource(id = R.string.message_ui_state_empty_main_screen_no_filters))
                        } else { // ngược lại
                            PoiListContent(poiItems = filteredList) { id -> // id này sẽ là string được trả về khi thực hiện hành động onSelected
                                // onNavigate là một hàm callback, gán các giá trị Screen.ViewPoiDetailed và listOf(Screen.ViewPoiDetailed.ARG_POI_ID to id)
                                // và sẽ được xử lý tại PoiStateApp.navigate
                                onNavigate(
                                    Screen.ViewPoiDetailed,
                                    // code này tạo ra một danh sách chứa một cặp key-value.
                                    // listOf: Hàm tạo danh sách trong Kotlin.
                                    // creen.ViewPoiDetailed.ARG_POI_ID: Key là một biến static được định nghĩa trong class Screen.ViewPoiDetailed
                                    // sử dụng key static Screen.ViewPoiDetailed.ARG_POI_ID giúp đảm bảo rằng danh sách luôn chứa giá trị id mới nhất.
                                    // và tại hàm xử lý ở class PoitateApp với hàm navigateTo sẽ tạo thành một chuỗi ký tự dài
                                    // vd : ARG_POI_ID = "poiId". thì sẽ tạo thành các chuỗi "...poiId_20,poiId_21,poiId_22..."
                                    // hàm sẽ tạo route = "ViewPoiDetailed?poi_id=123"
                                    /**
                                     * val pair = "name" to "John Doe"
                                     *
                                     * println(pair.first) // name
                                     * println(pair.second) // John Doe
                                     *
                                     * */
                                    // đây là cách giải thích bên dưới
                                    listOf(Screen.ViewPoiDetailed.ARG_POI_ID to id)
                                )
                            }
                        }
                    }
                }
            }

            // nếu giá trị = true sẽ hiển thị dialog sort
            if (showSortDialogState) {
                Dialog(
                    // Đoạn code trên tạo ra một Dialog object với một event listener onDismissRequest. Event listener này sẽ được gọi khi người dùng yêu cầu đóng Dialog.
                    onDismissRequest = onCloseSortDialog,
                    content = {
                        // tạo giao diện hình card
                        Card(
                            modifier = Modifier
                                .fillMaxWidth(1f),
                            // dạng bo tròn góc với giã trị 8.dp
                            shape = RoundedCornerShape(8.dp),

                            colors = CardDefaults.cardColors(
                                // containerColor dạng background, quy định màu nền toàn bộ cái card
                                // contentColor Quy định màu sắc của các thành phần nội dung bên trong Card từ text,icon, hình ảnh
                                containerColor = MaterialTheme.colorScheme.background,
                                contentColor = MaterialTheme.colorScheme.onBackground
                            )
                        ) {
                            // Trong Compose UI, không bắt buộc phải tạo Column trong mọi trường hợp. Tuy nhiên, Column thường được sử dụng vì nó mang lại một số lợi ích:
                            //1. Sắp xếp các thành phần theo chiều dọc:
                            //2. Tự động điều chỉnh kích thước:
                            //3. Column có thể được lồng嵌 vào nhau để tạo ra các layout phức tạp hơn. Ví dụ, bạn có thể sử dụng Column để tạo bố cục dạng lưới hoặc bố cục tab.
                            Column(
                                modifier = Modifier.background(
                                    // color ở đây có nghĩa là background column, với màu của tertiary với độ mờ 30% so với màu gốc , là hiện tại màu này còn 70%
                                    color = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.3f),
                                    shape = RoundedCornerShape(8.dp)
                                )
                            ) {
                                Text(
                                    modifier = Modifier.padding(end = 24.dp, top = 24.dp, start = 24.dp, bottom = 8.dp),
                                    text = stringResource(id = R.string.title_dialog_sort_by),
                                    style = MaterialTheme.typography.titleMedium
                                )
                                // forEach lặp qua từng giá trị của enum PoiSortByUiOption
                                PoiSortByUiOption.values().forEach { option -> // trong đoạn code bạn cung cấp, option có thể là một trong ba giá trị:DATE, SEVERITY, and TITLE
                                    Row(
                                        modifier = Modifier
                                            // tạo cái tag trong các row
                                            .testTag("sort_selector${option.name}")
                                            .fillMaxWidth()
                                            .clickable { // cho phép người dùng click vào các row thì hàm Hàm onApplySortByOption(option), onCloseSortDialog() sẽ được gọi.
                                                onApplySortByOption(option)
                                                onCloseSortDialog()
                                            },
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Start
                                    ) {
                                        RadioButton(
                                            modifier = Modifier.padding(vertical = 8.dp, horizontal = 24.dp),
                                            selected = (option == selectedSortByOption),
                                            onClick = null,
                                            // set màu sắc khi click và unClick
                                            colors = RadioButtonDefaults.colors(
                                                selectedColor = MaterialTheme.colorScheme.secondary,
                                                unselectedColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f)
                                            )
                                        )

                                        Column(modifier = Modifier.padding(end = 24.dp, top = 8.dp, bottom = 8.dp)) {
                                            Text(
                                                text = stringResource(id = option.toTitle()),
                                                style = MaterialTheme.typography.bodyLarge,
                                                fontSize = 18.sp,
                                                color = MaterialTheme.colorScheme.onBackground
                                            )

                                            Text(
                                                text = stringResource(id = option.toSubTitle()),
                                                style = MaterialTheme.typography.labelMedium,
                                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                                            )
                                        }
                                    }
                                }
                                // Spacer là một component được sử dụng để thêm khoảng trống giữa các thành phần.
                                Spacer(modifier = Modifier.size(4.dp))

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 24.dp, end = 8.dp, bottom = 8.dp),
                                    horizontalArrangement = Arrangement.End,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    // đây là dạng button nổi, có bóng mở bên dưới
                                    ActionButton(
                                        text = stringResource(id = R.string.cancel),
                                        // thiết lập chế độ alpha cho màu nền button
                                        containerColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0f),
                                        onClick = { onCloseSortDialog() }
                                    )
                                }
                            }
                        }
                    },
                    properties = DialogProperties(
                        dismissOnBackPress = true,
                        dismissOnClickOutside = true
                    )
                )
            }
        }
    }


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PoiListContent(
    poiItems: List<PoiListItem>,
    onPoiSelected: (String) -> Unit,
) {
    // LazyColumn là một component trong Compose UI được sử dụng để tạo danh sách cuộn theo chiều dọc.
    LazyColumn(
        modifier = Modifier.testTag("poi_content_list")
    ) {
        // Việc cung cấp key là một cách tốt để cải thiện hiệu suất của LazyColumn khi thêm, xóa hoặc sắp xếp lại các mục.
        // việc sử dụng cùng key trong các item là ko đc phép, nên phải set key = {item -> item.id]
        // cách viết items(poiItems, key = { item -> item.id }) có nghĩa là truyền danh sách poiItems vào component items.
        // item -> item.id: Lấy id của mỗi POI trong poiItems làm mã định danh.
        items(poiItems, key = { item -> item.id }) { item ->
            Column(modifier = Modifier.animateItemPlacement()) {
                PoiCard(poiListItem = item, onClick = onPoiSelected)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun HomeScreenFilterContent(
    onAddCategories: () -> Unit,
    categories: List<CategoryUiModel>,
    selectedFilters: List<String>,
    onClickFirstGroup: (String) -> Unit,
) {
    LazyRow(
        modifier = Modifier.testTag("filters_horizontal_collection")
    ) {
        // Sử dụng itemsIndexed khi bạn cần truy cập cả vị trí (index) và dữ liệu của từng mục trong danh sách.
        itemsIndexed(categories, key = { _, item -> item.id }) { index, item ->
            // index: Vị trí của mục trong danh sách (bắt đầu từ 0).
            // item: Dữ liệu của mục.
            if (index == 0) Spacer(modifier = Modifier.width(16.dp))

            CategoryFilterChips(
                categoryListItem = item,
                onClick = onClickFirstGroup ,
                isSelected = item.id in selectedFilters
            )
            Spacer(modifier = Modifier.width(8.dp))


        }
        // Thêm một button vào bên dưới list item
        item(key = "AddMoreButton") {
            AddMoreButton(onClick = onAddCategories)
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
}