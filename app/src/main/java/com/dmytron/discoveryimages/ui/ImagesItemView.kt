package com.dmytron.discoveryimages.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.dmytron.discoveryimages.data.Image

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageItem(image: Image, onClick: () -> Unit = {}) {
    Card(
        modifier = Modifier
            .padding(horizontal = 4.dp, vertical = 4.dp)
            .fillMaxWidth()
            .height(200.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(corner = CornerSize(4.dp))
    ) {
        Text(modifier = Modifier.fillMaxWidth(), text = image.title, maxLines = 1)
        AsyncImage(
            modifier = Modifier.fillMaxSize().padding(4.dp),
            model = image.url,
            contentDescription = image.title
        )

    }
}
