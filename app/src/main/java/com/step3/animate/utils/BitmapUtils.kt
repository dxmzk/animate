package com.step3.animate.utils

import android.content.res.Resources
import android.graphics.*
import android.graphics.Bitmap.CompressFormat
import android.graphics.drawable.Drawable
import android.media.MediaMetadataRetriever
import android.graphics.Bitmap.Config;
import android.util.Log
import java.io.*
import java.nio.Buffer
import java.nio.ByteBuffer


/**
 * Author: Meng
 * Date: 2022/12/16
 * Desc:
 */
class BitmapUtils {
    private val TAG = "BitmapUtil"

    /**
     * 根据资源id获取图片,并进行压缩
     *
     * @param res
     * @param resId
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    fun decodeSampleBitmapFromResource(
        res: Resources?, resId: Int,
        reqWidth: Int, reqHeight: Int
    ): Bitmap? {
        val opts = BitmapFactory.Options()
        opts.inJustDecodeBounds = true
        BitmapFactory.decodeResource(res, resId, opts)
        val inSampleSize: Int = calculateInSampleSize(opts, reqWidth, reqHeight)
        opts.inSampleSize = inSampleSize
        opts.inJustDecodeBounds = false
        return BitmapFactory.decodeResource(res, resId, opts)
    }

    /**
     * 根据文件名获取图片,并进行压缩
     *
     * @param pathName
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    fun decodeSampleBitmapFromResource(
        pathName: String?,
        reqWidth: Int, reqHeight: Int
    ): Bitmap? {
        val opts = BitmapFactory.Options()
        opts.inJustDecodeBounds = true
        BitmapFactory.decodeFile(pathName, opts)
        val inSampleSize = calculateInSampleSize(opts, reqWidth, reqHeight)
        opts.inSampleSize = inSampleSize
        opts.inJustDecodeBounds = false
        return BitmapFactory.decodeFile(pathName, opts)
    }

    /**
     * 从byte数组中获取图片并压缩
     *
     * @param data
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    fun decodeSampleBitmapFromByteArray(
        data: ByteArray,
        reqWidth: Int, reqHeight: Int
    ): Bitmap? {
        val opts = BitmapFactory.Options()
        opts.inJustDecodeBounds = true
        BitmapFactory.decodeByteArray(data, 0, data.size, opts)
        val inSampleSize = calculateInSampleSize(opts, reqWidth, reqHeight)
        opts.inSampleSize = inSampleSize
        opts.inJustDecodeBounds = false
        return BitmapFactory.decodeByteArray(data, 0, data.size, opts)
    }

    /**
     * 计算缩放比例
     *
     * @param opts
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private fun calculateInSampleSize(
        opts: BitmapFactory.Options?, reqWidth: Int,
        reqHeight: Int
    ): Int {
        if (opts == null) return 1
        var inSampleSize = 1
        val realWidth: Int = opts.outWidth
        val realHeight: Int = opts.outHeight
        if (realHeight > reqHeight || realWidth > reqWidth) {
            val widthRatio = realWidth / reqWidth
            val heightRatio = realHeight / reqHeight
            inSampleSize = if (heightRatio > widthRatio) widthRatio else heightRatio
        }
        return inSampleSize
    }

    /**
     * 将drawable转换为bitmap
     *
     * @param drawable
     * @return
     */
    fun drawableToBitmap(drawable: Drawable): Bitmap? {
        val width = drawable.intrinsicWidth
        val height = drawable.intrinsicHeight
        val bitmap = Bitmap.createBitmap(
            width, height,
            if (drawable.opacity != PixelFormat.OPAQUE) Config.ARGB_4444 else Config.RGB_565
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, width, height)
        drawable.draw(canvas)
        return bitmap
    }

    /**
     * 缩放图片
     *
     * @param bitmap
     * @param w
     * @param h
     * @return
     */
    fun zoomBitmap(bitmap: Bitmap?, w: Int, h: Int): Bitmap? {
        var newBitmap: Bitmap? = null
        if (bitmap != null) {
            val width = bitmap.width
            val height = bitmap.height
            val matrix = Matrix()
            val scaleWidth = w.toFloat() / width
            val scaleHeight = h.toFloat() / height
            matrix.postScale(scaleWidth, scaleHeight)
            newBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true)
        }
        return newBitmap
    }

    /**
     * 按比例缩放图片
     *
     * @param bitmap
     * @param scale
     * @return
     */
    fun zoomBitmap(bitmap: Bitmap?, scale: Float): Bitmap? {
        var scale = scale
        if (scale <= 0) scale = 1f
        var newBitmap: Bitmap? = null
        if (bitmap != null) {
            val width = bitmap.width
            val height = bitmap.height
            val matrix = Matrix()
            matrix.postScale(scale, scale)
            newBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true)
        }
        return newBitmap
    }

    /**
     * 从图片中获取字节数组
     */
    fun getBytes(bitmap: Bitmap): ByteArray? {
        val buffer = ByteArray(bitmap.rowBytes * bitmap.height)
        val byteBuffer: Buffer = ByteBuffer.wrap(buffer)
        bitmap.copyPixelsToBuffer(byteBuffer)
        return buffer
    }

    /**
     * 将字节数组加载到图片中
     *
     * @param bitmap
     * @param buffer
     */
    fun loadFromBytes(bitmap: Bitmap, buffer: ByteArray?): Bitmap? {
        val byteBuffer: Buffer = ByteBuffer.wrap(buffer)
        bitmap.copyPixelsFromBuffer(byteBuffer)
        return bitmap
    }

    fun loadFromBitmap(bitmap: Bitmap, bitmap_src: Bitmap): Bitmap? {
        return loadFromBitmap(bitmap, bitmap_src, true)
    }

    /**
     * 从图片加载到另一张图片
     *
     * @param bitmap
     * @param bitmap_src
     * @return
     */
    fun loadFromBitmap(bitmap: Bitmap, bitmap_src: Bitmap, clearBmp: Boolean): Bitmap? {
        if (clearBmp) bitmap.eraseColor(Color.TRANSPARENT)
        //		bitmap_src = zoomBitmap(bitmap_src, bitmap.getWidth(), bitmap.getHeight());
//		loadFromBytes(bitmap, getBytes(bitmap_src));
        val canvas = Canvas(bitmap)
        canvas.setDrawFilter(
            PaintFlagsDrawFilter(
                0,
                Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG
            )
        )
        val rw = bitmap.width.toFloat() / bitmap_src.width
        val rh = bitmap.height.toFloat() / bitmap_src.height
        canvas.scale(rw, rh)
        canvas.drawBitmap(bitmap_src, 0f, 0f, null)
        return bitmap
    }

    /**
     * 将图片转化为字节数组
     *
     * @param bitmap
     * @return
     */
    fun toBytes(bitmap: Bitmap): ByteArray? {
        val baos = ByteArrayOutputStream()
        bitmap.compress(CompressFormat.PNG, 100, baos)
        return baos.toByteArray()
    }

    /**
     * 将字节数组转化为图片
     *
     * @param bytes
     * @return
     */
    fun toBitmap(bytes: ByteArray): Bitmap? {
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }

    /**
     * 压缩图片为png
     *
     * @param bitmap
     * @return
     */
    fun getBytesByPNG(bitmap: Bitmap): ByteArray? {
        var out: ByteArrayOutputStream? = null
        var buffer: ByteArray? = null
        try {
            out = ByteArrayOutputStream()
            bitmap.compress(CompressFormat.PNG, 100, out)
            out.flush()
            buffer = out.toByteArray()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (out != null) try {
                out.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return buffer
    }

    /**
     * 解析图片从png字节里面
     *
     * @param buffer
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    fun loadFromBytesByPNG(
        buffer: ByteArray?,
        reqWidth: Int,
        reqHeight: Int
    ): Bitmap? {
        if (buffer == null) return null
        val opts = BitmapFactory.Options()
        opts.inJustDecodeBounds = true
        BitmapFactory.decodeByteArray(buffer, 0, buffer.size, opts)
        val inSampleSize = calculateInSampleSize(opts, reqWidth, reqHeight)
        opts.inSampleSize = inSampleSize
        opts.inJustDecodeBounds = false
        return BitmapFactory.decodeByteArray(buffer, 0, buffer.size, opts)
    }

    /**
     * 解析图片从png字节里面
     *
     * @param buffer
     * @return
     */
    fun loadFromBytesByPNG(buffer: ByteArray): Bitmap? {
        return BitmapFactory.decodeByteArray(buffer, 0, buffer.size)
    }

    /**
     * 保存图片
     *
     * @param path
     * @param bmp
     */
    fun saveBitmapAsJPEG(path: String?, bmp: Bitmap) {
        val file = File(path)
        var out: FileOutputStream? = null
        try {
            out = FileOutputStream(file)
            bmp.compress(CompressFormat.JPEG, 100, out)
            out.flush()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (out != null) {
                try {
                    out.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    /**
     * 保存图片
     *
     * @param path
     * @param bmp
     */
    fun saveBitmapAsPNG(path: String?, bmp: Bitmap) {
        val file = File(path)
        var out: FileOutputStream? = null
        try {
            out = FileOutputStream(file)
            bmp.compress(CompressFormat.PNG, 100, out)
            out.flush()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (out != null) {
                try {
                    out.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    /**
     * 创建图片
     *
     * @param width
     * @param height
     * @return
     */
    fun create(width: Int, height: Int): Bitmap? {
        return Bitmap.createBitmap(width, height, Config.ARGB_4444)
    }

    fun getImageSize(path: String): IntArray? {
        if (path.endsWith(".mp4")) {
            return getVideoSize(path)
        }
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        val bitmap = BitmapFactory.decodeFile(path, options) // 此时返回的bitmap为null
        return intArrayOf(options.outWidth, options.outHeight)
    }

    private fun getVideoSize(path: String): IntArray? {
        val mmr = MediaMetadataRetriever()
        val size = intArrayOf(-1, -1)
        try {
            mmr.setDataSource(path)
            //			String duration = mmr.extractMetadata(android.media.MediaMetadataRetriever.METADATA_KEY_DURATION);
            val width = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH)
            val height = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT)
            size[0] = width!!.toInt()
            size[1] = height!!.toInt()
        } catch (ex: Exception) {
            Log.e(TAG, "MediaMetadataRetriever exception $ex")
        } finally {
            mmr.release()
        }
        return size
    }
}