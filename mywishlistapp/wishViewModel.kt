import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mywishlistapp.Graph
import com.example.mywishlistapp.data.wish
import com.example.mywishlistapp.data.wishRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class wishViewModel(
    private val wishRepository: wishRepository = Graph.wishRepository
) : ViewModel() {

    var wishItemState by mutableStateOf("")
    var wishDescriptionState by mutableStateOf("")

    fun onWishTitleChanged(newString: String) {
        wishItemState = newString
    }

    fun onwishDescriptionChanged(newString: String) {
        wishDescriptionState = newString
    }

    val getAllWishes: Flow<List<wish>> = wishRepository.getWishes()

    fun addWish(wish: wish) {
        viewModelScope.launch(Dispatchers.IO) {
            wishRepository.addAWish(wish)
        }
    }

    fun getAWishByID(id: Long): Flow<wish?> {
        return wishRepository.getAWishByID(id)
    }

    fun updateWish(wish: wish) {
        viewModelScope.launch(Dispatchers.IO) {
            wishRepository.updateAWish(wish)
        }
    }

    fun deleteWish(wish: wish) {
        viewModelScope.launch(Dispatchers.IO) {
            wishRepository.deleteAWish(wish)
        }
    }
}
