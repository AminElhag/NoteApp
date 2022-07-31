package com.example.noteapp.presenter.note.crupdate.components

import androidx.activity.compose.BackHandler
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.noteapp.R
import com.example.noteapp.domain.note.model.Note
import com.example.noteapp.presenter.note.crupdate.CrupdateNoteEvent
import com.example.noteapp.presenter.note.crupdate.CrupdateViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun CrupdateNoteScreen(
    navController: NavController,
    noteColor: Int,
    viewModel: CrupdateViewModel = hiltViewModel()
) {
    val titleState = viewModel.noteTitle.value
    val contentState = viewModel.noteContent.value

    val scaffoldState = rememberScaffoldState()

    val backgroundAnimation = remember {
        Animatable(
            Color(if (noteColor != -1) noteColor else viewModel.noteColor.value)
        )
    }

    val scope = rememberCoroutineScope()

    val openDialog = remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { value: CrupdateViewModel.UiEvent ->
            when (value) {
                CrupdateViewModel.UiEvent.SaveNoteEvent -> {
                    navController.navigateUp()
                }
                is CrupdateViewModel.UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = value.message,
                    )
                }
            }
        }
    }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onEvent(CrupdateNoteEvent.SaveNote)
            }, backgroundColor = MaterialTheme.colors.primary) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_diskette),
                    contentDescription = "Save the note",
                    tint = Color.Black
                )
            }
        },
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundAnimation.value)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Note.noteColor.forEach { color: Color ->
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                            .shadow(16.dp)
                            .background(color)
                            .border(
                                width = 3.dp,
                                color = if (viewModel.noteColor.value == color.toArgb()) {
                                    Color.Black
                                } else {
                                    Color.Transparent
                                },
                                shape = CircleShape
                            )
                            .clickable {
                                scope.launch {
                                    backgroundAnimation.animateTo(
                                        targetValue = color,
                                        animationSpec = tween(
                                            500
                                        )
                                    )
                                }
                                viewModel.onEvent(CrupdateNoteEvent.ChangeColor(color.toArgb()))
                            }
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            TransparentHintTextFiled(
                text = titleState.text,
                hint = titleState.hint,
                onValueChange = {
                    viewModel.onEvent(CrupdateNoteEvent.EnteredTitle(it))
                },
                onFocusChange = {
                    viewModel.onEvent(CrupdateNoteEvent.ChangeTitleFocus(it))
                },
                isHintVisible = titleState.isHintVisible,
                singleLine = true,
                textStyle = MaterialTheme.typography.h4
            )
            Spacer(modifier = Modifier.height(16.dp))
            TransparentHintTextFiled(
                text = contentState.text,
                hint = contentState.hint,
                onValueChange = {
                    viewModel.onEvent(CrupdateNoteEvent.EnteredContent(it))
                },
                onFocusChange = {
                    viewModel.onEvent(CrupdateNoteEvent.ChangeContentFocus(it))
                },
                isHintVisible = contentState.isHintVisible,
                textStyle = MaterialTheme.typography.body1,
                modifier = Modifier.fillMaxHeight()
            )
        }
    }

    BackHandler {
        openDialog.value = true
    }

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                // Dismiss the dialog when the user clicks outside the dialog or on the back
                // button. If you want to disable that functionality, simply use an empty
                // onCloseRequest.
                openDialog.value = false
            },
            title = {
                Text(text = "Exit")
            },
            text = {
                Text("Your changes have not been saved")
            },
            confirmButton = {
                Button(
                    onClick = {
                        openDialog.value = false
                        viewModel.onEvent(CrupdateNoteEvent.SaveNote)
                    }) {
                    Text("Save")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        openDialog.value = false
                        navController.navigateUp()
                    }) {
                    Text("Ignore ")
                }
            }
        )
    }
}

