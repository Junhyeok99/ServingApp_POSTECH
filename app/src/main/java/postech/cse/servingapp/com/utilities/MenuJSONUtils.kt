package postech.cse.servingapp.com.utilities

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject
import postech.cse.servingapp.com.listdata.Menu

object MenuJSONUtils {

    fun getMenuDataFronJSON(context: Context, menuJSONstr: String?): Array<Menu>?{
        var ret: Array<Menu>? = null

        val menudataArray = JSONArray(menuJSONstr)
        ret = Array<Menu>(menudataArray.length(), {Menu("", 0, 0)})

        for(i in 0 until menudataArray.length()){
            val menudata = menudataArray.getJSONObject(i)

            ret[i].name = menudata.getString("name")
            ret[i].price = menudata.getInt("price")
            ret[i].selled = menudata.getInt("selled")
        }

        return ret
    }
}