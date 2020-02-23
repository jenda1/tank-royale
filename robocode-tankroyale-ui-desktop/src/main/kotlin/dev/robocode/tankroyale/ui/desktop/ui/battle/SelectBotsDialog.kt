package dev.robocode.tankroyale.ui.desktop.ui.battle

import dev.robocode.tankroyale.ui.desktop.client.Client
import dev.robocode.tankroyale.ui.desktop.extensions.JComponentExt.addButton
import dev.robocode.tankroyale.ui.desktop.extensions.JListExt.onChanged
import dev.robocode.tankroyale.ui.desktop.extensions.WindowExt.onActivated
import dev.robocode.tankroyale.ui.desktop.model.BotInfo
import dev.robocode.tankroyale.ui.desktop.server.ServerProcess
import dev.robocode.tankroyale.ui.desktop.settings.GameType
import dev.robocode.tankroyale.ui.desktop.settings.GamesSettings
import dev.robocode.tankroyale.ui.desktop.ui.MainWindow
import dev.robocode.tankroyale.ui.desktop.ui.ResourceBundles
import dev.robocode.tankroyale.ui.desktop.util.Event
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.UnstableDefault
import net.miginfocom.swing.MigLayout
import java.awt.Dimension
import java.awt.EventQueue
import javax.swing.*


@UnstableDefault
@ImplicitReflectionSerializer
object SelectBotsDialog : JDialog(MainWindow, ResourceBundles.UI_TITLES.get("select_bots_dialog")) {

    init {
        defaultCloseOperation = DISPOSE_ON_CLOSE

        size = Dimension(600, 450)

        setLocationRelativeTo(null) // center on screen

        val selectBotsAndStartPanel = SelectBotsAndStartPanel()

        contentPane.add(selectBotsAndStartPanel)

        onActivated {
            selectBotsAndStartPanel.apply {
                updateAvailableBots()
                clear()
            }
        }
    }
}

@UnstableDefault
@ImplicitReflectionSerializer
class SelectBotsAndStartPanel : JPanel(MigLayout("fill")) {
    // Private events
    private val onStartBattle = Event<JButton>()
    private val onCancel = Event<JButton>()

    private val startBattleButton: JButton

    private val selectBotsPanel = SelectBotsPanel()

    init {
        val buttonPanel = JPanel(MigLayout("center, insets 0"))

        val lowerPanel = JPanel(MigLayout("insets 10, fill")).apply {
            add(selectBotsPanel, "north")
            add(buttonPanel, "center")
        }
        add(lowerPanel, "south, h 1000000")

        buttonPanel.apply {
            startBattleButton = addButton("start_battle", onStartBattle, "tag ok")
            addButton("cancel", onCancel, "tag cancel")
        }
        startBattleButton.isEnabled = false

        selectBotsPanel.selectedBotList.onChanged {
            startBattleButton.isEnabled = selectBotsPanel.selectedBotListModel.size >= 2
        }

        onStartBattle.subscribe { startGame() }

        Client.onBotListUpdate.subscribe { updateAvailableBots() }

        updateAvailableBots()
    }

    fun clear() {
        selectBotsPanel.selectedBotListModel.clear()
    }

    fun updateAvailableBots() {
        SwingUtilities.invokeLater {
            val availableBotListModel = selectBotsPanel.availableBotListModel
            availableBotListModel.clear()
            Client.availableBots.forEach { availableBotListModel.addElement(it) }
        }
    }

    @ImplicitReflectionSerializer
    @UnstableDefault
    private fun startGame() {
        isVisible = true

        val gameType = ServerProcess.gameType
            ?: GameType.CLASSIC.type // FIXME: Dialog must be shown to select game with remote server

        val botAddresses = selectBotsPanel.selectedBotListModel.toArray()
            .map { b -> (b as BotInfo).botAddress }
        Client.startGame(GamesSettings.games[gameType]!!, botAddresses.toSet())

        SelectBotsDialog.dispose()
    }
}

@UnstableDefault
@ImplicitReflectionSerializer
private fun main() {
    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())

    EventQueue.invokeLater {
        SelectBotsDialog.isVisible = true
    }
}