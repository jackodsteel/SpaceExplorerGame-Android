package nz.co.jacksteel.spaceexplorer.util

import android.os.AsyncTask
import android.util.Log
import java.io.BufferedInputStream
import java.lang.ref.WeakReference
import java.net.URL
import java.nio.charset.Charset
import javax.net.ssl.HttpsURLConnection
import kotlin.random.Random

class RandomSeedFetcher(callback: (Int) -> Unit) : AsyncTask<URL, Void, Int>() {

    private val callback = WeakReference(callback)

    private val url = "https://www.random.org/integers/?num=1&min=-9999999&max=9999999&col=1&base=10&format=plain&rnd=new"

    override fun doInBackground(vararg urls: URL): Int {
        Log.i("RandomSeedFetcher", "doInBackground")
        return getNumber(URL(url))
    }

    private fun getNumber(url: URL): Int {
        val connection = url.openConnection() as HttpsURLConnection
        return try {
            BufferedInputStream(connection.inputStream).readBytes().toString(Charset.defaultCharset()).toInt()
        } catch (e: Exception) {
            Random.nextInt()
        } finally {
            connection.disconnect()
        }
    }

    override fun onPostExecute(result: Int) {
        super.onPostExecute(result)
        Log.i("RandomSeedFetcher", "onPostExecute")
        callback.get()?.let { it(result) }
    }
}