package us.pomme

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
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
    }
}

