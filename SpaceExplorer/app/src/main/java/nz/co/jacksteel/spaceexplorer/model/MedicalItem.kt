package nz.co.jacksteel.spaceexplorer.model

abstract class MedicalItem(
    val name: String,
    val price: Int,
    val healing: Int,
    val curesPlague: Boolean
)