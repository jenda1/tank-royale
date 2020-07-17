package dev.robocode.tankroyale.sample.bots;

import dev.robocode.tankroyale.botapi.Bot;
import dev.robocode.tankroyale.botapi.BotInfo;
import dev.robocode.tankroyale.botapi.events.BotHitBotEvent;
import dev.robocode.tankroyale.botapi.events.ScannedBotEvent;

import java.io.IOException;

/**
 * SpinBot - a sample robot.
 *
 * <p>Moves in a circle, firing hard when an enemy is detected.
 */
public class SpinBot extends Bot {

  /** Main method starts our bot */
  public static void main(String[] args) throws IOException {
    new SpinBot().start();
  }

  /** Constructor, which loads the bot settings file */
  protected SpinBot() throws IOException {
    super(BotInfo.fromFile("bot.properties"));
  }

  /** SpinBot's run method - Move in a circle */
  @Override
  public void run() {
    setBodyColor("#00F"); // blue
    setTurretColor("#00F"); // blue
    setRadarColor("#000"); // black
    setScanColor("#FF0"); // yellow

    // Repeat while bot is running
    while (isRunning()) {
      // Tell the game that when we take move,
      // we'll also want to turn right... a lot.
      setTurnRight(10000);
      // Limit our speed to 5
      setMaxSpeed(5);
      // Start moving (and turning)
      forward(10000);
      // Repeat
    }
  }

  /** Fire hard when scanning another bot! */
  @Override
  public void onScannedBot(ScannedBotEvent e) {
    fire(3); // Fire the cannon!
  }

  /**
   * We hit another bot. If we rammed the bot, we'll stop turning and moving, so we need to turn
   * again to keep spinning.
   */
  @Override
  public void onHitBot(BotHitBotEvent e) {
    double direction = calcDirection(e.getX(), e.getY());
    double bearing = calcBearing(direction);
    if (bearing > -10 && bearing < 10) {
      fire(3);
    }
    if (e.isRammed()) {
      turnRight(10);
    }
  }
}