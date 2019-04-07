package nz.co.jacksteel.spaceexplorer.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import nz.co.jacksteel.spaceexplorer.R
import nz.co.jacksteel.spaceexplorer.model.Crew
import nz.co.jacksteel.spaceexplorer.model.CrewMember
import nz.co.jacksteel.spaceexplorer.model.CrewType
import nz.co.jacksteel.spaceexplorer.model.GameEnvironment
import nz.co.jacksteel.spaceexplorer.util.Json

class CreateCrewActivity : Activity() {

    private lateinit var startGameButton: Button
    private lateinit var shipNameTextInput: EditText
    private lateinit var characterOneTextInput: EditText
    private lateinit var characterTwoTextInput: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_crew)

        val days = intent.extras!!.getInt("days")

        startGameButton = findViewById(R.id.startGameButton)
        shipNameTextInput = findViewById(R.id.shipNameTextInput)
        characterOneTextInput = findViewById(R.id.characterOneTextInput)
        characterTwoTextInput = findViewById(R.id.characterTwoTextInput)

        startGameButton.setOnClickListener {
            val env = createEnv(days) ?: return@setOnClickListener
            val file = openFileOutput("game.json", Context.MODE_PRIVATE)
            Json.write(env, file)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun createEnv(days: Int): GameEnvironment? {
        if (shipNameTextInput.text.isBlank() or characterOneTextInput.text.isBlank() or characterTwoTextInput.text.isBlank()) {
            Toast.makeText(applicationContext, getString(R.string.you_must_provide_a_name), Toast.LENGTH_SHORT).show()
            return null
        }
        val crewMembers = mutableListOf<CrewMember>()
        crewMembers.add(CrewMember(characterOneTextInput.text.toString(), CrewType.BigCrewman))
        crewMembers.add(CrewMember(characterTwoTextInput.text.toString(), CrewType.BigCrewman))
        val crew = Crew(shipNameTextInput.text.toString(), crewMembers)
        return GameEnvironment(days, crew)
    }

}
