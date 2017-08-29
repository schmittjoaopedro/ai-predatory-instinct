# Algoritmo genético para otimização aplicada em estratégias de jogos.

Atualmente diversas inteligências vem sendo aplicadas em jogos onlines com o objetivo de gerar uma maior similaridade com mundo real. As inteligências artificais normalmente são aplicadas em interações com agentes do jogo ou para criar um inimigo com o objetivo de nos derrotar.

## Contextualização

### Algoritmos Genéticos

Algoritmos genéticos pertencem uma classe de técnicas inspiradas na evolução das espécies (teoria de sobrevivência das espécies - Darwin) que visam resolver problemas de otimização complexos. Normalmente a classe de problemas que os algoritmos genéticos visam resolver possuem um tempo de processamento extremamente alto para encontrar da melhor solução usando métodos exatos, o que os torna inviáveis para cenários que necessitam de respostas rápidas. Dessa forma, algoritmos genéticos tentam fornecer soluções ótimas ou muito próximas das ótimas em um tempo menor de execução.

### Clash Royale

Clash Royale é um jogo de estratégia baseado em jogadas sequênciais de cartas que são posicionadas na arena com o objetivo de derrubar as torres do inimigo. As cartas somente podem ser posicionadas nos espaços conquistados e cada carta possui um lógica única que descreve sua interação com a arena. Na figura do exemplo abaixo, o jogador azul jogou duas cartas que avançaram pela ponte na zona do jogador em vermelho com o objetivo de destruir a torre. Além do posicionamento, cada carta possui um custo de elixir para ser lançada, assim conforme o tempo vai passando o elixir do jogador vai enchendo e liberando o uso das cartas.

<img src="http://i.utdstc.com/screen/android/thumb/clash-royale-5.jpg" width="200">

## Problema

Como definir qual a melhor jogada a ser feita a cada momento de tempo no estado atual da arena de forma a maximizar a diferença entre vida perdida tomado e vida tirada?

## Métodologia

A seguir será apresentada a métodologia aplicada para resolver o problema de determinar a melhor jogada a cada segundo de tempo com base no estado atual da arena. Todo o código fonte em Java está disponível no seguinte repositório do [Github](https://github.com/schmittjoaopedro/ai-predatory-instinct).

### Modelagem simplificada do jogo

Para poder-se realizar os testes do algoritmo genético, foi necessário recriar o jogo em um cenário muito mais simplificado, assim, as seguintes classes em java representam a estrutura do jogo com as seguintes atribuições:

1. [Arena](https://github.com/schmittjoaopedro/ai-predatory-instinct/blob/master/src/main/java/net/schmittjoaopedro/game/Arena.java): Possui uma largura e altura que determina um grid onde as cartas podem ser lançadas, é composta também por uma lista de guerreiros que representam os agentes lutando para derrubar as torres e possui uma lista de players que tem pedaços da arena de tamanho igual para jogar as cartas (normalmente são dois players, e cada um tem metadade da arena).
2. [Player](https://github.com/schmittjoaopedro/ai-predatory-instinct/blob/master/src/main/java/net/schmittjoaopedro/game/Player.java): Possui os limites da arena que ele pode usar para lançar as suas cartas, possui a quantidade de elixir acumulado que será usado para lançar novas cartas, a sequência de cartas que serão lançadas com a posição na arena (eixo x e y), e possui também uma torre que quando destruída significa a derrota do player.
3. [Warrior](https://github.com/schmittjoaopedro/ai-predatory-instinct/blob/master/src/main/java/net/schmittjoaopedro/game/warrior/Warrior.java): Representa as ações genéricas de um guerreiro. Um guerreiro quando lançado na arena tem o objetivo de ir em direção a torre inimiga ou ao guerreiro inimigo mais próximo e destruílo, se um inimigo não está sob seu raio de ataque ele se aproxima do mesmo para poder aniquila-lo. Exitem 6 tipos de guerreiros:
4.1. [Dragon](https://github.com/schmittjoaopedro/ai-predatory-instinct/blob/master/src/main/java/net/schmittjoaopedro/game/warrior/Dragon.java): Tem um custo médio, ataca guerreiros e torres, é uma carta voadora, possui vida média, tira pouco dano, e tem um raio de alcançe médio.
4.2. [Giant](https://github.com/schmittjoaopedro/ai-predatory-instinct/blob/master/src/main/java/net/schmittjoaopedro/game/warrior/Giant.java): Tem um custo alto, ataca somente a torre e não guerreiros, é uma carta terrestre, possui bastante vida, tira um dano razoável e possui alcance pequeno.
4.3. [Minipeka](https://github.com/schmittjoaopedro/ai-predatory-instinct/blob/master/src/main/java/net/schmittjoaopedro/game/warrior/Minipeka.java): Tem um custo médio, ataca guerreiros e torres terrestres, é uma carta terrestre, possui vida média, tira bastante dano e possui alcance pequeno.
4.4: [Musketeer](https://github.com/schmittjoaopedro/ai-predatory-instinct/blob/master/src/main/java/net/schmittjoaopedro/game/warrior/Musketeer.java): Tem um custo médio, ataca torres e guerreiros terrestre e aéreos, é uma carta terrestre, possui vida baixa, tira um dano médio e possui alcance longo.
4.5: [Skeleton](https://github.com/schmittjoaopedro/ai-predatory-instinct/blob/master/src/main/java/net/schmittjoaopedro/game/warrior/Skeleton.java): Tem um custo baixo, ataca torres e guerreiros terrestres, é uma carta terrestre, possui vida muito baixa, dano baixo e alcance pequeno.
4.6: [Witch](https://github.com/schmittjoaopedro/ai-predatory-instinct/blob/master/src/main/java/net/schmittjoaopedro/game/warrior/Witch.java): Tem um custo médio, ataca torres e guerreiros terrestres e aéreos, é uma carta terrestre, possui vida média, dano baixo, alcançe médio e invoca esqueletos a cada 5 de elixir.

### Iniciação

Com as cartas e o cenário modelado, agora o código principal responsável por dar vida à uma partida será apresentado. A partida acontece dentro de um ciclo de iterações, a cada iteração todos os guerreiros na arena avançam um passo, nesse passo cada guerreiro executa sua heuristíca e busca o inimigo mais próximo, depois ele verifica a distância em relação ao inimigo encontrado e decide com base no raio de alcance se ele irá se mover uma célula em direção ao inimigo ou executar um golpe de dano para tirar sua vida. A cada iteração cada player executa as seguintes ações: o elixir do player é acrescido por um, se o player tem elixir suficiente ele joga a próxima carta da sequência de cartas, aumenta o contador para a próxima carta da sequência e decremente o custo da última carta do seu elixir atual. Quando a última carta da sequência é jogada, o contador volta para a posição inicial e três opções podem ser tomadas em relação à nova sequência de cartas:
1. Não se altera a sequência e as mesmas jogadas são feitas.
2. Se cria randômicamente um nova lista de cartas e posicionamentos.
3. O algoritmo genético é executado para definir a nova sequência de cartas e posicionamentos.

### Algoritmo Genético

O [Algoritmo Genético](https://github.com/schmittjoaopedro/ai-predatory-instinct/blob/master/src/main/java/net/schmittjoaopedro/ia/GeneticAlgorithm.java) por sua vez tem o objetivo de calcular a próxima melhor sequência de cartas em um dado momento de tempo para um dado estado da arena. O algoritmo faz isso por meio de uma população de tamanho *popsize*, em que cada indíviduo representa um clone da arena no estado atual e o cromossomo é dado pelos pares de cartas e posicionamente da sequência de cartas do player a ser evoluído.
Quando um algoritmo genético é instanciado pela primeira vez, ele recebe a referência da arena onde o jogo real está executando, e por meio desse ele ten acesso ao estado atual do jogo.
Assim para um dado momento *t* e um dado estado *s* o algoritmo genético cria uma população de tamanho *popsize* clonando o estado *s* da arena atual no momento *t* para todos indíviduos. Nessa população o primeiro indíviduo da arena mantém a sequência de cartas que está sendo usadas atualmente pelo jogador na arena corrente, os demais são reiniciados randômicamente. Com isso é iniciado o processo de evolução do algoritmo que executa *k* gerações, para cada geração é cálculado o fitness de cada indíviduo da população e armazenado por elitimos o *best* individuo com maior fitness (problema de maximização). Depois disso, são aplicados os operadores de variação para gerar os novos decendentes *(popsize - 1)*. Para cada decendente é selecionado dois pais da população atual aplicando o método de torneio (o torneio é feito com o tamanho de 20% do valor de *popsize*) e executado o processo de crossover entre eles com dois pontos de cortes para definir a troca de genes e gerar o descendente, com o novo descendente gerado, aplica-se o processo de mutação com uma probabilidade de *1.5%* para cada gene substitúir randômicamente a carta e o posicionamente. Depois que, a nova população de descendentes foi gerada, a população antiga é substiuída e a próxima geração inicía o processo novamente, esse processo é repetido até atingir o critério de parada *k*. No final o melhor indíviduo da última população será usado para definir a próxima sequência de cartas e posicionamento do jogador online.
A função fitness do algoritmo, executa para cada arena clonada um execução separada considerando um avanço no tempo de *l* iterações. Finalizado as iterações o algortimo calcula a diferença entre o dano dado e o dano recebido, o objetivo final do algoritmo é tentar maximizar essa diferença. Para agilizar o processo de simulação, como cada arena é idependente, são criadas *popsize* threads para fazer os cálculos das arenas de forma paralela.

## Conclusão

Podemos ver no vídeo abaixo um exemplo de simulação do algoritmo na prática, o jogador azul está usando o algoritmo genético enquanto o jogador vermelho está usando uma lógica randômica (baseline).

<a href="http://www.youtube.com/watch?feature=player_embedded&v=fcrzux2yaJk" target="_blank"><img src="https://img.youtube.com/vi/fcrzux2yaJk/0.jpg" alt="IMAGE ALT TEXT HERE" width="240" height="180" border="10" /></a>

## Referências

https://en.wikipedia.org/wiki/Genetic_algorithm

https://clashroyale.com/
