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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.res.painterResource
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



var line1:MutableList<Int> = mutableListOf()
var line2:MutableList<Int> = mutableListOf()
var line3:MutableList<Int> = mutableListOf()
var line4:MutableList<Int> = mutableListOf()
var line5:MutableList<Int> = mutableListOf()

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


    var show = remember{
        mutableStateOf(false)
    }

    line1 = getRandomImages(images, 8000)
    line2 = getRandomImages(images, 8000)
    line3 = getRandomImages(images, 8000)
    line4 = getRandomImages(images, 8000)
    line5 = getRandomImages(images, 8000)

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

    Box(){
        //background
        Image(painter = painterResource(id = R.drawable.back), contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds)



        Column {


            Image(painter = painterResource(id = R.drawable.slots), contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 170.dp)
                    .size(400.dp, 240.dp)

            )
            Spacer(modifier = Modifier.height(8.dp))

            //кнопочка spin
            CustomButton(onClick = {
                CoroutineScope(Dispatchers.Main).launch{
                show.value = false
                delay(1000)
                visibleImages.value = listOf()
                visibleImages1.value = listOf()
                visibleImages2.value = listOf()
                visibleImages3.value = listOf()
                visibleImages4.value = listOf()

                repeat(3){
                    spin(visibleImages,visibleImages1, visibleImages2,visibleImages3, visibleImages4)
                    delay(1000)
                    }
                }

              //roll
            },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .scale(2f)
            ){
                Image(painter = painterResource(id = R.drawable.spin_button), contentDescription = null)

            }


        }
        LazyColumnWithImages(visibleImages, modifier = Modifier
            .align(Alignment.Center)
            .padding(bottom = 255.dp, top = 200.dp), show)

        LazyColumnWithImages(visibleImages1, modifier = Modifier
            .align(Alignment.Center)
            .padding(bottom = 255.dp, start = 145.dp, top = 200.dp), show)

        LazyColumnWithImages(visibleImages2, modifier = Modifier
            .align(Alignment.Center)
            .padding(bottom = 255.dp, end = 150.dp, top = 200.dp), show)

        LazyColumnWithImages(visibleImages3, modifier = Modifier
            .align(Alignment.Center)
            .padding(bottom = 255.dp, end = 300.dp, top = 200.dp), show)

        LazyColumnWithImages(visibleImages4, modifier = Modifier
            .align(Alignment.Center)
            .padding(bottom = 255.dp, start = 300.dp, top = 200.dp), show)

    }

}

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

//@Composable
//fun randomImage(): Painter {
//    val randomImageNumber = Random.nextInt(1, 9)
//    val resourceName = "drawable/image$randomImageNumber"
//    val resId = R.drawable::class.java.getField(resourceName).getInt(null)
//    return painterResource(id = resId)
//}

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
fun LazyColumnWithImages(visibleImages:MutableState<List<Int>>, modifier: Modifier = Modifier, show:MutableState<Boolean>) {

        LazyColumn(
            contentPadding = PaddingValues(2f.dp),
            modifier = Modifier
                .then(modifier)
        ) {

            items(visibleImages.value) { image ->

                LaunchedEffect(key1 =Unit ){
                    show.value = true
                }
                AnimatedVisibility(visible =show.value,enter = scaleIn(tween (500)),
                exit = scaleOut(tween(100)) ) {
                    Image(
                        painter = painterResource(id = image),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(15.dp)
                            .scale(2f)
                    )
                }
                
            }
        }
    }




