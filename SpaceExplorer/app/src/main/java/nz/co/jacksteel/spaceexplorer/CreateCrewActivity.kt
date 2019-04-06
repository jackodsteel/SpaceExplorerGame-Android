package nz.co.jacksteel.spaceexplorer

import android.app.Activity
import android.os.Bundle
import android.util.Log

class CreateCrewActivity : Activity() {

    private val TAG = "CreateCrewActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_crew)

        val days = intent.extras?.getInt("days")
        val crewCount = intent.extras?.getInt("crewCount")


        Log.i(TAG, "Days: $days, Crew Count: $crewCount")

    }
}
