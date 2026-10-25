package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.SpreadType
import com.example.ui.AiReadingState
import com.example.ui.AstroOracleViewModel
import com.example.ui.components.CosmicCard
import com.example.ui.components.InteractiveTarotCard
import com.example.ui.components.SectionHeader
import com.example.ui.theme.BorderCelestial
import com.example.ui.theme.CelestialGold
import com.example.ui.theme.CinzelFontFamily
import com.example.ui.theme.ErrorRuby
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
fun TarotScreen(
    viewModel: AstroOracleViewModel,
    modifier: Modifier = Modifier
) {
    val selectedSpread by viewModel.selectedSpread.collectAsState()
    val question by viewModel.spreadQuestion.collectAsState()
    val drawnCards by viewModel.drawnSpreadCards.collectAsState()
    val flippedIndices by viewModel.flippedCardsIndices.collectAsState()
    val aiState by viewModel.aiReadingState.collectAsState()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("tarot_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header
        item {
            SectionHeader(
                title = "Tarot Reading Sanctum",
                subtitle = "Consult the ancient arcana for guidance, clarity, and fate",
                icon = "🔮"
            )
        }

        // Spread Selector Chips
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "Select Spread",
                    fontFamily = CinzelFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = PaleGold
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    SpreadType.entries.forEach { spread ->
                        val isSelected = spread == selectedSpread
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = if (isSelected) CelestialGold else SurfaceVariantCosmic,
                            border = BorderStroke(1.dp, if (isSelected) GoldGlow else BorderCelestial),
                            modifier = Modifier
                                .clickable { viewModel.setSpreadType(spread) }
                                .testTag("spread_chip_${spread.name}")
                        ) {
                            Text(
                                text = spread.title,
                                fontFamily = CinzelFontFamily,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                fontSize = 12.sp,
                                color = if (isSelected) VoidBlack else PaleGold,
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                            )
                        }
                    }
                }
            }
        }

        // Spread Description Card
        item {
            CosmicCard {
                Column {
                    Text(
                        text = selectedSpread.title,
                        fontFamily = CinzelFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = CelestialGold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = selectedSpread.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextMutedCosmic
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Positions: ${selectedSpread.positionNames.joinToString(" → ")}",
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 11.sp,
                        color = StarlightLavender
                    )
                }
            }
        }

        // Question Input Field
        item {
            OutlinedTextField(
                value = question,
                onValueChange = { viewModel.setSpreadQuestion(it) },
                label = { Text("Your sacred question (optional)", color = TextMutedCosmic) },
                placeholder = { Text("e.g. What energy surrounds my career shift this month?", color = TextMutedCosmic.copy(alpha = 0.5f)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("tarot_question_input"),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = CelestialGold,
                    unfocusedBorderColor = BorderCelestial,
                    focusedTextColor = PaleGold,
                    unfocusedTextColor = PaleGold,
                    focusedContainerColor = SurfaceCosmic,
                    unfocusedContainerColor = SurfaceCosmic
                ),
                shape = RoundedCornerShape(12.dp)
            )
        }

        // Draw Cards Action Button
        item {
            Button(
                onClick = { viewModel.shuffleAndDrawSpread() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .testTag("shuffle_and_draw_button"),
                colors = ButtonDefaults.buttonColors(containerColor = CelestialGold),
                shape = RoundedCornerShape(14.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = null,
                    tint = VoidBlack,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (drawnCards.isEmpty()) "Shuffle & Draw Deck (${selectedSpread.cardCount} Cards)" else "Reshuffle & Draw New Cards",
                    fontFamily = CinzelFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = VoidBlack
                )
            }
        }

        // Drawn Cards Display
        if (drawnCards.isNotEmpty()) {
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Drawn Arcana (Tap each to flip)",
                            fontFamily = CinzelFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = PaleGold
                        )
                        Surface(
                            modifier = Modifier.clickable { viewModel.flipAllSpreadCards() },
                            color = SurfaceVariantCosmic,
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.dp, BorderCelestial)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Visibility,
                                    contentDescription = null,
                                    tint = CelestialGold,
                                    modifier = Modifier.size(14.dp)
                                )
                                Text(
                                    text = "Flip All",
                                    fontSize = 10.sp,
                                    color = CelestialGold,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }

                    // Horizontal cards spread
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(vertical = 8.dp)
                    ) {
                        items(drawnCards.size) { index ->
                            val drawn = drawnCards[index]
                            val isFlipped = flippedIndices.contains(index)
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = SurfaceVariantCosmic,
                                    border = BorderStroke(0.5.dp, BorderCelestial)
                                ) {
                                    Text(
                                        text = drawn.positionName,
                                        fontFamily = CinzelFontFamily,
                                        fontSize = 11.sp,
                                        color = PaleGold,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                    )
                                }

                                InteractiveTarotCard(
                                    drawnCard = drawn,
                                    isFlipped = isFlipped,
                                    onCardClick = { viewModel.flipSpreadCard(index) },
                                    cardWidth = 150.dp,
                                    cardHeight = 240.dp
                                )
                            }
                        }
                    }
                }
            }

            // Cards Detail Accordion
            item {
                CosmicCard {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text(
                            text = "Card Meanings in this Spread",
                            fontFamily = CinzelFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = CelestialGold
                        )
                        drawnCards.forEachIndexed { idx, drawn ->
                            val isFlipped = flippedIndices.contains(idx)
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(SurfaceVariantCosmic.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                                    .padding(10.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "${idx + 1}. ${drawn.positionName}",
                                        fontFamily = CinzelFontFamily,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                        color = PaleGold
                                    )
                                    Text(
                                        text = if (isFlipped) drawn.card.name + if (drawn.isReversed) " (Rev)" else " (Upright)" else "Mystery Card",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = if (isFlipped) (if (drawn.isReversed) MysticAmethyst else CelestialGold) else TextMutedCosmic
                                    )
                                }
                                if (isFlipped) {
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = if (drawn.isReversed) drawn.card.reversedMeaning else drawn.card.uprightMeaning,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = StarDust
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // AI Oracle Interpretation Trigger
            item {
                Button(
                    onClick = { viewModel.requestAiTarotInterpretation() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("ai_tarot_interpretation_button"),
                    colors = ButtonDefaults.buttonColors(containerColor = MysticAmethyst),
                    shape = RoundedCornerShape(14.dp),
                    enabled = aiState !is AiReadingState.Loading
                ) {
                    if (aiState is AiReadingState.Loading) {
                        CircularProgressIndicator(
                            color = PaleGold,
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Channeling Celestial Wisdom...",
                            fontFamily = CinzelFontFamily,
                            fontWeight = FontWeight.Bold,
                            color = PaleGold
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            tint = PaleGold,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Interpret with Mystic Oracle AI",
                            fontFamily = CinzelFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = PaleGold
                        )
                    }
                }
            }

            // AI Interpretation Result Card
            if (aiState is AiReadingState.Success) {
                val interpretation = (aiState as AiReadingState.Success).text
                item {
                    CosmicCard(borderColor = CelestialGold) {
                        Column {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(text = "📜", fontSize = 20.sp)
                                Text(
                                    text = "Oracle's Sacred Synthesis",
                                    fontFamily = CinzelFontFamily,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    color = CelestialGold
                                )
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = interpretation,
                                style = MaterialTheme.typography.bodyMedium,
                                color = StarDust,
                                lineHeight = 22.sp
                            )
                        }
                    }
                }
            } else if (aiState is AiReadingState.Error) {
                val errorMsg = (aiState as AiReadingState.Error).message
                item {
                    CosmicCard(borderColor = ErrorRuby) {
                        Text(
                            text = "Celestial disturbance: $errorMsg",
                            style = MaterialTheme.typography.bodyMedium,
                            color = ErrorRuby
                        )
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
