package akash.rana.financeapp.models

enum class Category(val id: Int, val description: String) {
    GROCERIES(1, "Groceries"),
    UTILITIES(2, "Utilities"),
    WORKS(3, "Works"),
    TRANSPORTATION(4, "Transportation"),
    ENTERTAINMENT(5, "Entertainment"),
    HEALTH(6, "Health"),
    CLOTHING(7, "Clothing"),
    HOUSING(8, "Housing"),
    SAVINGS(9, "Savings and Investments"),
    EDUCATION(10, "Education"),
    CAR(11, "Car"),
    HOLIDAY(12, "Holiday"),
    OTHER(13, "Other");

}