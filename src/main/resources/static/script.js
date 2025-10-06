// Preenche select de anos
window.addEventListener("DOMContentLoaded", () => {
  const anoSelect = document.getElementById("anoAposentadoria");
  const anoAtual = new Date().getFullYear();
  for (let i = anoAtual; i <= anoAtual + 50; i++) {
    const opt = document.createElement("option");
    opt.value = i;
    opt.innerText = i;
    anoSelect.appendChild(opt);
  }
});

// Envia dados pro backend
function enviarContrato(event) {
  event.preventDefault();

  const tipo = document.getElementById("tipo").value;
  const regime = document.getElementById("regime").value;
  const ano = document.getElementById("anoAposentadoria").value;
  const valorMensal = parseFloat(document.getElementById("valorMensal").value);

  // Aqui você pega o ID do usuário logado
  const usuarioId = 123; // substituir pelo ID real do usuário vindo da sessão ou token

  const data = {
    tipoAposentadoria: tipo + "_" + regime,
    valor_mensal: valorMensal,
    valor_deposito: valorMensal, // mesmo valor inicial do depósito
    data_contratada: new Date().toISOString().split('T')[0],
    data_aposentar: ano + "-01-01",
    data_inicio: new Date().toISOString().split('T')[0],
    id: usuarioId
  };

  fetch('/aposentadoria/assinar', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(data)
  })
  .then(res => res.json())
  .then(res => {
    document.getElementById("resultado").innerText = "Contrato assinado com sucesso!";
    console.log("Resposta do backend:", res);
  })
  .catch(err => {
    console.error("Erro:", err);
    document.getElementById("resultado").innerText = "Erro ao assinar contrato!";
  });
}
