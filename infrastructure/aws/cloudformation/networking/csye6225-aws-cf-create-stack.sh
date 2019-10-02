#!/bin/bash
echo "CREATING STACK"
stackName=$1
csye_const=-csye6225-
vpc_const=vpc
ig_const=InternetGateway
public_route_table_const=public-table
private_route_table_const=private-table
web_subnet_tag=web-subnet
web_subnet2_tag=web-subnet2
db_subnet_tag=db-subnet
ws_security_group=ws-sg
db_security_group=db-sg
elb_security_group=elb-sg
vpcTag=$stackName$csye_const$vpc_const
echo $vpcTag
stackId=$(aws cloudformation create-stack --stack-name $stackName --template-body \
 file://csye6225-cf-networking.json --parameters \
ParameterKey=vpcTag,ParameterValue=$vpcTag \
ParameterKey=igTag,ParameterValue=stackName$csye_const$ig_const \
ParameterKey=publicRouteTableTag,ParameterValue=$stackName$csye_const$public_route_table_const \
ParameterKey=privateRouteTableTag,ParameterValue=$stackName$csye_const$private_route_table_const \
ParameterKey=webSubnetTag,ParameterValue=$stackName$csye_const$web_subnet_tag \
ParameterKey=webSubnet2Tag,ParameterValue=$stackName$csye_const$web_subnet2_tag \
ParameterKey=dbSubnetTag,ParameterValue=$stackName$csye_const$db_subnet_tag \
ParameterKey=elbSecurityGroupNameTag,ParameterValue=$stackName$csye_const$elb_security_group \
ParameterKey=webServerSecurityGroupNameTag,ParameterValue=$stackName$csye_const$ws_security_group \
ParameterKey=dbSecurityGroupNameTag,ParameterValue=$stackName$csye_const$db_security_group \
--query [StackId] --output text)

echo "#############################"
echo $stackId
echo "#############################"

if [ -z $stackId ]; then
    echo 'Error occurred.Dont proceed. TERMINATED'
else
    aws cloudformation wait stack-create-complete --stack-name $stackId
    echo "STACK CREATION COMPLETE."
fi








