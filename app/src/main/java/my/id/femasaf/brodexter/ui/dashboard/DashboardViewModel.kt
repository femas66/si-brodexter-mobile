package my.id.femasaf.brodexter.ui.dashboard

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import my.id.femasaf.brodexter.data.AppDatabase
import my.id.femasaf.brodexter.data.Transaction
import my.id.femasaf.brodexter.data.TransactionRepository
import my.id.femasaf.brodexter.data.TransactionType

class DashboardViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: TransactionRepository
    val allTransactions: StateFlow<List<Transaction>>
    val totalIncome: StateFlow<Long>
    val totalExpense: StateFlow<Long>
    val balance: StateFlow<Long>

    init {
        val dao = AppDatabase.getDatabase(application).transactionDao()
        repository = TransactionRepository(dao)
        
        allTransactions = repository.allTransactions.stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
        )
        
        totalIncome = repository.totalIncome.map { it ?: 0L }.stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(5000), 0L
        )
        
        totalExpense = repository.totalExpense.map { it ?: 0L }.stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(5000), 0L
        )
        
        balance = combine(repository.totalIncome, repository.totalExpense) { income, expense ->
            (income ?: 0L) - (expense ?: 0L)
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0L)
    }

    fun addTransaction(amount: Long, description: String, type: TransactionType) {
        viewModelScope.launch {
            repository.insert(Transaction(type = type, amount = amount, description = description))
        }
    }

    fun deleteTransaction(transaction: Transaction) {
        viewModelScope.launch {
            repository.delete(transaction)
        }
    }
}
