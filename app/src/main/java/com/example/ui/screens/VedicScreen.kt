package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.icons.filled.Diamond
import androidx.compose.material.icons.filled.Flare
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.filled.Shield
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.AstroOracleViewModel
import com.example.ui.components.CosmicCard
import com.example.ui.components.CosmicPill
import com.example.ui.components.SectionHeader
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
fun VedicScreen(
    viewModel: AstroOracleViewModel,
    modifier: Modifier = Modifier
) {
    val profile by viewModel.userProfile.collectAsState()
    val vedic = profile?.vedicProfile

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("vedic_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            SectionHeader(
                title = "Vedic Jyotish & Kundali",
                subtitle = "Sidereal zodiac, 27 sacred Nakshatras, and Navagraha karmic remedies",
                icon = "🕉️"
            )
        }

        // Janma Rashi & Nakshatra Hero Card
        item {
            vedic?.let { v ->
                CosmicCard(borderColor = CelestialGold) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Sidereal Janma Rashi (Vedic Moon/Sun)",
                                    fontFamily = CinzelFontFamily,
                                    fontSize = 11.sp,
                                    color = TextMutedCosmic
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "${v.rashi.sanskritName} Rashi",
                                    fontFamily = CinzelFontFamily,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp,
                                    color = PaleGold
                                )
                                Text(
                                    text = "Western equivalent: ${v.rashi.westernEquivalent} • ${v.rashi.elementTattva}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = StarlightLavender
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .size(52.dp)
                                    .background(SurfaceVariantCosmic, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = v.rashi.symbol,
                                    fontSize = 26.sp,
                                    color = CelestialGold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Ruler Graha: ${v.rashi.rulingGraha.sanskritName} (${v.rashi.rulingGraha.englishName})",
                            fontFamily = CinzelFontFamily,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 12.sp,
                            color = CelestialGold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Nature: ${v.rashi.nature}",
                            style = MaterialTheme.typography.bodySmall,
                            color = StarDust
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Spiritual Sadhana: ${v.rashi.spiritualPath}",
                            style = MaterialTheme.typography.bodySmall,
                            color = GoldGlow
                        )
                    }
                }
            }
        }

        // Janma Nakshatra Card
        item {
            vedic?.let { v ->
                CosmicCard(borderColor = MysticAmethyst) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Flare,
                                    contentDescription = null,
                                    tint = MysticAmethyst,
                                    modifier = Modifier.size(20.dp)
                                )
                                Text(
                                    text = "Janma Nakshatra (Lunar Mansion)",
                                    fontFamily = CinzelFontFamily,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = PaleGold
                                )
                            }
                            CosmicPill(text = "Star #${v.nakshatra.number}", color = RadiantCyan)
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = v.nakshatra.name,
                            fontFamily = CinzelFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = PaleGold
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Ruled by: ${v.nakshatra.rulingGraha.sanskritName} • Deity: ${v.nakshatra.deity}",
                            style = MaterialTheme.typography.bodySmall,
                            color = StarlightLavender
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp),
                            color = SurfaceVariantCosmic.copy(alpha = 0.5f),
                            border = BorderStroke(0.5.dp, BorderCelestial)
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text(
                                    text = "Nakshatra Shakti (Divine Power):",
                                    fontFamily = CinzelFontFamily,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.sp,
                                    color = CelestialGold
                                )
                                Text(
                                    text = v.nakshatra.shakti,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = StarDust
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Symbol: ${v.nakshatra.symbol} • Qualities: ${v.nakshatra.qualities}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = TextMutedCosmic
                                )
                            }
                        }
                    }
                }
            }
        }

        // Ank Jyotish (Vedic Numerology: Moolank, Bhagyank, Namank)
        item {
            vedic?.let { v ->
                val num = v.numerology
                CosmicCard {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text(
                            text = "Ank Jyotish (Vedic Numerology)",
                            fontFamily = CinzelFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = CelestialGold
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            VedicNumberPill(
                                title = "Moolank (Root)",
                                value = num.moolank,
                                graha = num.moolankGraha.sanskritName,
                                subtitle = "Psychic driver",
                                modifier = Modifier.weight(1f)
                            )
                            VedicNumberPill(
                                title = "Bhagyank (Destiny)",
                                value = num.bhagyank,
                                graha = num.bhagyankGraha.sanskritName,
                                subtitle = "Life path karma",
                                modifier = Modifier.weight(1f)
                            )
                            VedicNumberPill(
                                title = "Namank (Name)",
                                value = num.namank,
                                graha = num.namankGraha.sanskritName,
                                subtitle = "Chaldean code",
                                modifier = Modifier.weight(1f)
                            )
                        }

                        // Auspicious details
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp),
                            color = SurfaceVariantCosmic.copy(alpha = 0.5f),
                            border = BorderStroke(0.5.dp, BorderCelestial)
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text(
                                    text = "Auspicious Vedic Days: ${num.luckyVedicDays.joinToString(" • ")}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = PaleGold,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "Friendly Numbers: ${num.friendlyNumbers.joinToString(", ")} | Inimical: ${if (num.enemyNumbers.isEmpty()) "None (Universal friend)" else num.enemyNumbers.joinToString(", ")}",
                                    style = MaterialTheme.typography.bodySmall,
                                    fontSize = 11.sp,
                                    color = StarlightLavender
                                )
                            }
                        }
                    }
                }
            }
        }

        // Navagraha Upayas (Remedies, Mantras & Gemstones)
        item {
            vedic?.let { v ->
                val num = v.numerology
                CosmicCard(borderColor = RadiantCyan.copy(alpha = 0.7f)) {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Shield,
                                contentDescription = null,
                                tint = RadiantCyan,
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = "Navagraha Upayas (Sacred Remedies)",
                                fontFamily = CinzelFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = PaleGold
                            )
                        }

                        // Chanting Mantra Card
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = SurfaceVariantCosmic,
                            border = BorderStroke(1.dp, CelestialGold.copy(alpha = 0.5f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(
                                    text = "Primary Graha Japa Mantra",
                                    fontFamily = CinzelFontFamily,
                                    fontSize = 11.sp,
                                    color = CelestialGold,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "\"${num.primaryRemedyMantra}\"",
                                    fontFamily = CinzelFontFamily,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = PaleGold
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Chant 108 times at sunrise on ${num.moolankGraha.dayOfWeek}s to strengthen positive karma.",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = StarDust
                                )
                            }
                        }

                        // Gemstone & Dasha
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Surface(
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(10.dp),
                                color = SurfaceVariantCosmic.copy(alpha = 0.5f),
                                border = BorderStroke(0.5.dp, BorderCelestial)
                            ) {
                                Column(modifier = Modifier.padding(10.dp)) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Diamond,
                                            contentDescription = null,
                                            tint = RadiantCyan,
                                            modifier = Modifier.size(14.dp)
                                        )
                                        Text(
                                            text = "Auspicious Ratna",
                                            fontFamily = CinzelFontFamily,
                                            fontSize = 10.sp,
                                            color = RadiantCyan,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = num.recommendedGemstone,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = PaleGold,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }

                            Surface(
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(10.dp),
                                color = SurfaceVariantCosmic.copy(alpha = 0.5f),
                                border = BorderStroke(0.5.dp, BorderCelestial)
                            ) {
                                Column(modifier = Modifier.padding(10.dp)) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.SelfImprovement,
                                            contentDescription = null,
                                            tint = GoldGlow,
                                            modifier = Modifier.size(14.dp)
                                        )
                                        Text(
                                            text = "Active Dasha",
                                            fontFamily = CinzelFontFamily,
                                            fontSize = 10.sp,
                                            color = GoldGlow,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = v.dashaPeriod,
                                        style = MaterialTheme.typography.bodySmall,
                                        fontSize = 11.sp,
                                        color = PaleGold
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Consult Acharya Jyotish Rishi Action Cards
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "Consult Acharya Jyotish AI Chatbot",
                    fontFamily = CinzelFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = PaleGold
                )

                Button(
                    onClick = {
                        val rashi = vedic?.rashi?.sanskritName ?: "Mesha"
                        val nakshatra = vedic?.nakshatra?.name ?: "Ashwini"
                        viewModel.openVedicChatWithQuery("Pranam Acharya. Please analyze my Vedic Janma Rashi ($rashi) and Nakshatra ($nakshatra). What karmic blessings and obstacles do the Grahas indicate?")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("vedic_chat_rashi_button"),
                    colors = ButtonDefaults.buttonColors(containerColor = CelestialGold),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ChatBubble,
                        contentDescription = null,
                        tint = VoidBlack,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Ask About My Rashi & Nakshatra Karma",
                        color = VoidBlack,
                        fontFamily = CinzelFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }

                OutlinedButton(
                    onClick = {
                        val moolank = vedic?.numerology?.moolank ?: 1
                        val bhagyank = vedic?.numerology?.bhagyank ?: 1
                        viewModel.openVedicChatWithQuery("Namaste. In Vedic Ank Jyotish, my Moolank is $moolank and Bhagyank is $bhagyank. What specific Upayas (mantras, donations, gemstones) should I perform for success?")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("vedic_chat_remedies_button"),
                    border = BorderStroke(1.dp, MysticAmethyst),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = null,
                        tint = MysticAmethyst,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Request Personalized Vedic Remedies (Upayas)",
                        color = StarlightLavender,
                        fontFamily = CinzelFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun VedicNumberPill(
    title: String,
    value: Int,
    graha: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceVariantCosmic),
        border = BorderStroke(1.dp, BorderCelestial)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                fontFamily = CinzelFontFamily,
                fontSize = 10.sp,
                color = TextMutedCosmic,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(4.dp))
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(SurfaceCosmic, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = value.toString(),
                    fontFamily = CinzelFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = CelestialGold
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = graha,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = PaleGold,
                textAlign = TextAlign.Center
            )
            Text(
                text = subtitle,
                fontSize = 9.sp,
                color = StarlightLavender,
                textAlign = TextAlign.Center
            )
        }
    }
}
