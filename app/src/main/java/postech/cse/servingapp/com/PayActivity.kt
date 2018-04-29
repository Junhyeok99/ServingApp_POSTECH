package postech.cse.servingapp.com

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import postech.cse.servingapp.com.adapter.OrderListAdapter
import postech.cse.servingapp.com.listdata.StoreMenu

class PayActivity : AppCompatActivity() {
    var OrderList: Array<StoreMenu>? = null
    private var mRecyclerView: RecyclerView? = null
    private var mOrderListAdapter: OrderListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receipt)
        OrderList = intent.getSerializableExtra("OrderList") as Array<StoreMenu>

        Toast.makeText(applicationContext, OrderList!![0].name, Toast.LENGTH_SHORT).show()
    }

    fun update_order_data(){
        var count: Int = 0

        for(i in 0 until OrderList!!.size){
            if(OrderList!![i].selled != 0)
                count++
        }

        var newOrderList = Array<StoreMenu>(count, {StoreMenu("", 0, 0)})
        for(i in 0 until OrderList!!.size){
            if(OrderList!![i].selled != 0)
                newOrderList[i] = OrderList!![i]
        }

        mOrderListAdapter!!.setOrderListData(newOrderList)
    }
}
