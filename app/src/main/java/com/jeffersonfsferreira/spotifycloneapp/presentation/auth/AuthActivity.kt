package com.jeffersonfsferreira.spotifycloneapp.presentation.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.jeffersonfsferreira.spotifycloneapp.core.Constants
import com.jeffersonfsferreira.spotifycloneapp.data.local.TokenManager
import com.jeffersonfsferreira.spotifycloneapp.databinding.ActivityAuthBinding
import com.jeffersonfsferreira.spotifycloneapp.presentation.MainActivity
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationRequest
import com.spotify.sdk.android.auth.AuthorizationResponse
import org.koin.android.ext.android.inject

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding

    //Inject TokenManager via Koin
    private val tokenManager: TokenManager by inject()
    private val AUTH_REQUEST_CODE = 1337

    private val spotifyAuthLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        // Este bloco será executado quando a Activity de autenticação do Spotify retornar um resultado
        val response = AuthorizationClient.getResponse(result.resultCode, result.data)

        when (response.type) {
            AuthorizationResponse.Type.TOKEN -> {
                val accessToken = response.accessToken
                Log.d("AuthActivity", "Autenticação bem-sucedida! Token: $accessToken")
                Toast.makeText(this, "Login bem-sucedido!", Toast.LENGTH_SHORT).show()

                tokenManager.saveAccessToken(accessToken)
                Log.d("AuthActivity", "Token salvo.")
                navigateToMainScreen()
            }
            AuthorizationResponse.Type.ERROR -> {
                val error = response.error
                Log.e("AuthActivity", "Erro de autenticação: $error")
                Toast.makeText(this, "Erro de login: $error", Toast.LENGTH_LONG).show()
            }
            AuthorizationResponse.Type.CODE -> {
                Log.d("AuthActivity", "Resposta de código recebida. Não esperado para este fluxo.")
            }
            AuthorizationResponse.Type.EMPTY -> {
                Log.d("AuthActivity", "Resposta vazia. Autenticação cancelada.")
            }

            AuthorizationResponse.Type.UNKNOWN -> {
                Log.d("AuthActivity", "Resposta desconhecida. Autenticação cancelada.")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Checks if an access token has already been saved.
        // If yes, the user is already logged in, navigates directly to MainActivity.
        if(tokenManager.hasAccessToken()) {
            navigateToMainScreen()
            return
        }

        binding.btnLoginSpotify.setOnClickListener {
            authenticateSpotify()
        }
    }

    private fun authenticateSpotify() {
        Log.d("AuthActivity", "Iniciando autenticacao no Spotify")

        val request = AuthorizationRequest.Builder(
            Constants.CLIENT_ID,
            AuthorizationResponse.Type.TOKEN,
            Constants.REDIRECT_URI
        )
            .setScopes(Constants.SCOPES.split(",").toTypedArray())
            .setShowDialog(true)
            .build()

        val intent = AuthorizationClient.createLoginActivityIntent(this, request)
        if(intent == null) {
            Log.e("AuthActivity", "Erro: Intent de autenticação Spotify é nula!")
            Toast.makeText(this, "Erro ao iniciar autenticação. Tente novamente.", Toast.LENGTH_LONG).show()
            return
        }
        Log.d("AuthActivity", "Intent de autenticação Spotify criada: ${intent.action}")

        spotifyAuthLauncher.launch(intent)
        Log.d("AuthActivity", "Lançando Activity de autenticação...")
    }

    private fun navigateToMainScreen() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}