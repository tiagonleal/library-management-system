## Testes Básicos

* A-01-01-M-ok - Abrir aplicação ver menu inicial
* A-01-02-M-ok - Abrir aplicação ver restantes menus
* A-01-03-M-ok - Abrir aplicação com Livros em import e ver as obras   
* A-01-04-M-ok - Abrir aplicação com DVD em import e ver as obras   
* A-01-05-M-ok - Abrir aplicação com um user em import e ver os users

## Mostrar Obras

* A-02-01-M-ok - Ver obras sem nenhuma obra 
* A-02-02-M-ok - Ver obras com vários Livros  
* A-02-03-M-ok - Ver obras com vários DVDs  
* A-02-04-M-ok - Ver obras só com várias obras Livros e DVDs

## Mostrar Obra

* A-04-01-M-ok - Mostrar obra não existente sem obras 
* A-04-02-M-ok - Mostrar obra não existente com obras 
* A-04-03-M-ok - Mostar livro existente com várias obras  
* A-04-04-M-ok - Mostar DVD existente com várias obras  
* A-04-05-M-ok - Mostar Livro existente com vários autores ordenados
* A-04-06-M-ok - Mostar Livro existente com vários autores desordenados

## Mostrar Utentes

* A-05-01-M-ok - Ver utentes sem nenhum utente
* A-05-02-M-ok - Abrir aplicação com utentes em import e ver os utentes
* A-05-03-M-ok - Abrir aplicação com users desordenados em import e ver os users 
* A-05-04-M-ok - Abrir aplicação com users desordenados e nomes repetidos em import e ver os users 

## Mostrar Data/Avançar

* A-06-01-M-ok - Mostrar data inicial 
* A-06-02-M-ok - Avançar data válida e Mostrar data
* A-06-03-M-ok - Avançar data inválida e Mostrar data 
* A-06-04-M-ok - Avançar data duas vezes e Mostrar data 

## Serialização/Desserialização (Abrir/Guardar em Ficheiro)

* A-07-01-M-ok - Serializar obras e guarda em works
* A-07-02-M-ok - Desserializar Obras e vê works
* A-07-03-M-ok - Serializar user  e guarda em user
* A-07-04-M-ok - Desserializar users vê user
* A-07-05-M-ok - Desserializar users, cria novo utilizador, vê novo utente
* A-07-06-M-ok - Desserializar users, cria novo utilizador, vê novo utente e guarda em user
* A-07-07-M-ok - Abre ficheiro não existente
* A-07-12-M-ok - Começar com import, guardar em user2.dat, adicionar utente, abrir ficheiro "user", responder sim
* A-07-13-M-ok - Começar com import, adicionar utente, abrir ficheiro "user", responder sim e guardar em user2.dat  
* A-07-14-M-ok - Abrir ficheiro user2.dat, ver users
* A-07-15-M-ok - Abrir ficheiro user2.dat, ver users, adicionar novo utente, guardar, abrir "user"
* A-07-16-M-ok - Começar com import, adicionar utente, abrir ficheiro "user", responder não  e ver utentes

## Registar Utente

* A-08-01-M-ok - Registar utente sem estado inicial 
* A-08-02-M-ok - Registar utente com estado inicial via import 
* A-08-03-M-ok - Registar utente em diferentes posições 
* A-08-04-M-ok - Registar utente com nome inválido
* A-08-05-M-ok - Registar utente com email inválido 

## Efectuar Pesquisa

* A-03-01-M-ok - Efectuar pesquisa sem nenhuma obra 
* A-03-02-M-ok - Efectuar pesquisa com string igual com uma obra  
* A-03-03-M-ok - Efectuar pesquisa com substring com uma obra  
* A-03-04-M-ok - Efectuar pesquisa com vários resultado por realizador DVD com várias obras  
* A-03-05-M-ok - Efectuar pesquisa com vários resultado por título DVD com várias obras  
* A-03-06-M-ok - Efectuar pesquisa com vários resultados Livro por autor/título e vários livros/DVD por autor
* A-03-07-M-ok - Efectuar pesquisa com dois resultados Livro/DVD que verificam ambos os campos
* A-03-08-M-ok - Efectuar pesquisa com um resultado Livro por autor/título e termo de pesquisa em maiúsculas/minúsculas com várias obras  
* A-03-09-M-ok - Efectuar pesquisa com um resultado DVD por realizador/título e termo de pesquisa em maiúsculas/minúsculas com várias obras
* A-03-10-M-ok - Efectuar pesquisa com um resultado Livro por autor/título e termo de pesquisa faz match com 3º autor
* A-03-11-M-ok - Efectuar pesquisa sem resultados com livro com vários autores

## Serialização/Desserialização

* A-07-08-M-ok - Serializar requisição e guarda em requests  
* A-07-09-M-ok - Dessserializar user, vê requisições, cria nova e vê
* A-07-10-M-ok - Serializar notificação e guarda em notifications 
* A-07-11-M-ok - Deserializar notificação e vê

## Mostrar Notificações

* A-09-01-M-ok - Ver utente sem notificações /Ver utente não existente
* A-09-02-M-ok - Ver utente com uma  notificação  
* A-09-03-M-ok - Ver utente sem  notificação  por recusar
* A-09-04-M-ok - Ver utente com várias notificações
* A-09-05-M-ok - Ver utente com uma notificação ver que é apagada
* A-09-06-M-ok - Ver utente com uma subscrição de disponibilidade e ver que se mantém  
* A-09-07-M-ok - Ver várias notificação para vários utentes
* A-09-08-M-ok - Ver utente com uma subscrição de disponibilidade e obra devolvida 2 vezes mas só gera uma notificação
* A-09-09-M-ok - Ver utente com uma subscrição de disponibilidade e ver que ela é cancelada automaticamente 
* A-09-10-M-ok - aumentar nº de exemplares em 1 unidade para obra sem disponíveis gera notificação    
* A-09-11-M-ok - aumentar nº de exemplares em 4 unidades para obra sem disponíveis gera notificação   
* A-09-12-M-ok - aumentar nº de exemplares em 1 unidade para obra com disponíveis não gera notificação     
* A-09-13-M-ok - diminuir nº de exemplares disponíveis para 1 não gera notificação
* A-09-14-M-ok - Verificar cancelar subscrição automático para obra com vários interessados
* A-09-15-M-ok - Registar interesse de disponibilidade duas vezes e ver notificação

## Requisita Obra/Suspender

* A-11-01-M-ok - Requisitar obra não existente 
* A-11-02-M-ok - Requisitar obra por user não existente 
* A-11-03-M-ok - Requisitar obra existente (7) com 1 exemplar por utente normal e ver prazo de entrega   
* A-11-04-M-ok - Requisitar obra existente com 5 exemplares (6) e com 3 exemplares (1) por utente normal e ver prazo de entrega   
* A-11-05-M-ok - Requisitar obra existente com mais do que 5 (2) exemplar por utente normal e ver prazo de entrega   
* A-11-06-M-ok - fazer normal (de 0) para cumpridor (5 entregas), guardar cumpridor
* A-11-07-M-ok - fazer normal para faltoso, guardar faltoso  (três atraso de uma vez) 
* A-11-08-M-ok - fazer faltoso para normal
* A-11-09-M-ok - fazer normal para faltoso (um atraso de cada vez)  
* A-11-10-M-ok - Levar faltoso a normal e depois a cumpridor
* A-11-11-M-ok - Requisitar obra existente (7) com 1 exemplar e com mais do que 5 (2) exemplar por utente cumpridor e ver prazo de entrega 
* A-11-12-M-ok - Requisitar obra existente com 5 exemplares (6) e com 3 exemplares (1) por utente cumpridor e ver prazo de entrega   
* A-11-13-M-ok - Requisitar obra existente (7) com 1 exemplar e com mais do que 5 (2) exemplar por utente faltoso e ver prazo de entrega 
* A-11-14-M-ok - Requisitar obra existente com 5 exemplares (6) e com 3 exemplares (1) por utente faltoso e ver prazo de entrega   
* A-11-15-M-ok - Requisitar obra existente por utente normal suspenso sem entegar  
* A-11-16-M-ok - Requisitar obra existente por utente normal suspenso entregar mas não pagar
* A-11-17-M-ok - Requisitar obra existente por utente cumpridor suspenso 
* A-11-18-M-ok - Requisitar obra existente por utente faltoso suspenso
* A-11-19-M-ok - Carregar cumpridor requisitar uma obra, fica atrasada e fica suspenso 
* A-11-20-M-ok - Carregar cumpridor requisitar três obras, uma fica atrasada e fica suspenso 
* A-11-21-M-ok - Requisitar uma obra por normal/faltoso, fica atrasada e fica suspenso 
* A-11-22-M-ok - Requisitar duas obras por normal, ficam atrasadas e fica suspenso 
* A-11-23-M-ok - Carregar cumpridor requisitar uma obra e levar a normal não suspenso 
* A-11-24-M-ok - Carregar cumpridor requisitar duas obras, ficar atrasado e levar a normal não suspenso e depois faltoso   
* A-11-25-M-ok - Carregar cumpridor requisitar três obras, ficar atrasado e levar a normal não suspenso e depois faltoso   
* A-11-26-M-ok - Carregar cumpridor requisitar três obra, fica atrasado para uma e entrega, entrega duas no prazo e entrega três novas no prazo e fica cumpridor
* A-11-27-M-ok - Cumpridor, requisitar 4, entregar três atrasadas, faltoso e tentar requisitar obra
* A-11-28-M-ok - Requisitar obra existente utente activo cumpridor sem vagas  
* A-11-29-M-ok - Requisitar obra existente utente activo faltoso e normal sem vagas  
* A-11-30-M-ok - Requisitar obra existente mas só com um exemplar por utente cumpridor e verificar que obra não pode ser requisitada  
* A-11-31-M-ok - Requisitar obra existente mas só com um exemplar por utente faltoso e sem exemplares disponíveis
* A-11-32-M-ok - Requisitar 4 exemplares de obra só com três existente
* A-11-33-M-ok - Requisitar obra existente duas vezes pelo mesmo utente
* A-11-34-M-ok - Requisitar obra existente de referência por utilizador normal e faltoso
* A-11-35-M-ok - Requisitar obra existente de referência por utilizador cumpridor
* A-11-36-M-ok - Requisitar obra existente com preço superior a 25 por utente cumpridor 
* A-11-37-M-ok - Requisitar obra existente com preço superior a 25 por utente normal e faltoso
* A-11-38-M-ok - Requisitar obra, não consegue e verificar que utente não fica com a obra 
* A-11-39-M-ok - Verifica ordem de regras de requisição (2x e suspenso mais prioritária que restantes) 
* A-11-40-M-ok - Verifica ordem de regras de requisição (sem obras, cheio, referência e preço  mais prioritária que restantes) 
 
## Devolver Obra

* A-12-01-M-ok - Devolver obra não requisitada/mão existente pelo utente 
* A-12-02-M-ok - Devolver obra requisitada por outro utente por utente existente/não existente
* A-12-03-M-ok - Requisitar obra, entregar no mesmo dia
* A-12-04-M-ok - Requisitar obra entregar no dia seguinte por utente normal 
* A-12-05-M-ok - Requisitar obra entregar no último dia do prazo por utente normal/faltoso
* A-12-06-M-ok - Requisitar obra entregar no último dia do prazo por utente cumpridor
* A-12-07-M-ok - Requisitar obra entregar no dia seguinte do prazo por utente cumpridor 
* A-12-08-M-ok - Requisitar obra entregar no dia seguinte do prazo por utente faltoso/normal 
* A-12-09-M-ok - Requisitar obra entregar três dias depois do prazo por utente cumpridor 
* A-12-10-M-ok - Requisitar obra entregar três dias depois do prazo por utente normal e faltoso
* A-12-11-M-ok - Requisitar obra, utente normal fica cheio, devolver obra e requisitar de seguida
* A-12-12-M-ok - faltoso, devolver 5 obras, 2 no prazo, uma atrasado e duas no prazo, continua faltoso 
* A-12-13-M-ok - normal, devolver 5 obras, 2 fora do prazo, uma no prazo e duas atrasdas, continua normal 
* A-12-14-M-ok - normal, devolver 7 obras, 3 dentro do prazo, uma fora do prazo e três no prazo, continua normal 
* A-12-15-M-ok - cumpridor, Devolver 5 obras  obra dentro do prazo e continua cumpridor 
* A-12-16-M-ok - normal com 3 entregas no prazo, requisita mais 3 e fica com uma atrasada, devolve duas no prazo e passa a cumpridor e devolve atrasada passa a normal.

## Pagar Multa

* A-13-01-M-ok - faltoso/normal, 1 obra atrasada entrega e não paga multa. Paga depois 
* A-13-02-M-ok - normal, 2 obra atrasada entrega e não paga multa. Paga depois  
* A-13-03-M-ok - normal, 2 obra atrasada entrega 1 e não paga multa, entrega 2ª e paga.
* A-13-04-M-ok - cumpridor 2 obra atrasada entrega, paga a primeira mas não paga a 2ª multa na entrega. Paga depois 
* A-13-05-M-ok - Paga utente sem multa/não existente 
* A-13-06-M-ok - Paga utente suspenso e com multa a 0
* A-13-07-M-ok - Paga utente suspenso com multa e com obras em atraso 
* A-13-08-M-ok - Obra atrasada, alterar data e ver que multa aumenta 
* A-13-09-M-ok - Obra atrasada, entrega obra,  alterar data e ver que multa mantém
* A-13-10-M-ok - cumpridor 2 obra atrasada entrega e não paga multa nos dois casos. Paga depois 

## Alterar exemplares de obra

* A-14-01-M-ok - Acrescentar exemplares a livro e DVD 
* A-14-02-M-ok - Diminuir exemplares a livro e DVD sem requisições
* A-14-03-M-ok - Diminuir exemplares a livro e DVD com requisições
* A-14-04-M-ok - Diminuir exemplares com valor maior que nº de unidades de livro sem requisições 
* A-14-05-M-ok - Diminuir exemplares com valor maior que nº de unidades disponíveis de DVD
* A-14-06-M-ok - Colocar exemplares disponíveis a 0 a livro e DVD com requisições 
* A-14-07-M-ok - Colocar exemplares disponíveis a 0 a livro e DVD sem requisições e ver Obra
* A-14-08-M-ok - Verificar que diminuir nº exemplares não tem impacto ho pazo das requisições já feitas 
* A-14-09-M-ok - Verificar que aumentar nº exemplares não tem impacto ho pazo das requisições já feitas 

## Ver Obras de Criador

* A-15-01-M-ok - Ver obras de criador só com um livros e só com um DVDs
* A-15-02-M-ok - Ver obras de criadores de obra com vários criadores
* A-15-03-M-ok - Ver obras de criador com livros e DVDs já ordenados
* A-15-04-M-ok - Ver obras de criador com livros e DVDs desordenados
* A-15-05-M-ok - Ver obras de criador com livros e DVDs desordenados maiusculas/minusculas
* A-15-06-M-ok - Ver obras de criador não existente
* A-15-07-M-ok - Ver obras de criador não existente mas com string presente em na concatenação dos nomes todos
* A-15-08-M-ok - Colocar exemplares disponíveis a 0 a livro com um criador com várias obras e ver Criador
* A-15-09-M-ok - Colocar exemplares disponíveis a 0 a DVD com um criador com várias obras e ver Criador
* A-15-10-M-ok - Colocar exemplares disponíveis a 0 a livro com dois criadores com várias obras e ver Criadores
* A-15-11-M-ok - Ver obras de criador com uma obra que deixou de ter obras
* A-15-12-M-ok - Ver obras de criador com várias obras que deixou de ter obras

