# Capco MultiBank Workshop #

Welcome to the Capco MultiBank Night of Chances 2017 WorkShop. Before we start, 
please follow the steps below in order to prepare for the workshop.

### Git installation ###
Before we start, we need to install git client on a machine in order to checkout the
source code of the application. 

If there isn't a git client already installed on your machine, you can download the zipped
sources directly using the 'Clone or download' button on this page and then pressing 'Download ZIP'.
(see the picture below)

![Download zipped](https://github.com/plastre/noc-psd2/blob/master/media/git.PNG "Download zipped")

If you want to use the git client, please, download it from the following link:
[Git download page](https://git-scm.com/downloads)

After installation, you can checkout the repository running the following command from command
line in a directory you wish to download the sources to:

`git checkout https://github.com/plastre/noc-psd2.git`

### Maven install ###
Our application uses maven as a build tool. In order to build the application from downloaded sources. To
install maven on your machine, download it from following link:
[Maven download page](https://maven.apache.org/download.cgi)

After download, install maven according to information in following link: 
[Maven install](https://maven.apache.org/install.html)
Make sure you can run the `mvn -v` in command line and see the version of installed maven.

### Building the application ###
MultiBank demo application is build using [Spring Boot](https://projects.spring.io/spring-boot/). There are
multiple ways to run the application, the first one is using an IDE (We prefer to work with IntelliJ IDEA,
but feel free to use your favourite IDE):
* Import and run application from IntelliJ IDEA: [IntelliJ Import](https://www.jetbrains.com/help/idea/maven.html#maven_import_project_start)

Other option is to build and run it from command line. Navigate to `noc-psd2-app` directory and run the following command:
* Build and run: `mvn spring-boot:run`

After this step, you should be able to navigate to `http://localhost:8080/` in your favourite browser and
see the following login page:
![Login Page](https://github.com/plastre/noc-psd2/blob/master/media/login.PNG "Login Page")

We will start our workshop from this step. Looking forward to seeing you all.


