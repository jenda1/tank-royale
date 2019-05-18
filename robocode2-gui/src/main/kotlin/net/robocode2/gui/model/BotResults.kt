package net.robocode2.gui.model

data class BotResults(
        val id: Int,
        val name: String,
        val version: String,
        val rank: Int,
        val survival: Int,
        val lastSurvivorBonus: Int,
        val bulletDamage: Int,
        val bulletKillBonus: Int,
        val ramDamage: Int,
        val ramKillBonus: Int,
        val totalScore: Int,
        val firstPlaces: Int,
        val secondPlaces: Int,
        val thirdPlaces: Int
) : Message(MessageType.BOT_RESULTS.type)