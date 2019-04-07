package nz.co.jacksteel.spaceexplorer.model

abstract class FoodItem(
    val stringResourceName: String,
    val price: Int,
    val nutrition: Int
)

class Lunch : FoodItem(
    stringResourceName = "food_lunch",
    price = 5,
    nutrition = 50
)