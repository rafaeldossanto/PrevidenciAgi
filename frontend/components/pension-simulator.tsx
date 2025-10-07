"use client"

import { useState } from "react"
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card"
import { Label } from "@/components/ui/label"
import { Input } from "@/components/ui/input"
import { Button } from "@/components/ui/button"
import { PensionResults } from "@/components/pension-results"
import { Calculator, TrendingUp, Wallet, Calendar } from "lucide-react"

interface SimulationData {
  currentAge: number
  retirementAge: number
  monthlyContribution: number
  expectedReturn: number
}

interface SimulationResults {
  totalContributed: number
  totalAccumulated: number
  totalInterest: number
  monthlyIncome: number
  yearlyData: Array<{
    year: number
    age: number
    contributed: number
    accumulated: number
  }>
}

export function PensionSimulator() {
  const [formData, setFormData] = useState<SimulationData>({
    currentAge: 30,
    retirementAge: 65,
    monthlyContribution: 500,
    expectedReturn: 8,
  })

  const [results, setResults] = useState<SimulationResults | null>(null)

  const calculatePension = () => {
    const { currentAge, retirementAge, monthlyContribution, expectedReturn } = formData
    const years = retirementAge - currentAge
    const months = years * 12
    const monthlyRate = expectedReturn / 100 / 12

    let accumulated = 0
    const yearlyData = []

    for (let month = 1; month <= months; month++) {
      accumulated = (accumulated + monthlyContribution) * (1 + monthlyRate)

      if (month % 12 === 0) {
        yearlyData.push({
          year: month / 12,
          age: currentAge + month / 12,
          contributed: monthlyContribution * month,
          accumulated: Math.round(accumulated),
        })
      }
    }

    const totalContributed = monthlyContribution * months
    const totalInterest = accumulated - totalContributed
    const monthlyIncome = (accumulated * monthlyRate) / (1 - Math.pow(1 + monthlyRate, -300))

    setResults({
      totalContributed: Math.round(totalContributed),
      totalAccumulated: Math.round(accumulated),
      totalInterest: Math.round(totalInterest),
      monthlyIncome: Math.round(monthlyIncome),
      yearlyData,
    })
  }

  const handleInputChange = (field: keyof SimulationData, value: string) => {
    const numValue = Number.parseFloat(value) || 0
    setFormData((prev) => ({ ...prev, [field]: numValue }))
  }

  return (
    <div className="w-full max-w-7xl grid grid-cols-1 lg:grid-cols-2 gap-4 sm:gap-6 px-2 sm:px-0">
      <Card className="shadow-lg border-primary/10">
        <CardHeader className="bg-gradient-to-r from-primary/10 to-secondary/10 p-4 sm:p-6">
          <CardTitle className="flex items-center gap-2 text-xl sm:text-2xl">
            <Calculator className="h-5 w-5 sm:h-6 sm:w-6 text-primary" />
            Dados da Simulação
          </CardTitle>
          <CardDescription className="text-xs sm:text-sm">
            Preencha os campos abaixo para simular sua previdência
          </CardDescription>
        </CardHeader>
        <CardContent className="pt-4 sm:pt-6 space-y-4 sm:space-y-6 p-4 sm:p-6">
          <div className="space-y-2">
            <Label htmlFor="currentAge" className="flex items-center gap-2 text-sm sm:text-base">
              <Calendar className="h-4 w-4 text-primary" />
              Idade Atual
            </Label>
            <Input
              id="currentAge"
              type="number"
              min="18"
              max="100"
              value={formData.currentAge}
              onChange={(e) => handleInputChange("currentAge", e.target.value)}
              className="text-base sm:text-lg h-10 sm:h-12"
            />
          </div>

          <div className="space-y-2">
            <Label htmlFor="retirementAge" className="flex items-center gap-2 text-sm sm:text-base">
              <Calendar className="h-4 w-4 text-secondary" />
              Idade de Aposentadoria
            </Label>
            <Input
              id="retirementAge"
              type="number"
              min="18"
              max="100"
              value={formData.retirementAge}
              onChange={(e) => handleInputChange("retirementAge", e.target.value)}
              className="text-base sm:text-lg h-10 sm:h-12"
            />
          </div>

          <div className="space-y-2">
            <Label htmlFor="monthlyContribution" className="flex items-center gap-2 text-sm sm:text-base">
              <Wallet className="h-4 w-4 text-primary" />
              Contribuição Mensal (R$)
            </Label>
            <Input
              id="monthlyContribution"
              type="number"
              min="0"
              step="50"
              value={formData.monthlyContribution}
              onChange={(e) => handleInputChange("monthlyContribution", e.target.value)}
              className="text-base sm:text-lg h-10 sm:h-12"
            />
          </div>

          <div className="space-y-2">
            <Label htmlFor="expectedReturn" className="flex items-center gap-2 text-sm sm:text-base">
              <TrendingUp className="h-4 w-4 text-secondary" />
              Rentabilidade Anual Esperada (%)
            </Label>
            <Input
              id="expectedReturn"
              type="number"
              min="0"
              max="30"
              step="0.5"
              value={formData.expectedReturn}
              onChange={(e) => handleInputChange("expectedReturn", e.target.value)}
              className="text-base sm:text-lg h-10 sm:h-12"
            />
          </div>

          <Button
            onClick={calculatePension}
            className="w-full h-10 sm:h-12 text-base sm:text-lg font-semibold"
            size="lg"
          >
            <Calculator className="mr-2 h-4 w-4 sm:h-5 sm:w-5" />
            Calcular Previdência
          </Button>

          <p className="text-xs text-muted-foreground text-center">
            * Esta é uma simulação e não representa uma garantia de rentabilidade
          </p>
        </CardContent>
      </Card>

      {results && <PensionResults results={results} formData={formData} />}
    </div>
  )
}
