package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.ui.AstroOracleViewModel
import com.example.ui.theme.BorderCelestial
import com.example.ui.theme.CelestialGold
import com.example.ui.theme.CinzelFontFamily
import com.example.ui.theme.PaleGold
import com.example.ui.theme.SurfaceCosmic
import com.example.ui.theme.SurfaceVariantCosmic
import com.example.ui.theme.TextMutedCosmic
import com.example.ui.theme.VoidBlack

@Composable
fun ProfileEditDialog(
    viewModel: AstroOracleViewModel,
    onDismiss: () -> Unit
) {
    val currentProfile = viewModel.userProfile.value

    var name by remember { mutableStateOf(currentProfile?.name ?: "") }
    var birthDate by remember { mutableStateOf(currentProfile?.birthDate ?: "1998-07-15") }
    var birthTime by remember { mutableStateOf(currentProfile?.birthTime ?: "") }
    var birthCity by remember { mutableStateOf(currentProfile?.birthCity ?: "") }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("profile_edit_dialog"),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceCosmic),
            border = BorderStroke(1.5.dp, CelestialGold)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Text(
                    text = "Cosmic Birth Coordinates",
                    fontFamily = CinzelFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = PaleGold
                )

                Text(
                    text = "Your natal coordinates calibrate your Zodiac alignment and Pythagorean numerology numbers.",
                    fontSize = 11.sp,
                    color = TextMutedCosmic
                )

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Your Name / Seeker Title", color = TextMutedCosmic) },
                    placeholder = { Text("e.g. Celeste", color = TextMutedCosmic.copy(alpha = 0.5f)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_profile_name"),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = CelestialGold,
                        unfocusedBorderColor = BorderCelestial,
                        focusedTextColor = PaleGold,
                        unfocusedTextColor = PaleGold,
                        focusedContainerColor = SurfaceVariantCosmic,
                        unfocusedContainerColor = SurfaceVariantCosmic
                    ),
                    shape = RoundedCornerShape(10.dp)
                )

                OutlinedTextField(
                    value = birthDate,
                    onValueChange = { birthDate = it },
                    label = { Text("Birthdate (YYYY-MM-DD)", color = TextMutedCosmic) },
                    placeholder = { Text("1998-07-15", color = TextMutedCosmic.copy(alpha = 0.5f)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_profile_birthdate"),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = CelestialGold,
                        unfocusedBorderColor = BorderCelestial,
                        focusedTextColor = PaleGold,
                        unfocusedTextColor = PaleGold,
                        focusedContainerColor = SurfaceVariantCosmic,
                        unfocusedContainerColor = SurfaceVariantCosmic
                    ),
                    shape = RoundedCornerShape(10.dp)
                )

                OutlinedTextField(
                    value = birthTime,
                    onValueChange = { birthTime = it },
                    label = { Text("Birth Time (optional, e.g. 14:30)", color = TextMutedCosmic) },
                    placeholder = { Text("14:30", color = TextMutedCosmic.copy(alpha = 0.5f)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_profile_birthtime"),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = CelestialGold,
                        unfocusedBorderColor = BorderCelestial,
                        focusedTextColor = PaleGold,
                        unfocusedTextColor = PaleGold,
                        focusedContainerColor = SurfaceVariantCosmic,
                        unfocusedContainerColor = SurfaceVariantCosmic
                    ),
                    shape = RoundedCornerShape(10.dp)
                )

                OutlinedTextField(
                    value = birthCity,
                    onValueChange = { birthCity = it },
                    label = { Text("Birth Place / City (optional)", color = TextMutedCosmic) },
                    placeholder = { Text("e.g. Athens, Greece", color = TextMutedCosmic.copy(alpha = 0.5f)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_profile_birthcity"),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = CelestialGold,
                        unfocusedBorderColor = BorderCelestial,
                        focusedTextColor = PaleGold,
                        unfocusedTextColor = PaleGold,
                        focusedContainerColor = SurfaceVariantCosmic,
                        unfocusedContainerColor = SurfaceVariantCosmic
                    ),
                    shape = RoundedCornerShape(10.dp)
                )

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        border = BorderStroke(1.dp, BorderCelestial),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(text = "Cancel", color = TextMutedCosmic)
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Button(
                        onClick = {
                            viewModel.saveProfile(name, birthDate, birthTime, birthCity)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = CelestialGold),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.testTag("save_profile_button")
                    ) {
                        Text(
                            text = "Align & Save",
                            fontFamily = CinzelFontFamily,
                            fontWeight = FontWeight.Bold,
                            color = VoidBlack
                        )
                    }
                }
            }
        }
    }
}
