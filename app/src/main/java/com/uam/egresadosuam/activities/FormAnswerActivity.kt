package com.uam.egresadosuam.activities

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.uam.egresadosuam.R
import com.uam.egresadosuam.model.QuestionType
import com.uam.egresadosuam.ui.theme.LibreFranklin
import com.uam.egresadosuam.ui.theme.UAMPrimary
import com.uam.egresadosuam.ui.theme.UAMPrimaryForeground
import com.uam.egresadosuam.viewmodel.FormAnswerViewModel
import com.uam.egresadosuam.viewmodel.FormAnswerViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormAnswerActivity(
    navController: NavController,
    formId: String,
    snackbarHostState: SnackbarHostState
) {
    val context = LocalContext.current

    val formAnswerViewModel = viewModel(
        modelClass = FormAnswerViewModel::class.java,
        factory = FormAnswerViewModelFactory(context, formId, navController, snackbarHostState)
    )

    val form = formAnswerViewModel.form.collectAsState()
    val responses: MutableState<Map<Int, String>> = remember { mutableStateOf(mapOf()) }

    //add state code

    Scaffold(
        contentWindowInsets = WindowInsets.navigationBars,
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = form.value.name,
                        fontFamily = LibreFranklin,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Regresar")
                },
                colors = TopAppBarColors(
                    containerColor = UAMPrimary,
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                    scrolledContainerColor = UAMPrimary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    formAnswerViewModel.onFormAnswer(answers = responses.value)
                },
                containerColor = UAMPrimaryForeground,
                contentColor = Color.White
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.Send,
                    contentDescription = stringResource(id = R.string.addQuestion)
                )
            }
        }
    ) { innerPadding ->
        LazyColumn(
            contentPadding = innerPadding,
            modifier = Modifier
                .fillMaxSize()
                .background(UAMPrimary),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            itemsIndexed(items = form.value.questions) { index, item ->

                when (item.type) {
                    QuestionType.TEXT -> Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                    ) {
                        Text(
                            text = item.question,
                            style = MaterialTheme.typography.headlineSmall,
                            fontFamily = LibreFranklin,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        TextField(
                            value = responses.value[index] ?: "",
                            onValueChange = { newValue ->
                                responses.value = responses.value.toMutableMap().apply {
                                    this[index] = newValue
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .border(1.dp, UAMPrimary)
                                .padding(8.dp)
                        )
                    }

                    QuestionType.MULTIPLE_CHOICE -> Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                    ) {
                        Text(
                            text = item.question,
                            style = MaterialTheme.typography.headlineSmall,
                            fontFamily = LibreFranklin,
                            color = Color.White
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        item.possibleAnswers.forEach { answer ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 5.dp)
                                    .clickable {
                                        responses.value = responses.value
                                            .toMutableMap()
                                            .apply {
                                                this[index] = answer
                                            }
                                    },
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start
                            ) {
                                RadioButton(
                                    selected = responses.value[index] == answer,
                                    onClick = {
                                        responses.value = responses.value.toMutableMap().apply {
                                            this[index] = answer
                                        }
                                    }
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = answer,
                                    fontFamily = LibreFranklin,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }

        }
    }
}