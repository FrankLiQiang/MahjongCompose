package com.frank.mahjongcompose.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Slider
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.frank.mahjongcompose.GetPaiData
import com.frank.mahjongcompose.R
import com.frank.mahjongcompose.getPaiList
import com.frank.mahjongcompose.huPaiCount

var isToDraw by mutableStateOf(0)
var isPaiType by mutableStateOf(false)
var myTitle by mutableStateOf("麻将胡牌计算器")
var searchString by mutableStateOf("")
var myFontSize by mutableStateOf(23f)
var isDoing by mutableStateOf(false)
var kouTing by mutableStateOf(3)
var paiNum by mutableStateOf(7)
var isSpecify by mutableStateOf(false)

@Composable
fun ShowTile() {
    if (isToDraw > 100) return
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .height(255.dp)
            .background(colorResource(id = R.color.title))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.title))
                .padding(bottom = 2.dp)
        ) {
            Row(
                modifier = Modifier
                    .background(colorResource(id = R.color.title))
                    .height(45.dp)
            ) {}
            Row {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 5.dp),
                    text = myTitle,
                    color = Color.White,
                    fontSize = 22.sp,
                    textAlign = TextAlign.Center,
                )
            }
            if (isToDraw < 100) {
                if (isDoing) {
                    LinearProgressIndicator(
                        color = colorResource(id = R.color.title),
                        trackColor = Color.LightGray,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 5.dp)
                    )
                }
            }
            Row(
                modifier = Modifier
                    .height(60.dp)
                    .background(colorResource(R.color.title))
                    .padding(5.dp)
            ) {
                Checkbox(
                    checked = isPaiType,
                    onCheckedChange = { isPaiType = it },
                    enabled = true,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Text(
                    text = "牌型",
                    color = Color.White,
                    fontSize = 23.sp,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(end = 5.dp)
                )
                TextField(
                    modifier = Modifier.weight(1.0f),
                    value = searchString,
                    textStyle = TextStyle(
                        color = Color.Black,
                        textIndent = TextIndent(12.sp),
                        background = Color.White,
                        fontSize = 23.sp,
                    ),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                    ),
                    maxLines = 1,
                    onValueChange = { searchString = it },
                )
                Image(
                    modifier = Modifier
                        .background(colorResource(id = R.color.title))
                        .padding(15.dp)
                        .clickable{ getPaiList() },
                    painter = painterResource(id = R.drawable.search),
                    contentDescription = stringResource(id = R.string.app_name),
                )
            }
            Row(
                modifier = Modifier
                    .background(Color.LightGray)
                    .padding(5.dp)
            ) {

                Text(
                    text = "($kouTing)口听",
                    color = Color.Black,
                    fontSize = 23.sp,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(5.dp)
                )
                Slider(
                    value = kouTing.toFloat(),
                    onValueChange = { kouTing = it.toInt() },
                    valueRange = 3f..9f,
                    modifier = Modifier.padding(end = 10.dp)
                )
            }
            Row(
                modifier = Modifier
                    .background(Color.LightGray)
                    .padding(5.dp)
            ) {

                Text(
                    text = "($paiNum)张牌",
                    color = Color.Black,
                    fontSize = 23.sp,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(5.dp)
                )
                Slider(
                    value = paiNum.toFloat(),
                    onValueChange = { paiNum = it.toInt() },
                    valueRange = 4f..13f,
                    modifier = Modifier.padding(end = 10.dp)
                )
            }
        }
    }
}
