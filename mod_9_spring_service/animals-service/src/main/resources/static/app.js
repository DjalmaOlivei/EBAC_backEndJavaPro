const state = {
  animals: { cachorros: [], gatos: [] },
  catalog: { cachorros: [], gatos: [] },
  status: 'all'
};

const speciesLabels = { cachorros: 'Cão', gatos: 'Gato' };

const notice = document.querySelector('#notice');
const rows = document.querySelector('#animal-rows');
const searchInput = document.querySelector('#search');

async function requestJson(url, options) {
  const response = await fetch(url, options);
  if (!response.ok) {
    const body = await response.text();
    throw new Error(body || `A solicitação falhou (${response.status}).`);
  }
  if (response.status === 204) return null;
  return response.json();
}

function showNotice(message, type) {
  notice.textContent = message;
  notice.className = `notice ${type}`;
  notice.hidden = false;
}

function clearNotice() {
  notice.hidden = true;
  notice.textContent = '';
}

function allAnimals() {
  return Object.entries(state.catalog).flatMap(([species, animals]) =>
    animals.map(animal => ({ ...animal, species }))
  );
}

function refreshStats() {
  const animals = allAnimals();
  document.querySelector('#stat-total').textContent = animals.length;
  document.querySelector('#stat-available').textContent =
    animals.filter(animal => !animal.dataSaida).length;
  document.querySelector('#stat-adopted').textContent =
    animals.filter(animal => animal.dataSaida).length;
}

async function loadAllAnimals() {
  const [dogs, cats] = await Promise.all([
    requestJson('/cachorros/listar'),
    requestJson('/gatos/listar')
  ]);
  state.animals = { cachorros: dogs, gatos: cats };
  state.catalog = { cachorros: dogs, gatos: cats };
  refreshStats();
  renderAnimals();
}

function formatDate(value) {
  if (!value) return '—';
  const parts = value.slice(0, 10).split('-');
  if (parts.length !== 3) return value;
  return `${parts[2]}/${parts[1]}/${parts[0]}`;
}

function localDateValue(date) {
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, '0');
  const day = String(date.getDate()).padStart(2, '0');
  return `${year}-${month}-${day}`;
}

function renderAnimals() {
  const speciesToShow = ['cachorros', 'gatos'];
  const query = searchInput.value.trim().toLocaleLowerCase('pt-BR');
  const animals = speciesToShow.flatMap(species =>
    state.animals[species].map(animal => ({ ...animal, species }))
  ).filter(animal => {
    const isAdopted = Boolean(animal.dataSaida);
    const matchesStatus = state.status === 'all'
      || (state.status === 'available' && !isAdopted)
      || (state.status === 'adopted' && isAdopted);
    const searchable = `${animal.nomeProvisorio || ''} ${animal.raca || ''}`.toLocaleLowerCase('pt-BR');
    return matchesStatus && searchable.includes(query);
  });

  rows.replaceChildren();
  if (animals.length === 0) {
    const emptyRow = document.createElement('tr');
    const message = document.createElement('td');
    message.colSpan = 6;
    message.className = 'table-message';
    message.textContent = 'Nenhum animal encontrado com esses filtros.';
    emptyRow.append(message);
    rows.append(emptyRow);
  } else {
    animals.forEach(animal => rows.append(createAnimalRow(animal)));
  }
  document.querySelector('#table-count').textContent =
    `${animals.length} ${animals.length === 1 ? 'animal exibido' : 'animais exibidos'}`;
}

function createAnimalRow(animal) {
  const row = document.createElement('tr');
  const nameCell = document.createElement('td');
  const nameContent = document.createElement('div');
  const avatar = document.createElement('span');
  avatar.className = 'animal-avatar';
  avatar.setAttribute('aria-hidden', 'true');
  avatar.textContent = animal.species === 'gatos' ? '🐈' : '🐕';
  const name = document.createElement('span');
  name.textContent = animal.nomeProvisorio || 'Sem nome';
  nameContent.className = 'animal-cell';
  nameContent.append(avatar, name);
  nameCell.append(nameContent);

  const cells = [
    speciesLabels[animal.species],
    `${animal.idadeEstimada} ${animal.idadeEstimada === 1 ? 'ano' : 'anos'}`,
    formatDate(animal.dataEntrada),
    animal.resgatador || '—'
  ];
  row.append(nameCell);
  cells.forEach(value => {
    const cell = document.createElement('td');
    cell.textContent = value;
    if (value === speciesLabels[animal.species]) cell.className = 'species-tag';
    row.append(cell);
  });

  const statusCell = document.createElement('td');
  const badge = document.createElement('span');
  const adopted = Boolean(animal.dataSaida);
  badge.className = `status-pill ${adopted ? 'status-adopted' : 'status-available'}`;
  badge.textContent = adopted ? 'Adotado' : 'Para adoção';
  statusCell.append(badge);
  row.append(statusCell);
  return row;
}

function setPage(page) {
  document.querySelectorAll('[data-page]').forEach(button => {
    if (button.classList.contains('nav-link')) {
      button.classList.toggle('active', button.dataset.page === page);
    }
  });
  document.querySelector('#animals-page').hidden = page !== 'animals';
  document.querySelector('#register-page').hidden = page !== 'register';
  document.querySelector('#ranking-page').hidden = page !== 'ranking';
  clearNotice();
  if (page === 'register') {
    document.querySelector('#animal-form').elements.dataEntrada.value =
      localDateValue(new Date());
  }
}

document.querySelectorAll('[data-page]').forEach(button => {
  button.addEventListener('click', () => setPage(button.dataset.page));
});

document.querySelectorAll('[data-status]').forEach(button => {
  button.addEventListener('click', async () => {
    const previousStatus = state.status;
    state.status = button.dataset.status;
    document.querySelectorAll('[data-status]').forEach(filter =>
      filter.classList.toggle('selected', filter === button)
    );
    const path = {
      all: '/listar',
      available: '/para-adocao',
      adopted: '/adotados'
    }[state.status];
    try {
      const [dogs, cats] = await Promise.all([
        requestJson(`/cachorros${path}`),
        requestJson(`/gatos${path}`)
      ]);
      state.animals = { cachorros: dogs, gatos: cats };
      refreshStats();
      renderAnimals();
      clearNotice();
    } catch (error) {
      state.status = previousStatus;
      document.querySelectorAll('[data-status]').forEach(filter =>
        filter.classList.toggle('selected', filter.dataset.status === previousStatus)
      );
      showNotice(`Não foi possível carregar os animais: ${error.message}`, 'error');
    }
  });
});

searchInput.addEventListener('input', renderAnimals);

document.querySelector('#animal-form').addEventListener('submit', async event => {
  event.preventDefault();
  const form = event.currentTarget;
  const formData = new FormData(form);
  const species = formData.get('species');
  const payload = {
    nomeProvisorio: formData.get('nomeProvisorio').trim(),
    idadeEstimada: Number(formData.get('idadeEstimada')),
    raca: formData.get('raca').trim(),
    dataEntrada: formData.get('dataEntrada'),
    dataSaida: formData.get('dataSaida') || null,
    condicaoChegada: formData.get('condicaoChegada').trim(),
    nomeRecebedor: formData.get('nomeRecebedor').trim() || null,
    dataObito: formData.get('dataObito') || null,
    porte: formData.get('porte'),
    resgatador: formData.get('resgatador').trim()
  };
  const submitButton = form.querySelector('[type="submit"]');
  submitButton.disabled = true;
  submitButton.textContent = 'Salvando...';
  clearNotice();

  try {
    await requestJson(`/${species}`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload)
    });
    form.reset();
    state.status = 'all';
    document.querySelectorAll('[data-status]').forEach(button =>
      button.classList.toggle('selected', button.dataset.status === 'all')
    );
    setPage('animals');
    showNotice('Animal cadastrado com sucesso.', 'success');
    try {
      await loadAllAnimals();
      renderAnimals();
    } catch (error) {
      state.animals = state.catalog;
      renderAnimals();
      showNotice(`Cadastro salvo, mas não foi possível atualizar a lista: ${error.message}`, 'error');
    }
  } catch (error) {
    showNotice(`Não foi possível cadastrar o animal: ${error.message}`, 'error');
  } finally {
    submitButton.disabled = false;
    submitButton.innerHTML = 'Salvar cadastro <span aria-hidden="true">→</span>';
  }
});

const rankingForm = document.querySelector('#ranking-form');
rankingForm.elements.dataInicio.value = `${new Date().getFullYear()}-01-01`;
rankingForm.elements.dataFim.value = localDateValue(new Date());

rankingForm.addEventListener('submit', async event => {
  event.preventDefault();
  const formData = new FormData(rankingForm);
  const species = formData.get('species');
  const dataInicio = formData.get('dataInicio');
  const dataFim = formData.get('dataFim');
  const rankingContent = document.querySelector('#ranking-content');
  if (dataInicio > dataFim) {
    showNotice('A data inicial precisa ser anterior ou igual à data final.', 'error');
    return;
  }

  clearNotice();
  rankingContent.replaceChildren();
  const loading = document.createElement('p');
  loading.className = 'table-message';
  loading.textContent = 'Consultando ranking...';
  rankingContent.append(loading);

  try {
    const params = new URLSearchParams({ dataInicio, dataFim });
    const result = await requestJson(`/${species}/ranking-resgatadores?${params}`);
    document.querySelector('#ranking-heading').textContent =
      `Resgates de ${species === 'gatos' ? 'gatos' : 'cães'} no período`;
    rankingContent.replaceChildren();
    if (!Array.isArray(result) || result.length === 0) {
      const empty = document.createElement('p');
      empty.className = 'table-message';
      empty.textContent = 'Nenhum resgate encontrado nesse período.';
      rankingContent.append(empty);
      return;
    }
    result.forEach((entry, index) => {
      const row = document.createElement('div');
      row.className = 'ranking-row';
      const position = document.createElement('span');
      position.className = 'rank-number';
      position.textContent = index + 1;
      const name = document.createElement('span');
      name.className = 'rank-name';
      name.textContent = Array.isArray(entry) ? entry[0] : entry.resgatador;
      const count = document.createElement('span');
      count.className = 'rank-count';
      const amount = document.createElement('strong');
      amount.textContent = Array.isArray(entry) ? entry[1] : entry.quantidade;
      count.append(amount, ` ${Number(amount.textContent) === 1 ? 'resgate' : 'resgates'}`);
      row.append(position, name, count);
      rankingContent.append(row);
    });
  } catch (error) {
    rankingContent.replaceChildren();
    showNotice(`Não foi possível consultar o ranking: ${error.message}`, 'error');
  }
});

loadAllAnimals().catch(error => {
  rows.replaceChildren();
  const row = document.createElement('tr');
  const message = document.createElement('td');
  message.colSpan = 6;
  message.className = 'table-message';
  message.textContent = 'Não foi possível conectar ao serviço de animais.';
  row.append(message);
  rows.append(row);
  showNotice(`Verifique se o backend está em execução e tente novamente: ${error.message}`, 'error');
});
