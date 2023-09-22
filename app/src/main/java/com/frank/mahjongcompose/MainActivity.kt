package com.frank.mahjongcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import com.frank.mahjongcompose.ui.ShowTile
import com.frank.mahjongcompose.ui.isToDraw
import com.frank.mahjongcompose.ui.myFontSize
import com.frank.mahjongcompose.ui.theme.MahjongComposeTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlin.system.exitProcess

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 沉浸式 状态栏 条件之一
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            MahjongComposeTheme {
                TransparentSystemBars()         // 沉浸式 状态栏 条件之二
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .padding(bottom = 2.dp)
                ) {
                    ShowTile()
                    Row(
                        Modifier
                            .weight(1.0f)
                            .background(Color.White)) {
                        if (isToDraw < 100) ShowList()
                    }
                    Row(
                        modifier = Modifier
                            .background(Color.LightGray)
                            .padding(5.dp)
                    ) {

                        Text(
                            text = "文字大小",
                            color = Color.Black,
                            fontSize = 23.sp,
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .padding(5.dp)
                        )
                        Slider(
                            value = myFontSize,
                            onValueChange = { myFontSize = it },
                            valueRange = 20f..50f,
                            modifier = Modifier.padding(end = 10.dp)
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun TransparentSystemBars() {
        val systemUiController = rememberSystemUiController()
        val useDarkIcons = !isSystemInDarkTheme()
        SideEffect {
            systemUiController.setSystemBarsColor(
                color = Color.Transparent,
                darkIcons = useDarkIcons,
                isNavigationBarContrastEnforced = false,
            )
        }
    }

    override fun onBackPressed() {
        exitProcess(0)
    }
}
