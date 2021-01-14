package uk.co.bubblebearapps.presentation.base

import android.content.Context
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import uk.co.bubblebearapps.presentation.R

@GlideModule
class GlideModule : AppGlideModule() {

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        builder.setDefaultRequestOptions(
                RequestOptions()
                        .placeholder(R.drawable.image_placeholder)
                        .error(R.drawable.image_error)
                        .fallback(R.drawable.image_placeholder)
                        .override(Target.SIZE_ORIGINAL)
        )
    }
}