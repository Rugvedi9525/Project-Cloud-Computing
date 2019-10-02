#!/bin/bash
echo "DELETING STACK"
stack_name=$1
csye_const=csye6225
vpc_const=vpc
ig_const=InternetGateway
route_table_const=route-table

vpcTag=$stack_name$csye_const$vpc_const

aws cloudformation delete-stack --stack-name $stack_name

aws cloudformation wait stack-delete-complete --stack-name $stack_name


echo "STACK TERMINATED SUCCESSFULLY"