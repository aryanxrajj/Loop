package com.example.loop.ui.screens

import android.text.Layout
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.loop.data.Topic
import com.example.loop.network.fetchTopicsFromBackend

@Composable
fun TopicsScreen() {
    var topics by remember { mutableStateOf<List<Topic>>(emptyList()) }
    var currentPage by remember { mutableIntStateOf(0) }
    var isLoading by remember { mutableStateOf(false) }
    var endReached by remember { mutableStateOf(false) }

    // Fetch first page initially
    LaunchedEffect(Unit) {
        loadNextPage(currentPage, topics, onLoaded = { newList ->
            topics = newList
        }, onPageLoaded = { currentPage = it }, onEndReached = { endReached = it })
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(topics) { topic ->
            ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = topic.title,
                    modifier = Modifier.padding(16.dp),
                        MaterialTheme.colorScheme.primary
                )
            }
        }

        item {
            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (!endReached) {
                // When user scrolls near bottom, trigger next load
                LaunchedEffect(topics.size) {
                    loadNextPage(currentPage, topics, onLoaded = { newList ->
                        topics = newList
                    }, onPageLoaded = { currentPage = it }, onEndReached = { endReached = it })
                }
            }
        }
    }
}


private fun loadNextPage(
    currentPage: Int,
    currentTopics: List<Topic>,
    onLoaded: (List<Topic>) -> Unit,
    onPageLoaded: (Int) -> Unit,
    onEndReached: (Boolean) -> Unit,
    pageSize: Int = 10
) {
    var loading = true
    fetchTopicsFromBackend(currentPage, pageSize) { newTopics ->
        loading = false
        if (newTopics != null && newTopics.isNotEmpty()) {
            val updatedList = currentTopics + newTopics
            onLoaded(updatedList)
            onPageLoaded(currentPage + 1)
        } else {
            onEndReached(true)
        }
    }
}
