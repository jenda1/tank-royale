package dev.robocode.tankroyale.gui.ui.newbattle

import dev.robocode.tankroyale.gui.settings.ConfigSettings
import dev.robocode.tankroyale.gui.settings.GameType
import dev.robocode.tankroyale.gui.settings.GameType.*
import javax.swing.JComboBox

class GameTypeDropdown : JComboBox<String>(
    listOf(CLASSIC, MELEE, ONE_VS_ONE, CUSTOM).map { it.displayName }.toTypedArray()) { // setup in specific order

    init {
        setSelectedGameType(ConfigSettings.gameType)

        ConfigSettings.onSaved.subscribe(this) {
            setSelectedGameType(ConfigSettings.gameType)
        }
    }

    fun getSelectedGameType(): GameType =
        if (model.selectedItem == null) {
            CLASSIC
        } else {
            GameType.from(model.selectedItem as String)
        }

    fun setSelectedGameType(gameType: GameType) {
        model.selectedItem = gameType.displayName
    }
}