package com.apero.videoeditor.ui.home

import android.content.Intent
import android.graphics.SurfaceTexture
import android.media.MediaCodec
import android.media.MediaCodecList
import android.media.MediaExtractor
import android.media.MediaFormat
import android.util.Log
import android.view.Surface
import com.apero.videoeditor.R
import com.apero.videoeditor.core.BaseFragment
import com.apero.videoeditor.databinding.HomeFragmentBinding
import com.google.android.datatransport.cct.internal.LogEvent
import java.io.File
import java.io.IOException


class HomeFragment : BaseFragment<HomeViewModel, HomeFragmentBinding>(
    R.layout.home_fragment,
    HomeViewModel::class.java
) {
    var codec: MediaCodec? = null

    override fun initView() {
        super.initView()
        /*val file = File("/storage/emulated/0/Download/MeCutVideo/atomm.mp4")
        if (file.exists()) {
            Log.e("TrungAtom", "initView: ")
            val mediaExtractor = MediaExtractor()
            mediaExtractor.setDataSource("/storage/emulated/0/Download/MeCutVideo/atomm.mp4")
            //分离出音轨和视轨
            val trackCount: Int = mediaExtractor.trackCount
            for (i in 0 until trackCount) {
                val format = mediaExtractor.getTrackFormat(i)
                val mime: String = format.getString(MediaFormat.KEY_MIME).toString()
                if (mime.startsWith("audio/")) {
                    mediaExtractor.selectTrack(i)
                    createMediaCodec(mediaExtractor.getTrackFormat(i))
                }
                *//*if (mime.startsWith("video/")) {
                    Log.e("TrungAtom", "initView: ${mediaExtractor.getTrackFormat(i)}", )
                    createMediaCodec(mediaExtractor.getTrackFormat(i))
                }*//*
            }
        } else {
            Log.e("TrungAtom", "initView: is not exist")
        }
        run()*/
        binding.txtClick.setOnClickListener {
            val intent = Intent(context, DecodeActivity::class.java)
            startActivity(intent)
        }


    }

    private fun createMediaCodec(format: MediaFormat?): MediaCodec? {
        val codecName = MediaCodecList(MediaCodecList.ALL_CODECS).findDecoderForFormat(format)
        try {
            val mime = format!!.getString(MediaFormat.KEY_MIME)

            if (codecName != null) {
                codec = MediaCodec.createByCodecName(codecName)
            } else if (mime != null) {
                // may be throw IllegalArgumentException
                codec = MediaCodec.createDecoderByType(mime)
            }
            codec!!.configure(format, null, null, 0)
            codec!!.start()
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
        Log.e("TrungAtom", "createMediaCodec: $codec", )
        return codec
    }

    fun run() {

    }

}
