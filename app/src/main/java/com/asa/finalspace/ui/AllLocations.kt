package com.asa.finalspace.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerSnapDistance
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.asa.finalspace.model.locations.GetAllLocationsItem
import com.asa.finalspace.viewmodel.AllLocationsViewModel
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AllLocations(
    viewModel: AllLocationsViewModel,
    modifier: Modifier = Modifier,
    onLocationClick: (GetAllLocationsItem) -> Unit
) {
    val locationList by viewModel.locationsList.observeAsState(emptyList())
    println("AllLocations composable called, list size: ${locationList.size}")


    // Use HorizontalPager instead of LazyVerticalGrid for carousel effect
    val pagerState = rememberPagerState(
        initialPage = 2,
        pageCount = { locationList.size }
    )

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Title for the locations
        Text(
            text = "Final Space Locations",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        // 3D Carousel implementation
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp),
            contentPadding = PaddingValues(horizontal = 64.dp),
            pageSpacing = 8.dp,
            // Customize the fling behavior
            flingBehavior = PagerDefaults.flingBehavior(
                state = pagerState,
                pagerSnapDistance = PagerSnapDistance.atMost(2)
            )
        ) { page ->
            if (page < locationList.size) {
                val location = locationList[page]
                val density = LocalDensity.current.density

                // Calculate the page offset for 3D effect
                val pageOffset = (
                        (pagerState.currentPage - page) + pagerState
                            .currentPageOffsetFraction
                        ).absoluteValue

                // Scale and rotation transformations based on page offset
                val scale = animateFloatAsState(
                    targetValue = if (pageOffset < 0.5) 1f - (pageOffset * 0.15f) else 0.85f,
                    label = "scale"
                )

                // Apply 3D circular effect
                CarouselCard(
                    location = location,
                    modifier = Modifier
                        .graphicsLayer {
                            // Scale effect
                            scaleX = scale.value
                            scaleY = scale.value

                            // Rotation effect for 3D circular movement
                            rotationY = pageOffset * 30f

                            // Adjust cameraDistance for depth effect
                            cameraDistance = 8 * (density * 96f) * (1f - pageOffset * 0.5f)

                            // Adjust alpha for fade effect
                            alpha = 1f - (pageOffset * 0.5f).coerceAtMost(0.5f)

                            // Adjust X-translation for circular path effect
                            val radius = 100f
                            translationX = (kotlin.math.sin(pageOffset * kotlin.math.PI.toFloat() * 0.5f) * radius)
                        }
                        .clickable { onLocationClick(location) }
                )
            }
        }

        // Optional: Add indicators for current page
        Row(
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(locationList.size) { index ->
                val color = if (pagerState.currentPage == index) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                }

                Box(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .size(10.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .background(color)
                )
            }
        }
    }
}

@Composable
fun CarouselCard(
    location: GetAllLocationsItem,
    modifier: Modifier = Modifier  // Added default value
) {
    println("HERE IS THE LOCATION ${location.name}")
    println("HERE IS THE LOCATION TYPE ${location.type}")
    println("HERE IS THE LOCATION IMAGE${location.img_url}")
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(350.dp)
            .aspectRatio(0.89f)
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Location image - Add debug info
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(location.img_url)
                    .crossfade(true)
                    .build(),
                contentDescription = location.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),

            )

            // Location details
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = location.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Type: ${location.type}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(2.dp))

                // Image URL debug text
//                Text(
//                    text = "Image URL: ${location.img_url}",
//                    style = MaterialTheme.typography.bodySmall,
//                    color = MaterialTheme.colorScheme.error,
//                    fontSize = 10.sp,
//                    maxLines = 1,
//                    overflow = TextOverflow.Ellipsis
//                )

                // Uncommented the inhabitants section with null safety
//                location.inhabitants?.let { inhabitants ->
//                    if (inhabitants.isNotEmpty()) {
//                        Text(
//                            text = "Inhabitants: ${inhabitants.joinToString(", ", limit = 2)}",
//                            style = MaterialTheme.typography.bodySmall,
//                            color = MaterialTheme.colorScheme.onSurfaceVariant,
//                            maxLines = 1,
//                            overflow = TextOverflow.Ellipsis
//                        )
//                    }
//                }
            }
        }
    }
}