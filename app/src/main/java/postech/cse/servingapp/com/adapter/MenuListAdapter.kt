package postech.cse.servingapp.com.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import postech.cse.servingapp.com.R
import postech.cse.servingapp.com.listdata.Menu

class MenuListAdapter : RecyclerView.Adapter<MenuListAdapter.MenuListAdapterViewHolder>() {
    private var mMenuData : Array<Menu>? = null

    override fun getItemCount(): Int {
        return if (null == mMenuData) 0 else mMenuData!!.size
    }

    override fun onBindViewHolder(holder: MenuListAdapterViewHolder, position: Int) {
        val thisMenuItem = mMenuData!![position]
        holder.mMenuName.text = thisMenuItem.name
        holder.mMenuPrice.text = thisMenuItem.price.toString()
        holder.mMenuNum.text = "0"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuListAdapterViewHolder {
        val context = parent.context
        val layoutIdForListItem = R.layout.menu_list_item
        val inflater = LayoutInflater.from(context)
        val shouldAttachToParentImmediately = false

        val view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately)
        return MenuListAdapterViewHolder(view)
    }

    inner class MenuListAdapterViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val mMenuName : TextView
        val mMenuPrice : TextView
        val mMenuUpButton : Button
        val mMenuDownButton : Button
        val mMenuNum : TextView

        init {
            mMenuName = v.findViewById(R.id.tv_menu_name) as TextView
            mMenuPrice = v.findViewById(R.id.tv_menu_price) as TextView
            mMenuUpButton = v.findViewById(R.id.bt_up) as Button
            mMenuDownButton = v.findViewById(R.id.bt_down) as Button
            mMenuNum = v.findViewById(R.id.tv_menu_num) as TextView
        }
    }

    fun setMenuListData(menuData: Array<Menu>?){
        mMenuData = menuData
        notifyDataSetChanged()
    }
}