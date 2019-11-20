package nz.co.jacksteel.spaceexplorer.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import nz.co.jacksteel.spaceexplorer.R
import nz.co.jacksteel.spaceexplorer.model.HealthPack
import nz.co.jacksteel.spaceexplorer.model.Lunch
import nz.co.jacksteel.spaceexplorer.model.withEnv

class StoreActivity : EnvBasedActivity() {

    override val TAG = "StoreActivity"

    private lateinit var moneyTextView: TextView
    private lateinit var buyMedicalItemButton: Button
    private lateinit var buyFoodItemButton: Button
    private lateinit var homeButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store)

        buyMedicalItemButton = findViewById(R.id.buyMedicalItemButton)
        buyFoodItemButton = findViewById(R.id.buyFoodItemButton)
        homeButton = findViewById(R.id.homeButton)
        moneyTextView = findViewById(R.id.moneyTextView)
    }

    override fun setup() {
        moneyTextView.text = getString(R.string.current_money, env.crew.money)

        buyMedicalItemButton.setOnClickListener {
            if (env.crew.money < 10) {
                Toast.makeText(applicationContext, getString(R.string.not_enough_money), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            env.crew.money -= 10
            env.crew.medicalItems.add(HealthPack())
            Log.i(TAG, "Food: ${env.crew.foodItems.size}, Medical: ${env.crew.medicalItems.size}")
            moneyTextView.text = getString(R.string.current_money, env.crew.money)
        }

        buyFoodItemButton.setOnClickListener {
            if (env.crew.money < 5) {
                Toast.makeText(applicationContext, getString(R.string.not_enough_money), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            env.crew.money -= 5
            env.crew.foodItems.add(Lunch())
            Log.i(TAG, "Food: ${env.crew.foodItems.size}, Medical: ${env.crew.medicalItems.size}")
            moneyTextView.text = getString(R.string.current_money, env.crew.money)
        }

        homeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
                .withEnv(env)
            startActivity(intent)
        }
    }
}