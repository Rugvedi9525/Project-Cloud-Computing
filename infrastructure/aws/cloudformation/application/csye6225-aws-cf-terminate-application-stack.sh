#!/bin/bash
echo "DELETING STACK"
stack_name=$1

idRsa=$2
subnetExportName1="csye6225-devesh-Networking-db-subnet1Id"
subnetExportName2="csye6225-devesh-Networking-db-subnet2Id"

bucketNamePicUpload=$3

bucket_base_path='s3://'

echo $bucket_base_path$bucketNamePicUpload

aws s3 rm $bucket_base_path$bucket_name --recursive

#aws cloudformation update-stack --stack-name $stack_name --template-body file://csye6225-cf-application-update.json --parameters ParameterKey=subnetExportName1,ParameterValue=$subnetExportName1 ParameterKey=subnetExportName2,ParameterValue=$subnetExportName2 ParameterKey=keyTag,ParameterValue=$idRsa
#aws cloudformation wait stack-update-complete --stack-name $stack_name
aws cloudformation delete-stack --stack-name $stack_name
aws cloudformation wait stack-delete-complete --stack-name $stack_name


echo "STACK TERMINATED SUCCESSFULLY"