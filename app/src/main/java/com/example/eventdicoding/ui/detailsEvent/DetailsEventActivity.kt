package com.example.eventdicoding.ui.detailsEvent

import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.text.HtmlCompat
import com.bumptech.glide.Glide
import com.example.eventdicoding.R
import com.example.eventdicoding.data.Result
import com.example.eventdicoding.data.local.entity.EventEntity
import com.example.eventdicoding.data.remote.response.Event
import com.example.eventdicoding.databinding.ActivityDetailsEventBinding
import com.example.eventdicoding.ui.ViewModelFactory


class DetailsEventActivity :
    AppCompatActivity() {
    private lateinit var binding: ActivityDetailsEventBinding

    private val factory: ViewModelFactory by lazy { ViewModelFactory.getInstance(this) }
    private val viewModel: DetailsEventViewModel by viewModels { factory }

    companion object {
        const val ID = "id"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsEventBinding.inflate(layoutInflater)

        val idEvent = intent.getIntExtra(ID, 0)

        viewModel.findEventDetail(idEvent)

        viewModel.eventDetail.observe(this) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    setDetailEvent(result.data)
                    setupBookmarkButton(result.data)
                }

                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(
                        this,
                        "Terjadi kesalahan: ${result.error}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                null -> binding.progressBar.visibility = View.GONE
            }
        }
        setContentView(binding.root)
    }

    private fun setDetailEvent(details: EventEntity) {
        val avail = details.quota - details.registrants
        binding.apply {
            cardDescription.text = details.name
            cardQouta.text = avail.toString()
            cardDate.text = details.beginTime
            cardEndDate.text = details.endTime
            titleAuthor.text = details.ownerName
            titleDesc.text =
                HtmlCompat.fromHtml(
                    details.description,
                    HtmlCompat.FROM_HTML_MODE_LEGACY
                )
            Glide.with(this@DetailsEventActivity)
                .load(details.mediaCover)
                .into(binding.cardDetailImage)

            registerButton.setOnClickListener() {
                val registerLink = Intent(Intent.ACTION_VIEW, Uri.parse(details.link))
                startActivity(registerLink)
            }
        }
    }

    private fun setupBookmarkButton(event: EventEntity) {
        val ivBookmark = binding.ivBookmark
        updateBookmarkIcon(event.isBookmarked, ivBookmark)

        ivBookmark.setOnClickListener {
            event.isBookmarked = !event.isBookmarked
            updateBookmarkIcon(event.isBookmarked, ivBookmark)

            if (event.isBookmarked) {
                viewModel.saveEvent(event)
            } else {
                viewModel.deleteEvent(event)
            }
        }
    }

    private fun updateBookmarkIcon(isBookmarked: Boolean, ivBookmark: ImageView) {
        val drawable = ContextCompat.getDrawable(
            this,
            if (isBookmarked) R.drawable.baseline_favorite_24 else R.drawable.baseline_favorite_border_24
        )
        if (isDarkMode()) {
            DrawableCompat.setTint(drawable!!, Color.WHITE)
        }
        ivBookmark.setImageDrawable(drawable)
    }

    private fun isDarkMode(): Boolean {
        return (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
    }
}
