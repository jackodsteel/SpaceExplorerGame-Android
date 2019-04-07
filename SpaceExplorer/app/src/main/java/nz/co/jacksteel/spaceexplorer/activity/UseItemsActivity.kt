package nz.co.jacksteel.spaceexplorer.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Button
import android.widget.Toast
import nz.co.jacksteel.spaceexplorer.R
import nz.co.jacksteel.spaceexplorer.model.NoActionsAvailableException
import nz.co.jacksteel.spaceexplorer.model.withEnv

class UseItemsActivity : EnvBasedActivity() {

    override val TAG = "UseItemsActivity"

    private lateinit var medicalItemsRV: RecyclerView
    private lateinit var foodItemsRV: RecyclerView
    private lateinit var useHomeButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_use_items)

        foodItemsRV = findViewById(R.id.foodItemsRV)
        medicalItemsRV = findViewById(R.id.medicalItemsRV)
        useHomeButton = findViewById(R.id.useHomeButton)
    }

    override fun setup() {
        foodItemsRV.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@UseItemsActivity)
            adapter = FoodAdapter(env.crew.foodItems, env.crew.crewMembers) { crewMember, food ->
                try {
                    crewMember.eat(food)
                    Toast.makeText(applicationContext, getString(R.string.ate_food, getString(R.string.food_lunch)), Toast.LENGTH_SHORT).show()
                    true
                } catch (e: NoActionsAvailableException) {
                    Toast.makeText(applicationContext, getString(R.string.no_more_actions), Toast.LENGTH_SHORT).show()
                    false
                }
            }
        }


        medicalItemsRV.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@UseItemsActivity)
            adapter = MedicalAdapter(env.crew.medicalItems, env.crew.crewMembers) { crewMember, med ->
                try {
                    crewMember.heal(med)
                    Toast.makeText(applicationContext, getString(R.string.healed_item, getString(R.string.medical_health_pack)), Toast.LENGTH_SHORT).show()
                    true
                } catch (e: NoActionsAvailableException) {
                    Toast.makeText(applicationContext, getString(R.string.no_more_actions), Toast.LENGTH_SHORT).show()
                    false
                }
            }
        }

        useHomeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
                .withEnv(env)
            startActivity(intent)
        }
    }

}
