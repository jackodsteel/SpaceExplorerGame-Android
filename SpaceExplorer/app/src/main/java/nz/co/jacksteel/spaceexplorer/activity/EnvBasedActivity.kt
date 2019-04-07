package nz.co.jacksteel.spaceexplorer.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import nz.co.jacksteel.spaceexplorer.model.GameEnvironment
import nz.co.jacksteel.spaceexplorer.util.Json
import java.io.FileNotFoundException

abstract class EnvBasedActivity : Activity() {

    open val TAG = "EnvBasedActivity"

    lateinit var env: GameEnvironment

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(GameEnvironment.KEY, Json.to(env))
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val maybeEnv = savedInstanceState.getString(GameEnvironment.KEY)
        if (maybeEnv != null) {
            env = Json.from(maybeEnv, GameEnvironment::class)
            setup()
            return
        }
    }

    override fun onStart() {
        super.onStart()

        val maybeEnv = intent.getStringExtra(GameEnvironment.KEY)
        if (maybeEnv != null) {
            intent.removeExtra(GameEnvironment.KEY)
            env = Json.from(maybeEnv, GameEnvironment::class)
            setup()
            return
        }

        try {
            val file = openFileInput("game.json")
            env = Json.from(file, GameEnvironment::class)
            setup()
        } catch (e: FileNotFoundException) {
            Log.i(TAG, "No save state found, going to setup screen")
            val intent = Intent(this, CreateGameActivity::class.java)
            startActivity(intent)
            return
        }

    }

    override fun onStop() {
        super.onStop()
        val file = openFileOutput("game.json", Context.MODE_PRIVATE)
        Log.i(TAG, Json.to(env))
        Json.write(env, file)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        setResult(1, Intent().putExtra(GameEnvironment.KEY, Json.to(env)))
    }

    abstract fun setup()
}