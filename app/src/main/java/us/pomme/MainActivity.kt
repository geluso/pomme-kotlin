package us.pomme

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
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

        var login = findViewById<Button>(R.id.login)
        var usernameInput = findViewById<EditText>(R.id.username_input)
        var passwordInput = findViewById<EditText>(R.id.password_input)
        login.setOnClickListener {
            var username = usernameInput.text
            Toast.makeText(this@MainActivity, username, Toast.LENGTH_SHORT).show()

            Thread {
                var client = OkHttpClient.Builder()
                    .connectionSpecs(Arrays.asList(
                        ConnectionSpec.MODERN_TLS,
                        ConnectionSpec.COMPATIBLE_TLS,
                        ConnectionSpec.CLEARTEXT
                    ))
                    .build()
                var url = "https://pomme.us:32123/user/login"

                var body = FormBody.Builder()
                    .add("name", "moon")
                    .build();
                var request = Request.Builder().url(url).post(body).build();
                var response = client.newCall(request).execute();
                System.out.println(response.body?.string() ?: "");

                runOnUiThread({
                    //Update UI
                })
            }.start()


            // var intent = Intent(this, GameActivity::class.java)
            // startActivity(intent)
            true
        }
    }
}
