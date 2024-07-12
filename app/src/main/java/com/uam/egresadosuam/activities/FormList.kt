package com.uam.egresadosuam.activities

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.uam.egresadosuam.navigation.FormAnswer
import com.uam.egresadosuam.ui.theme.LibreFranklin
import com.uam.egresadosuam.ui.theme.UAMPrimary
import com.uam.egresadosuam.viewmodel.FormListViewModel
import com.uam.egresadosuam.viewmodel.FormListViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormListActivity(navController: NavController, snackbarHostState: SnackbarHostState) {
    val context = LocalContext.current

    val formListViewModel = viewModel(
        modelClass = FormListViewModel::class.java, factory = FormListViewModelFactory(
            context = context, navController = navController, snackbarHostState = snackbarHostState
        )
    )

    val forms by formListViewModel.reqData.collectAsState()
    val isLoading by formListViewModel.isLoading.collectAsState()



    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Encuestas sin responder",
                        fontFamily = LibreFranklin,
                        color = Color.White
                    )
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Filled.Person, contentDescription = "Ir a perfil")
                    }
                },
                colors = TopAppBarColors(
                    containerColor = UAMPrimary,
                    scrolledContainerColor = UAMPrimary,
                    actionIconContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                    titleContentColor = Color.White,
                )
            )
        }
    ) { paddingValues ->

        if (isLoading) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(UAMPrimary),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(color = Color.White)
            }

        } else {
            LazyColumn(
                contentPadding = paddingValues,
                modifier = Modifier
                    .background(UAMPrimary)
                    .fillMaxSize()
            ) {
                itemsIndexed(items = forms) { _, item ->
                    Card(
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate(FormAnswer(item.id))
                            },
                        elevation = CardDefaults.cardElevation()
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = item.name,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface,
                                fontFamily = LibreFranklin,
                                fontSize = 20.sp
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = item.description,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurface,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }
        }
    }
}