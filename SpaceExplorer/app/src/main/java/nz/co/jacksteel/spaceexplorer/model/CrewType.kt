package nz.co.jacksteel.spaceexplorer.model

sealed class CrewType(
    val name: String,
    val healthyness: Double,
    val hungryness: Double,
    val tiredness: Double,
    val repairSkill: Double,
    val searchSkill: Double
) {

    object BigCrewman : CrewType("Big Fella", 1.0, 1.0, 1.0, 1.0, 1.0)
    object MidCrewman : CrewType("Mid Fella", 1.0, 1.0, 1.0, 1.0, 1.0)
    object SmallCrewman : CrewType("Small Fella", 1.0, 1.0, 1.0, 1.0, 1.0)
}
