package dev.robocode.tankroyale.gui.ui.components

import javax.swing.JTextField

class JLimitedTextField(columns: Int, text: String? = null) : JTextField(JTextFieldLimit(columns), text, columns)