package slotGame.com.AdditionalElements

import android.view.MotionEvent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.pointerInteropFilter
import kotlinx.coroutines.launch


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CustomButton(
    onClick:()-> Unit,
    modifier: Modifier =Modifier,
    content: @Composable () -> Unit
    ){
    val animateScale = remember { Animatable(1f) }
    val cor = rememberCoroutineScope()
    Box(
        modifier = Modifier.then(modifier)
            .scale(animateScale.value)
            .pointerInteropFilter {
                when(it.action){
                    MotionEvent.ACTION_DOWN->{
                        cor.launch {
                            animateScale.animateTo(0.79f, animationSpec = tween(80))
                        }
                    }
                    MotionEvent.ACTION_UP -> {
                        cor.launch {
                            animateScale.animateTo(1f, animationSpec =tween(80), block = { if (animateScale.value == 1f) { onClick.invoke() } })
                        }
                    }
                }
                true
            },
        contentAlignment = Alignment.Center
    ){
    content.invoke()
    }

}