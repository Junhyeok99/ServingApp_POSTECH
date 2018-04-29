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
        return if (null == mMenuData) 0 else mMenuData!!.size
    }

    override fun onBindViewHolder(holder: OrderListAdapterViewHolder, position: Int) {
        val thisMenuItem = mOrderData!![position]
        holder.mMenuName.text = thisMenuItem.name
        holder.mMenuPrice.text = thisMenuItem.price.toString()
        holder.mMenuNum.text = thisMenuItem.selled.toString()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderListAdapterViewHolder {
        val context = parent.context
        val layoutIdForListItem = R.layout.menu_list_item
        val inflater = LayoutInflater.from(context)
        val shouldAttachToParentImmediately = false

        val view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately)
        return OrderListAdapterViewHolder(view)
    }

    inner class OrderListAdapterViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        val mMenuName: TextView
        val mMenuPrice: TextView
        val mMenuNum: TextView

        init {
            mMenuName = v.findViewById(R.id.tv_menu_name) as TextView
            mMenuPrice = v.findViewById(R.id.tv_menu_price) as TextView
            mMenuNum = v.findViewById(R.id.tv_menu_num) as TextView
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