package com.trungkieu.pointofinterest.features.profile.ui

import android.app.Activity
import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.SubcomposeAsyncImage
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.trungkieu.pointofinterest.R
import com.trungkieu.pointofinterest.features.profile.models.ProfileScreenUiState
import com.trungkieu.pointofinterest.features.profile.models.ProfileSectionItem
import com.trungkieu.pointofinterest.features.profile.models.ProfileSectionType
import com.trungkieu.pointofinterest.features.profile.models.UserInfo
import com.trungkieu.pointofinterest.features.profile.vm.ProfileViewModel
import com.trungkieu.pointofinterest.navigation.Screen
import com.trungkieu.pointofinterest.ui.composables.uikit.PulsingProgressBar
import com.trungkieu.pointofinterest.ui.theme.PointOfInterestTheme

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun ProfileScreen(
    vm: ProfileViewModel = hiltViewModel(),
    onNavigate: (Screen) -> Unit
) {
    val context = LocalContext.current

    val startForResult =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                if (result.data != null) {
                    vm.onUserSignedIn(GoogleSignIn.getSignedInAccountFromIntent(intent))
                }
            }
        }

    val profileSectionsState by vm.profileState.collectAsStateWithLifecycle()

    val onNavigateInternal: (ProfileSectionType) -> Unit = { type ->
        if (type == ProfileSectionType.CATEGORIES) {
            onNavigate(Screen.Categories)
        }
        if (type == ProfileSectionType.ABOUT) {
            onNavigate(Screen.About)
        }
    }

    val onSignInClicked: () -> Unit = {
        startForResult.launch(getGoogleLoginAuth(context).signInIntent)
    }

    val onSignOutClicked: () -> Unit = {
        vm.onSignOutClicked()
        getGoogleLoginAuth(context).signOut()
    }

    if (profileSectionsState is ProfileScreenUiState.Result) {
        ProfileScreenContent(
            profileSections = (profileSectionsState as ProfileScreenUiState.Result).sections,
            onSignInClicked = onSignInClicked,
            onSignOutClicked = onSignOutClicked,
            onNavigateInternal = onNavigateInternal,
            onSettingsToggled = vm::onSettingsToggled
        )
    }
}

@Composable
fun ProfileScreenContent(
    profileSections: List<ProfileSectionItem>,
    onSignInClicked: () -> Unit,
    onSignOutClicked: () -> Unit,
    onNavigateInternal: (ProfileSectionType) -> Unit,
    onSettingsToggled: (ProfileSectionType, Boolean) -> Unit
) {
    LazyColumn(
        modifier = Modifier.testTag("profile_section_content_lazy_list")
    ) {
        items(profileSections, key = { item -> item.type }) { item ->
            if (item is ProfileSectionItem.AccountSectionItem) {
                AccountSection(userInfo = item.userInfo, onSignInClicked, onSignOutClicked)
            }
            if (item is ProfileSectionItem.NavigationItem) {
                NavigationSection(item = item, onNavigationClicked = onNavigateInternal)
            }
            if (item is ProfileSectionItem.BooleanSettingsItem) {
                BooleanSettingsSection(item = item, onToggleBooleanSettings = onSettingsToggled)
            }
            Divider(
                Modifier
                    .height(0.5.dp)
                    .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f))
            )
        }
    }
}

@Composable
fun BooleanSettingsSection(
    item: ProfileSectionItem.BooleanSettingsItem,
    onToggleBooleanSettings: (ProfileSectionType, Boolean) -> Unit
) {

    Row(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background, shape = RectangleShape)
            .clickable(item.isEnabled) { onToggleBooleanSettings(item.type, item.state) }
            .testTag("boolean_settings_section:${item.type.name}"),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BaseSettingContent(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp),
            icon = item.icon,
            title = item.title,
            subtitle = item.subtitle,
            isEnabled = item.isEnabled
        )

        Switch(
            modifier = Modifier
                .padding(end = 16.dp),
            checked = item.state,
            onCheckedChange = null,
            enabled = item.isEnabled,
            colors = SwitchDefaults.colors(
                checkedTrackColor = MaterialTheme.colorScheme.secondary,
                uncheckedTrackColor = MaterialTheme.colorScheme.background,
                uncheckedBorderColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                uncheckedThumbColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
            )
        )
    }
}

@Composable
fun NavigationSection(
    item: ProfileSectionItem.NavigationItem,
    onNavigationClicked: (ProfileSectionType) -> Unit
) {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background, shape = RectangleShape)
            .clickable(item.isEnabled) { onNavigationClicked(item.type) }
            .testTag("navigation_section:${item.type.name}")
    ) {
        BaseSettingContent(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
            icon = item.icon,
            title = item.title,
            subtitle = item.subtitle,
            isEnabled = item.isEnabled
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AccountSection(
    userInfo: UserInfo?,
    onSignInClicked: () -> Unit,
    onSignOutClicked: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(128.dp)
            .padding(start = 16.dp, end = 16.dp)
            .testTag("account_section"),
        contentAlignment = Alignment.Center
    ) {

        AnimatedContent(
            targetState = userInfo == null
        ) { targetState ->
            if (targetState) {
                ElevatedButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 48.dp, vertical = 16.dp)
                        .testTag("account_sign_in_container"),
                    shape = RoundedCornerShape(6.dp),
                    elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 2.dp),
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = MaterialTheme.colorScheme.background,
                        contentColor = MaterialTheme.colorScheme.onBackground,
                        disabledContainerColor = MaterialTheme.colorScheme.background.copy(alpha = 0.5f),
                        disabledContentColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                    ),
                    onClick = { onSignInClicked() }
                ) {
                    Image(
                        modifier = Modifier.size(20.dp),
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_google_icon),
                        contentDescription = "Google sign icon"
                    )
                    Text(
                        modifier = Modifier.padding(6.dp),
                        text = stringResource(id = R.string.button_signin_with_google),
                        style = MaterialTheme.typography.titleSmall,
                        fontSize = 16.sp
                    )
                }
            } else if (userInfo != null) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("account_user_information"),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SubcomposeAsyncImage(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape),
                        model = userInfo.avatarUrl,
                        contentDescription = "User image",
                        loading = { PulsingProgressBar() },
                        error = {
                            Icon(
                                modifier = Modifier.size(80.dp),
                                imageVector = ImageVector.vectorResource(id = R.drawable.ic_account_placeholder),
                                contentDescription = "User image placeholder",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    )
                    Spacer(modifier = Modifier.size(24.dp))

                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.Start,
                    ) {
                        Text(
                            text = userInfo.fullName,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = userInfo.email,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }

                    Spacer(modifier = Modifier.size(24.dp))

                    IconButton(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .testTag("account_sign_out_button"),
                        onClick = onSignOutClicked
                    ) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            imageVector = ImageVector.vectorResource(R.drawable.ic_exit),
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BaseSettingContent(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    @StringRes title: Int,
    @StringRes subtitle: Int?,
    isEnabled: Boolean
) {
    val contentColor = MaterialTheme.colorScheme.onBackground.copy(alpha = if (isEnabled) 1f else 0.2f)

    Row(
        modifier = modifier
            .height(80.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            imageVector = ImageVector.vectorResource(id = icon),
            contentDescription = "",
            tint = contentColor
        )
        Spacer(modifier = Modifier.size(16.dp))

        Column(
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = stringResource(id = title),
                style = MaterialTheme.typography.titleSmall,
                color = contentColor
            )

            if (subtitle != null) {
                Text(
                    text = stringResource(id = subtitle),
                    style = MaterialTheme.typography.bodySmall,
                    color = contentColor.copy(alpha = if (isEnabled) 0.5f else 0.1f)
                )
            }
        }
    }
}

@Preview
@Composable
fun ProfileScreenPreview() {
    PointOfInterestTheme {
        Column(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
            BaseSettingContent(
                icon = R.drawable.ic_system_settings,
                title = R.string.profile_section_title_system_theme,
                subtitle = R.string.profile_section_subtitle_system_theme,
                isEnabled = false
            )

            Spacer(modifier = Modifier.size(16.dp))

            NavigationSection(
                item = ProfileSectionItem.NavigationItem(
                    icon = R.drawable.ic_category,
                    title = R.string.profile_section_title_categories,
                    subtitle = null,
                    isEnabled = true,
                    sectionType = ProfileSectionType.CATEGORIES
                ),
                onNavigationClicked = {}
            )

            Divider()

            BooleanSettingsSection(
                item = ProfileSectionItem.BooleanSettingsItem(
                    icon = R.drawable.ic_dark_mode,
                    title = R.string.profile_section_title_dark_theme,
                    isEnabled = true,
                    state = false,
                    sectionType = ProfileSectionType.DARK_THEME
                ),
                onToggleBooleanSettings = { _, _ -> }
            )
        }
    }
}

private fun getGoogleLoginAuth(context: Context): GoogleSignInClient {
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()
        .requestIdToken(context.getString(R.string.gcp_id))
        .requestId()
        .requestProfile()
        .build()
    return GoogleSignIn.getClient(context, gso)
}
