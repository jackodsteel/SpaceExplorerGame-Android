package nz.co.jacksteel.spaceexplorer.model

data class Crew(
    val name: String,
    val ship: SpaceShip,
    var money: Int = 100,
    val crewMembers: MutableList<CrewMember>,
    val medicalItems: MutableList<MedicalItem>,
    val foodItems: MutableList<FoodItem>
)

data class SpaceShip(
    var shieldHealth: Int = 100
)