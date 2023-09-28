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
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.math.BigInteger
import java.security.MessageDigest

class GameActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_activity)

        setJudgeCard()
        setPlayerCards()
    }

    fun setJudgeCard() {
        Thread {
            var session = "86c6d965d07a7b4e3023f4dc65e31856318e309073c6e80d7d1cdef74f1d8b40"
            var game = "bigapple"

            var client = OkHttpClient.Builder().build()
            var url = "https://pomme.us:32123/game/poll"

            var bodyBuilder = FormBody.Builder()
                .add("session", session)
                .add("game", game)

            var body = bodyBuilder.build()

            var request = Request.Builder().url(url).post(body).build()
            var response = client.newCall(request).execute()
            var text = response.body?.string() ?: "error"

            val obj = JSONObject(text)
            var image = obj.getString("image")

            runOnUiThread {
                var imageUrl = "https://pomme.us/img/main/" + image
                val mainCard: ImageView = findViewById(R.id.main_card)
                Glide.with(this).load(imageUrl).into(mainCard)
            }
        }.start()
    }

    fun setPlayerCards() {
        Thread {
            var session = "86c6d965d07a7b4e3023f4dc65e31856318e309073c6e80d7d1cdef74f1d8b40"
            var game = "bigapple"

            var client = OkHttpClient.Builder().build()
            var url = "https://pomme.us:32123/game/join"

            var bodyBuilder = FormBody.Builder()
                .add("session", session)
                .add("game", game)

            var body = bodyBuilder.build()

            var request = Request.Builder().url(url).post(body).build()
            var response = client.newCall(request).execute()
            var text = response.body?.string() ?: "error"

            val obj = JSONObject(text)
            var cards = obj.getJSONArray("cards")
            var imageViewIds = intArrayOf(
                R.id.player_card_1,
                R.id.player_card_2,
                R.id.player_card_3,
                R.id.player_card_4,
                R.id.player_card_5,
            )
            for (i in 0.. (cards.length() - 1)) {
                var card = cards.getString(i)
                var imageViewId = imageViewIds[i]

                var imageUrl = "https://pomme.us/img/player/" + card

                runOnUiThread {
                    setPlayerCard(imageViewId, imageUrl)
                }
            }
        }.start()
    }

    fun setPlayerCard(imageViewId: Int, cardUrl: String) {
        val card: ImageView = findViewById(imageViewId)
        Glide.with(this).load(cardUrl).into(card)

        var initialX = card.x

        var xx = 0f
        var yy = 0f
        card.setOnTouchListener { view, ev ->
            Log.i("POMME", "touch " + ev.action)
            if (ev.action == MotionEvent.ACTION_UP || ev.action == MotionEvent.ACTION_CANCEL) {
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
