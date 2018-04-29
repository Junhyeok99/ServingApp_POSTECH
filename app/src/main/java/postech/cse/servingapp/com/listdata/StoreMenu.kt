package postech.cse.servingapp.com.listdata

import java.io.Serializable

data class StoreMenu (
        var name: String,
        var price: Int,
        var selled: Int
): Serializable