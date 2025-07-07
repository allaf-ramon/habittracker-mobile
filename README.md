# Rastreador de Hábitos - App Mobile

Este é um aplicativo Android para rastreamento de hábitos, desenvolvido como parte de um projeto de estudos.

## Descrição

O aplicativo permite que os usuários cadastrem, visualizem, atualizem e deletem hábitos, além de marcar um hábito como "concluído" para o dia. O objetivo é ajudar os usuários a construir e manter rotinas positivas.

A aplicação consome uma API REST para persistir os dados dos hábitos.

## Funcionalidades

- Listar todos os hábitos.
- Adicionar um novo hábito.
- Visualizar detalhes de um hábito.
- Marcar um hábito como concluído/não concluído.
- Editar um hábito existente.
- Excluir um hábito.

## Tecnologias e Bibliotecas

- **Linguagem:** Java
- **Arquitetura:** MVVM (Model-View-ViewModel)
- **Componentes de UI:**
  - Material Design 3
  - RecyclerView para listas
- **Comunicação com API:**
  - Retrofit 2
  - Gson
- **Gerenciamento de Ciclo de Vida:**
  - ViewModel
  - LiveData
- **Build:** Gradle

## Como Executar

1.  Clone este repositório.
2.  Abra o projeto no Android Studio.
3.  Certifique-se de que a [API do Rastreador de Hábitos](https://github.com/allaf-ramon/habittracker-api) esteja em execução e acessível pela rede.
4.  Atualize o endereço da API no código-fonte, se necessário (atualmente configurado para `http://10.0.2.2:8080` para emuladores Android).
5.  Compile e execute o aplicativo em um emulador ou dispositivo Android.
