package nz.co.jacksteel.spaceexplorer.activity

import android.app.Activity
import android.content.Intent
import android.content.Intent.EXTRA_SUBJECT
import android.content.Intent.EXTRA_TEXT
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_end_game.*
import nz.co.jacksteel.spaceexplorer.R


class EndGameActivity : Activity() {

    companion object {
        const val SCORE_KEY = "score"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_end_game)

        val score = intent.getStringExtra(SCORE_KEY)

        scoreTextView.text = score

        shareScore.setOnClickListener {
            val sharingIntent = Intent(android.content.Intent.ACTION_SEND)
                .setType("text/plain")
                .putExtra(EXTRA_SUBJECT, getString(R.string.space_explorer_score))
                .putExtra(EXTRA_TEXT, getString(R.string.share_text_body_with_score, score))
            startActivity(Intent.createChooser(sharingIntent, "Share via"))
        }

        newGameButton.setOnClickListener { newGame() }
    }


    override fun onBackPressed() {
        newGame()
    }

    private fun newGame() {
        val intent = Intent(this, CreateGameActivity::class.java)
        startActivity(intent)
    }
}
