package postech.cse.servingapp.com.adapter

import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import postech.cse.servingapp.com.R
import postech.cse.servingapp.com.listdata.Order
import postech.cse.servingapp.com.listdata.StoreMenu

class OrderListAdapter: RecyclerView.Adapter<OrderListAdapter.OrderListAdapterViewHolder>() {

    private var mOrderData : Array<Order>? = null

    override fun getItemCount(): Int {
        return if (null == mOrderData) 0 else mOrderData!!.size
    }

    override fun onBindViewHolder(holder: OrderListAdapterViewHolder, position: Int) {
        val thisMenuItem = mOrderData!![position]
        holder.mOrderName.text = thisMenuItem.menu
        holder.mOrderPrice.text = thisMenuItem.price.toString()
        holder.mOrderNum.text = thisMenuItem.count.toString()

        holder.OrderListAdapterEditTextListener.updatePosition(position)
        holder.mOrderPay.setText(thisMenuItem.buyername)
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
        var mOrderPay: EditText
        var OrderListAdapterEditTextListener: OrderListEditTextListener

        init {
            mOrderName = v.findViewById(R.id.tv_menu_name_receipt) as TextView
            mOrderPrice = v.findViewById(R.id.tv_menu_price_receipt) as TextView
            mOrderNum = v.findViewById(R.id.tv_menu_num_receipt) as TextView
            mOrderPay = v.findViewById(R.id.et_pay) as EditText

            OrderListAdapterEditTextListener = OrderListEditTextListener()
            mOrderPay.addTextChangedListener(OrderListAdapterEditTextListener)
        }
    }

    fun getOrderListData(): Array<Order>? {
        return mOrderData
    }

    fun setOrderListData(inputOrderData: Array<Order>?){
        mOrderData = inputOrderData
        notifyDataSetChanged()
    }

    inner class OrderListEditTextListener : TextWatcher {
        var position: Int = 0
        fun updatePosition(position: Int){
            this.position = position
        }
        override fun afterTextChanged(p0: Editable?) { }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            mOrderData!![position].buyername = p0.toString()
        }

    }
}