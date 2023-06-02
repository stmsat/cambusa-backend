# cambusa-backend

Da [treccani.it](https://www.treccani.it/vocabolario/cambusa/):

**cambuṡa** (region. **gambuṡa**) s. f. \[dal fr. *cambuse*, che è dall’oland. medio *kabuys* «cucina sulle navi»\]. – Nelle navi militari e mercantili, locale dove vengono conservati i viveri di bordo. Per estens., l’insieme dei viveri stessi.

## Obiettivo

Creare un servizio backend RESTful per la tracciatura delle scadenze degli alimenti, anche tenendo conto delle differenze tra scadenze preferibili e tassative, avendo come ispirazione l'articolo [La vera data di scadenza degli alimenti - Il Post](https://www.ilpost.it/2020/04/19/mangiare-alimenti-dopo-data-scadenza/).

La medesima logica potrebbe essere applicata in altri ambiti, ad esempio la tracciatura delle scadenze in un armadietto farmaci.

## Specifiche tecniche e requisiti

Il progetto è basato su **Spring Boot 3** e richiede **java 17** o superiore per l'esecuzione.

Per facilitare l'esecuzione estemporanea, come impostazione predefinita, il DBMS è H2 con persistenza su file.

## Esecuzione

Se se ne è sprovvisti, scaricare Java 17 (LTS) da https://adoptium.net/ per il proprio sistema operativo, impostare la variabile d'ambiente JAVA_HOME (cartella di installazione) e aggiungere la sottocartella /bin al PATH del sistema.

Per l'avvio, anche se non si dispone di un IDE Java configurato, è sufficiente porsi nella cartella radice del progetto ed eseguire

	.\mvnw.cmd spring-boot:run

su sistemi Windows, oppure

	./mvnw spring-boot:run

su sistemi Unix, dopo eventualmente aver dato il permesso di esecuzione al file.

## Funzionamento

Sono attualmente presenti tre entità:

* Tipo: tipo del prodotto, con caratteristiche distintive quali il fatto che la data di scadenza sia preferibile o meno, il prodotto sia apribile o meno, e quanti giorni dopo la scadenza o l'apertura il prodotto si può ancora ritenere (potenzialmente) utilizzabile
* Posizione: la collocazione del prodotto, utilizzata solo a fini di ricerca e catalogazione
* Prodotto: l'oggetto concreto che è collocato spazialmente, può scadere ed essere aperto; esiste una data di scadenza specificata dal produttore, e una stabilita dal tipo di prodotto o se sia aperto o meno

La scelta del tipo di prodotto determina le regole che generano la data di scadenza "effettiva", che viene salvata staticamente sull'entità; nel caso in cui le caratteristiche di un tipo di prodotto vengano cambiate, la data di scadenza dei prodotti associati viene ricalcolata.

## API

Ad applicazione avviata, la libreria Swagger/OpenApi genera varie url per il test o l'elaborazione delle api (OpenApi versione 3).

* http://127.0.0.1:8080/swagger-ui.html o http://127.0.0.1:8080/swagger-ui/index.html: panoramica e test delle api disponibili
* http://127.0.0.1:8080/v3/api-docs: definizione in formato json
* http://127.0.0.1:8080/v3/api-docs.yaml: definizione in formato yaml

Si riportano qui versioni statiche delle definizioni (che potrebbero diventare obsolete perché per ora generate a mano):

* [docs/api-docs.json](docs/api-docs.json)
* [docs/api-docs.yaml](docs/api-docs.yaml)
* [docs/index.html](docs/index.html) (da scaricare e consultare in locale)
* [javascript/README.md](javascript/README.md)