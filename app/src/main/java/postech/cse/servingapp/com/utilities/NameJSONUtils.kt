package postech.cse.servingapp.com.utilities

import org.json.JSONArray
import postech.cse.servingapp.com.listdata.Name

object NameJSONUtils {
    fun getMenuDataFromJSON(menuJSONstr: String?): Array<Name>?{
        val namedataArray = JSONArray(menuJSONstr)
        var ret = Array<Name>(namedataArray.length(), {Name("", 0)})

        for(i in 0 until namedataArray.length()){
            val namedata = namedataArray.getJSONObject(i)

            ret[i].name = namedata.getString("name")
            ret[i].total = namedata.getInt("total")
        }

        return ret
    }
}