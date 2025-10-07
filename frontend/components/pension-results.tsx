"use client"

import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card"
import { TrendingUp, Wallet, PiggyBank, Banknote } from "lucide-react"
import { Line, LineChart, XAxis, YAxis, CartesianGrid, Legend, ResponsiveContainer } from "recharts"
import { ChartContainer, ChartTooltip, ChartTooltipContent } from "@/components/ui/chart"

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

interface PensionResultsProps {
  results: SimulationResults
  formData: {
    currentAge: number
    retirementAge: number
    monthlyContribution: number
    expectedReturn: number
  }
}

export function PensionResults({ results, formData }: PensionResultsProps) {
  const formatCurrency = (value: number) => {
    return new Intl.NumberFormat("pt-BR", {
      style: "currency",
      currency: "BRL",
    }).format(value)
  }

  const chartData = results.yearlyData.map((data) => ({
    ano: `${data.year}`,
    idade: data.age,
    "Valor Contribuído": data.contributed,
    "Valor Acumulado": data.accumulated,
  }))

  return (
    <div className="space-y-4 sm:space-y-6 w-full">
      <Card className="shadow-lg border-primary/10">
        <CardHeader className="bg-gradient-to-r from-secondary/10 to-primary/10 p-4 sm:p-6">
          <CardTitle className="text-xl sm:text-2xl">Resultados da Simulação</CardTitle>
          <CardDescription className="text-xs sm:text-sm">
            Projeção para {formData.retirementAge - formData.currentAge} anos de contribuição
          </CardDescription>
        </CardHeader>
        <CardContent className="pt-4 sm:pt-6 p-4 sm:p-6">
          <div className="grid grid-cols-1 sm:grid-cols-2 gap-3 sm:gap-4">
            <div className="bg-primary/5 rounded-lg p-3 sm:p-4 border border-primary/20">
              <div className="flex items-center gap-2 mb-2">
                <Wallet className="h-4 w-4 sm:h-5 sm:w-5 text-primary" />
                <p className="text-xs sm:text-sm font-medium text-muted-foreground">Total Contribuído</p>
              </div>
              <p className="text-lg sm:text-2xl font-bold text-primary">{formatCurrency(results.totalContributed)}</p>
            </div>

            <div className="bg-secondary/5 rounded-lg p-3 sm:p-4 border border-secondary/20">
              <div className="flex items-center gap-2 mb-2">
                <PiggyBank className="h-4 w-4 sm:h-5 sm:w-5 text-secondary" />
                <p className="text-xs sm:text-sm font-medium text-muted-foreground">Total Acumulado</p>
              </div>
              <p className="text-lg sm:text-2xl font-bold text-secondary">{formatCurrency(results.totalAccumulated)}</p>
            </div>

            <div className="bg-primary/5 rounded-lg p-3 sm:p-4 border border-primary/20">
              <div className="flex items-center gap-2 mb-2">
                <TrendingUp className="h-4 w-4 sm:h-5 sm:w-5 text-primary" />
                <p className="text-xs sm:text-sm font-medium text-muted-foreground">Rendimento Total</p>
              </div>
              <p className="text-lg sm:text-2xl font-bold text-primary">{formatCurrency(results.totalInterest)}</p>
            </div>

            <div className="bg-secondary/5 rounded-lg p-3 sm:p-4 border border-secondary/20">
              <div className="flex items-center gap-2 mb-2">
                <Banknote className="h-4 w-4 sm:h-5 sm:w-5 text-secondary" />
                <p className="text-xs sm:text-sm font-medium text-muted-foreground">Renda Mensal Estimada</p>
              </div>
              <p className="text-lg sm:text-2xl font-bold text-secondary">{formatCurrency(results.monthlyIncome)}</p>
            </div>
          </div>
        </CardContent>
      </Card>

      <Card className="shadow-lg border-primary/10">
        <CardHeader className="p-4 sm:p-6">
          <CardTitle className="text-lg sm:text-xl">Evolução do Patrimônio</CardTitle>
          <CardDescription className="text-xs sm:text-sm">Crescimento ao longo dos anos</CardDescription>
        </CardHeader>
        <CardContent className="p-2 sm:p-6">
          <ChartContainer
            config={{
              "Valor Contribuído": {
                label: "Valor Contribuído",
                color: "hsl(var(--chart-1))",
              },
              "Valor Acumulado": {
                label: "Valor Acumulado",
                color: "hsl(var(--chart-2))",
              },
            }}
            className="h-[250px] sm:h-[350px] md:h-[400px]"
          >
            <ResponsiveContainer width="100%" height="100%">
              <LineChart data={chartData}>
                <CartesianGrid strokeDasharray="3 3" className="stroke-muted" />
                <XAxis
                  dataKey="ano"
                  label={{ value: "Anos", position: "insideBottom", offset: -5 }}
                  className="text-[10px] sm:text-xs"
                  tick={{ fontSize: 10 }}
                />
                <YAxis
                  tickFormatter={(value) => `R$ ${(value / 1000).toFixed(0)}k`}
                  label={{ value: "Valor (R$)", angle: -90, position: "insideLeft" }}
                  className="text-[10px] sm:text-xs"
                  tick={{ fontSize: 10 }}
                />
                <ChartTooltip content={<ChartTooltipContent />} />
                <Legend wrapperStyle={{ fontSize: "12px" }} />
                <Line
                  type="monotone"
                  dataKey="Valor Contribuído"
                  stroke="hsl(var(--chart-1))"
                  strokeWidth={2}
                  dot={false}
                />
                <Line
                  type="monotone"
                  dataKey="Valor Acumulado"
                  stroke="hsl(var(--chart-2))"
                  strokeWidth={3}
                  dot={false}
                />
              </LineChart>
            </ResponsiveContainer>
          </ChartContainer>
        </CardContent>
      </Card>
    </div>
  )
}
