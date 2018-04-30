package postech.cse.servingapp.com.utilities

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject
import postech.cse.servingapp.com.listdata.StoreMenu

object OrderJSONUtils {
    fun getOrderDataFromJSON(context: Context, successJSONstr: String?): Int{
        val resultData = JSONObject(successJSONstr)

        return resultData.getInt("success")
    }
}