package postech.cse.servingapp.com

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.View

class CompleteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complete)
    }

    fun complete_button_click(v: View){
        PayActivity.present_screen!!.finish()
        OrderActivity.present_screen!!.finish()
        finish()
    }
}
