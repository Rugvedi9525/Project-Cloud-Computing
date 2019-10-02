#!/bin/bash
stack_name=$1
csye_const=-csye6225-
vpc_const=vpc
ig_const=InternetGateway
route_table_const=route-table

route_table_id=$(aws ec2 describe-route-tables --filters Name="tag-value",Values=$stack_name$csye_const$route_table_const --query [RouteTables[0].RouteTableId] --output text)
echo $route_table_id

if [ -z $route_table_id ]; then
    echo 'Error fetching route table id'
    
else
    aws ec2 delete-route-table --route-table-id $route_table_id
    echo 'DELETED ROUTE TABLE'
    ig_id=$(aws ec2 describe-internet-gateways --filters Name="tag-value",Values=$stack_name$csye_const$ig_const --query [InternetGateways[0].InternetGatewayId] --output text)
    echo $ig_id
    if [ -z $ig_id ]; then
        echo 'Error fetching Internet Gateway ID'

    else
        vpc_id=$(aws ec2 describe-vpcs --filters Name="tag-value",Values=$stack_name$csye_const$vpc_const --query [Vpcs[0].VpcId] --output text)
        if [ -z $vpc_id ]; then
            echo 'Error fetching VPC id.'

        else
            echo $vpc_id
            aws ec2 detach-internet-gateway --internet-gateway-id $ig_id --vpc-id $vpc_id
            aws ec2 delete-internet-gateway --internet-gateway-id $ig_id
            aws ec2 delete-vpc --vpc-id $vpc_id
            echo 'COMPLETED STACK TERMINATION.'
            
        fi

        

    fi

fi    













