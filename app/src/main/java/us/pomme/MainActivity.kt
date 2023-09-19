package us.pomme

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        var login = findViewById<Button>(R.id.login)
        var usernameInput = findViewById<EditText>(R.id.username_input)
        var passwordInput = findViewById<EditText>(R.id.password_input)
        login.setOnClickListener {
            var username = usernameInput.text
            Toast.makeText(this@MainActivity, username, Toast.LENGTH_SHORT).show()

            var intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
        }
    }
}
