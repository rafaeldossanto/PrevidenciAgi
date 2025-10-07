import { PensionSimulator } from "@/components/pension-simulator"

export default function SimulatorPage() {
  return (
    <main className="min-h-screen bg-gradient-to-br from-primary/5 via-background to-secondary/5">
      <div className="container mx-auto px-3 sm:px-4 py-6 sm:py-8 md:py-12 flex flex-col items-center">
        <div className="mb-6 sm:mb-8 text-center">
          <h1 className="text-2xl sm:text-3xl md:text-4xl lg:text-5xl font-bold text-primary mb-2 sm:mb-3 text-balance px-2">
            Simulador de Previdência Privada
          </h1>
          <p className="text-sm sm:text-base md:text-lg text-muted-foreground max-w-2xl mx-auto text-pretty px-4">
            Planeje seu futuro financeiro e descubra quanto você pode acumular para sua aposentadoria
          </p>
        </div>
        <PensionSimulator />
      </div>
    </main>
  )
}
