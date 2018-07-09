MUTANT
====================

This README contain the steps that are necessary to get your application up and running.

------------
### What is this repository for? ###
We have a reactive REST service to validate if a dna is from a mutant.

------------
###Installation###

- Prerequisites (see _Technology Stack_):
    * JDK 1.8
    * gradle >= 4.5
    * Docker compose
 - Install
 
```bash
gradle install
docker-compose up
```

----------
###Generate report coberage###
```bash
gradle test jacocoTestReport
```
----------
###Run server###
```bash
gradle bootRun
```
---------------
###Technology Stack###

- [java8](http://docs.oracle.com/javase/8/ "java8")
- [SpringBoot](http://projects.spring.io/spring-boot/ "SpringBoot")
- [gradle](https://maven.apache.org/ "gradle")
- [lombok](https://projectlombok.org/ "lombok")
- [RxJava]


### Contribution guidelines ###

* Writing tests
* Use english for coding
* Comment code to be as specific as you can so everyone know how to use each class.
* Remember [KISS principle](https://en.wikipedia.org/wiki/KISS_principle)
* Code review or at least comment with someone what are you doing to have another opinion
* Remember to keep versions updated (in this file, on each pom file and on the footer of the front) every time a mayor change is done.
* Developing code should be on a dev branch, once is deployed to production you would merge that code into master and generate a tag.

###Understanding REST###
---------------
	https://spring.io/understanding/REST
