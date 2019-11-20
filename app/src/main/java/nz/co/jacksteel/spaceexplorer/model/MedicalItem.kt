package nz.co.jacksteel.spaceexplorer.model

abstract class MedicalItem(
    val stringResourceName: String,
    val price: Int,
    val healing: Int,
    val curesPlague: Boolean
)

class HealthPack : MedicalItem(
    stringResourceName = "medical_health_pack",
    price = 10,
    healing = 50,
    curesPlague = true
)