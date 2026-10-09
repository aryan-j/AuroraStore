/*
 * SPDX-FileCopyrightText: 2025 The Calyx Institute
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package com.aurora.store.compose.ui.preferences

import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLocale
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewWrapper
import androidx.core.net.toUri
import com.aurora.extensions.isTAndAbove
import com.aurora.store.R
import com.aurora.store.compose.composable.TopAppBar
import com.aurora.store.compose.preview.ThemePreviewProvider
import com.aurora.store.compose.ui.preferences.network.SingleChoiceDialog
import com.aurora.store.util.Preferences
import com.aurora.store.util.Preferences.PREFERENCE_DEFAULT_SELECTED_TAB
import com.aurora.store.util.Preferences.PREFERENCE_FOR_YOU
import com.aurora.store.util.save

@Composable
fun UIPreferenceScreen() {
    ScreenContent()
}

@Composable
private fun ScreenContent() {
    val context = LocalContext.current

    val tabEntries = stringArrayResource(R.array.pref_default_tab)
    var selectedTab by remember {
        mutableIntStateOf(Preferences.getInteger(context, PREFERENCE_DEFAULT_SELECTED_TAB))
    }
    var forYou by remember {
        mutableStateOf(Preferences.getBoolean(context, PREFERENCE_FOR_YOU, true))
    }
    var showTabDialog by remember { mutableStateOf(false) }

    if (showTabDialog) {
        SingleChoiceDialog(
            title = stringResource(R.string.pref_ui_layout_tab),
            options = tabEntries.toList(),
            selected = selectedTab,
            onSelect = { index ->
                selectedTab = index
                context.save(PREFERENCE_DEFAULT_SELECTED_TAB, index)
                showTabDialog = false
            },
            onDismiss = { showTabDialog = false }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = stringResource(R.string.pref_ui_title)
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            if (isTAndAbove) {
                item {
                    PreferenceListItem(
                        modifier = Modifier.clickable {
                            context.startActivity(
                                Intent(Settings.ACTION_APP_LOCALE_SETTINGS).apply {
                                    data = ("package:" + context.packageName).toUri()
                                }
                            )
                        },
                        headlineContent = { Text(stringResource(R.string.app_language)) },
                        supportingContent = { Text(LocalLocale.current.platformLocale.displayName) }
                    )
                }
            }
            item { PreferenceSectionHeader(stringResource(R.string.pref_ui_layout)) }
            item {
                PreferenceListItem(
                    modifier = Modifier.clickable { showTabDialog = true },
                    headlineContent = { Text(stringResource(R.string.pref_ui_layout_tab)) },
                    supportingContent = { Text(tabEntries.getOrElse(selectedTab) { "" }) }
                )
            }
            item { PreferenceSectionHeader(stringResource(R.string.pref_common_extra)) }
            item {
                PreferenceListItem(
                    modifier = Modifier.clickable {
                        forYou = !forYou
                        context.save(PREFERENCE_FOR_YOU, forYou)
                    },
                    headlineContent = { Text(stringResource(R.string.pref_ui_no_for_you)) },
                    supportingContent = { Text(stringResource(R.string.pref_ui_no_for_you_desc)) },
                    trailingContent = {
                        Switch(
                            checked = forYou,
                            onCheckedChange = { checked ->
                                forYou = checked
                                context.save(PREFERENCE_FOR_YOU, checked)
                            }
                        )
                    }
                )
            }
        }
    }
}

@PreviewWrapper(ThemePreviewProvider::class)
@Preview
@Composable
private fun UIPreferenceScreenPreview() {
    ScreenContent()
}
