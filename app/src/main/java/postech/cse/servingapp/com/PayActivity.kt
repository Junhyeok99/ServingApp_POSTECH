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
import postech.cse.servingapp.com.listdata.Order
import postech.cse.servingapp.com.listdata.StoreMenu
import postech.cse.servingapp.com.utilities.NetworkUtils
import postech.cse.servingapp.com.utilities.OrderJSONUtils
import java.net.HttpURLConnection

class PayActivity : AppCompatActivity() {
    companion object {
        var present_screen: PayActivity? = null
    }

    private var MenuList: Array<StoreMenu>? = null
    private var mRecyclerView: RecyclerView? = null
    private var mOrderListAdapter: OrderListAdapter? = null
    private var total: Int = 0
    private var total_textview: TextView? = null
    private var table_num: TextInputEditText? = null
    private var mCustomerName: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receipt)

        present_screen = this
        table_num = findViewById(R.id.et_table_num) as TextInputEditText
        total_textview = findViewById(R.id.tv_total) as TextView
        mCustomerName = findViewById(R.id.et_customername) as EditText

        MenuList = intent.getSerializableExtra("OrderList") as Array<StoreMenu>

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
            var OrderList = mOrderListAdapter!!.getOrderListData()
            for(i in 0 until OrderList!!.size){
                var newOrder = OrderList[i]
                var sendURL = NetworkUtils.buildUrl(newOrder, table_num!!.text.toString(), mCustomerName!!.text.toString())

                try{
                    var result = NetworkUtils.getResponseFromHttpUrl(sendURL)
                    if(OrderJSONUtils.getOrderDataFromJSON(applicationContext, result) != 1)
                        return -1
                }
                catch(e: Exception){
                    e.printStackTrace()
                    return -1
                }
            }

            return 1
        }

        override fun onPostExecute(result: Int?) {
            if(result == 1) {
                var intent = Intent(applicationContext, CompleteActivity::class.java)
                intent.putExtra("tablenum", Integer.parseInt(table_num!!.text.toString()))
                startActivity(intent)
                finish()
            }
            else
                Toast.makeText(applicationContext, "Error occured. Please try again", Toast.LENGTH_SHORT).show()
        }

    }

    fun update_order_data(){
        var count = 0

        for(i in 0 until MenuList!!.size){
            if(MenuList!![i].selled != 0) {
                count++
                total += MenuList!![i].price * MenuList!![i].selled
            }
        }

        var newOrderList = Array<Order>(count, {Order("", 0, 0, total, "현금")})
        count = 0
        for(i in 0 until MenuList!!.size){
            if(MenuList!![i].selled != 0) {
                newOrderList[count].menu = MenuList!![i].name
                newOrderList[count].count = MenuList!![i].selled
                newOrderList[count++].price = MenuList!![i].price
            }
        }

        mOrderListAdapter!!.setOrderListData(newOrderList)
        total_textview!!.text = total.toString()
    }

    fun cash_button_click(v: View){
        PushOrderDataTask().execute()
    }
}
