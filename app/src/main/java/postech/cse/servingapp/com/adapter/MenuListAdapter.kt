package postech.cse.servingapp.com.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import postech.cse.servingapp.com.listdata.Menu

class MenuListAdapter : RecyclerView.Adapter<MenuListAdapter.MenuListAdapterViewHolder>() {
    private var mMenuData : Array<Menu>? = null

    override fun getItemCount(): Int {

    }

    override fun onBindViewHolder(holder: MenuListAdapterViewHolder, position: Int) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuListAdapterViewHolder {

    }

    inner class MenuListAdapterViewHolder(v: View) : RecyclerView.ViewHolder(v){
        val mMenuName : TextView? = null
        val mMenuPrice : TextView? = null
        val mMenuButton : Button? = null

        init {

        }

    }
}