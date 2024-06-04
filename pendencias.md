Fazer as correções necessárias no front end da aplicação
//Iniciar a refatoração do projeto para ter um melhor desacoplamento e organização do código -- pedir ajuda
```

O que ja fiz:
    --> Corrigir todas os controller <Cliente> para recuperar o Header da requisição e setar o contexto do usuario logado
    --> Iniciei a refatoração da entidade Cliente
    --> Use case impl para salvar no banco de dados falta os demais metodos
    --> Criar um patch para ativar e desativar um cliente !!!
    --> Refatorei a classe de clientes
    --> Refatorar a entidade Produto
    -->Refatorar a entidade Despesa
    -->Refatorar a entidade Receita
    -->Refatorar a entidade Venda
    -->Refatorar a entidade IemVenda
    -->Refatorar a entidade Checkout
    --> Retirar o campo dataUpdated e alterar o tipo de LocalDateTime para LocalDate
    --> No item Venda Adicionar o compo DataCreated

O que fazer:
    ***** Como adicionar no caixa a opçao de adicionar recebimento de contas a receber


```[]: # Path: pendencias.md


ANTES DE FAZER O UPDATE  - em produção
ALTERAR O CAMPO NO BANCO DE DADOS DE Clientes e Fornecedores para aceitar LocalDateTime
ALTERAR O CAMPO cpf  NO BANCO DE DADOS DE Clientes  e Fornecedores para cnpj para aceitar documento
ADICIOANAR O CAMPO STATUS NAS TABELAS DE FORNCEDORES E CLIENTES
ALTERAR OS CLIENTES JA CADASTRADOS COLOCANDO O STATUS COMO ATIVO
ALTERAR OS CAMPOS DATA_CREATED E DATA_UPDATE PARA DATETIME de todas as tabelas e verificar os nomes dos campos




****IMPORTANTE****
Verificar depois a logica do nome do produto para garantir que nao seja null

