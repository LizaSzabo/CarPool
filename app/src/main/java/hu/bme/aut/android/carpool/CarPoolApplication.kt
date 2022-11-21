package hu.bme.aut.android.carpool

import android.app.Application
import co.zsmb.rainbowcake.config.Loggers
import co.zsmb.rainbowcake.config.rainbowCake
import co.zsmb.rainbowcake.timber.TIMBER
import co.zsmb.requirektx.bundle.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import hu.bme.aut.android.carpool.data.util.FirebaseAuthentication
import hu.bme.aut.android.carpool.domain.model.User
import timber.log.Timber

@HiltAndroidApp
class CarPoolApplication : Application() {

    companion object {
        lateinit var firebaseAuth: FirebaseAuthentication
        lateinit var currentUser: User
    }

    override fun onCreate() {
        super.onCreate()

        setupRainbowCake()
    }

    private fun setupRainbowCake() {
        rainbowCake {
            logger = Loggers.TIMBER
            isDebug = BuildConfig.DEBUG
        }

        Timber.plant(Timber.DebugTree())
    }
}