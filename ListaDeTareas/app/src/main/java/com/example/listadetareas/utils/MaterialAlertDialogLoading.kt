package com.example.listadetareas.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.example.listadetareas.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import androidx.appcompat.app.AlertDialog

fun Context.alertLoading(
): AlertDialog {
    val alertDialogBuilderLoading = MaterialAlertDialogBuilder(this)
    val customLayoutLoading: View = LayoutInflater.from(this@alertLoading).inflate(R.layout.dialog_loading, null)
    alertDialogBuilderLoading.setCancelable(false)
    alertDialogBuilderLoading.setView(customLayoutLoading)
    return alertDialogBuilderLoading.create()
}