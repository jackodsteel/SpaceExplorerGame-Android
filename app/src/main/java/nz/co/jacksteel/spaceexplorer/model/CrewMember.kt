package nz.co.jacksteel.spaceexplorer.model

import android.content.Context
import android.widget.Toast
import nz.co.jacksteel.spaceexplorer.R
import kotlin.math.roundToInt
import kotlin.random.Random

class NoActionsAvailableException: Throwable()

class CrewMember(
    val name: String,
    val type: CrewType,
    var health: Int = 100,
    var tiredness: Int = 50,
    var hunger: Int = 50,
    var availableActions: Int = 2,
    var isSick: Boolean = false
) {

    fun nextDay(applicationContext: Context, seed: Int) {
        availableActions = 2
        tiredness += (20 * type.tiredness).roundToInt()
        hunger += (20 * type.hungryness).roundToInt()
        if (tiredness > 50) {
            health -= 10
        }
        if (hunger > 50) {
            health -= 10
        }
        if (isSick) {
            health -= 30
        }
        if (health <= 0) {
            Toast.makeText(applicationContext, applicationContext.getString(R.string.crew_died), Toast.LENGTH_LONG).show()
            return
        }

        if (Random(seed).nextDouble() > 0.8) {
            isSick = true
            Toast.makeText(applicationContext, applicationContext.getString(R.string.crew_ill, name), Toast.LENGTH_SHORT).show()
        }
    }

    fun eat(foodItem: FoodItem) {
        if (availableActions <= 0) throw NoActionsAvailableException()
        hunger -= foodItem.nutrition
        availableActions--
    }

    fun heal(medicalItem: MedicalItem) {
        if (availableActions <= 0) throw NoActionsAvailableException()
        health += medicalItem.healing
        if (isSick && medicalItem.curesPlague) {
            isSick = false
        }
        availableActions--
    }

    fun sleep() {
        if (availableActions <= 0) throw NoActionsAvailableException()
        tiredness -= 30
        availableActions--
    }

    fun searchForParts(seed: Int): Boolean {
        if (availableActions <= 0) throw NoActionsAvailableException()
        availableActions--
        return Random(seed).nextBoolean()
    }

    fun repair() {
        if (availableActions <= 0) throw NoActionsAvailableException()
        availableActions--
    }
}
