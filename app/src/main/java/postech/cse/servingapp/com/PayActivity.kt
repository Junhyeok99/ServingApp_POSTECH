package postech.cse.servingapp.com

import android.content.Intent
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import postech.cse.servingapp.com.adapter.OrderListAdapter
import postech.cse.servingapp.com.listdata.StoreMenu
import postech.cse.servingapp.com.utilities.NetworkUtils
import postech.cse.servingapp.com.utilities.OrderJSONUtils
import java.net.HttpURLConnection

class PayActivity : AppCompatActivity() {
    companion object {
        var present_screen: PayActivity? = null
    }
    private var OrderList: Array<StoreMenu>? = null
    private var mRecyclerView: RecyclerView? = null
    private var mOrderListAdapter: OrderListAdapter? = null
    private var total: Int = 0
    private var total_textview: TextView? = null
    private var table_num: TextInputEditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receipt)

        present_screen = this
        table_num = findViewById(R.id.et_table_num) as TextInputEditText
        total_textview = findViewById(R.id.tv_total) as TextView
        OrderList = intent.getSerializableExtra("OrderList") as Array<StoreMenu>

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mRecyclerView = findViewById(R.id.list_receipt) as RecyclerView
        mRecyclerView!!.layoutManager = layoutManager
        mRecyclerView!!.setHasFixedSize(true)
        mOrderListAdapter = OrderListAdapter()
        mRecyclerView!!.adapter = mOrderListAdapter

        update_order_data()
    }

    inner class PushOrderDataTask: AsyncTask<Void, Void, Int>(){
        override fun doInBackground(vararg p0: Void?): Int {
            var sendURL = NetworkUtils.buildUrl(mOrderListAdapter!!.getOrderListData(), Integer.parseInt(table_num!!.text.toString()), 0)
            try{
                var result = NetworkUtils.getResponseFromHttpUrl(sendURL)
                return OrderJSONUtils.getOrderDataFromJSON(applicationContext, result)
            }
            catch(e: Exception){
                e.printStackTrace()
                return -1
            }
        }

        override fun onPostExecute(result: Int) {
            Toast.makeText(applicationContext, result.toString(), Toast.LENGTH_SHORT).show()
            var intent = Intent(applicationContext, CompleteActivity::class.java)
            intent.putExtra("tablenum", table_num!!.text.toString())
            startActivity(intent)
        }

    }

    fun update_order_data(){
        var count: Int = 0

        for(i in 0 until OrderList!!.size){
            if(OrderList!![i].selled != 0) {
                count++
                total += OrderList!![i].price * OrderList!![i].selled
            }
        }

        var newOrderList = Array<StoreMenu>(count, {StoreMenu("", 0, 0)})
        count = 0
        for(i in 0 until OrderList!!.size){
            if(OrderList!![i].selled != 0)
                newOrderList[count++] = OrderList!![i]
        }

        mOrderListAdapter!!.setOrderListData(newOrderList)
        total_textview!!.text = total.toString()
    }

    fun cash_button_click(v: View){
        PushOrderDataTask().execute()
    }

    fun naming_button_click(v: View){
        var intent = Intent(this, NameActivity::class.java)
        intent.putExtra("OrderList", mOrderListAdapter!!.getOrderListData())
        startActivity(intent)
    }
}
