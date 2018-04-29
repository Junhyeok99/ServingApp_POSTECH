package postech.cse.servingapp.com

import android.content.Intent
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
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
import postech.cse.servingapp.com.listdata.StoreMenu
import postech.cse.servingapp.com.utilities.MenuJSONUtils
import postech.cse.servingapp.com.utilities.NetworkUtils

class OrderActivity : AppCompatActivity(), MenuListAdapter.MenuOnClickListener {
    companion object{
        var present_screen: OrderActivity? = null
    }
    private var total: Int = 0
    private var mRecyclerView: RecyclerView? = null
    private var mMenuListAdapter: MenuListAdapter? = null
    private var mLoadingIndicator: ProgressBar? = null

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

        present_screen = this
        mLoadingIndicator = findViewById(R.id.pg_menu_list) as ProgressBar
        total_textview = findViewById(R.id.tv_total) as TextView

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mMenuListAdapter = MenuListAdapter(this)
        mRecyclerView = findViewById(R.id.list_menu) as RecyclerView
        mRecyclerView!!.layoutManager = layoutManager
        mRecyclerView!!.setHasFixedSize(true)
        mRecyclerView!!.adapter = mMenuListAdapter

        //update_menu_data()
        FetchMenuDataTask().execute()
    }

    fun pay_button_click(v : View){
        val intent = Intent(this, PayActivity::class.java)
        intent.putExtra("OrderList", mMenuListAdapter!!.getMenuListData())
        startActivity(intent)
    }

    inner class FetchMenuDataTask: AsyncTask<Void, Void, Array<StoreMenu>>() {

        override fun onPreExecute() {
            super.onPreExecute()
            mLoadingIndicator!!.visibility = View.VISIBLE
        }
        override fun doInBackground(vararg p0: Void?): Array<StoreMenu>? {
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

        override fun onPostExecute(result: Array<StoreMenu>?) {
            mLoadingIndicator!!.visibility = View.INVISIBLE
            if(result != null) {
                mMenuListAdapter!!.setMenuListData(result)
                total = 0
                total_textview!!.text = "0"
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        /* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
        val inflater = menuInflater
        /* Use the inflater's inflate method to inflate our menu layout to this menu */
        inflater.inflate(R.menu.order_menu, menu)
        /* Return true so that the menu is displayed in the Toolbar */
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.action_refresh) {
            FetchMenuDataTask().execute()
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}
