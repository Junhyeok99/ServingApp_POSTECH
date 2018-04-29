package postech.cse.servingapp.com

import android.content.Intent
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
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

class OrderActivity : AppCompatActivity(), MenuListAdapter.MenuOnClickListener {

    private var total: Int = 0
    private var mRecyclerView: RecyclerView? = null
    private var mMenuListAdapter: MenuListAdapter? = null
    private var total_textview: TextView? = null

    override fun onUpPositionClicked(position: Int) {
        changeTotal(position, 1)
    }

    override fun onDownPositionClicked(position: Int) {
        changeTotal(position, -1)
    }

    fun changeTotal(position: Int, multiply: Int){
        var updateMenuArray = mMenuListAdapter!!.getMenuListData()
        updateMenuArray!![position].selled += multiply

        if(updateMenuArray!![position].selled >= 0){
            total += updateMenuArray!![position].price * multiply
            total_textview!!.text = total.toString()
            mMenuListAdapter!!.setMenuListData(updateMenuArray)
        }
        else
            updateMenuArray!![position].selled = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)
        total_textview = findViewById(R.id.tv_total) as TextView
        total_textview!!.text = total.toString()

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mMenuListAdapter = MenuListAdapter(this)
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

    fun pay_button_click(v : View){
        val intent = Intent(this, PayActivity::class.java)
        startActivity(intent)
    }

    inner class FetchMenuDataTask: AsyncTask<Void, Void, Array<Menu>>() {

        override fun doInBackground(vararg p0: Void?): Array<Menu>? {
            val menuRequestURL = NetworkUtils.buildUrl()

            try {
                val menudataResponse = NetworkUtils.getResponseFromHttpUrl(menuRequestURL)

                return MenuJSONUtils.getMenuDataFromJSON(this@OrderActivity, menudataResponse)
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
