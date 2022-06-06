package com.example.animationlazycolum

import androidx.compose.animation.*
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp

@ExperimentalAnimationApi
@Preview
@ExperimentalMaterialApi
@Composable
fun ContentAnimation() {
    val lazyListState = rememberLazyListState()
    val allTopics = stringArrayResource(id = R.array.topics).toList()
    var expandedTopic by remember { mutableStateOf<String?>(null) }
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.wrapContentHeight(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 32.dp),
        state = lazyListState
    ) {
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            Text(
                text = "Topics",
                modifier = Modifier.semantics { heading() },
                style = MaterialTheme.typography.h5
            )
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
        items(allTopics) { topic ->
            TopicRow(
                topic = topic,
                expanded = expandedTopic == topic,
                onClick = {
                    expandedTopic = if (expandedTopic == topic) null else topic
                }
            )
        }
    }
}


@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun TopicRow(
    topic: String,
    expanded: Boolean,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        elevation = 5.dp,
        shape = RoundedCornerShape(10.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .animateContentSize()
        ) {
            Row {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null
                )

                Spacer(modifier = Modifier.width(16.dp))
                Text(text = topic, style = MaterialTheme.typography.body1)
            }

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
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = stringResource(id = R.string.lorem_ipsum),
                        textAlign = TextAlign.Justify
                    )
                }
            }
        }
    }

}