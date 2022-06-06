@file:OptIn(ExperimentalAnimationApi::class)

package com.example.animationlazycolum

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Info
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.example.animationlazycolum.ui.theme.AnimationLazyColumTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnimationLazyColumTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ColumnAnimation()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ColumnAnimation() {
    Surface(color = MaterialTheme.colors.background) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
        ) {
            TopAppBar(
                title = {
                    Text(
                        text = "Column Animation",
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(imageVector = Icons.Filled.Menu, contentDescription = "Menu")
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            ContentWithIconAnimation()

            ContentAnimation()

        }
    }
}

@Preview(showBackground = true)
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun ContentWithIconAnimation() {
    var expanded by remember { mutableStateOf(false) }

    Surface(
        onClick = { expanded = !expanded },
        border = BorderStroke(1.dp, MaterialTheme.colors.primaryVariant),
        shape = RoundedCornerShape(10.dp),
        elevation = 8.dp,
        modifier = Modifier.padding(10.dp)
    ) {
        AnimatedContent(targetState = expanded, transitionSpec = {
            fadeIn(
                animationSpec = tween(
                    150, 150
                )
            ) with fadeOut(animationSpec = tween(150)) using SizeTransform { initialSize, targetSize ->
                if (targetState) {
                    keyframes {
                        IntSize(targetSize.width, initialSize.height) at 150
                        durationMillis = 300
                    }
                } else {
                    keyframes {
                        IntSize(initialSize.width, targetSize.height) at 150
                        durationMillis = 300
                    }
                }
            }
        }) { targetExpanded ->
            if (targetExpanded) {
                Text(
                    text = stringResource(id = R.string.lorem_ipsum),
                    modifier = Modifier.padding(15.dp)
                )
            } else {
                Icon(
                    imageVector = Icons.Outlined.Info,
                    contentDescription = "Info",
                    modifier = Modifier.size(50.dp)
                )
            }
        }
    }
}


