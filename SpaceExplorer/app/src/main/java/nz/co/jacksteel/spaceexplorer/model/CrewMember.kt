package nz.co.jacksteel.spaceexplorer.model

import kotlin.math.roundToInt

class NoActionsAvailableException: Throwable()

class CrewMember(
    val name: String,
    val type: CrewType,
    var health: Int = 100,
    var tiredness: Int = 0,
    var hunger: Int = 0,
    var availableActions: Int = 2,
    var isSick: Boolean = false
) {

    fun nextDay() {
        tiredness += (20 * type.tiredness).roundToInt()
        hunger += (20 * type.hungryness).roundToInt()
        if (tiredness > 50) {
            health -= 10
        }
        if (hunger > 50) {
            health -= 10
        }
    }

    fun eat(foodItem: FoodItem) {
        if (availableActions <= 0) throw NoActionsAvailableException()
        hunger -= foodItem.nutrition
    }

    fun heal(medicalItem: MedicalItem) {
        if (availableActions <= 0) throw NoActionsAvailableException()
        health += medicalItem.healing
        if (isSick && medicalItem.curesPlague) {
            isSick = false
        }
    }

}
