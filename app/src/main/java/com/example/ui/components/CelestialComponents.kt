package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.DrawnCard
import com.example.data.model.ZodiacSign
import com.example.ui.theme.BorderCelestial
import com.example.ui.theme.CelestialGold
import com.example.ui.theme.CinzelFontFamily
import com.example.ui.theme.DeepSpaceNavy
import com.example.ui.theme.GoldGlow
import com.example.ui.theme.MysticAmethyst
import com.example.ui.theme.PaleGold
import com.example.ui.theme.RadiantCyan
import com.example.ui.theme.StarlightLavender
import com.example.ui.theme.SurfaceCosmic
import com.example.ui.theme.SurfaceVariantCosmic
import com.example.ui.theme.TextMutedCosmic
import com.example.ui.theme.VoidBlack

@Composable
fun CelestialBackground(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        VoidBlack,
                        DeepSpaceNavy,
                        SurfaceCosmic
                    )
                )
            )
    ) {
        content()
    }
}

@Composable
fun CosmicCard(
    modifier: Modifier = Modifier,
    borderColor: Color = BorderCelestial,
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = SurfaceCosmic.copy(alpha = 0.92f)
        ),
        border = BorderStroke(1.dp, borderColor)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            content()
        }
    }
}

@Composable
fun SectionHeader(
    title: String,
    subtitle: String? = null,
    icon: String = "✨",
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = icon, fontSize = 20.sp)
            Text(
                text = title,
                fontFamily = CinzelFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = PaleGold
            )
        }
        if (subtitle != null) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = TextMutedCosmic
            )
        }
    }
}

@Composable
fun ZodiacBadge(
    sign: ZodiacSign,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        color = SurfaceVariantCosmic,
        border = BorderStroke(1.dp, CelestialGold.copy(alpha = 0.6f))
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(text = sign.symbol, fontSize = 16.sp, color = CelestialGold)
            Text(
                text = sign.englishName,
                fontWeight = FontWeight.SemiBold,
                fontSize = 13.sp,
                color = PaleGold
            )
            Text(
                text = "• ${sign.element.icon}",
                fontSize = 11.sp,
                color = TextMutedCosmic
            )
        }
    }
}

@Composable
fun CosmicPill(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MysticAmethyst
) {
    Surface(
        modifier = modifier,
        shape = CircleShape,
        color = color.copy(alpha = 0.15f),
        border = BorderStroke(1.dp, color.copy(alpha = 0.4f))
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelMedium,
            color = color
        )
    }
}

/**
 * Interactive 3D Flip Tarot Card Component
 */
@Composable
fun InteractiveTarotCard(
    drawnCard: DrawnCard,
    isFlipped: Boolean,
    onCardClick: () -> Unit,
    modifier: Modifier = Modifier,
    cardWidth: Dp = 160.dp,
    cardHeight: Dp = 260.dp
) {
    // 3D rotation animation
    val rotation by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing),
        label = "tarot_flip"
    )

    Box(
        modifier = modifier
            .width(cardWidth)
            .height(cardHeight)
            .clickable { onCardClick() }
            .graphicsLayer {
                rotationY = rotation
                cameraDistance = 12f * density
            },
        contentAlignment = Alignment.Center
    ) {
        if (rotation <= 90f) {
            // Card Back
            TarotCardBack(width = cardWidth, height = cardHeight)
        } else {
            // Card Front (mirrored back for correct perspective)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer { rotationY = 180f }
            ) {
                TarotCardFront(drawnCard = drawnCard)
            }
        }
    }
}

@Composable
fun TarotCardBack(
    width: Dp,
    height: Dp,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .width(width)
            .height(height)
            .testTag("tarot_card_back"),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(2.dp, CelestialGold),
        colors = CardDefaults.cardColors(containerColor = SurfaceVariantCosmic)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.ic_tarot_back),
                contentDescription = "Mystic Tarot Card Back",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            // Tap hint overlay
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 8.dp)
                    .background(VoidBlack.copy(alpha = 0.7f), RoundedCornerShape(12.dp))
                    .padding(horizontal = 8.dp, vertical = 2.dp)
            ) {
                Text(
                    text = "Tap to Flip",
                    fontFamily = CinzelFontFamily,
                    fontSize = 10.sp,
                    color = PaleGold
                )
            }
        }
    }
}

@Composable
fun TarotCardFront(
    drawnCard: DrawnCard,
    modifier: Modifier = Modifier
) {
    val card = drawnCard.card
    val isReversed = drawnCard.isReversed

    Card(
        modifier = modifier
            .fillMaxSize()
            .testTag("tarot_card_front_${card.id}"),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(2.dp, if (isReversed) MysticAmethyst else CelestialGold),
        colors = CardDefaults.cardColors(
            containerColor = SurfaceCosmic
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            SurfaceVariantCosmic,
                            SurfaceCosmic,
                            VoidBlack
                        )
                    )
                )
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Top: Roman Numeral & Astrology
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = card.romanNumeral,
                        fontFamily = CinzelFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = CelestialGold
                    )
                    Text(
                        text = card.astrologyAssociation,
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 9.sp,
                        color = StarlightLavender
                    )
                }

                // Center Icon with Glowing Aura
                Box(
                    modifier = Modifier
                        .size(68.dp)
                        .clip(CircleShape)
                        .background(SurfaceVariantCosmic)
                        .border(1.dp, CelestialGold.copy(alpha = 0.4f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = card.iconSymbol,
                        fontSize = 32.sp,
                        modifier = Modifier.then(
                            if (isReversed) Modifier.graphicsLayer { rotationZ = 180f }
                            else Modifier
                        )
                    )
                }

                // Card Name & Status
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(
                        text = card.name,
                        fontFamily = CinzelFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = PaleGold,
                        textAlign = TextAlign.Center,
                        maxLines = 1
                    )

                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = if (isReversed) MysticAmethyst.copy(alpha = 0.25f) else CelestialGold.copy(alpha = 0.25f),
                        border = BorderStroke(0.5.dp, if (isReversed) MysticAmethyst else CelestialGold)
                    ) {
                        Text(
                            text = if (isReversed) "REVERSED" else "UPRIGHT",
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isReversed) StarlightLavender else GoldGlow,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 1.dp)
                        )
                    }

                    // Key keywords
                    val keywords = if (isReversed) card.reversedKeywords else card.uprightKeywords
                    Text(
                        text = keywords.take(2).joinToString(" • "),
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 9.sp,
                        color = TextMutedCosmic,
                        textAlign = TextAlign.Center,
                        maxLines = 1
                    )
                }
            }
        }
    }
}
