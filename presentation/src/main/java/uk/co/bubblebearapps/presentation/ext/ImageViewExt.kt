package uk.co.bubblebearapps.presentation.ext

import android.widget.ImageView
import com.bumptech.glide.Glide
import uk.co.bubblebearapps.domain.UrlString

fun ImageView.loadUrl(photo: UrlString) {
    Glide.with(this)
            .load(photo)
            .into(this)
}
