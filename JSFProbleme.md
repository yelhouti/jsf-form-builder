Ich habe mich gerade mit JSF Problemen herum geschlagen, die durch Ajax
Fileupload verursacht wurden. Dies war ein bekanntest Problem in JSF und
wurde auch bereits gefixed. [1](1.md)
Um bei der Entwicklung in Zukunft solchen Problemen von JSF zu entgehen,
empfehle ich ein JSF Update eures Glassfish Servers.

Anleitung:
Glassfish stoppen
Download latest JSF [2](2.md)
Datei in javax.faces.jar umbenennen
Ordner glassfish-4.0/glassfish/domains/domain1/osgi-cache löschen
javax.faces.jar nach glassfish-4.0/glassfish/modules kopieren

[1](1.md)
http://stackoverflow.com/questions/16336795/jsf-2-2-fileupload-does-not-work-with-ajax-form-appears-to-have-incorrect-enc
[2](2.md)
https://maven.java.net/service/local/artifact/maven/redirect?r=snapshots&g=org.glassfish&a=javax.faces&v=LATEST