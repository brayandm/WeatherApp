package com.example.finalproject.animation

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun LoadingAnimation(darkMode: MutableState<Boolean>) {
    val circles = listOf(
        remember { Animatable(initialValue = 0f) },
        remember { Animatable(initialValue = 0f) },
        remember { Animatable(initialValue = 0f) },
    )

    circles.forEachIndexed { index, animate ->
        LaunchedEffect(key1 = animate) {
            delay(index * 100L)
            animate.animateTo(
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = keyframes {
                        durationMillis = 1200
                        0.0f at 0 with LinearOutSlowInEasing
                        1.0f at 300 with LinearOutSlowInEasing
                        0.0f at 600 with LinearOutSlowInEasing
                        0.0f at 1200 with LinearOutSlowInEasing
                    },
                    repeatMode = RepeatMode.Restart
                )
            )

        }
    }

    val circlesValues = circles.map{ it.value }
    val distance = with(LocalDensity.current) { 20.dp.toPx() }

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        circlesValues.forEachIndexed { index, it ->
            Box(
                modifier = Modifier
                    .size(15.dp)
                    .graphicsLayer {
                        translationY = -it * distance
                    }
                    .background(
                        color = if (darkMode.value) darkColors().onBackground else lightColors().onBackground,
                        shape = CircleShape,
                    )
            )

            if (index + 1 < circlesValues.size)
                Spacer(modifier = Modifier.width(8.dp))
        }
    }
}