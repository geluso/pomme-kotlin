package us.pomme

import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import okhttp3.ConnectionSpec
import okhttp3.FormBody
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import java.io.IOException
import java.util.Arrays

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        var skipLogin = true
        var isEnteringPassword = false

        var usernamePrompt = findViewById<TextView>(R.id.enter_username)
        var usernameInput = findViewById<EditText>(R.id.username_input)

        var passwordPrompt = findViewById<TextView>(R.id.enter_password)
        var badPasswordPrompt = findViewById<TextView>(R.id.bad_password)
        var passwordInput = findViewById<EditText>(R.id.password_input)

        var login = findViewById<Button>(R.id.login)
        login.setOnClickListener {
            var username = usernameInput.text.toString()
            var password = passwordInput.text.toString()

            Toast.makeText(this@MainActivity, username, Toast.LENGTH_SHORT).show()

            Thread {
                var client = OkHttpClient.Builder()
                    .connectionSpecs(
                        Arrays.asList(
                            ConnectionSpec.MODERN_TLS,
                            ConnectionSpec.COMPATIBLE_TLS,
                            ConnectionSpec.CLEARTEXT
                        )
                    )
                    .build()
                var url = "https://pomme.us:32123/user/login"

                var bodyBuilder = FormBody.Builder()
                    .add("name", username)

                if (isEnteringPassword) {
                    bodyBuilder.add("password", password)
                }

                var body = bodyBuilder.build()

                var request = Request.Builder().url(url).post(body).build()
                var response = client.newCall(request).execute()
                var text = response.body?.string() ?: ""

                if (skipLogin || !text.contains(("error"))) {
                    var intent = Intent(this, GameActivity::class.java)
                    startActivity(intent)
                } else if (text.contains("error") && text.contains("bad_password")) {
                    runOnUiThread {
                        passwordPrompt.visibility = View.GONE
                        badPasswordPrompt.visibility = View.VISIBLE
                    }
                } else if (text.contains("error") && text.contains("password")) {
                    isEnteringPassword = true

                    runOnUiThread {
                        usernamePrompt.visibility = View.GONE
                        usernameInput.visibility = View.GONE

                        passwordPrompt.visibility = View.VISIBLE
                        passwordInput.visibility = View.VISIBLE

                        login.text = getString(R.string.its_me)
                    }
                } else {
                    System.out.println("Something went wrong.")
                }
            }.start()
            true
        }
    }
}
