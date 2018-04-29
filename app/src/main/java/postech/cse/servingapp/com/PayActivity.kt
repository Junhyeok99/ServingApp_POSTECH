package postech.cse.servingapp.com

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import android.widget.Toast
import postech.cse.servingapp.com.adapter.OrderListAdapter
import postech.cse.servingapp.com.listdata.StoreMenu
import postech.cse.servingapp.com.utilities.NetworkUtils

class PayActivity : AppCompatActivity() {
    private var OrderList: Array<StoreMenu>? = null
    private var mRecyclerView: RecyclerView? = null
    private var mOrderListAdapter: OrderListAdapter? = null
    private var total: Int = 0
    private var total_textview: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receipt)
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

        NetworkUtils.buildUrl(newOrderList)
    }
}
