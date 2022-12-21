package dgtic.unam.authfirebase.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import dgtic.unam.authfirebase.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup
        setup()
    }

    private fun setup() {
        binding.signInButton.setOnClickListener {
            if (binding.editTextTextEmailAddress.text.isNotEmpty() && binding.editTextTextPassword.text.isNotEmpty()) {
                FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(
                        binding.editTextTextEmailAddress.text.toString(),
                        binding.editTextTextPassword.text.toString()
                    )
                    .addOnCompleteListener {
                         if (it.isSuccessful){
                            showHome(it.result.user!!.email!!)
                         } else {
                            showAlert()
                         }
                    }
            }
        }

        binding.loginButton.setOnClickListener {
            if (binding.editTextTextEmailAddress.text.isNotEmpty() && binding.editTextTextPassword.text.isNotEmpty()) {
                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(
                        binding.editTextTextEmailAddress.text.toString(),
                        binding.editTextTextPassword.text.toString()
                    )
                    .addOnCompleteListener {
                        if (it.isSuccessful){
                            showHome(it.result.user!!.email!!)
                        } else {
                            showAlert()
                        }
                    }
            }
        }
    }

    private fun showHome(email: String) {
        val homeIntent = Intent(this, HomeActivity::class.java).apply {
            putExtra("email", email)
        }
        startActivity(homeIntent)
    }

    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        with(builder) {
            setTitle("Error")
            setMessage("Se ha producido un error")
            setPositiveButton("Aceptar", null)
            val dialog: AlertDialog = create()
            dialog.show()
        }
    }
}