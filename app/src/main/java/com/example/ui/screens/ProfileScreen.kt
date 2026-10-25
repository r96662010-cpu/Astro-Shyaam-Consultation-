package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.SavedReadingEntity
import com.example.ui.AstroOracleViewModel
import com.example.ui.components.CosmicCard
import com.example.ui.components.CosmicPill
import com.example.ui.components.SectionHeader
import com.example.ui.components.ZodiacBadge
import com.example.ui.theme.BorderCelestial
import com.example.ui.theme.CelestialGold
import com.example.ui.theme.CinzelFontFamily
import com.example.ui.theme.MysticAmethyst
import com.example.ui.theme.PaleGold
import com.example.ui.theme.RadiantCyan
import com.example.ui.theme.StarDust
import com.example.ui.theme.StarlightLavender
import com.example.ui.theme.SurfaceCosmic
import com.example.ui.theme.SurfaceVariantCosmic
import com.example.ui.theme.TextMutedCosmic
import com.example.ui.theme.VoidBlack
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ProfileScreen(
    viewModel: AstroOracleViewModel,
    modifier: Modifier = Modifier
) {
    val profile by viewModel.userProfile.collectAsState()
    val savedReadings by viewModel.savedReadings.collectAsState()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("profile_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            SectionHeader(
                title = "Cosmic Blueprint & Journal",
                subtitle = "Your soul's natal coordinates and recorded oracle consultations",
                icon = "🌌"
            )
        }

        // Profile Card
        item {
            CosmicCard(borderColor = CelestialGold) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = profile?.name ?: "Seeker",
                                fontFamily = CinzelFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 22.sp,
                                color = PaleGold
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Born: ${profile?.birthDate ?: "1998-07-15"} ${if (!profile?.birthTime.isNullOrBlank()) "at " + profile?.birthTime else ""}",
                                style = MaterialTheme.typography.bodySmall,
                                color = TextMutedCosmic
                            )
                            if (!profile?.birthCity.isNullOrBlank()) {
                                Text(
                                    text = "City: ${profile?.birthCity}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = TextMutedCosmic
                                )
                            }
                        }

                        Button(
                            onClick = { viewModel.setShowProfileDialog(true) },
                            colors = ButtonDefaults.buttonColors(containerColor = CelestialGold),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.testTag("edit_birth_details_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = null,
                                tint = VoidBlack,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Edit",
                                color = VoidBlack,
                                fontWeight = FontWeight.Bold,
                                fontFamily = CinzelFontFamily,
                                fontSize = 12.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    profile?.let { p ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            ZodiacBadge(sign = p.zodiacSign)
                            CosmicPill(text = "Ruler: ${p.zodiacSign.ruler}", color = MysticAmethyst)
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Astrological Traits: ${p.zodiacSign.traits}",
                            style = MaterialTheme.typography.bodySmall,
                            color = StarDust
                        )
                    }
                }
            }
        }

        // Numerological Matrix Overview
        item {
            CosmicCard {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "Numerological Alignment",
                        fontFamily = CinzelFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = CelestialGold
                    )

                    profile?.numerologyProfile?.let { n ->
                        NumberRow(label = "Life Path Number", value = n.lifePathNumber, desc = n.lifePathInfo.title)
                        NumberRow(label = "Destiny / Expression", value = n.destinyNumber, desc = n.destinyInfo.title)
                        NumberRow(label = "Soul Urge Number", value = n.soulUrgeNumber, desc = n.soulUrgeInfo.title)
                        NumberRow(label = "Personal Year 2026", value = n.personalYearNumber, desc = n.personalYearInfo.title)
                    }
                }
            }
        }

        // Saved Tarot Readings Journal
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Book,
                    contentDescription = null,
                    tint = CelestialGold,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = "Tarot Reading Journal (${savedReadings.size})",
                    fontFamily = CinzelFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = PaleGold
                )
            }
        }

        if (savedReadings.isEmpty()) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceCosmic),
                    border = BorderStroke(1.dp, BorderCelestial)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "📖", fontSize = 32.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "No saved readings yet",
                            fontFamily = CinzelFontFamily,
                            fontWeight = FontWeight.Bold,
                            color = PaleGold,
                            fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Draw cards in the Tarot Sanctum and request an AI interpretation to save your readings here.",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextMutedCosmic
                        )
                    }
                }
            }
        } else {
            items(savedReadings, key = { it.id }) { reading ->
                JournalReadingItem(reading = reading)
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun NumberRow(
    label: String,
    value: Int,
    desc: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(SurfaceVariantCosmic.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
            .padding(horizontal = 10.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label,
                fontFamily = CinzelFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp,
                color = PaleGold
            )
            Text(
                text = desc,
                style = MaterialTheme.typography.bodySmall,
                fontSize = 10.sp,
                color = StarlightLavender
            )
        }
        Box(
            modifier = Modifier
                .size(32.dp)
                .background(SurfaceCosmic, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = value.toString(),
                fontFamily = CinzelFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = CelestialGold
            )
        }
    }
}

@Composable
private fun JournalReadingItem(reading: SavedReadingEntity) {
    var expanded by remember { mutableStateOf(false) }
    val dateStr = SimpleDateFormat("MMM d, yyyy • h:mm a", Locale.getDefault()).format(Date(reading.timestamp))

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceCosmic),
        border = BorderStroke(1.dp, BorderCelestial)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = reading.spreadType,
                        fontFamily = CinzelFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = CelestialGold
                    )
                    Text(
                        text = dateStr,
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 10.sp,
                        color = TextMutedCosmic
                    )
                }
                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    tint = PaleGold
                )
            }

            if (reading.question.isNotBlank()) {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Question: \"${reading.question}\"",
                    style = MaterialTheme.typography.bodySmall,
                    color = StarlightLavender,
                    fontWeight = FontWeight.Medium
                )
            }

            AnimatedVisibility(visible = expanded) {
                Column(modifier = Modifier.padding(top = 10.dp)) {
                    Text(
                        text = reading.aiInterpretation,
                        style = MaterialTheme.typography.bodySmall,
                        color = StarDust,
                        lineHeight = 18.sp
                    )
                }
            }
        }
    }
}
