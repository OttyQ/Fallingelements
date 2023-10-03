package slotGame.com.screens

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import slotGame.com.AdditionalElements.CustomButton
import slotGame.com.Constants.TAG
import slotGame.com.R
import kotlin.random.Random


const val bars = 5

@Composable
fun GameScreen(){

    val images = listOf(
        R.drawable.image1,
        R.drawable.image2,
        R.drawable.image3,
        R.drawable.image4,
        R.drawable.image5,
        R.drawable.image6,
        R.drawable.image7,
        R.drawable.image8
    )

    var line1:MutableList<Int> = remember{getRandomImages(images, 8000)  }
    var line2:MutableList<Int> = remember{getRandomImages(images, 8000)  }
    var line3:MutableList<Int> = remember{getRandomImages(images, 8000)  }
    var line4:MutableList<Int> = remember{getRandomImages(images, 8000)  }
    var line5:MutableList<Int> = remember{getRandomImages(images, 8000)  }



    var show = remember{
        mutableStateOf(false)
    }
    var visibleImages = remember { mutableStateOf(listOf<Int>()) }
    var visibleImages1 = remember { mutableStateOf(listOf<Int>()) }
    var visibleImages2 = remember { mutableStateOf(listOf<Int>()) }
    var visibleImages3 = remember { mutableStateOf(listOf<Int>()) }
    var visibleImages4 = remember { mutableStateOf(listOf<Int>()) }

    if(visibleImages.value.size > 3){
        visibleImages.value = listOf()
        visibleImages1.value = listOf()
        visibleImages2.value = listOf()
        visibleImages3.value = listOf()
        visibleImages4.value = listOf()
    }
    val screenHeightDp = LocalConfiguration.current.screenHeightDp
    val screenWidthDp = LocalConfiguration.current.screenWidthDp
    Box {
        Image(painter = painterResource(id = R.drawable.back), contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds)

        val cs = rememberCoroutineScope()
        Column {
            Box(contentAlignment = Alignment.Center,modifier = Modifier
                .padding(top = (screenHeightDp * 0.15).dp)
                .size(screenWidthDp.dp, (screenWidthDp / 5 * 3).dp)){
                Image(painter = painterResource(id = R.drawable.slots), contentDescription = null,
                    modifier = Modifier.fillMaxSize(), contentScale = ContentScale.FillBounds,
                )
                Row(modifier = Modifier.fillMaxSize()){
                    LazyColumnWithImages(visibleImages,  show,(screenWidthDp/bars).dp)
                    LazyColumnWithImages(visibleImages1, show,(screenWidthDp/bars).dp)
                    LazyColumnWithImages(visibleImages2, show,(screenWidthDp/bars).dp)
                    LazyColumnWithImages(visibleImages3, show,(screenWidthDp/bars).dp)
                    LazyColumnWithImages(visibleImages4, show,(screenWidthDp/bars).dp)

                }
            }

            Spacer(modifier = Modifier.height((screenHeightDp*0.1).dp))
            fun spin(visibleImages:MutableState<List<Int>>, visibleImages1:MutableState<List<Int>>,
                     visibleImages2:MutableState<List<Int>>, visibleImages3:MutableState<List<Int>>,
                     visibleImages4:MutableState<List<Int>>
            ){
                visibleImages.value += line1.get(Random.nextInt(line1.size-1))
                visibleImages1.value += line2.get(Random.nextInt(line2.size-1))
                visibleImages2.value += line3.get(Random.nextInt(line3.size-1))
                visibleImages3.value += line4.get(Random.nextInt(line4.size-1))
                visibleImages4.value += line5.get(Random.nextInt(line5.size-1))
            }
            CustomButton(onClick = {
                cs.launch {
                    show.value = false
                    visibleImages.value = listOf()
                    visibleImages1.value = listOf()
                    visibleImages2.value = listOf()
                    visibleImages3.value = listOf()
                    visibleImages4.value = listOf()
                    delay(500)

                    repeat(3){
                        spin(visibleImages,visibleImages1, visibleImages2,visibleImages3, visibleImages4)
                        delay(200)
                    }
                }


            },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .scale(2f)
            ){
                Image(painter = painterResource(id = R.drawable.spin_button), contentDescription = null)

            }


        }


    }

}


fun getRandomImages(resourceIds: List<Int>, count: Int): MutableList<Int> {
    val images = mutableListOf<Int>()
    val random = Random.Default

    repeat(count) {
        val randomIndex = random.nextInt(resourceIds.size)
        val randomImageId = resourceIds[randomIndex]
        images.add(randomImageId)
    }
    return images
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun LazyColumnWithImages(visibleImages:MutableState<List<Int>>, show:MutableState<Boolean>,fullWidth: Dp) {

        LazyColumn(
            contentPadding = PaddingValues(2f.dp),
            modifier = Modifier
                .fillMaxHeight()
                .width(fullWidth),
            horizontalAlignment = Alignment.CenterHorizontally,
            reverseLayout = true,
            verticalArrangement = Arrangement.Bottom
        ) {

            items(visibleImages.value) { image ->
                val temp = remember(image,show.value) {
                    mutableStateOf(false)
                }
                LaunchedEffect(image,show.value){
                    temp.value = true
                }

                AnimatedVisibility(visible =temp.value,enter = fadeIn(tween(200))+ slideInVertically(tween(200) { it }),
                exit = scaleOut(tween(100)) ) {
                    Image(
                        painter = painterResource(id = image),
                        contentDescription = null,
                        modifier = Modifier
                            .size((fullWidth.value).dp)
                            .padding(16.dp),
                        contentScale = ContentScale.FillBounds
                    )
                }
                
            }
        }
    }




