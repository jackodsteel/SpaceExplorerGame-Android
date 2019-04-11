package nz.co.jacksteel.spaceexplorer.activity

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import nz.co.jacksteel.spaceexplorer.R
import nz.co.jacksteel.spaceexplorer.fragment.CrewMemeberFragment
import nz.co.jacksteel.spaceexplorer.model.CrewMember
import nz.co.jacksteel.spaceexplorer.model.GameEnvironment
import nz.co.jacksteel.spaceexplorer.model.NoActionsAvailableException
import nz.co.jacksteel.spaceexplorer.model.withEnv
import nz.co.jacksteel.spaceexplorer.util.Json
import nz.co.jacksteel.spaceexplorer.util.RandomSeedFetcher


class MainActivity : Activity() {

    private val TAG = "MainActivity"

    var seed: Int = 0

    private var isReset = false

    private lateinit var env: GameEnvironment
    private lateinit var crewOneFragment: CrewMemeberFragment
    private lateinit var crewTwoFragment: CrewMemeberFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        crewOneFragment = fragmentManager.findFragmentById(R.id.crewOneFragment) as CrewMemeberFragment
        crewTwoFragment = fragmentManager.findFragmentById(R.id.crewTwoFragment) as CrewMemeberFragment

        Log.i(TAG, "onCreate")
        RandomSeedFetcher {
            seed = it
            Log.i(TAG, "Set seed to $it")
        }.execute()
    }

    override fun onStart() {
        super.onStart()
        isReset = false
        Log.i(TAG, "onStart")

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
            Log.i(TAG, "Food: ${env.crew.foodItems.size}, Medical: ${env.crew.medicalItems.size}")
            setup()
        } catch (e: Exception) {
            Log.i(TAG, "No save state found, going to setup screen")
            isReset = true
            val intent = Intent(this, CreateGameActivity::class.java)
            startActivity(intent)
            return
        }
    }

    override fun onStop() {
        super.onStop()
        if (!isReset) {
            val file = openFileOutput("game.json", Context.MODE_PRIVATE)
            Json.write(env, file)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val maybeEnv = data?.getStringExtra(GameEnvironment.KEY)
        if (maybeEnv != null) {
            env = Json.from(maybeEnv, GameEnvironment::class)
            setup()
            return
        }
    }

    private fun refresh() {
        shipNameTextView.text = env.crew.name
        shipHealthBar.progress = env.crew.ship.shieldHealth
        currentDayTextView.text = getString(R.string.main_info, env.currentDay, env.totalDays, env.partsFound, env.score)
        crewOneFragment.setup(env.crew.crewMembers[0])
        crewTwoFragment.setup(env.crew.crewMembers[1])
    }

    private fun setup() {
        refresh()

        storeButton.setOnClickListener {
            val intent = Intent(this, StoreActivity::class.java)
                .withEnv(env)
            startActivityForResult(intent, 2)
        }
        inventoryButton.setOnClickListener {
            val intent = Intent(this, UseItemsActivity::class.java)
                .withEnv(env)
            startActivityForResult(intent, 2)
        }

        sleepButton.setOnClickListener {
            crewSelectorDialog(this, env.crew.crewMembers) {
                try {
                    it.sleep()
                    refresh()
                } catch (e: NoActionsAvailableException) {
                    Toast.makeText(this, getString(R.string.no_more_actions), Toast.LENGTH_SHORT).show()
                }
            }
        }

        searchForPartsButton.setOnClickListener {
            crewSelectorDialog(this, env.crew.crewMembers) {
                try {
                    if (it.searchForParts(seed)) {
                        env.partsFound++
                        refresh()
                        Toast.makeText(applicationContext, getString(R.string.found_a_part), Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(applicationContext, getString(R.string.didnt_find_a_part), Toast.LENGTH_SHORT).show()
                    }
                } catch (e: NoActionsAvailableException) {
                    Toast.makeText(applicationContext, getString(R.string.no_more_actions), Toast.LENGTH_SHORT).show()
                }
            }
        }

        repairShipButton.setOnClickListener {
            crewSelectorDialog(this, env.crew.crewMembers) {
                try {
                    it.repair()
                    env.crew.ship.shieldHealth += 40
                    refresh()
                } catch (e: NoActionsAvailableException) {
                    Toast.makeText(applicationContext, getString(R.string.no_more_actions), Toast.LENGTH_SHORT).show()
                }
            }
        }

        nextDayButton.setOnClickListener {
            if (env.nextDay(applicationContext, seed)) {
                val intent = Intent(this, EndGameActivity::class.java)
                    .putExtra(EndGameActivity.SCORE_KEY, env.score.toString())
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
            } else {
                refresh()
            }
        }
    }

}

fun crewSelectorDialog(context: Context, crew: List<CrewMember>, callback: (CrewMember) -> Unit) {
    AlertDialog.Builder(context)
        .setTitle(context.getString(R.string.select_crew_member))
        .setItems(crew.map { "${it.name} (${it.availableActions})" }.toTypedArray()) { _, pos -> callback(crew[pos]) }
        .show()
}