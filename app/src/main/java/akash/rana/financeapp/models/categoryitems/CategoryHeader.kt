package akash.rana.financeapp.models.categoryitems

import akash.rana.financeapp.models.Category

data class CategoryHeader(
    val category: Category
) : CategoryItem()