package io.dina.githubuser.utility

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

fun AppCompatActivity.showBackButton() {
    supportActionBar?.apply {
        setHomeButtonEnabled(true)
        setDisplayHomeAsUpEnabled(true)
        setDisplayShowHomeEnabled(true)
    }
}

fun AppCompatActivity.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}