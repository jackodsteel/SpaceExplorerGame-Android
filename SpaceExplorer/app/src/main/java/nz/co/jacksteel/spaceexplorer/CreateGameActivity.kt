package nz.co.jacksteel.spaceexplorer

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.NumberPicker
import nz.co.jacksteel.spaceexplorer.util.GameConfig

class CreateGameActivity : Activity() {

    private lateinit var nextButton: Button
    private lateinit var daysPicker: NumberPicker
    private lateinit var crewCountPicker: NumberPicker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_game)
        nextButton = findViewById(R.id.nextButton)

        daysPicker = findViewById(R.id.daysPicker)
        daysPicker.minValue = GameConfig.MIN_DAYS
        daysPicker.maxValue = GameConfig.MAX_DAYS

        crewCountPicker = findViewById(R.id.crewCountPicker)
        crewCountPicker.minValue = GameConfig.MIN_CREW
        crewCountPicker.maxValue = GameConfig.MAX_CREW

        nextButton.setOnClickListener {
            val intent = Intent(this, CreateCrewActivity::class.java)
                .putExtra("days", daysPicker.value)
                .putExtra("crewCount", crewCountPicker.value)
            startActivity(intent)
        }
    }

}
