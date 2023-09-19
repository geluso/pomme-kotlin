package us.pomme

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.bumptech.glide.Glide

class GameActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_activity)

        val mainCard: ImageView = findViewById(R.id.main_card)
        Glide.with(this).load(R.drawable.ariel).into(mainCard)

        setPlayerCard(R.id.player_card_1, R.drawable.player1)
        setPlayerCard(R.id.player_card_2, R.drawable.player2)
        setPlayerCard(R.id.player_card_3, R.drawable.player3)
        setPlayerCard(R.id.player_card_4, R.drawable.player4)
        setPlayerCard(R.id.player_card_5, R.drawable.player5)
    }

    fun setPlayerCard(imageViewId: Int, cardId: Int) {
        val card: ImageView = findViewById(imageViewId)
        Glide.with(this).load(cardId).into(card)

        var initialX = card.x

        var xx = 0f
        var yy = 0f
        card.setOnTouchListener { view, ev ->
            Log.i("POMME", "touch " + ev.action)
            if (ev.action == MotionEvent.ACTION_UP) {
                card.x = initialX

                card.performClick()
            } else if (ev.action == MotionEvent.ACTION_DOWN) {
                xx = ev.x
                yy = ev.y

                var scroller: ScrollView = findViewById(R.id.player_cards_holder)
            } else if (ev.action == MotionEvent.ACTION_MOVE) {
                var dx = ev.x - xx
                var dy = ev.y - yy

                System.out.println("initial x:" + initialX + " x:" + card.x + " dx: " + dx)
                card.x = card.x + dx
                //card.y = card.y + dy

                xx = ev.x
                yy = ev.y
            }
            true
        }
    }
}
