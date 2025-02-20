package com.example.staggered_photo_gallery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.staggered_photo_gallery.ui.theme.Staggered_Photo_GalleryTheme

//Create a photo gallery using LazyVerticalGrid in a staggered layout. Store image file
//names and titles in an XML resource (res/xml/photos.xml). Use LazyVerticalGrid
//with a staggered layout to display images of varying heights. You must load images
//dynamically from the drawable folder. When the user clicks an image should enlarge
//it using a coroutine animation. The photos.xml file should be in this format:
//<photos>
//<photo>
//<title>Sunset</title>
//<file>sunset.jpg</file>
//</photo>
//<photo>
//<title>Mountains</title>
//<file>mountains.jpg</file>
//</photo>
//</photos>

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Staggered_Photo_GalleryTheme {
                GalleryScreen()
            }
        }
    }
}

@Composable
fun GalleryScreen() {
    //add pictures
    val photos = listOf(
        painterResource(R.drawable.gallery_photo),
        painterResource(R.drawable.gallery_photo_1),
        painterResource(R.drawable.gallery_photo_2),
        painterResource(R.drawable.gallery_photo_4),
        painterResource(R.drawable.gallery_photo_7),
        painterResource(R.drawable.gallery_photo_3),
        painterResource(R.drawable.gallery_photo_5)
    )

    //initialize var for keeping track of which photo is clicked
    var photoClicked by remember { mutableStateOf<Painter?>(null) }

    Box(modifier = Modifier.fillMaxSize()){
        //staggered grid holds photos and makes photos clickable w var
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            content = {
                items(photos) { photo ->
                    Image(
                        painter = photo,
                        contentDescription = "photo",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                            .clickable { photoClicked = photo }
                    )
                }
            }
        )
    }

    // if photo is clicked, enlarge it and set it back to null state
    if (photoClicked != null){
        EnlargePhoto(photoClicked!!){
            photoClicked = null
        }
    }
}

//function for enlarging clicked photo
@Composable
fun EnlargePhoto(photo:Painter, onClose:() -> Unit) {
    // var for keeping track and enlarged photos are all the same scale
    var enlarged by remember { mutableStateOf(true) }
    val scale by animateFloatAsState(
        targetValue = if (enlarged) 2f else 0.0f,
        animationSpec = tween(300)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                enlarged = false
                onClose()
            },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = photo,
            contentDescription = "Enlarged photo",
            modifier = Modifier
                .fillMaxWidth(1f)
                .scale(scale)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GalleryScreenPreview() {
    GalleryScreen()
}