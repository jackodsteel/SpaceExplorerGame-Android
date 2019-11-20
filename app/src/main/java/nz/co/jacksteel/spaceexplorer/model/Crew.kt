package nz.co.jacksteel.spaceexplorer.model

data class Crew(
    val name: String,
    val crewMembers: MutableList<CrewMember>
) {
    val medicalItems: MutableList<MedicalItem> = mutableListOf()
    val foodItems: MutableList<FoodItem> = mutableListOf()
    var money: Int = 100
    val ship: SpaceShip = SpaceShip()
}

data class SpaceShip(
    var shieldHealth: Int = 100
)