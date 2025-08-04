package com.example.truecallerassignment.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.truecallerassignment.R
import com.example.truecallerassignment.presentation.viewmodel.MainViewModel

// composable view

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: MainViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Truecaller Web Analyzer") },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                )
            )
        }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(paddingValues)
                .verticalScroll(scrollState)
        ) {
            if (uiState.content.isNullOrBlank() && uiState.errorMessage == null) {
                Button(
                    onClick = {
                        viewModel.fetchContentUsecase(15)
                    }, modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Load Content")
                }
        }
            Spacer(modifier = Modifier.height(16.dp))
            if (uiState.isLoading) {
                CircularProgressIndicator()
                Spacer(modifier = Modifier.height(16.dp))
            }
            uiState.errorMessage?.let { text ->
                ErrorView(text,viewModel)
            }
            if (!uiState.content.isNullOrBlank()) {
                ResultCard(title = "Content of website", content = uiState.content ?: "")
            }
            if (!uiState.nthChar.isNullOrBlank()) {
                ResultCard(title = "15th Character", content = uiState.nthChar ?: "")
            }

            if (uiState.allNthChar.isNotEmpty()) {
                ResultCard(
                    title = "Every 15th Character", content = uiState.allNthChar
                )
            }
            if (uiState.wordCounts.isNotEmpty()) {
                ResultCard(
                    title = "Word count", content = uiState.wordCounts
                )

            }
        }
    }
}


@Composable
fun ResultCard(title: String, content: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
            ExpandableText(
                text = content,
                style = MaterialTheme.typography.bodyLarge
            )

        }
    }
}


@Composable
fun ErrorView(text: String, viewModel: MainViewModel){
    Column (
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Image(
            painter = painterResource(id = R.drawable.error),
            contentDescription = "Network Error",

            modifier = Modifier
                .size(70.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                viewModel.fetchContentUsecase(15)
            }, modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Retry")
        }
    }
}

