# Ensimag 2A POO - TP 2018/19
# ============================
#
# Ce Makefile permet de compiler le test de l'ihm en ligne de commande.
# Alternative (recommandee?): utiliser un IDE (eclipse, netbeans, ...)
# Le but est ici d'illustrer les notions de "classpath", a vous de l'adapter
# a votre projet.
#
# Organisation:
#  1) Les sources (*.java) se trouvent dans le repertoire src
#     Les classes d'un package toto sont dans src/toto
#     Les classes du package par defaut sont dans src
#
#  2) Les bytecodes (*.class) se trouvent dans le repertoire bin
#     La hierarchie des sources (par package) est conservee.
#     L'archive lib/gui.jar contient les classes de l'interface graphique
#
# Compilation:
#  Options de javac:
#   -d : repertoire dans lequel sont places les .class compiles
#   -classpath : repertoire dans lequel sont cherches les .class deja compiles
#   -sourcepath : repertoire dans lequel sont cherches les .java (dependances)

all: testInvader testLecture testEtape2 testEtape3 testChefElementaire testChefEvolue

testInvader:
	javac -d bin -classpath lib/gui.jar -sourcepath src src/TestInvader.java

testLecture:
	javac -d bin -sourcepath src src/TestLecteurDonnees.java

testEtape2:
	javac -d bin -classpath lib/gui.jar -sourcepath src src/tests/TestEtape2.java

testEtape3:
	javac -d bin -classpath lib/gui.jar -sourcepath src src/tests/TestEtape3.java

testChefElementaire:
	javac -d bin -classpath lib/gui.jar -sourcepath src src/tests/TestChefElementaire.java

testChefEvolue:
	javac -d bin -classpath lib/gui.jar -sourcepath src src/tests/TestChefEvolue.java

# Execution:
# on peut taper directement la ligne de commande :
#   > java -classpath bin:lib/gui.jar TestInvader
# ou bien lancer l'execution en passant par ce Makefile:
#   > make exeInvader
exeInvader: 
	java -classpath bin:lib/gui.jar TestInvader

exeLecture: 
	java -classpath bin TestLecteurDonnees cartes/carteSujet.map
	
	
# exemple : make exeEtape4 MAP=cartes/mushroomOfHell-20x20.map 

exeChefElementaire: MAP ?= cartes/carteSujet.map
exeChefElementaire:
	java -classpath bin:lib/gui.jar tests/TestChefElementaire $(MAP)

exeChefEvolue: MAP ?= cartes/carteSujet.map
exeChefEvolue:
	java -classpath bin:lib/gui.jar tests/TestChefEvolue $(MAP)

exeEtape2:
	java -classpath bin:lib/gui.jar tests/TestEtape2 

exeEtape3:
	java -classpath bin:lib/gui.jar tests/TestEtape3

clean:
	rm -rf bin/*
