package postech.cse.servingapp.com.utilities

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject
import postech.cse.servingapp.com.listdata.StoreMenu

object MenuJSONUtils {

    fun getMenuDataFromJSON(context: Context, menuJSONstr: String?): Array<StoreMenu>?{
        var ret: Array<StoreMenu>? = null

        val menudataArray = JSONArray(menuJSONstr)
        ret = Array<StoreMenu>(menudataArray.length(), {StoreMenu("", 0, 0)})

        for(i in 0 until menudataArray.length()){
            val menudata = menudataArray.getJSONObject(i)

            ret[i].name = menudata.getString("name")
            ret[i].price = menudata.getInt("price")
            ret[i].selled = 0//menudata.getInt("selled")
        }

        return ret
    }
}