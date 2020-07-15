Feature: Enviar uma request para a API e validar os métodos de POST, GET, PUT, DELETE


  @validacaoAplicacao @testeAPI1
  Scenario: Enviar uma Requisição para a API validando o nome do usuário informado no request
    Given valido_endpoint "users"
   Then valido_nome_response "users" "Helena" "1"

  @validacaoAplicacao @testeAPI2
  Scenario: Enviar uma Requisição para a API validando o id do usuário informado no request
    Given valido_endpoint "users"
    Then valido_userId_response "users" "12"

  @validacaoAplicacao @testeAPI3
  Scenario: Enviar uma Requisição para a API deletando um registro
    Given valido_endpoint "users"
    Then deletar_user_ID "users" "72"

  @validacaoAplicacao @testeAPI4
  Scenario: Enviar uma Requisição para a API criando um registro
    Given valido_endpoint "users"
    Then criar_registro_api "users" ""

  @validacaoAplicacao @testeAPI5
  Scenario: Enviar uma Requisição para a API atualizando  um registro
    Given valido_endpoint "users"
    Then update_registro_api "users" "5"

  @validacaoAplicacao @testeAPI6
  Scenario: Enviar uma Requisição para a API validando o nome do usuário informado no request com endpoint invalido
    Given valido_endpoint "users"
    Then valido_nome_response "teste" "Andre" "1"

  @validacaoAplicacao @testeAPI7
  Scenario: Enviar uma Requisição para a API validando o nome do usuário informado no request
    Given valido_endpoint "users"
    Then valido_nome_response "users" "testexyz" "1"


  @validacaoAplicacao @testeAPI8
  Scenario: Enviar  Requisição para a API validando todos os nomes de usuários informado pelo dataprovider no request
    Given valido_endpoint "users"
    Then valido_nome_response_dataprovider "users" "dataprovider" ""

  @validacaoAplicacao @testeAPI9
  Scenario: Enviar uma Requisição para a API atualizando  vários registros usando dataprovider
    Given valido_endpoint "users"
    Then update_registro_api_dataprovider "users" "updates"