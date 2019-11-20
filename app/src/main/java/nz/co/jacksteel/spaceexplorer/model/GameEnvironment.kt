package nz.co.jacksteel.spaceexplorer.model

import android.content.Context
import android.content.Intent
import android.widget.Toast
import nz.co.jacksteel.spaceexplorer.R
import nz.co.jacksteel.spaceexplorer.util.Json
import kotlin.math.max
import kotlin.random.Random

class GameEnvironment(
    var totalDays: Int,
    var crew: Crew
) {
    var currentDay = 0
    var partsFound = 0
    var score = 0

    companion object {
        val KEY = "env"
    }

    fun nextDay(applicationContext: Context, seed: Int): Boolean {
        score += crew.ship.shieldHealth
        score += crew.money
        score += crew.medicalItems.size * 10
        score += crew.foodItems.size * 5
        score += crew.crewMembers.sumBy { (it.health * 2) - it.hunger - it.tiredness - if (it.isSick) 100 else 0 }
        score += partsFound * 150

        currentDay++

        if (totalDays == currentDay) {
            return true
        }
        if (crew.crewMembers.all { it.health <= 0 }) {
            Toast.makeText(applicationContext, applicationContext.getString(R.string.all_dead), Toast.LENGTH_SHORT).show()
            return true
        }

        crew.money += 50
        crew.crewMembers.forEach { it.nextDay(applicationContext, seed) }
        if (Random(seed).nextBoolean()) {
            if (crew.ship.shieldHealth <= 0) {
                Toast.makeText(applicationContext, applicationContext.getString(R.string.attacked_no_shield), Toast.LENGTH_SHORT).show()
                return true
            }
            crew.ship.shieldHealth = max(0, crew.ship.shieldHealth - 30)
            Toast.makeText(applicationContext, applicationContext.getString(R.string.attacked), Toast.LENGTH_SHORT).show()
        }
        return false
    }
}

fun Intent.withEnv(gameEnvironment: GameEnvironment): Intent {
    return this.putExtra(GameEnvironment.KEY, Json.to(gameEnvironment))
}