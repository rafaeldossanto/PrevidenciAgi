"use client";

import { useEffect, useState } from "react";
import { getClientes } from "@/lib/api";

export default function HomePage() {
  const [clientes, setClientes] = useState([]);

  useEffect(() => {
    async function carregar() {
      try {
        const lista = await getClientes();
        setClientes(lista);
      } catch (erro) {
        console.error("Erro ao carregar clientes:", erro);
      }
    }
    carregar();
  }, []);

  return (
    <main className="p-6">
      <h1 className="text-2xl font-bold mb-4">Lista de Clientes</h1>
      <ul>
        {clientes.map((c) => (
          <li key={c.id}>{c.nome}</li>
        ))}
      </ul>
    </main>
  );
}
