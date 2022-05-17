package com.livingTechUSA.thatsnewstome.util

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.DialogInterface
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.appcompat.app.AlertDialog
import com.example.thatsnewstome.R
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException
import java.io.IOException


class Ui {
    companion object {

        /**
         * Return @ColorInt base on build version
         */
        @ColorInt
        fun getColorInt(context: Context, @ColorRes color: Int): Int {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                context.resources.getColor(color, null)
            } else {
                context.resources.getColor(color)
            }
        }
        fun isTablet(context: Context): Boolean {
            return context.resources.getBoolean(R.bool.is_tablet)
        }

        fun isLandscape(context: Context): Boolean {
            return context.resources.getBoolean(R.bool.is_landscape)
        }

        fun isPortrait(context: Context): Boolean {
            return context.resources.getBoolean(R.bool.is_portrait)
        }

        fun getMeasuredHeight(view: View): Int {
            view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
            return view.measuredHeight
        }

        /**
         * dismiss keyboard after scrolling away from the text field that has focus
         * @param view is the view to set the onTouchListener to
         * @param context is the context we are in
         */
        @JvmStatic
        fun dismissKeyboardOnTouch(view: View, context: Context) {
            view.setOnTouchListener { v, _ ->
                v.performClick()
                val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v.windowToken, 0)
                view.requestFocus()
                false
            }
        }

        @JvmStatic
        fun dismissKeyboard(activity: Activity) {
            activity.currentFocus?.let {
                val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(it.windowToken, 0)
            }
        }

        @JvmStatic
        fun showSoftKeyBoard(activity: Activity, editBox: EditText){
            val inputMethodManager: InputMethodManager =
                activity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            editBox.requestFocus()
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        }

        fun getDpFromPx(context: Context, px: Float): Float {
            return px / context.resources.displayMetrics.density
        }

        fun getPxFromDp(context: Context, dp: Float): Float {
            return dp * context.resources.displayMetrics.density
        }

        /**
         * if an image bitmap is in an orientation that is not 0 degrees, then shift the bitmap to the appropriate orientation
         */
        @Throws(IOException::class)
        fun rotateImageIfRequired(bitmap: Bitmap, context: Context, selectedImage: Uri?): Bitmap {

            if (selectedImage?.scheme == "content") {
                val projection = arrayOf(MediaStore.Images.ImageColumns.ORIENTATION)
                val c = context.contentResolver.query(selectedImage, projection, null, null, null)
                if (c != null && c.moveToFirst()) {
                    c.close()
                    return rotateImage(bitmap)
                }
                return bitmap
            } else {
                if (selectedImage != null) {
                    val ei = selectedImage.path?.let { ExifInterface(it) }
                    return when (ei?.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)) {
                        ExifInterface.ORIENTATION_ROTATE_90 -> rotateImage(bitmap)
                        ExifInterface.ORIENTATION_ROTATE_180 -> rotateImage(bitmap)
                        ExifInterface.ORIENTATION_ROTATE_270 -> rotateImage(bitmap)
                        else -> bitmap
                    }
                }
                return bitmap
            }
        }

        private fun rotateImage(bitmap: Bitmap): Bitmap {
            val matrix = Matrix()
            matrix.postRotate(0F)
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        }

        /**
         * gets the uri for a given bitmap
         */
        fun getImageUri(context: Context, bitmap: Bitmap): Uri {
            val bytes = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
            val path = MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, "Title", null)
            return Uri.parse(path)
        }

        /**
         * gets the bitmap for a given uri
         */
        fun getBitmap(file: Uri, cr: ContentResolver): Bitmap {
            lateinit var bitmap: Bitmap
            try {
                val inputStream = cr.openInputStream(file)
                bitmap = BitmapFactory.decodeStream(inputStream)
                // close stream
                try {
                    inputStream?.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
            return bitmap
        }
    }


}
