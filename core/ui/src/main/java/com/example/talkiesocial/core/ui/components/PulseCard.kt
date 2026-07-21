package com.example.talkiesocial.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.talkiesocial.core.ui.util.neonGlow

enum class PulseType { TEXT, IMAGE, AUDIO }

@Composable
fun PulseCard(
    type: PulseType,
    username: String,
    avatarUrl: String,
    content: String,
    timeAgo: String,
    imageUrl: String? = null,
    resonateFrom: String? = null,
    likes: String,
    comments: String,
    shares: String,
    onLikeClick: () -> Unit = {},
    onCommentClick: () -> Unit = {},
    onResonateClick: () -> Unit = {},
    onVoiceReplyClick: () -> Unit = {}
) {
    var isLiked by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                brush = Brush.linearGradient(
                    listOf(Color.White.copy(alpha = 0.1f), Color.Transparent)
                ),
                shape = RoundedCornerShape(32.dp)
            ),
        shape = RoundedCornerShape(32.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (resonateFrom != null) Color(0xFF1A1230) else Color(0x1AFFFFFF)
        )
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            if (resonateFrom != null) {
                Row(
                    modifier = Modifier.padding(bottom = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Repeat,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "RESONATED FROM $resonateFrom",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Black,
                            letterSpacing = 1.sp
                        ),
                        color = MaterialTheme.colorScheme.tertiary
                    )
                }
            }

            // Header
            Row(verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    model = avatarUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .border(1.dp, MaterialTheme.colorScheme.primary, CircleShape),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = username,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 14.sp
                    )
                    Text(text = timeAgo, fontSize = 10.sp, color = Color.Gray)
                }
                Spacer(modifier = Modifier.weight(1f))
                Text(text = "•••", color = Color.Gray, modifier = Modifier.clickable { })
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Content
            Text(
                text = content,
                color = Color.White.copy(alpha = 0.9f),
                style = if (type == PulseType.TEXT) MaterialTheme.typography.bodyLarge.copy(fontStyle = androidx.compose.ui.text.font.FontStyle.Italic) 
                        else MaterialTheme.typography.bodyMedium,
                lineHeight = 20.sp
            )

            if (type == PulseType.IMAGE && imageUrl != null) {
                Spacer(modifier = Modifier.height(16.dp))
                AsyncImage(
                    model = imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(20.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            if (type == PulseType.AUDIO) {
                Spacer(modifier = Modifier.height(16.dp))
                AudioVisualizer()
            }

            Spacer(modifier = Modifier.height(20.dp))
            HorizontalDivider(color = Color.White.copy(alpha = 0.05f))
            Spacer(modifier = Modifier.height(12.dp))

            // Interaction Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
                    InteractionItem(
                        icon = if (isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        count = likes,
                        color = if (isLiked) Color.Red else Color.Gray,
                        onClick = { 
                            isLiked = !isLiked
                            onLikeClick()
                        },
                        isGlow = isLiked
                    )
                    InteractionItem(
                        icon = Icons.AutoMirrored.Filled.Chat,
                        count = comments,
                        onClick = onCommentClick
                    )
                    InteractionItem(
                        icon = Icons.Default.Repeat,
                        count = shares,
                        onClick = onResonateClick
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(Color.White.copy(alpha = 0.05f), CircleShape)
                            .clickable { onVoiceReplyClick() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Mic, null, modifier = Modifier.size(16.dp), tint = MaterialTheme.colorScheme.primary)
                    }
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(Color.White.copy(alpha = 0.05f), CircleShape)
                            .clickable { },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Share, null, modifier = Modifier.size(16.dp), tint = Color.White)
                    }
                }
            }
        }
    }
}

@Composable
fun InteractionItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    count: String,
    color: Color = Color.Gray,
    onClick: () -> Unit,
    isGlow: Boolean = false
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable { onClick() }
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = color,
            modifier = Modifier
                .size(20.dp)
                .then(
                    if (isGlow) Modifier.neonGlow(color.copy(alpha = 0.5f), blurRadius = 10.dp)
                    else Modifier
                )
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(text = count, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = color)
    }
}

@Composable
fun AudioVisualizer() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(Color.Black.copy(alpha = 0.4f), RoundedCornerShape(16.dp))
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(MaterialTheme.colorScheme.primary, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.PlayArrow, null, tint = Color.Black)
        }
        
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            repeat(20) {
                val height = (10..30).random().dp
                Box(
                    modifier = Modifier
                        .width(3.dp)
                        .height(height)
                        .background(
                            brush = Brush.verticalGradient(
                                listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.tertiary)
                            ),
                            shape = RoundedCornerShape(2.dp)
                        )
                )
            }
        }
        
        Text(text = "0:42", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
    }
}
