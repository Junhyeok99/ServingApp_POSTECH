package postech.cse.servingapp.com

import android.content.Intent
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import postech.cse.servingapp.com.adapter.MenuListAdapter
import postech.cse.servingapp.com.listdata.Menu
import postech.cse.servingapp.com.utilities.MenuJSONUtils
import postech.cse.servingapp.com.utilities.NetworkUtils

class OrderActivity : AppCompatActivity() {

    private var mRecyclerView: RecyclerView? = null
    private var mMenuListAdapter: MenuListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mMenuListAdapter = MenuListAdapter()
        mRecyclerView = findViewById(R.id.list_menu) as RecyclerView
        mRecyclerView!!.layoutManager = layoutManager
        mRecyclerView!!.setHasFixedSize(true)
        mRecyclerView!!.adapter = mMenuListAdapter

        //update_menu_data()
        FetchMenuDataTask().execute()
    }

    fun update_menu_data(){
        val stringRequest = object : StringRequest(Request.Method.POST, "http://sjin9805.cafe24.com/postech/menu_get_data.php", //php파일 접속
                Response.Listener<String> { response ->
                    try {
                        var storelist: Array<Menu>? = null

                        val array = JSONArray(response)
                        storelist = Array<Menu>(array.length(), {Menu("", 0, 0)})

                        for(i in 0 until storelist.size){
                            val menu = array.getJSONObject(i)
                            storelist[i].name = menu.getString("name")
                            storelist[i].price = menu.getInt("price")
                            storelist[i].selled = menu.getInt("selled")
                        }

                        mMenuListAdapter!!.setMenuListData(storelist)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener{}){
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                return params
            }
        }


        Volley.newRequestQueue(this).add(stringRequest)
    }

    inner class FetchMenuDataTask: AsyncTask<Void, Void, Array<Menu>>() {

        override fun doInBackground(vararg p0: Void?): Array<Menu>? {
            val menuRequestURL = NetworkUtils.buildUrl()

            try {
                val menudataResponse = NetworkUtils.getResponseFromHttpUrl(menuRequestURL)

                return MenuJSONUtils.getMenuDataFronJSON(this@OrderActivity, menudataResponse)
            }
            catch (e: Exception){
                e.printStackTrace()
                return null
            }
        }

        override fun onPostExecute(result: Array<Menu>?) {
            if(result != null)
                mMenuListAdapter!!.setMenuListData(result)
        }
    }

}
