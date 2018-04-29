package postech.cse.servingapp.com.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import postech.cse.servingapp.com.R
import postech.cse.servingapp.com.listdata.StoreMenu

class OrderListAdapter: RecyclerView.Adapter<OrderListAdapter.OrderListAdapterViewHolder>() {

    private var mOrderData : Array<StoreMenu>? = null

    override fun getItemCount(): Int {
        return if (null == mOrderData) 0 else mOrderData!!.size
    }

    override fun onBindViewHolder(holder: OrderListAdapterViewHolder, position: Int) {
        val thisMenuItem = mOrderData!![position]
        holder.mOrderName.text = thisMenuItem.name
        holder.mOrderPrice.text = thisMenuItem.price.toString()
        holder.mOrderNum.text = thisMenuItem.selled.toString()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderListAdapterViewHolder {
        val context = parent.context
        val layoutIdForListItem = R.layout.menu_receipt_item
        val inflater = LayoutInflater.from(context)
        val shouldAttachToParentImmediately = false

        val view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately)
        return OrderListAdapterViewHolder(view)
    }

    inner class OrderListAdapterViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        val mOrderName: TextView
        val mOrderPrice: TextView
        val mOrderNum: TextView

        init {
            mOrderName = v.findViewById(R.id.tv_menu_name_receipt) as TextView
            mOrderPrice = v.findViewById(R.id.tv_menu_price_receipt) as TextView
            mOrderNum = v.findViewById(R.id.tv_menu_num_receipt) as TextView
        }
    }

    fun getMenuListData(): Array<StoreMenu>? {
        return mOrderData
    }

    fun setOrderListData(inputOrderData: Array<StoreMenu>?){
        mOrderData = inputOrderData
        notifyDataSetChanged()
    }
}