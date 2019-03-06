package net.robocode2.gui.model

data class GameSetup(
        val gameType: String,
        val arenaWidth: Int,
        val isArenaWidthLocked: Boolean,
        val arenaHeight: Int,
        val isArenaHeightLocked: Boolean,
        val minNumberOfParticipants: Int,
        val isMinNumberOfParticipantsLocked: Boolean,
//        val maxNumberOfParticipants: Int?, // FIXME
        val isMaxNumberOfParticipantsLocked: Boolean,
        val numberOfRounds: Int,
        val isNumberOfRoundsLocked: Boolean,
        val gunCoolingRate: Double,
        val isGunCoolingRateLocked: Boolean,
        val inactivityTurns: Int,
        val isInactivityTurnsLocked: Boolean,
        val turnTimeout: Int,
        val isTurnTimeoutLocked: Boolean,
        val readyTimeout: Int,
        val isReadyTimeoutLocked: Boolean,
        val delayedObserverTurns: Int,
        val isDelayedObserverTurnsLocked: Boolean
) : Message(type = MessageType.GAME_SETUP.type)