package postech.cse.servingapp.com

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.view.View
import android.widget.EditText
import postech.cse.servingapp.com.listdata.StoreMenu
import postech.cse.servingapp.com.utilities.NetworkUtils

class NameActivity : AppCompatActivity() {
    var customerEditText: TextInputEditText? = null
    var buyerEditText: TextInputEditText? = null
    var OrderList: Array<StoreMenu>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_name)

        OrderList = intent.getSerializableExtra("OrderList") as Array<StoreMenu>

        customerEditText = findViewById(R.id.et_customer) as TextInputEditText
        buyerEditText = findViewById(R.id.et_buyer) as TextInputEditText
    }

    fun confirm_button_click(v: View){
        var customername = customerEditText!!.text.toString()
        var buyername = customerEditText!!.text.toString()

        var sendURL = NetworkUtils.buildUrl(OrderList, customername, buyername)
        //var result = NetworkUtils.getResponseFromHttpUrl(sendURL)

        var intent = Intent(this, CompleteActivity::class.java)
        startActivity(intent)
        finish()
    }
}
