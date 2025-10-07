import { LoginForm } from "@/components/login-form"

export default function Home() {
  return (
    <main className="min-h-screen bg-gradient-to-br from-primary/5 via-background to-secondary/5 flex items-center justify-center">
      <div className="container mx-auto px-4 py-8">
        <div className="mb-8 text-center">
          <h1 className="text-4xl md:text-5xl font-bold text-primary mb-3 text-balance">
            Simulador de Previdência Privada
          </h1>
          <p className="text-lg text-muted-foreground max-w-2xl mx-auto text-pretty">
            Faça login para acessar seu simulador
          </p>
        </div>
        <LoginForm />
      </div>
    </main>
  )
}
