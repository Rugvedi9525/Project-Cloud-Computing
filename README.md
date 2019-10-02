csye6225-spring2018
Cloud Computing - Spring 2018

Steps :



Install Java : http://www.oracle.com/technetwork/java/javase/downloads/index.html

Install MySQL : https://dev.mysql.com/downloads/

Install JMeter : http://jmeter.apache.org/

Create Database in MySQL with name : cloud-native-app-db

Get codebase at : https://github.com/deveshkandpal24121990/csye6225-spring2018/tree/assignment2/cloud-native-app

Navigate to cloud-native-app root directory and run "mvn clean install". This will also run unit test

Make sure MySQL server is running with the created database schema

java -jar ./target/cloud-native-app-0.0.1-SNAPSHOT.war will bring up the Spring Boot Application

Access the web-app at http://localhost:8080

Open JMeter, import the script and press Run to execute the following script for load testing : https://github.com/deveshkandpal24121990/csye6225-spring2018/blob/assignment2/jmeter/Assignment2.jmx

To run cloudformation scripts :

A). Networking

eg: ./csye6225-aws-cf-create-stack.sh <networking_stack_name>

B). CI

./csye6225-aws-cf-create-ci-cd-stack.sh <ci_cd_stack_name> <code_deploy_bucket_name> <bucket_name_for_images>

C). Application ./csye6225-aws-cf-create-application-stack.sh <application_stack_name> <key_name> <sbucket_name_for_images> <domain_name>

Team Info :

Anubhav Gupta - gupta.anu@husky.neu.edu
Rugvedi Kapse - kapse.r@husky.neu.edu
Devesh Kandpal - kandpal.d@husky.neu.edu
