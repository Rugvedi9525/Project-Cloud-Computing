#!/bin/bash
stack_name=$1
csye_const=-csye6225-
vpc_const=vpc
ig_const=InternetGateway
route_table_const=route-table

vpc_id=$(aws ec2 create-vpc --cidr-block 10.0.0.0/16 --region us-east-1 --query [Vpc.VpcId] --output text)

if [ -z $vpc_id ]; then
    echo 'Error creating VPC. Terminating'
fi
    

echo 'VPC CREATED SUCCESSFULLY'



aws ec2 create-tags --resources $vpc_id --tags "Key=Name,Value=$stack_name$csye_const$vpc_const"



ig_id=$(aws ec2 create-internet-gateway --query [InternetGateway.InternetGatewayId] --output text)
echo $ig_id

if [ -z $ig_id ]; then
    echo 'Error creating INTERNET GATEWAY. Terminating'
fi    

echo 'INTERNET GATEWAY CREATED SUCCESSFULLY'

aws ec2 create-tags --resources $ig_id --tags "Key=Name,Value=$stack_name$csye_const$ig_const"

aws ec2 attach-internet-gateway --internet-gateway-id $ig_id --vpc-id $vpc_id

route_table_id=$(aws ec2 create-route-table --vpc-id $vpc_id --query [RouteTable.RouteTableId] --output text)
echo $route_table_id

if [ -z $route_table_id ]; then
    echo 'Error creating ROUTE TABLE. Terminating'
fi    

echo 'ROUTE TABLE CREATED SUCCESSFULLY'

aws ec2 create-tags --resources $route_table_id --tags "Key=Name,Value=$stack_name$csye_const$route_table_const"

aws ec2 create-route --route-table-id $route_table_id --destination-cidr-block 0.0.0.0/0 --gateway-id $ig_id

echo 'COMPLETED!'