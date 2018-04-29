package postech.cse.servingapp.com.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import postech.cse.servingapp.com.R
import postech.cse.servingapp.com.listdata.StoreMenu
import java.lang.ref.WeakReference

class MenuListAdapter(private val mClickHandler: MenuListAdapter.MenuOnClickListener)
    : RecyclerView.Adapter<MenuListAdapter.MenuListAdapterViewHolder>() {

    private var mMenuData : Array<StoreMenu>? = null

    interface MenuOnClickListener {
        fun onUpPositionClicked(position: Int)
        fun onDownPositionClicked(position: Int)
    }

    override fun getItemCount(): Int {
        return if (null == mMenuData) 0 else mMenuData!!.size
    }

    override fun onBindViewHolder(holder: MenuListAdapterViewHolder, position: Int) {
        val thisMenuItem = mMenuData!![position]
        holder.mMenuName.text = thisMenuItem.name
        holder.mMenuPrice.text = thisMenuItem.price.toString()
        holder.mMenuNum.text = thisMenuItem.selled.toString()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuListAdapterViewHolder {
        val context = parent.context
        val layoutIdForListItem = R.layout.menu_list_item
        val inflater = LayoutInflater.from(context)
        val shouldAttachToParentImmediately = false

        val view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately)
        return MenuListAdapterViewHolder(view, mClickHandler)
    }

    inner class MenuListAdapterViewHolder(v: View, listener: MenuOnClickListener) : RecyclerView.ViewHolder(v), View.OnClickListener {

        val listenerRef: WeakReference<MenuOnClickListener>
        val mMenuName : TextView
        val mMenuPrice : TextView
        val mMenuUpButton : ImageButton
        val mMenuDownButton : ImageButton
        val mMenuNum : TextView

        override fun onClick(v: View) {
            if(v.id == mMenuUpButton.id){
                Toast.makeText(v.context, "up button", Toast.LENGTH_LONG)
                listenerRef.get()!!.onUpPositionClicked(adapterPosition)
            }
            else if(v.id == mMenuDownButton.id){
                Toast.makeText(v.context, "down button", Toast.LENGTH_LONG)
                listenerRef.get()!!.onDownPositionClicked(adapterPosition)
            }
        }

        init {
            mMenuName = v.findViewById(R.id.tv_menu_name) as TextView
            mMenuPrice = v.findViewById(R.id.tv_menu_price) as TextView
            mMenuUpButton = v.findViewById(R.id.bt_up) as ImageButton
            mMenuDownButton = v.findViewById(R.id.bt_down) as ImageButton
            mMenuNum = v.findViewById(R.id.tv_menu_num) as TextView

            listenerRef = WeakReference<MenuOnClickListener>(listener)
            mMenuUpButton.setOnClickListener(this)
            mMenuDownButton.setOnClickListener(this)
        }
    }

    fun getMenuListData(): Array<StoreMenu>? {
        return mMenuData
    }

    fun setMenuListData(menuData: Array<StoreMenu>?){
        mMenuData = menuData
        notifyDataSetChanged()
    }
}