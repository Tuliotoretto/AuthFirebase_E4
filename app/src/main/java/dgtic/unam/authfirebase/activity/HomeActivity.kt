package dgtic.unam.authfirebase.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import dgtic.unam.authfirebase.R
import dgtic.unam.authfirebase.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityHomeBinding

    private lateinit var drawer: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        inicioToolsBar()

        val bundle = intent.extras
        val email = bundle?.getString("email")

        // Setup
        setup(email ?: "")
    }

    private fun  inicioToolsBar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)
        drawer = findViewById(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.abrir, R.string.cerrar)
        toggle.syncState()
        toolbar.setNavigationIcon(R.drawable.unam_32)
        iniciarNavegationView()
    }

    private fun iniciarNavegationView() {
        val navView: NavigationView = findViewById(R.id.nav_view)
        navView.setNavigationItemSelectedListener(this)
        val headerView: View = LayoutInflater.from(this).inflate(R.layout.header_main, navView, false)
        navView.addHeaderView(headerView)
    }

    private fun setup(email: String) {

        binding.TextViewEmailAddress.text = "Usuario: $email"

        FirebaseAuth.getInstance().currentUser!!.getIdToken(true)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    binding.TextViewToken.text = "Token: ${it.result.token}"
                }
            }

        binding.logoutButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.logout -> {
                FirebaseAuth.getInstance().signOut()
                onBackPressed()
            }
        }
        drawer.closeDrawer(GravityCompat.START)
        return true
    }
}