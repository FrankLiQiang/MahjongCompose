package com.frank.mahjongcompose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.frank.mahjongcompose.ui.isToDraw
import com.frank.mahjongcompose.ui.myFontSize

data class Information(
    var item: String = "",
    var password: String = "",
    var isItemChosen: Boolean = false,
)

var mutableData = mutableListOf(Information())
var isItem by mutableStateOf(true)

var lastClickItem: Information? = null

@Composable
fun ShowList() {
    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.background(Color.White)) {
        LazyColumn(
            state = scrollState,
            modifier = Modifier
                .weight(1f)
                .background(Color.White)
        ) {

            if (isToDraw < -1) return@LazyColumn

            items(mutableData) { menuItem ->

                NavigationDrawerItem(
                    modifier = Modifier.height(60.dp),
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = Color.White,
                        unselectedContainerColor = Color.White,
                    ),
                    shape = MaterialTheme.shapes.small,
                    selected = true,
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.baseline_brightness_1_24),
                            contentDescription = stringResource(id = R.string.app_name),
                            tint = if (menuItem.isItemChosen) Color.Red else Color.DarkGray,
                        )
                    },
                    label = {
                        Row {
                            Text(
                                text = menuItem.item,
                                fontSize = myFontSize.sp,
                                color = Color.Black,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                style = MaterialTheme.typography.labelMedium,
                            )
                        }
                    },
                    onClick = {
                        if (lastClickItem != null && menuItem != lastClickItem) {
                            lastClickItem!!.isItemChosen = false
                        }
                        menuItem.isItemChosen = !menuItem.isItemChosen
                        if (menuItem.isItemChosen) {
                            lastClickItem = menuItem
                        }
                        isItem = true
                        isToDraw = 1 - isToDraw
                    },
                )
                if (menuItem.isItemChosen) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.LightGray)
                    )
                    {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    brush = Brush.verticalGradient(
                                        colors = listOf(
                                            Color.DarkGray,
                                            Color.LightGray,
                                        ),
                                        startY = 0f,
                                        endY = 30f,
                                        tileMode = TileMode.Clamp
                                    )
                                )
                                .padding(5.dp)
                                .padding(start = 50.dp)
                                .clickable {
                                    menuItem.isItemChosen = false
                                    isItem = false
                                    isToDraw = 1 - isToDraw
                                },
                            text = menuItem.password,
                            fontSize = myFontSize.sp,
                            color = Color.Black,
                            lineHeight = (myFontSize + 2).sp,
                            maxLines = 10,
                            style = MaterialTheme.typography.labelMedium,
                        )
                    }
                }
                Divider(color = Color.LightGray)
            }
        }
    }
}
