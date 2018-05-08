package postech.cse.servingapp.com.utilities

import android.net.Uri
import android.util.Log
import postech.cse.servingapp.com.listdata.Order
import postech.cse.servingapp.com.listdata.StoreMenu

import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.Scanner

/**
 * These utilities will be used to communicate with the weather servers.
 */
object NetworkUtils {

    private val TAG = NetworkUtils::class.java!!.getSimpleName()
    private val MENU_BASE_URL = "http://sjin9805.cafe24.com/postech/menu_get_data.php"
    private val STATUS_MENU_BASE_URL = "http://sjin9805.cafe24.com/postech/menu_status_get_data.php"
    private val NAME_STATUS_BASE_URL = "http://sjin9805.cafe24.com/postech/name_status_get_data.php"
    private val ORDER_BASE_URL = "http://sjin9805.cafe24.com/postech/order_set_data.php"
    private val ORDER_CHECK_URL = "http://sjin9805.cafe24.com/postech/check_order_data.php"

    fun name_buildUrl(): URL? {
        val builtUri: Uri
        builtUri = Uri.parse(NAME_STATUS_BASE_URL).buildUpon().build()

        var url: URL? = null
        try {
            url = URL(builtUri.toString())
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }
        Log.v(TAG, "Built URI " + url!!)

        return url
    }

    fun buildUrl(check: Boolean): URL? {
        val builtUri: Uri
        if(check)
            builtUri = Uri.parse(MENU_BASE_URL).buildUpon().build()
        else
            builtUri = Uri.parse(STATUS_MENU_BASE_URL).buildUpon().build()

        var url: URL? = null
        try {
            url = URL(builtUri.toString())
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }
        Log.v(TAG, "Built URI " + url!!)

        return url
    }

    fun buildUrl(table_num: Int): URL?{
        var builtUri = Uri.parse(ORDER_CHECK_URL).buildUpon()
        builtUri = builtUri.appendQueryParameter("tablenum", table_num.toString())

        var url: URL? = null
        try {
            url = URL(builtUri.build().toString())
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }

        Log.v(TAG, "Built URI " + url!!)

        return url
    }

    fun buildUrl(input: Order, table_num: String, customername: String): URL? {
        var builtUri = Uri.parse(ORDER_BASE_URL).buildUpon()

        builtUri = builtUri.appendQueryParameter("menu", input.menu)
        builtUri = builtUri.appendQueryParameter("count", input.count.toString())
        builtUri = builtUri.appendQueryParameter("tablenum", table_num)
        builtUri = builtUri.appendQueryParameter("total", input.total.toString())
        builtUri = builtUri.appendQueryParameter("buyername", input.buyername)
        builtUri = builtUri.appendQueryParameter("customername", customername)

        var url: URL? = null
        try {
            url = URL(builtUri.build().toString())
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }

        Log.v(TAG, "Built URI " + url!!)

        return url
    }

    fun buildUrl(input: Array<StoreMenu>?, customer: String?, buyer: String?, table_num: Int, total: Int): URL? {
        var builtUri = Uri.parse(ORDER_BASE_URL).buildUpon()

        for(i in 0 until input!!.size){
            builtUri = builtUri.appendQueryParameter(input[i].name, input[i].selled.toString())
        }
        builtUri = builtUri.appendQueryParameter("tablenum", table_num.toString())
        builtUri = builtUri.appendQueryParameter("customername", customer).appendQueryParameter("buyername", buyer)
        builtUri = builtUri.appendQueryParameter("total", total.toString())

        var url: URL? = null
        try {
            url = URL(builtUri.build().toString())
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }
        Log.v(TAG, "Built URI " + url!!)

        return url
    }


    @Throws(IOException::class)
    fun getResponseFromHttpUrl(url: URL?): String? {
        val urlConnection = url?.openConnection() as HttpURLConnection
        try {
            val `in` = urlConnection.inputStream

            val scanner = Scanner(`in`)
            scanner.useDelimiter("\\A")

            val hasInput = scanner.hasNext()
            return if (hasInput) {
                scanner.next()
            } else {
                null
            }
        } finally {
            urlConnection.disconnect()
        }
    }
}