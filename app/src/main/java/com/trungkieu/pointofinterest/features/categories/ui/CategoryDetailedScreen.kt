package com.trungkieu.pointofinterest.features.categories.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.trungkieu.pointofinterest.R
import com.trungkieu.pointofinterest.features.categories.ui.composable.GridColorPalette
import com.trungkieu.pointofinterest.features.categories.ui.models.CategoryUiModel
import com.trungkieu.pointofinterest.features.categories.ui.models.DetailedCategoriesUiState
import com.trungkieu.pointofinterest.features.categories.vm.CategoriesViewModel
import com.trungkieu.pointofinterest.ui.composables.uikit.PrimaryButton
import com.trungkieu.pointofinterest.ui.composables.uistates.ProgressView

/**
 * Categories detailed screen
 *
 * @param categoryId
 * @param viewModel
 * @param onBack
 * @receiver
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CategoriesDetailedScreen(
    categoryId: String?,
    viewModel: CategoriesViewModel,
    onBack: () -> Unit
) {
    // dùng "by" khi muốn sử dụng thuộc tính của một đối tượng để truy cập giá trị. Mếu dùng by sẽ có hướng dùng thuộc tính nhiều hơn
    // dùng "=" khi muốn gán giá trị, và dùng giá trị trực tiếp
    val uiState by viewModel.detailedCategoriesUiState.collectAsStateWithLifecycle()
    var textFieldState by remember { mutableStateOf(TextFieldValue("")) }
    var selectedColorState by remember { mutableStateOf(Color.Transparent) }
    //  var aa by remember { mutableStateOf(true) } truy cập giá trị true sẽ viết aa.value()
    //  var aa = remember { mutableStateOf(true) } truy cập giá trị true sẽ viết aa


    val focusRequester by remember { mutableStateOf(FocusRequester()) }
    val keyboardController = LocalSoftwareKeyboardController.current

    // LaunchedEffect là một thành phần kết hợp trong Jetpack Compose cho phép bạn thực thi các chức năng tạm ngưng (coroutine) trong phạm vi của một thành phần khác
    LaunchedEffect(categoryId) { // bên trong LaunchedEffect sẽ khởi chạy một coroutine để lấy dữ liệu từ API
        viewModel.onFetchDetailedState(categoryId)
    }

    LaunchedEffect(key1 = uiState) {
        if (uiState is DetailedCategoriesUiState.Success) {
            // éo kiểu uiState thành DetailedCategoriesUiState.Success và lấy categoryUiModel để gán vào model
            val model = (uiState as DetailedCategoriesUiState.Success).categoryUiModel
            val name = model?.title ?: ""
            textFieldState = textFieldState.copy(name, selection = TextRange(name.length))
            selectedColorState = model?.color ?: Color.Transparent
        }
    }

    CategoriesDetailedContent(
        uiState = uiState,
        selectedColor = selectedColorState,
        focusRequester = focusRequester,
        keyboardController = keyboardController,
        textFieldValue = textFieldState,
        onTextChanged = { textFieldState = it },
        onColorSelected = { selectedColorState = it },
        onSave = { name, color ->
            viewModel.onCreateItem(name, color)
            onBack()
        },
        onUpdate = { item, name, color ->
            viewModel.onUpdateItem(item, name, color)
            onBack()
        }
    )
}

/**
 * Categories detailed content
 *
 * @param uiState
 * @param selectedColor
 * @param textFieldValue
 * @param focusRequester
 * @param keyboardController
 * @param onTextChanged
 * @param onColorSelected
 * @param onSave
 * @param onUpdate
 * @receiver
 * @receiver
 * @receiver
 * @receiver
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CategoriesDetailedContent(
    uiState: DetailedCategoriesUiState,
    selectedColor: Color,
    textFieldValue: TextFieldValue,
    focusRequester: FocusRequester,
    keyboardController: SoftwareKeyboardController?,
    onTextChanged: (TextFieldValue) -> Unit,
    onColorSelected: (Color) -> Unit,
    onSave: (String, Color) -> Unit,
    onUpdate: (CategoryUiModel, String, Color) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        when (uiState) {
            is DetailedCategoriesUiState.Loading ->
                ProgressView()
            is DetailedCategoriesUiState.Success -> {
                CategoriesDetailedSuccessContent(
                    selectedCategory = uiState.categoryUiModel,
                    selectedColor = selectedColor,
                    focusRequester = focusRequester,
                    keyboardController = keyboardController,
                    textFieldValue = textFieldValue,
                    onTextChanged = onTextChanged,
                    onColorSelected = onColorSelected,
                    onSave = onSave,
                    onUpdate = onUpdate
                )
            }
        }
    }
}

/**
 * Categories detailed success content
 *
 * @param selectedCategory
 * @param selectedColor
 * @param textFieldValue
 * @param focusRequester
 * @param keyboardController
 * @param onTextChanged
 * @param onColorSelected
 * @param onSave
 * @param onUpdate
 * @receiver
 * @receiver
 * @receiver
 * @receiver
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CategoriesDetailedSuccessContent(
    selectedCategory: CategoryUiModel? = null,
    selectedColor: Color,
    textFieldValue: TextFieldValue,
    focusRequester: FocusRequester,
    keyboardController: SoftwareKeyboardController?,
    onTextChanged: (TextFieldValue) -> Unit,
    onColorSelected: (Color) -> Unit,
    onSave: (String, Color) -> Unit,
    onUpdate: (CategoryUiModel, String, Color) -> Unit
) {
    // bên trong LaunchedEffect sẽ khởi chạy một coroutine để thực hiện requestFocus
    // Trong trường hợp này, việc đặt key1 = true đảm bảo rằng hiệu ứng chỉ được khởi động một lần khi thành phần được kết hợp.
    //focusRequester: Là một biến lưu trữ FocusRequester, một lớp được sử dụng để yêu cầu focus cho các thành phần Compose.
    LaunchedEffect(key1 = true) {
        focusRequester.requestFocus()
    }
    // tạo column bên ngoài
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .testTag("detailed_category_content")
    ) {

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            )

            OutlinedTextField(
                value = textFieldValue,
                onValueChange = { value -> onTextChanged(value) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .focusRequester(focusRequester),
                textStyle = MaterialTheme.typography.bodyLarge,
                leadingIcon = {
                    Icon(
                        ImageVector.vectorResource(id = R.drawable.ic_hash_tag),
                        contentDescription = "",
                        modifier = Modifier.size(12.dp),
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                },
                trailingIcon = {
                    if (textFieldValue.text.isNotEmpty()) {
                        IconButton(
                            onClick = { onTextChanged(TextFieldValue("")) },

                        ) {
                            Icon(
                                modifier = Modifier
                                    .size(20.dp)
                                    .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f), shape = CircleShape),
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Clear text icon",
                                tint = MaterialTheme.colorScheme.background
                            )
                        }
                    }
                },
                singleLine = true,
                maxLines = 1,
                shape = RoundedCornerShape(8.dp),
                label = { Text(text = stringResource(id = R.string.title_category_name)) },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { keyboardController?.hide() })
            )

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(24.dp)
            )

            GridColorPalette(
                modifier = Modifier
                    .padding(16.dp)
                    .testTag("grid_color_pallete"),
                onColorSelected = onColorSelected,
                originalColor = selectedCategory?.color,
                selectedColor = selectedColor
            )
        }

        val isEnabled = if (selectedCategory != null) {
            // textFieldValue.text != selectedCategory.title == true
            // (selectedColor != Color.Transparent && selectedColor != selectedCategory.color) == true
            (textFieldValue.text != selectedCategory.title || (selectedColor != Color.Transparent && selectedColor != selectedCategory.color))
                    && textFieldValue.text.isNotEmpty()
        } else {
            // textFieldValue.text.isNotEmpty() == true và (selectedColor != Color.Transparent) == true
            textFieldValue.text.isNotEmpty() && selectedColor != Color.Transparent
        }

        Surface(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background),
            shadowElevation = 8.dp
        ) {
            PrimaryButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                text = if (selectedCategory != null) stringResource(R.string.button_update) else stringResource(id = R.string.button_save),
                enabled = isEnabled,
                onClick = {
                    if (selectedCategory != null) {
                        // truyền các parameter như : selectedCategory, textFieldValue.text, selectedColor
                        // và gọi hàm callback và xử lý tại CategoriesViewModel.onUpdateItem
                        onUpdate(selectedCategory, textFieldValue.text, selectedColor)
                    } else {
                        // truyền các parameter như : textFieldValue.text, selectedColor
                        // và gọi hàm callback và xử lý tại CategoriesViewModel.onCreateItem
                        onSave(textFieldValue.text, selectedColor)
                    }
                }
            )
        }
    }
}