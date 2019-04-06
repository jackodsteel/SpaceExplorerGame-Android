package nz.co.jacksteel.spaceexplorer.activity

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import nz.co.jacksteel.spaceexplorer.R
import nz.co.jacksteel.spaceexplorer.model.GameEnvironment
import nz.co.jacksteel.spaceexplorer.util.Json

class MainActivity : Activity() {

    private val TAG = "MainActivity"

    private lateinit var env: GameEnvironment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        val file = openFileInput("game.json")
        env = Json.from(file, GameEnvironment::class)
        Log.i(TAG, env.totalDays.toString())
    }

    override fun onStop() {
        super.onStop()
        val file = openFileOutput("game.json", Context.MODE_PRIVATE)
        Json.write(env, file)
    }
}
