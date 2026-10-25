package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.ai.OracleAiService
import com.example.data.local.AstroDatabase
import com.example.data.local.ChatMessageEntity
import com.example.data.local.DailyTarotEntity
import com.example.data.local.SavedReadingEntity
import com.example.data.local.UserProfileEntity
import com.example.data.model.ChatMessage
import com.example.data.model.ChatSender
import com.example.data.model.DrawnCard
import com.example.data.model.SpreadType
import com.example.data.model.TarotCard
import com.example.data.model.UserProfile
import com.example.data.model.ZodiacSign
import com.example.util.AstrologyCalculator
import com.example.util.DailyCosmicForecast
import com.example.util.NumerologyCalculator
import com.example.util.TarotDeck
import com.example.util.VedicCalculator
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.UUID

enum class AppTab(val title: String, val iconResName: String) {
    DAILY("Daily Sky", "ic_daily"),
    TAROT("Tarot Sanctum", "ic_tarot"),
    NUMEROLOGY("Numerology", "ic_numerology"),
    VEDIC("Vedic Jyotish", "ic_vedic"),
    ORACLE("Mystic Chat", "ic_chat"),
    PROFILE("Cosmic Chart", "ic_profile")
}

sealed interface AiReadingState {
    data object Idle : AiReadingState
    data object Loading : AiReadingState
    data class Success(val text: String) : AiReadingState
    data class Error(val message: String) : AiReadingState
}

class AstroOracleViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AstroDatabase.getInstance(application)
    private val dao = db.astroDao()
    private val aiService = OracleAiService()

    // Current Navigation Tab
    private val _currentTab = MutableStateFlow(AppTab.DAILY)
    val currentTab: StateFlow<AppTab> = _currentTab.asStateFlow()

    fun selectTab(tab: AppTab) {
        _currentTab.value = tab
    }

    // User Profile
    private val _userProfile = MutableStateFlow<UserProfile?>(null)
    val userProfile: StateFlow<UserProfile?> = _userProfile.asStateFlow()

    // Show Profile Edit Sheet
    private val _showProfileDialog = MutableStateFlow(false)
    val showProfileDialog: StateFlow<Boolean> = _showProfileDialog.asStateFlow()

    fun setShowProfileDialog(show: Boolean) {
        _showProfileDialog.value = show
    }

    // Daily Forecast
    private val _dailyForecast = MutableStateFlow<DailyCosmicForecast?>(null)
    val dailyForecast: StateFlow<DailyCosmicForecast?> = _dailyForecast.asStateFlow()

    // Daily Card Draw
    private val _dailyCard = MutableStateFlow<DrawnCard?>(null)
    val dailyCard: StateFlow<DrawnCard?> = _dailyCard.asStateFlow()

    private val _isDailyCardFlipped = MutableStateFlow(false)
    val isDailyCardFlipped: StateFlow<Boolean> = _isDailyCardFlipped.asStateFlow()

    fun flipDailyCard() {
        _isDailyCardFlipped.value = true
    }

    // Tarot Spread Screen State
    private val _selectedSpread = MutableStateFlow(SpreadType.THREE_CARD_TIMELINE)
    val selectedSpread: StateFlow<SpreadType> = _selectedSpread.asStateFlow()

    private val _spreadQuestion = MutableStateFlow("")
    val spreadQuestion: StateFlow<String> = _spreadQuestion.asStateFlow()

    fun setSpreadQuestion(q: String) {
        _spreadQuestion.value = q
    }

    private val _drawnSpreadCards = MutableStateFlow<List<DrawnCard>>(emptyList())
    val drawnSpreadCards: StateFlow<List<DrawnCard>> = _drawnSpreadCards.asStateFlow()

    private val _flippedCardsIndices = MutableStateFlow<Set<Int>>(emptySet())
    val flippedCardsIndices: StateFlow<Set<Int>> = _flippedCardsIndices.asStateFlow()

    private val _aiReadingState = MutableStateFlow<AiReadingState>(AiReadingState.Idle)
    val aiReadingState: StateFlow<AiReadingState> = _aiReadingState.asStateFlow()

    val savedReadings: StateFlow<List<SavedReadingEntity>> = dao.getAllSavedReadings()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Numerology Compatibility State
    private val _partnerName = MutableStateFlow("")
    val partnerName: StateFlow<String> = _partnerName.asStateFlow()

    fun setPartnerName(name: String) {
        _partnerName.value = name
    }

    private val _partnerBirthDate = MutableStateFlow("1996-08-15")
    val partnerBirthDate: StateFlow<String> = _partnerBirthDate.asStateFlow()

    fun setPartnerBirthDate(date: String) {
        _partnerBirthDate.value = date
    }

    private val _compatibilityResult = MutableStateFlow<Pair<Int, String>?>(null)
    val compatibilityResult: StateFlow<Pair<Int, String>?> = _compatibilityResult.asStateFlow()

    // Oracle Chat State
    private val _chatMessages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val chatMessages: StateFlow<List<ChatMessage>> = _chatMessages.asStateFlow()

    private val _chatInput = MutableStateFlow("")
    val chatInput: StateFlow<String> = _chatInput.asStateFlow()

    fun setChatInput(text: String) {
        _chatInput.value = text
    }

    private val _isChatGenerating = MutableStateFlow(false)
    val isChatGenerating: StateFlow<Boolean> = _isChatGenerating.asStateFlow()

    private val _isVedicChatMode = MutableStateFlow(false)
    val isVedicChatMode: StateFlow<Boolean> = _isVedicChatMode.asStateFlow()

    fun setVedicChatMode(enabled: Boolean) {
        _isVedicChatMode.value = enabled
    }

    fun openVedicChatWithQuery(prompt: String) {
        _isVedicChatMode.value = true
        _chatInput.value = prompt
        selectTab(AppTab.ORACLE)
    }

    init {
        loadUserProfile()
        loadChatHistory()
    }

    private fun loadUserProfile() {
        viewModelScope.launch {
            dao.getUserProfile().collect { entity ->
                if (entity != null) {
                    val parsed = AstrologyCalculator.parseBirthDate(entity.birthDate)
                    if (parsed != null) {
                        val (y, m, d) = parsed
                        val sign = ZodiacSign.fromMonthDay(m, d)
                        val numProfile = NumerologyCalculator.calculateProfile(entity.name, y, m, d)
                        val vedicProfile = VedicCalculator.calculateVedicProfile(entity.name, y, m, d)
                        val profile = UserProfile(
                            name = entity.name,
                            birthDate = entity.birthDate,
                            birthTime = entity.birthTime,
                            birthCity = entity.birthCity,
                            zodiacSign = sign,
                            numerologyProfile = numProfile,
                            vedicProfile = vedicProfile
                        )
                        _userProfile.value = profile
                        _dailyForecast.value = AstrologyCalculator.getDailyForecast(sign)
                        loadDailyCard(profile)
                    }
                } else {
                    // Seed default profile for first-time delightful experience
                    val defaultBirthDate = "1998-07-15"
                    val parsed = AstrologyCalculator.parseBirthDate(defaultBirthDate)!!
                    val sign = ZodiacSign.fromMonthDay(parsed.second, parsed.third)
                    val numProfile = NumerologyCalculator.calculateProfile("Celeste", parsed.first, parsed.second, parsed.third)
                    val vedicProfile = VedicCalculator.calculateVedicProfile("Celeste", parsed.first, parsed.second, parsed.third)
                    val defaultProfile = UserProfile(
                        name = "Celeste",
                        birthDate = defaultBirthDate,
                        birthTime = "12:00",
                        birthCity = "San Francisco",
                        zodiacSign = sign,
                        numerologyProfile = numProfile,
                        vedicProfile = vedicProfile
                    )
                    _userProfile.value = defaultProfile
                    _dailyForecast.value = AstrologyCalculator.getDailyForecast(sign)
                    loadDailyCard(defaultProfile)
                }
            }
        }
    }

    private fun loadDailyCard(profile: UserProfile) {
        viewModelScope.launch {
            val todayStr = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            val existing = dao.getDailyTarot(todayStr)
            if (existing != null) {
                val card = TarotDeck.majorArcana.find { it.id == existing.cardId } ?: TarotDeck.majorArcana.first()
                _dailyCard.value = DrawnCard(
                    card = card,
                    isReversed = existing.isReversed,
                    positionName = "Card of the Day",
                    positionDescription = "Your daily guiding celestial energy"
                )
            } else {
                // Draw new daily card deterministically or freshly
                val drawn = TarotDeck.getRandomCards(1).first()
                val entity = DailyTarotEntity(
                    dateString = todayStr,
                    cardId = drawn.first.id,
                    isReversed = drawn.second
                )
                dao.saveDailyTarot(entity)
                _dailyCard.value = DrawnCard(
                    card = drawn.first,
                    isReversed = drawn.second,
                    positionName = "Card of the Day",
                    positionDescription = "Your daily guiding celestial energy"
                )
            }
        }
    }

    fun saveProfile(name: String, birthDate: String, birthTime: String, birthCity: String) {
        viewModelScope.launch {
            val entity = UserProfileEntity(
                name = name.ifBlank { "Seeker" },
                birthDate = birthDate,
                birthTime = birthTime,
                birthCity = birthCity
            )
            dao.saveUserProfile(entity)
            _showProfileDialog.value = false
        }
    }

    fun setSpreadType(type: SpreadType) {
        _selectedSpread.value = type
        _drawnSpreadCards.value = emptyList()
        _flippedCardsIndices.value = emptySet()
        _aiReadingState.value = AiReadingState.Idle
    }

    fun shuffleAndDrawSpread() {
        val spread = _selectedSpread.value
        val drawn = TarotDeck.getRandomCards(spread.cardCount)
        val list = drawn.mapIndexed { index, (card, isReversed) ->
            DrawnCard(
                card = card,
                isReversed = isReversed,
                positionName = spread.positionNames.getOrElse(index) { "Card ${index + 1}" },
                positionDescription = spread.positionDescriptions.getOrElse(index) { "" }
            )
        }
        _drawnSpreadCards.value = list
        _flippedCardsIndices.value = emptySet()
        _aiReadingState.value = AiReadingState.Idle
    }

    fun flipSpreadCard(index: Int) {
        _flippedCardsIndices.value = _flippedCardsIndices.value + index
    }

    fun flipAllSpreadCards() {
        _flippedCardsIndices.value = _drawnSpreadCards.value.indices.toSet()
    }

    fun requestAiTarotInterpretation() {
        val cards = _drawnSpreadCards.value
        if (cards.isEmpty()) return

        // Automatically flip all cards so user sees them
        flipAllSpreadCards()

        viewModelScope.launch {
            _aiReadingState.value = AiReadingState.Loading
            try {
                val interpretation = aiService.interpretTarotSpread(
                    spreadTitle = _selectedSpread.value.title,
                    question = _spreadQuestion.value,
                    cards = cards,
                    profile = _userProfile.value
                )
                _aiReadingState.value = AiReadingState.Success(interpretation)

                // Save to local Room journal
                val cardDataStr = cards.joinToString(",") { "${it.card.id}:${it.isReversed}" }
                dao.insertReading(
                    SavedReadingEntity(
                        spreadType = _selectedSpread.value.title,
                        question = _spreadQuestion.value,
                        cardData = cardDataStr,
                        aiInterpretation = interpretation
                    )
                )
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _aiReadingState.value = AiReadingState.Error(e.localizedMessage ?: "Interpretation failed.")
            }
        }
    }

    fun calculatePartnerCompatibility() {
        val parsed = AstrologyCalculator.parseBirthDate(_partnerBirthDate.value)
        val userLp = _userProfile.value?.numerologyProfile?.lifePathNumber ?: 7
        if (parsed != null) {
            val partnerLp = NumerologyCalculator.calculateLifePath(parsed.first, parsed.second, parsed.third)
            val result = NumerologyCalculator.calculateCompatibility(userLp, partnerLp)
            _compatibilityResult.value = result
        }
    }

    private fun loadChatHistory() {
        viewModelScope.launch {
            dao.getChatMessages().collect { entities ->
                if (entities.isNotEmpty()) {
                    _chatMessages.value = entities.map {
                        ChatMessage(
                            id = it.id,
                            sender = if (it.sender == "USER") ChatSender.USER else ChatSender.ORACLE,
                            text = it.text,
                            timestamp = it.timestamp
                        )
                    }
                } else {
                    // Seed initial welcome oracle message
                    val welcomeMsg = ChatMessage(
                        id = "welcome",
                        sender = ChatSender.ORACLE,
                        text = "Greetings, radiant seeker. The veil of time and stars parts for you. I am your Mystic Oracle, attuned to the planetary alignments, sacred tarot arcana, and the rhythmic vibrations of Pythagorean numerology.\n\nAsk me of your celestial birth chart, your love connections, career crossroads, or request an in-depth tarot synthesis.",
                        timestamp = System.currentTimeMillis()
                    )
                    _chatMessages.value = listOf(welcomeMsg)
                }
            }
        }
    }

    fun sendChatMessage(textOverride: String? = null) {
        val query = (textOverride ?: _chatInput.value).trim()
        if (query.isBlank() || _isChatGenerating.value) return

        _chatInput.value = ""
        val userMsg = ChatMessage(
            id = UUID.randomUUID().toString(),
            sender = ChatSender.USER,
            text = query,
            timestamp = System.currentTimeMillis()
        )

        // Optimistically add to UI
        val currentList = _chatMessages.value.toMutableList()
        currentList.add(userMsg)
        _chatMessages.value = currentList

        // Persist user message to Room
        viewModelScope.launch {
            dao.insertChatMessage(
                ChatMessageEntity(
                    id = userMsg.id,
                    sender = "USER",
                    text = userMsg.text,
                    timestamp = userMsg.timestamp
                )
            )
        }

        // Oracle streaming response
        val oracleMsgId = UUID.randomUUID().toString()
        val streamingMsg = ChatMessage(
            id = oracleMsgId,
            sender = ChatSender.ORACLE,
            text = "",
            timestamp = System.currentTimeMillis(),
            isStreaming = true
        )
        currentList.add(streamingMsg)
        _chatMessages.value = currentList.toList()
        _isChatGenerating.value = true

        viewModelScope.launch {
            val accumulated = StringBuilder()
            try {
                aiService.streamChatGuidance(
                    userMessage = query,
                    profile = _userProfile.value,
                    recentDrawnCards = _drawnSpreadCards.value.ifEmpty {
                        _dailyCard.value?.let { listOf(it) } ?: emptyList()
                    },
                    isVedicMode = _isVedicChatMode.value
                ).collect { chunk ->
                    accumulated.append(chunk)
                    val updated = _chatMessages.value.map { msg ->
                        if (msg.id == oracleMsgId) {
                            msg.copy(text = accumulated.toString(), isStreaming = true)
                        } else msg
                    }
                    _chatMessages.value = updated
                }

                // Finalize streaming
                val finalMsg = ChatMessage(
                    id = oracleMsgId,
                    sender = ChatSender.ORACLE,
                    text = accumulated.toString().ifBlank { "The stars whisper softly; meditate on your inner truth." },
                    timestamp = System.currentTimeMillis(),
                    isStreaming = false
                )
                _chatMessages.value = _chatMessages.value.map {
                    if (it.id == oracleMsgId) finalMsg else it
                }
                // Persist oracle reply to Room
                dao.insertChatMessage(
                    ChatMessageEntity(
                        id = finalMsg.id,
                        sender = "ORACLE",
                        text = finalMsg.text,
                        timestamp = finalMsg.timestamp
                    )
                )
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                val errorMsg = ChatMessage(
                    id = oracleMsgId,
                    sender = ChatSender.ORACLE,
                    text = "A temporary celestial haze disrupted the channeling: ${e.localizedMessage ?: "Unknown transit error"}",
                    timestamp = System.currentTimeMillis(),
                    isStreaming = false
                )
                _chatMessages.value = _chatMessages.value.map {
                    if (it.id == oracleMsgId) errorMsg else it
                }
            } finally {
                _isChatGenerating.value = false
            }
        }
    }

    fun clearChat() {
        viewModelScope.launch {
            dao.clearChatHistory()
            val welcomeMsg = ChatMessage(
                id = UUID.randomUUID().toString(),
                sender = ChatSender.ORACLE,
                text = "The cosmic sanctuary is renewed. What questions do the stars hold for you today?",
                timestamp = System.currentTimeMillis()
            )
            _chatMessages.value = listOf(welcomeMsg)
        }
    }
}
