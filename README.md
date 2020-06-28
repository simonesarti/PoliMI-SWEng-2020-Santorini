# Progetto di ingegneria del software 2019-2020
![alt text](https://images-na.ssl-images-amazon.com/images/I/91irtho0CNL._SL1500_.jpg)

Il progetto consiste nello sviluppo di una versione software distribuita del gioco da tavolo Santorini.
Il codice è scritto in linguaggio Java, ed è stato realizzato applicando il pattern architetturale Model-View-Controller (MVC).
Le partite possono essere giocate utilizzando un'interfaccia da linea di comando (CLI) oppure un'interfaccia grafica (GUI).
Tutte e 9 le divinità base richieste da specifica sono utilizzabili, ed è implemementata la funzionalità avanzata di partite multiple. 


## Componenti del gruppo:
* Alessandro Polidori
* Olimpia Rivera
* Simone Sarti


## Funzionalità implementate
1. Regole Complete
2. CLI
3. GUI
4. Socket
5. FA partite multiple


## Tool utilizzati
|Tool            |Descrizione|
|----------------|-----------|
|__maven__|Strumento di gestione per software basati su Java e build automation|
|__junit 5__|Framework per testing|
|__Java Swing__|Libreria grafica di Java|
|__StarUML__|Tool per creazione di diagrammi UML|

### Coverage dei test
|Elelement, %|Class, %|Methods, %| Lines, %|
|------------|--------|----------|---------|
|__Model__|100% (41/41)|97% (184/188)| 97% (1137/1168)|
|__Controller__|100% (1/1)|100% (15/15)|91%(154/169)|
|__View__|100% (4/4)| 84% (16/19)| 74% (111/149)|


## UML
Sono presenti due diagrammi UML:
* UML di alto livello
* UML di dettaglio

Entrambi possono essere trovati [qui](https://github.com/simonesarti/ing-sw-2020-Polidori-Rivera-Sarti/tree/master/Deliverables/uml) nelle versioni .mdj e .pdf

## JavaDocs
La documentazione Javadoc relativa al progetto è disponibile [qui](https://github.com/simonesarti/ing-sw-2020-Polidori-Rivera-Sarti/tree/master/JavaDocs)


## Jars
Nella cartella **Deliverables** sono presenti due file jar compatibili con Java 14 o versioni più recenti.
Per giocare sarà necessario lanciare prima il server. Per farlo basta posizionarsi all'interno della cartella contenente il file **server.jar** con un terminale e inserire  
`java -jar server.jar`  
Una volta che il server è up sarà possibile lanciare i client (la modalità di lancio per **client.jar** è identica).  
`java -jar client.jar`  
L'indirizzo ip e il tipo di interfaccia (cli/gui) verranno richiesti all'utente da terminale dopo aver lanciato il client.  

Attenzione: 
* per una corretta visualizzazione della versione cli è necessario utilizzare un terminale che supporti gli ANSI escape
* il progetto utilizza funzionalità di Java 14 non compatibili con versioni precedenti





