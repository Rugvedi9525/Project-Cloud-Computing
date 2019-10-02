#!/bin/bash
echo "DELETING CI/CD STACK"
stack_name=$1

bucket_name=$2
#bucketNamePicUpload=$3
echo $stack_name

bucket_base_path='s3://'

echo $bucket_base_path$bucket_name

aws s3 rm $bucket_base_path$bucket_name --recursive
#aws s3 rm $bucket_base_path$bucketNamePicUpload --recursive

aws cloudformation delete-stack --stack-name $stack_name
aws cloudformation wait stack-delete-complete --stack-name $stack_name


echo "STACK TERMINATED SUCCESSFULLY"