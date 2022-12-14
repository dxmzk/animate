package com.step3.animate.ui.activity

import android.hardware.display.DisplayManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Surface
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.SeekBar
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.room.Transaction
import com.google.common.util.concurrent.ListenableFuture
import com.step3.animate.R
import com.step3.animate.modules.base.AppActivity
import com.step3.animate.modules.base.SeekBarChangeListener
import com.step3.animate.modules.room.entity.Animate
import com.step3.animate.modules.room.entity.Photo
import com.step3.animate.modules.store.AnimateStore
import com.step3.animate.modules.store.PhotoStore
import com.step3.animate.utils.Files
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * Author: Meng
 * Date: 2022/08/30
 * Desc: 拍照界面
 */
class CameraActivity : AppActivity() {
    private val TAG = "CameraActivity"
    private var animId = 0
    private var animItem: Animate? = null
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var camera: Camera
    private lateinit var photoStore: PhotoStore
    private lateinit var animStore: AnimateStore
    private var imageCapture: ImageCapture? = null
    private lateinit var photoView: ImageView
    private lateinit var btnView: View
    private lateinit var previewView: PreviewView
    private lateinit var savePath: String
    private var folder = ""
    private var imgUri: Uri? = null
    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_camera)
        cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        animId = intent.getIntExtra("pid", 0)
        folder = intent.getStringExtra("folder").toString()

        initView()
        getPhotoStore()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
        imageCapture = null
        cameraProviderFuture.cancel(true)
    }

    private fun onDisplayListener() {
        val displayListener = object : DisplayManager.DisplayListener {
            override fun onDisplayAdded(displayId: Int) {}
            override fun onDisplayRemoved(displayId: Int) {}
            override fun onDisplayChanged(displayId: Int){
//                imageCapture?.targetRotation = view.display.rotation
            }
        }
    }

    private fun initView() {
        previewView = findViewById<PreviewView>(R.id.camera_preview)
        previewView.implementationMode = PreviewView.ImplementationMode.COMPATIBLE
        previewView.scaleType = PreviewView.ScaleType.FIT_CENTER
        cameraExecutor = Executors.newSingleThreadExecutor()
        savePath = this.filesDir.path + "/animate/photo/${folder}"
        if (!File(savePath).exists()) {
            File(savePath).mkdirs()
        }
        photoView = findViewById<ImageView>(R.id.last_take_photo)
        btnView = findViewById<View>(R.id.take_photo_btn)
        btnView.setOnClickListener(View.OnClickListener { onTakePhoto() })
        findViewById<SeekBar>(R.id.photo_alpha_bar).setOnSeekBarChangeListener(object : SeekBarChangeListener() {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
            }
        })

        cameraProviderFuture.addListener(Runnable {
            val cameraProvider = cameraProviderFuture.get()
            bindPreview(cameraProvider)
        }, ContextCompat.getMainExecutor(this))

    }

    /**
     * 选择相机并绑定生命周期和用例
     *   创建并确认 CameraProvider 后，执行以下操作：
     *   - 创建 Preview。
     *   - 指定所需的相机 LensFacing 选项。
     *   - 将所选相机和任意用例绑定到生命周期。
     *   - 将 Preview 连接到 PreviewView。
     */
    private fun bindPreview(cameraProvider: ProcessCameraProvider?) {
        val rotation = if(previewView.display != null) previewView.display.rotation else Surface.ROTATION_0
        var preview: Preview = Preview.Builder()
            .setTargetAspectRatio(AspectRatio.RATIO_16_9)
            .setTargetRotation(rotation)
            .build()

        var cameraSelector: CameraSelector = CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
            .build()

        imageCapture = ImageCapture.Builder()
            .setTargetRotation(rotation)
            .setTargetAspectRatio(AspectRatio.RATIO_16_9)
            .setCaptureMode(ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY) // 优化照片质量
            .build()

        preview.setSurfaceProvider(previewView.surfaceProvider)

         camera = cameraProvider!!.bindToLifecycle(
            this as LifecycleOwner,
            cameraSelector,
            imageCapture,
            preview
        )
    }

    private fun onTakePhoto() {
        val name = Files.getImgName()
        val outputFileOptions = ImageCapture.OutputFileOptions.Builder(File(savePath, name)).build()
        btnView.isEnabled = false
        imageCapture?.takePicture(outputFileOptions, cameraExecutor,
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(error: ImageCaptureException) {
                    Log.i(TAG, "onError")
                    Log.i(TAG, error.toString())
                }

                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    Log.i(TAG, "onImageSaved") // 此处是子线程非UI线程
                    imgUri = outputFileResults.savedUri
                    photoStore.insert(Photo(animId, name, imgUri.toString()))
                    if(animItem != null) {
                        animItem!!.count += 1
                        animItem!!.cover = imgUri.toString()
                        animStore.update(animItem!!)
                    }

                    // 刷新状态
                    this@CameraActivity.runOnUiThread {
                        btnView.isEnabled = true
                        if (imgUri != null) {
                            photoView.setImageURI(imgUri)
                        }
                    }
                }
            })
    }

    @Transaction
    private fun getPhotoStore() {
        photoStore = PhotoStore(applicationContext)
        animStore = AnimateStore(applicationContext)
        if(animId > 0) {
            animItem = animStore.findById(animId)
        }
    }
}