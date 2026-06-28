package my.id.femasaf.brodexter.ui.dashboard

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import my.id.femasaf.brodexter.data.Transaction
import my.id.femasaf.brodexter.data.TransactionType
import my.id.femasaf.brodexter.ui.theme.*
import my.id.femasaf.brodexter.util.toRupiah

@Composable
fun DashboardScreen(viewModel: DashboardViewModel = viewModel()) {
    val balance by viewModel.balance.collectAsState()
    val income by viewModel.totalIncome.collectAsState()
    val expense by viewModel.totalExpense.collectAsState()
    val transactions by viewModel.allTransactions.collectAsState()
    
    DashboardContent(
        balance = balance,
        income = income,
        expense = expense,
        transactions = transactions,
        onAddTransaction = { amount, desc, type ->
            viewModel.addTransaction(amount, desc, type)
        },
        onDeleteTransaction = { transaction ->
            viewModel.deleteTransaction(transaction)
        }
    )
}

@Composable
fun DashboardContent(
    balance: Long,
    income: Long,
    expense: Long,
    transactions: List<Transaction>,
    onAddTransaction: (Long, String, TransactionType) -> Unit,
    onDeleteTransaction: (Transaction) -> Unit
) {
    var showAddDialog by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = Background,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = Primary,
                contentColor = Color.White,
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Tambah Transaksi")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                HeaderSection()
            }
            item {
                BalanceCard(balance = balance.toRupiah())
            }
            item {
                SummarySection(income = income.toRupiah(), expense = expense.toRupiah())
            }
            item {
                SectionTitle("Transaksi Terakhir")
            }
            if (transactions.isEmpty()) {
                item {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text("Belum ada transaksi", color = Muted)
                    }
                }
            } else {
                items(transactions.size) { index ->
                    val transaction = transactions[index]
                    TransactionItem(
                        description = transaction.description,
                        amount = transaction.amount.toRupiah(),
                        type = transaction.type,
                        onDelete = { onDeleteTransaction(transaction) }
                    )
                }
            }
            item {
                TrendsChart(transactions = transactions)
            }
        }
        
        if (showAddDialog) {
            AddTransactionDialog(
                onDismiss = { showAddDialog = false },
                onConfirm = { amountValue, desc, typeValue ->
                    onAddTransaction(amountValue, desc, typeValue)
                    showAddDialog = false
                }
            )
        }
    }
}

@Composable
fun TransactionItem(description: String, amount: String, type: TransactionType, onDelete: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Surface),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Hapus", tint = Muted.copy(alpha = 0.5f))
                }
                Column {
                    Text(text = description, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold, color = Ink)
                    Text(text = if (type == TransactionType.INCOME) "Pemasukan" else "Pengeluaran", style = MaterialTheme.typography.bodySmall, color = Muted)
                }
            }
            Text(
                text = (if (type == TransactionType.INCOME) "+" else "-") + amount,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = if (type == TransactionType.INCOME) Success else Alert
            )
        }
    }
}

@Composable
fun AddTransactionDialog(
    onDismiss: () -> Unit,
    onConfirm: (Long, String, TransactionType) -> Unit
) {
    var amount by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var type by remember { mutableStateOf(TransactionType.INCOME) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Tambah Transaksi") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = amount,
                    onValueChange = { if (it.all { char -> char.isDigit() }) amount = it },
                    label = { Text("Jumlah") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Keterangan") },
                    modifier = Modifier.fillMaxWidth()
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = type == TransactionType.INCOME,
                        onClick = { type = TransactionType.INCOME }
                    )
                    Text("Pemasukan")
                    Spacer(modifier = Modifier.width(16.dp))
                    RadioButton(
                        selected = type == TransactionType.EXPENSE,
                        onClick = { type = TransactionType.EXPENSE }
                    )
                    Text("Pengeluaran")
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val amountLong = amount.toLongOrNull() ?: 0L
                    if (amountLong > 0 && description.isNotEmpty()) {
                        onConfirm(amountLong, description, type)
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Primary)
            ) {
                Text("Simpan")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Batal")
            }
        }
    )
}

@Composable
fun HeaderSection() {
    Column {
        Text(
            text = "Halo, Juragan!",
            style = MaterialTheme.typography.titleMedium,
            color = Muted
        )
        Text(
            text = "BroDexter",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = Ink
        )
    }
}

@Composable
fun BalanceCard(balance: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Primary),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            Text(
                text = "Total Saldo Anda",
                color = Color.White.copy(alpha = 0.7f),
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = balance,
                color = Color.White,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                letterSpacing = (-0.02).sp
            )
        }
    }
}

@Composable
fun SummarySection(income: String, expense: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SummaryItem(
            modifier = Modifier.weight(1f),
            label = "Pemasukan",
            amount = income,
            icon = Icons.Default.ArrowUpward,
            color = Success
        )
        SummaryItem(
            modifier = Modifier.weight(1f),
            label = "Pengeluaran",
            amount = expense,
            icon = Icons.Default.ArrowDownward,
            color = Alert
        )
    }
}

@Composable
fun SummaryItem(
    modifier: Modifier = Modifier,
    label: String,
    amount: String,
    icon: ImageVector,
    color: Color
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Surface),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = label, style = MaterialTheme.typography.bodySmall, color = Muted)
            Text(
                text = amount,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = color
            )
        }
    }
}

@Composable
fun TrendsChart(transactions: List<Transaction>) {
    Column {
        SectionTitle("Tren Keuangan")
        Spacer(modifier = Modifier.height(16.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp),
            colors = CardDefaults.cardColors(containerColor = Surface),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            if (transactions.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Belum ada data untuk grafik", color = Muted, style = MaterialTheme.typography.bodyMedium)
                }
            } else {
                val sortedTransactions = transactions.sortedBy { it.date }.takeLast(10)
                val maxAmount = sortedTransactions.maxOfOrNull { it.amount } ?: 1L
                
                Canvas(modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp, vertical = 32.dp)) {
                    val width = size.width
                    val height = size.height
                    val spacing = width / (if (sortedTransactions.size > 1) sortedTransactions.size - 1 else 1)
                    
                    val incomePath = Path()
                    val expensePath = Path()
                    
                    var incomeStarted = false
                    var expenseStarted = false

                    sortedTransactions.forEachIndexed { index, transaction ->
                        val x = index * spacing
                        val y = height - (transaction.amount.toFloat() / maxAmount * height)
                        
                        if (transaction.type == TransactionType.INCOME) {
                            if (!incomeStarted) {
                                incomePath.moveTo(x, y)
                                incomeStarted = true
                            } else {
                                incomePath.lineTo(x, y)
                            }
                            drawCircle(color = Success, radius = 4.dp.toPx(), center = androidx.compose.ui.geometry.Offset(x, y))
                        } else {
                            if (!expenseStarted) {
                                expensePath.moveTo(x, y)
                                expenseStarted = true
                            } else {
                                expensePath.lineTo(x, y)
                            }
                            drawCircle(color = Alert, radius = 4.dp.toPx(), center = androidx.compose.ui.geometry.Offset(x, y))
                        }
                    }
                    
                    drawPath(
                        path = incomePath,
                        color = Success,
                        style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
                    )
                    drawPath(
                        path = expensePath,
                        color = Alert,
                        style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
                    )
                }
            }
        }
        
        // Legend
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.size(10.dp).background(Success, RoundedCornerShape(2.dp)))
            Spacer(modifier = Modifier.width(4.dp))
            Text("Pemasukan", style = MaterialTheme.typography.bodySmall, color = Muted)
            Spacer(modifier = Modifier.width(16.dp))
            Box(modifier = Modifier.size(10.dp).background(Alert, RoundedCornerShape(2.dp)))
            Spacer(modifier = Modifier.width(4.dp))
            Text("Pengeluaran", style = MaterialTheme.typography.bodySmall, color = Muted)
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        color = Ink
    )
}


@Preview(showBackground = true)
@Composable
fun DashboardPreview() {
    BroDexterTheme {
        DashboardContent(
            balance = 5000000L,
            income = 7000000L,
            expense = 2000000L,
            transactions = listOf(
                Transaction(1, TransactionType.INCOME, 1000000, "Gaji Bulanan"),
                Transaction(2, TransactionType.EXPENSE, 250000, "Belanja Mingguan"),
                Transaction(3, TransactionType.EXPENSE, 50000, "Makan Siang")
            ),
            onAddTransaction = { _, _, _ -> },
            onDeleteTransaction = {}
        )
    }
}
