package nz.co.jacksteel.spaceexplorer.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.NumberPicker
import nz.co.jacksteel.spaceexplorer.R
import nz.co.jacksteel.spaceexplorer.util.GameConfig

class CreateGameActivity : Activity() {

    private lateinit var nextButton: Button
    private lateinit var daysPicker: NumberPicker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_game)

        nextButton = findViewById(R.id.nextButton)

        daysPicker = findViewById(R.id.daysPicker)
        daysPicker.minValue = GameConfig.MIN_DAYS
        daysPicker.maxValue = GameConfig.MAX_DAYS

        nextButton.setOnClickListener {
            val intent = Intent(this, CreateCrewActivity::class.java)
                .putExtra("days", daysPicker.value)
            startActivity(intent)
        }
    }

}
