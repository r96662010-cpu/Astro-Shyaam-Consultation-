package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.AppTab
import com.example.ui.AstroOracleViewModel
import com.example.ui.components.CosmicCard
import com.example.ui.components.CosmicPill
import com.example.ui.components.InteractiveTarotCard
import com.example.ui.components.SectionHeader
import com.example.ui.components.ZodiacBadge
import com.example.ui.theme.BorderCelestial
import com.example.ui.theme.CelestialGold
import com.example.ui.theme.CinzelFontFamily
import com.example.ui.theme.GoldGlow
import com.example.ui.theme.MysticAmethyst
import com.example.ui.theme.PaleGold
import com.example.ui.theme.RadiantCyan
import com.example.ui.theme.StarDust
import com.example.ui.theme.StarlightLavender
import com.example.ui.theme.SurfaceCosmic
import com.example.ui.theme.SurfaceVariantCosmic
import com.example.ui.theme.TextMutedCosmic
import com.example.ui.theme.VoidBlack

@Composable
fun DailyInsightScreen(
    viewModel: AstroOracleViewModel,
    modifier: Modifier = Modifier
) {
    val profile by viewModel.userProfile.collectAsState()
    val forecast by viewModel.dailyForecast.collectAsState()
    val dailyCard by viewModel.dailyCard.collectAsState()
    val isCardFlipped by viewModel.isDailyCardFlipped.collectAsState()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("daily_insight_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero Cosmic Banner
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp),
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(1.5.dp, CelestialGold.copy(alpha = 0.5f)),
                colors = CardDefaults.cardColors(containerColor = SurfaceCosmic)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = painterResource(id = R.drawable.bg_celestial_hero),
                        contentDescription = "Celestial Hero Banner",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    // Gradient scrim
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        VoidBlack.copy(alpha = 0.85f)
                                    )
                                )
                            )
                    )

                    // Text Overlay
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = VoidBlack.copy(alpha = 0.7f),
                                border = BorderStroke(0.5.dp, CelestialGold)
                            ) {
                                Text(
                                    text = forecast?.moonPhase ?: "Moon in Transit",
                                    fontFamily = CinzelFontFamily,
                                    fontSize = 11.sp,
                                    color = PaleGold,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                )
                            }

                            IconButton(
                                onClick = { viewModel.setShowProfileDialog(true) },
                                modifier = Modifier
                                    .size(34.dp)
                                    .background(VoidBlack.copy(alpha = 0.7f), CircleShape)
                                    .testTag("edit_profile_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "Edit Profile",
                                    tint = CelestialGold,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }

                        Column {
                            Text(
                                text = "Welcome, ${profile?.name ?: "Seeker"}",
                                fontFamily = CinzelFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 22.sp,
                                color = PaleGold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                profile?.zodiacSign?.let { sign ->
                                    ZodiacBadge(sign = sign)
                                }
                                profile?.numerologyProfile?.let { num ->
                                    CosmicPill(text = "Life Path ${num.lifePathNumber}", color = RadiantCyan)
                                }
                            }
                        }
                    }
                }
            }
        }

        // Daily Card of the Day Section
        item {
            CosmicCard(
                borderColor = if (isCardFlipped) CelestialGold else MysticAmethyst
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    SectionHeader(
                        title = "Tarot Card of the Day",
                        subtitle = if (isCardFlipped) "Today's cosmic archetype and spiritual counsel" else "Tap the sacred card to reveal your guiding vibration",
                        icon = "🎴"
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    dailyCard?.let { drawn ->
                        InteractiveTarotCard(
                            drawnCard = drawn,
                            isFlipped = isCardFlipped,
                            onCardClick = { viewModel.flipDailyCard() },
                            cardWidth = 170.dp,
                            cardHeight = 270.dp
                        )

                        if (isCardFlipped) {
                            Spacer(modifier = Modifier.height(16.dp))
                            Surface(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                color = SurfaceVariantCosmic,
                                border = BorderStroke(1.dp, BorderCelestial)
                            ) {
                                Column(modifier = Modifier.padding(14.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = drawn.card.name,
                                            fontFamily = CinzelFontFamily,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 16.sp,
                                            color = PaleGold
                                        )
                                        Text(
                                            text = if (drawn.isReversed) "Reversed" else "Upright",
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = if (drawn.isReversed) MysticAmethyst else CelestialGold
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = if (drawn.isReversed) drawn.card.reversedMeaning else drawn.card.uprightMeaning,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = StarDust
                                    )

                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = "Oracle Counsel: ${drawn.card.cosmicAdvice}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = GoldGlow,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))
                            Button(
                                onClick = {
                                    viewModel.setChatInput("What does today's card '${drawn.card.name}' (${if (drawn.isReversed) "Reversed" else "Upright"}) mean for my journey today?")
                                    viewModel.selectTab(AppTab.ORACLE)
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("ask_oracle_about_card_button"),
                                colors = ButtonDefaults.buttonColors(containerColor = CelestialGold)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ChatBubble,
                                    contentDescription = null,
                                    tint = VoidBlack,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Ask Oracle About This Card",
                                    color = VoidBlack,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = CinzelFontFamily
                                )
                            }
                        }
                    }
                }
            }
        }

        // Daily Celestial Horoscope & Transits
        item {
            CosmicCard {
                Column(modifier = Modifier.fillMaxWidth()) {
                    SectionHeader(
                        title = "${profile?.zodiacSign?.englishName ?: "Zodiac"} Daily Horoscope",
                        subtitle = forecast?.dateString,
                        icon = profile?.zodiacSign?.symbol ?: "♈"
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = profile?.zodiacSign?.dailyAdvice ?: "Align with the celestial rhythms.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = StarDust,
                        lineHeight = 22.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Elemental Transit Note
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        color = SurfaceVariantCosmic.copy(alpha = 0.6f),
                        border = BorderStroke(1.dp, BorderCelestial)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = "Planetary Transit Focus",
                                fontFamily = CinzelFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                color = CelestialGold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = forecast?.planetaryTransit ?: "Planets move through harmonic aspects.",
                                style = MaterialTheme.typography.bodySmall,
                                color = StarlightLavender
                            )
                        }
                    }
                }
            }
        }

        // Sacred Numbers & Affirmation Grid
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Lucky Color
                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceCosmic),
                    border = BorderStroke(1.dp, BorderCelestial)
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "🎨", fontSize = 24.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Aura Color",
                            fontFamily = CinzelFontFamily,
                            fontSize = 11.sp,
                            color = TextMutedCosmic
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = forecast?.luckyColor ?: "Amethyst",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = PaleGold,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                // Lucky Numbers
                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceCosmic),
                    border = BorderStroke(1.dp, BorderCelestial)
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "🔮", fontSize = 24.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Lucky Numbers",
                            fontFamily = CinzelFontFamily,
                            fontSize = 11.sp,
                            color = TextMutedCosmic
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = forecast?.luckyNumbers?.joinToString(" • ") ?: "7 • 11 • 21",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = RadiantCyan,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }

        // Power Affirmation
        item {
            CosmicCard(borderColor = MysticAmethyst.copy(alpha = 0.6f)) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "✨ Sacred Daily Affirmation ✨",
                        fontFamily = CinzelFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = CelestialGold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "\"${forecast?.powerAffirmation ?: "I am aligned with cosmic abundance and truth."}\"",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                        color = PaleGold,
                        textAlign = TextAlign.Center,
                        lineHeight = 22.sp
                    )
                }
            }
        }

        // Quick Consultation Prompt to Oracle
        item {
            OutlinedButton(
                onClick = { viewModel.selectTab(AppTab.ORACLE) },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("open_oracle_chat_prompt"),
                border = BorderStroke(1.dp, CelestialGold),
                shape = RoundedCornerShape(14.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.AutoAwesome,
                    contentDescription = null,
                    tint = CelestialGold,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Speak with the Mystic Oracle AI",
                    color = PaleGold,
                    fontFamily = CinzelFontFamily,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
