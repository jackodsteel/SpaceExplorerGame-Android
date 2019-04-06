package nz.co.jacksteel.spaceexplorer.model

abstract class CrewType(
    val name: String,
    val healthyness: Double,
    val hungryness: Double,
    val tiredness: Double,
    val repairSkill: Double,
    val searchSkill: Double
)

class BigCrewman: CrewType("Big Fella", 1.0, 1.0, 1.0, 1.0, 1.0)
class MidCrewman: CrewType("Mid Fella", 1.0, 1.0, 1.0, 1.0, 1.0)
class SmallCrewman: CrewType("Small Fella", 1.0, 1.0, 1.0, 1.0, 1.0)