O presente trabalho foi desenvolvido no Linux Mint 19 de codinome Tara. O código foi escrito utilizando a ide Intellij Idea. Por último o kit de desenvolvimento escolhido foi o Oracle JDK 1.8. Ressalto que não foram realizados testes em outros sistemas ou em outros ambientes de desenvolvimento. As instruções para executar o arquivo seguem abaixo:

java -jar missionMars.jar 

Observações:
    [1]Ao inserir o arquivo passe apenas o nome do arquivo ex.: Mars.bmp ou spots.tif
    [2]As imagens de saída serão salvas na pasta outputImages com a hora e data atual:
        DiscoverPath nomes de saída:
            Result-(LocalDateTime.now()).bmp -> resultado
            Grayscale-(LocalDateTime.now()).bmp -> imagem em escala de cinza usada no cálculo do caminho
            Equalized-(LocalDateTime.now()).bmp -> imagem equalizada por histograma
        FindLife path:
            Não gera arquivos de saída
    [3]Na pasta finalImages se encontram as imagens geradas pelo algoritmo
    [4]Foi utilizado o versionador de código git e o presente trabalho se encontra hospedado no github:
        https://github.com/helioAlmeida/missionsMars
    [5]No arquivo informacoes.info, este não versionado, se encontram dados do aluno
Ambiente: 
    #Informações do java
    java version "1.8.0_181"
    Java(TM) SE Runtime Environment (build 1.8.0_181-b13)
    Java HotSpot(TM) 64-Bit Server VM (build 25.181-b13, mixed mode)
    #Informações do S.O.
    Linux Mint 19 Cinnamon
    #Informações do Git
    git version 2.17.1

