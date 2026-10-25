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
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
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
import com.example.data.model.NumerologyNumberInfo
import com.example.ui.AppTab
import com.example.ui.AstroOracleViewModel
import com.example.ui.components.CosmicCard
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
fun NumerologyScreen(
    viewModel: AstroOracleViewModel,
    modifier: Modifier = Modifier
) {
    val profile by viewModel.userProfile.collectAsState()
    val partnerName by viewModel.partnerName.collectAsState()
    val partnerBirthDate by viewModel.partnerBirthDate.collectAsState()
    val compatibilityResult by viewModel.compatibilityResult.collectAsState()

    val num = profile?.numerologyProfile

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("numerology_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            SectionHeader(
                title = "Sacred Numerology",
                subtitle = "The mathematical code of destiny decoded through Pythagorean geometry",
                icon = "🔢"
            )
        }

        // Life Path Highlight Card
        item {
            num?.let { n ->
                CosmicCard(borderColor = CelestialGold) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "Life Path Number",
                                    fontFamily = CinzelFontFamily,
                                    fontSize = 12.sp,
                                    color = TextMutedCosmic
                                )
                                Text(
                                    text = n.lifePathInfo.title,
                                    fontFamily = CinzelFontFamily,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 17.sp,
                                    color = PaleGold
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .size(54.dp)
                                    .background(SurfaceVariantCosmic, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = n.lifePathNumber.toString(),
                                    fontFamily = CinzelFontFamily,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 24.sp,
                                    color = CelestialGold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = n.lifePathInfo.description,
                            style = MaterialTheme.typography.bodyMedium,
                            color = StarDust,
                            lineHeight = 20.sp
                        )

                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Surface(
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(8.dp),
                                color = SurfaceVariantCosmic.copy(alpha = 0.5f),
                                border = BorderStroke(0.5.dp, BorderCelestial)
                            ) {
                                Column(modifier = Modifier.padding(8.dp)) {
                                    Text(
                                        text = "Sacred Gift",
                                        fontFamily = CinzelFontFamily,
                                        fontSize = 10.sp,
                                        color = CelestialGold,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = n.lifePathInfo.gift,
                                        style = MaterialTheme.typography.bodySmall,
                                        fontSize = 11.sp,
                                        color = PaleGold
                                    )
                                }
                            }

                            Surface(
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(8.dp),
                                color = SurfaceVariantCosmic.copy(alpha = 0.5f),
                                border = BorderStroke(0.5.dp, BorderCelestial)
                            ) {
                                Column(modifier = Modifier.padding(8.dp)) {
                                    Text(
                                        text = "Shadow Aspect",
                                        fontFamily = CinzelFontFamily,
                                        fontSize = 10.sp,
                                        color = MysticAmethyst,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = n.lifePathInfo.shadow,
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
        }

        // Secondary Numbers Grid
        item {
            num?.let { n ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Expression / Destiny Number
                    CoreNumberCard(
                        title = "Destiny Number",
                        number = n.destinyNumber,
                        subtitle = n.destinyInfo.title,
                        modifier = Modifier.weight(1f)
                    )

                    // Soul Urge Number
                    CoreNumberCard(
                        title = "Soul Urge",
                        number = n.soulUrgeNumber,
                        subtitle = n.soulUrgeInfo.title,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // Personal Year Number
        item {
            num?.let { n ->
                CosmicCard(borderColor = RadiantCyan.copy(alpha = 0.5f)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Personal Year Vibration (2026)",
                                fontFamily = CinzelFontFamily,
                                fontSize = 11.sp,
                                color = TextMutedCosmic
                            )
                            Text(
                                text = n.personalYearInfo.title,
                                fontFamily = CinzelFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = RadiantCyan
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = n.personalYearInfo.description,
                                style = MaterialTheme.typography.bodySmall,
                                color = StarDust
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .background(SurfaceVariantCosmic, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = n.personalYearNumber.toString(),
                                fontFamily = CinzelFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                color = RadiantCyan
                            )
                        }
                    }
                }
            }
        }

        // Compatibility Calculator Section
        item {
            CosmicCard(borderColor = MysticAmethyst) {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = null,
                            tint = MysticAmethyst,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "Life Path Compatibility Matrix",
                            fontFamily = CinzelFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = PaleGold
                        )
                    }

                    Text(
                        text = "Enter a partner, friend, or collaborator's details to evaluate your sacred numerical synergy.",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextMutedCosmic
                    )

                    OutlinedTextField(
                        value = partnerName,
                        onValueChange = { viewModel.setPartnerName(it) },
                        label = { Text("Partner's Name", color = TextMutedCosmic) },
                        placeholder = { Text("e.g. Julian", color = TextMutedCosmic.copy(alpha = 0.5f)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("partner_name_input"),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MysticAmethyst,
                            unfocusedBorderColor = BorderCelestial,
                            focusedTextColor = PaleGold,
                            unfocusedTextColor = PaleGold,
                            focusedContainerColor = SurfaceVariantCosmic,
                            unfocusedContainerColor = SurfaceVariantCosmic
                        ),
                        shape = RoundedCornerShape(10.dp)
                    )

                    OutlinedTextField(
                        value = partnerBirthDate,
                        onValueChange = { viewModel.setPartnerBirthDate(it) },
                        label = { Text("Partner's Birthdate (YYYY-MM-DD)", color = TextMutedCosmic) },
                        placeholder = { Text("1996-08-15", color = TextMutedCosmic.copy(alpha = 0.5f)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("partner_birthdate_input"),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MysticAmethyst,
                            unfocusedBorderColor = BorderCelestial,
                            focusedTextColor = PaleGold,
                            unfocusedTextColor = PaleGold,
                            focusedContainerColor = SurfaceVariantCosmic,
                            unfocusedContainerColor = SurfaceVariantCosmic
                        ),
                        shape = RoundedCornerShape(10.dp)
                    )

                    Button(
                        onClick = { viewModel.calculatePartnerCompatibility() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("calculate_compatibility_button"),
                        colors = ButtonDefaults.buttonColors(containerColor = MysticAmethyst),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "Calculate Celestial Synergy",
                            fontFamily = CinzelFontFamily,
                            fontWeight = FontWeight.Bold,
                            color = PaleGold
                        )
                    }

                    // Compatibility Result Display
                    compatibilityResult?.let { (score, description) ->
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = SurfaceVariantCosmic,
                            border = BorderStroke(1.dp, CelestialGold.copy(alpha = 0.5f)),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp)
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Synergy Score",
                                        fontFamily = CinzelFontFamily,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        color = PaleGold
                                    )
                                    Text(
                                        text = "$score%",
                                        fontFamily = CinzelFontFamily,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 20.sp,
                                        color = CelestialGold
                                    )
                                }

                                Spacer(modifier = Modifier.height(8.dp))
                                LinearProgressIndicator(
                                    progress = { score / 100f },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(6.dp),
                                    color = CelestialGold,
                                    trackColor = BorderCelestial,
                                )

                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = description,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = StarDust,
                                    lineHeight = 20.sp
                                )

                                Spacer(modifier = Modifier.height(12.dp))
                                Button(
                                    onClick = {
                                        val pName = partnerName.ifBlank { "Partner" }
                                        viewModel.setChatInput("What does the numerological connection between my Life Path (${num?.lifePathNumber}) and $pName's birthdate ($partnerBirthDate) reveal about our relationship?")
                                        viewModel.selectTab(AppTab.ORACLE)
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .testTag("ask_oracle_about_compatibility_button"),
                                    colors = ButtonDefaults.buttonColors(containerColor = CelestialGold)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.ChatBubble,
                                        contentDescription = null,
                                        tint = VoidBlack,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Ask Oracle About Our Connection",
                                        color = VoidBlack,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                        fontFamily = CinzelFontFamily
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun CoreNumberCard(
    title: String,
    number: Int,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceCosmic),
        border = BorderStroke(1.dp, BorderCelestial)
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                fontFamily = CinzelFontFamily,
                fontSize = 11.sp,
                color = TextMutedCosmic,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(6.dp))
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .background(SurfaceVariantCosmic, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = number.toString(),
                    fontFamily = CinzelFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = PaleGold
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                fontSize = 11.sp,
                color = StarlightLavender,
                textAlign = TextAlign.Center,
                maxLines = 2
            )
        }
    }
}
