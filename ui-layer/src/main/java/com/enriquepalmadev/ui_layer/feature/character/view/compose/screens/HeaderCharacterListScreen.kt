package com.enriquepalmadev.ui_layer.feature.character.view.compose.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.enriquepalmadev.ui_layer.feature.character.view.compose.dataclass.HeaderCharacterListModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeaderCharacterList(
    dialogOrderBy: () -> Unit,
    onSearchQueryChange: (newText: String) -> Unit
) {
    var text by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }

    Row {
        Box(
            modifier = Modifier
                .width(230.dp)
                .height(80.dp)
                .padding(5.dp, 8.dp, 5.dp, 5.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            SearchBar(
                query = text,
                onQueryChange = {
                    text = it
                    onSearchQueryChange(text)
                },
                onSearch = { active = false },
                active = active,
                onActiveChange = { active = it },
                placeholder = {
                    Text(text = "Search")
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = null)
                },
                trailingIcon = {
                    if (active) {
                        Icon(
                            modifier = Modifier.clickable {
                                if (text.isNotEmpty()) {
                                    text = ""
                                } else {
                                    active = false
                                    onSearchQueryChange(text)
                                }
                            },
                            imageVector = Icons.Default.Clear,
                            contentDescription = null
                        )
                    }
                }
            ) { }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .absolutePadding(5.dp, 12.dp, 5.dp, 5.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            Button(
                modifier = Modifier.height(40.dp),
                onClick = { dialogOrderBy() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red
                )
            ) {
                Text(
                    text = "Order By",
                    color = Color.White
                )
            }
        }
    }
}

