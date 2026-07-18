package com.example.talkiesocial.core.ui.base

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.talkiesocial.core.common.base.BaseViewModel
import com.example.talkiesocial.core.common.base.CommonEffect
import com.example.talkiesocial.core.ui.components.TalkieScaffold
import kotlinx.coroutines.launch

@Composable
fun <S, E> BaseScreen(
    viewModel: BaseViewModel<S, E>,
    onEffect: (E) -> Unit = {},
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    content: @Composable (S, PaddingValues) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collect { effect ->
            if (effect is CommonEffect.ShowSnackbar) {
                launch {
                    snackbarHostState.showSnackbar(effect.message)
                }
            }
            onEffect(effect)
        }
    }

    TalkieScaffold(
        isLoading = isLoading,
        snackbarHostState = snackbarHostState,
        topBar = topBar,
        bottomBar = bottomBar
    ) { paddingValues ->
        content(uiState, paddingValues)
    }
}
