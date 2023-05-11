package com.example.windowinsetsdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.example.windowinsetsdemo.collapsable.CollapsableLayout

import com.example.windowinsetsdemo.collapsable.rememberCollapsableLayoutState

import com.example.windowinsetsdemo.ui.theme.WindowInsetsDemoTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private const val TAG = "MainActivity"
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)


        setContent {
            WindowInsetsDemoTheme {
                TransparentStatusBar()
                Test()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Magenta)
            .systemBarsPadding() //同时添加状态栏和导航栏高度对应的上下 padding
//            .statusBarsPadding() //只添加状态栏
//            .navigationBarsPadding()//只添加导航啦
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(22.dp)
        ) {
            items(20) {
                Text(text = "第 $it 项")
            }
        }
    }
}


@Composable
fun TransparentStatusBar() {
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = !isSystemInDarkTheme()
    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color.Transparent,
            darkIcons = useDarkIcons,
        )
    }
}


@Composable
fun Test() {

    val listState = rememberLazyListState()
    val bottomContentScrolled: State<Boolean> = remember {
        derivedStateOf {
            !(listState.firstVisibleItemIndex == 0 && listState.firstVisibleItemScrollOffset == 0)
        }
    }
    val collapsableLayoutState = rememberCollapsableLayoutState()

    CollapsableLayout(

        topContent = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .alpha(collapsableLayoutState.expendProgress)
            ){
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = R.drawable.c19e2e81da3ede74c24c29bf6b1a800b),
                    contentScale = ContentScale.FillBounds,
                    contentDescription =""
                )
            }
        },

        bottomContent = {

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .navigationBarsPadding(),
                state = listState,
                verticalArrangement = Arrangement.spacedBy(22.dp)
            ) {
                items(30) {
                    Text(text = "第 $it 项")
                }
            }
        },
        bottomContentScrolled = bottomContentScrolled,
        state = collapsableLayoutState
    )
}
