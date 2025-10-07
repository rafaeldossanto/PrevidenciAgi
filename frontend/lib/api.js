const API_URL = process.env.NEXT_PUBLIC_API_URL;

// Buscar todos os clientes
export async function getClientes() {
  const res = await fetch(`${API_URL}/clientes`);
  if (!res.ok) throw new Error("Erro ao buscar clientes");
  return res.json();
}

// Criar um novo cliente
export async function criarCliente(cliente) {
  const res = await fetch(`${API_URL}/clientes`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(cliente),
  });
  if (!res.ok) throw new Error("Erro ao criar cliente");
  return res.json();
}

// Atualizar cliente
export async function atualizarCliente(id, cliente) {
  const res = await fetch(`${API_URL}/clientes/${id}`, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(cliente),
  });
  if (!res.ok) throw new Error("Erro ao atualizar cliente");
  return res.json();
}

// Deletar cliente
export async function deletarCliente(id) {
  const res = await fetch(`${API_URL}/clientes/${id}`, {
    method: "DELETE",
  });
  if (!res.ok) throw new Error("Erro ao deletar cliente");
  return res.json();
}
