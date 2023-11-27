# AdaTech-Nuclea-Bingo
Repositório com projeto do curso.

Bingo Game
TL;DL
Construir um jogo de bingo multiplayer para rodar local com opções para sorteio automático aleatório ou manual que ao final indique alguns dados estatísticos

Game
01 - Etapa 1 - Apresentação e Jogadores
Mensagem de boas vindas e mostrar opções de comando
Dar um design bacana para o visual e dê um nome ao jogo
Considerar o modo multiplayer automático com 1 ou mais jogadores
Separar por hifen o nickname: player1-player2-player3

02 - Etapa 2 - Cartelas
Gerar as cartelas desejadas
Opções de comando para cartelas geradas RANDOM ou MANUAL
Para MANUAL localizar o nickname preencher a cartela
O input deverá ser: "1,2,3,4,5-6,7,8,9,1-2,3,4,5,6"
Para RANDOM será gerado automaticamente aleatórias cartelas não repetidas
A cartela não pode ter números repetidos
No dia do game iremos expor todas as cartelas como neste exemplo que fizemos no pad
Veja que aqui no pad fizemos um ensaio manual. A idéia é que o programa gere as cartelas

03 - Etapa 3 - Números Sorteados
Opções de comando do sorteio podem ser RANDOM ou MANUAL
Para MANUAL os números sorteados entrarão via Scanner
O input deverá seguir a sintaxe: "1,2,3,4,5"
Para RANDOM serão números aleatórios não repetidos na cartela
A cada round deverá imprimir:
Um ranking dos top 3 mais bem pontuados no game
Um pedido de pressionar a tecla para continuar via Scanner
Se pressionar X aborta o game

04 - Etapa 4 - Fim do Jogo
Cada jogador terá um array para indicar os números acertados
Esse array indica a posição de cada número na cartela
Aqui temos os dois ultimos números acertados, ex: 0,0,0,1,1
O bingo será eleito quando o jogador tiver todos com número 1
Ao final do game exibir o ranking geral e estatísticas do game:
Quantidade de rounds
Cartela premiada com números ordenados e nome do vencedor
Quantidade e números sorteados em ordem
Ranking geral ordenado pelo número de acertos

05 - Etapa 5 - Regras Gerais e Sugestões para implementar
Não usar classes e derivadas de Collections, use array/matriz
No modo manual iremos anunciar as cartelas no pad.riseup
Daí cada um marca a sua cartela manualmente para acompanhar
Pode usar classes utilitárias do java.util como Random e Arrays
Estruture seu código em métodos por responsabilidade



