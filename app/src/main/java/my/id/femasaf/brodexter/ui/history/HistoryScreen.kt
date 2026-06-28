package my.id.femasaf.brodexter.ui.history

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import my.id.femasaf.brodexter.data.Transaction
import my.id.femasaf.brodexter.data.TransactionType
import my.id.femasaf.brodexter.ui.dashboard.DashboardViewModel
import my.id.femasaf.brodexter.ui.dashboard.TransactionItem
import my.id.femasaf.brodexter.ui.theme.*
import my.id.femasaf.brodexter.util.toRupiah
import androidx.lifecycle.viewmodel.compose.viewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    onBack: () -> Unit,
    viewModel: DashboardViewModel = viewModel()
) {
    val allTransactions by viewModel.allTransactions.collectAsState()
    
    var filterType by remember { mutableStateOf<TransactionType?>(null) }
    var pageSize by remember { mutableIntStateOf(10) }
    
    val filteredTransactions = remember(allTransactions, filterType) {
        if (filterType == null) allTransactions else allTransactions.filter { it.type == filterType }
    }
    
    val pagedTransactions = filteredTransactions.take(pageSize)
    var transactionToDelete by remember { mutableStateOf<Transaction?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Riwayat Transaksi", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali")
                    }
                },
                actions = {
                    var showFilterMenu by remember { mutableStateOf(false) }
                    IconButton(onClick = { showFilterMenu = true }) {
                        Icon(Icons.Default.FilterList, contentDescription = "Filter")
                    }
                    DropdownMenu(
                        expanded = showFilterMenu,
                        onDismissRequest = { showFilterMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Semua") },
                            onClick = { filterType = null; showFilterMenu = false }
                        )
                        DropdownMenuItem(
                            text = { Text("Pemasukan") },
                            onClick = { filterType = TransactionType.INCOME; showFilterMenu = false }
                        )
                        DropdownMenuItem(
                            text = { Text("Pengeluaran") },
                            onClick = { filterType = TransactionType.EXPENSE; showFilterMenu = false }
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Background,
                    titleContentColor = Ink
                )
            )
        },
        containerColor = Background
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            if (filteredTransactions.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Tidak ada transaksi", color = Muted)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(pagedTransactions) { transaction ->
                        TransactionItem(
                            description = transaction.description,
                            amount = transaction.amount.toRupiah(),
                            type = transaction.type,
                            onDelete = { transactionToDelete = transaction }
                        )
                    }
                    
                    if (pageSize < filteredTransactions.size) {
                        item {
                            Box(
                                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                TextButton(
                                    onClick = { pageSize += 10 },
                                    colors = ButtonDefaults.textButtonColors(contentColor = Primary)
                                ) {
                                    Text("Lihat lainnya", fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }
        }

        if (transactionToDelete != null) {
            AlertDialog(
                onDismissRequest = { transactionToDelete = null },
                title = { Text("Konfirmasi Hapus") },
                text = { Text("Apakah Anda yakin ingin menghapus transaksi '${transactionToDelete?.description}'?") },
                confirmButton = {
                    Button(
                        onClick = {
                            transactionToDelete?.let { viewModel.deleteTransaction(it) }
                            transactionToDelete = null
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Alert)
                    ) {
                        Text("Hapus", color = Color.White)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { transactionToDelete = null }) {
                        Text("Batal")
                    }
                }
            )
        }
    }
}
