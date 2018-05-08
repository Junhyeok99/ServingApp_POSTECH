package postech.cse.servingapp.com.listdata

data class Order (
        var menu: String,
        var price: Int,
        var count: Int,
        var total: Int,
        var buyername: String
)