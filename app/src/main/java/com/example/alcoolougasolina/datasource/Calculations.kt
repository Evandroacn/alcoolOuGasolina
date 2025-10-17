package com.example.alcoolougasolina.datasource

object Calculations {
    fun calculate(alcool: String, gasolina: String, posto: String, porcentagem: Boolean, response: (String, Boolean) -> Unit) {
        if (alcool.isEmpty() || gasolina.isEmpty()) {
            response("Preencha todos os campos obrigatórios", true)
            return
        }

        val alcoolFormatado = alcool.replace(",", ".").toDoubleOrNull()
        val gasolinaFormatado = gasolina.replace(",", ".").toDoubleOrNull()

        if (alcoolFormatado == null || gasolinaFormatado == null) {
            response("Valores inválidos. Use apenas números.", true)
            return
        }

        val porct: Double = if (porcentagem) 0.75 else 0.70
        val limite = gasolinaFormatado * porct
        val resultMessage: String = if (posto.isNotEmpty()) {
            if (alcoolFormatado > limite) "A Gasolina é mais rentável no posto $posto" else "O Álcool é mais rentável no posto $posto"
        } else {
            if (alcoolFormatado > limite) "A Gasolina é mais rentável" else "O Álcool é mais rentável"
        }

        response(resultMessage, false)
    }
}