package com.apero.videoeditor.ui.home

import android.annotation.SuppressLint
import android.media.*
import android.os.Bundle
import android.view.Surface
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.FrameLayout
import com.apero.videoeditor.R
import com.apero.videoeditor.core.BaseActivity
import com.apero.videoeditor.databinding.ActivityDecodeBinding
import java.io.IOException
import java.nio.ByteBuffer


class DecodeActivity : BaseActivity<DecodeViewModel, ActivityDecodeBinding>(DecodeViewModel::class.java),
    SurfaceHolder.Callback {
    private val sample = "/storage/emulated/0/Download/MeCutVideo/atomm.mp4"
    private var mPlayer: PlayerThread? = null
    override fun surfaceCreated(holder: SurfaceHolder) {}
    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        if (mPlayer == null) {
            mPlayer = PlayerThread(holder.surface)
            mPlayer!!.start()
        }
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        if (mPlayer != null) {
            mPlayer!!.interrupt()
        }
    }

    override fun getLayoutRes(): Int {
        return R.layout.activity_decode
    }

    override fun initView() {
        val sv =  binding.sfView
        sv.holder.addCallback(this)
    }

    //đoạn này xử lí tách video
    private inner class PlayerThread(private val surface: Surface) : Thread() {
        private var codec: MediaCodec? = null
        override fun run() {
            val mediaExtractor = MediaExtractor()
            try {
                mediaExtractor.setDataSource("/storage/emulated/0/Download/MeCutVideo/atomm.mp4")
            } catch (e: IOException) {
                e.printStackTrace()
            }
            for (i in 0 until mediaExtractor.trackCount) {
                val format = mediaExtractor.getTrackFormat(i)
                val mime = format.getString(MediaFormat.KEY_MIME)
                if (mime!!.startsWith("video/")) {
                    mediaExtractor.selectTrack(i)
                    try {
                        codec = MediaCodec.createDecoderByType(mime)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                    codec!!.configure(format, surface, null, 0)
                    break
                }
            }
            if (codec == null) {
                return
            }
            codec!!.start()
            val info = MediaCodec.BufferInfo()
            var isEOS = false
            val startMs = System.currentTimeMillis()
            while (!interrupted()) {
                if (!isEOS) {
                    val inputIndex = codec!!.dequeueInputBuffer(10000)
                    if (inputIndex >= 0) {
                        val buffer = codec!!.getInputBuffer(inputIndex)
                        val sampleSize = mediaExtractor.readSampleData(buffer!!, 0)
                        if (sampleSize < 0) {
                            codec!!.queueInputBuffer(
                                inputIndex,
                                0,
                                0,
                                0,
                                MediaCodec.BUFFER_FLAG_END_OF_STREAM
                            )
                            isEOS = true
                        } else {
                            codec!!.queueInputBuffer(
                                inputIndex,
                                0,
                                sampleSize,
                                mediaExtractor.sampleTime,
                                0
                            )
                            mediaExtractor.advance()
                        }
                    }
                }
                val outIndex = codec!!.dequeueOutputBuffer(info, 10000)
                when (outIndex) {
                    MediaCodec.INFO_OUTPUT_FORMAT_CHANGED, MediaCodec.INFO_TRY_AGAIN_LATER -> {}
                    else -> {
                        while (info.presentationTimeUs / 1000 > System.currentTimeMillis() - startMs) {
                            try {
                                sleep(10)
                            } catch (e: InterruptedException) {
                                e.printStackTrace()
                                break
                            }
                        }
                        codec!!.releaseOutputBuffer(outIndex, true)
                    }
                }
                if (info.flags and MediaCodec.BUFFER_FLAG_END_OF_STREAM != 0) {
                    break
                }
            }
            codec!!.stop()
            codec!!.release()
            mediaExtractor.release()
        }
    }

    //đoạn này xử lí cả audio và video
    @SuppressLint("NewApi", "WrongConstant")
    @Throws(IOException::class)
    fun genVideoUsingMuxer(
        srcPath: String?,
        dstPath: String?,
        startMs: Int,
        endMs: Int,
        useAudio: Boolean,
        useVideo: Boolean
    ) {
        // Set up MediaExtractor to read from the source.
        val extractor = MediaExtractor()
        extractor.setDataSource(srcPath!!)
        val trackCount = extractor.trackCount
        // Set up MediaMuxer for the destination.
        val muxer: MediaMuxer
        muxer = MediaMuxer(dstPath!!, MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4)
        // Set up the tracks and retrieve the max buffer size for selected
        // tracks.
        val indexMap = HashMap<Int, Int>(trackCount)
        var bufferSize = -1
        for (i in 0 until trackCount) {
            val format = extractor.getTrackFormat(i)
            val mime = format.getString(MediaFormat.KEY_MIME)
            var selectCurrentTrack = false
            if (mime!!.startsWith("audio/") && useAudio) {
                selectCurrentTrack = true
            } else if (mime.startsWith("video/") && useVideo) {
                selectCurrentTrack = true
            }
            if (selectCurrentTrack) {
                extractor.selectTrack(i)
                val dstIndex = muxer.addTrack(format)
                indexMap[i] = dstIndex
                if (format.containsKey(MediaFormat.KEY_MAX_INPUT_SIZE)) {
                    val newSize = format.getInteger(MediaFormat.KEY_MAX_INPUT_SIZE)
                    bufferSize = if (newSize > bufferSize) newSize else bufferSize
                }
            }
        }
        if (bufferSize < 0) {
            bufferSize = 1024
        }
        // Set up the orientation and starting time for extractor.
        val retrieverSrc = MediaMetadataRetriever()
        retrieverSrc.setDataSource(srcPath)
        val degreesString =
            retrieverSrc.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_ROTATION)
        if (degreesString != null) {
            val degrees = degreesString.toInt()
            if (degrees >= 0) {
                muxer.setOrientationHint(degrees)
            }
        }
        if (startMs > 0) {
            extractor.seekTo((startMs * 1000).toLong(), MediaExtractor.SEEK_TO_CLOSEST_SYNC)
        }
        // Copy the samples from MediaExtractor to MediaMuxer. We will loop
        // for copying each sample and stop when we get to the end of the source
        // file or exceed the end time of the trimming.
        val offset = 0
        var trackIndex = -1
        val dstBuf = ByteBuffer.allocate(bufferSize)
        val bufferInfo = MediaCodec.BufferInfo()
        muxer.start()
        while (true) {
            bufferInfo.offset = offset
            bufferInfo.size = extractor.readSampleData(dstBuf, offset)
            if (bufferInfo.size < 0) {
                bufferInfo.size = 0
                break
            } else {
                bufferInfo.presentationTimeUs = extractor.sampleTime
                if (endMs > 0 && bufferInfo.presentationTimeUs > endMs * 1000) {
                    break
                } else {
                    bufferInfo.flags = extractor.sampleFlags
                    trackIndex = extractor.sampleTrackIndex
                    muxer.writeSampleData(indexMap[trackIndex]!!, dstBuf, bufferInfo)
                    extractor.advance()
                }
            }
        }
        muxer.stop()
        muxer.release()
        return
    }

    override fun getStatusBar(): Pair<Int, Boolean> {
        return Pair(first = R.color.globalBlack, second = false)
    }

    override fun initViewModel(viewModel: DecodeViewModel) {

    }
}