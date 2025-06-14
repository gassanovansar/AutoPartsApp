package com.ansar.autoPartsApp.uikit.screens

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime


fun Long.toDate(): Triple<Int, Int, Int> {
    val date = Instant.fromEpochMilliseconds(this).toLocalDateTime(TimeZone.currentSystemDefault())
    return Triple(date.dayOfMonth, date.monthNumber, date.year)
}

fun Int.toZero(): String {
    return if (this >= 10) this.toString() else "0$this"
}

fun Long.toTime(): Pair<Int, Int> {
    val date = Instant.fromEpochMilliseconds(this).toLocalDateTime(TimeZone.currentSystemDefault())
    return Pair(date.hour, date.minute)
}

//class DatePickerScreen(
//    private val current: Long? = null,
//    private val type: Int? = null
//) : Screen {
//
//    companion object {
//        var resetOnClick: (() -> Unit)? = null
//        var onDateSelected: (Long, Int) -> Unit = { _, _ -> }
//    }
//
//    @OptIn(ExperimentalMaterial3Api::class)
//    @Composable
//    override fun Content() {
//        val bottomSheetNavigator = LocalBottomSheetNavigator.current
//        val _current = current ?: Clock.System.now().toEpochMilliseconds()
//        val datePickerState =
//            rememberDatePickerState(initialSelectedDateMillis = _current)
//        Column(Modifier.background(Color.White)) {
//
//            DatePicker(
//                state = datePickerState,
//                modifier = Modifier.padding(16.dp),
//                title = {
//                    Row {
//                        Text(
//                            text = "Готово",
//                            modifier = Modifier.padding(top = 16.dp)
//                                .clickableRound(8.dp) {
//                                    onDateSelected(
//                                        datePickerState.selectedDateMillis ?: Clock.System.now()
//                                            .toEpochMilliseconds(),
//                                        type ?: 0
//                                    )
//                                    bottomSheetNavigator.hide()
//                                }.padding(horizontal = 8.dp),
//                            style = AppTheme.typography.regular.copy(
//                                fontSize = 16.sp,
//                                color = AppTheme.colors.greyBlack,
//                                textAlign = TextAlign.Center,
//                            )
//                        )
//                        Box(modifier = Modifier.weight(1f))
//                        if (resetOnClick != null) {
//                            Text(
//                                text = "Сбросить",
//                                modifier = Modifier.padding(top = 16.dp).clickableRound(8.dp) {
//                                    resetOnClick?.invoke()
//                                    bottomSheetNavigator.hide()
//                                }.padding(horizontal = 8.dp),
//                                style = AppTheme.typography.regular.copy(
//                                    fontSize = 16.sp,
//                                    color = AppTheme.colors.red1,
//                                    textAlign = TextAlign.Center,
//                                )
//                            )
//                        }
//
//                    }
//
//                },
//                colors = DatePickerDefaults.colors(
//                    containerColor = Color.White,
//                    selectedDayContainerColor = AppTheme.colors.color2,
//                    selectedDayContentColor = AppTheme.colors.greyWhite,
//                    todayDateBorderColor = AppTheme.colors.color2,
//                    todayContentColor = AppTheme.colors.greyBlack,
//                    selectedYearContentColor = AppTheme.colors.greyWhite,
//                    selectedYearContainerColor = AppTheme.colors.color2,
//                    currentYearContentColor = AppTheme.colors.greyBlack
//                )
//            )
//        }
//
//    }
//}