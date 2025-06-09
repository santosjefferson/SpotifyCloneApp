# Spotify Clone App

Este é um projeto de aplicativo Android, desenvolvido como parte de um desafio para o processo seletivo para a vaga de Android Developer na LuizaLabs, que se comunica com a API do Spotify para exibir informações do usuário, seus principais artistas, álbuns e playlists.

---

## 🎯 Objetivo do Projeto

O objetivo principal deste projeto foi construir uma aplicação Android nativa capaz de:

* Autenticar um usuário com a API do Spotify.
* Listar os artistas mais ouvidos pelo usuário.
* Listar os álbuns de um artista específico.
* Listar as playlists do usuário.
* Exibir os dados do perfil do usuário.
* Prover funcionamento offline para os dados listados (artistas, álbuns, playlists, perfil).

---

## ✨ Funcionalidades Implementadas

A seguir estão os requisitos considerados para a solução, marcando o que foi efetivamente implementado:

### Requisitos Obrigatórios
- [x] **Autenticação via Spotify:** Implementada utilizando o Spotify Android Auth SDK.
- [x] **Listar artistas:** Exibe os top artistas do usuário com paginação (limit e offset).
- [x] **Listar álbuns de um artista:** Exibe os álbuns de um artista selecionado ao clicar no artista.
- [x] **Utilizar paginação (scroll infinito ou não):** Implementada via parâmetros `limit` e `offset` nas chamadas de API. Embora o scroll infinito na UI não tenha sido aprofundado devido ao prazo, a carga inicial já demonstra o conceito.
- [x] **Funcionamento offline (manter dados em storage local):** Todos os dados listados (artistas, álbuns, playlists, perfil) são persistidos localmente usando Room Database, garantindo exibição mesmo sem conexão de rede após o primeiro carregamento.
- [ ] Testes unitários: *Não foi possível implementar testes unitários no MVP. Esta seria a próxima etapa para garantir a qualidade e robustez do código.*
- [x] Segmentação de commits: Os commits são segmentados por funcionalidade/etapa de desenvolvimento, facilitando o rastreamento do progresso.

### Bônus
- [ ] Testes instrumentados: *Não implementados devido ao prazo.*
- [ ] Integração com Firebase (Crashlytics): *Não implementada devido ao prazo.*
- [ ] CI/CD (pipelines e deploy): *Não implementada devido ao prazo.*
- [ ] Responsividade (celular e tablet): O layout foi desenvolvido pensando em celular, com adaptações básicas de responsividade através do `ConstraintLayout` e `fitsSystemWindows`.

---

## 🏗️ Arquitetura e Tecnologias Escolhidas

A solução do desafio foi desenvolvida seguindo os princípios de **Clean Architecture** e o padrão **MVVM (Model-View-ViewModel)** para garantir manutenabilidade, escalabilidade e desempenho.

### Estrutura da Clean Architecture:
-   **Domain Layer:** Contém as entidades de negócio (modelos de domínio), interfaces de repositório e **Use Cases** (ou Interactors). É a camada mais interna e agnóstica a qualquer framework Android ou fonte de dados, focada nas regras de negócio.
-   **Data Layer:** Responsável pela obtenção e persistência dos dados. Inclui fontes de dados remotas (API com Retrofit) e locais (banco de dados com Room), mapeadores (DTOs para Entidades/Domínio) e a implementação dos repositórios definidos na camada de Domínio. Ela lida com os detalhes de implementação de como e de onde os dados vêm.
-   **Presentation Layer:** Lida com a interface do usuário. Contém Activities, ViewModels e Adapters para exibir os dados e gerenciar a interação do usuário. Essa camada observa os dados expostos pelos ViewModels e reage a mudanças de estado.

### Tecnologias e Bibliotecas Principais:
-   **Kotlin:** Linguagem de programação primária, moderna e segura para desenvolvimento Android.
-   **Android Jetpack:** Coleção de bibliotecas que ajudam a construir apps de alta qualidade.
    -   **Lifecycle Components (ViewModel, LiveData/StateFlow):** Para gerenciar o ciclo de vida da UI e expor dados de forma reativa e segura. `StateFlow` foi utilizado para gerenciamento de estado reativo no `ViewModel`.
    -   **Room Database:** ORM para persistência de dados local (caching) e suporte offline, permitindo que o aplicativo funcione mesmo sem conexão à internet após o primeiro carregamento dos dados.
    -   **Activity Result API:** Para lidar com resultados de atividades (como o fluxo de autenticação do Spotify) de forma moderna, eliminando o uso obsoleto de `onActivityResult`.
-   **Koin:** Framework de **Injeção de Dependência (DI)**.
    -   **Justificativa para o Koin:** Foi escolhido por sua simplicidade, facilidade de configuração e curva de aprendizado mais suave em comparação com ferramentas como Dagger Hilt. Isso permitiu uma rápida prototipagem e desenvolvimento do MVP dentro do prazo apertado, mantendo os benefícios da inversão de controle e testabilidade.
-   **Retrofit:** Cliente HTTP para comunicação com a **API REST do Spotify**, facilitando a declaração de endpoints e o mapeamento de respostas JSON.
-   **OkHttp:** Cliente HTTP subjacente ao Retrofit, configurado com interceptores para logging (HttpLoggingInterceptor, útil para depuração de requisições de rede) e adição automática de tokens de autorização (AuthInterceptor).
-   **Moshi:** Biblioteca de serialização/desserialização JSON, escolhida por sua interoperabilidade com Kotlin, segurança de tipo e boa integração com Retrofit e OkHttp.
-   **Kotlin Coroutines & Flow:** Para gerenciamento de concorrência e programação assíncrona, garantindo que operações de rede e banco de dados não bloqueiem a thread principal da UI, mantendo o aplicativo responsivo. `Flow` foi utilizado para emitir múltiplos valores de forma assíncrona.
-   **Glide:** Biblioteca para carregamento e cache de imagens de forma eficiente e otimizada na UI.

---

## ⚙️ Como Executar a Aplicação

Siga os passos abaixo para configurar e executar o projeto em seu ambiente:

### Pré-requisitos
-   **Android Studio** (Versão Giraffe 2022.3.1 ou superior, compatível com Android Gradle Plugin 8.x).
-   **JDK 17** configurado para o Gradle (via Android Studio Settings e a variável de ambiente `JAVA_HOME` no seu sistema operacional).
-   **Git** instalado no seu sistema (verifique com `git --version` no terminal).
-   Conexão com a internet para baixar dependências do projeto e para o primeiro login com a API do Spotify.
-   Um dispositivo Android (físico ou emulador) com acesso à Play Store para instalar o aplicativo Spotify (necessário para o fluxo de autenticação).

### 1. Configurar Credenciais do Spotify API
1.  Vá para [https://developer.spotify.com/dashboard/](https://developer.spotify.com/dashboard/) e faça login com sua conta Spotify.
2.  Clique em "Create an App" e preencha os detalhes (Nome e Descrição).
3.  Após criar o app, você verá o **Client ID**. Copie-o.
4.  Em "Redirect URIs", adicione `com.seuprimeironome.spotifycloneapp://callback` (substitua `com.seuprimeironome.spotifycloneapp` pelo `applicationId` configurado no seu `build.gradle.kts` do módulo `app`). **É crucial que o valor seja exatamente igual ao do seu `applicationId`.**
5.  Role para baixo até a seção "Android Hashes" e clique em "Add Fingerprint". Para obter o SHA1 do seu certificado de depuração:
    * No Android Studio, abra o painel **"Gradle"** (geralmente à direita).
    * Navegue até `[root_project_name]` -> `Tasks` -> `android` -> clique duplo em `signingReport`.
    * No "Build Output" (ou na janela do terminal que abriu), copie o valor **SHA1** (ex: `YY:YY:YY:YY:YY:YY:YY:YY:YY:YY:YY:YY:YY:YY:YY:YY:YY:YY:YY:YY`).
    * Cole-o no campo "SHA-1 fingerprint" no Spotify Developer Dashboard e **clique em "Save"** na parte inferior da página.
