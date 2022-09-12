package com.mahmudul.movieapp.ui.videoplayer

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.ads.interactivemedia.v3.api.AdErrorEvent
import com.google.ads.interactivemedia.v3.api.AdEvent
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.ext.ima.ImaAdsLoader
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory
import com.google.android.exoplayer2.source.MediaSourceFactory
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.upstream.HttpDataSource
import com.google.android.exoplayer2.util.Log
import com.google.android.exoplayer2.util.MimeTypes
import com.google.android.exoplayer2.util.Util
import com.mahmudul.movieapp.R
import com.mahmudul.movieapp.databinding.FragmentVideoPlayerBinding


class VideoPlayerFragment : Fragment(), Player.EventListener,
    AdErrorEvent.AdErrorListener, AdEvent.AdEventListener {

    private lateinit var binding: FragmentVideoPlayerBinding

    private var player: SimpleExoPlayer? = null
    private var playWhenReady = true
    private var currentWindow = 0
    private var playbackPosition: Long = 0
    private var adsLoader: ImaAdsLoader? = null

    val ADS_URL =
        "http://pubads.g.doubleclick.net/gampad/ads?sz=640x480&iu=/124319096/external/single_ad_samples&ciu_szs=300x250&impl=s&gdfp_req=1&env=vp&output=vast&unviewed_position_start=1&cust_params=deployment%3Ddevsite%26sample_ct%3Dskippablelinear&correlator="


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_video_player, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentVideoPlayerBinding.bind(view)

        binding.videoPlayerToolBar.toolbarTitle.text = "Video Player"
        binding.videoPlayerToolBar.toolbarBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

    }

    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT >= 24) {
            initializePlayer()
        }
    }

    override fun onResume() {
        super.onResume()
       // hideSystemUi()
        if (Util.SDK_INT < 24 || player == null) {
            initializePlayer()
        }
    }

    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT < 24) {
            releasePlayer()
        }
    }

    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT >= 24) {
            releasePlayer()
        }
    }

    override fun onPlaybackStateChanged(playbackState: Int) {
        val stateString: String
        stateString = when (playbackState) {
            ExoPlayer.STATE_IDLE -> "ExoPlayer.STATE_IDLE "
            ExoPlayer.STATE_BUFFERING -> "ExoPlayer.STATE_BUFFERING "
            ExoPlayer.STATE_READY -> "ExoPlayer.STATE_READY "
            ExoPlayer.STATE_ENDED -> "ExoPlayer.STATE_ENDED "
            else -> "UNKNOWN_STATE "
        }
        Log.d("TAG", "changed state to $stateString")
    }

    override fun onAdEvent(adEvent: AdEvent?) {
        adEvent?.type?.name.let { Log.d("ADS", "AdEvent listener :: $it!!") }
    }


    override fun onAdError(adErrorEvent: AdErrorEvent?) {
        adErrorEvent?.error?.message?.let { Log.d("ADS", "AdError listener :: $it!!") }
    }

    @SuppressLint("InlinedApi")
    private fun hideSystemUi() {
        binding.videoPlayer.systemUiVisibility = (View.SYSTEM_UI_FLAG_LOW_PROFILE
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
    }

    private fun releasePlayer() {
        if (player != null) {
            playWhenReady = player!!.playWhenReady
            playbackPosition = player!!.currentPosition
            currentWindow = player!!.currentWindowIndex
            player!!.removeListener(this);
            player!!.release()
            player = null
        }
    }


    fun initializePlayer() {

        val httpDataSourceFactory: HttpDataSource.Factory = DefaultHttpDataSourceFactory(
            ExoPlayerLibraryInfo.DEFAULT_USER_AGENT,
            DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS,
            DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS,  /* allowCrossProtocolRedirects= */
            true
        )
        val adsProvider: DefaultMediaSourceFactory.AdsLoaderProvider =
            DefaultMediaSourceFactory.AdsLoaderProvider {
                getAdsLoaderProvider()
            }


        val mediaSourceFactory: MediaSourceFactory = DefaultMediaSourceFactory(requireContext())
            .setAdsLoaderProvider(adsProvider)
            .setAdViewProvider(binding.videoPlayer)
            .setDrmHttpDataSourceFactory(httpDataSourceFactory)

        player = SimpleExoPlayer.Builder(requireContext())
            .setMediaSourceFactory(mediaSourceFactory)
            .build()
        binding.videoPlayer.player = player
        player!!.setMediaItem(getMediaItem())

        // prepare media list
        player!!.playWhenReady = playWhenReady;
        player!!.seekTo(currentWindow, playbackPosition);
        player!!.addListener(this);
        player!!.prepare();
    }


    fun getMediaItem(): MediaItem {
        // add one media item
        // val mediaItem: MediaItem = MediaItem.fromUri(getString(R.string.media_url_mp3))
        return MediaItem.Builder()
            .setUri(Uri.parse("https://datanapps.com/public/dnarestapi/media/videos/MyExerciseMotivation.mp4"))
            .setMimeType(MimeTypes.BASE_TYPE_VIDEO)
            .setAdTagUri(Uri.parse(ADS_URL))
            .build()
    }

    private fun getAdsLoaderProvider(): ImaAdsLoader {
        adsLoader = ImaAdsLoader.Builder(requireContext())
            .setAdErrorListener(this)
            .setAdEventListener(this)
            .build()
        adsLoader!!.setPlayer(player)
        return adsLoader!!
    }
}