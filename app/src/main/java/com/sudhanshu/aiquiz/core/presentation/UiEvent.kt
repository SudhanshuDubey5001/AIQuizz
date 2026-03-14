package com.sudhanshu.aiquiz.core.presentation

sealed class UiEvent {
    data class showSnackBar(val msg: String): UiEvent()

    data class navigate(val screen: String): UiEvent()
}