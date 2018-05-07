package postech.cse.servingapp.com.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import postech.cse.servingapp.com.R
import postech.cse.servingapp.com.listdata.StoreMenu

class StatusMenuListAdapter : RecyclerView.Adapter<StatusMenuListAdapter.StatusMenuListAdapterViewHolder>(){
    private var mStatusMenuData : Array<StoreMenu>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatusMenuListAdapterViewHolder {
        val context = parent.context
        val layoutIdForListItem = R.layout.status_menu_list_item
        val inflater = LayoutInflater.from(context)
        val shouldAttachToParentImmediately = false

        val view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately)
        return StatusMenuListAdapterViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if (null == mStatusMenuData) 0 else mStatusMenuData!!.size
    }

    override fun onBindViewHolder(holder: StatusMenuListAdapterViewHolder, position: Int) {
        val thisMenuItem = mStatusMenuData!![position]
        holder.mMenuOrder.text = (position + 1).toString()
        holder.mMenuName.text = thisMenuItem.name
        holder.mMenuSelled.text = thisMenuItem.selled.toString()
    }

    inner class StatusMenuListAdapterViewHolder(v: View) : RecyclerView.ViewHolder(v){
        val mMenuName : TextView
        val mMenuSelled : TextView
        val mMenuOrder : TextView

        init {
            mMenuName = v.findViewById(R.id.tv_menu_status_name) as TextView
            mMenuSelled = v.findViewById(R.id.tv_menu_status_selled) as TextView
            mMenuOrder = v.findViewById(R.id.tv_menu_status_order) as TextView
        }
    }

    fun setMenuListData(menuData: Array<StoreMenu>?){
        mStatusMenuData = menuData
        notifyDataSetChanged()
    }
}