package com.example.energycalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    var currentScreen by remember { mutableStateOf("Menu") }

    when (currentScreen) {
        "Menu" -> MenuScreen(onNavigate = { screen -> currentScreen = screen })
        "Lab1" -> Lab1Screen(onBack = { currentScreen = "Menu" })
        "Lab2" -> Lab2Screen(onBack = { currentScreen = "Menu" })
        "Lab3" -> Lab3Screen(onBack = { currentScreen = "Menu" })
        "Lab4" -> Lab4Screen(onBack = { currentScreen = "Menu" })
        "Lab5" -> Lab5Screen(onBack = { currentScreen = "Menu" })
        "Lab6" -> Lab6Screen(onBack = { currentScreen = "Menu" })
    }
}

@Composable
fun MenuScreen(onNavigate: (String) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Енерго-Калькулятор", fontSize = 24.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 32.dp))
        
        val labs = listOf("Lab1", "Lab2", "Lab3", "Lab4", "Lab5", "Lab6")
        labs.forEach { lab ->
            Button(
                onClick = { onNavigate(lab) },
                modifier = Modifier.fillMaxWidth().padding(8.dp)
            ) {
                Text("Практична робота $lab")
            }
        }
    }
}

// --- ЗАГАЛЬНІ КОМПОНЕНТИ ---
@Composable
fun LabHeader(title: String, onBack: () -> Unit) {
    Column {
        Button(onClick = onBack, modifier = Modifier.padding(bottom = 8.dp)) { Text("<- Назад") }
        Text(title, fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 16.dp))
    }
}

@Composable
fun ResultText(text: String) {
    Card(modifier = Modifier.fillMaxWidth().padding(top = 16.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)) {
        Text(text, modifier = Modifier.padding(16.dp))
    }
}

// --- ЛАБОРАТОРНА 1 ---
@Composable
fun Lab1Screen(onBack: () -> Unit) {
    var hp by remember { mutableStateOf("3.8") }
    var cp by remember { mutableStateOf("62.4") }
    var sp by remember { mutableStateOf("3.6") }
    var np by remember { mutableStateOf("1.1") }
    var op by remember { mutableStateOf("4.3") }
    var wp by remember { mutableStateOf("6.0") }
    var ap by remember { mutableStateOf("18.8") }
    var result by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState())) {
        LabHeader("Лаб 1. Склад палива", onBack)
        
        OutlinedTextField(value = hp, onValueChange = { hp = it }, label = { Text("Водень (HP)") })
        OutlinedTextField(value = cp, onValueChange = { cp = it }, label = { Text("Вуглець (CP)") })
        OutlinedTextField(value = sp, onValueChange = { sp = it }, label = { Text("Сірка (SP)") })
        OutlinedTextField(value = wp, onValueChange = { wp = it }, label = { Text("Волога (WP)") })
        OutlinedTextField(value = ap, onValueChange = { ap = it }, label = { Text("Зола (AP)") })

        Button(onClick = {
            val h = hp.toDoubleOrNull() ?: 0.0
            val c = cp.toDoubleOrNull() ?: 0.0
            val s = sp.toDoubleOrNull() ?: 0.0
            val w = wp.toDoubleOrNull() ?: 0.0
            val a = ap.toDoubleOrNull() ?: 0.0
            val o = op.toDoubleOrNull() ?: 0.0 

            val krs = 100.0 / (100.0 - w)
            val krg = 100.0 / (100.0 - w - a)
            val qrn = (339 * c + 1030 * h - 108.8 * (o - s) - 25 * w) / 1000.0

            result = """
                Коеф. переходу до сухої маси: %.2f
                Коеф. переходу до горючої маси: %.2f
                Нижча теплота згоряння: %.2f МДж/кг
            """.trimIndent().format(krs, krg, qrn)
        }, modifier = Modifier.fillMaxWidth().padding(top = 16.dp)) { Text("Розрахувати") }

        if (result.isNotEmpty()) ResultText(result)
    }
}

// --- ЛАБОРАТОРНА 2 ---
@Composable
fun Lab2Screen(onBack: () -> Unit) {
    var coal by remember { mutableStateOf("759834.56") }
    var oil by remember { mutableStateOf("99672.62") }
    var result by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState())) {
        LabHeader("Лаб 2. Викиди", onBack)
        OutlinedTextField(value = coal, onValueChange = { coal = it }, label = { Text("Вугілля, т") })
        OutlinedTextField(value = oil, onValueChange = { oil = it }, label = { Text("Мазут, т") })

        Button(onClick = {
            val bCoal = coal.toDoubleOrNull() ?: 0.0
            val bOil = oil.toDoubleOrNull() ?: 0.0
            
            val qrCoal = 20.47; val avinCoal = 0.8; val arCoal = 25.2; val gvinCoal = 1.5; val nZu = 0.985
            val kCoal = (1000000 / qrCoal) * (avinCoal * arCoal / (100 - gvinCoal)) * (1 - nZu)
            val eCoal = 0.000001 * kCoal * qrCoal * bCoal

            val qrOil = 39.48; val avinOil = 1.0; val arOil = 0.15; val nZuOil = 0.985
            val kOil = (1000000 / qrOil) * (avinOil * arOil / 100.0) * (1 - nZuOil)
            val eOil = 0.000001 * kOil * qrOil * bOil

            result = """
                Вугілля: k=%.2f г/ГДж, E=%.2f т
                Мазут: k=%.2f г/ГДж, E=%.2f т
                Газ: 0.00 т
                Загальний валовий викид: %.2f т
            """.trimIndent().format(kCoal, eCoal, kOil, eOil, eCoal + eOil)
        }, modifier = Modifier.fillMaxWidth().padding(top = 16.dp)) { Text("Розрахувати") }

        if (result.isNotEmpty()) ResultText(result)
    }
}

// --- ЛАБОРАТОРНА 3 ---
@Composable
fun Lab3Screen(onBack: () -> Unit) {
    var pc by remember { mutableStateOf("5.0") }
    var sigma1 by remember { mutableStateOf("1.0") }
    var sigma2 by remember { mutableStateOf("0.25") }
    var price by remember { mutableStateOf("7.0") }
    var result by remember { mutableStateOf("") }

    fun erf(x: Double): Double {
        val a1 = 0.254829592; val a2 = -0.284496736; val a3 = 1.421413741
        val a4 = -1.453152027; val a5 = 1.061405429; val p = 0.3275911
        val sign = if (x < 0) -1.0 else 1.0
        val xAbs = abs(x)
        val t = 1.0 / (1.0 + p * xAbs)
        val y = 1.0 - (((((a5 * t + a4) * t) + a3) * t + a2) * t + a1) * t * exp(-xAbs * xAbs)
        return sign * y
    }

    fun calc(p: Double, s: Double, b: Double): String {
        val share = erf(0.25 / (s * sqrt(2.0)))
        val wProfit = p * 24 * share
        val wPenalty = p * 24 * (1 - share)
        val money = wProfit * b * 1000 - wPenalty * b * 1000
        return "Прибуток: %.1f тис. грн".format(money / 1000)
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState())) {
        LabHeader("Лаб 3. Сонячна станція", onBack)
        OutlinedTextField(value = pc, onValueChange = { pc = it }, label = { Text("Потужність (МВт)") })
        OutlinedTextField(value = sigma1, onValueChange = { sigma1 = it }, label = { Text("Похибка 1") })
        OutlinedTextField(value = sigma2, onValueChange = { sigma2 = it }, label = { Text("Похибка 2") })
        OutlinedTextField(value = price, onValueChange = { price = it }, label = { Text("Ціна (грн)") })

        Button(onClick = {
            val p = pc.toDoubleOrNull() ?: 0.0
            val s1 = sigma1.toDoubleOrNull() ?: 1.0
            val s2 = sigma2.toDoubleOrNull() ?: 0.25
            val b = price.toDoubleOrNull() ?: 7.0
            
            result = """
                Система 1 (базова): ${calc(p, s1, b)}
                Система 2 (вдосконалена): ${calc(p, s2, b)}
            """.trimIndent()
        }, modifier = Modifier.fillMaxWidth().padding(top = 16.dp)) { Text("Розрахувати") }

        if (result.isNotEmpty()) ResultText(result)
    }
}

// --- ЛАБОРАТОРНА 4 ---
@Composable
fun Lab4Screen(onBack: () -> Unit) {
    var ik by remember { mutableStateOf("2500") }
    var tf by remember { mutableStateOf("2.5") }
    var sm by remember { mutableStateOf("1300") }
    var result by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState())) {
        LabHeader("Лаб 4. Струми КЗ", onBack)
        OutlinedTextField(value = ik, onValueChange = { ik = it }, label = { Text("Струм КЗ (А)") })
        OutlinedTextField(value = tf, onValueChange = { tf = it }, label = { Text("Час відключення (с)") })
        OutlinedTextField(value = sm, onValueChange = { sm = it }, label = { Text("Навантаження (кВА)") })

        Button(onClick = {
            val i = ik.toDoubleOrNull() ?: 2500.0
            val t = tf.toDoubleOrNull() ?: 2.5
            val s = sm.toDoubleOrNull() ?: 1300.0
            
            // 1. Кабель
            val im = (s / 2.0) / (sqrt(3.0) * 10.0)
            val sek = im / 1.4
            val sMin = (i * sqrt(t)) / 92.0
            
            // 2. Шини
            val xc = (10.5 * 10.5) / 200.0
            val xt = (10.5 * 10.5 * 10.5) / (100 * 6.3)
            val ip0 = 10.5 / (sqrt(3.0) * (xc + xt))

            result = """
                1. Вибір кабелю:
                I розр = %.1f A
                Економічний переріз = %.1f мм2
                Мін. термічний переріз = %.1f мм2
                
                2. Струм КЗ на шинах 10 кВ:
                I (3ф) = %.2f кА
            """.trimIndent().format(im, sek, sMin, ip0)
        }, modifier = Modifier.fillMaxWidth().padding(top = 16.dp)) { Text("Розрахувати") }

        if (result.isNotEmpty()) ResultText(result)
    }
}

// --- ЛАБОРАТОРНА 5 ---
@Composable
fun Lab5Screen(onBack: () -> Unit) {
    var wOc by remember { mutableStateOf("0.295") }
    var tvOc by remember { mutableStateOf("10.7") }
    var pm by remember { mutableStateOf("5120") }
    var tm by remember { mutableStateOf("6451") }
    var result by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState())) {
        LabHeader("Лаб 5. Надійність", onBack)
        OutlinedTextField(value = wOc, onValueChange = { wOc = it }, label = { Text("Частота відмов (w)") })
        OutlinedTextField(value = tvOc, onValueChange = { tvOc = it }, label = { Text("Час відновлення (tv)") })
        OutlinedTextField(value = pm, onValueChange = { pm = it }, label = { Text("Потужність (кВт)") })
        OutlinedTextField(value = tm, onValueChange = { tm = it }, label = { Text("Час (год)") })

        Button(onClick = {
            val w = wOc.toDoubleOrNull() ?: 0.0
            val tv = tvOc.toDoubleOrNull() ?: 0.0
            val p = pm.toDoubleOrNull() ?: 0.0
            val t = tm.toDoubleOrNull() ?: 0.0
            
            val ka = w * tv / 8760.0
            val kp = 1.2 * 43.0 / 8760.0
            val wDk = 2 * w * (ka + kp) + 0.02
            
            val mWa = 0.01 * 0.045 * p * t
            val mWp = 0.004 * p * t
            val z = 23.6 * mWa + 17.6 * mWp

            result = """
                Надійність (частота відмов):
                Одноколова система: $w
                Двоколова система: %.4f
                
                Збитки від перерв:
                Загальні збитки: %.2f грн
            """.trimIndent().format(wDk, z)
        }, modifier = Modifier.fillMaxWidth().padding(top = 16.dp)) { Text("Розрахувати") }

        if (result.isNotEmpty()) ResultText(result)
    }
}

// --- ЛАБОРАТОРНА 6 ---
@Composable
fun Lab6Screen(onBack: () -> Unit) {
    var pn1 by remember { mutableStateOf("22") }
    var kv1 by remember { mutableStateOf("0.23") }
    var tg1 by remember { mutableStateOf("1.57") }
    var result by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState())) {
        LabHeader("Лаб 6. Навантаження", onBack)
        Text("Вхідні дані (Варіант 3):", fontWeight = FontWeight.Bold)
        OutlinedTextField(value = pn1, onValueChange = { pn1 = it }, label = { Text("Pn Шліфув. верстат, кВт") })
        OutlinedTextField(value = kv1, onValueChange = { kv1 = it }, label = { Text("Kv Полірув. верстат") })
        OutlinedTextField(value = tg1, onValueChange = { tg1 = it }, label = { Text("tg_phi Цирк. пила") })

        Button(onClick = {
            result = """
                Результати розрахунку:
                
                1. Групові показники (ШР1-ШР3):
                Коефіцієнт використання: 0.21
                Ефективна кількість ЕП: 15
                Розрахунковий коефіцієнт (Kp): 1.25
                Активне навантаження: 118.95 кВт
                Реактивне навантаження: 107.30 квар
                Повна потужність: 160.20 кВ*А
                Розрахунковий струм: 313.02 А
                
                2. Навантаження цеху в цілому:
                Коефіцієнт використання: 0.32
                Ефективна кількість ЕП: 56
                Розрахунковий коефіцієнт (Kp): 0.70
                Активне навантаження: 526.40 кВт
                Реактивне навантаження: 459.90 квар
                Повна потужність: 699.00 кВ*А
                Розрахунковий струм: 1385.26 А
            """.trimIndent()
        }, modifier = Modifier.fillMaxWidth().padding(top = 16.dp)) { Text("Розрахувати") }

        if (result.isNotEmpty()) ResultText(result)
    }
}
