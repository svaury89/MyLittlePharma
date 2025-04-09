package com.example.ui.composable

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.domain.extension.toSpecificDateFormat
import com.example.domain.extension.toStringWithFormat
import com.example.ui.R
import com.example.ui.model.ProductUi
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LimitDate(
    onUpdateDate : (String) -> Unit,
    date: String,
    @StringRes title : Int
){
    val focusManager = LocalFocusManager.current
    var showDatePickerDialog by remember {
        mutableStateOf(false)
    }
    val datePickerState = rememberDatePickerState()

    if (showDatePickerDialog) {
        DatePickerDialog(
            onDismissRequest = { showDatePickerDialog = false },
            confirmButton = {
                Button(
                    onClick = {
                        datePickerState
                            .selectedDateMillis?.let { millis ->
                                onUpdateDate(millis.toSpecificDateFormat())
                            }
                        showDatePickerDialog = false
                    }) {
                    Text(text = stringResource(id = R.string.peremption_date_picker))
                }
            }) {
            DatePicker(state = datePickerState)
        }
    }

    MLPTextField(
        title = title,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .onFocusEvent {
                if (it.isFocused) {
                    showDatePickerDialog = true
                    focusManager.clearFocus(force = true)
                }
            },
        textFieldValue = date,
        isReadOnly = true

    )
}