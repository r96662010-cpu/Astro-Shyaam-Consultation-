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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ChatMessage
import com.example.data.model.ChatSender
import com.example.ui.AstroOracleViewModel
import com.example.ui.theme.BorderCelestial
import com.example.ui.theme.CelestialGold
import com.example.ui.theme.CinzelFontFamily
import com.example.ui.theme.DeepSpaceNavy
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
fun OracleChatScreen(
    viewModel: AstroOracleViewModel,
    modifier: Modifier = Modifier
) {
    val messages by viewModel.chatMessages.collectAsState()
    val input by viewModel.chatInput.collectAsState()
    val isGenerating by viewModel.isChatGenerating.collectAsState()
    val profile by viewModel.userProfile.collectAsState()

    val listState = rememberLazyListState()

    // Auto-scroll when new messages arrive or when streaming
    LaunchedEffect(messages.size, messages.lastOrNull()?.text?.length) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    val isVedicMode by viewModel.isVedicChatMode.collectAsState()

    val suggestionChips = if (isVedicMode) {
        listOf(
            "Analyze my Janma Rashi & Nakshatra karma",
            "What do my Moolank & Bhagyank reveal?",
            "Vedic remedies (Upayas) and mantras for my chart",
            "Am I in Shani Sade Sati or difficult Mahadasha?",
            "Which gemstone (Ratna) is auspicious for me?",
            "Vedic compatibility (Kundali Guna Milan) guidance"
        )
    } else {
        listOf(
            "What does my Life Path ${profile?.numerologyProfile?.lifePathNumber ?: ""} mean for my career?",
            "Love guidance based on my ${profile?.zodiacSign?.englishName ?: "Sun"} sign",
            "How can I align with today's moon transit?",
            "Interpret a recurring dream I had",
            "Which crystal harmonizes my energy today?",
            "Spiritual advice for a major life decision"
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("oracle_chat_screen")
    ) {
        // Chat Top Bar with Vedic Toggle
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = SurfaceCosmic,
            border = BorderStroke(1.dp, BorderCelestial)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(38.dp)
                                .clip(CircleShape)
                                .background(SurfaceVariantCosmic),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = if (isVedicMode) "🕉️" else "✨", fontSize = 18.sp)
                        }
                        Column {
                            Text(
                                text = if (isVedicMode) "Acharya Jyotish (Vedic AI)" else "Mystic Oracle AI",
                                fontFamily = CinzelFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = if (isVedicMode) CelestialGold else PaleGold
                            )
                            Text(
                                text = if (isGenerating) "Channelling cosmic ether..." else if (isVedicMode) "Sidereal Parashara & Ank Jyotish" else "Western Astrology, Tarot & Numerology",
                                style = MaterialTheme.typography.bodySmall,
                                fontSize = 11.sp,
                                color = if (isGenerating) CelestialGold else TextMutedCosmic
                            )
                        }
                    }

                    IconButton(
                        onClick = { viewModel.clearChat() },
                        modifier = Modifier.testTag("clear_chat_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Clear Chat History",
                            tint = TextMutedCosmic
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Mode Selector Pills
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = if (!isVedicMode) CelestialGold else SurfaceVariantCosmic,
                        border = BorderStroke(1.dp, if (!isVedicMode) GoldGlow else BorderCelestial),
                        modifier = Modifier
                            .weight(1f)
                            .clickable { viewModel.setVedicChatMode(false) }
                            .testTag("toggle_western_mode")
                    ) {
                        Text(
                            text = "🔮 Western & Tarot",
                            fontFamily = CinzelFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp,
                            color = if (!isVedicMode) VoidBlack else PaleGold,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                            modifier = Modifier.padding(vertical = 6.dp)
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = if (isVedicMode) CelestialGold else SurfaceVariantCosmic,
                        border = BorderStroke(1.dp, if (isVedicMode) GoldGlow else BorderCelestial),
                        modifier = Modifier
                            .weight(1f)
                            .clickable { viewModel.setVedicChatMode(true) }
                            .testTag("toggle_vedic_mode")
                    ) {
                        Text(
                            text = "🕉️ Vedic Jyotish",
                            fontFamily = CinzelFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp,
                            color = if (isVedicMode) VoidBlack else PaleGold,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                            modifier = Modifier.padding(vertical = 6.dp)
                        )
                    }
                }
            }
        }

        // Suggestion Chips Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(DeepSpaceNavy)
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            suggestionChips.forEach { suggestion ->
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = SurfaceVariantCosmic,
                    border = BorderStroke(0.8.dp, BorderCelestial),
                    modifier = Modifier.clickable {
                        viewModel.sendChatMessage(suggestion)
                    }
                ) {
                    Text(
                        text = suggestion,
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 11.sp,
                        color = StarlightLavender,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                    )
                }
            }
        }

        // Chat Message List
        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(messages, key = { it.id }) { message ->
                ChatMessageItem(message = message)
            }

            if (isGenerating && messages.lastOrNull()?.isStreaming != true) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = SurfaceCosmic,
                            border = BorderStroke(1.dp, BorderCelestial),
                            modifier = Modifier.padding(vertical = 4.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(14.dp),
                                    color = CelestialGold,
                                    strokeWidth = 2.dp
                                )
                                Text(
                                    text = "Listening to the stars...",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = TextMutedCosmic
                                )
                            }
                        }
                    }
                }
            }
        }

        // Bottom Message Input Row
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = SurfaceCosmic,
            border = BorderStroke(1.dp, BorderCelestial)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = input,
                    onValueChange = { viewModel.setChatInput(it) },
                    placeholder = {
                        Text(
                            text = "Ask about astrology, tarot, numbers...",
                            color = TextMutedCosmic.copy(alpha = 0.5f),
                            fontSize = 13.sp
                        )
                    },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("chat_input_field"),
                    shape = RoundedCornerShape(20.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = CelestialGold,
                        unfocusedBorderColor = BorderCelestial,
                        focusedTextColor = PaleGold,
                        unfocusedTextColor = PaleGold,
                        focusedContainerColor = SurfaceVariantCosmic,
                        unfocusedContainerColor = SurfaceVariantCosmic
                    ),
                    maxLines = 4
                )

                IconButton(
                    onClick = { viewModel.sendChatMessage() },
                    enabled = input.isNotBlank() && !isGenerating,
                    modifier = Modifier
                        .size(46.dp)
                        .background(
                            if (input.isNotBlank() && !isGenerating) CelestialGold else SurfaceVariantCosmic,
                            CircleShape
                        )
                        .testTag("send_chat_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Send,
                        contentDescription = "Send Message",
                        tint = if (input.isNotBlank() && !isGenerating) VoidBlack else TextMutedCosmic,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun ChatMessageItem(message: ChatMessage) {
    val isUser = message.sender == ChatSender.USER

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start
    ) {
        if (!isUser) {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(SurfaceVariantCosmic),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "🔮", fontSize = 14.sp)
            }
            Spacer(modifier = Modifier.width(8.dp))
        }

        Surface(
            shape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
                bottomStart = if (isUser) 16.dp else 4.dp,
                bottomEnd = if (isUser) 4.dp else 16.dp
            ),
            color = if (isUser) MysticAmethyst.copy(alpha = 0.25f) else SurfaceCosmic,
            border = BorderStroke(
                1.dp,
                if (isUser) MysticAmethyst.copy(alpha = 0.6f) else BorderCelestial
            ),
            modifier = Modifier.fillMaxWidth(if (isUser) 0.82f else 0.88f)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                if (!isUser) {
                    Text(
                        text = "Mystic Oracle",
                        fontFamily = CinzelFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp,
                        color = CelestialGold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }

                Text(
                    text = message.text,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (isUser) PaleGold else StarDust,
                    lineHeight = 21.sp
                )

                if (message.isStreaming) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "● ● ●",
                        fontSize = 10.sp,
                        color = CelestialGold
                    )
                }
            }
        }
    }
}
